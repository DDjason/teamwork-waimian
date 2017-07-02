package com.example.wuxudong.xun;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wuxudong.xun.adapter.HistoryAdapter;
import com.example.wuxudong.xun.adapter.buylistAdapter;
import com.example.wuxudong.xun.list.Ab;
import com.example.wuxudong.xun.list.History;
import com.example.wuxudong.xun.list.buyList;

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
 * Created by wuxudong on 17-5-8.
 */

public class HistoryActivity extends AppCompatActivity {
    private ImageView history_return;

    private Map<String,String> formbodylist = new HashMap<String, String>();

    private List<History> historiesList= new ArrayList<History>();

    private String flag;


    private String History_Url = "http://121.126.211.81/Api/Home/Index/change";
    private static final int Post_History_Url = 1;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case Post_History_Url://返回处理初始UI
                    HistoryAdapter adapter = new HistoryAdapter(HistoryActivity.this,R.layout.history_item,historiesList);
                    ListView listView = (ListView)findViewById(R.id.history_list);
                    listView.setAdapter(adapter);
                    break;
                case 2://返回错误信息

                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        history_return= (ImageView) findViewById(R.id.history_return);
        history_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Intent intent = new Intent(CashActivity.this, MainActivity.class);
                //  startActivity(intent);
                HistoryActivity.this.finish();
            }
        });

        sendRequestWithOkHttp(History_Url,Post_History_Url);
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
                        case Post_History_Url:
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

            historiesList.clear();
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0 ; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                historiesList.add(new History(jsonObject.getString("price")
                        ,jsonObject.getString("num")
                        ,jsonObject.getString("total")
                        ,jsonObject.getString("zf_time")
                        ,jsonObject.getString("type")));
            }
            Message message = new Message();
            message.what = Post_History_Url;

            handler.sendMessage(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
