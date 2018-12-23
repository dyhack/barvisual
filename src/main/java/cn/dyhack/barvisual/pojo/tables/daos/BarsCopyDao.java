/*
 * This file is generated by jOOQ.
*/
package cn.dyhack.barvisual.pojo.tables.daos;


import cn.dyhack.barvisual.pojo.tables.BarsCopy;
import cn.dyhack.barvisual.pojo.tables.records.BarsCopyRecord;

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
public class BarsCopyDao extends DAOImpl<BarsCopyRecord, cn.dyhack.barvisual.pojo.tables.pojos.BarsCopy, String> {

    /**
     * Create a new BarsCopyDao without any configuration
     */
    public BarsCopyDao() {
        super(BarsCopy.BARS_COPY, cn.dyhack.barvisual.pojo.tables.pojos.BarsCopy.class);
    }

    /**
     * Create a new BarsCopyDao with an attached configuration
     */
    public BarsCopyDao(Configuration configuration) {
        super(BarsCopy.BARS_COPY, cn.dyhack.barvisual.pojo.tables.pojos.BarsCopy.class, configuration);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getId(cn.dyhack.barvisual.pojo.tables.pojos.BarsCopy object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.BarsCopy> fetchById(String... values) {
        return fetch(BarsCopy.BARS_COPY.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public cn.dyhack.barvisual.pojo.tables.pojos.BarsCopy fetchOneById(String value) {
        return fetchOne(BarsCopy.BARS_COPY.ID, value);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.BarsCopy> fetchByName(String... values) {
        return fetch(BarsCopy.BARS_COPY.NAME, values);
    }

    /**
     * Fetch records that have <code>longitude IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.BarsCopy> fetchByLongitude(Double... values) {
        return fetch(BarsCopy.BARS_COPY.LONGITUDE, values);
    }

    /**
     * Fetch records that have <code>latitude IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.BarsCopy> fetchByLatitude(Double... values) {
        return fetch(BarsCopy.BARS_COPY.LATITUDE, values);
    }

    /**
     * Fetch records that have <code>suspect_id_num IN (values)</code>
     */
    public List<cn.dyhack.barvisual.pojo.tables.pojos.BarsCopy> fetchBySuspectIdNum(Integer... values) {
        return fetch(BarsCopy.BARS_COPY.SUSPECT_ID_NUM, values);
    }
}
