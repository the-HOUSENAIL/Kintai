package com.example.kintai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class DataActivity extends AppCompatActivity {

    private KintaiOpenHelper helper = new KintaiOpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        setTitle("履歴");

    }

    @Override
    public void onResume() {
        super.onResume();
        // リストを表示
        // データ一覧の取得
        List<KintaiData> data = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] cols = {"id", "startDay", "startTime", "endDay", "endTime", "breakTime", "totalWork", "memo"};
        Cursor cs = db.query("kintai", cols, null, null, null, null, null, null);
        if (cs.moveToFirst()) {
            do {
                Integer id = cs.getInt(0);
                String startDay = cs.getString(1);
                String startTime = cs.getString(2);
                String endDay = cs.getString(3);
                String endTime = cs.getString(4);
                String breakTime = cs.getString(5);
                String totalWork = cs.getString(6);
                String memo = cs.getString(7);
                data.add(new KintaiData(id, startDay, startTime, endDay, endTime, breakTime, totalWork, memo));
            } while (cs.moveToNext());
        }
        cs.close();
        // アダプタのセット
        KintaiListAdapter adapter = new KintaiListAdapter(this, data, R.layout.kintai_list_data);
        ListView kintaiList = findViewById(R.id.kintaiListView);
        kintaiList.setAdapter(adapter);

        // リスト項目がクリックされた時の処理
        kintaiList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                KintaiData kintaiData = (KintaiData) parent.getItemAtPosition(position);
                Intent intent = new Intent(DataActivity.this, DetailKintai.class);
                intent.putExtra("kintaiData", kintaiData);
                startActivity(intent);
            }
        });
    }
    public void onClickDakokuBtn(View view) {
        Intent intent = new Intent(this, Kintai2Activity.class);
        startActivity(intent);
    }
}
