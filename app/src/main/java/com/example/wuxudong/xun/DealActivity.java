package com.example.wuxudong.xun;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.wuxudong.xun.fragment.buyfragment;
import com.example.wuxudong.xun.fragment.salefragment;

/**
 * Created by wuxudong on 17-4-15.
 */

public class DealActivity extends AppCompatActivity implements View.OnClickListener {


    private String flaguse = "3";

    public String get_Flaguse(){
        return flaguse;
    }

    private Button buy;
    private Button sale;
    private ImageView deal_return;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dealcenter);

        buy = (Button) findViewById(R.id.buy);
        sale = (Button) findViewById(R.id.sale);
        deal_return = (ImageView) findViewById(R.id.deal_return);
        buyfragment buybuy = new buyfragment();
        FragmentManager fragmentManager1 = getFragmentManager();
        FragmentTransaction transaction1 = fragmentManager1.beginTransaction();
        transaction1.replace(R.id.dfragment, buybuy);
        transaction1.commit();

        buy.setOnClickListener(this);
        sale.setOnClickListener(this);
        deal_return.setOnClickListener(this);
    }

    @Override
    public void onClick (View v){
        switch (v.getId()){
            case R.id.sale:
               // salefragment salesale = new salefragment();
               // FragmentManager fragmentManager = getFragmentManager();
               // FragmentTransaction transaction = fragmentManager.beginTransaction();
               // transaction.replace(R.id.dfragment, salesale);
               // transaction.commit();

                buyfragment buybuy1 = new buyfragment();

                flaguse = "1";

                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction transaction2 = fragmentManager2.beginTransaction();
                transaction2.replace(R.id.dfragment, buybuy1);
                transaction2.commit();
                break;
            case R.id.buy:
                buyfragment buybuy = new buyfragment();

                flaguse = "3";

                FragmentManager fragmentManager1 = getFragmentManager();
                FragmentTransaction transaction1 = fragmentManager1.beginTransaction();
                transaction1.replace(R.id.dfragment, buybuy);
                transaction1.commit();
                break;
            case R.id.deal_return:
              //  Intent intent = new Intent(DealActivity.this,MainActivity.class);
              //  startActivity(intent);
                DealActivity.this.finish();
                break;
            default:
                break;

        }
    }
}
