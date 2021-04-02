package com.VideoGameTracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;



@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.VideoGameTracker.repo")
@PropertySource("classpath:database.properties")
public class DBconfig {

	@Autowired
	private Environment env;
	
	@Bean(name="entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setJpaVendorAdapter(getJpaVendor());
		emf.setDataSource(getDataSource());
		emf.setPersistenceUnitName("VideoGameTracker");
		emf.setPackagesToScan("com.VideoGameTracker.entities");
		emf.setJpaProperties(getHibernateProperties());
		return emf;
	}
	
	@Bean(name="transactionManager")
	public PlatformTransactionManager txManager() {
		return new JpaTransactionManager(getEntityManagerFactory().getObject());
	}
	
	@Bean
    public DataSource getDataSource() {
        DriverManagerDataSource source = new DriverManagerDataSource();

        source.setDriverClassName(env.getRequiredProperty("jdbc.driver"));
        source.setUrl(env.getRequiredProperty("jdbc.url"));
        source.setUsername(env.getRequiredProperty("jdbc.username"));
        source.setPassword(env.getRequiredProperty("jdbc.password"));

        return source;
    }

	@Bean
    public JpaVendorAdapter getJpaVendor() { 
		return new HibernateJpaVendorAdapter(); 
	}

	@Bean
    public Properties getHibernateProperties() {
        Properties properties = new Properties();

        properties.put(DIALECT, env.getRequiredProperty("hibernate.dialect"));
        properties.put(SHOW_SQL, env.getRequiredProperty("hibernate.show_sql"));
        properties.put(FORMAT_SQL, env.getRequiredProperty("hibernate.format_sql"));
        properties.put(HBM2DDL_AUTO, env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put(ENABLE_LAZY_LOAD_NO_TRANS,
                env.getRequiredProperty("hibernate.enable_lazy_load_no_trans"));

        return properties;
    }

	
}
