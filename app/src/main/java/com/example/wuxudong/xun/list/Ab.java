package com.example.wuxudong.xun.list;

/**
 * Created by wuxudong on 17-5-6.
 */

public class Ab {

    public Ab(String _id,String _changeMoney,String _changeTime,String _intro){
        id =_id;
        changeMoney = _changeMoney;
        changeTime = _changeTime;
        intro = _intro;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChangeMoney() {
        return changeMoney;
    }

    public void setChangeMoney(String changeMoney) {
        this.changeMoney = changeMoney;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    private String id;
    private String changeMoney;
    private String changeTime;
    private String intro;

}
