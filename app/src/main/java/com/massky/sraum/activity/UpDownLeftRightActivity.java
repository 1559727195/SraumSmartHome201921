package com.massky.sraum.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.AddTogenInterface.AddTogglenInterfacer;
import com.kyle.radiogrouplib.NestedRadioGroup;
import com.massky.sraum.R;
import com.massky.sraum.User;
import com.massky.sraum.Util.DialogUtil;
import com.massky.sraum.Util.IntentUtil;
import com.massky.sraum.Util.LogUtil;
import com.massky.sraum.Util.MusicUtil;
import com.massky.sraum.Util.MyOkHttp;
import com.massky.sraum.Util.Mycallback;
import com.massky.sraum.Util.ToastUtil;
import com.massky.sraum.Util.TokenUtil;
import com.massky.sraum.Utils.ApiHelper;
import com.massky.sraum.base.BaseActivity;
import com.massky.sraum.fragment.HomeFragment;
import com.yanzhenjie.statusview.StatusUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.massky.sraum.activity.MainGateWayActivity.MESSAGE_TONGZHI;
import static com.massky.sraum.fragment.HomeFragment.ACTION_INTENT_RECEIVER_TO_SECOND_PAGE;

/**
 * Created by zhu on 2018/1/30.
 */

public class UpDownLeftRightActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    private String status;
    private String number;
    private String type;
    private String name;
    private String name1;
    private String name2;
    @BindView(R.id.project_select)
    TextView project_select;
    //    @BindView(R.id.name1_txt)
//    TextView name1_txt;
//    @BindView(R.id.name2_txt)
//    TextView name2_txt;
//    @BindView(R.id.radio_group_out)
//    LinearLayout radio_group_out;
//    @BindView(R.id.radio_group_in)
//    LinearLayout radio_group_in;
//    @BindView(R.id.radio_group_all)
//    LinearLayout radio_group_all;
    private String loginPhone;
    private boolean vibflag;
    private boolean musicflag;
    private String boxnumber;
    private DialogUtil dialogUtil;
    private String statusflag;
    private String flagone;
    private String flagtwo;
    private String flagthree;
    private boolean whriteone = true, whritetwo = true, whritethree = true;
    private String curtain;
    private String dimmer;
    private String modeflag;
    private String temperature;
    private String windflag;
    String statusm;
    //private MessageReceiver mMessageReceiver;
    private boolean mapflag;
    private boolean statusbo;
    private String areaNumber;
    private String roomNumber;
    private Map<String, Object> mapalldevice = new HashMap<>();


    @BindView(R.id.first_txt)
    TextView first_txt;
    @BindView(R.id.second_txt)
    TextView second_txt;
    @BindView(R.id.third_txt)
    TextView three_txt;
    @BindView(R.id.four_txt)
    TextView four_txt;
    @BindView(R.id.nestedGroup)
    com.kyle.radiogrouplib.NestedRadioGroup radioLayout;
    @BindView(R.id.first_linear)
    com.kyle.radiogrouplib.NestedRadioLayout first_linear;
    @BindView(R.id.second_linear)
    com.kyle.radiogrouplib.NestedRadioLayout second_linear;
    @BindView(R.id.third_linear)
    com.kyle.radiogrouplib.NestedRadioLayout third_linear;
    @BindView(R.id.four_linear)
    com.kyle.radiogrouplib.NestedRadioLayout four_linear;
    boolean first_times;


    @Override
    protected int viewId() {
        return R.layout.updown_leftright_window_act;
    }

    @Override
    protected void onView() {
        StatusUtils.setFullToStatusBar(this);
        dialogUtil = new DialogUtil(this);
//        init_receiver_control();
        init_View();
        registerMessageReceiver();
    }

    private void init_View() {
        radioLayout.setOnCheckedChangeListener((group, checkedId) -> {
            Log.d("MainActivity", checkedId + "");
            switch (checkedId) {
                case R.id.first_linear:
                    switch (type) {
                        case "19":
                            statusm = "1";
                            break;
                        case "20":
                            statusm = "1";
                            break;
                        case "21":
                            statusm = "1";
                            break;
                    }
                    break;
                case R.id.second_linear:
                    switch (type) {
                        case "19":
                            statusm = "2";
                            break;
                        case "20":
                            statusm = "2";
                            break;
                        case "21":
                            statusm = "2";
                            break;
                    }
                    break;
                case R.id.third_linear:
                    switch (type) {
                        case "19":
                            statusm = "0";
                            break;
                        case "20":
                            statusm = "0";
                            break;
                        case "21":
                            statusm = "3";
                            break;
                    }
                    break;
                case R.id.four_linear:
                    switch (type) {
                        case "21":
                            statusm = "4";
                            break;
                    }
                    break;
            }

            if (first_times) {
                first_times = false;
                return;
            }
            sraum_device_control();
        });
    }

    @Override
    protected void onEvent() {
        back.setOnClickListener(this);
    }

    @Override
    protected void onData() {
        init_Data();
        init_type();
    }


    private void init_Data() {
        Bundle bundle = IntentUtil.getIntentBundle(UpDownLeftRightActivity.this);
        type = bundle.getString("type");
        number = bundle.getString("number");
        name1 = bundle.getString("name1");
        name2 = bundle.getString("name2");
        name = bundle.getString("name");
        status = bundle.getString("status");
        areaNumber = bundle.getString("areaNumber");
        roomNumber = bundle.getString("roomNumber");//当前房间编号
        mapalldevice = (Map<String, Object>) bundle.getSerializable("mapalldevice");
        if (mapalldevice != null) {
            type = (String) mapalldevice.get("type");
            //初始化窗帘参数
            statusm = status;
            first_times = true;
            change_status_toui(type, status);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                UpDownLeftRightActivity.this.finish();
                break;
        }
    }


    private void init_type() {
        switch (type) {
            case "19":
                four_linear.setVisibility(View.GONE);
                first_txt.setText("上升");
                second_txt.setText("下降");
                three_txt.setText("暂停");
                project_select.setText("智能升降");
                break;
            case "20":
                four_linear.setVisibility(View.GONE);
                first_txt.setText("向左");
                second_txt.setText("向右");
                three_txt.setText("暂停");
                project_select.setText("智能平移");
                break;
            case "21":
                four_linear.setVisibility(View.VISIBLE);
                first_txt.setText("高位");
                second_txt.setText("中位");
                three_txt.setText("低位");
                four_txt.setText("暂停");
                project_select.setText("智能高中低");
                break;
        }
    }



    private MessageReceiver mMessageReceiver;
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_INTENT_RECEIVER_TO_SECOND_PAGE);
        registerReceiver(mMessageReceiver, filter);
    }
    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(ACTION_INTENT_RECEIVER_TO_SECOND_PAGE)) {
                Log.e("zhu", "LamplightActivity:" + "LamplightActivity");
                //控制部分的二级页面进去要同步更新推送的信息显示 （推送的是消息）。
                upload();
            }
        }
    }


    //下载设备信息并且比较状态（为了显示开关状态）
    private void upload() {
        Map<String, String> mapdevice = new HashMap<>();
        mapdevice.put("areaNumber", areaNumber);
        mapdevice.put("roomNumber", roomNumber);
        mapdevice.put("token", TokenUtil.getToken(this));
        dialogUtil.loadDialog();
        MyOkHttp.postMapString(ApiHelper.sraum_getOneRoomInfo, mapdevice, new Mycallback(new AddTogglenInterfacer() {
            @Override
            public void addTogglenInterfacer() {//获取togglen成功后重新刷新数据
                upload();
            }
        }, this, dialogUtil) {
            @Override
            public void onSuccess(User user) {
                super.onSuccess(user);
                //拿到设备状态值
                for (User.device d : user.deviceList) {
                    if (d.number.equals(number)) {
                        //匹配状值设置当前状态
                        if (d.status != null) {
                            //进行判断是否为窗帘
                            statusflag = d.status;
                            LogUtil.eLength("下载数据", statusflag);
                            change_status_toui(type, d.status);
                        }
                    }
                }
            }

            @Override
            public void wrongToken() {
                super.wrongToken();
            }
        });
    }

    /**
     * 根据status去切换UI显示
     */
    private void change_status_toui(String type, String status) {
        switch (type) {
            case "19":
                switch (status) {
                    case "1":
                        first_linear.setChecked(true);
                        break;
                    case "2":
                        second_linear.setChecked(true);
                        break;
                    case "0":
                        third_linear.setChecked(true);
                        break;
                }
                break;
            case "20":
                switch (status) {
                    case "1":
                        first_linear.setChecked(true);
                        break;
                    case "2":
                        second_linear.setChecked(true);
                        break;
                    case "0":
                        third_linear.setChecked(true);
                        break;
                }
                break;
            case "21":
                switch (status) {
                    case "1":
                        first_linear.setChecked(true);
                        break;
                    case "2":
                        second_linear.setChecked(true);
                        break;
                    case "3":
                        third_linear.setChecked(true);
                        break;
                    case "0":
                        four_linear.setChecked(true);
                        break;
                }
                break;
        }
    }


    private void sraum_device_control() {
        Map<String, Object> mapalldevice1 = new HashMap<>();
        List<Map> listobj = new ArrayList<>();
        Map map = new HashMap();
        map.put("type", mapalldevice.get("type").toString());
        map.put("number", mapalldevice.get("number").toString());
        map.put("name", mapalldevice.get("name").toString());
        map.put("status", statusm);
        map.put("mode", mapalldevice.get("mode").toString());
        map.put("dimmer", mapalldevice.get("dimmer").toString());
        map.put("temperature", mapalldevice.get("temperature").toString());
        map.put("speed", mapalldevice.get("speed").toString());
        listobj.add(map);
        mapalldevice1.put("token", TokenUtil.getToken(UpDownLeftRightActivity.this));
        mapalldevice1.put("areaNumber", areaNumber);
        mapalldevice1.put("deviceInfo", listobj);

        MyOkHttp.postMapObject(ApiHelper.sraum_deviceControl, mapalldevice1, new Mycallback(() -> sraum_device_control(), UpDownLeftRightActivity.this, dialogUtil) {

            @Override
            public void wrongToken() {
                super.wrongToken();
            }

            @Override
            public void wrongBoxnumber() {
                ToastUtil.showToast(UpDownLeftRightActivity.this, "areaNumber\n" +
                        "不存在");
            }

            @Override
            public void fourCode() {
                super.fourCode();
                change_status_toui(type, status);
                ToastUtil.showToast(UpDownLeftRightActivity.this, "控制失败");
            }

            @Override
            public void threeCode() {
                super.threeCode();
                change_status_toui(type, status);
                ToastUtil.showToast(UpDownLeftRightActivity.this, "deviceInfo 不正确");
            }

            @Override
            public void defaultCode() {
                change_status_toui(type, status);
                ToastUtil.showToast(UpDownLeftRightActivity.this, "操作失败");
            }

            @Override
            public void pullDataError() {
                change_status_toui(type, status);
                ToastUtil.showToast(UpDownLeftRightActivity.this, "操作失败");
            }

            @Override
            public void onSuccess(User user) {
                super.onSuccess(user);
                status = statusm;
                if (vibflag) {
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(200);
                }
                if (musicflag) {
                    MusicUtil.startMusic(UpDownLeftRightActivity.this, 1, "");
                } else {
                    MusicUtil.stopMusic(UpDownLeftRightActivity.this, "");
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }
}
