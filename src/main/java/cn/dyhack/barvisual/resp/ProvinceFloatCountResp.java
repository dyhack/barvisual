package cn.dyhack.barvisual.resp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * 返回对应的省份和流动人口的数量
 *
 * @author zhangke
 * @since 0.0.5
 */
public class ProvinceFloatCountResp {

    private List<ProvinceCount> provinceFloatCount = new ArrayList<>();

    public List<ProvinceCount> getProvinceFloatCount() {
        return provinceFloatCount;
    }

    public void setProvinceFloatCount(List<ProvinceCount> provinceFloatCount) {
        this.provinceFloatCount = provinceFloatCount;
    }
    
    
    

   
}
