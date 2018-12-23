package cn.dyhack.barvisual.dao;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.dyhack.barvisual.pojo.tables.pojos.RecordsPersons;
import static cn.dyhack.barvisual.pojo.Tables.RECORDS_PERSONS;
@Repository("RecordPersonMapperImpl")
public class RecordPersonMapperImpl extends CommonMapper {
     
    @Autowired
    DSLContext dsl;
    public List<RecordsPersons> selectAll()
    {
        return dsl.selectFrom(RECORDS_PERSONS).fetch().into(RecordsPersons.class);
    }

}
