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
