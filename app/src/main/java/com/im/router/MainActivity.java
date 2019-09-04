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

        IMRouter.getInstance().build("/mobile/main4/activity")
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*if (requestCode == 12) {
            int nums = data.getIntExtra("INT_1", 0);
            text_result.setText("返回数字=" + nums);

        }*/
    }
}
