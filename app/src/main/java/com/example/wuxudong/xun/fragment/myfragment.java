package com.example.wuxudong.xun.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuxudong.xun.LoginActivity;
import com.example.wuxudong.xun.R;
import com.example.wuxudong.xun.SignupActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wuxudong on 17-3-30.
 */

public class myfragment extends Fragment implements View.OnClickListener{
    private TextView login;
    private TextView register;
    private TextView Rmb_num;
    private TextView A_num;
    private TextView Da_num;
    private String moneychina,moneya,moneydongjie ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.myfragment, container,false);
        login = (TextView) view.findViewById(R.id.login);
        login.setOnClickListener(this);
        register = (TextView) view.findViewById(R.id.register);
        register.setOnClickListener(this);
        Rmb_num = (TextView) view.findViewById(R.id.Rmb_num);
        A_num = (TextView) view.findViewById(R.id.A_num);
        Da_num = (TextView) view.findViewById(R.id.Da_num);
        sendRequestWithOkHttp();
        return view;
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.register:
                Intent intent2 = new Intent(getActivity(), SignupActivity.class);
                startActivity(intent2);
                break;
            default:

                break;
        }
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1://返回处理初始UI
                    Rmb_num.setText(moneychina);
                    A_num.setText(moneya);
                    Da_num.setText(moneydongjie);
                    break;
                case 2://返回错误信息
                    Toast.makeText(getActivity(),msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //Log.d("sdfs","sdfds");
                    RequestBody requestBody = new FormBody.Builder().add("account","xbd00").build();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://121.126.211.81/Api/Home/Index/qianbao")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();


                    String responseData = response.body().string();
                    //execute JSON

                    String responseJsonData = "["+responseData+"]";
                    Log.d("asdad",responseData);
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
                moneydongjie = jsonObject.getString("dongjie");
                moneya = jsonObject.getString("money6");
                moneychina = jsonObject.getString("money4");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }
}
