package com.massky.sraum.activity

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.AddTogenInterface.AddTogglenInterfacer
import com.massky.sraum.R
import com.massky.sraum.User
import com.massky.sraum.Util.MyOkHttp
import com.massky.sraum.Util.Mycallback
import com.massky.sraum.Util.TokenUtil
import com.massky.sraum.Utils.ApiHelper
import com.massky.sraum.base.BaseActivity
import com.yanzhenjie.statusview.StatusUtils
import com.yanzhenjie.statusview.StatusView
import java.sql.Types
import java.util.*

/**
 * Created by zhu on 2018/1/16.
 */
class WangGuanBaseInformationActivity : BaseActivity() {
    @JvmField
    @BindView(R.id.back)
    var back: ImageView? = null

    @JvmField
    @BindView(R.id.status_view)
    var statusView: StatusView? = null
    private var gateway_map= HashMap<Any?, Any?>()

    @JvmField
    @BindView(R.id.type)
    var type: TextView? = null

    @JvmField
    @BindView(R.id.mac)
    var mac: TextView? = null

    @JvmField
    @BindView(R.id.version)
    var version: TextView? = null

    @JvmField
    @BindView(R.id.pannel)
    var pannel: TextView? = null

    @JvmField
    @BindView(R.id.pan_id)
    var pan_id: TextView? = null
    private var gatewayNumber //网关编号
            : String? = null
    private var areaNumber: String? = null
    private var panelItem_map = HashMap<Any?, Any?>()
   private var types:String? = null


    override fun viewId(): Int {
        return R.layout.wangguan_baseinfor_act
    }

    override fun onView() {
//        if (!StatusUtils.setStatusBarDarkFont(this, true)) {// Dark font for StatusBar.
//            statusView.setBackgroundColor(Color.BLACK);
//        }
        StatusUtils.setFullToStatusBar(this) // StatusBar.
        areaNumber = intent.getSerializableExtra("areaNumber") as String
        gatewayNumber = intent.getSerializableExtra("number") as String
        panelItem_map = intent.getSerializableExtra("panelItem_map") as HashMap<Any?, Any?>
        types = panelItem_map["type"] as String?
    }

    override fun onEvent() {
        back!!.setOnClickListener(this)
    }

    override fun onData() { //"主卫水浸","防盗门门磁","防盗门猫眼","人体监测","防盗门门锁"
    }

    override fun onResume() {
        when(types) {
            "网关"->{
                sraum_getGatewayInfo()
            }
            else-> {

                //显示
               mac!!.text = panelItem_map["mac"].toString()
               type!!.text = panelItem_map["type"].toString()
               version!!.text = ""
               pannel!!.text = ""
               pan_id!!.text =""
            }
        }
        super.onResume()
    }

    /**
     * 获取网关基本信息（APP->网关）
     */
    private fun sraum_getGatewayInfo() {
        val map= HashMap<Any?, Any?>()
        map["token"] = TokenUtil.getToken(this)
        map["number"] = gatewayNumber
        map["areaNumber"] = areaNumber
        MyOkHttp.postMapObject(ApiHelper.sraum_getGatewayInfo, map as Map<String,Any>,
                object : Mycallback(AddTogglenInterfacer { }, this@WangGuanBaseInformationActivity, null) {
                    override fun onSuccess(user: User) {
                        //"roomNumber":"1"
                        gateway_map = HashMap<Any?, Any?>()
                        gateway_map["type"] = user.type
                        gateway_map["status"] = user.status
                        gateway_map["name"] = user.name
                        gateway_map["mac"] = user.mac
                        gateway_map["versionType"] = user.versionType
                        gateway_map["hardware"] = user.hardware
                        gateway_map["firmware"] = user.firmware
                        gateway_map["bootloader"] = user.bootloader
                        gateway_map["coordinator"] = user.coordinator
                        gateway_map["panid"] = user.panid
                        gateway_map["channel"] = user.channel
                        gateway_map["deviceCount"] = user.deviceCount
                        gateway_map["sceneCount"] = user.sceneCount

                        //显示
                        mac!!.text = user.mac
                        type!!.text = user.type
                        version!!.text = user.hardware
                        pannel!!.text = user.channel
                        pan_id!!.text = user.panid
                    }
                })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.back -> finish() //
        }
    }
}