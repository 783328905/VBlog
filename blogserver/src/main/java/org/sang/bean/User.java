package org.sang.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.extern.slf4j.Slf4j;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
@Slf4j
@JsonIgnoreProperties
public class User implements UserDetails,Serializable {

    public User() {
    }
    public User(Integer id){ this.id=id;}
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String name;

    private String avatar;

    private String tag;

    private String description;

    private String phone;

    private String openid;

    private Date created;

    private String score ="0";

    private List<Role>roles;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    @Override
    public List<GrantedAuthority> getAuthorities() {
         //List<GrantedAuthority> authorities=new ArrayList<>();
         List<GrantedAuthority> simpleAuthorities = new ArrayList<>();
         List<Role> roles=this.roles;
         List<Permission> permissions;
         if(roles !=null) {
             //simpleAuthorities.add(new SimpleGrantedAuthority("ss"));
             for (Role role : roles) {
                 //authorities.add(new SimpleGrantedAuthority(role.getRname()));
                 //List<SimpleGrantedAuthority> authorities1=new ArrayList<>();
                 permissions = role.getPermissions();
                 simpleAuthorities.add(new SimpleGrantedAuthority(role.getRname()));
                 for (Permission permission : permissions) {
                     simpleAuthorities.add(new SimpleGrantedAuthority(permission.getPname()));
                 }
             /*for(GrantedAuthority grantedAuthority: authorities1)
                 simpleAuthorities.add(new SimpleGrantedAuthority(grantedAuthority.getAuthority()));*/

                 //simpleAuthorities.addAll(new SimpleGrantedAuthority(authorities1.get));

             }
             return simpleAuthorities;
         }else {
             return null;
         }

    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", avatr='" + avatar + '\'' +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                ", phone='" + phone + '\'' +
                ", openid='" + openid + '\'' +
                ", created=" + created +
                ", score='" + score + '\'' +
                ", roles=" + roles +
                '}';
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score == null ? null : score.trim();
    }


}