package cn.dyhack.barvisual.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dyhack.barvisual.dao.RecordPersonMapperImpl;
import cn.dyhack.barvisual.pojo.tables.pojos.RecordsPersons;

@Service("RecordsPersonsServiceImpl")
public class RecordsPersonsServiceImpl {
    
    @Autowired
    RecordPersonMapperImpl recordPersonMapper;
    public List<RecordsPersons> selectAll()
    {
        return recordPersonMapper.selectAll();
    }

}
