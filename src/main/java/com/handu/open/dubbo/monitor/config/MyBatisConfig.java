/**
 * Copyright 2006-2015 handu.com
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.handu.open.dubbo.monitor.config;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.base.Preconditions;

/**
 * MyBatis配置文件
 *
 * Created by Silence on 2014-12-08.
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig implements ApplicationContextAware {

	@Autowired
	private Environment env;

	private ApplicationContext context;

	private static final String DB_URL = "db.url";
	private static final String DB_USERNAME = "db.username";
	private static final String DB_PASSWORD = "db.password";
	private static final String DB_MAX_ACTIVE = "db.maxActive";

	@Bean
	public DruidDataSource dataSource() {
		final String url = Preconditions.checkNotNull(env.getProperty(DB_URL));
		final String username = Preconditions.checkNotNull(env.getProperty(DB_USERNAME));
		final String password = env.getProperty(DB_PASSWORD);
		final int maxActive = Integer.parseInt(env.getProperty(DB_MAX_ACTIVE, "200"));

		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");

		dataSource.setMaxActive(maxActive);
		dataSource.setInitialSize(5);
		dataSource.setMaxWait(60000);
		dataSource.setMinIdle(10);

		dataSource.setTimeBetweenEvictionRunsMillis(60000);
		dataSource.setMinEvictableIdleTimeMillis(300000);

		dataSource.setValidationQuery("SELECT 'x'");
		dataSource.setTestWhileIdle(true);
		dataSource.setTestOnBorrow(false);
		dataSource.setTestOnReturn(false);
		dataSource.setMaxOpenPreparedStatements(maxActive);
		
		// 打开PSCache，并且指定每个连接上PSCache的大小,如果用Oracle，则把poolPreparedStatements配置为true，mysql可以配置为false。分库分表较多的数据库，建议配置为false
		dataSource.setPoolPreparedStatements(false);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxActive);

		dataSource.setRemoveAbandoned(false);// 禁用对于长时间不使用的连接强制关闭的功能
		dataSource.setRemoveAbandonedTimeout(1800);// 超过30分钟开始关闭空闲连接，由于removeAbandoned为false，这个设置项不再起作用
		dataSource.setLogAbandoned(true);// 关闭abanded连接时输出错误日志，由于removeAbandoned为false，这个设置项不再起作用

		try {
			dataSource.setFilters("stat");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dataSource;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setConfigLocation(context.getResource("classpath:mybatis-config.xml"));
		factoryBean.setDataSource(dataSource());
		factoryBean.setMapperLocations(context.getResources("classpath*:mappers/**/*.xml"));
		return factoryBean.getObject();
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
}
