package com.example.kintai;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class DetailKintai extends AppCompatActivity implements View.OnClickListener {
    private Calendar cal;
    private String date;
    private String time;
    private EditText t1;
    private EditText t2;
    private EditText t3;
    private EditText t4;
    private EditText t5;
    private EditText t6;
    private EditText t7;
    private KintaiData kintaiData;

    private KintaiOpenHelper helper = new KintaiOpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kintai);
        setTitle("勤怠内容");

        t1 = findViewById(R.id.startDay);
        t1.setOnClickListener(this);
        t2 = findViewById(R.id.startTime);
        t2.setOnClickListener(this);
        t3 = findViewById(R.id.endDay);
        t3.setOnClickListener(this);
        t4 = findViewById(R.id.endTime);
        t4.setOnClickListener(this);
        t5 = findViewById(R.id.breakTime);
        t5.setOnClickListener(this);
        t6 = findViewById(R.id.totalWork);
        t6.setOnClickListener(this);
        t7 = findViewById(R.id.memo);
        t7.setOnClickListener(this);

        Intent intent =getIntent();
        kintaiData = (KintaiData) intent.getSerializableExtra("kintaiData");
        t1.setText(kintaiData.getStartDay());
        t2.setText(kintaiData.getStartTime());
        t3.setText(kintaiData.getEndDay());
        t4.setText(kintaiData.getEndTime());
        t5.setText(kintaiData.getBreakTime());
        t6.setText(kintaiData.getTotalWork());
        t7.setText(kintaiData.getMemo());
    }

    @Override
    public void onClick(View v) {
        cal = Calendar.getInstance();


        if (v == t1) {
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date = String.format("%04d/%02d/%02d", year, month + 1, dayOfMonth);
                    t1.setText(date);
                }
            },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }

        if (v == t2) {
            TimePickerDialog dialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            time =
                                    String.format("%02d:%02d", hourOfDay, minute);
                            t2.setText(time);
                        }
                    },
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    true);
            dialog.show();
        }

        if (v == t3) {
            DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    date = String.format("%04d/%02d/%02d", year, month + 1, dayOfMonth);
                    t3.setText(date);
                }
            },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        }

        if (v == t4) {
            TimePickerDialog dialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            time =
                                    String.format("%02d:%02d", hourOfDay, minute);
                            t4.setText(time);
                        }
                    },
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    true);
            dialog.show();
        }
    }


    // 編集
    public void onClickEditDoneButton(View view) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("startDay", t1.getText().toString());
        cv.put("startTime", t2.getText().toString());
        String start = t1 + "/" + t2;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.JAPAN);
        try {
            Date date1 = sdf.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cv.put("endDay", t3.getText().toString());
        cv.put("endTime", t4.getText().toString());
        cv.put("breakTime",t5.getText().toString());
        cv.put("totalWork", t6.getText().toString());
        cv.put("memo", t7.getText().toString());
        String[] params = {kintaiData.getId().toString()};
        db.update("kintai", cv, "id=?", params);
        finish();
    }

    // 削除
    public void onClickDeleteButton(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("削除してよろしいですか？");
        builder.setPositiveButton("はい", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SQLiteDatabase db = helper.getWritableDatabase();
                String[] params = {kintaiData.getId().toString()};
                db.delete("kintai", "id=?", params);
                finish();
            }
        });
        builder.setNegativeButton("いいえ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 特に何もしない
            }
        });

        builder.show();
    }

    // 戻る
    public void onClickBackButton(View view) {
        finish();
    }
}
