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
public class Suspects implements Serializable {

    private static final long serialVersionUID = -1953207398;

    private String  personid;
    private Integer count;

    public Suspects() {}

    public Suspects(Suspects value) {
        this.personid = value.personid;
        this.count = value.count;
    }

    public Suspects(
        String  personid,
        Integer count
    ) {
        this.personid = personid;
        this.count = count;
    }

    public String getPersonid() {
        return this.personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public Integer getCount() {
        return this.count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Suspects (");

        sb.append(personid);
        sb.append(", ").append(count);

        sb.append(")");
        return sb.toString();
    }
}
