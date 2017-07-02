package com.example.wuxudong.xun.list;

/**
 * Created by wuxudong on 17-4-16.
 */

public class buyList {
    public String getBuy_num() {
        return buy_num;
    }

    public void setBuy_num(String buy_num) {
        this.buy_num = buy_num;
    }

    public String getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(String buy_time) {
        this.buy_time = buy_time;
    }

    public String getBuy_single() {
        return buy_single;
    }

    public void setBuy_single(String buy_single) {
        this.buy_single = buy_single;
    }

    public String getBuy_sum() {
        return buy_sum;
    }

    public void setBuy_sum(String buy_sum) {
        this.buy_sum = buy_sum;
    }

    public String getBuy_volume() {
        return buy_volume;
    }

    public void setBuy_volume(String buy_volume) {
        this.buy_volume = buy_volume;
    }

    public String getBuy_status() {
        return buy_status;
    }

    public void setBuy_status(String buy_status) {
        this.buy_status = buy_status;
    }
    public buyList(String buy_num, String buy_time,String buy_single,
                   String buy_sum,String buy_volume,String buy_status){
        setBuy_num(buy_num);
        setBuy_time(buy_time);
        setBuy_single(buy_single);
        setBuy_num(buy_num);
        setBuy_volume(buy_volume);
        setBuy_status(buy_status);
    }
    public buyList(){

    };
    private String buy_num, buy_time,buy_single,
    buy_sum,buy_volume,buy_status;


}
