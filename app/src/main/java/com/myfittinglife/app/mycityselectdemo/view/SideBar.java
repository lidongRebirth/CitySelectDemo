package com.myfittinglife.app.mycityselectdemo.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.myfittinglife.app.mycityselectdemo.Constants;

/**
 * @Author      LD
 * @Time        2018/12/18 9:58
 * @Describe    自定义View, 用来显示拼音列表
 * @Modify
 */
public class SideBar extends View {

    public String[] characters = new String[]{"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Paint paint;
    private int textSize = 20;        //拼音文字大小
    private int defaultTextColor = Color.parseColor("#D2D2D2");   //默认拼音文字的颜色 Color.parseColor("#D2D2D2");
    private int selectedTextColor = Color.parseColor("#2DB7E1");  //选中后的拼音文字的颜色 Color.parseColor("#2DB7E1");
    private int touchedBgColor = Color.parseColor("#FF4081");    //触摸时的拼音文字的颜色 Color.parseColor("#F5F5F5");点击拼音控件时的背景颜色
    private TextView text_dialog;

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;//当触摸拼音时的监听事件

    private int position = -1;

    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //设置选中拼音时弹的dialog框字体
    public void setTextDialog(TextView textView) {
        this.text_dialog = textView;
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        int singleHeight = height / characters.length;

        for (int i = 0; i < characters.length; i++) {
            if (i == position) {
                paint.setColor(selectedTextColor);
            } else {
                paint.setColor(defaultTextColor);
            }
            paint.setTextSize(textSize);

            float xPos = width / 2 - paint.measureText(characters[i]) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText(characters[i], xPos, yPos, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y = event.getY();

        position = (int) (y / (getHeight() / characters.length));
        if (position >= 0 && position < Constants.CITY_TYPE.length) {
            onTouchingLetterChangedListener.onTouchingLetterChanged(position);
            switch (action) {
                case MotionEvent.ACTION_UP:
                    setBackgroundColor(Color.TRANSPARENT);
                    position = -1;
                    invalidate();
                    if (text_dialog != null) {
                        text_dialog.setVisibility(View.INVISIBLE);
                    }
                    break;
                default:
                    setBackgroundColor(touchedBgColor);
                    invalidate();
                    text_dialog.setText(characters[position]);
                    break;
            }
        } else {
            setBackgroundColor(Color.TRANSPARENT);
            if (text_dialog != null) {
                text_dialog.setVisibility(View.INVISIBLE);
            }

        }
        return true;
    }

    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(int position);
    }

}
