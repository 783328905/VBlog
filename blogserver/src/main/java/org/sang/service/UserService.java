package org.sang.service;

import org.sang.bean.Role;
import org.sang.bean.User;
import org.sang.mapper.RolesMapper;
import org.sang.mapper.UserMapper;
import org.sang.utils.DateUtils;
import org.sang.utils.RedisUtil;
import org.sang.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

/**
 * Created by sang on 2017/12/17.
 */
@Service
@Transactional
public class UserService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserMapper userMapper;
    @Autowired
    RolesMapper rolesMapper;
    @Autowired
    RedisUtil redisUtil;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(s);
        if (user == null) {
            //避免返回null，这里返回一个不含有任何值的User对象，在后期的密码比对过程中一样会验证失败
            return new User();
        }
        //查询用户的角色信息，并返回存入user中
        //List<Role> roles = rolesMapper.getRolesByUid(user.getId());
        //user.setRoles(roles);
        logger.info(user.toString());

        List<GrantedAuthority> authorities = user.getAuthorities();
        for(GrantedAuthority authority:authorities){
            logger.info("{}",authority);
        }
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setEnabled(true);

        return user;
    }

    /**
     * @param user
     * @return 0表示成功
     * 1表示用户名重复
     * 2表示失败
     */
    public int reg(User user) {
        User loadUserByUsername = userMapper.loadUserByUsername(user.getUsername());
        if (loadUserByUsername != null) {
            return 1;
        }
        //插入用户,插入之前先对密码进行加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        //user.setEnabled(true);//用户可用
        long result = userMapper.reg(user);
        //配置用户的角色，默认都是普通用户
        Integer rid = 3;
        int i = rolesMapper.addRoles(rid, user.getId());
        boolean b = i == 1 && result == 1;
        if (b) {
            return 0;
        } else {
            return 2;
        }
    }

    public int updateUserEmail(String email) {
        return userMapper.updateUserEmail(email, Util.getCurrentUser().getId());
    }

    public List<User> getUserByNickname(String nickname) {
        List<User> list = userMapper.getUserByNickname(nickname);
        for(User user : list)
            user.setEnabled( redisUtil.get(user.getUsername()) == null );
        return list;
    }

    public List<Role> getAllRole() {
        return userMapper.getAllRole();
    }

    public int updateUserEnabled(Boolean enabled, String username ,String dateString) {

        try {
            if(!enabled){
                Long t = DateUtils.dateStringToSeconds(dateString);
                redisUtil.set(username, dateString, t);
                return 1;
            }else {
                redisUtil.del(username);
                return 1;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteUserById(Integer uid) {
        return userMapper.deleteUserById(uid);
    }

    public int updateUserRoles(Integer[] rids, Integer id) {
        int i = userMapper.deleteUserRolesByUid(id);
        return userMapper.setUserRoles(rids, id);
    }

    public User getUserById(Integer id) {
        return userMapper.getUserById(id);
    }
}
