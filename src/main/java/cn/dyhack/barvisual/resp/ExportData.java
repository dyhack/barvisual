package cn.dyhack.barvisual.resp;

/**
 * 
 * 
 * 导出数据结果类
 *
 * @author zhangke
 * @since 0.0.6
 */
public class ExportData {
    
    //网吧ID
    private String barId;
    //网吧名称
    private String barName;
    //未成年上网人员数量
    private int underAudit;
    //可疑身份证数量
    private int suspectIdNums;
    public String getBarId() {
        return barId;
    }
    public void setBarId(String barId) {
        this.barId = barId;
    }
    public String getBarName() {
        return barName;
    }
    public void setBarName(String barName) {
        this.barName = barName;
    }
    public int getUnderAudit() {
        return underAudit;
    }
    public void setUnderAudit(int underAudit) {
        this.underAudit = underAudit;
    }
    public int getSuspectIdNums() {
        return suspectIdNums;
    }
    public void setSuspectIdNums(int suspectIdNums) {
        this.suspectIdNums = suspectIdNums;
    }
    
    

}
