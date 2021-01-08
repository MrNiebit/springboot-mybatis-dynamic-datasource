# springboot + mybatis 多数据源操作

1、 分别在mysql和oracle中创建表t_user，和实体类user 保持一致即可。
2、 导入springboot 和 springboot-mybatis-starter等相关依赖。
3、 配置yml文件
4、 分别配置 mysql 和 oracle 配置类。 其中 至少有一个配置类，放spring容器要加@Primary注解
> 当有多个 同类型的注入到spring容器，可以使用@Primary注解。 就是在众多相同bean中，优先使用用@Primary注解打bean




- application.yml
```properties
# 应用名称
spring.application.name=springboot-mybatis-dynamic-datasource

# 应用服务 WEB 访问端口
server.port=8080

# 显示 sql
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
logging.level.cn.lacknb.mapper=debug


spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql:///study?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=xxxx


spring.datasource.oracle-driver=oracle.jdbc.OracleDriver
spring.datasource.oracle-url=jdbc:oracle:thin:@localhost:1521:helowin
spring.datasource.oracle-username=gitsilence
spring.datasource.oracle-password=gitsilence
```

- mysql datasource config
```java
package cn.lacknb.datasource.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Desc:
 * @Author: gitsilence
 * @Date: 2021/1/8
 */
@Configuration
@MapperScan(basePackages = MysqlDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "mysqlSqlSessionFactory")
public class MysqlDataSourceConfig {

    public static final String PACKAGE = "cn.lacknb.mapper.mysql";
    public static final String MAPPER_LOCATION = "classpath:mapper/mysql/*Mapper.xml";

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClass;


    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource () {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClass);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }

    /**
     * 事务管理器
     * @return
     */
    @Bean(name = "mysqlTransactionManager")
    public DataSourceTransactionManager mysqlTransactionManager () {
        return new DataSourceTransactionManager(mysqlDataSource());
    }

    /**
     * sqlSessionFactory 数据库连接 会话工厂。
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "mysqlSqlSessionFactory")
    public SqlSessionFactory mysqlSqlSessionFactory ( @Qualifier("mysqlDataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        // 配置mapper 路径
        sessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(MysqlDataSourceConfig.MAPPER_LOCATION)
        );
        return sessionFactoryBean.getObject();
    }



}

```

- oracle datasource config

```java
package cn.lacknb.datasource.oracle;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Desc:
 * @Author: gitsilence
 * @Date: 2021/1/8
 */
@Configuration
@MapperScan(basePackages = OracleDataSourceConfig.PACKAGE)
public class OracleDataSourceConfig {

    public static final String PACKAGE = "cn.lacknb.mapper.oracle";
    public static final String MAPPER_LOCATION = "classpath:mapper/oracle/*Mapper.xml";

    @Value("${spring.datasource.oracle-driver}")
    private String driverClass;

    @Value("${spring.datasource.oracle-url}")
    private String url;

    @Value("${spring.datasource.oracle-username}")
    private String username;

    @Value("${spring.datasource.oracle-password}")
    private String password;

    @Bean(name = "oracleDataSource")
    @Primary
    public DataSource dataSource () {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClass);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        return druidDataSource;
    }

    /**
     * 事务管理器
     * @return
     */
    @Bean(name = "oracleTransactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager () {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean(name = "oracleSqlsessionFactory")
    @Primary
    public SqlSessionFactory sessionFactory (@Qualifier("oracleDataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setMapperLocations(
                // 配置maper文件路径
                new PathMatchingResourcePatternResolver().getResources(OracleDataSourceConfig.MAPPER_LOCATION)
        );
        return sqlSessionFactoryBean.getObject();
    }
    
}

```
