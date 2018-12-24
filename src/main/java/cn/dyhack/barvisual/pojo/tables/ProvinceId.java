/*
 * This file is generated by jOOQ.
*/
package cn.dyhack.barvisual.pojo.tables;


import cn.dyhack.barvisual.pojo.InternetBarBackup;
import cn.dyhack.barvisual.pojo.tables.records.ProvinceIdRecord;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


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
public class ProvinceId extends TableImpl<ProvinceIdRecord> {

    private static final long serialVersionUID = -1808489551;

    /**
     * The reference instance of <code>internet_bar_backup.province_id</code>
     */
    public static final ProvinceId PROVINCE_ID = new ProvinceId();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProvinceIdRecord> getRecordType() {
        return ProvinceIdRecord.class;
    }

    /**
     * The column <code>internet_bar_backup.province_id.province_id</code>.
     */
    public final TableField<ProvinceIdRecord, Integer> PROVINCE_ID_ = createField("province_id", org.jooq.impl.SQLDataType.INTEGER, this, "");

    /**
     * The column <code>internet_bar_backup.province_id.province_name</code>. 省的名字
     */
    public final TableField<ProvinceIdRecord, String> PROVINCE_NAME = createField("province_name", org.jooq.impl.SQLDataType.VARCHAR(128), this, "省的名字");

    /**
     * Create a <code>internet_bar_backup.province_id</code> table reference
     */
    public ProvinceId() {
        this(DSL.name("province_id"), null);
    }

    /**
     * Create an aliased <code>internet_bar_backup.province_id</code> table reference
     */
    public ProvinceId(String alias) {
        this(DSL.name(alias), PROVINCE_ID);
    }

    /**
     * Create an aliased <code>internet_bar_backup.province_id</code> table reference
     */
    public ProvinceId(Name alias) {
        this(alias, PROVINCE_ID);
    }

    private ProvinceId(Name alias, Table<ProvinceIdRecord> aliased) {
        this(alias, aliased, null);
    }

    private ProvinceId(Name alias, Table<ProvinceIdRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return InternetBarBackup.INTERNET_BAR_BACKUP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProvinceId as(String alias) {
        return new ProvinceId(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProvinceId as(Name alias) {
        return new ProvinceId(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public ProvinceId rename(String name) {
        return new ProvinceId(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public ProvinceId rename(Name name) {
        return new ProvinceId(name, null);
    }
}