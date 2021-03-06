package com.massky.sraum.activity;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.massky.sraum.R;
import com.massky.sraum.base.BaseActivity;
import com.massky.sraum.widget.ApplicationContext;
import com.yanzhenjie.statusview.StatusUtils;
import com.yanzhenjie.statusview.StatusView;

import butterknife.BindView;

/**
 * Created by zhu on 2018/1/8.
 */

public class SettingWangGuanActivity extends BaseActivity{
    @BindView(R.id.next_step_txt)
    TextView next_step_txt;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.status_view)
    StatusView statusView;

    @Override
    protected int viewId() {
        return R.layout.setting_wangguan_btn;
    }

    @Override
    protected void onView() {
//        if (!StatusUtils.setStatusBarDarkFont(this, true)) {// Dark font for StatusBar.
//            statusView.setBackgroundColor(Color.BLACK);
//        }
        StatusUtils.setFullToStatusBar(this);  // StatusBar.
    }

    @Override
    protected void onEvent() {
        back.setOnClickListener(this);
    }

    @Override
    protected void onData() {
        next_step_txt.setOnClickListener(this);
        Intent intent = getIntent();
//        if (intent == null) return;
//        String excute = (String) intent.getSerializableExtra("excute");
//        switch (excute) {
//            case "auto"://自动
//                rel_scene_set.setVisibility(View.GONE);
//                break;
//            default:
//                rel_scene_set.setVisibility(View.VISIBLE);
//                break;
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_step_txt:
//                GuanLianSceneBtnActivity.this.finish();
                SettingWangGuanActivity.this.finish();
                break;
            case R.id.back:
                SettingWangGuanActivity.this.finish();
                break;
        }
    }
}
