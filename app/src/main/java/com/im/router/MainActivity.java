package com.im.router;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.imrouter.annotation.Router;
import com.imrouter.api.IMRouter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

@Router(path = "/test/activity")
public class MainActivity extends AppCompatActivity {

    private TextView text_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_result = findViewById(R.id.text_result);
        IMRouter.initIndex(getApplication());
    }


    public void btn1(View view) {

        IMRouter.getInstance().build("/mobile/test/activity")
                .withFlag(Intent.FLAG_ACTIVITY_NO_HISTORY)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     *module之间的资源文件不可以重复，否则无法跳转
     *
     */
}
