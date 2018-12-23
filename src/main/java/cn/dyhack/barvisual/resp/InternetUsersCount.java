package cn.dyhack.barvisual.resp;

public class InternetUsersCount {
    
    private long startTime;
    private long endtTime;
    private long count;
    public InternetUsersCount()
    {
        
    }
    public InternetUsersCount(long startTime,long endTime,long count)
    {
        this.startTime = startTime;
        this.endtTime = endTime;
        this.count = count;
    }
    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public long getEndtTime() {
        return endtTime;
    }
    public void setEndtTime(long endtTime) {
        this.endtTime = endtTime;
    }
    public long getCount() {
        return count;
    }
    public void setCount(long count) {
        this.count = count;
    }
    

}
