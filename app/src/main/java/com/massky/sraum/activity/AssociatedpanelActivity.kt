package com.massky.sraum.activity

import android.os.Bundle
import android.view.View
import android.widget.*
import butterknife.BindView
import com.AddTogenInterface.AddTogglenInterfacer
import com.massky.sraum.R
import com.massky.sraum.User
import com.massky.sraum.User.panellist
import com.massky.sraum.Util.*
import com.massky.sraum.Utils.ApiHelper
import com.massky.sraum.adapter.AsccociatedpanelAdapter
import com.massky.sraum.base.BaseActivity
import com.yanzhenjie.statusview.StatusUtils
import java.lang.StringBuilder
import java.util.*

/**
 * Created by masskywcy on 2017-03-22.
 */
//关联面板界面
class AssociatedpanelActivity : BaseActivity(), AdapterView.OnItemClickListener {
    @JvmField
    @BindView(R.id.backrela_id)
    var backrelaId: RelativeLayout? = null

    @JvmField
    @BindView(R.id.titlecen_id)
    var titlecenId: TextView? = null

    @JvmField
    @BindView(R.id.panelistview)
    var panelistview: ListView? = null

    @JvmField
    @BindView(R.id.paonebtn)
    var paonebtn: Button? = null

    @JvmField
    @BindView(R.id.patwobtn)
    var patwobtn: Button? = null

    @JvmField
    @BindView(R.id.pathreebtn)
    var pathreebtn: Button? = null

    @JvmField
    @BindView(R.id.pafourbtn)
    var pafourbtn: Button? = null

    @JvmField
    @BindView(R.id.pafivebtn)
    var pafivebtn: ImageView? = null

    @JvmField
    @BindView(R.id.pasixbtn)
    var pasixbtn: ImageView? = null

    @JvmField
    @BindView(R.id.pasevenbtn)
    var pasevenbtn: ImageView? = null

    @JvmField
    @BindView(R.id.paeightbtn)
    var paeightbtn: ImageView? = null

    @JvmField
    @BindView(R.id.panelrela)
    var panelrela: RelativeLayout? = null

    @JvmField
    @BindView(R.id.paonerela)
    var paonerela: RelativeLayout? = null

    @JvmField
    @BindView(R.id.patworela)
    var patworela: RelativeLayout? = null

    @JvmField
    @BindView(R.id.pathreerela)
    var pathreerela: RelativeLayout? = null

    @JvmField
    @BindView(R.id.pafourrela)
    var pafourrela: RelativeLayout? = null

    @JvmField
    @BindView(R.id.pafiverela)
    var pafiverela: RelativeLayout? = null

    @JvmField
    @BindView(R.id.pasixrela)
    var pasixrela: RelativeLayout? = null

    @JvmField
    @BindView(R.id.pasevenrela)
    var pasevenrela: RelativeLayout? = null

    @JvmField
    @BindView(R.id.paeightrela)
    var paeightrela: RelativeLayout? = null

    @JvmField
    @BindView(R.id.backsave)
    var backsave: RelativeLayout? = null

    @JvmField
    @BindView(R.id.pafivetext)
    var pafivetext: TextView? = null

    @JvmField
    @BindView(R.id.pasixtext)
    var pasixtext: TextView? = null

    @JvmField
    @BindView(R.id.paseventext)
    var paseventext: TextView? = null

    @JvmField
    @BindView(R.id.paeighttext)
    var paeighttext: TextView? = null

    @JvmField
    @BindView(R.id.panelinearone)
    var panelinearone: LinearLayout? = null

    @JvmField
    @BindView(R.id.panelineartwo)
    var panelineartwo: LinearLayout? = null

    @JvmField
    @BindView(R.id.panelinearthree)
    var panelinearthree: LinearLayout? = null

    @JvmField
    @BindView(R.id.panelinearfour)
    var panelinearfour: LinearLayout? = null

    @JvmField
    @BindView(R.id.ptlitone)
    var ptlitone: RelativeLayout? = null

    @JvmField
    @BindView(R.id.ptlittwo)
    var ptlittwo: RelativeLayout? = null

    @JvmField
    @BindView(R.id.ptlitthree)
    var ptlitthree: RelativeLayout? = null

    @JvmField
    @BindView(R.id.ptlittwoone)
    var ptlittwoone: RelativeLayout? = null

    @JvmField
    @BindView(R.id.ptlittwotwo)
    var ptlittwotwo: RelativeLayout? = null

    @JvmField
    @BindView(R.id.ptlitoneone)
    var ptlitoneone: RelativeLayout? = null

    @JvmField
    @BindView(R.id.paneThreeLuTiaoGuang)
    var paneThreeLuTiaoGuang: LinearLayout? = null

    @JvmField
    @BindView(R.id.paonerela_sanlu)
    var paonerela_sanlu: RelativeLayout? = null

    @JvmField
    @BindView(R.id.patworela_sanlu)
    var patwobtn_sanlu: RelativeLayout? = null

    @JvmField
    @BindView(R.id.pathreerela_sanlu)
    var pathreebtn_sanlu: RelativeLayout? = null

    @JvmField
    @BindView(R.id.pafourrela_sanlu)
    var pafourbtn_sanlu: RelativeLayout? = null

    @JvmField
    @BindView(R.id.back)
    var back: ImageView? = null
    private var dialogUtil: DialogUtil? = null
    private var dialogUtilview: DialogUtil? = null
    private val boxNumber: String? = null
    private var panelNumber: String? = ""
    private var type: String? = null
    private var button5Type: String? = null
    private var button6Type: String? = null
    private var button7Type: String? = null
    private var button8Type: String? = null
    private var sceneName: String? = null
    private var sceType: String? = null
    private var buttonNumber = ""
    private var flagimagefive = ""
    private var flagimagesix = ""
    private var flagimageseven = ""
    private var flagimageight = ""
    private var panelid: String? = null
    private val assopanelname = ""
    private var gatewayid: String? = null
    private var panelType: String? = null
    private var sceneId: String? = null
    private var btnflag = ""
    private var adapter: AsccociatedpanelAdapter? = null
    private val checkList: MutableList<Boolean> = ArrayList()
    private var panelList: MutableList<panellist?> = ArrayList()
    private var flagfive = true
    private var flagsix = true
    private var flagseven = true
    private var flageight = true
    private var bundle: Bundle? = null
    private var dtext_id: TextView? = null
    private var belowtext_id: TextView? = null
    private var qxbutton_id: Button? = null
    private var checkbutton_id: Button? = null
    private var position = 0
    override fun viewId(): Int {
        return R.layout.asspanel
    }

    override fun onView() {
        StatusUtils.setFullToStatusBar(this) // StatusBar.
        bundle = IntentUtil.getIntentBundle(this@AssociatedpanelActivity)
        sceneName = bundle!!.getString("sceneName", "")
        sceType = bundle!!.getString("sceneType", "")
        gatewayid = if (bundle!!.getString("boxNumber", "") == null) "" else bundle!!.getString("boxNumber", "")
        panelType = bundle!!.getString("panelType", "")
        panelNumber = bundle!!.getString("panelNumber", "")
        buttonNumber = bundle!!.getString("buttonNumber", "")
        //sceneId
        sceneId = bundle!!.getString("sceneId", "")

//        bundle1.putString("sceneName", linkName);
//        bundle1.putString("sceneType", "1");
//        bundle1.putString("boxNumber", boxNumber);
//        bundle1.putString("panelType", "");
//        bundle1.putString("panelNumber", "");
//        bundle1.putString("buttonNumber", "");
        LogUtil.eLength("查看数据panelNumber", panelNumber + "数据问题" + buttonNumber)
        //        boxNumber = (String) SharedPreferencesUtil.getData(AssociatedpanelActivity.this, "boxnumber", "");
        dialogUtil = DialogUtil(this@AssociatedpanelActivity)
        backrelaId!!.setOnClickListener(this)
        paonerela!!.setOnClickListener(this)
        patworela!!.setOnClickListener(this)
        pathreerela!!.setOnClickListener(this)
        pafourrela!!.setOnClickListener(this)
        pafiverela!!.setOnClickListener(this)
        pasixrela!!.setOnClickListener(this)
        pasevenrela!!.setOnClickListener(this)
        paeightrela!!.setOnClickListener(this)
        panelistview!!.onItemClickListener = this
        backsave!!.setOnClickListener(this)
        ptlitone!!.setOnClickListener(this)
        ptlittwo!!.setOnClickListener(this)
        ptlitthree!!.setOnClickListener(this)
        ptlittwoone!!.setOnClickListener(this)
        ptlittwotwo!!.setOnClickListener(this)
        ptlitoneone!!.setOnClickListener(this)

        //三路调光
        paonerela_sanlu!!.setOnClickListener(this)
        patwobtn_sanlu!!.setOnClickListener(this)
        pathreebtn_sanlu!!.setOnClickListener(this)
        pafourbtn_sanlu!!.setOnClickListener(this)
        back!!.setOnClickListener(this)
        titlecenId!!.text = "关联面板"
        getData(1, "refresh")
        replacePanel()
    }

    override fun onEvent() {}
    override fun onData() {}
    private fun replacePanel() {
        val view = layoutInflater.inflate(R.layout.check, null)
        dtext_id = view.findViewById(R.id.dtext_id)
        belowtext_id = view.findViewById(R.id.belowtext_id)
        qxbutton_id = view.findViewById(R.id.qxbutton_id)
        checkbutton_id = view.findViewById(R.id.checkbutton_id)
        dtext_id!!.setText("替换面板")
        qxbutton_id!!.setOnClickListener(this)
        checkbutton_id!!.setOnClickListener(this)
        dialogUtilview = DialogUtil(this@AssociatedpanelActivity, view)
    }

    //设置选中的位置，将其他位置设置为未选
    fun checkPosition(position: Int) {
        for (i in checkList.indices) {
            if (position == i) { // 设置已选位置
                checkList[i] = true
            } else {
                checkList[i] = false
            }
        }

        adapter!!.notifyDataSetChanged()
    }


    private fun getData(index: Int, action: String?) {
        dialogUtil!!.loadDialog()
        var api = ""
        val map: MutableMap<String, Any?> = HashMap()
        val areaNumber = SharedPreferencesUtil.getData(this@AssociatedpanelActivity,
                "areaNumber", "") as String
        map["token"] = TokenUtil.getToken(this@AssociatedpanelActivity)
        map["areaNumber"] = areaNumber
        when (gatewayid) {
            "" -> {
                // api = ApiHelper.sraum_getWifiDeviceCommon
                api = ApiHelper.sraum_getRelateWifiDevice
            }
            else -> {
                map["boxNumber"] = gatewayid
                api = ApiHelper.sraum_getAllPanel
            }
        }

        MyOkHttp.postMapObject(api,
                map, object : Mycallback(AddTogglenInterfacer { getData(index, action) }, this@AssociatedpanelActivity, dialogUtil) {
            override fun onSuccess(user: User) {
                super.onSuccess(user)
                panelList = user.panelList
                checkList.clear()
                val panelListnew: MutableList<panellist?> = ArrayList()
                for (i in panelList.indices) {
                    val upanel = panelList[i]
                    if (upanel!!.type.trim { it <= ' ' } == "A401" || upanel.type.trim { it <= ' ' } == "A501" || upanel.type.trim { it <= ' ' } == "A601" || upanel.type.trim { it <= ' ' } == "A701" || upanel.type.trim { it <= ' ' } == "A611" || upanel.type.trim { it <= ' ' } == "A711" || upanel.type.trim { it <= ' ' } == "A511") {
                        panelListnew.add(upanel)
                    }
                }

                panelList.removeAll(panelListnew)
                var flag = false
                if (panelList.size != 0) {
                    val upone = panelList[0]
                    for (i in panelList.indices) {
                        val up = panelList[i]
                        checkList.add(false)
                        when (gatewayid) {
                            "" -> {
                                flag = ondata_wifi(up!!, flag, i, upone, index, action)
                            }
                            else -> {
                                flag = ondata_zigbee(up!!, flag, i, upone, index)
                            }
                        }
                    }
                }
                if (flag) { //该场景关联了面板，实现如果该场景未关联该面板的按钮，则面板框不显示，面板不被选中（待实现）
                    panelrela!!.visibility = View.VISIBLE //主布局显示
                } else {
                    panelrela!!.visibility = View.GONE //主布局显示
                }

                adapter = AsccociatedpanelAdapter(this@AssociatedpanelActivity, panelList, checkList)
                panelistview!!.adapter = adapter

                when (gatewayid) {
                    "" -> {
                        when (action) {
                            "onclick" -> {
                                common_adapter(index)
                                onitemclick(position)
                            }
                            "refresh" -> {
                                for (i in panelList.indices) {
//                                    buttonStatus!!.clear()
                                    if (panelList[i]!!.buttonStatus.contains("1")) {
                                      //  panelList.set(0,panelList[i])
                                        Collections.swap(panelList, i, 0)
                                        checkList[0] = true
                                        adapter!!.notifyDataSetChanged()
                                        break
                                    }
                                }
                                common_adapter(index)
                            }
                        }
                    }
                    else -> {
                        common_adapter(index)
                    }
                }
            }

            override fun wrongToken() {
                super.wrongToken()
            }
        })
    }

    private fun common_adapter(index: Int) {

        when (index) {
            1 -> {
                var i = 0
                while (i < checkList.size) {
                    if (checkList[i]) {
                        onitemclick(0)
                        break
                    }
                    i++
                }
            }
        }
    }


    private fun ondata_wifi(up: panellist, flag: Boolean, i: Int, upone: panellist?, index: Int
                            , action: String?): Boolean {
        var flag1 = flag
        if (name_common.equals(up!!.name)) { //说明该面板已经关联了该场景，置顶该面板
            position = i
            flag1 = true
            panelid = up.id
            panelList[0] = up
            panelList[i] = upone //替换位置
            LogUtil.eLength("改变图片", "数据问题")
            //                                panelrela.setVisibility(View.VISIBLE);//主布局显示
            setPicture(up.type, up.button5Type, pafivebtn, 5) //pafivebtn为下面第五个relativelayout里面的图片
            setPicture(up.type, up.button6Type, pasixbtn, 6)
            setPicture(up.type, up.button7Type, pasevenbtn, 7)
            setPicture(up.type, up.button8Type, paeightbtn, 8)
            setLinear(up.type)

            pafivetext!!.text = LengthUtil.doit_spit_str(if (up.button5Name == null) "" else up.button5Name)
            pasixtext!!.text = LengthUtil.doit_spit_str(if (up.button6Name == null) "" else up.button6Name)
            paseventext!!.text = LengthUtil.doit_spit_str(if (up.button7Name == null) "" else up.button7Name)
            paeighttext!!.text = LengthUtil.doit_spit_str(if (up.button8Name == null) "" else up.button8Name)


            setFlag(up.buttonStatus, up.button5Type, up.button6Type, up.button7Type, up.button8Type)

            when (index) {
                1 -> checkList[0] = true
            }
        } else {
            //                                checkList.add(false);
            when (index) {
                1 -> checkList[i] = false
            }
        }

        when (action) {
            "refresh" -> {
                return flag1
            }

            "onclick" -> {
                return flag
            }
        }

        return flag1
    }


    private fun ondata_zigbee(up: panellist, flag: Boolean, i: Int, upone: panellist?, index: Int): Boolean {
        var flag1 = flag
        if (panelNumber == up!!.id) { //说明该面板已经关联了该场景，置顶该面板
            flag1 = true
            panelid = up.id
            panelList[0] = up
            panelList[i] = upone //替换位置
            LogUtil.eLength("改变图片", "数据问题")
            //                                panelrela.setVisibility(View.VISIBLE);//主布局显示
            setPicture(up.type, up.button5Type, pafivebtn, 5) //pafivebtn为下面第五个relativelayout里面的图片
            setPicture(up.type, up.button6Type, pasixbtn, 6)
            setPicture(up.type, up.button7Type, pasevenbtn, 7)
            setPicture(up.type, up.button8Type, paeightbtn, 8)
            setLinear(up.type)

            pafivetext!!.text = LengthUtil.doit_spit_str(if (up.button5Name == null) "" else up.button5Name)
            pasixtext!!.text = LengthUtil.doit_spit_str(if (up.button6Name == null) "" else up.button6Name)
            paseventext!!.text = LengthUtil.doit_spit_str(if (up.button7Name == null) "" else up.button7Name)
            paeighttext!!.text = LengthUtil.doit_spit_str(if (up.button8Name == null) "" else up.button8Name)


            setFlag(up.buttonStatus, up.button5Type, up.button6Type, up.button7Type, up.button8Type)

            when (index) {
                1 -> checkList[0] = true
            }
        } else {
            //                                checkList.add(false);
            when (index) {
                1 -> checkList[i] = false
            }
        }
        return flag1
    }

    /**
     * 显示那种布局
     *
     * @param linearType
     */
    private fun setLinear(linearType: String) {
        clear()
        when (linearType) {
            "A201", "A2A1" -> {
                panelinearone!!.visibility = View.VISIBLE
                LogUtil.eLength("这是进入A201", "看看操作")
            }
            "A202", "A411", "A311", "A2A2" -> {
                panelineartwo!!.visibility = View.VISIBLE
                LogUtil.eLength("这是进入A202", "进入了")
            }
            "A203", "A412", "A312", "A321", "A2A3" -> {
                panelinearthree!!.visibility = View.VISIBLE
                LogUtil.eLength("这是进入A203", "看看操作")
            }
            "A204", "A313", "A322", "A331", "A413", "A414", "A2A4" -> panelinearfour!!.visibility = View.VISIBLE
            "A303" -> {
                flagfive = false
                flagsix = false
                flagseven = false
                flageight = true
                paneThreeLuTiaoGuang!!.visibility = View.VISIBLE
            }
            else -> panelinearfour!!.visibility = View.VISIBLE
        }
    }

    private fun setFlag(buttonStatus: String?, fivetype: String?, sixtype: String?,
                        sevemtype: String?, eighttype: String?) {

        when (gatewayid) {
            "" -> {
                wifi_getflag(buttonStatus)
            }
            else -> {
                zigbee_getflag(fivetype, sixtype, sevemtype, eighttype)
            }

        }


        //
    }

    private fun wifi_getflag(buttonStatus: String?) {
        var five = buttonStatus!!.get(0)
    }

    private fun zigbee_getflag(fivetype: String?, sixtype: String?, sevemtype: String?, eighttype: String?) {
        flagimagefive = if (fivetype == null) {
            "1"
        } else {
            "3"
        }
        flagimagesix = if (sixtype == null) {
            "1"
        } else {
            "3"
        }
        flagimageseven = if (sevemtype == null) {
            "1"
        } else {
            "3"
        }
        flagimageight = if (eighttype == null) {
            "1"
        } else {
            "3"
        }
        val scenename = LengthUtil.doit_spit_str(if (sceneName == null) "" else sceneName)
        when (buttonNumber) {
            "5" -> {
                pafivetext!!.text = scenename
                flagimagefive = "2"
            }
            "6" -> {
                pasixtext!!.text = scenename
                flagimagesix = "2"
            }
            "7" -> {
                paseventext!!.text = scenename
                flagimageseven = "2"
            }
            "8" -> {
                paeighttext!!.text = scenename
                flagimageight = "2"
            }
        }
    }

    override fun onClick(v: View) {
        val scenename = LengthUtil.doit_spit_str(if (sceneName == null) "" else sceneName)
        when (v.id) {
            R.id.ptlitone -> ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
            R.id.ptlittwo -> ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
            R.id.ptlitthree -> ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
            R.id.ptlittwoone -> ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
            R.id.ptlittwotwo -> ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
            R.id.ptlitoneone -> ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
            R.id.paonerela_sanlu -> ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
            R.id.patworela_sanlu -> ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
            R.id.pathreerela_sanlu -> ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
            R.id.pafourrela_sanlu -> ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
            R.id.qxbutton_id -> dialogUtilview!!.removeviewDialog()
            R.id.checkbutton_id -> {
                dialogUtilview!!.removeviewDialog()
                when (btnflag) {//切换
                    "5" -> {
                        buttonNumber = "5"
                        panelRelation("0", panelNumber)
                    }
                    "6" -> {
                        buttonNumber = "6"
                        panelRelation("0", panelNumber)
                    }
                    "7" -> {
                        buttonNumber = "7"
                        panelRelation("0", panelNumber)
                    }
                    "8" -> {
                        buttonNumber = "8"
                        panelRelation("0", panelNumber)
                    }
                }
            }
            R.id.backrela_id, R.id.back -> finish()
            R.id.paonerela -> ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
            R.id.patworela -> ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
            R.id.pathreerela -> ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
            R.id.pafourrela -> ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
            R.id.pafiverela -> {

                when (gatewayid) {
                    "" -> {
                        common_wifi_five(scenename)
                    }
                    else -> {
                        five_click_zigbee(scenename)
                    }
                }
            }
            R.id.pasixrela -> {

                when (gatewayid) {
                    "" -> {
                        common_wifi_six(scenename)
                    }
                    else -> {
                        sixclick_zigbee(scenename)
                    }
                }
            }
            R.id.pasevenrela -> {

                when (gatewayid) {
                    "" -> {
                        comon_wifi_seven(scenename)
                    }
                    else -> {
                        sevenclick_zigbee(scenename)
                    }
                }

            }
            R.id.paeightrela -> {

                when (gatewayid) {
                    "" -> {
                        common_wifi_eight(scenename)
                    }
                    else -> {
                        eightclick_zigbee(scenename)
                    }
                }
            }
        }
    }

    private fun common_wifi_eight(scenename: String?) {
        //var eightStatus = panelList[position]!!.buttonStatus.substring(3, 4)
        if (buttonStatus == null) return
        var eightStatus = buttonStatus!!.substring(3, 4)

        buttonNumber = "8"
        panelNumber = panelid
        if (StringUtils.replaceBlank(paeighttext!!.text.toString()) != scenename) {
            paeighttext!!.text = scenename
            panelRelation("1", panelNumber)
            return
        }
        paeighttext!!.text = scenename
        when (eightStatus) {
            "0" -> {
                //go to rela
                panelRelation("1", panelNumber)
            }
            else -> {
                panelRelation("2", panelNumber)
            }
        }
    }

    private fun comon_wifi_seven(scenename: String?) {
        //  var sevenStatus = panelList[position]!!.buttonStatus.substring(2, 3)
        if (buttonStatus == null) return
        var sevenStatus = buttonStatus!!.substring(2, 3)

        buttonNumber = "7"
        panelNumber = panelid

        if (StringUtils.replaceBlank(paseventext!!.text.toString()) != scenename) {
            paseventext!!.text = scenename
            panelRelation("1", panelNumber)
            return
        }

        paseventext!!.text = scenename
        when (sevenStatus) {
            "0" -> {
                //go to rela
                panelRelation("1", panelNumber)
            }
            else -> {
                panelRelation("2", panelNumber)
            }
        }
    }

    var buttonStatus: StringBuilder? = StringBuilder()

    private fun common_wifi_five(scenename: String?) {
        // var fiveStatus = panelList[position]!!.buttonStatus.substring(0, 1)
        if (buttonStatus == null) return
        var fiveStatus = buttonStatus!!.substring(0, 1)

        buttonNumber = "5"
        panelNumber = panelid

        if (StringUtils.replaceBlank(pafivetext!!.text.toString()) != scenename) {
            pafivetext!!.text = scenename
            panelRelation("1", panelNumber)
            return
        }
        pafivetext!!.text = scenename

        when (fiveStatus) {
            "0" -> {
                //go to rela
                panelRelation("1", panelNumber)
            }
            else -> {
                panelRelation("2", panelNumber)
            }
        }
    }

    private fun common_wifi_six(scenename: String?) {
        if (buttonStatus == null) return

        var sixStatus = buttonStatus!!.substring(1, 2)
        buttonNumber = "6"
        panelNumber = panelid

        if (StringUtils.replaceBlank(pasixtext!!.text.toString()) != scenename) {
            pasixtext!!.text = scenename
            panelRelation("1", panelNumber)
            return
        }

        pasixtext!!.text = scenename

        when (sixStatus) {
            "0" -> {
                //go to rela
                panelRelation("1", panelNumber)
            }
            else -> {
                panelRelation("2", panelNumber)
            }
        }
    }

    private fun eightclick_zigbee(scenename: String?) {
        LogUtil.eLength("查看数据", sceType + flageight + "第二次数据" + panelNumber)
        if (flageight) {
            when (flagimageight) {
                "1" -> {
                    LogUtil.eLength("确定关联数据", "传入")
                    flagimageight = "2"
                    buttonNumber = "8"
                    panelNumber = panelid
                    paeighttext!!.text = scenename
                    panelRelation("0", panelNumber)
                }
                "2" -> {
                    LogUtil.eLength("相等传输数据", "传入")
                    flagimageight = "1"
                    buttonNumber = "0"
                    panelRelation("0", "0")
                }
                "3" -> if (StringUtils.replaceBlank(paeighttext!!.text.toString()) == sceneName) {
                    LogUtil.eLength("相等传输数据", "传入")
                    flagimageight = "1"
                    buttonNumber = "0"
                    panelRelation("0", "0")
                } else {
                    belowtext_id!!.text = "确定从 " +
                            StringUtils.replaceBlank(paeighttext!!.text.toString()) + " 替换成 " + sceneName + " 吗？"
                    btnflag = "8"
                    dialogUtilview!!.loadViewdialog()
                }
                else -> {

                }
            }
        } else {
            ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
        }
    }

    private fun sevenclick_zigbee(scenename: String?) {
        LogUtil.eLength("查看数据", sceType + flagseven + "数据" + flagimageseven)
        if (flagseven) {
            when (flagimageseven) {
                "1" -> {
                    LogUtil.eLength("直接选中空白", "第七数据判断")
                    flagimageseven = "2"
                    buttonNumber = "7"
                    panelNumber = panelid
                    paseventext!!.text = scenename
                    panelRelation("0", panelNumber)
                }
                "2" -> {
                    LogUtil.eLength("直接取消状态", "取消行为")
                    flagimageseven = "1"
                    buttonNumber = "0"
                    panelRelation("0", "0")
                }
                "3" -> if (StringUtils.replaceBlank(paseventext!!.text.toString()) == sceneName) {
                    LogUtil.eLength("直接取消状态", "取消行为")
                    flagimageseven = "1"
                    buttonNumber = "0"
                    panelRelation("0", "0")
                } else {
                    belowtext_id!!.text = "确定从 " +
                            StringUtils.replaceBlank(paseventext!!.text.toString()) + " 替换成 " + sceneName + " 吗？"
                    btnflag = "7"
                    dialogUtilview!!.loadViewdialog()
                }

                else -> {

                }
            }
        } else {
            ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
        }
    }

    private fun sixclick_zigbee(scenename: String?) {
        if (flagsix) { //1代表为空值或者非空值不相等2非空值代表场景一致
            LogUtil.eLength("数据查看", panelNumber)
            when (flagimagesix) {
                "1" -> {
                    LogUtil.eLength("进入行为", "行为操作")
                    flagimagesix = "2"
                    buttonNumber = "6"
                    panelNumber = panelid
                    pasixtext!!.text = scenename
                    panelRelation("0", panelNumber)
                }
                "2" -> {
                    LogUtil.eLength("取消行为", "取消数据")
                    flagimagesix = "1"
                    buttonNumber = "0"
                    panelRelation("0", "0")
                }
                "3" -> if (StringUtils.replaceBlank(pasixtext!!.text.toString()) == sceneName) {
                    LogUtil.eLength("取消行为", "取消数据")
                    flagimagesix = "1"
                    buttonNumber = "0"
                    panelRelation("0", "0")
                } else {
                    belowtext_id!!.text = "确定从 " +
                            StringUtils.replaceBlank(pasixtext!!.text.toString()) + " 替换成 " + sceneName + " 吗？"
                    btnflag = "6"
                    dialogUtilview!!.loadViewdialog()
                }
                else -> {
                }
            }
        } else {
            ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
        }
    }

    private fun five_click_zigbee(scenename: String?) {
        if (flagfive) { //第5个按钮可以设置场景
            //1代表为空值2非空值代表场景一致3非空值不相等
            when (flagimagefive) {
                "1" -> {
                    flagimagefive = "2"
                    buttonNumber = "5"
                    panelNumber = panelid
                    pafivetext!!.text = scenename
                    panelRelation("0", panelNumber)
                }
                "2" -> {
                    flagimagefive = "1"
                    buttonNumber = "0"
                    panelRelation("0", "0")
                }
                "3" -> if (StringUtils.replaceBlank(pafivetext!!.text.toString()) == sceneName) {
                    flagimagefive = "1"
                    buttonNumber = "0"
                    panelRelation("0", "0")
                } else {
                    belowtext_id!!.text = "确定从 " +
                            StringUtils.replaceBlank(pafivetext!!.text.toString()) + " 替换成 " + sceneName + " 吗？"
                    btnflag = "5"
                    dialogUtilview!!.loadViewdialog()
                }
                else -> {
                }
            }
        } else {
            ToastUtil.showToast(this@AssociatedpanelActivity, "不可以设置场景")
        }
    }

    private fun panelRelation(action: String?, panelrenumb: String?) {
        dialogUtil!!.loadDialog()
        sraum_panelRelation_(action, panelrenumb)
    }

    private fun sraum_panelRelation_(action: String?, panelrenumb: String?) {
        val map: MutableMap<String, Any?> = HashMap()
        var api = ""
        map["token"] = TokenUtil.getToken(this@AssociatedpanelActivity)
        val areaNumber = SharedPreferencesUtil.getData(this@AssociatedpanelActivity,
                "areaNumber", "") as String
        map["areaNumber"] = areaNumber
        map["panelNumber"] = panelrenumb
        map["buttonNumber"] = buttonNumber //关联哪个面板上的哪个按钮

        when (gatewayid) {
            "" -> {
                api = ApiHelper.sraum_relateWifiDevice
                map["action"] = action
                map["sceneId"] = sceneId
            }
            else -> {
                map["boxNumber"] = gatewayid
                map["sceneName"] = sceneName
                api = ApiHelper.sraum_panelRelation
            }
        }

        MyOkHttp.postMapObject(api, map,
                object : Mycallback(AddTogglenInterfacer { sraum_panelRelation_(action, panelrenumb) }, this@AssociatedpanelActivity, dialogUtil) {
                    override fun onSuccess(user: User) {
                        super.onSuccess(user)
                        when (gatewayid) {
                            "" -> {
                                when (buttonNumber) {
                                    "5" -> {

                                        when (action) {
                                            "1" -> {
                                                buttonStatus!!.replace(0, 1, "1")
                                            }
                                            "2" -> {
                                                pafivetext!!.text = ""
                                                buttonStatus!!.replace(0, 1, "0")
                                            }
                                        }
                                    }

                                    "6" -> {
                                        when (action) {
                                            "1" -> {
                                                buttonStatus!!.replace(1, 2, "1")
                                            }
                                            "2" -> {
                                                pasixtext!!.text = ""
                                                buttonStatus!!.replace(1, 2, "0")
                                            }
                                        }
                                    }

                                    "7" -> {
                                        when (action) {
                                            "1" -> {
                                                buttonStatus!!.replace(2, 3, "1")
                                            }
                                            "2" -> {
                                                buttonStatus!!.replace(2, 3, "0")
                                                paseventext!!.text = ""
                                            }
                                        }
                                    }

                                    "8" -> {
                                        when (action) {
                                            "1" -> {
                                                buttonStatus!!.replace(3, 4, "1")
                                            }
                                            "2" -> {
                                                buttonStatus!!.replace(3, 4, "0")
                                                paeighttext!!.text = ""
                                            }
                                        }
                                    }
                                }


                                //position

                                for (i in panelList.indices) {
                                    if (position == i) {
                                        panelList[i]!!.buttonStatus = buttonStatus.toString()
                                        panelList[i]!!.button5Name = pafivetext!!.text.toString()
                                        panelList[i]!!.button6Name = pasixtext!!.text.toString()
                                        panelList[i]!!.button7Name = paseventext!!.text.toString()
                                        panelList[i]!!.button8Name = paeighttext!!.text.toString()
                                        break
                                    }
                                }
                            }
                            else -> {
                                getData(1, "onclick")
                            }
                        }
                    }

                    override fun wrongToken() {
                        super.wrongToken()
                    }
                })
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
        this.position = position
        onitemclick(position)
    }


    var name_common: String? = null
    private fun onitemclick(position: Int) {
        buttonStatus!!.clear()
        buttonStatus!!.append(panelList[position]!!.buttonStatus)
        name_common = panelList[position]!!.name
        panelNumber = panelList[position]!!.id
        panelid = panelList[position]!!.id
        type = panelList[position]!!.type
        button5Type = panelList[position]!!.button5Type
        button6Type = panelList[position]!!.button6Type
        button7Type = panelList[position]!!.button7Type
        button8Type = panelList[position]!!.button8Type
        val fivename = panelList[position]!!.button5Name
        val sixname = panelList[position]!!.button6Name
        val sevenname = panelList[position]!!.button7Name
        val eightname = panelList[position]!!.button8Name
        LogUtil.eLength("查看name", fivename + "那么" + sixname + "数据" +
                sevenname + "查看" + eightname + "你看呢")

        compareName(panelList[position]!!.buttonStatus, fivename, sixname, sevenname, eightname)
        pafivetext!!.text = LengthUtil.doit_spit_str(fivename ?: "")
        pasixtext!!.text = LengthUtil.doit_spit_str(sixname ?: "")
        paseventext!!.text = LengthUtil.doit_spit_str(sevenname ?: "")
        paeighttext!!.text = LengthUtil.doit_spit_str(eightname ?: "")
        LogUtil.eLength("点击数据", type + "查看数据" + button5Type + "12" + button6Type + "34" +
                button7Type + "45" + button8Type + "67")

        when (gatewayid) {
            "" -> {
                checkWifiPosition(position)
            }
            else -> {
                checkPosition(position)
            }
        }

        panelrela!!.visibility = View.GONE
        panelrela!!.visibility = View.VISIBLE
        clear()
        setPicture(type, button5Type, pafivebtn, 5)
        setPicture(type, button6Type, pasixbtn, 6)
        setPicture(type, button7Type, pasevenbtn, 7)
        setPicture(type, button8Type, paeightbtn, 8)
        LogUtil.eLength("查看item", type + "数据" + position)
        when (type) {
            "A201", "A2A1" -> {
                panelinearone!!.visibility = View.VISIBLE
                LogUtil.eLength("这是进入A201", "看看操作")
            }
            "A202", "A311", "A411", "A2A2" -> {
                panelineartwo!!.visibility = View.VISIBLE
                LogUtil.eLength("这是进入A202", "进入了")
            }
            "A203", "A312", "A321", "A412", "A2A3" -> {
                panelinearthree!!.visibility = View.VISIBLE
                LogUtil.eLength("这是进入A203", "看看操作")
            }
            "A204", "A313", "A322", "A331", "A413", "A414", "A2A4" -> panelinearfour!!.visibility = View.VISIBLE
            "A301" -> {
                panelinearfour!!.visibility = View.VISIBLE
                flagfive = false
                flagsix = true
                flagseven = true
                flageight = true
            }
            "A302" -> {
                panelinearfour!!.visibility = View.VISIBLE
                flagfive = false
                flagsix = false
                flagseven = true
                flageight = true
            }
            "A303" -> {
                flagfive = false
                flagsix = false
                flagseven = false
                flageight = true
                paneThreeLuTiaoGuang!!.visibility = View.VISIBLE
            }
            else -> panelinearfour!!.visibility = View.VISIBLE
        }
    }

    private fun checkWifiPosition(position: Int) {
        for (i in checkList.indices) {
            checkList[i] = false
        }
        checkList[position] = true
        adapter!!.notifyDataSetChanged()
    }

    private fun clear() {
        panelinearone!!.visibility = View.GONE
        panelineartwo!!.visibility = View.GONE
        panelinearthree!!.visibility = View.GONE
        panelinearfour!!.visibility = View.GONE
        paneThreeLuTiaoGuang!!.visibility = View.GONE
        pafivebtn!!.setImageBitmap(null)
        pasixbtn!!.setImageBitmap(null)
        pasevenbtn!!.setImageBitmap(null)
        paeightbtn!!.setImageBitmap(null)
    }

    private fun compareName(buttonStatus: String?, fivename: String?, sixname: String?, sevenname: String?, eightname: String?) {
        var fivename = fivename
        var sixname = sixname
        var sevenname = sevenname
        var eightname = eightname
        if (fivename == null) {
            fivename = ""
        }
        if (sixname == null) {
            sixname = ""
        }
        if (sevenname == null) {
            sevenname = ""
        }
        if (eightname == null) {
            eightname = ""
        }


        //1代表为空值或者2非空值代表场景一直3非空值不相等
        flagimagefive = if (fivename == "") {
            "1"
        } else {
            if (fivename == sceneName) {
                "2"
            } else {
                "3"
            }
        }
        flagimagesix = if (sixname == "") {
            "1"
        } else {
            if (sixname == sceneName) {
                "2"
            } else {
                "3"
            }
        }
        flagimageseven = if (sevenname == "") {
            "1"
        } else {
            if (sevenname == sceneName) {
                "2"
            } else {
                "3"
            }
        }
        flagimageight = if (eightname == "") {
            "1"
        } else {
            if (fivename == sceneName) {
                "2"
            } else {
                "3"
            }
        }
    }

    private fun setPicture(panel_type: String?, type: String?, button: ImageView?, index: Int) { //buttonType是按钮关联的场景类型
        LogUtil.eLength("这是类型数据", type + "查看类型")
        button!!.visibility = View.VISIBLE
    }
}