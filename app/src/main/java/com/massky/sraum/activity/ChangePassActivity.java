package com.massky.sraum.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.AddTogenInterface.AddTogglenInterfacer;
import com.alibaba.fastjson.JSON;
import com.data.LoginService;
import com.massky.sraum.R;
import com.massky.sraum.User;
import com.massky.sraum.Util.DialogUtil;
import com.massky.sraum.Util.EyeUtil;
import com.massky.sraum.Util.MyOkHttp;
import com.massky.sraum.Util.Mycallback;
import com.massky.sraum.Util.ToastUtil;
import com.massky.sraum.Util.TokenUtil;
import com.massky.sraum.Utils.ApiHelper;
import com.massky.sraum.base.BaseActivity;
import com.massky.sraum.view.ClearEditText;
import com.yanzhenjie.statusview.StatusUtils;
import com.yanzhenjie.statusview.StatusView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by zhu on 2018/1/18.
 */

public class ChangePassActivity extends BaseActivity {
    @InjectView(R.id.status_view)
    StatusView statusView;
    @InjectView(R.id.back)
    ImageView back;
    private DialogUtil dialogUtil;
    @InjectView(R.id.btn_login_gateway)
    Button btn_login_gateway;
    @InjectView(R.id.input_pass_old)
    ClearEditText input_pass_old;
    @InjectView(R.id.input_pass_new)
    ClearEditText input_pass_new;
    @InjectView(R.id.input_pass_again)
    ClearEditText input_pass_again;
    @InjectView(R.id.eyeimageview_id_gateway)
    ImageView eyeimageview_id_gateway;
    @InjectView(R.id.eyeimageview_id_gateway_one)
    ImageView eyeimageview_id_gateway_one;
    @InjectView(R.id.eyeimageview_id_gateway_two)
    ImageView eyeimageview_id_gateway_two;
    private EyeUtil eyeUtil;
    private EyeUtil eyeUtil_1;
    private EyeUtil eyeUtil_2;

    @Override
    protected int viewId() {
        return R.layout.chag_pass_act;
    }

    @Override
    protected void onView() {
//        if (!StatusUtils.setStatusBarDarkFont(this, true)) {// Dark font for StatusBar.
//            statusView.setBackgroundColor(Color.BLACK);
//        }
        dialogUtil = new DialogUtil(this);
        StatusUtils.setFullToStatusBar(this);
        btn_login_gateway.setOnClickListener(this);
    }

    @Override
    protected void onEvent() {
        back.setOnClickListener(this);
        eyeimageview_id_gateway.setOnClickListener(this);
        eyeUtil = new EyeUtil(ChangePassActivity.this, eyeimageview_id_gateway, input_pass_old, true);
        eyeimageview_id_gateway_one.setOnClickListener(this);
        eyeUtil_1 = new EyeUtil(ChangePassActivity.this, eyeimageview_id_gateway_one, input_pass_new, true);
        eyeimageview_id_gateway_two.setOnClickListener(this);
        eyeUtil_2 = new EyeUtil(ChangePassActivity.this, eyeimageview_id_gateway_two, input_pass_again, true);
    }

    @Override
    protected void onData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                ChangePassActivity.this.finish();
                break;
            case R.id.btn_login_gateway:
                init_chang_pass();
                break;
            case R.id.eyeimageview_id_gateway:
                eyeUtil.EyeStatus();
                break;
            case R.id.eyeimageview_id_gateway_one:
                eyeUtil_1.EyeStatus();
                break;
            case R.id.eyeimageview_id_gateway_two:
                eyeUtil_2.EyeStatus();
                break;
        }
    }

    /**
     * 改变密码
     */
    private void init_chang_pass() {
        if (input_pass_old.getText().toString().trim().equals("")) {
            ToastUtil.showToast(ChangePassActivity.this, "旧密码为空");
            return;
        }

        if (input_pass_new.getText().toString().trim().equals("")) {
            ToastUtil.showToast(ChangePassActivity.this, "新密码为空");
            return;
        }

        if (input_pass_again.getText().toString().trim().equals("")) {
            ToastUtil.showToast(ChangePassActivity.this, "确认密码为空");
            return;
        }


        if (!input_pass_new.getText().toString().trim().equals(input_pass_again.getText().toString().trim())) {
            ToastUtil.showToast(ChangePassActivity.this, "两次输入密码不一致");
            return;
        }

        Map map = new HashMap();
        map.put("token", TokenUtil.getToken(ChangePassActivity.this));
        map.put("oldPwd", input_pass_old.getText().toString().trim());
        map.put("newPwd", input_pass_new.getText().toString().trim());
        retrofit_post_json(map);
    }


    /**
     * retrofit发送json数据
     *
     * @param maptwo
     */
    private void retrofit_post_json(Map<String, Object> maptwo) {
        String obj = JSON.toJSONString(maptwo);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiHelper.api).build();
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), obj);
        final LoginService login = retrofit.create(LoginService.class);
        retrofit2.Call<ResponseBody> data = login.sraum_setPwd(body);
        data.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                User user = null;
                try {
                    user = JSON.parseObject(response.body().string(), User.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                switch (user.result) {//
                    case "100":
                        ToastUtil.showToast(ChangePassActivity.this, "修改成功");
                        ChangePassActivity.this.finish();
                        break;
                    case "101":
                        ToastUtil.showToast(ChangePassActivity.this, "token 错误");
                        break;
                    case "102":
                        ToastUtil.showToast(ChangePassActivity.this, "旧密码错误");
                        break;
                    case "103":
                        ToastUtil.showToast(ChangePassActivity.this, "新密码不正确");
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
