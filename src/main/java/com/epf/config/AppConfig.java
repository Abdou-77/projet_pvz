package com.epf.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"com.epf"})
@PropertySource("classpath:database.properties")
public class AppConfig {
    private static final String DB_URL = "db.url";
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_DRIVER = "db.driver";
    private static final int SOCKET_TIMEOUT = 30000;
    private static final int MAX_POOL_SIZE = 10;
    private static final int MIN_IDLE = 2;
    private static final int IDLE_TIMEOUT = 30000;
    private static final int CONNECTION_TIMEOUT = 30000;

    private final Environment environment;

    public AppConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        configureDatasource(dataSource);
        return dataSource;
    }

    private void configureDatasource(HikariDataSource dataSource) {
        dataSource.setJdbcUrl(environment.getProperty(DB_URL));
        dataSource.setUsername(environment.getProperty(DB_USERNAME));
        dataSource.setPassword(environment.getProperty(DB_PASSWORD));
        dataSource.setDriverClassName(environment.getProperty(DB_DRIVER));
        dataSource.addDataSourceProperty("socketTimeout", SOCKET_TIMEOUT);
        
        // Connection pool settings
        dataSource.setMaximumPoolSize(MAX_POOL_SIZE);
        dataSource.setMinimumIdle(MIN_IDLE);
        dataSource.setIdleTimeout(IDLE_TIMEOUT);
        dataSource.setConnectionTimeout(CONNECTION_TIMEOUT);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}