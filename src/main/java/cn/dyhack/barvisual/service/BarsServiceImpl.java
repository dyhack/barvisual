package cn.dyhack.barvisual.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dyhack.barvisual.dao.BarMapperImpl;
import cn.dyhack.barvisual.pojo.tables.pojos.Bars;

/**
 * 
 * 网吧信息服务层
 *
 * @author zhangke
 * @since 0.0.1
 */
@Service("BarsServiceImpl")
public class BarsServiceImpl {
    
    @Autowired
    BarMapperImpl barMapper;
    public void updateIllegalBar(String barId)
    {
        barMapper.updateIllegalBar(barId);
        
    }
    
    public void updateIllegalBar(String barId,int weight)
    {
        barMapper.updateIllegalBar(barId,weight);
        
    }
    
    public List<Bars> selectAll()
    {
        return barMapper.selectAll();
    }
    
    public List<Bars> selectByCondition(String filter)
    {
        return barMapper.selectByCondition(filter);
    }
    
    public  List<Bars> getAllBars(String fields,String  filters,Integer  offset,Integer  limit,String sorts)
    {
        return barMapper.selectByCondition(fields, filters, offset, limit, sorts);
    }
    

}
