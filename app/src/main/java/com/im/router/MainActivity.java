package com.im.router;

import android.content.Intent;
import android.os.Bundle;

import com.imrouter.annotation.Router;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@Router(path = "/test/activity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //IMRouter.initIndex(getApplication(),ImRouterIndex.loadInfo());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
