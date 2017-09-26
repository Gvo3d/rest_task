package org.yakimovdenis.rest_task;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.yakimovdenis.rest_task.RestTaskApplication;
import org.yakimovdenis.rest_task.service.ContactService;
import org.yakimovdenis.rest_task.service.ContactServiceImpl;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;


@ContextConfiguration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = RestTaskApplication.class)
public class TestJPAConfig implements TransactionManagementConfigurer {

    private String driver;
    private String url;
    private String username;
    private String password;
    private String dialect;
    private String modelPackage;
    private String hbm2ddlAuto;
    private long quantity;
    private int count;

    public TestJPAConfig() {
        driver = "org.postgresql.Driver";
        url = "jdbc:postgresql://localhost:5433/postgres";
        username = "postgres";
        password = "root";
        modelPackage = "org.yakimovdenis.rest_task.models";
        dialect = "org.hibernate.dialect.PostgreSQLDialect";
        hbm2ddlAuto = "false";
        quantity = 10000;
        count = 50;
    }

    @Bean
    public DataSource dataSource(){
        Log4jdbcProxyDataSource dataSource = new Log4jdbcProxyDataSource(customDataSource());
        Log4JdbcCustomFormatter log4JdbcCustomFormatter = new Log4JdbcCustomFormatter();
        log4JdbcCustomFormatter.setLoggingType(LoggingType.SINGLE_LINE);
        log4JdbcCustomFormatter.setSqlPrefix("SQL:::");
        dataSource.setLogFormatter(log4JdbcCustomFormatter);
        return dataSource;
    }

    @Bean
    DataSource customDataSource() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();

        try {
            comboPooledDataSource.setDriverClass(driver);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        comboPooledDataSource.setJdbcUrl(url);
        comboPooledDataSource.setUser(username);
        comboPooledDataSource.setPassword(password);
        return comboPooledDataSource;

    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(modelPackage);
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put(org.hibernate.cfg.Environment.DIALECT, dialect);
        jpaProperties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, hbm2ddlAuto);
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        entityManagerFactoryBean.setDataSource(dataSource());
        return entityManagerFactoryBean;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    ContactService contactService(){
        return new ContactServiceImpl(quantity,count);
    }
}
