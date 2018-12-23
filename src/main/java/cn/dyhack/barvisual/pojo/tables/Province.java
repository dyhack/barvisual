/*
 * This file is generated by jOOQ.
*/
package cn.dyhack.barvisual.pojo.tables;


import cn.dyhack.barvisual.pojo.Indexes;
import cn.dyhack.barvisual.pojo.InternetBarBackup;
import cn.dyhack.barvisual.pojo.Keys;
import cn.dyhack.barvisual.pojo.tables.records.ProvinceRecord;

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
public class Province extends TableImpl<ProvinceRecord> {

    private static final long serialVersionUID = 1028357810;

    /**
     * The reference instance of <code>internet_bar_backup.province</code>
     */
    public static final Province PROVINCE = new Province();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ProvinceRecord> getRecordType() {
        return ProvinceRecord.class;
    }

    /**
     * The column <code>internet_bar_backup.province.areaid</code>.
     */
    public final TableField<ProvinceRecord, Integer> AREAID = createField("areaid", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>internet_bar_backup.province.name</code>.
     */
    public final TableField<ProvinceRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(64), this, "");

    /**
     * Create a <code>internet_bar_backup.province</code> table reference
     */
    public Province() {
        this(DSL.name("province"), null);
    }

    /**
     * Create an aliased <code>internet_bar_backup.province</code> table reference
     */
    public Province(String alias) {
        this(DSL.name(alias), PROVINCE);
    }

    /**
     * Create an aliased <code>internet_bar_backup.province</code> table reference
     */
    public Province(Name alias) {
        this(alias, PROVINCE);
    }

    private Province(Name alias, Table<ProvinceRecord> aliased) {
        this(alias, aliased, null);
    }

    private Province(Name alias, Table<ProvinceRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.PROVINCE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<ProvinceRecord> getPrimaryKey() {
        return Keys.KEY_PROVINCE_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<ProvinceRecord>> getKeys() {
        return Arrays.<UniqueKey<ProvinceRecord>>asList(Keys.KEY_PROVINCE_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Province as(String alias) {
        return new Province(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Province as(Name alias) {
        return new Province(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Province rename(String name) {
        return new Province(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Province rename(Name name) {
        return new Province(name, null);
    }
}