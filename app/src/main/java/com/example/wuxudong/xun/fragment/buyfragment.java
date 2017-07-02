package com.example.wuxudong.xun.fragment;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wuxudong.xun.DealActivity;
import com.example.wuxudong.xun.R;
import com.example.wuxudong.xun.adapter.buylistAdapter;
import com.example.wuxudong.xun.list.buyList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wuxudong on 17-4-14.
 */

public class buyfragment extends Fragment{

    private List<buyList> listbuy= new ArrayList<buyList>();
    private View dealview,view;

    private String account = "xbd66";
    private String flag;

    private String Buy_Url = "http://121.126.211.81/Api/Home/Index/mairu";
    private String Sail_Urk = "http://121.126.211.81/Api/Home/Index/maichudata";
    private static final int Post_Buy_Url = 1;


    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1://返回处理初始UI
                   // Toast.makeText(getActivity(),msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    buylistAdapter adapter = new buylistAdapter(getActivity(),R.layout.buy,listbuy);


                    ListView listView = (ListView)view.findViewById(R.id.buy_list);
                    Log.d("asss",Integer.toString(listbuy.size()));
                    listView.setAdapter(adapter);
                    break;
                case 2://返回错误信息
                    Toast.makeText(getActivity(),msg.obj.toString(),Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,
                             Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.buy_list,container,false);

        String _flag = ((DealActivity)getActivity()).get_Flaguse();
        if(_flag.length() != 0) {
            flag = _flag;
        }

        if(flag.equals("3")) {
            sendRequestWithOkHttp(Buy_Url);
        }else{
          //  sendRequestWithOkHttp();
            sendRequestWithOkHttp(Sail_Urk);
        }

        return view;
    }


    private void sendRequestWithOkHttp(final String my_url){
        Log.d("URL",my_url);
        Log.d("flag", flag);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //Log.d("sdfs","sdfds");
                    RequestBody requestBody = new FormBody.Builder().add("account",account).build();
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(my_url)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();


                    String responseData = response.body().string();
                    //execute JSON

                    String responseJsonData =responseData;
                    Log.d("asdad",responseData);
                    parseJSONWithJSONObject(responseJsonData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData){
        //首先清空LIST
        listbuy.clear();

        try {

            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0 ; i < jsonArray.length(); i++){

                buyList bulistuse = new buyList();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                bulistuse.setBuy_num(jsonObject.getString("id"));
                bulistuse.setBuy_single(jsonObject.getString("price"));
                bulistuse.setBuy_sum(jsonObject.getString("total"));
                bulistuse.setBuy_time(jsonObject.getString("zf_time"));
                bulistuse.setBuy_volume(jsonObject.getString("num"));
                bulistuse.setBuy_status(jsonObject.getString("type"));

                if(bulistuse.getBuy_status().equals(flag)) {
                    listbuy.add(bulistuse);
                }
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
