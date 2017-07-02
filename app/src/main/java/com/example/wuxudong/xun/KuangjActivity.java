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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuxudong.xun.adapter.AbAdapter;
import com.example.wuxudong.xun.list.Ab;

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
 * Created by wuxudong on 17-5-6.
 */

public class KuangjActivity extends AppCompatActivity {



    private String Account = "xbd66";
    private String Jihuoma;
    private static final String Kuangj_Url = "http://121.126.211.81/Api/Home/Index/kuangjiinfo";
    private static final int Post_Kuangj_Url = 1;
    private static final String Jihuoma_Url = "http://121.126.211.81/Api/Home/Index/activate";
    private static final int Post_Jihuoma_Url = 2;


    private Map<String,String> formbodylist = new HashMap<String, String>();
    private String kuangji1;
    private String kuangji2;
    private String kuangji3;
    private String kuangji4;
    private String judge;
    private String num;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // super.handleMessage(msg);super 代表调用老爹的方法

            switch (msg.what){
                case Post_Kuangj_Url://
                    TextView textView = (TextView)findViewById(R.id.kuangji1);
                    textView.setText(kuangji1);
                    textView = (TextView)findViewById(R.id.kuangji2);
                    textView.setText(kuangji2);
                    textView = (TextView)findViewById(R.id.kuangji3);
                    textView.setText(kuangji3);
                    textView = (TextView)findViewById(R.id.kuangji4);
                    textView.setText(kuangji4);
                    textView = (TextView)findViewById(R.id.kuangj2_number);
                    textView.setText(num);
                    break;
                case Post_Jihuoma_Url:

                    if(judge.equals("1")){
                        Toast.makeText(KuangjActivity.this,"该激活码已经使用过了",Toast.LENGTH_SHORT).show();
                    }else if(judge.equals("2")){
                        Toast.makeText(KuangjActivity.this,"该激活码已经失效",Toast.LENGTH_SHORT).show();
                    }else if(judge.equals("3")){
                        Toast.makeText(KuangjActivity.this,"激活成功",Toast.LENGTH_SHORT).show();
                        formbodylist.clear();
                        formbodylist.put("account",Account);
                        sendRequestWithOkHttp(Kuangj_Url,Post_Kuangj_Url);
                    }else if(judge.equals("4")){
                        Toast.makeText(KuangjActivity.this,"激活失败",Toast.LENGTH_SHORT).show();
                    }else if(judge.equals("5")){
                        Toast.makeText(KuangjActivity.this,"该激活码不存在",Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kuangj);
        ImageView kuangj_return = (ImageView) findViewById(R.id.kuangj_return);
        kuangj_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent(KuangjActivity.this, MainActivity.class);
              //  startActivity(intent);
                KuangjActivity.this.finish();
            }
        });


        formbodylist.clear();
        formbodylist.put("account",Account);
        sendRequestWithOkHttp(Kuangj_Url,Post_Kuangj_Url);

        Button button_kuangj_register = (Button) findViewById(R.id.kuangj_register);
        button_kuangj_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)findViewById(R.id.kuanj_sign);
                String get_jihuoma = editText.getText().toString();
                if(get_jihuoma.length() == 0){
                    Toast.makeText(KuangjActivity.this,"请输入激活码",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Jihuoma = get_jihuoma;
                    formbodylist.clear();
                    formbodylist.put("account",Account);
                    formbodylist.put("jihuoma",Jihuoma);
                    sendRequestWithOkHttp(Jihuoma_Url,Post_Jihuoma_Url);
                }
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
                        case Post_Kuangj_Url:

                            Log.d("msgwat",responseJsonData);
                            parseJSONWithJSONObject(responseJsonData);
                            break;
                        case Post_Jihuoma_Url:

                            parseJSONWithJSONObject_Jihuoma(responseJsonData);
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
    private void parseJSONWithJSONObject_Jihuoma(String jsonData){


        try {

            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0 ; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String _judge = jsonObject.getString("judge");
                judge = _judge;
                Message message = new Message();
                message.what = Post_Jihuoma_Url;
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
                String _num = jsonObject.getString("num");
                String _loading = jsonObject.getString("loading");
                String _zongliang1 = jsonObject.getString("zongliang1");
                String _zongliang2 = jsonObject.getString("zongliang2");
                String _zongliang3 = jsonObject.getString("zongliang3");
                String _zongliang4 = jsonObject.getString("zongliang4");

                num = _num;
                kuangji1 = _zongliang1;
                kuangji2 = _zongliang2;
                kuangji3 = _zongliang3;
                kuangji4 = _zongliang4;


                Message message = new Message();
                message.what = Post_Kuangj_Url;
                handler.sendMessage(message);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}