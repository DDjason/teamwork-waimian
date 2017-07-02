package com.example.wuxudong.xun;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.wuxudong.xun.adapter.AbAdapter;
import com.example.wuxudong.xun.adapter.RmbAdapter;
import com.example.wuxudong.xun.list.Ab;
import com.example.wuxudong.xun.list.Rmb;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wuxudong on 17-5-6.
 */

public class AbActivity extends AppCompatActivity {
    private List<Ab> abList = new ArrayList<Ab>();
    private ImageView ab_return;

    private String Account = "xbd66";
    private static final String Ab_Url = "http://121.126.211.81/Api/Home/Index/Abijilu";
    private static final int Post_Ab_Url = 1;
    private Map<String,String> formbodylist = new HashMap<String, String>();


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // super.handleMessage(msg);super 代表调用老爹的方法

            switch (msg.what){
                case Post_Ab_Url://

                    AbAdapter adapter = new AbAdapter(AbActivity.this,R.layout.ab_item,abList);
                    ListView listView = (ListView) findViewById(R.id.ab_list);
                    listView.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ab);
        ab_return = (ImageView) findViewById(R.id.ab_return);
        ab_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    Intent intent = new Intent(AbActivity.this, MainActivity.class);
               // startActivity(intent);
                AbActivity.this.finish();
            }
        });

        formbodylist.clear();
        formbodylist.put("account",Account);
        sendRequestWithOkHttp(Ab_Url,Post_Ab_Url);
    }


    private void sendRequestWithOkHttp(final String my_url,final int msgwat){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d("sdfs","sdfds");


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

                    String responseJsonData = responseData;
                    switch (msgwat){
                        case Post_Ab_Url:
                            Log.d("msgwat",responseJsonData);
                            parseJSONWithJSONObject(responseJsonData);
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

            abList.clear();
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0 ; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String _id = jsonObject.getString("id");
                String _money = jsonObject.getString("money");
                String _zf_time = jsonObject.getString("zf_time");
                String _note = jsonObject.getString("note");



                abList.add(new Ab(_id,_money,_zf_time,_note));
            }
            Message message = new Message();
            message.what = Post_Ab_Url;

            handler.sendMessage(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
