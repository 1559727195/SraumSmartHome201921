package com.massky.sraum.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.AddTogenInterface.AddTogglenInterfacer;
import com.king.photo.activity.MessageSendActivity;
import com.massky.sraum.R;
import com.massky.sraum.User;
import com.massky.sraum.Util.BitmapUtil;
import com.massky.sraum.Util.DialogUtil;
import com.massky.sraum.Util.MyOkHttp;
import com.massky.sraum.Util.Mycallback;
import com.massky.sraum.Util.SharedPreferencesUtil;
import com.massky.sraum.Util.TokenUtil;
import com.massky.sraum.Utils.ApiHelper;
import com.massky.sraum.activity.AboutActivity;
import com.massky.sraum.activity.AreaSettingActivity;
import com.massky.sraum.activity.DeviceSettingActivity;
import com.massky.sraum.activity.FamilySettingActivity;
import com.massky.sraum.activity.HomeSettingActivity;
import com.massky.sraum.activity.LoginGateWayActivity;
import com.massky.sraum.activity.MyDeviceListActivity;
import com.massky.sraum.activity.MyfamilyActivity;
import com.massky.sraum.activity.PersonMessageActivity;
import com.massky.sraum.activity.SettingActivity;
import com.massky.sraum.activity.ShareDeviceActivity;
import com.massky.sraum.activity.SystemMessageActivity;
import com.massky.sraum.activity.WangGuanListActivity;
import com.massky.sraum.base.BaseFragment1;
import com.massky.sraum.event.MyDialogEvent;
import com.massky.sraum.myzxingbar.qrcodescanlib.CaptureActivity;
import com.massky.sraum.permissions.RxPermissions;
import com.massky.sraum.tool.Constants;
import com.yanzhenjie.statusview.StatusUtils;
import com.yanzhenjie.statusview.StatusView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhu on 2017/11/30.
 */

public class MineFragment extends BaseFragment1 {
    private List<Map> list_alarm = new ArrayList<>();
    private int currentpage = 1;
    private Handler mHandler = new Handler();
    @BindView(R.id.status_view)
    StatusView statusView;
    private View account_view;
    private Button cancelbtn_id, camera_id, photoalbum;
    private DialogUtil dialogUtil;
    @BindView(R.id.headportrait)
    CircleImageView headportrait;
    @BindView(R.id.wode_device_share_rel)
    RelativeLayout wode_device_share_rel;
    private LinearLayout linear_popcamera;
    @BindView(R.id.wangguan_list_rel)
    RelativeLayout wangguan_list_rel;
    @BindView(R.id.sugestion_rel)
    RelativeLayout sugestion_rel;
    @BindView(R.id.wode_sao_rel)
    RelativeLayout wode_sao_rel;
    @BindView(R.id.system_infor_rel)
    RelativeLayout system_infor_rel;
    @BindView(R.id.about_rel)
    RelativeLayout about_rel;
    @BindView(R.id.setting_rel)
    RelativeLayout setting_rel;
    @BindView(R.id.dev_manager_rel)
    RelativeLayout dev_manager_rel;
    @BindView(R.id.room_manager_rel)
    RelativeLayout room_manager_rel;
    @BindView(R.id.area_manager_rel)
    RelativeLayout area_manager_rel;
    @BindView(R.id.add_family_rel)
    RelativeLayout add_family_rel;
    @BindView(R.id.nicheng_name)
    TextView nicheng_name;
    private User.userinfo userinfo;
    private int intfirst;

    @Override
    protected void onData() {

    }

    @Override
    protected void onEvent() {
        headportrait.setOnClickListener(this);
        wode_device_share_rel.setOnClickListener(this);
        wangguan_list_rel.setOnClickListener(this);
        sugestion_rel.setOnClickListener(this);
        wode_sao_rel.setOnClickListener(this);
        system_infor_rel.setOnClickListener(this);
        about_rel.setOnClickListener(this);
        setting_rel.setOnClickListener(this);
        dev_manager_rel.setOnClickListener(this);
        room_manager_rel.setOnClickListener(this);
        area_manager_rel.setOnClickListener(this);
        add_family_rel.setOnClickListener(this);
    }


    //账号个人基本信息
    private void getAccountInfo() {
//        dialogUtil.loadDialog();
        account_number_Act();
    }

    private void account_number_Act() {
        Map<String, Object> map = new HashMap<>();
        String areaNumber = (String) SharedPreferencesUtil.getData(getActivity(), "areaNumber", "");
        map.put("token", TokenUtil.getToken(getActivity()));
//        map.put("areaNumber", areaNumber);

        MyOkHttp.postMapObject(ApiHelper.sraum_getAccountInfo, map, new Mycallback
                (new AddTogglenInterfacer() {
                    @Override
                    public void addTogglenInterfacer() {
                        account_number_Act();
                    }
                }, getActivity(), null) {
            @Override
            public void onSuccess(User user) {
                super.onSuccess(user);
                userinfo = user.userInfo;
                handler.sendEmptyMessage(0);
            }

            @Override
            public void wrongToken() {
                super.wrongToken();
            }
        });
    }

    @Override
    public void onEvent(MyDialogEvent eventData) {
        currentpage = 1;
    }

    @Override
    protected int viewId() {
        return R.layout.mine_frag;
    }

    @Override
    public void onResume() {
        super.onResume();
        headportrait.setImageBitmap(BitmapUtil.stringtoBitmap((String)
                SharedPreferencesUtil.getData(getActivity(), "avatar", "")));
        new Thread(new Runnable() {
            @Override
            public void run() {
                getAccountInfo();
            }
        }).start();

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            nicheng_name.setText(userinfo.userId);
            SharedPreferencesUtil.saveData(getActivity(), "userName", nicheng_name.getText().toString());
        }
    };

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (intfirst == 1) {
                intfirst = 2;
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getAccountInfo();
                    }
                }).start();
            }
        }
    }


    @Override
    protected void onView(View view) {
        list_alarm = new ArrayList<>();
        dialogUtil = new DialogUtil(getActivity());
        StatusUtils.setFullToStatusBar(getActivity());  // StatusBar.
        init_permissions();
    }

    private void init_permissions() {

        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions((AppCompatActivity) getActivity());
        permissions.request(Manifest.permission.CAMERA).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.headportrait://头像选择
//                dialogUtil.loadViewBottomdialog();
                startActivity(new Intent(getActivity(), PersonMessageActivity.class));
                break;
            case R.id.wode_device_share_rel:
                startActivity(new Intent(getActivity(), ShareDeviceActivity.class));
                break;//设备共享
            case R.id.wangguan_list_rel://网关列表
                startActivity(new Intent(getActivity(), WangGuanListActivity.class));
                break;
            case R.id.sugestion_rel:
                startActivity(new Intent(getActivity(), MessageSendActivity.class));
                break;//意见反馈
            case R.id.wode_sao_rel:
                Intent openCameraIntent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(openCameraIntent, Constants.SCAN_REQUEST_CODE);
                break;//我的扫一扫
            case R.id.system_infor_rel:
                startActivity(new Intent(getActivity(), SystemMessageActivity.class));
                break;//系统消息
            case R.id.about_rel:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;//关于
            case R.id.setting_rel:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;//设置
            case R.id.dev_manager_rel://设备列表
                startActivity(new Intent(getActivity(), DeviceSettingActivity.class));
                break;
            case R.id.room_manager_rel://房间管理
                startActivity(new Intent(getActivity(), HomeSettingActivity.class));
                break;
            case R.id.area_manager_rel://区域设置
                startActivity(new Intent(getActivity(), AreaSettingActivity.class));
                break;
            case R.id.add_family_rel:
                Intent mintent = new Intent(getActivity(), FamilySettingActivity
                        .class);
                startActivity(mintent);
                break;
        }
    }

    public static MineFragment newInstance() {
        MineFragment newFragment = new MineFragment();
        Bundle bundle = new Bundle();
        newFragment.setArguments(bundle);
        return newFragment;
    }
}
