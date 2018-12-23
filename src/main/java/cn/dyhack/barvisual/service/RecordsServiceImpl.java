package cn.dyhack.barvisual.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dyhack.barvisual.dao.RecordMapperImpl;
import cn.dyhack.barvisual.pojo.tables.pojos.Records;

/**
 * 
 * 操作record表的服务层类
 *
 * @author zhangke
 * @since 0.0.1
 */
@Service("RecordsServiceImpl")
public class RecordsServiceImpl {
    
    @Autowired
    RecordMapperImpl recordMapper;
    
    
    public List<Records> selectByTime(String time)
    {
        
        
        return recordMapper.selectByCondition(time);
    }
     
    /**
     * 获取各个时间段上网的人数和具体的人
     * 时间间隔为3个小时。
     */
   /* public Map<String,List<Records>> findNetizen()
    {
        //String[] timeInterval = new String;
    }*/

    public void fixDirtyData()
    {
        System.err.println(selectByTime("onlinetime>=offlinetime").size());
        recordMapper.batchDelete(selectByTime("onlinetime>=offlinetime"));
   
    }
}
