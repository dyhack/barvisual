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
public class Bars implements Serializable {

    private static final long serialVersionUID = -45767661;

    private String  id;
    private String  name;
    private Double  longitude;
    private Double  latitude;
    private Integer suspectIdNum;
    private Integer suspectCuitedNum;
    private Integer suspectAuditNum;

    public Bars() {}

    public Bars(Bars value) {
        this.id = value.id;
        this.name = value.name;
        this.longitude = value.longitude;
        this.latitude = value.latitude;
        this.suspectIdNum = value.suspectIdNum;
        this.suspectCuitedNum = value.suspectCuitedNum;
        this.suspectAuditNum = value.suspectAuditNum;
    }

    public Bars(
        String  id,
        String  name,
        Double  longitude,
        Double  latitude,
        Integer suspectIdNum,
        Integer suspectCuitedNum,
        Integer suspectAuditNum
    ) {
        this.id = id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.suspectIdNum = suspectIdNum;
        this.suspectCuitedNum = suspectCuitedNum;
        this.suspectAuditNum = suspectAuditNum;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getSuspectIdNum() {
        return this.suspectIdNum;
    }

    public void setSuspectIdNum(Integer suspectIdNum) {
        this.suspectIdNum = suspectIdNum;
    }

    public Integer getSuspectCuitedNum() {
        return this.suspectCuitedNum;
    }

    public void setSuspectCuitedNum(Integer suspectCuitedNum) {
        this.suspectCuitedNum = suspectCuitedNum;
    }

    public Integer getSuspectAuditNum() {
        return this.suspectAuditNum;
    }

    public void setSuspectAuditNum(Integer suspectAuditNum) {
        this.suspectAuditNum = suspectAuditNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Bars (");

        sb.append(id);
        sb.append(", ").append(name);
        sb.append(", ").append(longitude);
        sb.append(", ").append(latitude);
        sb.append(", ").append(suspectIdNum);
        sb.append(", ").append(suspectCuitedNum);
        sb.append(", ").append(suspectAuditNum);

        sb.append(")");
        return sb.toString();
    }
}
