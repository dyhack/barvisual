package cn.dyhack.barvisual.resp;

import com.fasterxml.jackson.core.sym.Name;

import cn.dyhack.barvisual.pojo.tables.pojos.Total;

public class TotalByTime extends Total {
    
    //上网时长
    private long internet_time;
    
    //网吧名称
    private String barName;
    
    private Long age;
    

    public long getInternet_time() {
        return internet_time;
    }

    public void setInternet_time(long internet_time) {
        this.internet_time = internet_time;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public void setAge(Long age) {
        this.age = age;
    }
    

}
