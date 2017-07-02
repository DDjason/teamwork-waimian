package com.example.wuxudong.xun.list;

/**
 * Created by wuxudong on 17-5-8.
 */

public class History {
    public String getHistory_price() {
        return history_price;
    }

    public History(String _history_price,String _history_number,String _history_real,String _history_time,String _history_status){
        history_price  = _history_price;
        history_number = _history_number;
        history_real   = _history_real;
        history_time   = _history_time;
        history_status = _history_status;
    }
    public void setHistory_price(String history_price) {
        this.history_price = history_price;
    }

    public String getHistory_number() {
        return history_number;
    }

    public void setHistory_number(String history_number) {
        this.history_number = history_number;
    }

    public String getHistory_real() {
        return history_real;
    }

    public void setHistory_real(String history_real) {
        this.history_real = history_real;
    }

    public String getHistory_time() {
        return history_time;
    }

    public void setHistory_time(String history_time) {
        this.history_time = history_time;
    }

    public String getHistory_status() {
        return history_status;
    }

    public void setHistory_status(String history_status) {
        this.history_status = history_status;
    }

    private String history_price;
    private String history_number;
    private String history_real;
    private String history_time;
    private String history_status;

}
