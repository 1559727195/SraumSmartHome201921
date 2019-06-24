package com.massky.sraum.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.AddTogenInterface.AddTogglenInterfacer;
import com.example.jpushdemo.ExampleUtil;
import com.google.android.material.tabs.TabLayout;
import com.massky.sraum.R;
import com.massky.sraum.User;
import com.massky.sraum.Util.DialogUtil;
import com.massky.sraum.Util.IntentUtil;
import com.massky.sraum.Util.LogUtil;
import com.massky.sraum.Util.MyOkHttp;
import com.massky.sraum.Util.Mycallback;
import com.massky.sraum.Util.SharedPreferencesUtil;
import com.massky.sraum.Util.TokenUtil;
import com.massky.sraum.Utils.ApiHelper;
import com.massky.sraum.adapter.DynamicFragmentViewPagerAdapter;
import com.massky.sraum.base.BaseActivity;
import com.massky.sraum.fragment.AutoSceneFragment;
import com.massky.sraum.fragment.HandSceneFragment;
import com.massky.sraum.fragment.PmFragment;
import com.yanzhenjie.statusview.StatusUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.InjectView;

import static com.massky.sraum.fragment.HomeFragment.ACTION_INTENT_RECEIVER_TABLE_PM;
import static com.massky.sraum.fragment.HomeFragment.ACTION_INTENT_RECEIVER_TO_SECOND_PAGE;

/**
 * Created by zhu on 2018/1/25.
 */

public class Pm25SecondActivity extends BaseActivity {
    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.temp_label)
    TextView temp_label;
    @InjectView(R.id.temp_txt)
    TextView temp_txt;
    @InjectView(R.id.pm_label)
    TextView pm_label;
    @InjectView(R.id.pm_txt)
    TextView pm_txt;
    @InjectView(R.id.shidu_label)
    TextView shidu_label;
    @InjectView(R.id.shidu_txt)
    TextView shidu_txt;
    @InjectView(R.id.project_select)
    TextView project_select;

    private String loginPhone;
    private boolean vibflag;
    private boolean musicflag;
    private String boxnumber;
    private DialogUtil dialogUtil;
    private String statusflag;
    private String dimmer;
    private String modeflag;
    private String temperature;
    private String windflag;
    private String type;
    private String number;
    private String name;
    private String areaNumber;
    private String roomNumber;
    private Map<String, Object> mapalldevice;
    private MessageReceiver mMessageReceiver;
    private String speed;
    private String mode;

    @Override
    protected int viewId() {
        return R.layout.pm25_new_act;
    }

    @Override
    protected void onView() {
        StatusUtils.setFullToStatusBar(this);
        registerMessageReceiver();
        loginPhone = (String) SharedPreferencesUtil.getData(Pm25SecondActivity.this, "loginPhone", "");
        SharedPreferences preferences = getSharedPreferences("sraum" + loginPhone,
                Context.MODE_PRIVATE);
        boxnumber = (String) SharedPreferencesUtil.getData(Pm25SecondActivity.this, "boxnumber", "");
        dialogUtil = new DialogUtil(Pm25SecondActivity.this);
        init_Data();
    }

    private void init_Data() {
        Bundle bundle = IntentUtil.getIntentBundle(Pm25SecondActivity.this);
        type = bundle.getString("type");
        number = bundle.getString("number");
        name = bundle.getString("name");
        project_select.setText(name);
        areaNumber = bundle.getString("areaNumber");
        roomNumber = bundle.getString("roomNumber");//当前房间编号
        show_pm(bundle);
    }

    /**
     * 显示pm2.5
     *
     * @param bundle
     */
    private void show_pm(Bundle bundle) {
        mapalldevice = (Map<String, Object>) bundle.getSerializable("mapalldevice");
        if (mapalldevice != null) {

            switch (type) {
                case "10"://入墙Pm2.5
                    dimmer = (String) mapalldevice.get("dimmer");//pm25
                    temperature = (String) mapalldevice.get("temperature");//温度
                    speed = (String) mapalldevice.get("speed");//湿度
                    temp_label.setText("温度");
                    pm_label.setText("PM2.5");
                    shidu_label.setText("湿度");

                    temp_txt.setText(temperature);
                    pm_txt.setText(dimmer);
                    shidu_txt.setText(speed);
                    break;
                case "102"://桌面pm2.5
                    dimmer = (String) mapalldevice.get("dimmer");//pm25
                    mode = (String) mapalldevice.get("mode");//pm1.0
                    temperature = (String) mapalldevice.get("temperature");//pm10
                    temp_label.setText("PM1.0");
                    pm_label.setText("PM2.5");
                    shidu_label.setText("PM10");

                    temp_txt.setText(mode);
                    pm_txt.setText(dimmer);
                    shidu_txt.setText(temperature);
                    break;
            }
        }
    }

    /**
     * 动态注册广播
     */
    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_INTENT_RECEIVER_TO_SECOND_PAGE);
        filter.addAction(ACTION_INTENT_RECEIVER_TABLE_PM);
        registerReceiver(mMessageReceiver, filter);
    }

    @Override
    protected void onEvent() {
        back.setOnClickListener(this);
    }

    @Override
    protected void onData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Pm25SecondActivity.this.finish();
                break;
        }
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (intent.getAction().equals(ACTION_INTENT_RECEIVER_TO_SECOND_PAGE)) {
                Log.e("zhu", "LamplightActivity:" + "LamplightActivity");
                //控制部分的二级页面进去要同步更新推送的信息显示 （推送的是消息）。
                upload();
            } else if (intent.getAction().equals(ACTION_INTENT_RECEIVER_TABLE_PM)) {
                JSONObject extraJson;
                String extras = intent.getStringExtra("extras");
                if (!ExampleUtil.isEmpty(extras)) {
                    try {
                        extraJson = new JSONObject(extras);
                        String id = extraJson.getString("id");
                        String pm2_5 = extraJson.getString("pm2.5");
                        String pm1_0 = extraJson.getString("pm1.0");
                        String pm10 = extraJson.getString("pm10");
                        if (number != null) {
                            if (number.equals(id)) {
                                temp_txt.setText(pm1_0);
                                pm_txt.setText(pm2_5);
                                shidu_txt.setText(pm10);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //下载设备信息并且比较状态（为了显示开关状态）
        private void upload() {
            Map<String, String> mapdevice = new HashMap<>();
            mapdevice.put("areaNumber", areaNumber);
            mapdevice.put("roomNumber", number);
            mapdevice.put("token", TokenUtil.getToken(Pm25SecondActivity.this));
            dialogUtil.loadDialog();
            SharedPreferencesUtil.saveData(Pm25SecondActivity.this, "boxnumber", boxnumber);
            MyOkHttp.postMapString(ApiHelper.sraum_getOneRoomInfo
                    , mapdevice, new Mycallback(new AddTogglenInterfacer() {
                        @Override
                        public void addTogglenInterfacer() {//获取togglen成功后重新刷新数据
                            upload();
                        }
                    }, Pm25SecondActivity.this, dialogUtil) {
                        @Override
                        public void onSuccess(User user) {
                            super.onSuccess(user);
                            //拿到设备状态值
                            jpush_get(user);
                        }

                        @Override
                        public void wrongToken() {
                            super.wrongToken();
                        }
                    });
        }


        /**
         * 收到极光推送后刷新
         *
         * @param user
         */
        private void jpush_get(User user) {
            for (User.device d : user.deviceList) {
                if (d.number.equals(number)) {
                    //
                    switch (d.type == null ? "" : d.type) {
                        case "10"://入墙Pm2.5
                            temp_txt.setText(d.temperature);
                            pm_txt.setText(d.dimmer);
                            shidu_txt.setText(d.speed);
                            break;
                        case "102"://桌面pm2.5
                            temp_txt.setText(d.mode);
                            pm_txt.setText(d.dimmer);
                            shidu_txt.setText(d.temperature);
                            break;
                    }
                    break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMessageReceiver);
    }


}
