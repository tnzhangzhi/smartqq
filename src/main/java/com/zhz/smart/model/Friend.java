package com.zhz.smart.model;

import javax.persistence.*;

public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    private String markname;

    private Long userid;

    private Byte vip;

    private Integer viplevel;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return markname
     */
    public String getMarkname() {
        return markname;
    }

    /**
     * @param markname
     */
    public void setMarkname(String markname) {
        this.markname = markname;
    }

    /**
     * @return userid
     */
    public Long getUserid() {
        return userid;
    }

    /**
     * @param userid
     */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /**
     * @return vip
     */
    public Byte getVip() {
        return vip;
    }

    /**
     * @param vip
     */
    public void setVip(Byte vip) {
        this.vip = vip;
    }

    /**
     * @return viplevel
     */
    public Integer getViplevel() {
        return viplevel;
    }

    /**
     * @param viplevel
     */
    public void setViplevel(Integer viplevel) {
        this.viplevel = viplevel;
    }
}