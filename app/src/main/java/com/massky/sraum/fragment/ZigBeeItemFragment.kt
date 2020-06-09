package com.massky.sraum.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import com.AddTogenInterface.AddTogglenInterfacer
import com.massky.sraum.R
import com.massky.sraum.User
import com.massky.sraum.Util.*
import com.massky.sraum.Utils.ApiHelper
import com.massky.sraum.activity.AddZigbeeDevActivity
import com.massky.sraum.activity.EditTablePMActivity
import com.massky.sraum.activity.SelectSmartDoorLockActivity
import com.massky.sraum.adapter.SelectDevTypeAdapter
import com.massky.sraum.fragment.GatewayDialogFragment.Companion.newInstance
import com.massky.sraum.tool.Constants
import com.massky.sraum.widget.ListViewForScrollView
import okhttp3.Call
import java.io.Serializable
import java.util.*
import kotlin.collections.HashMap

/**
 * Created by masskywcy on 2018-11-13.
 */
class ZigBeeItemFragment : Fragment() {
    private var views: View? = null
    private var title: String? = null
    private var gatewayList = ArrayList<Map<String, Any>>()
    private val icon = intArrayOf(
            R.drawable.icon_type_yijiandk_90, R.drawable.icon_type_liangjiandk_90,
            R.drawable.icon_type_sanjiandk_90, R.drawable.icon_type_sijiandk_90, R.drawable.icon_type_yilutiaoguang_90,
            R.drawable.icon_type_lianglutiaoguang_90, R.drawable.icon_type_sanlutiaoguang_90, R.drawable.icon_type_chuanglianmb_90,
            R.drawable.icon_type_kongtiaomb_90,
            R.drawable.icon_type_menci_90, R.drawable.icon_type_rentiganying_90,
            R.drawable.icon_type_toa_90, R.drawable.icon_type_yanwucgq_90, R.drawable.icon_type_tianranqibjq_90,
            R.drawable.icon_type_jinjianniu_90, R.drawable.icon_type_zhinengmensuo_90, R.drawable.icon_type_pm25_90,
            R.drawable.icon_type_shuijin_90, R.drawable.icon_type_duogongneng_90, R.drawable.icon_type_zhinengchazuo_90,
            R.drawable.pic_zigbee_pingyikzq,
            R.drawable.icon_type_wangguan_90, R.drawable.duogongneng1
    )

    //"B301"暂时为多功能模块
    private val types = arrayOf(
            "A201", "A202", "A203", "A204", "A301", "A302", "A303", "A401", "A501",
            "A801", "A901", "A902", "AB01", "AB04", "B001", "B201", "AD01", "AC01", "B301", "B101", "平移控制器", "网关", "多功能面板"
    )
    private val iconName = intArrayOf(R.string.yijianlight, R.string.liangjianlight, R.string.sanjianlight, R.string.sijianlight,
            R.string.yilutiaoguang1, R.string.lianglutiaoguang1, R.string.sanlutiao1, R.string.window_panel1, R.string.kongtiao_panel
            , R.string.menci, R.string.rentiganying, R.string.jiuzuo, R.string.yanwu, R.string.tianranqi, R.string.jinjin_btn,
            R.string.zhineng, R.string.pm25, R.string.shuijin, R.string.duogongneng, R.string.cha_zuo_2, R.string.pingyi_control, R.string.wangguan, R.string.duogongneng1
    )
    private var adapter: SelectDevTypeAdapter? = null
    private var newFragment: ConfigDialogFragment? = null
    private var newGatewayFragment: GatewayDialogFragment? = null
    private var dialogUtil: DialogUtil? = null
    private val list_hand_scene: List<Map<*, *>> = ArrayList()
    private val TAG = "robin debug"
    private val mac: String? = null
    private val number: String? = null
    private val handler = Handler()
    private var zigbee_list: ListViewForScrollView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        views = inflater.inflate(R.layout.fragment_zigbee_selects, null)
        val bundle = arguments
        title = bundle!!.getSerializable("title") as String
        return views
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onView(view)
        dialogUtil = DialogUtil(activity)
        initDialog()
        initGatewayDialog()
        onData()
    }

    private fun onView(view: View) {
        zigbee_list = view.findViewById(R.id.zigbee_list)
    }

    /*
     * 初始化dialog
     */
    private fun initDialog() {
        // TODO Auto-generated method stub
        newFragment = ConfigDialogFragment.newInstance(activity, "", "") //初始化快配和搜索设备dialogFragment
    }

    /*
     * 初始化dialog
     */
    private fun initGatewayDialog() {
        // TODO Auto-generated method stub
        newGatewayFragment = newInstance(activity, "", "") //初始化快配和搜索设备dialogFragment
        //        newGatewayFragment = (GatewayDialogFragment) getGatewayInterfacer;
        getGatewayInterfacer = newGatewayFragment as GatewayDialogFragment
    }

    /**
     * 展示全窗体dialog对话框
     */
    private fun show_dialog_fragment() {
        if (!newFragment!!.isAdded) { //DialogFragment.show()内部调用了FragmentTransaction.add()方法，所以调用DialogFragment.show()方法时候也可能
            val manager = childFragmentManager
            val ft = manager.beginTransaction()
            ft.add(newFragment!!, "dialog")
            ft.commit()
        }
    }

    /**
     * 展示网关列表全窗体dialog对话框
     */
    private fun show_gateway_dialog_fragment() {
        if (!newGatewayFragment!!.isAdded) { //DialogFragment.show()内部调用了FragmentTransaction.add()方法，所以调用DialogFragment.show()方法时候也可能
            val manager = childFragmentManager
            val ft = manager.beginTransaction()
            ft.add(newGatewayFragment!!, "dialog")
            ft.commit()
        }
    }

    /**
     * 获取选择区域下的所有网关
     *
     * @param map
     */
    private fun sraum_getAllGateWays(map: HashMap<String, Any>) {
        if (dialogUtil != null) {
            dialogUtil!!.loadDialog()
        }
        val mapdevice: MutableMap<String, String> = HashMap()
        mapdevice["token"] = TokenUtil.getToken(activity)
        val areaNumber = SharedPreferencesUtil.getData(activity, "areaNumber", "") as String
        mapdevice["areaNumber"] = areaNumber
        //        mapdevice.put("boxNumber", TokenUtil.getBoxnumber(SelectSensorActivity.this));
        MyOkHttp.postMapString(ApiHelper.sraum_getAllBox
                , mapdevice, object : Mycallback(AddTogglenInterfacer { //刷新togglen数据
            sraum_getAllGateWays(map)
        }, activity, dialogUtil) {
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
                gatewayList = ArrayList()
                for (i in user.gatewayList.indices) {
                    val mapdevice: MutableMap<String, String> = HashMap()
                    mapdevice["name"] = user.gatewayList[i].name
                    mapdevice["number"] = user.gatewayList[i].number
                    gatewayList.add(mapdevice)
                }
                //
//                        if (user.gatewayList != null && user.gatewayList.size() != 0) {//区域命名
//                            showGatewayListAdapter = new ShowGatewayListAdapter(getActivity()
//                                    , gatewayList);
//                            list_show_rev_item.setAdapter(showGatewayListAdapter);
//                        }
                if (gatewayList.size != 0) {
                    if (gatewayList.size == 1) {
                        val type = map["type"] as String?
                        val gateway_number = gatewayList[0]["number"] as String?
                        when (type) {
                            "B201" -> {
                                val intent_position = Intent(activity, SelectSmartDoorLockActivity::class.java)
                                map["gateway_number"] = gateway_number!!
                                intent_position.putExtra("map", map as Serializable)
                                startActivity(intent_position)
                            }
                            else -> set_gateway(0, map)
                        }
                    } else {
                        show_gateway_dialog_fragment()
                        if (getGatewayInterfacer != null) getGatewayInterfacer!!.sendGateWayparams(map, gatewayList)
                    }
                } else {
                    ToastUtil.showToast(activity, "网关列表为空")
                }
            }
        })
    }

    /**
     * 设置网关开启模式
     *
     * @param position
     */
    private fun set_gateway(position: Int, map1: Map<*, *>) {
        //去设置设置网关模式
        val type = map1["type"] as String?
        val status = map1["status"] as String?
        val gateway_number = gatewayList[position]["number"] as String?
        val areaNumber = SharedPreferencesUtil.getData(activity, "areaNumber", "") as String
        //在这里先调
        //设置网关模式-sraum-setBox
        val map= HashMap<Any?, Any?>()
        //        String phoned = getDeviceId(SelectZigbeeDeviceActivity.this);
        map["token"] = TokenUtil.getToken(activity)
        map["boxNumber"] = gateway_number
        val regId = SharedPreferencesUtil.getData(activity, "regId", "") as String
        map["regId"] = regId
        map["status"] = status
        map["areaNumber"] = areaNumber
        dialogUtil!!.loadDialog()
        MyOkHttp.postMapObject(ApiHelper.sraum_setGateway, map as HashMap<String,Any>, object : Mycallback(AddTogglenInterfacer { }, activity, dialogUtil) {
            override fun onSuccess(user: User) {
                var intent_position: Intent? = null
                intent_position = Intent(activity, AddZigbeeDevActivity::class.java)
                intent_position.putExtra("type", type)
                intent_position.putExtra("boxNumber", gateway_number)
                startActivity(intent_position)
            }

            override fun wrongToken() {
                super.wrongToken()
            }

            override fun wrongBoxnumber() {
                ToastUtil.showToast(activity,
                        "该网关不存在")
            }
        }
        )
    }

    /**
     * 给全屏窗体传参数
     */
    private var getGatewayInterfacer: GetGatewayInterfacer? = null

    interface GetGatewayInterfacer {
        fun sendGateWayparams(map: Map<String, Any>?, gatewayList: List<Map<String, Any>>?)
    }

    protected fun onData() {
        adapter = SelectDevTypeAdapter(activity, icon, iconName)
        zigbee_list!!.adapter = adapter
        zigbee_list!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            when (types[position]) {
                "A201", "A202", "A203", "A204", "A301", "A302", "A303", "A401", "B301", "B101", "A902", "AD01", "A501", "B201", "多功能面板", "平移控制器" -> //                    case "A511":
//                        sraum_setBox_accent(types[position], "normal");
                    sraum_setBox_accent(types[position], "normal")
                "A801", "A901", "AB01", "AB04", "B001", "AC01" -> //                        sraum_setBox_accent(types[position], "zigbee");
                    sraum_setBox_accent(types[position], "zigbee")
                "网关" -> show_dialog_fragment()
            }
            //                ToastUtil.showToast(SelectZigbeeDeviceActivity.this, "添加网关");
        }
    }

    /**
     * 设置网关开始模式
     *
     * @param type
     * @param normal
     */
    private fun sraum_setBox_accent(type: String, normal: String) {
        val map= HashMap<String, Any>()
        map["type"] = type
        when (normal) {
            "normal" -> map["status"] = "1" //普通进入设置模式
            "zigbee" -> map["status"] = "12" //zigbee进入设置模式
        }
        sraum_getAllGateWays(map)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // 扫描二维码/条码回传
        if (requestCode == Constants.SCAN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val content = data.getStringExtra("result")
                Log.e("robin debug", "content:$content")
                if (TextUtils.isEmpty(content)) return
                //在这里解析二维码，变成房号
                // 密钥
                val key = "ztlmassky6206ztl"
                // 解密
                var DeString: String? = null
                try {
//                    content = "0a4ab23ad13aac565069283aac3882e5";
                    DeString = AES.Decrypt(content, key)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                if (DeString == null) {
                    ToastUtil.showToast(activity, "此二维码不是PM2.5二维码")
                } else {
//                    scanlinear.setVisibility(View.GONE);
//                    detairela.setVisibility(View.VISIBLE);
//                    gateid.setText(DeString);
//                    gatedditexttwo.setText("");
                    //跳转到编辑网关界面
                    if (DeString.length == 12) {
                        val stringBuffer = StringBuffer()
                        for (i in 0..5) {
                            stringBuffer.append(DeString.substring(i * 2, i * 2 + 2)).append(":")
                        }
                        val intent = Intent(activity, EditTablePMActivity::class.java)
                        intent.putExtra("mac", stringBuffer.toString().substring(0, stringBuffer.length - 1).toUpperCase())
                        intent.putExtra("id", DeString.toUpperCase())
                        startActivity(intent)
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance(title: String?): ZigBeeItemFragment {
            val bundle = Bundle()
            bundle.putSerializable("title", title)
            val fragment = ZigBeeItemFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}