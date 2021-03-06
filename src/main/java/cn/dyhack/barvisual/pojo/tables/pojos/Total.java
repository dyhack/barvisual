/*
 * This file is generated by jOOQ.
*/
package cn.dyhack.barvisual.pojo.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;


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
public class Total implements Serializable {

    private static final long serialVersionUID = -1309162189;

    private Integer id;
    private String  personid;
    private String  barid;
    private String  sex;
    private String  name;
    private Long    onlinetime;
    private Long    offlinetime;
    private Integer areaid;
    private Long    birthday;
    private Byte    floatPopulation;

    public Total() {}

    public Total(Total value) {
        this.id = value.id;
        this.personid = value.personid;
        this.barid = value.barid;
        this.sex = value.sex;
        this.name = value.name;
        this.onlinetime = value.onlinetime;
        this.offlinetime = value.offlinetime;
        this.areaid = value.areaid;
        this.birthday = value.birthday;
        this.floatPopulation = value.floatPopulation;
    }

    public Total(
        Integer id,
        String  personid,
        String  barid,
        String  sex,
        String  name,
        Long    onlinetime,
        Long    offlinetime,
        Integer areaid,
        Long    birthday,
        Byte    floatPopulation
    ) {
        this.id = id;
        this.personid = personid;
        this.barid = barid;
        this.sex = sex;
        this.name = name;
        this.onlinetime = onlinetime;
        this.offlinetime = offlinetime;
        this.areaid = areaid;
        this.birthday = birthday;
        this.floatPopulation = floatPopulation;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonid() {
        return this.personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getBarid() {
        return this.barid;
    }

    public void setBarid(String barid) {
        this.barid = barid;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOnlinetime() {
        return this.onlinetime;
    }

    public void setOnlinetime(Long onlinetime) {
        this.onlinetime = onlinetime;
    }

    public Long getOfflinetime() {
        return this.offlinetime;
    }

    public void setOfflinetime(Long offlinetime) {
        this.offlinetime = offlinetime;
    }

    public Integer getAreaid() {
        return this.areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    public Long getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Byte getFloatPopulation() {
        return this.floatPopulation;
    }

    public void setFloatPopulation(Byte floatPopulation) {
        this.floatPopulation = floatPopulation;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Total (");

        sb.append(id);
        sb.append(", ").append(personid);
        sb.append(", ").append(barid);
        sb.append(", ").append(sex);
        sb.append(", ").append(name);
        sb.append(", ").append(onlinetime);
        sb.append(", ").append(offlinetime);
        sb.append(", ").append(areaid);
        sb.append(", ").append(birthday);
        sb.append(", ").append(floatPopulation);

        sb.append(")");
        return sb.toString();
    }
}
