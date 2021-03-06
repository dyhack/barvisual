/*
 * This file is generated by jOOQ.
*/
package cn.dyhack.barvisual.pojo.tables;


import cn.dyhack.barvisual.pojo.Indexes;
import cn.dyhack.barvisual.pojo.InternetBarBackup;
import cn.dyhack.barvisual.pojo.tables.records.RecordsRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Index;
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
public class Records extends TableImpl<RecordsRecord> {

    private static final long serialVersionUID = -1647437330;

    /**
     * The reference instance of <code>internet_bar_backup.records</code>
     */
    public static final Records RECORDS = new Records();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RecordsRecord> getRecordType() {
        return RecordsRecord.class;
    }

    /**
     * The column <code>internet_bar_backup.records.barid</code>.
     */
    public final TableField<RecordsRecord, String> BARID = createField("barid", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>internet_bar_backup.records.personid</code>.
     */
    public final TableField<RecordsRecord, String> PERSONID = createField("personid", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");

    /**
     * The column <code>internet_bar_backup.records.onlinetime</code>.
     */
    public final TableField<RecordsRecord, Long> ONLINETIME = createField("onlinetime", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>internet_bar_backup.records.offlinetime</code>.
     */
    public final TableField<RecordsRecord, Long> OFFLINETIME = createField("offlinetime", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>internet_bar_backup.records</code> table reference
     */
    public Records() {
        this(DSL.name("records"), null);
    }

    /**
     * Create an aliased <code>internet_bar_backup.records</code> table reference
     */
    public Records(String alias) {
        this(DSL.name(alias), RECORDS);
    }

    /**
     * Create an aliased <code>internet_bar_backup.records</code> table reference
     */
    public Records(Name alias) {
        this(alias, RECORDS);
    }

    private Records(Name alias, Table<RecordsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Records(Name alias, Table<RecordsRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.RECORDS_BARID, Indexes.RECORDS_PERSONID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Records as(String alias) {
        return new Records(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Records as(Name alias) {
        return new Records(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Records rename(String name) {
        return new Records(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Records rename(Name name) {
        return new Records(name, null);
    }
}
