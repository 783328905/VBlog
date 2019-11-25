package org.sang.bean;

import lombok.*;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Permission implements Serializable {
    private int pid;
    private String pname;
    private String url;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
