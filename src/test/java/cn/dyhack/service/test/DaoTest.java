package cn.dyhack.service.test;

import static cn.dyhack.barvisual.pojo.tables.Bars.BARS;

import java.util.List;

import org.jooq.DSLContext;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dyhack.barvisual.dao.RecordMapperImpl;
import cn.dyhack.barvisual.pojo.tables.pojos.Records;
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class DaoTest {
    
    @Autowired
    RecordMapperImpl recordMapperImpl;
    @Autowired
    ObjectMapper ObjectMapper;
    
    @Autowired
    DSLContext dsl;
    @Test
    public void recordtest() {
        try {
            System.out.println(ObjectMapper.writeValueAsString(recordMapperImpl.selectPersonsByBarId("50010810000100")));;
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void selectQueryTest()
    {   
        List<Records> result = recordMapperImpl.selectByCondition("onlinetime >= offlinetime");
        System.out.println(result.size());
    }
    @Test
    @Ignore
    public void insertTest()
    {
        dsl.insertInto(BARS).set(BARS.ID, "00000000000000").set(BARS.NAME,"未登记网吧").execute();
    }

    
    

}
