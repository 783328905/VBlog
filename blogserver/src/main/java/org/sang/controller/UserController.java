package org.sang.controller;

import org.sang.bean.RespBean;
import org.sang.service.UserService;
import org.sang.utils.RedisUtil;
import org.sang.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Created by sang on 2017/12/24.
 */
@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    RedisUtil redisUtil;

    @RequestMapping("/currentUserName")
    public String currentUserName() {
        return Util.getCurrentUser().getUsername();
    }

    @RequestMapping("/currentUserId")
    public Integer currentUserId() {
        return Util.getCurrentUser().getId();
    }

    @RequestMapping("/currentUserEmail")
    public String currentUserEmail() {
        return Util.getCurrentUser().getEmail();
    }

    @RequestMapping("/isAdmin")
    public Boolean isAdmin() {
        List<GrantedAuthority> authorities = Util.getCurrentUser().getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().contains("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/updateUserEmail",method = RequestMethod.PUT)
    public RespBean updateUserEmail(String email) {
        if (userService.updateUserEmail(email) == 1) {
            return new RespBean("success", "开启成功!");
        }
        return new RespBean("error", "开启失败!");
    }

    @RequestMapping("/rget")
    public RespBean getRedis(String key){
        Set<Object> strings = redisUtil.sGet(key);
        for(Object s :strings){
            s = (String) s;
            System.out.println(s);
        }


        return new RespBean();
    }
    @RequestMapping("rpush")
    public RespBean pushRedis(String key,String value){
        Long l   = new Long(60 * 60 *24L);
        redisUtil.sSetAndTime(key, l,value);
        return new RespBean("success","插入成功");
    }
}
