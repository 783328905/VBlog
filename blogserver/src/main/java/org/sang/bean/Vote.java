package org.sang.bean;

import java.util.Date;

public class Vote {
    private Integer id;

    private Date createTime;

    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {


        this.user = user;
    }

    protected Vote() {
    }

    public Vote(User user) {
        this.user = user;
    }
}