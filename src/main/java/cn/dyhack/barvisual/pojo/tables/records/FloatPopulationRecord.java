/*
 * This file is generated by jOOQ.
*/
package cn.dyhack.barvisual.pojo.tables.records;


import cn.dyhack.barvisual.pojo.tables.FloatPopulation;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record9;
import org.jooq.Row9;
import org.jooq.impl.TableRecordImpl;


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
public class FloatPopulationRecord extends TableRecordImpl<FloatPopulationRecord> implements Record9<String, String, String, String, Long, Long, Integer, Long, Byte> {

    private static final long serialVersionUID = 223507223;

    /**
     * Setter for <code>internet_bar_backup.float_population.personid</code>.
     */
    public void setPersonid(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>internet_bar_backup.float_population.personid</code>.
     */
    public String getPersonid() {
        return (String) get(0);
    }

    /**
     * Setter for <code>internet_bar_backup.float_population.barid</code>.
     */
    public void setBarid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>internet_bar_backup.float_population.barid</code>.
     */
    public String getBarid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>internet_bar_backup.float_population.sex</code>.
     */
    public void setSex(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>internet_bar_backup.float_population.sex</code>.
     */
    public String getSex() {
        return (String) get(2);
    }

    /**
     * Setter for <code>internet_bar_backup.float_population.name</code>.
     */
    public void setName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>internet_bar_backup.float_population.name</code>.
     */
    public String getName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>internet_bar_backup.float_population.onlinetime</code>.
     */
    public void setOnlinetime(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>internet_bar_backup.float_population.onlinetime</code>.
     */
    public Long getOnlinetime() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>internet_bar_backup.float_population.offlinetime</code>.
     */
    public void setOfflinetime(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>internet_bar_backup.float_population.offlinetime</code>.
     */
    public Long getOfflinetime() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>internet_bar_backup.float_population.areaid</code>.
     */
    public void setAreaid(Integer value) {
        set(6, value);
    }

    /**
     * Getter for <code>internet_bar_backup.float_population.areaid</code>.
     */
    public Integer getAreaid() {
        return (Integer) get(6);
    }

    /**
     * Setter for <code>internet_bar_backup.float_population.birthday</code>.
     */
    public void setBirthday(Long value) {
        set(7, value);
    }

    /**
     * Getter for <code>internet_bar_backup.float_population.birthday</code>.
     */
    public Long getBirthday() {
        return (Long) get(7);
    }

    /**
     * Setter for <code>internet_bar_backup.float_population.float_population</code>.
     */
    public void setFloatPopulation(Byte value) {
        set(8, value);
    }

    /**
     * Getter for <code>internet_bar_backup.float_population.float_population</code>.
     */
    public Byte getFloatPopulation() {
        return (Byte) get(8);
    }

    // -------------------------------------------------------------------------
    // Record9 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<String, String, String, String, Long, Long, Integer, Long, Byte> fieldsRow() {
        return (Row9) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row9<String, String, String, String, Long, Long, Integer, Long, Byte> valuesRow() {
        return (Row9) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field1() {
        return FloatPopulation.FLOAT_POPULATION.PERSONID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return FloatPopulation.FLOAT_POPULATION.BARID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return FloatPopulation.FLOAT_POPULATION.SEX;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return FloatPopulation.FLOAT_POPULATION.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field5() {
        return FloatPopulation.FLOAT_POPULATION.ONLINETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field6() {
        return FloatPopulation.FLOAT_POPULATION.OFFLINETIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field7() {
        return FloatPopulation.FLOAT_POPULATION.AREAID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Long> field8() {
        return FloatPopulation.FLOAT_POPULATION.BIRTHDAY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Byte> field9() {
        return FloatPopulation.FLOAT_POPULATION.FLOAT_POPULATION_;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component1() {
        return getPersonid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getBarid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getSex();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component5() {
        return getOnlinetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component6() {
        return getOfflinetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component7() {
        return getAreaid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long component8() {
        return getBirthday();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte component9() {
        return getFloatPopulation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value1() {
        return getPersonid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getBarid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getSex();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value5() {
        return getOnlinetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value6() {
        return getOfflinetime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value7() {
        return getAreaid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long value8() {
        return getBirthday();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Byte value9() {
        return getFloatPopulation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatPopulationRecord value1(String value) {
        setPersonid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatPopulationRecord value2(String value) {
        setBarid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatPopulationRecord value3(String value) {
        setSex(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatPopulationRecord value4(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatPopulationRecord value5(Long value) {
        setOnlinetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatPopulationRecord value6(Long value) {
        setOfflinetime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatPopulationRecord value7(Integer value) {
        setAreaid(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatPopulationRecord value8(Long value) {
        setBirthday(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatPopulationRecord value9(Byte value) {
        setFloatPopulation(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FloatPopulationRecord values(String value1, String value2, String value3, String value4, Long value5, Long value6, Integer value7, Long value8, Byte value9) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached FloatPopulationRecord
     */
    public FloatPopulationRecord() {
        super(FloatPopulation.FLOAT_POPULATION);
    }

    /**
     * Create a detached, initialised FloatPopulationRecord
     */
    public FloatPopulationRecord(String personid, String barid, String sex, String name, Long onlinetime, Long offlinetime, Integer areaid, Long birthday, Byte floatPopulation) {
        super(FloatPopulation.FLOAT_POPULATION);

        set(0, personid);
        set(1, barid);
        set(2, sex);
        set(3, name);
        set(4, onlinetime);
        set(5, offlinetime);
        set(6, areaid);
        set(7, birthday);
        set(8, floatPopulation);
    }
}