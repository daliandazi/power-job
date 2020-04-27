package com.github.kfcfans.oms.server.persistence.config;

import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;
import java.util.Objects;

/**
 * 本地H2数据库配置
 *
 * @author tjq
 * @since 2020/4/27
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        // repository包名
        basePackages = LocalJpaConfig.LOCAL_PACKAGES,
        // 实体管理bean名称
        entityManagerFactoryRef = "localEntityManagerFactory",
        // 事务管理bean名称
        transactionManagerRef = "localTransactionManager"
)
public class LocalJpaConfig {

    @Resource(name = "omsLocalDatasource")
    private DataSource omsLocalDatasource;

    public static final String LOCAL_PACKAGES = "com.github.kfcfans.oms.server.persistence.local";

    private static Map<String, Object> genDatasourceProperties() {

        JpaProperties jpaProperties = new JpaProperties();
        jpaProperties.setOpenInView(false);
        jpaProperties.setShowSql(false);

        HibernateProperties hibernateProperties = new HibernateProperties();
        // 考虑要不要用 create 模式，每次启动都删除数据
        hibernateProperties.setDdlAuto("update");
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }

    @Bean(name = "localEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean initLocalEntityManagerFactory(EntityManagerFactoryBuilder builder) {

        return builder
                .dataSource(omsLocalDatasource)
                .properties(genDatasourceProperties())
                .packages(LOCAL_PACKAGES)
                .persistenceUnit("localPersistenceUnit")
                .build();
    }


    @Bean(name = "localTransactionManager")
    public PlatformTransactionManager initLocalTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(initLocalEntityManagerFactory(builder).getObject()));
    }
}
