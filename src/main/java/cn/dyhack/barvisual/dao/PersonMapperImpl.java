package cn.dyhack.barvisual.dao;

import static cn.dyhack.barvisual.pojo.tables.Persons.PERSONS;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.SelectQuery;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.dyhack.barvisual.pojo.tables.pojos.Persons;
import cn.dyhack.barvisual.pojo.tables.records.PersonsRecord;

/**
 * 对 person表操作类
 *
 * @author zhangke
 */
@Repository("PersonMapperImpl")
public class PersonMapperImpl extends CommonMapper {

    @Autowired
    DSLContext dsl;
    @Autowired
    TotalMapperImpl totalMapper;

    public Persons selectPersonsById(String personId) {
        PersonsRecord record = dsl.selectFrom(PERSONS)
                .where(PERSONS.ID.eq(personId))
                .fetchOne();
        return record.into(Persons.class);

    }

    public int countPersonByAreaId(String areaId) {
        return dsl.selectFrom(PERSONS)
                .where(PERSONS.AREAID.eq(areaId))
                .fetchCount();
    }

    public List<Persons> selectByConditon(String filter) {
        SelectQuery<PersonsRecord> selectQuery = dsl.selectQuery(PERSONS);
        selectQuery.addConditions(DSL.condition(filter));
        return selectQuery.fetch()
                .into(Persons.class);

    }

    public List<Persons> selectAll() {
        return dsl.selectFrom(PERSONS)
                .fetch()
                .into(Persons.class);
    }

    public List<Persons> selectAudit() {
        String Sql = "SELECT *,TIMESTAMPDIFF(YEAR,FROM_UNIXTIME(birthday,'%Y-%m-%d'),FROM_UNIXTIME(907257600000,'%Y-%m-%d')) AS age FROM persons ORDER BY birthday DESC LIMIT 19531";
        return dsl.fetch(Sql)
                .into(Persons.class);
    }
    

}
