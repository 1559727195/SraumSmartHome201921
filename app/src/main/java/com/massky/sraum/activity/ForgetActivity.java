package com.massky.sraum.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.massky.sraum.R;
import com.massky.sraum.Util.IntentUtil;
import com.massky.sraum.base.BaseActivity;
import com.massky.sraum.view.ClearEditText;
import com.yanzhenjie.statusview.StatusUtils;
import com.yanzhenjie.statusview.StatusView;

import butterknife.BindView;

/**
 * Created by zhu on 2017/12/29.
 */

public class ForgetActivity extends BaseActivity {
    @BindView(R.id.status_view)
    StatusView statusView;
    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.edit_password_gateway)
    ClearEditText edit_password_gateway;
    @BindView(R.id.btn_login_gateway)
    Button btn_login_gateway;

    @Override
    protected int viewId() {
        return R.layout.forget_act;
    }

    @Override
    protected void onView() {
//        if (!StatusUtils.setStatusBarDarkFont(this, true)) {// Dark font for StatusBar.
//            statusView.setBackgroundColor(Color.BLACK);
//        }
        StatusUtils.setFullToStatusBar(this);  // StatusBar.
        btn_login_gateway.setOnClickListener(this);

        edit_password_gateway.setText(IntentUtil.getIntentString(ForgetActivity.this, "registerphone"));
        edit_password_gateway.setSelection(edit_password_gateway.getText().length());
    }

    @Override
    protected void onEvent() {
        back.setOnClickListener(this);
    }

    @Override
    protected void onData() {

    }

//    private void init_permissions() {
//
//        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
//        RxPermissions permissions = new RxPermissions(this);
//        permissions.request(Manifest.permission.CAMERA).subscribe(new Observer<Boolean>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(Boolean aBoolean) {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                ForgetActivity.this.finish();
                break;
            case R.id.btn_login_gateway:
//                startActivity(new Intent(ForgetActivity.this,InputCheckCodeActivity.class));
//                ForgetActivity.this.finish();
                String registerphone = edit_password_gateway.getText().toString();
                Intent intent = new Intent(ForgetActivity.this, InputCheckCodeActivity.class);
                intent.putExtra("registerphone",registerphone);
                intent.putExtra("from","forget");
                startActivity(intent);
              //  IntentUtil.startActivity(ForgetActivity.this, InputCheckCodeActivity.class, "registerphone", registerphone);
                break;//登录网关
        }
    }
}
