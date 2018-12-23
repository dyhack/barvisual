/*
 * This file is generated by jOOQ.
*/
package cn.dyhack.barvisual.pojo.tables.daos;


import cn.dyhack.barvisual.pojo.tables.SichuanProvince;
import cn.dyhack.barvisual.pojo.tables.records.SichuanProvinceRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.10.8"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class SichuanProvinceDao extends DAOImpl<SichuanProvinceRecord, cn.dyhack.barvisual.pojo.tables.pojos.SichuanProvince, Integer> {

    /**
     * Create a new SichuanProvinceDao without any configuration
     */
    public SichuanProvinceDao() {
        super(SichuanProvince.SICHUAN_PROVINCE, cn.dyhack.barvisual.pojo.tables.pojos.SichuanProvince.class);
    }

    /**
     * Create a new SichuanProvinceDao with an attached configuration
     */
    public SichuanProvinceDao(Configuration configuration) {
        super(SichuanProvince.SICHUAN_PROVINCE, cn.dyhack.barvisual.pojo.tables.pojos.SichuanProvince.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(cn.dyhack.barvisual.pojo.tables.pojos.SichuanProvince object) {
        return object.getAreaid();
    }

    /**
     * Fetch records that have <code>areaid IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.SichuanProvince> fetchByAreaid(Integer... values) {
        return fetch(SichuanProvince.SICHUAN_PROVINCE.AREAID, values);
    }

    /**
     * Fetch a unique record that has <code>areaid = value</code>
     */
    public cn.dyhack.barvisual.pojo.tables.pojos.SichuanProvince fetchOneByAreaid(Integer value) {
        return fetchOne(SichuanProvince.SICHUAN_PROVINCE.AREAID, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.SichuanProvince> fetchByName(String... values) {
        return fetch(SichuanProvince.SICHUAN_PROVINCE.NAME, values);
    }
}
