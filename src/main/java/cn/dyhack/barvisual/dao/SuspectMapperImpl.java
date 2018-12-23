package cn.dyhack.barvisual.dao;

import static cn.dyhack.barvisual.pojo.tables.Suspects.SUSPECTS;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.dyhack.barvisual.pojo.tables.pojos.Suspects;
import cn.dyhack.barvisual.pojo.tables.records.SuspectsRecord;

/**
 * 
 * 上网嫌疑人员表
 *
 * @author zhangke
 * @since 0.0.1
 */
@Repository("SuspectMapperImpl")
public class SuspectMapperImpl  extends CommonMapper{

    @Autowired
    DSLContext dsl;
    /**
     *   
     * 通过过滤调价查询suspect表的记录
     *
     * @param filter 过滤条件
     * @return suspect列表
     * @author zhangke
     * @since 0.0.1
     */
    public List<Suspects> selectByconditon(String filter) {
        SelectQuery<SuspectsRecord> query = dsl.selectQuery(SUSPECTS);
        query.addConditions(DSL.condition(filter));
        return query.fetch()
                .into(Suspects.class);
    }
}
