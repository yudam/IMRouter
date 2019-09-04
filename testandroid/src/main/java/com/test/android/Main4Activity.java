package com.test.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.imrouter.annotation.Router;

@Router(path = "/mobile/main4/activity")
public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
    }
}
