package cn.dyhack.barvisual.dao;

import static cn.dyhack.barvisual.pojo.tables.Records.RECORDS;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.DeleteQuery;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dyhack.barvisual.pojo.tables.Persons;
import cn.dyhack.barvisual.pojo.tables.pojos.Records;
import cn.dyhack.barvisual.pojo.tables.records.RecordsRecord;

/**
 * 
 * record数据操作类
 *
 * @author zhangke
 */
@Repository("RecordMapperImpl")
public class RecordMapperImpl extends CommonMapper {

    @Autowired
    DSLContext dsl;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 
     * 通过网吧id找到在这个网吧上网的所有人的记录
     *
     * @param barId
     *            网吧id
     * @return 在网吧上网的所有人的记录
     * @author zhangke
     * @since 0.0.1
     */
    public List<Records> selectPersonsByBarId(String barId) {
        return dsl.selectFrom(RECORDS)
                .where(RECORDS.BARID.eq(barId))
                .fetchInto(Records.class);
    }

    /**
     * 
     * 通过条件过滤筛选出数据
     *
     * @param filter
     *            过滤条件
     * @return 结果列表
     * @author zhangke
     * @since 0.0.1
     */
    public List<Records> selectByCondition(String filter) {
        filter = filter.replace('"', ' ');
        SelectQuery<RecordsRecord> selectQuery = dsl.selectQuery(RECORDS);
        selectQuery.addConditions(DSL.condition(filter));
        return selectQuery.fetch()
                .into(Records.class);

    }

    /**
     * 
     * 批量删除记录
     *
     * @param records
     * @return 批量删除是否成功
     * @author zhangke
     * @since 0.0.1
     */
    public boolean batchDelete(List<Records> records) {
        for (Records re : records) {
            delRecordsByCondition(RECORDS.BARID.eq(re.getBarid())
                    .and(RECORDS.PERSONID.eq(re.getPersonid()))
                    .and(RECORDS.OFFLINETIME.eq(re.getOfflinetime()))
                    .and(RECORDS.ONLINETIME.eq(re.getOnlinetime()))
                    .toString());
        }
        return true;

    }

    /**
     * 
     * 通过id删除records里的一条记录
     *
     * @param id
     *            网吧id
     * @return 删除成功true，失败false
     * @author zhangke
     * @since 0.0.1
     */
    public boolean delRecordsByBarid(String id) {
        int result = dsl.delete(RECORDS)
                .where(RECORDS.BARID.eq(id))
                .execute();
        return result >= 0 ? true : false;
    }

    /**
     * 
     * 通过id删除records里的一条记录
     *
     * @param id
     *            网吧id
     * @return 删除成功true，失败false
     * @author zhangke
     * @since 0.0.1
     */
    public boolean delRecordsByCondition(String filter) {
        filter = filter.replace('"', ' ');
        DeleteQuery<RecordsRecord> delQuery = dsl.deleteQuery(RECORDS);
        delQuery.addConditions(DSL.condition(filter));
        int result = delQuery.execute();
        return true;
    }

    public void fixTimeStamp() {
        dsl.update(Persons.PERSONS)
                .set(Persons.PERSONS.BIRTHDAY, Persons.PERSONS.BIRTHDAY.div(1000))
                .execute();
        dsl.update(RECORDS)
                .set(RECORDS.OFFLINETIME, RECORDS.OFFLINETIME.div(1000))
                .execute();
        dsl.update(RECORDS)
                .set(RECORDS.ONLINETIME, RECORDS.ONLINETIME.div(1000))
                .execute();
    }

}
