package com.github.kfcfans.powerjob.samples.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.h2.Driver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import tech.powerjob.official.processors.impl.sql.SimpleSpringSqlProcessor;

import javax.sql.DataSource;

/**
 * @author Echo009
 * @since 2021/3/10
 */
@Configuration
public class SqlProcessorConfiguration {


    @Bean
    @DependsOn({"initPowerJob"})
    public DataSource sqlProcessorDataSource() {
        String path = System.getProperty("user.home") + "/test/h2/";
        String jdbcUrl = String.format("jdbc:h2:file:%spowerjob_sql_processor_db;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false", path);
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(Driver.class.getName());
        config.setJdbcUrl(jdbcUrl);
        config.setAutoCommit(true);
        // 池中最小空闲连接数量
        config.setMinimumIdle(1);
        // 池中最大连接数量
        config.setMaximumPoolSize(10);
        return new HikariDataSource(config);
    }


    @Bean
    public SimpleSpringSqlProcessor simpleSpringSqlProcessor(@Qualifier("sqlProcessorDataSource") DataSource dataSource) {
        SimpleSpringSqlProcessor simpleSpringSqlProcessor = new SimpleSpringSqlProcessor(dataSource);
        // do nothing
        simpleSpringSqlProcessor.registerSqlValidator("fakeSqlValidator", sql -> true);
        // 排除掉包含 drop 的 SQL
        simpleSpringSqlProcessor.registerSqlValidator("interceptDropValidator", sql -> sql.matches("^(?i)((?!drop).)*$"));
        // do nothing
        simpleSpringSqlProcessor.setSqlParser((sql, taskContext) -> sql);
        return simpleSpringSqlProcessor;
    }

}
