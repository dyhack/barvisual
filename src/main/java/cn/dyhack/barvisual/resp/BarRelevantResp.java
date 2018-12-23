package cn.dyhack.barvisual.resp;

public class BarRelevantResp {
    
    //网吧Id
    private String barId;
    //网上在当前时间段上网人数
    private long count;
    //流动人口人数
    private long float_count;
    
    public BarRelevantResp()
    {
        
    }
    public BarRelevantResp(String barId,long count,long float_count)
    {
        this.barId = barId;
        this.count = count;
        this.float_count = float_count;
    }
    public String getBarId() {
        return barId;
    }
    public void setBarId(String barId) {
        this.barId = barId;
    }
    public long getCount() {
        return count;
    }
    public void setCount(long count) {
        this.count = count;
    }
    public long getFloat_count() {
        return float_count;
    }
    public void setFloat_count(long float_count) {
        this.float_count = float_count;
    }
    
    

}
