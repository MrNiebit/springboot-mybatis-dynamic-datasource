package cn.lacknb.service.impl;

import cn.lacknb.mapper.mysql.MysqlUserMapper;
import cn.lacknb.pojo.User;
import cn.lacknb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc:
 * @Author: gitsilence
 * @Date: 2021/1/8
 */
@Service("mysqlUserService")
public class MysqlUserServiceImpl implements UserService {

    @Resource
    private MysqlUserMapper mysqlUserMapper;

    @Override
    public int addUser(User user) {
        return mysqlUserMapper.addUser(user);
    }

    @Override
    public int batchAddUser(List<User> users) {
        return mysqlUserMapper.batchAddUser(users);
    }

    @Override
    public int deleteUser(int id) {
        return mysqlUserMapper.deleteUser(id);
    }

    @Override
    public int updateUser(User user) {
        return mysqlUserMapper.updateUser(user);
    }

    @Override
    public User findUserById(int id) {
        return mysqlUserMapper.findUserById(id);
    }

    @Override
    public List<User> findAllUser() {
        return mysqlUserMapper.findAllUser();
    }
}
