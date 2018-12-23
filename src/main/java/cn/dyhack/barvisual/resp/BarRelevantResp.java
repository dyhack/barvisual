package cn.dyhack.barvisual.resp;

public class BarRelevantResp {
    
    //网吧Id
    private String barId;
    //网上在当前时间段上网人数
    private long count;
    //流动人口人数
    private long float_count;
    //未成年
    private long under_age_count;
    
    public BarRelevantResp()
    {
        
    }
    public BarRelevantResp(String barId,long count,long float_count)
    {
        this.barId = barId;
        this.count = count;
        this.float_count = float_count;
        this.under_age_count = 0;
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
	public long getUnder_age_count() {
		return under_age_count;
	}
	public void setUnder_age_count(long under_age_count) {
		this.under_age_count = under_age_count;
	}

}
