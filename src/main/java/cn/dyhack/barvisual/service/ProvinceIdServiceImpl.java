package cn.dyhack.barvisual.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.logging.log4j.message.AsynchronouslyFormattable;
import org.springframework.stereotype.Service;

import cn.dyhack.barvisual.dao.ProvinceIdMapperImpl;
import cn.dyhack.barvisual.pojo.tables.pojos.ProvinceId;

/**
 * 
 * provinceId操作类
 * provinceId表里对应的省份和身份证开头前2位
 * @author zhangke
 * @since 0.0.1
 */
@Service("ProvinceIdServiceImpl")
public class ProvinceIdServiceImpl {
    
    @Autowired
    ProvinceIdMapperImpl provinceIdMapper;
    public List<ProvinceId> getAll()
    {
        return provinceIdMapper.selectAll();
    }

}
