package com.massky.sraum.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.multidex.MultiDex;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.dialog.CommonData;
import com.dialog.CommonDialogService;
import com.dialog.ToastUtils;
import com.iflytek.cloud.SpeechUtility;
import com.massky.sraum.R;
import com.massky.sraum.Util.LogUtil;
import com.massky.sraum.Util.SharedPreferencesUtil;
import com.michoi.cloudtalksdk.util.SystemUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;
import webapp.config.SystemParams;


/**
 * Created by masskywcy on 2017-01-04.
 */

public class App extends Application implements Application.ActivityLifecycleCallbacks {


    private Context context;
    public String calledAcccout;
    private static App _instance;
    /**
     * 当前Acitity个数
     */
    private int activityAount = 0;

    // 开放平台申请的APP key & secret key
    public static String APP_KEY = "ccd38858cc5a459bbeedcf93a25ae6be";
    public static String API_URL = "https://open.ys7.com";
    public static String WEB_URL = "https://auth.ys7.com";
    private boolean isForeground;
    private boolean isDoflag;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
//        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        // 应用程序入口处调用，避免手机内存过小，杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 如在Application中调用初始化，需要在Mainifest中注册该Applicaiton
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用半角“,”分隔。
        // 设置你申请的应用appid,请勿在'='与appid之间添加空格及空转义符

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误

        SpeechUtility.createUtility(App.this, "appid=" + getString(R.string.app_id));

        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
        // Setting.setShowLog(false);

        JPushInterface.init(this);            // 初始化 JPush
        SystemParams.init(this);
       // CrashHandlerUtil.getInstance().init_crash(_instance);
        //用于判断log值是否打印
        LogUtil.isDebug = true;
        //okhttp网络配置
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //.addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);

        //application生命周期
        this.registerActivityLifecycleCallbacks(this);//注册
        CommonData.applicationContext = this;
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(metric);
        CommonData.ScreenWidth = metric.widthPixels; // 屏幕宽度（像素）
        Intent dialogservice = new Intent(this, CommonDialogService.class);
        startService(dialogservice);
        /**
         * 这是个初始化云对讲
         */
        Log.i("robin debug", "onCreate");
        if (SystemUtil.getCurrentProcessName(this).equals("com.tencent.mm")) { // 针对守护进程的处理
            Log.i("robin debug", "守护进程，不初始化");
            return;
        }

//        calledAcccout = "13714348080";
//        context = getApplicationContext();
//        CloudTalkManager.getInstance().init(this);
//
//        CloudTalkManager.getInstance().setNetworkConnected(true);
//        // Register for broadcasts when network status changed
//        IntentFilter intentFilter = new IntentFilter(); //Intentfilter
//        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        this.context.registerReceiver(networkConnectionStatusBroadcastReceiver, intentFilter);
//        ToastUtil.showToast(_instance,"你好");
    }

    /**
     * @return
     */
    public static App getInstance() {
        return _instance;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity.getParent() != null) {
            CommonData.mNowContext = activity.getParent();
        } else
            CommonData.mNowContext = activity;
    }


    @Override
    public void onActivityStarted(Activity activity) {
        if (activity.getParent() != null) {
            CommonData.mNowContext = activity.getParent();
        } else
            CommonData.mNowContext = activity;

        activityAount++;
        if (activityAount > 0) {
            if (!isDoflag) {
                isForeground = true;
                isDoflag = true;
                Log.e("zhu-", "isForeground:" + isForeground);
                if (CommonData.mNowContext != null) {
                    boolean loginflag = (boolean) SharedPreferencesUtil.getData(CommonData.mNowContext, "loginflag", false);
                    if (loginflag)
//                        ToastUtil.showToast(CommonData.mNowContext,"App-loginflag:" + loginflag);
                        ToastUtils.getInstances().show_fourbackground("账号在其他地方登录，请重新登录。");
                }
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity.getParent() != null) {
            CommonData.mNowContext = activity.getParent();
        } else
            CommonData.mNowContext = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {
//		ToastUtils.getInstances().cancel();// activity死的时候，onActivityPaused(Activity activity)
        //ToastUtils.getInstances().cancel();
    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityAount--;
        if (activityAount == 0) {
            isForeground = false;
            isDoflag = false;
            Log.e("zhu-", "isForeground:" + isForeground);
            //
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

}
