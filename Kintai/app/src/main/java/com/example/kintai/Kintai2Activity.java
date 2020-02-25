package com.example.kintai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Kintai2Activity extends AppCompatActivity implements View.OnClickListener {
    private long startDay;
    private long endDay;
    private long ddff;
    private long breakTime;
    private long endBreakTime;
    private long totalBT;
    private SimpleDateFormat sdfday = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm");
    private Button clockOnBtn, clockOutBtn, breakBtn, returmBtn;


    /*KintaiOpenHelper helper = new KintaiOpenHelper(this);*/
    KintaiOpenHelper helper = null;

    private GestureDetector gestureDetector;
    private EditText ddText, dateIn, dateOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kintai2);
        setTitle("打刻");

        clockOnBtn = (Button) findViewById(R.id.clockOnBtn);
        clockOnBtn.setOnClickListener(this);
        clockOutBtn = (Button) findViewById(R.id.clockOutBtn);
        clockOutBtn.setOnClickListener(this);
        clockOutBtn.setEnabled(false);
        breakBtn = (Button) findViewById(R.id.breakBtn);
        breakBtn.setOnClickListener(this);
        breakBtn.setEnabled(false);
        returmBtn = (Button) findViewById(R.id.returmBtn);
        returmBtn.setOnClickListener(this);
        returmBtn.setEnabled(false);

        ddText = findViewById(R.id.startDay);
        ddText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        dateIn = findViewById(R.id.dateIn);
        dateIn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

        dateOut = findViewById(R.id.dateOut);
        dateOut.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

    }

    @Override
    public void onClick(View v) {

        if (v == clockOnBtn) {
            if (helper == null) {
                helper = new KintaiOpenHelper(Kintai2Activity.this);
            }
            startDay = System.currentTimeMillis();
            SQLiteDatabase db = helper.getWritableDatabase();
           /* ddText.setText(sdfday.format(startDay));
            dateIn.setText(sdftime.format(startDay));
            clockOnBtn.setEnabled(false);
            clockOutBtn.setEnabled(true);
            breakBtn.setEnabled(true);*/
            /*ContentValues cv = new ContentValues();
            cv.put("startDay", sdfday.format(startDay));
            cv.put("startTime", sdftime.format(startDay));
            db.insert("kintai", null, cv);
            String[] cols = {"id", "startDay", "startTime", "endDay", "endTime", "breakTime", "totalWork", "memo"};
            String[] query = {"startDay =" +  sdfday.format(startDay)};
            Cursor cs = db.query("kintai", cols,  null, query, null, null, null, null);*/
            // 本日出勤した記録を抽出する
            /*if(cs.getCount() != 0){
                Context context = getApplicationContext();
                Toast.makeText(context , "本日はすでに出勤済です。", Toast.LENGTH_LONG).show();
            }else{
                db.insert("kintai", null, cv);
        }*/
            // レコードが1件以上ある場合
            String[] cols = {"id"};
            String selectColumn =  "startDay = ?";
            String[] selectValue = {sdfday.format(startDay)};
            Cursor cs = db.query("kintai", cols,  selectColumn, selectValue, null, null, null, null);
            if(cs.getCount() != 0){
                Context context = getApplicationContext();
                Toast.makeText(context , "本日はすでに出勤済です。", Toast.LENGTH_LONG).show();
            }else{
                ddText.setText(sdfday.format(startDay));
                dateIn.setText(sdftime.format(startDay));
                clockOnBtn.setEnabled(false);
                clockOutBtn.setEnabled(true);
                breakBtn.setEnabled(true);
                ContentValues cv = new ContentValues();
                cv.put("startDay", sdfday.format(startDay));
                cv.put("startTime", sdftime.format(startDay));
                db.insert("kintai", null, cv);
            }

    } else if(v ==breakBtn)

    {
        breakTime = System.currentTimeMillis();
        breakBtn.setEnabled(false);
        returmBtn.setEnabled(true);
        clockOutBtn.setEnabled(false);

    } else if(v ==returmBtn)

    {
        endBreakTime = System.currentTimeMillis();
        totalBT = endBreakTime - breakTime;
        long mm = ddff/ 60000;
        long ss = ((ddff % 3600000) % 60000) / 1000;
        String totalBreak = String.valueOf(mm);
        returmBtn.setEnabled(false);
        clockOutBtn.setEnabled(true);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String[] params = {sdfday.format(startDay)};
        cv.put("breakTime", /*sdfday.format(totalBT)*/totalBreak);
        db.update("kintai", cv, "startDay=?", params);


    } else if(v ==clockOutBtn)

    {
        endDay = System.currentTimeMillis();
        dateOut.setText(sdftime.format(endDay));
        clockOnBtn.setEnabled(true);
        clockOutBtn.setEnabled(false);
        breakBtn.setEnabled(false);
        returmBtn.setEnabled(false);

        // 勤務時間計算
        ddff = endDay - startDay - totalBT;
        long HH = ddff / 3600000;
        long mm = (ddff % 3600000) / 60000;
        long ss = ((ddff % 3600000) % 60000) / 1000;
        String totalWork = String.valueOf(HH) + ":" + (mm);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("endDay", sdfday.format(endDay));
        cv.put("endTime", sdftime.format(endDay));
        cv.put("totalWork", /*sdftime.format(ddff)*/totalWork);
        String[] params = {sdfday.format(startDay)};
        db.update("kintai", cv, "startDay=?", params);
    }

}

    public void onClickCheckBtn(View view) {
        Intent intent = new Intent(this, DataActivity.class);
        startActivity(intent);
    }

    public void onClickConfBtn(View view) {
        Intent intent = new Intent(this, Config.class);
        startActivity(intent);
    }

}
