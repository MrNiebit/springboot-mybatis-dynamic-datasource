package cn.lacknb.service;

import cn.lacknb.pojo.User;

import java.util.List;

/**
 * @Desc:
 * @Author: gitsilence
 * @Date: 2021/1/8
 */
public interface UserService {

    int addUser (User user);

    int batchAddUser (List<User> users);

    int deleteUser (int id);

    int updateUser (User user);

    User findUserById (int id);

    List<User> findAllUser ();

}
