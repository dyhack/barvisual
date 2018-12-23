package cn.dyhack.barvisual.resp;

import java.util.HashMap;

/**
 * 
 * 返回对应的省份和流动人口的数量
 *
 * @author zhangke
 * @since 0.0.5
 */
public class ProvinceFloatCountResp {

    private HashMap<String, Integer> provinceFloatCount = new HashMap<>();

    public HashMap<String, Integer> getProvinceFloatCount() {
        return provinceFloatCount;
    }

    public void setProvinceFloatCount(HashMap<String, Integer> provinceFloatCount) {
        this.provinceFloatCount = provinceFloatCount;
    }

}
