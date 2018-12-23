/*
 * This file is generated by jOOQ.
*/
package cn.dyhack.barvisual.pojo.tables;


import cn.dyhack.barvisual.pojo.Indexes;
import cn.dyhack.barvisual.pojo.InternetBarBackup;
import cn.dyhack.barvisual.pojo.Keys;
import cn.dyhack.barvisual.pojo.tables.records.SuspectsRecord;

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
public class Suspects extends TableImpl<SuspectsRecord> {

    private static final long serialVersionUID = 1595721474;

    /**
     * The reference instance of <code>internet_bar_backup.suspects</code>
     */
    public static final Suspects SUSPECTS = new Suspects();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SuspectsRecord> getRecordType() {
        return SuspectsRecord.class;
    }

    /**
     * The column <code>internet_bar_backup.suspects.personid</code>. 上网用户的id
     */
    public final TableField<SuspectsRecord, String> PERSONID = createField("personid", org.jooq.impl.SQLDataType.VARCHAR(128).nullable(false), this, "上网用户的id");

    /**
     * The column <code>internet_bar_backup.suspects.count</code>. 上网次数
     */
    public final TableField<SuspectsRecord, Integer> COUNT = createField("count", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "上网次数");

    /**
     * Create a <code>internet_bar_backup.suspects</code> table reference
     */
    public Suspects() {
        this(DSL.name("suspects"), null);
    }

    /**
     * Create an aliased <code>internet_bar_backup.suspects</code> table reference
     */
    public Suspects(String alias) {
        this(DSL.name(alias), SUSPECTS);
    }

    /**
     * Create an aliased <code>internet_bar_backup.suspects</code> table reference
     */
    public Suspects(Name alias) {
        this(alias, SUSPECTS);
    }

    private Suspects(Name alias, Table<SuspectsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Suspects(Name alias, Table<SuspectsRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.SUSPECTS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<SuspectsRecord> getPrimaryKey() {
        return Keys.KEY_SUSPECTS_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<SuspectsRecord>> getKeys() {
        return Arrays.<UniqueKey<SuspectsRecord>>asList(Keys.KEY_SUSPECTS_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Suspects as(String alias) {
        return new Suspects(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Suspects as(Name alias) {
        return new Suspects(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Suspects rename(String name) {
        return new Suspects(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Suspects rename(Name name) {
        return new Suspects(name, null);
    }
}