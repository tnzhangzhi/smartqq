package com.zhz.smart.model;

import javax.persistence.*;

@Table(name = "group_message")
public class GroupMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Long userid;

    private Long groupid;

    private Long time;

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
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
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
     * @return groupid
     */
    public Long getGroupid() {
        return groupid;
    }

    /**
     * @param groupid
     */
    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    /**
     * @return time
     */
    public Long getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Long time) {
        this.time = time;
    }
}