/*
 * This file is generated by jOOQ.
*/
package cn.dyhack.barvisual.pojo.tables.daos;


import cn.dyhack.barvisual.pojo.tables.Bars;
import cn.dyhack.barvisual.pojo.tables.records.BarsRecord;

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
public class BarsDao extends DAOImpl<BarsRecord, cn.dyhack.barvisual.pojo.tables.pojos.Bars, String> {

    /**
     * Create a new BarsDao without any configuration
     */
    public BarsDao() {
        super(Bars.BARS, cn.dyhack.barvisual.pojo.tables.pojos.Bars.class);
    }

    /**
     * Create a new BarsDao with an attached configuration
     */
    public BarsDao(Configuration configuration) {
        super(Bars.BARS, cn.dyhack.barvisual.pojo.tables.pojos.Bars.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getId(cn.dyhack.barvisual.pojo.tables.pojos.Bars object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.Bars> fetchById(String... values) {
        return fetch(Bars.BARS.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public cn.dyhack.barvisual.pojo.tables.pojos.Bars fetchOneById(String value) {
        return fetchOne(Bars.BARS.ID, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.Bars> fetchByName(String... values) {
        return fetch(Bars.BARS.NAME, values);
    }

    /**
     * Fetch records that have <code>longitude IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.Bars> fetchByLongitude(Double... values) {
        return fetch(Bars.BARS.LONGITUDE, values);
    }

    /**
     * Fetch records that have <code>latitude IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.Bars> fetchByLatitude(Double... values) {
        return fetch(Bars.BARS.LATITUDE, values);
    }

    /**
     * Fetch records that have <code>suspect_id_num IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.Bars> fetchBySuspectIdNum(Integer... values) {
        return fetch(Bars.BARS.SUSPECT_ID_NUM, values);
    }

    /**
     * Fetch records that have <code>suspect_cuited_num IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.Bars> fetchBySuspectCuitedNum(Integer... values) {
        return fetch(Bars.BARS.SUSPECT_CUITED_NUM, values);
    }

    /**
     * Fetch records that have <code>suspect_audit_num IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.Bars> fetchBySuspectAuditNum(Integer... values) {
        return fetch(Bars.BARS.SUSPECT_AUDIT_NUM, values);
    }
}
