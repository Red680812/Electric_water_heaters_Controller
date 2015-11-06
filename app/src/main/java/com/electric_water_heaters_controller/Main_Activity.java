package com.electric_water_heaters_controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.electric_water_heaters_controller.widget.TosAdapterView;
import com.electric_water_heaters_controller.widget.TosGallery;
import com.electric_water_heaters_controller.widget.WheelView;

import java.util.Calendar;

public class Main_Activity extends AppCompatActivity {
    private String[] hoursArray = { "00", "01","02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    private String[] minsArray = { "00", "01","02", "03", "04", "05", "06", "07", "08", "09", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
            "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
            "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53", "54", "55", "56", "57", "58", "59"};

    private WheelView mHours = null;
    private WheelView mMins = null;
    private WheelView mHours_1 = null;
    private WheelView mMins_1 = null;
    private TextView mTextView = null;
    private View mDecorView = null;

    private NumberAdapter hourAdapter;
    private NumberAdapter minAdapter;
    private NumberAdapter hourAdapter_1;
    private NumberAdapter minAdapter_1;

    Button Btn_Timer1_PopupWindow;
    Button Btn_Timer2_PopupWindow;
    Button Btn_Timer3_PopupWindow;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //定义按钮
        Btn_Timer1_PopupWindow=(Button)this.findViewById(R.id.Btn_Timer1);
        Btn_Timer1_PopupWindow.setOnClickListener(new ClickEvent());
        Btn_Timer2_PopupWindow=(Button)this.findViewById(R.id.Btn_Timer2);
        Btn_Timer2_PopupWindow.setOnClickListener(new ClickEvent());
        Btn_Timer3_PopupWindow=(Button)this.findViewById(R.id.Btn_Timer3);
        Btn_Timer3_PopupWindow.setOnClickListener(new ClickEvent());
    }

    //统一处理按键事件
    class ClickEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if(v==Btn_Timer1_PopupWindow)
            {
                showPopupWindow(Main_Activity.this,
                        Main_Activity.this.findViewById(R.id.Btn_Timer1));
            }
            if(v==Btn_Timer2_PopupWindow)
            {
                showPopupWindow(Main_Activity.this,
                        Main_Activity.this.findViewById(R.id.Btn_Timer2));
            }
            if(v==Btn_Timer3_PopupWindow)
            {
                showPopupWindow(Main_Activity.this,
                        Main_Activity.this.findViewById(R.id.Btn_Timer3));
            }
        }
    }

    public void showPopupWindow(Context context,View parent){
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopupWindow=inflater.inflate(R.layout.wheel_time, null, false);
        final PopupWindow pw= new PopupWindow(vPopupWindow,1050,900,true);
        // 使其聚集
        pw.setFocusable(true);
        // 设置允许在外点击消失
        pw.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        pw.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        //OK按钮及其处理事件
        Button btnOK=(Button)vPopupWindow.findViewById(R.id.BtnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置文本框内容
                //EditText edtUsername = (EditText) vPopupWindow.findViewById(R.id.username_edit);
                //edtUsername.setText("username");
                //EditText edtPassword = (EditText) vPopupWindow.findViewById(R.id.password_edit);
                //edtPassword.setText("password");
                Toast.makeText(getApplicationContext(), "確定", Toast.LENGTH_SHORT).show();
                pw.dismiss();//关闭
            }
        });

        //Cancel按钮及其处理事件
        Button btnCancel=(Button)vPopupWindow.findViewById(R.id.BtnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pw.dismiss();//关闭
            }
        });
        //显示popupWindow对话框
        pw.showAtLocation(parent, Gravity.CENTER, 0, 0);

        mTextView = (TextView)vPopupWindow.findViewById(R.id.sel_password);

        mHours = (WheelView)vPopupWindow.findViewById(R.id.wheel1);
        mMins = (WheelView)vPopupWindow.findViewById(R.id.wheel2);
        mHours_1 = (WheelView)vPopupWindow.findViewById(R.id.wheel1_1);
        mMins_1 = (WheelView)vPopupWindow.findViewById(R.id.wheel2_1);

        mHours.setScrollCycle(true);
        mMins.setScrollCycle(true);
        mHours_1.setScrollCycle(true);
        mMins_1.setScrollCycle(true);

        hourAdapter = new NumberAdapter(hoursArray);
        minAdapter = new NumberAdapter(minsArray);
        hourAdapter_1 = new NumberAdapter(hoursArray);
        minAdapter_1 = new NumberAdapter(minsArray);

        mHours.setAdapter(hourAdapter);
        mMins.setAdapter(minAdapter);
        mHours_1.setAdapter(hourAdapter_1);
        mMins_1.setAdapter(minAdapter_1);

        // set current time
        Calendar c = Calendar.getInstance();
        int curHours = c.get(Calendar.HOUR_OF_DAY);
        int curMinutes = c.get(Calendar.MINUTE);

        mHours.setSelection(curHours, true);
        mMins.setSelection(curMinutes, true);
        mHours_1.setSelection(17, true);
        mMins_1.setSelection(30, true);

        ((WheelTextView)mHours.getSelectedView()).setTextSize(30);
        ((WheelTextView)mMins.getSelectedView()).setTextSize(30);
        ((WheelTextView)mHours.getSelectedView()).setTextColor(0xffffffff);
        ((WheelTextView)mMins.getSelectedView()).setTextColor(0xffffffff);
        ((WheelTextView)mHours.getSelectedView()).setGravity(Gravity.CENTER_HORIZONTAL);
        ((WheelTextView)mMins.getSelectedView()).setGravity(Gravity.CENTER_HORIZONTAL);

        ((WheelTextView)mHours_1.getSelectedView()).setTextSize(30);
        ((WheelTextView)mMins_1.getSelectedView()).setTextSize(30);
        ((WheelTextView)mHours_1.getSelectedView()).setTextColor(0xffffffff);
        ((WheelTextView)mMins_1.getSelectedView()).setTextColor(0xffffffff);
        ((WheelTextView)mHours_1.getSelectedView()).setGravity(Gravity.CENTER_HORIZONTAL);
        ((WheelTextView)mMins_1.getSelectedView()).setGravity(Gravity.CENTER_HORIZONTAL);

        mHours.setOnItemSelectedListener(mListener);
        mMins.setOnItemSelectedListener(mListener);
        mHours_1.setOnItemSelectedListener(mListener);
        mMins_1.setOnItemSelectedListener(mListener);

        mHours.setUnselectedAlpha(0.5f);
        mMins.setUnselectedAlpha(0.5f);
        mHours_1.setUnselectedAlpha(0.5f);
        mMins_1.setUnselectedAlpha(0.5f);

        mDecorView = getWindow().getDecorView();

    }

    private TosAdapterView.OnItemSelectedListener mListener = new TosAdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(TosAdapterView<?> parent, View view, int position, long id) {
            ((WheelTextView)view).setTextSize(30);

            int index = Integer.parseInt(view.getTag().toString());
            int count = parent.getChildCount();
            //Log.e("DEBUG_TAG", view.getTag().toString());

            if(index < count-1){
                ((WheelTextView)parent.getChildAt(index+1)).setTextSize(20);
            }
            if(index>0){
                ((WheelTextView)parent.getChildAt(index-1)).setTextSize(20);
            }

            formatData();
        }

        @Override
        public void onNothingSelected(TosAdapterView<?> parent) {

        }
    };

    private void formatData() {
        int pos1 = mHours.getSelectedItemPosition();
        int pos2 = mMins.getSelectedItemPosition();

        String text = String.format("%d%d", pos1, pos2);
        mTextView.setText(text);
    }

    private class NumberAdapter extends BaseAdapter {
        int mHeight = 50;
        String[] mData = null;

        public NumberAdapter(String[] data) {
            mHeight = (int) Utils.dipToPx(Main_Activity.this, mHeight);
            this.mData = data;
        }

        @Override
        public int getCount() {
            return (null != mData) ? mData.length : 0;
        }

        @Override
        public View getItem(int arg0) {
            return getView(arg0, null, null);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            WheelTextView textView = null;

            if (null == convertView) {
                convertView = new WheelTextView(Main_Activity.this);
                convertView.setLayoutParams(new TosGallery.LayoutParams(-1, mHeight));
                textView = (WheelTextView) convertView;
                textView.setTextSize(20);
                textView.setTextColor(0xffffffff);
                textView.setGravity(Gravity.CENTER);
            }

            String text = mData[position];
            if (null == textView) {
                textView = (WheelTextView) convertView;
            }

            textView.setText(text);
            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
