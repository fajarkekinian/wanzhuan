package com.froyo.playcity.share;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

/**
 * Created by Administrator on 2015/12/26.
 */
public class ShowShare extends PopupWindow {


    private ImageView btn_qq, btn_weibo, btn_weixin,btn_friends;
    private View mMenuView;

    public ShowShare(Activity context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.share_alert, null);
        btn_qq = (ImageView) mMenuView.findViewById(R.id.btn_qq);
        btn_weibo = (ImageView) mMenuView.findViewById(R.id.btn_sinaweibo);
        btn_weixin = (ImageView) mMenuView.findViewById(R.id.btn_wechat);
        btn_friends= (ImageView) mMenuView.findViewById(R.id.btn_wechatmoments);

//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//
//                dismiss();
//            }
//        });
        //设置按钮监听
//        btn_qq.setOnClickListener(itemsOnClick);
//        btn_weibo.setOnClickListener(itemsOnClick);

        this.setContentView(mMenuView);

        this.setWidth(LayoutParams.FILL_PARENT);

        this.setHeight(LayoutParams.WRAP_CONTENT);

        this.setFocusable(true);

        this.setAnimationStyle(R.style.AnimBottom);

        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });

        btn_weibo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

}

