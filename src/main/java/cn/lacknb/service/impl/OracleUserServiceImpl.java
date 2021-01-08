package cn.lacknb.service.impl;

import cn.lacknb.mapper.oracle.OracleUserMapper;
import cn.lacknb.pojo.User;
import cn.lacknb.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Desc:
 * @Author: gitsilence
 * @Date: 2021/1/8
 */
@Service("oracleUserService")
public class OracleUserServiceImpl implements UserService {
    @Resource
    private OracleUserMapper oracleUserMapper;

    @Override
    public int addUser(User user) {
        return oracleUserMapper.addUser(user);
    }

    @Override
    public int batchAddUser(List<User> users) {
        return oracleUserMapper.batchAddUser(users);
    }

    @Override
    public int deleteUser(int id) {
        return oracleUserMapper.deleteUser(id);
    }

    @Override
    public int updateUser(User user) {
        return oracleUserMapper.updateUser(user);
    }

    @Override
    public User findUserById(int id) {
        return oracleUserMapper.findUserById(id);
    }

    @Override
    public List<User> findAllUser() {
        return oracleUserMapper.findAllUser();
    }
}
