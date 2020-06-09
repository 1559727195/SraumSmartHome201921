package com.massky.sraum.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.AddTogenInterface.AddTogglenInterfacer;
import com.gizwits.gizwifisdk.api.GizWifiDevice;
import com.gizwits.gizwifisdk.enumration.GizWifiErrorCode;
import com.massky.sraum.R;
import com.massky.sraum.User;
import com.massky.sraum.Util.DialogUtil;
import com.massky.sraum.Util.MyOkHttp;
import com.massky.sraum.Util.Mycallback;
import com.massky.sraum.Util.SharedPreferencesUtil;
import com.massky.sraum.Util.ToastUtil;
import com.massky.sraum.Util.TokenUtil;
import com.massky.sraum.Utils.ApiHelper;
import com.massky.sraum.activity.AddWifiDevActivity;
import com.massky.sraum.activity.EditTablePMActivity;
import com.massky.sraum.activity.SelectControlApplianceActivity;
import com.massky.sraum.activity.SelectInfraredForwardActivity;
import com.massky.sraum.adapter.SelectDevTypeAdapter;
import com.massky.sraum.adapter.SelectWifiDevAdapter;
import com.massky.sraum.myzxingbar.qrcodescanlib.CaptureActivity;
import com.massky.sraum.tool.Constants;
import com.massky.sraum.widget.ListViewForScrollView;
import com.yaokan.sdk.utils.Logger;
import com.yaokan.sdk.utils.Utility;
import com.yaokan.sdk.wifi.DeviceManager;
import com.yaokan.sdk.wifi.GizWifiCallBack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import okhttp3.Call;

import static android.app.Activity.RESULT_OK;
import static com.massky.sraum.Util.AES.Decrypt;

/**
 * Created by masskywcy on 2018-11-13.
 */

public class WifiItemFragment extends Fragment {
    private View view;
    private String title;


    private SelectWifiDevAdapter adapter_wifi;
    private List<Map> gatewayList = new ArrayList<>();


    //        //type：设备类型，1-灯，2-调光，3-空调，4-窗帘，5-新风，6-地暖,7-门磁，8-人体感应，9-水浸检测器，10-入墙PM2.5
    //11-紧急按钮，12-久坐报警器，13-烟雾报警器，14-天然气报警器，15-智能门锁，16-直流电阀机械手

    //wifi类型
    //wifi类型
    private String[] types_wifi = { //红外转发器类型暂定为hongwai,遥控器类型暂定为yaokong
            "hongwai", "yaokong", "101", "103", "102"
    };

    private int[] icon_wifi = {
            R.drawable.icon_type_hongwaizfq_90,
            R.drawable.icon_type_yaokongqi_90,
            R.drawable.icon_type_shexiangtou_90,
            // R.drawable.icon_type_pmmofang_90,
            R.drawable.icon_type_keshimenling_90,
            R.drawable.icon_pmmofang_90 //桌面PM2.5

    };

    private int[] iconNam_wifi = {R.string.hongwai, R.string.yaokongqi, R.string.shexiangtou, R.string.keshimenling, R.string.table_pm};//, R.string.pm_mofang


    private SelectDevTypeAdapter adapter;
    private ConfigDialogFragment newFragment;
    private GatewayDialogFragment newGatewayFragment;
    private DialogUtil dialogUtil;
    private List<Map> list_hand_scene = new ArrayList<>();


    /**
     * 小苹果绑定列表
     */
    private DeviceManager mDeviceManager;
    List<GizWifiDevice> wifiDevices = new ArrayList<GizWifiDevice>();
    private List<String> deviceNames = new ArrayList<String>();
    private GizWifiDevice mGizWifiDevice = null;
    private List<Map> wifi_apple_list = new ArrayList<>();

    private String TAG = "robin debug";
    private String mac;
    private String number;
    private Handler handler = new Handler();
    private ListViewForScrollView wifi_list;


    public static WifiItemFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("title", title);
        WifiItemFragment fragment = new WifiItemFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wifi_selects, null);
        Bundle bundle = getArguments();
        title = (String) bundle.getSerializable("title");
        // Log.e("TAG","bbsCategoryList="+bbsCategoryList);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onView(view);
        dialogUtil = new DialogUtil(getActivity());

        onWifi();


        onData();
    }

    private void onView(View view) {
        wifi_list = view.findViewById(R.id.wifi_list);
    }


    private void onData() {
        adapter_wifi = new SelectWifiDevAdapter(getActivity(), icon_wifi, iconNam_wifi);
        wifi_list.setAdapter(adapter_wifi);

        wifi_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent_wifi = null;
                switch (types_wifi[position]) {
                    case "101":
                    case "103":
                        intent_wifi = new Intent(getActivity(), AddWifiDevActivity.class);
                        intent_wifi.putExtra("type", types_wifi[position]);
                        startActivity(intent_wifi);
                        break;
                    case "102":
                        Intent openCameraIntent = new Intent(getActivity(), CaptureActivity.class);
                        startActivityForResult(openCameraIntent, Constants.SCAN_REQUEST_CODE);
                        break;
                    case "hongwai":
                        intent_wifi = new Intent(getActivity(), AddWifiDevActivity.class);
                        intent_wifi.putExtra("type", types_wifi[position]);
                        startActivity(intent_wifi);
                        break;
                    case "yaokong":
                        getOtherDevices();
                        break;
                }
            }
        });
    }


    /**
     * 获取门磁等第三方设备
     */
    private void getOtherDevices() {
        if (dialogUtil != null) {
            dialogUtil.loadDialog();
        }
        Map<String, String> mapdevice = new HashMap<>();
        mapdevice.put("token", TokenUtil.getToken(getActivity()));
        String areaNumber = (String) SharedPreferencesUtil.getData(getActivity(), "areaNumber", "");
//        mapdevice.put("boxNumber", TokenUtil.getBoxnumber(SelectSensorActivity.this));
        mapdevice.put("areaNumber", areaNumber);
        MyOkHttp.postMapString(ApiHelper.sraum_getWifiAppleInfos
                , mapdevice, new Mycallback(new AddTogglenInterfacer() {
                    @Override
                    public void addTogglenInterfacer() {//刷新togglen数据
                        getOtherDevices();
                    }
                }, getActivity(), dialogUtil) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        super.onError(call, e, id);
                    }

                    @Override
                    public void pullDataError() {
                        super.pullDataError();
                    }

                    @Override
                    public void emptyResult() {
                        super.emptyResult();
                    }

                    @Override
                    public void wrongToken() {
                        super.wrongToken();
                        //重新去获取togglen,这里是因为没有拉到数据所以需要重新获取togglen
                    }

                    @Override
                    public void wrongBoxnumber() {
                        super.wrongBoxnumber();
                    }

                    @Override
                    public void onSuccess(final User user) {
                        list_hand_scene = new ArrayList<>();
                        for (int i = 0; i < user.controllerList.size(); i++) {
                            Map<String, String> mapdevice = new HashMap<>();
                            mapdevice.put("name", user.controllerList.get(i).name);
                            mapdevice.put("number", user.controllerList.get(i).number);
                            mapdevice.put("type", user.controllerList.get(i).type);
                            mapdevice.put("controllerId", user.controllerList.get(i).controllerId);
                            list_hand_scene.add(mapdevice);
                        }

                        if (list_hand_scene.size() == 1) {
                            if (wifiDevices.size() != 0) {
                                choose_the_brand(0);
                            } else {
//                                update(mDeviceManager.getCanUseGizWifiDevice());
                                ToastUtil.showToast(getActivity(), "请与" + list_hand_scene.get(0)
                                        .get("name").toString()
                                        +
                                        "在同一网络后再添加");
                            }
                        } else {
                            Intent intent_wifi =
                                    new Intent(getActivity(),
                                            SelectInfraredForwardActivity.class);
                            intent_wifi.putExtra("list_hand_scene", (Serializable) list_hand_scene);
                            startActivity(intent_wifi);
                        }
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        mDeviceManager.setGizWifiCallBack(mGizWifiCallBack);
        update(mDeviceManager.getCanUseGizWifiDevice());
    }


    private GizWifiCallBack mGizWifiCallBack = new GizWifiCallBack() {

        @Override
        public void didUnbindDeviceCd(GizWifiErrorCode result, String did) {
            super.didUnbindDeviceCd(result, did);
            if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {
                // 解绑成功
                Logger.d(TAG, "解除绑定成功");
            } else {
                // 解绑失败
                Logger.d(TAG, "解除绑定失败");
            }
        }

        @Override
        public void didBindDeviceCd(GizWifiErrorCode result, String did) {    //
            super.didBindDeviceCd(result, did);
            if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {//
                // 绑定成功
                Logger.d(TAG, "绑定成功");
            } else {
                // 绑定失败
                Logger.d(TAG, "绑定失败");
            }
        }

        @Override
        public void didSetSubscribeCd(GizWifiErrorCode result, GizWifiDevice device, boolean isSubscribed) {
            super.didSetSubscribeCd(result, device, isSubscribed);
            if (result == GizWifiErrorCode.GIZ_SDK_SUCCESS) {
                // 解绑成功
                Logger.d(TAG, (isSubscribed ? "订阅" : "取消订阅") + "成功");
            } else {
                // 解绑失败
                Logger.d(TAG, "订阅失败");
            }
        }

        @Override
        public void discoveredrCb(GizWifiErrorCode result,
                                  List<GizWifiDevice> deviceList) {
            Logger.d(TAG,
                    "discoveredrCb -> deviceList size:" + deviceList.size()
                            + "  result:" + result);
            switch (result) {
                case GIZ_SDK_SUCCESS:
                    Logger.e(TAG, "load device  sucess");
                    update(deviceList);
//                    if(deviceList.get(0).getNetStatus()==GizWifiDeviceNetStatus.GizDeviceOffline)

                    break;
                default:
                    break;
            }
        }
    };


    private void onWifi() {
        mDeviceManager = DeviceManager
                .instanceDeviceManager(getActivity().getApplicationContext());
    }


    /**
     * 跳转到选择品牌界面
     *
     * @param position
     */
    private void choose_the_brand(int position) {
        mac = (String) list_hand_scene.get(position).get("controllerId");
        number = list_hand_scene.get(position).get("number").toString();
        //去根据mac去服务器端下载GizWifiDevice
        String apple_name = "";
        for (int i = 0; i < list_hand_scene.size(); i++) {
            if (list_hand_scene.get(i).get("controllerId").equals(mac)) {
                apple_name = list_hand_scene.get(i).get("name").toString();

            }
        }
        get_to_wifi(mac, apple_name);
    }

    private void get_to_wifi(String mac, String apple_name) {

        for (int i = 0; i < wifiDevices.size(); i++) {
            if (wifiDevices.get(i).getMacAddress().equals(mac)) {
                mGizWifiDevice = wifiDevices.get(i);
            }
        }
        if (!Utility.isEmpty(mGizWifiDevice)) { //
            mDeviceManager.bindRemoteDevice(mGizWifiDevice);
            final GizWifiDevice finalMGizWifiDevice = mGizWifiDevice;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mDeviceManager.setSubscribe(finalMGizWifiDevice, true);
                }
            }, 1000);
        } else {
            ToastUtil.showToast(getActivity(), "请与" + apple_name
                    +
                    "在同一网络后再控制");
            return;
        }
        toControlApplianAct();
    }

    private void toControlApplianAct() {
        Intent intent = new Intent(getActivity(), SelectControlApplianceActivity.class);
        if (mGizWifiDevice == null) {
            return;
        }
        intent.putExtra("GizWifiDevice", mGizWifiDevice);
//        intent.putExtra("tid", getIntent().getSerializableExtra("tid"));
        intent.putExtra("number", number);
        startActivity(intent);
    }


    void update(List<GizWifiDevice> gizWifiDevices) {
        GizWifiDevice mGizWifiDevice = null;


        if (gizWifiDevices == null) {
            deviceNames.clear();
        } else if (gizWifiDevices != null && gizWifiDevices.size() >= 1) {
//            wifiDevices.clear();
            wifiDevices.addAll(gizWifiDevices);
            HashSet<GizWifiDevice> h = new HashSet<GizWifiDevice>(wifiDevices);
            wifiDevices.clear();
            for (GizWifiDevice gizWifiDevice : h) {
                wifiDevices.add(gizWifiDevice);
            }
            deviceNames.clear();
//            for (int i = 0; i < wifiDevices.size(); i++) {
////                deviceNames.add(wifiDevices.get(i).getProductName() + "("
////                        + wifiDevices.get(i).getMacAddress() + ") "
////                        + getBindInfo(wifiDevices.get(i).isBind()) + "\n"
////                        + getLANInfo(wifiDevices.get(i).isLAN()) + "  " + getOnlineInfo(wifiDevices.get(i).getNetStatus()));
//                mGizWifiDevice = wifiDevices.get(i);
//                // list_hand_scene
//                // 绑定选中项
//                if (!Utility.isEmpty(mGizWifiDevice)) { //
//                    mDeviceManager.bindRemoteDevice(mGizWifiDevice);
//                    final GizWifiDevice finalMGizWifiDevice = mGizWifiDevice;
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mDeviceManager.setSubscribe(finalMGizWifiDevice, true);
//                        }
//                    }, 1000);
//                }
//            }
            //去绑定和订阅
        }
//        adapter.notifyDataSetChanged();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == Constants.SCAN_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra("result");
                Log.e("robin debug", "content:" + content);
                if (TextUtils.isEmpty(content))
                    return;
                //在这里解析二维码，变成房号
                // 密钥
                String key = "ztlmassky6206ztl";
                // 解密
                String DeString = null;
                try {
//                    content = "0a4ab23ad13aac565069283aac3882e5";
                    DeString = Decrypt(content, key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (DeString == null) {
                    ToastUtil.showToast(getActivity(), "此二维码不是PM2.5二维码");
                } else {
//                    scanlinear.setVisibility(View.GONE);
//                    detairela.setVisibility(View.VISIBLE);
//                    gateid.setText(DeString);
//                    gatedditexttwo.setText("");
                    //跳转到编辑网关界面
                    if (DeString.length() == 12) {

                        StringBuffer stringBuffer = new StringBuffer();
                        for (int i = 0; i < 6; i++) {
                            stringBuffer.append(DeString.substring(i * 2, i * 2 + 2)).append(":");
                        }

                        Intent intent = new Intent(getActivity(), EditTablePMActivity.class);
                        intent.putExtra("mac", stringBuffer.toString().substring(0,stringBuffer.length() - 1).toUpperCase());
                        intent.putExtra("id", DeString.toUpperCase());
                        startActivity(intent);
                    }
                }
            }
        }
    }


}
