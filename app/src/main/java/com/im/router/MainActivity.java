package com.im.router;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.imrouter.annotation.Router;

@Router(path = "/test/activity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
