package org.sang.bean;

import lombok.ToString;

import java.util.Date;

@ToString
public class Catalog {
    private Integer id;

    private String name;

    private Date createTime;

    private Integer userId;

    private Long count = 0L;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Catalog(Integer userId, String name) {
        this.name = name;
        this.userId = userId;
    }

    public Catalog() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}