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

public class RestingorderActivity extends AppCompatActivity {

    private String URL_Guadandata = "http://121.126.211.81/Api/Home/Index/guadandata";
    private String URL_Guadan= "http://121.126.211.81/Api/Home/Index/guadan";

    private String Account = "xbd66";
    private String Mairu_num;
    private String Mairu_price;
    private String Secpwd;


    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 2://返回挂单信息
                    Toast.makeText(RestingorderActivity.this,msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    TextView textView = (TextView)findViewById(R.id.optimalprice);
                    textView.setText(max_price_use);
                    textView = (TextView)findViewById(R.id.order_rmb);
                    textView.setText(money4_use);
                    break;
                default:
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restingorder);

        sendRequestWithOkHttp_getGuadandata();

        Button button_guadan = (Button)findViewById(R.id.Restingorder_gudan);
        button_guadan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Check_input()) {
                    sendRequestWithOkHttp();
                }else {
                    Toast.makeText(RestingorderActivity.this,"请输入完整",Toast.LENGTH_SHORT).show();

                }
            }
        });
        ImageView restingorder_return = (ImageView) findViewById(R.id.restingorder_return);
        restingorder_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(RestingorderActivity.this, MainActivity.class);
              //  startActivity(intent);
                RestingorderActivity.this.finish();
            }
        });
    }
    //检查是否输入
    private boolean Check_input(){
        EditText editText = (EditText)findViewById(R.id.order_price);
        Mairu_price = editText.getText().toString();
        editText = (EditText)findViewById(R.id.order_num);
        Mairu_num = editText.getText().toString();
        editText = (EditText)findViewById(R.id.order_pass);
        Secpwd = editText.getText().toString();
        if(Secpwd.length() == 0 || Mairu_num.length() == 0 || Mairu_price.length() == 0){
            Toast.makeText(RestingorderActivity.this,"无输入",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void sendRequestWithOkHttp_getGuadandata(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d("sdfs","sdfds");
                    RequestBody requestBody = new FormBody.Builder().add("account",Account).build();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(URL_Guadandata)
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
                    RequestBody requestBody = new FormBody.Builder().add("account",Account).add("secpwd",Secpwd).add("mairu_num",Mairu_num).add("mairu_price",Mairu_price).build();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(URL_Guadan)
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

    private String money4_use;
    private String max_price_use;

    private void parseJSONWithJSONObject_guadandata(String jsonData){

        try {

            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0 ; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String Temp_Money4 = jsonObject.getString("money4");
                String Temp_MaxPrice = jsonObject.getString("max_price");
                money4_use = Temp_Money4;
                max_price_use = Temp_MaxPrice;

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
                if(judge.equals("2")){

                    message.obj = "挂单成功";
                    sendRequestWithOkHttp_getGuadandata();
                }else if(judge.equals("3")){
                    message.obj = "余额不足";

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
