package com.massky.sraum.activity;

import android.view.View;
import android.widget.ImageView;

import com.massky.sraum.R;
import com.massky.sraum.base.BaseActivity;
import com.yanzhenjie.statusview.StatusUtils;
import com.zanelove.aircontrolprogressbar.ColorArcProgressBar;

import butterknife.BindView;

/**
 * Created by zhu on 2018/1/30.
 */

public class SceneCommonDeviceActivity extends BaseActivity{
    @BindView(R.id.bar1)
    ColorArcProgressBar bar1;
    @BindView(R.id.back)
    ImageView back;
    @Override
    protected int viewId() {
        return R.layout.scene_common_dev_act;
    }

    @Override
    protected void onView() {
        StatusUtils.setFullToStatusBar(this);
    }

    @Override
    protected void onEvent() {
        back.setOnClickListener(this);
    }

    @Override
    protected void onData() {
        bar1.setCurrentValues(22);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                SceneCommonDeviceActivity.this.finish();
                break;
        }
    }
}
