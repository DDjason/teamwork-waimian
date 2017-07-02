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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wuxudong on 17-5-8.
 */

public class CashActivity extends AppCompatActivity {
    private ImageView cash_return;


    private String money4;

    private String Account = "xbd66";
    private static final String tixiandata_Url = "http://121.126.211.81/Api/Home/Index/tixiandata";
    private static final int Post_tixiandata_Url= 1;
    private static final String Tixian_Url = "http://121.126.211.81/Api/Home/Index/tixian";
    private static final int Post_Tixian_Url = 2;


    private Map<String,String> formbodylist = new HashMap<String, String>();



    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // super.handleMessage(msg);super 代表调用老爹的方法

            switch (msg.what){
                case Post_tixiandata_Url://

                    EditText editText = (EditText)findViewById(R.id.cash_rmb);
                    editText.setText(money4);
                    break;
                case Post_Tixian_Url:


                    break;
                default:
                    break;
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cash);
        cash_return= (ImageView) findViewById(R.id.cash_return);
        cash_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Intent intent = new Intent(CashActivity.this, MainActivity.class);
              //  startActivity(intent);
                CashActivity.this.finish();
            }
        });

        formbodylist.clear();
        formbodylist.put("account",Account);
        sendRequestWithOkHttp(tixiandata_Url,Post_tixiandata_Url);

        Button cash_btn = (Button)findViewById(R.id.cash_btn);
        cash_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                formbodylist.clear();

                EditText my_editText = (EditText)findViewById(R.id.cash_name);
                String tempvalue = my_editText.getText().toString();
                if(tempvalue.length() == 0){
                    Toast.makeText(CashActivity.this,"请填写完整",Toast.LENGTH_SHORT).show();
                    return;
                }
                //formbodylist.put("cash_name",tempvalue);

                my_editText = (EditText)findViewById(R.id.cash_qq);
                tempvalue = my_editText.getText().toString();
                if(tempvalue.length() == 0){
                    Toast.makeText(CashActivity.this,"请填写完整",Toast.LENGTH_SHORT).show();
                    return;
                }
                //formbodylist.put("tixian_qq",my_editText.getText().toString());

                my_editText = (EditText)findViewById(R.id.order_num);
                tempvalue = my_editText.getText().toString();
                if(tempvalue.length() == 0){
                    Toast.makeText(CashActivity.this,"请填写完整",Toast.LENGTH_SHORT).show();
                    return;
                }
                formbodylist.put("tixian_price",my_editText.getText().toString());

                my_editText = (EditText)findViewById(R.id.cash_pass);
                tempvalue = my_editText.getText().toString();
                if(tempvalue.length() == 0){
                    Toast.makeText(CashActivity.this,"请填写完整",Toast.LENGTH_SHORT).show();
                    return;
                }
                formbodylist.put("tixian_secpwd",my_editText.getText().toString());
                my_editText = (EditText)findViewById(R.id.cash_rmb);
                tempvalue = my_editText.getText().toString();
                if(tempvalue.length() == 0){
                    Toast.makeText(CashActivity.this,"请填写完整",Toast.LENGTH_SHORT).show();
                    return;
                }
                formbodylist.put("tixian_zf_tj4",my_editText.getText().toString());

                formbodylist.put("account",Account);
                sendRequestWithOkHttp(Tixian_Url,Post_Tixian_Url);



            }
        });
    }



    private void sendRequestWithOkHttp(final String my_url,final int msgwat){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    FormBody.Builder builder =  new FormBody.Builder();
                    Iterator<Map.Entry<String,String>> iterator = formbodylist.entrySet().iterator();
                    while (iterator.hasNext()){
                        Map.Entry<String,String> entry = iterator.next();
                        builder.add(entry.getKey(),entry.getValue());
                    }
                    RequestBody requestBody = builder.build();

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(my_url)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();


                    String responseData = response.body().string();
                    //execute JSON

                    String responseJsonData = "[" + responseData + "]";
                    switch (msgwat){
                        case Post_tixiandata_Url:

                            Log.d("111111",responseJsonData);
                            parseJSONWithJSONObject(responseJsonData);
                            break;
                        case Post_Tixian_Url:
                            Log.d("111111",responseJsonData);

                            break;
                        default:
                            Log.d("msgwat",responseJsonData);
                            break;
                    }
                    //parseJSONWithJSONObject(responseJsonData);
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
                money4 = jsonObject.getString("money4");
                Message message = new Message();
                message.what = Post_tixiandata_Url;
                handler.sendMessage(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
