package com.example.wuxudong.xun;

/**
 * Created by wuxudong on 17-3-29.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ImageView close = (ImageView) findViewById(R.id.close_login);
        close.setOnClickListener(this);
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.close_login:
                this.finish();
                break;
            default:

                break;
        }
    }
}