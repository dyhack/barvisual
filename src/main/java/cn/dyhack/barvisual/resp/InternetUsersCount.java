package cn.dyhack.barvisual.resp;

public class InternetUsersCount {
    
    private long startTime;
    private long endTime;
    private long count;
    public InternetUsersCount()
    {
        
    }
    public InternetUsersCount(long startTime,long endTime,long count)
    {
        this.startTime = startTime;
        this.endTime = endTime;
        this.count = count;
    }
    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    public long getEndTime() {
        return endTime;
    }
    public void setEndTime(long endtTime) {
        this.endTime = endtTime;
    }
    public long getCount() {
        return count;
    }
    public void setCount(long count) {
        this.count = count;
    }
    

}
