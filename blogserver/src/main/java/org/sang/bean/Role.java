package org.sang.bean;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sang on 2017/12/17.
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role implements Serializable,GrantedAuthority {
    private int rid;
    private String rname;
    private List<Permission> permissions;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String getAuthority() {
        return rname;
    }
}
