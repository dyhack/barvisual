package cn.dyhack.barvisual.dao;

import static cn.dyhack.barvisual.pojo.tables.FloatPopulation.FLOAT_POPULATION;
import static cn.dyhack.barvisual.pojo.tables.Total.TOTAL;

import java.util.List;

import javax.annotation.PostConstruct;

import org.jooq.DSLContext;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.dyhack.barvisual.pojo.tables.pojos.Total;
import cn.dyhack.barvisual.pojo.tables.records.TotalRecord;
import cn.dyhack.barvisual.resp.TotalByTime;

@Repository("TotalMapperImpl")
public class TotalMapperImpl extends CommonMapper {

    @Autowired
    DSLContext dsl;  
    

    public List<Total> selectTotalByCondition(String filter) {
        filter = filter.replace('"', ' ');
        SelectQuery<TotalRecord> selectQuery = dsl.selectQuery(TOTAL);
        selectQuery.addConditions(DSL.condition(filter));
        return selectQuery.fetch()
                .into(Total.class);
    }
    
    /**
     * 
     * 查找流动人员
     *
     * @return 流动人员列表对象
     * @author zhangke
     * @since 0.0.1
     */
    public List<Total> selectFloatPopulation() {
        String findFloatPopulationQuery = "SELECT * from total WHERE areaid NOT LIKE '51%' UNION SELECT * FROM total WHERE areaid in (SELECT areaid FROM sichuan_province)";
        return dsl.selectFrom(FLOAT_POPULATION).fetchInto(Total.class);
        //return dsl.fetch(findFloatPopulationQuery).into(Total.class);
        
    }
    
    /**
     * 
     * 在total表里面设置流动人员标志位为1
     *
     * @param Person 当前流动人员
     * @author zhangke
     * @since 0.0.1
     */
    public void setFloat(Total Person)
    {
        String updateQuery = "update total set float_population = 1 where personid ='" + Person.getPersonid() + "'"
                + "and onlinetime = " + Person.getOnlinetime()+" and barid = '"+Person.getBarid()+"'";
       dsl.fetch(updateQuery);
    }
    
    /**
     * 
     * 返回接纳流动人员上网的网吧，并排序
     *
     * @author zhangke
     * @since 0.0.1
     */
    public List<Total> getFloatPopleBar(String fields,String filters,Integer offset,Integer limit,String sorts)
    {
       return selectRecords(TOTAL, fields, filters, offset, limit, sorts,null).into(Total.class);
    }
    
    /**
     * 
     * 查找total所有的数据
     *
     * @return 返回total表里的所有字段
     * @author zhangke
     * @since 0.0.1
     */
    public List<Total> selectAll()
    {
        System.out.println("=============>初始化读取数据");
       return dsl.selectFrom(TOTAL).fetchInto(Total.class);
    }
    
    /**
     * 
     * 查找每个用户上网的时间并返回所有字段
     *
     * @return 加上了上网时间字段的人
     * @author zhangke
     * @since 0.0.1
     */
    public List<TotalByTime> selectAllTime()
    {
        
        String sql = "SELECT  *,(offlinetime-onlinetime) as internet_time FROM total";
        return dsl.fetch(sql).into(TotalByTime.class);
    }
    
    /**
     * 
     * 修复生日日期出错
     *
     * @param personId 用户id
     * @param birthday 生日,时间戳
     * @author zhangke
     * @since 0.0.1
     */
    public void updateBirthday(String personId,long birthday)
    {
        dsl.update(TOTAL).set(TOTAL.BIRTHDAY, birthday).where(TOTAL.PERSONID.eq(personId));
    }
    
    public void insetTotoal(Total t )
    {
       TotalRecord totalRecord =dsl.newRecord(TOTAL);
       totalRecord.from(t);
       totalRecord.insert();
    }
}
