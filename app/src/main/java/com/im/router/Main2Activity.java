package com.im.router;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.imrouter.annotation.Router;

@Router(path = "test/activity2")
public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView textView=findViewById(R.id.text);


        Bundle bundle=getIntent().getExtras();

        if(bundle!=null){
           int nums= bundle.getInt("INT");
           textView.setText("values="+nums);
        }
    }

    @Override
    public void onBackPressed() {

        Intent intent=new Intent();
        intent.putExtra("INT_1",123456);
        setResult(12,intent);
        finish();
    }
}
