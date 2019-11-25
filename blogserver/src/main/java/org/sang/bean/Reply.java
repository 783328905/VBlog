package org.sang.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Reply {
    private Integer id;

    private Integer commentId;

    private User fromUser;

    private User toUser;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String content;

    public Reply() {
    }

    public Reply(Integer commentId, User fromUser, User toUser, String content) {
        this.commentId = commentId;
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", commentId=" + commentId +
                ", fromUser=" + fromUser +
                ", toUser=" + toUser +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                '}';
    }
}