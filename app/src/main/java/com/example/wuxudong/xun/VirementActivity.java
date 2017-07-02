package com.example.wuxudong.xun;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by wuxudong on 17-4-18.
 */

public class VirementActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner virementSpin;
    private List<String>virementypeList;
    private ArrayAdapter virementAdapter;

    private static final int Type_Zhuanzhang = 2;
    private static final int Type_Zhuanzhangdata = 3;
    private String Url_Zhuanzhang = "http://121.126.211.81/Api/Home/Index/zhuanzhang";
    private String Url_Zhuanzhangdata = "http://121.126.211.81/Api/Home/Index/zhuanzhangdata";


    private String Account = "xbd66";
    private String Money6_use;
    private String Virement_pass;
    private String Virement_num;
    private String Virement_account;
    private String Virement_A;
    private String judge_use;

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case Type_Zhuanzhang:

                    String result_show = "";
                    if("1".equals(judge_use)){
                        result_show = "不能转给自己";
                    }else if("2".equals(judge_use)) {
                        result_show = "二级密码输入不正确";
                    }else if("3".equals(judge_use)){
                        result_show = "转账金额必须大于零";
                    }else if("4".equals(judge_use)){
                        result_show = "余额不足,请充值后重试";
                    }else if("5".equals(judge_use)){
                        result_show = "该会员不存在";
                    }else if( "6".equals(judge_use)){
                        result_show = "转账成功";
                    }
                    Toast.makeText(VirementActivity.this,result_show ,Toast.LENGTH_SHORT).show();
                    break;
                case Type_Zhuanzhangdata:
                    TextView textView = (TextView)findViewById(R.id.virement_A);
                    textView.setText(Money6_use);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.virement);
        sendRequestWithOkHttp(Type_Zhuanzhangdata);
        virementSpin = (Spinner)findViewById(R.id.virement_type);
        virementypeList = new ArrayList<String>();
        virementypeList.add("A币");
        virementypeList.add("B币");
        virementypeList.add("C币");
        // 2. 新建ArrayAdapter
        virementAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, virementypeList);

        // 3. 设置下拉菜单的样式
        virementAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // 4. 为Spinner加载适配器
        virementSpin.setAdapter(virementAdapter);

        // 5. 为Spinner设置监听器
        virementSpin.setOnItemSelectedListener(this);
        Button button = (Button)findViewById(R.id.Virement_sure);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Check_input()){
                    sendRequestWithOkHttp(Type_Zhuanzhang);
                }
            }
        });
        ImageView virement_return = (ImageView) findViewById(R.id.virement_return);
        virement_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent = new Intent(VirementActivity.this, MainActivity.class);
             //   startActivity(intent);
                VirementActivity.this.finish();
            }
        });
    }


    //检查是否输入
    private boolean Check_input(){
        EditText editText = (EditText)findViewById(R.id.virement_pass);
        Virement_pass = editText.getText().toString();
        editText = (EditText)findViewById(R.id.virement_num);
        Virement_num = editText.getText().toString();
        editText = (EditText)findViewById(R.id.virement_account);
        Virement_account = editText.getText().toString();
        TextView textView;
        textView = (TextView)findViewById(R.id.virement_A);
        Virement_A = editText.getText().toString();
        if(Virement_A.length() == 0 || Virement_account.length() == 0 || Virement_num.length() == 0 || Virement_pass.length() == 0){
            Toast.makeText(VirementActivity.this,"无输入",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void sendRequestWithOkHttp(final int _type){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.d("sdfs","sdfds");
                    RequestBody requestBody;
                    String _url;

                    if(_type==Type_Zhuanzhangdata) {
                        _url = Url_Zhuanzhangdata;
                        requestBody = new FormBody.Builder().add("account", Account).build();
                    }else{
                        _url = Url_Zhuanzhang;
                        requestBody = new FormBody.Builder()
                                .add("account", Account)
                                .add("zhuanzhang_account", Virement_account)
                                .add("zhuanzhang_secpwd", Virement_pass)
                                .add("zhuanzhang_price", Virement_num)
                                .add("zhuanzhang_money", Virement_A).build();
                    }


                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(_url)
                            .post(requestBody)
                            .build();


                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    //execute JSON
                    String responseJsonData = "["+responseData+"]";

                    if(_type==Type_Zhuanzhang) {
                        parseJSONWithJSONObject(responseJsonData);
                    }else{
                        parseJSONWithJSONObject_Zhuanzhangdata(responseJsonData);
                    }

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


                judge_use = judge;
                Message message = new Message();
                message.what = Type_Zhuanzhang;

                Log.d("asda",jsonData);

                handler.sendMessage(message);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseJSONWithJSONObject_Zhuanzhangdata(String jsonData){

        try {

            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0 ; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String Temp_Money6 = jsonObject.getString("money6");
                Money6_use = Temp_Money6;
                Message message = new Message();
                message.what = Type_Zhuanzhangdata;
                handler.sendMessage(message);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}