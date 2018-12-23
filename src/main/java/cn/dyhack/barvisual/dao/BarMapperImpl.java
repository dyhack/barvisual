package cn.dyhack.barvisual.dao;

import static cn.dyhack.barvisual.pojo.tables.Bars.BARS;
import static cn.dyhack.barvisual.pojo.tables.Total.TOTAL;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.dyhack.barvisual.pojo.tables.pojos.Bars;
import cn.dyhack.barvisual.pojo.tables.pojos.Total;
import cn.dyhack.barvisual.pojo.tables.records.BarsRecord;

/**
 * 
 * bar表数据库操作类
 *
 * @author zhangke
 */
@Repository("BarMapperImpl")
public class BarMapperImpl extends CommonMapper {

    @Autowired
    DSLContext dsl;
    
    public List<Bars> selectAll()
    {
        return dsl.selectFrom(BARS).fetchInto(Bars.class);
    }
    
    public List<Bars> selectByCondition(String filter)
    {
        SelectQuery<BarsRecord> barRecords = dsl.selectQuery(BARS);
        barRecords.addConditions(DSL.condition(filter));
        return barRecords.fetch().into(Bars.class);
    }

    public Bars selectBarById(String barId) {
        return dsl.selectFrom(BARS)
                .where(BARS.ID.eq(barId))
                .fetchOne()
                .into(Bars.class);
    }
    
     public int countBars()
     {
         return dsl.fetchCount(BARS);
     }
     
     public List<Bars> selectBarByLongitudeAndLatitude(double longitude,double latitude)
     {
         Result<BarsRecord>barrecords = dsl.selectFrom(BARS).where(BARS.LATITUDE.eq(latitude).and(BARS.LONGITUDE.eq(longitude))).fetch();
         return barrecords.into(Bars.class);
     }
     
     public void updateIllegalBar(String barId)
     {
        int result= dsl.update(BARS).set(BARS.SUSPECT_ID_NUM,BARS.SUSPECT_ID_NUM.add(1)).where(BARS.ID.eq(barId)).execute();
        //网吧信息在上网记录表里面有但是在网吧
        if(result == 0)
        {   
            dsl.insertInto(BARS).set(BARS.ID, barId).set(BARS.NAME,"未登记网吧").execute();
            dsl.update(BARS).set(BARS.SUSPECT_ID_NUM,BARS.SUSPECT_ID_NUM.add(1)).where(BARS.ID.eq(barId)).execute();
        }
     }
     
     public void updateIllegalBar(String barId,int weight)
     {
         int result= dsl.update(BARS).set(BARS.SUSPECT_AUDIT_NUM,BARS.SUSPECT_AUDIT_NUM.add(weight)).where(BARS.ID.eq(barId)).execute();
         //网吧信息在上网记录表里面有但是在网吧
         if(result == 0)
         {   
             dsl.insertInto(BARS).set(BARS.ID, barId).set(BARS.NAME,"未登记网吧").execute();
             dsl.update(BARS).set(BARS.SUSPECT_AUDIT_NUM,BARS.SUSPECT_AUDIT_NUM.add(weight)).where(BARS.ID.eq(barId)).execute();
         }
         
     }
     
     public List<Bars> selectByCondition(String fields,String  filters,Integer offset,Integer limit,String sorts)
     {
       return selectRecords(BARS, fields, filters, offset, limit, sorts,null).into(Bars.class);
     }
    
}
