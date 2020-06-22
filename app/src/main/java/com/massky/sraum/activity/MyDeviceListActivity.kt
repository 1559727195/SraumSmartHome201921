package com.massky.sraum.activity

import android.content.Intent
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import com.AddTogenInterface.AddTogglenInterfacer
import com.massky.sraum.R
import com.massky.sraum.User
import com.massky.sraum.Util.*
import com.massky.sraum.Utils.ApiHelper
import com.massky.sraum.adapter.MyDeviceListAdapter
import com.massky.sraum.base.BaseActivity
import com.massky.sraum.view.XListView
import com.yanzhenjie.statusview.StatusUtils
import okhttp3.Call
import java.util.*

/**
 * Created by zhu on 2018/1/8.
 */
class MyDeviceListActivity : BaseActivity(), XListView.IXListViewListener {
    private var list_hand_scene: MutableList<Map<*, *>>? = null
    private val guanlianSceneAdapter: MyDeviceListAdapter? = null

    @JvmField
    @BindView(R.id.back)
    var back: ImageView? = null

    @JvmField
    @BindView(R.id.xListView_scan)
    var xListView_scan: XListView? = null

    @JvmField
    @BindView(R.id.project_select)
    var project_select: TextView? = null

    @JvmField
    @BindView(R.id.next_step_txt)
    var next_step_txt: TextView? = null
    private val mHandler = Handler()
    private var mydeviceadapter: MyDeviceListAdapter? = null
    private val listint: MutableList<Int> = ArrayList()
    private val listintwo: MutableList<Int> = ArrayList()
    private var accountType: String? = null
    private var dialogUtil: DialogUtil? = null
    private var areaNumber: String? = null
    private var boxnumber: String? = null
    private var authType: String? = null
    override fun viewId(): Int {
        return R.layout.mydevice_list
    }

    override fun onView() {
        StatusUtils.setFullToStatusBar(this) // StatusBar.
        dialogUtil = DialogUtil(this)
        accountType = SharedPreferencesUtil.getData(this@MyDeviceListActivity, "accountType", "") as String
        //        areaNumber = (String) SharedPreferencesUtil.getData(MyDeviceListActivity.this, "areaNumber", "");
        areaNumber = intent.getSerializableExtra("areaNumber") as String
        authType = intent.getSerializableExtra("authType") as String
        boxnumber = SharedPreferencesUtil.getData(this@MyDeviceListActivity, "boxnumber", "") as String
        list_hand_scene = ArrayList()
        mydeviceadapter = MyDeviceListAdapter(this@MyDeviceListActivity, list_hand_scene as ArrayList<Map<*, *>>, listint, listintwo,
                authType!!, accountType!!, areaNumber!!, object : MyDeviceListAdapter.RefreshListener {
            override fun refresh() {
                onResumes() //界面出现之后，调数据刷新
            }
        })
        xListView_scan!!.adapter = mydeviceadapter
        xListView_scan!!.setPullLoadEnable(false)
        xListView_scan!!.setFootViewHide()
        xListView_scan!!.setXListViewListener(this@MyDeviceListActivity)
        if (authType != null) {
            when (authType) {
                "1" -> next_step_txt!!.visibility = View.VISIBLE
                "2" -> next_step_txt!!.visibility = View.GONE
            }
        }
    }

    override fun onEvent() {
        back!!.setOnClickListener(this)
        next_step_txt!!.setOnClickListener(this)
    }

    override fun onData() {}
    override fun onClick(v: View) {
        when (v.id) {
            R.id.back -> finish()
            R.id.next_step_txt -> {
                val intent = Intent(this@MyDeviceListActivity, FastEditPanelActivity::class.java)
                intent.putExtra("areaNumber", areaNumber)
                startActivity(intent)
            }
        }
    }

    private fun onLoad() {
        xListView_scan!!.stopRefresh()
        xListView_scan!!.stopLoadMore()
        xListView_scan!!.setRefreshTime("刚刚")
    }

    override fun onRefresh() {
        onLoad()
        onResumes()
    }

    override fun onLoadMore() {
        mHandler.postDelayed({ onLoad() }, 1000)
    }

    //7-门磁，8-人体感应，9-水浸检测器，10-入墙PM2.5
    //11-紧急按钮，12-久坐报警器，13-烟雾报警器，14-天然气报警器，15-智能门锁，16-直流电阀机械手
    //    R.drawable.magnetic_door_s,
    //    R.drawable.human_induction_s, R.drawable.water_s, R.drawable.pm25_s,
    //    R.drawable.emergency_button_s
    private fun onResumes() {
        getOtherDevices("")
    } //

    /**
     * 获取门磁等第三方设备
     *
     * @param doit
     */
    private fun getOtherDevices(doit: String) {
//        2.areaNumber：区域编号3.boxNumber网关
        dialogUtil!!.loadDialog()
        val mapdevice: MutableMap<String, String?> = HashMap()
        mapdevice["token"] = TokenUtil.getToken(this@MyDeviceListActivity)
        mapdevice["areaNumber"] = areaNumber
        MyOkHttp.postMapString(ApiHelper.sraum_myAllDevice
                , mapdevice, object : Mycallback(AddTogglenInterfacer { //刷新togglen数据
            getOtherDevices("load")
        }, this@MyDeviceListActivity, dialogUtil) {
            override fun onError(call: Call, e: Exception, id: Int) {
                super.onError(call, e, id)
            }

            override fun pullDataError() {
                super.pullDataError()
            }

            override fun emptyResult() {
                super.emptyResult()
            }

            override fun wrongToken() {
                super.wrongToken()
                //重新去获取togglen,这里是因为没有拉到数据所以需要重新获取togglen
            }

            override fun wrongBoxnumber() {
                super.wrongBoxnumber()
            }

            override fun onSuccess(user: User) {
                list_hand_scene = ArrayList()
                listint.clear()
                listintwo.clear()
                for (i in user.gatewayList.indices) {
                    val mapdevice: MutableMap<String, String> = HashMap()
                    mapdevice["number"] = user.gatewayList[i].number
                    mapdevice["name"] = user.gatewayList[i].name
                    mapdevice["status"] = if (user.gatewayList[i].status == null) "" else user.gatewayList[i].status
                    mapdevice["type"] = "网关"
                    mapdevice["boxNumber"] = ""
                    mapdevice["mac"] = ""
                    mapdevice["isUse"] = ""
                    //roomNumber
                    mapdevice["roomNumber"] = ""
                    mapdevice["roomName"] = ""
                    mapdevice["wifi"] = ""
                    mapdevice["boxName"] = ""
                    (list_hand_scene as ArrayList<Map<*, *>>).add(mapdevice)
                    setPicture("网关")
                }
                for (i in user.panelList.indices) {
                    val mapdevice: MutableMap<String, String> = HashMap()
                    mapdevice["number"] = user.panelList[i].number
                    mapdevice["name"] = user.panelList[i].name
                    mapdevice["type"] = user.panelList[i].type
                    mapdevice["boxNumber"] = user.panelList[i].boxNumber
                    mapdevice["mac"] = user.panelList[i].mac
                    mapdevice["isUse"] = ""

                    //roomNumber
                    mapdevice["roomNumber"] = ""
                    mapdevice["roomName"] = ""
                    mapdevice["wifi"] = ""
                    mapdevice["boxName"] = if (user.panelList[i].boxName == null) "" else user.panelList[i].boxName
                    (list_hand_scene as ArrayList<Map<*, *>>).add(mapdevice)
                    setPicture(user.panelList[i].type)
                }
                for (i in user.wifiList.indices) {
                    val mapdevice: MutableMap<String, String> = HashMap()
                    mapdevice["number"] = user.wifiList[i].id
                    mapdevice["name"] = if (user.wifiList[i].name == null) "" else user.wifiList[i].name
                    mapdevice["type"] = user.wifiList[i].type
                    mapdevice["isUse"] = user.wifiList[i].isUse
                    mapdevice["mac"] = ""
                    //roomNumber
                    mapdevice["boxNumber"] = ""
                    mapdevice["roomNumber"] = if (user.wifiList[i].roomNumber == null) "" else user.wifiList[i].roomNumber
                    mapdevice["roomName"] = user.wifiList[i].roomName
                    //wifi
                    mapdevice["wifi"] = user.wifiList[i].wifi
                    mapdevice["boxName"] = ""
                    (list_hand_scene as ArrayList<Map<*, *>>).add(mapdevice)
                    setPicture(user.wifiList[i].type)
                }
                project_select!!.text = "设备列表(" + (list_hand_scene as ArrayList<Map<*, *>>).size + ")"
                //                        mydeviceadapter.setLists(list_hand_scene, listint, listintwo);
//                        mydeviceadapter.notifyDataSetChanged();
                init_main()
                when (doit) {
                    "refresh" -> {
                    }
                    "load" -> {
                    }
                }
            }

            private fun init_main() {
                runOnUiThread {
                    mydeviceadapter = MyDeviceListAdapter(this@MyDeviceListActivity, list_hand_scene!!,
                            listint, listintwo, authType!!, accountType!!, areaNumber!!, object : MyDeviceListAdapter.RefreshListener {
                        override fun refresh() {
                            onResumes() //界面出现之后，调数据刷新
                        }
                    })
                    xListView_scan!!.adapter = mydeviceadapter
                    xListView_scan!!.setPullLoadEnable(false)
                    xListView_scan!!.setFootViewHide()
                    xListView_scan!!.setXListViewListener(this@MyDeviceListActivity)
                }
            }
        })
    }

    private fun setPicture(type: String) {
        when (type) {
            "A201", "A2A1" -> {
                listint.add(R.drawable.icon_yijiandk_40)
                listintwo.add(R.drawable.icon_yijiandk_40)
            }
            "A202", "A2A2" -> {
                listint.add(R.drawable.icon_liangjiandki_40)
                listintwo.add(R.drawable.icon_liangjiandki_40)
            }
            "A203", "A2A3" -> {
                listint.add(R.drawable.icon_sanjiandk_40)
                listintwo.add(R.drawable.icon_sanjiandk_40)
            }
            "A204", "A2A4" -> {
                listint.add(R.drawable.icon_kaiguan_40)
                listintwo.add(R.drawable.icon_kaiguan_40_active)
            }
            "A301", "A302", "A303", "A311", "A312", "A313", "A321", "A322", "A331" -> {
                listint.add(R.drawable.dimminglights)
                listintwo.add(R.drawable.dimminglights)
            }
            "A401", "A411", "A412", "A413", "A414" -> {
                listint.add(R.drawable.home_curtain)
                listintwo.add(R.drawable.home_curtain)
            }
            "A501", "A511" -> {
                listint.add(R.drawable.icon_kongtiao_40)
                listintwo.add(R.drawable.icon_kongtiao_40)
            }
            "A801" -> {
                listint.add(R.drawable.icon_menci_40)
                listintwo.add(R.drawable.icon_menci_40_active)
            }
            "A901" -> {
                listint.add(R.drawable.icon_rentiganying_40)
                listintwo.add(R.drawable.icon_rentiganying_40)
            }
            "AB01" -> {
                listint.add(R.drawable.icon_yanwubjq_40)
                listintwo.add(R.drawable.icon_yanwubjq_40)
            }
            "A902" -> {
                listint.add(R.drawable.icon_rucebjq_40)
                listintwo.add(R.drawable.icon_rucebjq_40)
            }
            "AB04" -> {
                listint.add(R.drawable.icon_ranqibjq_40)
                listintwo.add(R.drawable.icon_ranqibjq_40)
            }
            "AC01" -> {
                listint.add(R.drawable.icon_shuijin_40)
                listintwo.add(R.drawable.icon_shuijin_40)
            }
            "AD01" -> {
                listint.add(R.drawable.icon_pm25_40)
                listintwo.add(R.drawable.icon_pm25_40)
            }
            "AD02" -> {
                listint.add(R.drawable.icon_pmmofang_40_hs)
                listintwo.add(R.drawable.icon_pmmofang_40)
            }
            "B001" -> {
                listint.add(R.drawable.icon_jinjianniu_40)
                listintwo.add(R.drawable.icon_jinjianniu_40)
            }
            "B101" -> {
                listint.add(R.drawable.icon_kaiguan_socket_40)
                listintwo.add(R.drawable.icon_kaiguan_socket_40)
            }
            "网关" -> {
                listint.add(R.drawable.icon_type_wangguan_40)
                listintwo.add(R.drawable.icon_type_wangguan_40)
            }
            "B201" -> {
                listint.add(R.drawable.icon_zhinengmensuo_40)
                listintwo.add(R.drawable.icon_zhinengmensuo_40_active)
            }
            "AA02" -> {
                listint.add(R.drawable.icon_hongwaizfq_40)
                listintwo.add(R.drawable.icon_hongwaizfq_40)
            }
            "AA03" -> {
                listint.add(R.drawable.icon_shexiangtou_40)
                listintwo.add(R.drawable.icon_shexiangtou_40)
            }
            "AA04" -> {
                listint.add(R.drawable.icon_keshimenling_40)
                listintwo.add(R.drawable.icon_keshimenling_40)
            }
            "A611", "A601" -> {
                listint.add(R.drawable.freshair)
                listintwo.add(R.drawable.freshair)
            }
            "A711", "A701" -> {
                listint.add(R.drawable.floorheating)
                listintwo.add(R.drawable.floorheating)
            }
            "B301" -> {
                listint.add(R.drawable.icon_jixieshou_40)
                listintwo.add(R.drawable.icon_jixieshou_40)
            }
            "B401" -> {
                listint.add(R.drawable.icon_zhinengshengjiang_40)
                listintwo.add(R.drawable.icon_zhinengshengjiang_40)
            }
            "B402" -> {
                listint.add(R.drawable.icon_zhinengpingyi_40)
                listintwo.add(R.drawable.icon_zhinengpingyi_40)
            }
            "B403" -> {
                listint.add(R.drawable.icon_zhinenggaozhongdii_40)
                listintwo.add(R.drawable.icon_zhinenggaozhongdii_40)
            }
            else -> {
                listint.add(R.drawable.defaultpic)
                listintwo.add(R.drawable.defaultpic)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        onResumes() //界面出现之后，调数据刷新
    }
}