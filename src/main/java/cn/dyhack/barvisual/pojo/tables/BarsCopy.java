/*
 * This file is generated by jOOQ.
*/
package cn.dyhack.barvisual.pojo.tables;


import cn.dyhack.barvisual.pojo.Indexes;
import cn.dyhack.barvisual.pojo.InternetBarBackup;
import cn.dyhack.barvisual.pojo.Keys;
import cn.dyhack.barvisual.pojo.tables.records.BarsCopyRecord;

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
public class BarsCopy extends TableImpl<BarsCopyRecord> {

    private static final long serialVersionUID = -526162000;

    /**
     * The reference instance of <code>internet_bar_backup.bars_copy</code>
     */
    public static final BarsCopy BARS_COPY = new BarsCopy();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<BarsCopyRecord> getRecordType() {
        return BarsCopyRecord.class;
    }

    /**
     * The column <code>internet_bar_backup.bars_copy.id</code>. 网吧ID
     */
    public final TableField<BarsCopyRecord, String> ID = createField("id", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "网吧ID");

    /**
     * The column <code>internet_bar_backup.bars_copy.name</code>. 网吧名称
     */
    public final TableField<BarsCopyRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "网吧名称");

    /**
     * The column <code>internet_bar_backup.bars_copy.longitude</code>. 网吧经度
     */
    public final TableField<BarsCopyRecord, Double> LONGITUDE = createField("longitude", org.jooq.impl.SQLDataType.DOUBLE, this, "网吧经度");

    /**
     * The column <code>internet_bar_backup.bars_copy.latitude</code>. 网吧维度
     */
    public final TableField<BarsCopyRecord, Double> LATITUDE = createField("latitude", org.jooq.impl.SQLDataType.DOUBLE, this, "网吧维度");

    /**
     * The column <code>internet_bar_backup.bars_copy.suspect_id_num</code>. 嫌疑身份证数量
     */
    public final TableField<BarsCopyRecord, Integer> SUSPECT_ID_NUM = createField("suspect_id_num", org.jooq.impl.SQLDataType.INTEGER.defaultValue(org.jooq.impl.DSL.inline("0", org.jooq.impl.SQLDataType.INTEGER)), this, "嫌疑身份证数量");

    /**
     * Create a <code>internet_bar_backup.bars_copy</code> table reference
     */
    public BarsCopy() {
        this(DSL.name("bars_copy"), null);
    }

    /**
     * Create an aliased <code>internet_bar_backup.bars_copy</code> table reference
     */
    public BarsCopy(String alias) {
        this(DSL.name(alias), BARS_COPY);
    }

    /**
     * Create an aliased <code>internet_bar_backup.bars_copy</code> table reference
     */
    public BarsCopy(Name alias) {
        this(alias, BARS_COPY);
    }

    private BarsCopy(Name alias, Table<BarsCopyRecord> aliased) {
        this(alias, aliased, null);
    }

    private BarsCopy(Name alias, Table<BarsCopyRecord> aliased, Field<?>[] parameters) {
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
        return Arrays.<Index>asList(Indexes.BARS_COPY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<BarsCopyRecord> getPrimaryKey() {
        return Keys.KEY_BARS_COPY_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<BarsCopyRecord>> getKeys() {
        return Arrays.<UniqueKey<BarsCopyRecord>>asList(Keys.KEY_BARS_COPY_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BarsCopy as(String alias) {
        return new BarsCopy(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BarsCopy as(Name alias) {
        return new BarsCopy(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public BarsCopy rename(String name) {
        return new BarsCopy(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public BarsCopy rename(Name name) {
        return new BarsCopy(name, null);
    }
}
