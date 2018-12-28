package cn.dyhack.barvisual.resp;

public class InternetUsersCount {
    
    private long startTime;
    private long endTime;
    private long count;
    private long unadult_count;
    public InternetUsersCount()
    {
        
    }
    public InternetUsersCount(long startTime,long endTime,long count, long unadult_count)
    {
        this.startTime = startTime;
        this.endTime = endTime;
        this.count = count;
        this.unadult_count = unadult_count;
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
	public long getUnadult_count() {
		return unadult_count;
	}
	public void setUnadult_count(long unadult_count) {
		this.unadult_count = unadult_count;
	}
    

}
