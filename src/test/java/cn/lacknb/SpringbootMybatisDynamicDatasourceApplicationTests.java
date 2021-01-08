package cn.lacknb;

import cn.lacknb.pojo.User;
import cn.lacknb.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SpringbootMybatisDynamicDatasourceApplicationTests {

    @Autowired
    @Qualifier("mysqlUserService")
    private UserService mysqlUserService;

    @Autowired
    @Qualifier("oracleUserService")
    private UserService oracleUserService;


    /**
     * 添加user
     */
    @Test
    void addUser() {
        User user = new User(2, "李四", "女", 24);
        // mysql
        int row = mysqlUserService.addUser(user);
        System.out.println("mysql改变的行数： " + row);

        int r = oracleUserService.addUser(user);
        System.out.println("oracle改变的行数： " + r);
    }

    /**
     * 批量条件user
     */
    @Test
    public void batchAddUser () {
        List<User> users = new ArrayList<>();
        users.add(new User(3, "xxxasd", "男", 22));
        users.add(new User(4, "lisi", "男", 42));
        users.add(new User(5, "wanger", "男", 32));
        users.add(new User(6, "李白", "女", 11));
        users.add(new User(7, "的马西亚", "男", 52));
//        int row = mysqlUserService.batchAddUser(users);
//        System.out.println("mysql改变的行数：" + row);
        int r = oracleUserService.batchAddUser(users);
    }


    /**
     * 删除user
     */
    @Test
    public void deleteUser () {
        int row = mysqlUserService.deleteUser(1);
        System.out.println("mysql改变的行数:" + row);
        int r = oracleUserService.deleteUser(1);
        System.out.println("oracle改变的函数：" + r);
    }


    /**
     * 修改user
     */
    @Test
    public void updateUser () {
        User user = new User(3, "修改", "女", 99);
        int row = mysqlUserService.updateUser(user);
        System.out.println("mysql改变的行数：" + row);
        int r = oracleUserService.updateUser(user);
        System.out.println("oracle改变的行数：" + r);
    }

    /**
     * 查找单个user
     */
    @Test
    public void findById () {
        User userById = mysqlUserService.findUserById(3);
        System.out.println("mysql: " + userById);
        User userById1 = oracleUserService.findUserById(3);
        System.out.println("oracle: " + userById1);
    }


    /**
     * 查找所有的user
     */
    @Test
    public void findAllUser () {
        List<User> allUser = mysqlUserService.findAllUser();
        allUser.forEach(System.out::println);
        System.out.println("-----------------------分界线--------------------------");
        List<User> allUser1 = oracleUserService.findAllUser();
        allUser1.forEach(System.out::println);
    }

}
