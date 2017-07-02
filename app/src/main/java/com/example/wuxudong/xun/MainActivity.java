package com.example.wuxudong.xun;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wuxudong.xun.fragment.mainfragment;
import com.example.wuxudong.xun.fragment.myfragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private String strlong = "";
    private TimerTask task;
    private String recover,sum_num,maxprice;
    private LinearLayout mine;
    private LinearLayout menu;
    private String str="";
    public static final int UPDATE_TEXT = 1;
    public static final int UPDATE_TOP_BAR = 2;
    private  Timer timer = new Timer();

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_TEXT:
                    TextView textView;
                    textView = (TextView) findViewById(R.id.get_money);
                    textView.setText(recover);
                    textView = (TextView) findViewById(R.id.get_biggest);
                    textView.setText(maxprice);
                    textView = (TextView) findViewById(R.id.get_num);
                    textView.setText(sum_num);
                    break;
                case UPDATE_TOP_BAR:
                    textView = (TextView) findViewById(R.id.top_bar);
                    textView.setText(strlong);
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //MAINFRAGMENT

        mainfragment menumenu = new mainfragment();
        FragmentManager fragmentManager1 = getFragmentManager();
        FragmentTransaction transaction1 = fragmentManager1.beginTransaction();
        transaction1.replace(R.id.fir_fragment, menumenu);
        transaction1.commit();

        //

        mine = (LinearLayout)findViewById(R.id.mine);
        menu = (LinearLayout)findViewById(R.id.menu);
        mine.setOnClickListener(this);
        menu.setOnClickListener(this);
        //task myTask = new MyTask();
       // myTask.run();
        MyTask();
        sendRequestWithOkHttp();
    }
    @Override
    public void onClick (View v){
        switch (v.getId()){
            case R.id.mine:
                timer.cancel();
                myfragment minemine = new myfragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fir_fragment, minemine);
                transaction.commit();

                //sendRequestWithOkHttp();
                break;
            case R.id.menu:
                timer.cancel();
               // sendRequestWithOkHttp(); //补点交易
                mainfragment menumenu = new mainfragment();
                FragmentManager fragmentManager1 = getFragmentManager();
                FragmentTransaction transaction1 = fragmentManager1.beginTransaction();
                transaction1.replace(R.id.fir_fragment, menumenu);
                transaction1.commit();
//                MyTask myTask = new MyTask();
//                // myTask.run();
//                timer.schedule(myTask, 2000, 2000);
                timer = new Timer();
                MyTask();
                sendRequestWithOkHttp();
                break;
            default:
                break;

        }
    }


    private void MyTask(){
                task = new TimerTask() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATE_TOP_BAR;

                        Random ra = new Random();
                        double show = ra.nextDouble() * 499 + 1;
                        strlong = String.format("%.2f", show);
                        handler.sendMessage(message);

                    }
                };
                timer.schedule(task, 2000, 2000);
    }
    private String struse;
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://121.126.211.81/Api/Home/Index/first")
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
                String recover = jsonObject.getString("recover");
                String maxprice = jsonObject.getString("maxprice");
                String sum_num = jsonObject.getString("sum_num");
               // Log.d("sum_num",sum_num);
                this.recover = recover;
                this.maxprice = maxprice;
                this.sum_num = sum_num;
                Message message = new Message();
                message.what = UPDATE_TEXT;
                handler.sendMessage(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
