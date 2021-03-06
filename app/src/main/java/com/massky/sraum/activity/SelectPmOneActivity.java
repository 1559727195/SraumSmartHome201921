package com.massky.sraum.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.massky.sraum.R;
import com.massky.sraum.base.BaseActivity;
import com.yanzhenjie.statusview.StatusUtils;
import com.yanzhenjie.statusview.StatusView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by zhu on 2018/6/19.
 */

public class SelectPmOneActivity extends BaseActivity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.next_step_txt)
    TextView next_step_txt;
    @BindView(R.id.status_view)
    StatusView statusView;
    @BindView(R.id.wendu_linear)
    LinearLayout wendu_linear;
    @BindView(R.id.shidu_rel)
    RelativeLayout shidu_rel;
    @BindView(R.id.pm25_rel)
    RelativeLayout pm25_rel;

    @BindView(R.id.project_select)
    TextView project_select;

    @BindView(R.id.panel_scene_name_txt)
    TextView panel_scene_name_txt;
    @BindView(R.id.door_open_txt)
    TextView door_open_txt;
    @BindView(R.id.door_close_txt)
    TextView door_close_txt;

    private String condition = "0";
    private Map map_link = new HashMap();
    private String deviceType;

    @Override
    protected int viewId() {
        return R.layout.select_pm_one_lay;
    }

    @Override
    protected void onView() {
        StatusUtils.setFullToStatusBar(this);  // StatusBar.
        onEvent();
        map_link = (Map) getIntent().getSerializableExtra("map_link");
        if (map_link == null) return;
        deviceType = map_link.get("deviceType").toString();
        switch (deviceType) {
            case "10":
                panel_scene_name_txt.setText("温度");
                door_open_txt.setText("湿度");
                door_close_txt.setText("PM2.5");

                break;
            case "AD02":
                panel_scene_name_txt.setText("PM1.0");
                door_open_txt.setText("PM2.5");
                door_close_txt.setText("PM10");
                break;
        }
        setPicture();
    }

    @Override
    protected void onEvent() {
        back.setOnClickListener(this);
        next_step_txt.setOnClickListener(this);
        wendu_linear.setOnClickListener(this);
        shidu_rel.setOnClickListener(this);
        pm25_rel.setOnClickListener(this);
    }

    @Override
    protected void onData() {

    }

    private void setPicture() {
        project_select.setText(map_link.get("name").toString());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back:
                SelectPmOneActivity.this.finish();
                return;
            case R.id.wendu_linear:
                map_link.put("pm_action", "0");
                break;
            case R.id.shidu_rel:
                map_link.put("pm_action", "1");
                break;
            case R.id.pm25_rel:
                map_link.put("pm_action", "2");
                break;
        }
        init_intent();
    }


    private void init_intent() {
        Intent intent = null;
        intent = new Intent(SelectPmOneActivity.this, SelectPmTwoActivity.class);
        map_link.put("condition", condition);
        map_link.put("minValue", "");
        map_link.put("maxValue", "");
        map_link.put("name1", map_link.get("name"));

        intent.putExtra("map_link", (Serializable) map_link);
        startActivity(intent);
    }
}
