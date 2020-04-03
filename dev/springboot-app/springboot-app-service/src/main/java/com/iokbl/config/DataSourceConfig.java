package com.iokbl.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @desc 数据源配置
 */
@Configuration
@EnableConfigurationProperties(com.iokbl.config.DataSource.class)
@MapperScan(basePackages = "com.iokbl.persistence", sqlSessionTemplateRef  = "sqlSessionTemplate")
public class DataSourceConfig {
	private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
	
	private final com.iokbl.config.DataSource dataSource;
	
	public DataSourceConfig(com.iokbl.config.DataSource properties) {
		this.dataSource = properties;
	}

	@Bean("dataSource")
	@Primary
	public DataSource centerDataSource() {
		Properties configPros = new Properties();
		configPros.put("connectionTestQuery", dataSource.getConnectionTestQuery());
		configPros.put("driverClassName", dataSource.getDriverClassName());
		configPros.put("poolName", dataSource.getPoolName());
		configPros.put("maximumPoolSize", dataSource.getMaximumPoolSize());
		configPros.put("minimumIdle", dataSource.getMinimumIdle());
		configPros.put("connectionTimeout", dataSource.getConnectionTimeout());
		configPros.put("jdbcUrl", dataSource.getDataSourceUrl());
		configPros.put("username", dataSource.getDataUserName());
		configPros.put("password", dataSource.getPassword());
		HikariConfig hc = new HikariConfig(configPros);
		HikariDataSource ds = new HikariDataSource(hc);
		logger.info("数据源初始化已完成。");
		return ds;
	}
	
	@Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory centerSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }
 
    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager centerTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
 
    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate centerSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
