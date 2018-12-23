package cn.dyhack.service.test;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.dyhack.barvisual.dao.PersonMapperImpl;
import cn.dyhack.barvisual.dao.RecordMapperImpl;
import cn.dyhack.barvisual.dao.TotalMapperImpl;
import cn.dyhack.barvisual.pojo.tables.pojos.Persons;
import cn.dyhack.barvisual.pojo.tables.pojos.Records;
import cn.dyhack.barvisual.pojo.tables.pojos.Total;
import cn.dyhack.barvisual.service.BarsServiceImpl;
import cn.dyhack.barvisual.service.PersonsServiceImpl;
import cn.dyhack.barvisual.service.RecordsServiceImpl;
import cn.dyhack.barvisual.service.SusepctsServiceImpl;
import cn.dyhack.barvisual.service.TotalsServiceImpl;
@RunWith(SpringRunner.class)
@SpringBootTest
@Ignore
public class ServiceTest {
   
    @Autowired
    RecordsServiceImpl recordsService;
    @Autowired
    SusepctsServiceImpl susepctsService;
    @Autowired 
    RecordMapperImpl recordMapper;
    @Autowired
    PersonsServiceImpl personsService;
    @Autowired
    PersonMapperImpl personMapper;
    
    @Autowired
    TotalsServiceImpl totalsService;
    @Autowired
    TotalMapperImpl totalMapper;
    
    @Autowired
    BarsServiceImpl barsService;
    @Test
    @Ignore
    public void test() {
       recordsService.selectByTime("onlinetime > 1475286847");
    }
    
    /**
     * 
     * 清楚person表中的上网上线时间比下线时间晚
     *
     * @author zhangke
     */
    @Test
    @Ignore
    public void dirtyFilterTest()
    {
        recordMapper.fixTimeStamp();
       recordsService.fixDirtyData();
    }
    /**
     * 
     * 通过使用成年人身份证来提供上网
     *
     * 判断该身份证只在这家网吧上网，并且上网的时间段是学生放假时间段，当身份证嫌疑数量大于5时为非法网吧
     * @author zhangke
     * @since 0.0.1
     */
    @Test
    @Ignore
    public void getSuspectBarsByAuditTest()
    {
        try {
            susepctsService.getSuspectBars();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 
     * 通过套用成年人身份证来上网
     * @since 0.0.1
     */
    @Test
    @Ignore
    public void getSuspectBarsByOldPeopleTest()
    {
        susepctsService.getSusepctBarsByTime();
    }
    
    /**
     * 找到直接使用未成年身份证上网的网吧(效果不好)
     */
    @Test
    @Ignore
    public void finAccessAuditBar()
    {
        susepctsService.getAuditToCsv();
    }
    
    @Test
    @Ignore
    public void finAccessAuditBarByTotal()
    {
       List<Total> totals = totalsService.selectAudit();
       System.out.println(totals.size());
       for(Total t:totals)
       {
          barsService.updateIllegalBar(t.getBarid(), 1);
       }
        
    }
    
    /**
     * 将total表里面的数据，是流动人口的标记为1，不是的不标记
     * 
     * 流动人口的定义:
     * 考虑到重庆是1997年成为直辖市的。
     * 将前缀为51的人口，后面匹配为四川的去掉，再去掉重庆变成直辖市后的50开头的人。剩下的其余都为流动人口
     */
    @Test
    @Ignore
    public void setFloatPopulation()
    {
        totalsService.setFloatingPopulation();
    }
    
    @Test
    @Ignore
    public void Test1()
    {
        totalsService.selectAllByTimeSplit(1475467871, 1476467871, 1,null);
    }
    
    
    @Test
    @Ignore
    
    public void ageTest()
    {
        totalsService.getAgeFromBirthTime(1480458241,-922608000);
    }

    @Test
    @Ignore
    public void fixBirthday() {
       List<Persons> persons = personsService.selectAll();
       for(Persons p:persons)
       {   
           String personId=p.getId();
           String name = p.getName();
           int sex = p.getSex();
           String areaId= p.getAreaid();
           long birthday = p.getBirthday();
           if(p.getBirthday()<0)
           {
               List<Records> records = recordMapper.selectByCondition(cn.dyhack.barvisual.pojo.tables.Records.RECORDS.PERSONID.eq(p.getId()).toString());
               for(Records r:records)
               {   
                   Total t=new Total();
                   t.setAreaid(Integer.valueOf(areaId));
                   t.setBarid(r.getBarid());
                   t.setBirthday(birthday);
                   t.setName(name);
                   t.setOfflinetime(r.getOfflinetime());
                   t.setOnlinetime(r.getOnlinetime());
                   t.setPersonid(personId);
                   if(sex ==1)
                   {
                       t.setSex("男");
                   }else {
                    t.setSex("女");
                }
                   
                   totalMapper.insetTotoal(t);
               }
           }
       }
    }

}
