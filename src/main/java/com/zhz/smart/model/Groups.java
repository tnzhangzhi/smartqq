package com.zhz.smart.model;

import javax.persistence.*;

public class Groups {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long flag;

    private String name;

    private Long code;

    private Long gid;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return flag
     */
    public Long getFlag() {
        return flag;
    }

    /**
     * @param flag
     */
    public void setFlag(Long flag) {
        this.flag = flag;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return code
     */
    public Long getCode() {
        return code;
    }

    /**
     * @param code
     */
    public void setCode(Long code) {
        this.code = code;
    }

    /**
     * @return gid
     */
    public Long getGid() {
        return gid;
    }

    /**
     * @param gid
     */
    public void setGid(Long gid) {
        this.gid = gid;
    }
}