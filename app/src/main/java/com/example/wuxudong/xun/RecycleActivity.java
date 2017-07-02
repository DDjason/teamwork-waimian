package com.example.wuxudong.xun;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wuxudong on 17-4-17.
 */

public class RecycleActivity extends AppCompatActivity {


    private String URL_Huishoudata = "http://121.126.211.81/Api/Home/Index/huishoudata";
    private String URL_Huishou= "http://121.126.211.81/Api/Home/Index/huishou";

    private String Account = "xbd66";

    private String Secpwd_recover;
    private String Num_recover;


    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 2://返回挂单信息
                    Toast.makeText(RecycleActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    TextView textView = (TextView)findViewById(R.id.order_rmb);
                    textView.setText(money6_use);
                    textView = (TextView)findViewById(R.id.optimalprice);
                    textView.setText(recover_use);
                    break;
                default:
                    break;
            }
        }
    };



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycle);

        sendRequestWithOkHttp_getHuishoudata();
        Button button_guadan = (Button)findViewById(R.id.recycle_huishou);
        button_guadan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Check_input()) {
                    sendRequestWithOkHttp();
                }else {
                    Toast.makeText(RecycleActivity.this,"请输入完整",Toast.LENGTH_SHORT).show();

                }
            }
        });
        ImageView recycle_return = (ImageView) findViewById(R.id.recycle_return);
        recycle_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent = new Intent(RecycleActivity.this, MainActivity.class);
              //  startActivity(intent);
                RecycleActivity.this.finish();
            }
        });

    }


    //检查是否输入
    private boolean Check_input(){
        EditText editText = (EditText)findViewById(R.id.recycle_pass);
        Secpwd_recover = editText.getText().toString();
        editText = (EditText)findViewById(R.id.recycle_sale);
        Num_recover = editText.getText().toString();
        if(Secpwd_recover.length() == 0 || Num_recover.length() == 0 ){
            Toast.makeText(RecycleActivity.this,"无输入",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void sendRequestWithOkHttp_getHuishoudata(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d("sdfs","sdfds");
                    RequestBody requestBody = new FormBody.Builder().add("account",Account).build();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(URL_Huishoudata)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();


                    String responseData = response.body().string();
                    //execute JSON

                    String responseJsonData = "["+responseData+"]";
                    parseJSONWithJSONObject_guadandata(responseJsonData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d("sdfs","sdfds");
                    RequestBody requestBody = new FormBody.Builder().add("account",Account).add("num_recover",Num_recover)
                            .add("secpwd_recover",Secpwd_recover).build();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(URL_Huishou)
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

    private String money6_use;
    private String recover_use;

    private void parseJSONWithJSONObject_guadandata(String jsonData){

        try {

            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0 ; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String Temp_Money6 = jsonObject.getString("money6");
                String Temp_Recover = jsonObject.getString("recover");
                money6_use = Temp_Money6;
                recover_use = Temp_Recover;

                Message message = new Message();
                message.what = 3;

                handler.sendMessage(message);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseJSONWithJSONObject(String jsonData){

        try {

            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0 ; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String judge = jsonObject.getString("judge");

                Message message = new Message();
                message.what = 2;
                if(judge.equals("3")){
                    message.obj = "卖出成功";
                    sendRequestWithOkHttp_getHuishoudata();
                }else if(judge.equals("4")){

                    message.obj = "交易密码错误";

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
}
