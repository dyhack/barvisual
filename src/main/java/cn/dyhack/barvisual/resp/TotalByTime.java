package cn.dyhack.barvisual.resp;

import cn.dyhack.barvisual.pojo.tables.pojos.Total;

public class TotalByTime extends Total {
    
    private long internet_time;
    
    
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
    

}
