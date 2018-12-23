package cn.dyhack.barvisual.resp;

public class AgeAndTimeResp {
    
    private long age;
    private long internetTime;
    private long count;
    public AgeAndTimeResp()
    {}
    public AgeAndTimeResp(long age,long internetTime,long count)
    {
        this.age =age;
        this.internetTime = internetTime;
        this.count = count;
    }
    public long getAge() {
        return age;
    }
    public void setAge(long age) {
        this.age = age;
    }
    public long getInternetTime() {
        return internetTime;
    }
    public void setInternetTime(long internetTime) {
        this.internetTime = internetTime;
    }
    public long getCount() {
        return count;
    }
    public void setCount(long count) {
        this.count = count;
    }
    

}
