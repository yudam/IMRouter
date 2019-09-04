package com.test.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.imrouter.annotation.Router;

@Router(path = "/mobile/main2/activity")
public class Main2Activity<Rout> extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }
}
