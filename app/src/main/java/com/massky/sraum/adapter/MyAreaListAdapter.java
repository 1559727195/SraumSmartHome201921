package com.massky.sraum.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.massky.sraum.R;
import com.massky.sraum.activity.MyDeviceListActivity;
import com.massky.sraum.activity.MyfamilyActivity;
import com.massky.sraum.activity.PersonMessageActivity;
import com.massky.sraum.activity.RoomListActivity;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by masskywcy on 2017-05-16.
 */

public class MyAreaListAdapter extends BaseAdapter {
    private List<Map> list = new ArrayList<>();
    private boolean is_open_to_close;
    private String roomManager;

    public MyAreaListAdapter(Context context, List<Map> list, String roomManager) {
        super(context, list);
        this.list = list;
        this.roomManager = roomManager;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderContentType viewHolderContentType = null;
        if (null == convertView) {
            viewHolderContentType = new ViewHolderContentType();
            convertView = LayoutInflater.from(context).inflate(R.layout.myarealist_item, null);
            viewHolderContentType.swipe_context = (RelativeLayout) convertView.findViewById(R.id.swipe_context);
            viewHolderContentType.area_name_txt = (TextView) convertView.findViewById(R.id.area_name_txt);
            viewHolderContentType.rename_btn = (Button) convertView.findViewById(R.id.rename_btn);
            viewHolderContentType.swipemenu_layout = (SwipeMenuLayout) convertView.findViewById(R.id.swipemenu_layout);
            viewHolderContentType.hand_scene_btn = (ImageView) convertView.findViewById(R.id.hand_scene_btn);
            convertView.setTag(viewHolderContentType);
        } else {
            viewHolderContentType = (ViewHolderContentType) convertView.getTag();
        }
//
//        viewHolderContentType.area_name_txt.setText(list.get(position).get("name").toString());

        switch (list.get(position).get("authType").toString()) {
            case "1":
                viewHolderContentType.area_name_txt.setText(list.get(position).get("name").toString() + "(" + "业主" + ")");
                break;
            case "2":
                viewHolderContentType.area_name_txt.setText(list.get(position).get("name").toString() + "(" +
                        "" + "成员" + ")");
                break;
        }
//        final String authType = list.get(position).get("authType").toString();
//        switch (authType) {
//            case "1":
//                viewHolderContentType.swipemenu_layout.setSwipeEnable(true);
//                break;
//            case "2":
//                viewHolderContentType.swipemenu_layout.setSwipeEnable(false);
//                break;
//        }
        viewHolderContentType.swipemenu_layout.setSwipeEnable(false);
        viewHolderContentType.hand_scene_btn.setVisibility(View.VISIBLE);
        final ViewHolderContentType finalViewHolderContentType1 = viewHolderContentType;
        viewHolderContentType.swipe_context.setOnClickListener(new View.OnClickListener() {//事件被SwipeLayout释放后执行，
            @Override
            public void onClick(View view) {
//                switch (authType) {
//                    case "1":
//
//                        break;
//                    case "2":
//                        Intent intent = new Intent(context, RoomListActivity.class);
//                        intent.putExtra("areaNumber", list.get(position).get("number").toString());
//                        intent.putExtra("authType", list.get(position).get("authType").toString());
//                        context.startActivity(intent);
//                        break;
//                }
                Intent intent = null;
                switch (roomManager) {
                    case "roomManager":
                       intent = new Intent(context, RoomListActivity.class);
                        break;
                    case "deviceManager":
                        intent = new Intent(context, MyDeviceListActivity.class);
                        break;
                    case "familyManager":
                        intent = new Intent(context, MyfamilyActivity.class);
                        break;
                }
                intent.putExtra("areaNumber", list.get(position).get("number").toString());
                intent.putExtra("authType", list.get(position).get("authType").toString());
                context.startActivity(intent);
            }
        });

        switch (list.get(position).get("sign") == null ? "" : list.get(position).get("sign").toString()) {
            case "1":

                viewHolderContentType.area_name_txt.setTextColor(context.getResources().getColor(R.color.green));
                break;
            case "0":
                viewHolderContentType.area_name_txt.setTextColor(context.getResources().getColor(R.color.black));

                break;
        }


        ((SwipeMenuLayout) convertView).setOnMenuClickListener(new SwipeMenuLayout.OnMenuClickListener() {

            @Override
            public void onItemClick() {
                Intent intent = new Intent(context, RoomListActivity.class);
                intent.putExtra("areaNumber", list.get(position).get("number").toString());
                intent.putExtra("authType", list.get(position).get("authType").toString());
                context.startActivity(intent);
            }

            @Override
            public void onItemClick_By_btn(boolean is_open_to_close1) {//SwipeLayout是否在打开到关闭的过程
                is_open_to_close = is_open_to_close1;
            }
        });

        viewHolderContentType.rename_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                showRenameDialog();
            }
        });
        return convertView;
    }

    class ViewHolderContentType {
        ImageView device_type_pic;
        TextView area_name_txt;
        TextView hand_gateway_content;
        Button rename_btn;
        RelativeLayout swipe_context;
        SwipeMenuLayout swipemenu_layout;
        ImageView hand_scene_btn;
    }

    //自定义dialog,自定义重命名dialog

    public void showRenameDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.editscene_dialog, null);
        TextView confirm; //确定按钮
        TextView cancel; //确定按钮
        TextView tv_title;
//        final TextView content; //内容
        cancel = (TextView) view.findViewById(R.id.call_cancel);
        confirm = (TextView) view.findViewById(R.id.call_confirm);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
//        tv_title.setText("是否拨打119");
//        content.setText(message);
        //显示数据
        final Dialog dialog = new Dialog(context, R.style.BottomDialog);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        int displayHeight = dm.heightPixels;
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = (int) (displayWidth * 0.8); //宽度设置为屏幕的0.5
//        p.height = (int) (displayHeight * 0.5); //宽度设置为屏幕的0.5
//        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.getWindow().setAttributes(p);  //设置生效
        dialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}
