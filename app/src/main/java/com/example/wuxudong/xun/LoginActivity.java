package com.example.wuxudong.xun;

/**
 * Created by wuxudong on 17-3-29.
 */

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private String use_id;
    private String use_pwd;
    private String use_code;


    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 2://返回错误信息
                    Toast.makeText(LoginActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button_login = (Button)findViewById(R.id.btn_login);
        Button button_send_ver = (Button)findViewById(R.id.sendverification);
        button_send_ver.setOnClickListener(this);
        button_login.setOnClickListener(this);
        ImageView close = (ImageView) findViewById(R.id.close_login);
        close.setOnClickListener(this);

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_login:

                if(check_input()){

                    sendRequestWithOkHttp();
                }

                break;
            case R.id.sendverification:
                Toast.makeText(LoginActivity.this,"송신 인증 코드",Toast.LENGTH_SHORT).show();
                break;
            case R.id.close_login:
                this.finish();
                break;
            default:
                break;
        }

    }
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d("sdfs","sdfds");
                    RequestBody requestBody = new FormBody.Builder().add("account",use_id).add("pwd",use_pwd).add("verif",use_code).build();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://121.126.211.81/Api/Home/Index/applogin")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();


                    String responseData = response.body().string();
                    //execute JSON

                    String responseJsonData = "["+responseData+"]";
                    parseJSONWithJSONObject(responseJsonData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData){

        try {

            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0 ; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String judge = jsonObject.getString("judge");

                Message message = new Message();
                message.what = 2;
                if(judge.equals("last_insert")){
                    message.obj = this.getString(R.string.user_useless);
                }else if(judge.equals("true")){
                    message.obj = this.getString(R.string.user_login_ok);

                }else if(judge.equals("password_mistake")){

                    message.obj = this.getString(R.string.user_login_error);

                }else if (judge.equals("unsign_account")){
                    message.obj = this.getString(R.string.user_login_no);
                }
                if(message.what == 2) {
                    handler.sendMessage(message);
                }else{
                    //todo other
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //验证输入是否合法
    private boolean check_input(){
        EditText editText;
        editText = (EditText)findViewById(R.id.verification);
        use_code = editText.getText().toString();
        editText = (EditText)findViewById(R.id.input_email);
        use_id = editText.getText().toString();
        editText = (EditText)findViewById(R.id.input_password);
        use_pwd = editText.getText().toString();
        int ret = 1;
        if(use_id.length() <= 0){
            ret = 0;
            Toast.makeText(this,this.getString(R.string.required_user_phone),Toast.LENGTH_SHORT).show();
        }else if(use_pwd.length() <= 0){
            ret = 0;
            Toast.makeText(this,getString(R.string.required_user_pwd),Toast.LENGTH_SHORT).show();
        }else if(use_code.length() <= 0){
            ret = 0;
            Toast.makeText(this,getString(R.string.required_user_code),Toast.LENGTH_SHORT).show();
        }else{

        }


        if(ret == 1) {
            return true;
        }else{
            return false;
        }

    }

}