package org.sang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sang.bean.Role;
import org.sang.bean.User;

import java.util.List;

/**
 * Created by sang on 2017/12/17.
 */
@Mapper
public interface UserMapper {

    User loadUserByUsername(@Param("username") String username);

    long reg(User user);

    int updateUserEmail(@Param("email") String email, @Param("id") Integer id);

    List<User> getUserByNickname(@Param("nickname") String nickname);

    List<Role> getAllRole();

    int updateUserEnabled(@Param("enabled") Boolean enabled, @Param("uid") Integer uid);

    int deleteUserById(Integer uid);

    int deleteUserRolesByUid(Integer id);

    int setUserRoles(@Param("rids") Integer[] rids, @Param("id") Integer id);

    User getUserById(@Param("id") Integer id);
}
