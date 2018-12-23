package cn.dyhack.barvisual.dao;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.dyhack.barvisual.pojo.tables.pojos.ProvinceId;
import static cn.dyhack.barvisual.pojo.tables.ProvinceId.PROVINCE_ID;
/**
 * 
 * province_id 数据库操作类
 *
 * @since 0.0.5
 */
@Repository("ProvinceIdMapperImpl")
public class ProvinceIdMapperImpl {
    
    @Autowired
    DSLContext dsl;
    
    public List<ProvinceId> selectAll()
    {
        return dsl.selectFrom(PROVINCE_ID).fetch().into(ProvinceId.class);
    }

}
