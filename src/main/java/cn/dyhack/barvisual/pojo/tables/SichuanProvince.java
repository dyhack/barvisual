/*
 * This file is generated by jOOQ.
*/
package cn.dyhack.barvisual.pojo.tables;


import cn.dyhack.barvisual.pojo.Indexes;
import cn.dyhack.barvisual.pojo.InternetBarBackup;
import cn.dyhack.barvisual.pojo.Keys;
import cn.dyhack.barvisual.pojo.tables.records.SichuanProvinceRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
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
public class SichuanProvince extends TableImpl<SichuanProvinceRecord> {

    private static final long serialVersionUID = -336979136;

    /**
     * The reference instance of <code>internet_bar_backup.sichuan_province</code>
     */
    public static final SichuanProvince SICHUAN_PROVINCE = new SichuanProvince();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SichuanProvinceRecord> getRecordType() {
        return SichuanProvinceRecord.class;
    }

    /**
     * The column <code>internet_bar_backup.sichuan_province.areaid</code>.
     */
    public final TableField<SichuanProvinceRecord, Integer> AREAID = createField("areaid", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>internet_bar_backup.sichuan_province.name</code>.
     */
    public final TableField<SichuanProvinceRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(32), this, "");

    /**
     * Create a <code>internet_bar_backup.sichuan_province</code> table reference
     */
    public SichuanProvince() {
        this(DSL.name("sichuan_province"), null);
    }

    /**
     * Create an aliased <code>internet_bar_backup.sichuan_province</code> table reference
     */
    public SichuanProvince(String alias) {
        this(DSL.name(alias), SICHUAN_PROVINCE);
    }

    /**
     * Create an aliased <code>internet_bar_backup.sichuan_province</code> table reference
     */
    public SichuanProvince(Name alias) {
        this(alias, SICHUAN_PROVINCE);
    }

    private SichuanProvince(Name alias, Table<SichuanProvinceRecord> aliased) {
        this(alias, aliased, null);
    }

    private SichuanProvince(Name alias, Table<SichuanProvinceRecord> aliased, Field<?>[] parameters) {
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
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.SICHUAN_PROVINCE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<SichuanProvinceRecord> getPrimaryKey() {
        return Keys.KEY_SICHUAN_PROVINCE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SichuanProvinceRecord>> getKeys() {
        return Arrays.<UniqueKey<SichuanProvinceRecord>>asList(Keys.KEY_SICHUAN_PROVINCE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SichuanProvince as(String alias) {
        return new SichuanProvince(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SichuanProvince as(Name alias) {
        return new SichuanProvince(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public SichuanProvince rename(String name) {
        return new SichuanProvince(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public SichuanProvince rename(Name name) {
        return new SichuanProvince(name, null);
    }
}