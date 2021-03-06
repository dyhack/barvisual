/*
 * This file is generated by jOOQ.
*/
package cn.dyhack.barvisual.pojo.tables.daos;


import cn.dyhack.barvisual.pojo.tables.Province;
import cn.dyhack.barvisual.pojo.tables.records.ProvinceRecord;

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
public class ProvinceDao extends DAOImpl<ProvinceRecord, cn.dyhack.barvisual.pojo.tables.pojos.Province, Integer> {

    /**
     * Create a new ProvinceDao without any configuration
     */
    public ProvinceDao() {
        super(Province.PROVINCE, cn.dyhack.barvisual.pojo.tables.pojos.Province.class);
    }

    /**
     * Create a new ProvinceDao with an attached configuration
     */
    public ProvinceDao(Configuration configuration) {
        super(Province.PROVINCE, cn.dyhack.barvisual.pojo.tables.pojos.Province.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Integer getId(cn.dyhack.barvisual.pojo.tables.pojos.Province object) {
        return object.getAreaid();
    }

    /**
     * Fetch records that have <code>areaid IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.Province> fetchByAreaid(Integer... values) {
        return fetch(Province.PROVINCE.AREAID, values);
    }

    /**
     * Fetch a unique record that has <code>areaid = value</code>
     */
    public cn.dyhack.barvisual.pojo.tables.pojos.Province fetchOneByAreaid(Integer value) {
        return fetchOne(Province.PROVINCE.AREAID, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.Province> fetchByName(String... values) {
        return fetch(Province.PROVINCE.NAME, values);
    }
}
