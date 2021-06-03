package com.examples.restaurantmap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.sug.SuggestionResult;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {

    public static LatLng latLng;

    TextView tv_placeName,tv_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        tv_placeName = findViewById(R.id.tv_placeName);
        tv_location = findViewById(R.id.tv_location);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddActivity.this,SearchActivity.class),1);
            }
        });

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddActivity.this,MarkerDemo.class));
            }
        });
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.save(AddActivity.this,latLng);

                Toast.makeText(AddActivity.this,"Save success",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        latLng = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==1){

            SuggestionResult.SuggestionInfo info = data.getParcelableExtra("key");
            if (info!=null){
                tv_placeName.setText(info.getKey());
                tv_location.setText(info.getAddress());

                latLng = info.getPt();
            }
        }

    }
}