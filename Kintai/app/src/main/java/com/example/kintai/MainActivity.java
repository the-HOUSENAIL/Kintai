package com.example.kintai;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private SimpleDateFormat sdfday = new SimpleDateFormat("yyyy/MM/dd");
    private SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat dataFormat =
            new SimpleDateFormat("HH:mm:ss", Locale.JAPAN);

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("勤怠管理");

        tv = (TextView)findViewById(R.id.tv);

        Calendar cal1 = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS");
        String str1 = sdf.format(cal1.getTime());

        Calendar cal2 = Calendar.getInstance();

        try{
            cal2.setTime(sdf.parse(str1));

            if(cal1.equals(cal2)){
                tv.setText("true");
            } else {
                tv.setText("false");
            }

        }catch (ParseException e){
            tv.setText("exception");
        }
    }


    public void onClickKintaiButton(View view) {
        Intent intent = new Intent(MainActivity.this, Kintai2Activity.class);
        startActivity(intent);
    }

    public void onClickCheckBtn(View view) {
        Intent intent = new Intent(MainActivity.this, DataActivity.class);
        startActivity(intent);
    }

    public void onClickConfBtn(View view) {
        Intent intent = new Intent(MainActivity.this, Config.class);
        startActivity(intent);
    }

}
