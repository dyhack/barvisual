/*
 * This file is generated by jOOQ.
*/
package cn.dyhack.barvisual.pojo.tables;


import cn.dyhack.barvisual.pojo.Indexes;
import cn.dyhack.barvisual.pojo.InternetBarBackup;
import cn.dyhack.barvisual.pojo.tables.records.RecordsPersonsRecord;

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
public class RecordsPersons extends TableImpl<RecordsPersonsRecord> {

    private static final long serialVersionUID = 1913402619;

    /**
     * The reference instance of <code>internet_bar_backup.records_persons</code>
     */
    public static final RecordsPersons RECORDS_PERSONS = new RecordsPersons();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<RecordsPersonsRecord> getRecordType() {
        return RecordsPersonsRecord.class;
    }

    /**
     * The column <code>internet_bar_backup.records_persons.personid</code>.
     */
    public final TableField<RecordsPersonsRecord, String> PERSONID = createField("personid", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>internet_bar_backup.records_persons.barid</code>.
     */
    public final TableField<RecordsPersonsRecord, String> BARID = createField("barid", org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>internet_bar_backup.records_persons.name</code>.
     */
    public final TableField<RecordsPersonsRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(64), this, "");

    /**
     * The column <code>internet_bar_backup.records_persons.sex</code>.
     */
    public final TableField<RecordsPersonsRecord, Byte> SEX = createField("sex", org.jooq.impl.SQLDataType.TINYINT, this, "");

    /**
     * The column <code>internet_bar_backup.records_persons.areaid</code>.
     */
    public final TableField<RecordsPersonsRecord, String> AREAID = createField("areaid", org.jooq.impl.SQLDataType.VARCHAR(128), this, "");

    /**
     * The column <code>internet_bar_backup.records_persons.birthday</code>.
     */
    public final TableField<RecordsPersonsRecord, Long> BIRTHDAY = createField("birthday", org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * Create a <code>internet_bar_backup.records_persons</code> table reference
     */
    public RecordsPersons() {
        this(DSL.name("records_persons"), null);
    }

    /**
     * Create an aliased <code>internet_bar_backup.records_persons</code> table reference
     */
    public RecordsPersons(String alias) {
        this(DSL.name(alias), RECORDS_PERSONS);
    }

    /**
     * Create an aliased <code>internet_bar_backup.records_persons</code> table reference
     */
    public RecordsPersons(Name alias) {
        this(alias, RECORDS_PERSONS);
    }

    private RecordsPersons(Name alias, Table<RecordsPersonsRecord> aliased) {
        this(alias, aliased, null);
    }

    private RecordsPersons(Name alias, Table<RecordsPersonsRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.RECORDS_PERSONS_BARID, Indexes.RECORDS_PERSONS_PERSONID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RecordsPersons as(String alias) {
        return new RecordsPersons(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RecordsPersons as(Name alias) {
        return new RecordsPersons(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public RecordsPersons rename(String name) {
        return new RecordsPersons(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public RecordsPersons rename(Name name) {
        return new RecordsPersons(name, null);
    }
}