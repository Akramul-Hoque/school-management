package com.school.common.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Bean(initMethod = "migrate")
    public Flyway authFlyway(DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .schemas("auth")
                .locations("classpath:db/migration/auth")
                .load();
    }

    @Bean(initMethod = "migrate")
    public Flyway studentFlyway(DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .schemas("student")
                .locations("classpath:db/migration/student")
                .load();
    }

    @Bean(initMethod = "migrate")
    public Flyway feeFlyway(DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .schemas("fee")
                .locations("classpath:db/migration/fee")
                .load();
    }

    @Bean(initMethod = "migrate")
    public Flyway cmsFlyway(DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .schemas("cms")
                .locations("classpath:db/migration/cms")
                .load();
    }

    @Bean(initMethod = "migrate")
    public Flyway admissionFlyway(DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .schemas("admission")
                .locations("classpath:db/migration/admission")
                .load();
    }

    @Bean(initMethod = "migrate")
    public Flyway academicFlyway(DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .schemas("academic")
                .locations("classpath:db/migration/academic")
                .load();
    }

    @Bean(initMethod = "migrate")
    public Flyway attendanceFlyway(DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .schemas("attendance")
                .locations("classpath:db/migration/attendance")
                .load();
    }

    @Bean(initMethod = "migrate")
    public Flyway examFlyway(DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .schemas("exam")
                .locations("classpath:db/migration/exam")
                .load();
    }

    @Bean(initMethod = "migrate")
    public Flyway hrFlyway(DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .schemas("hr")
                .locations("classpath:db/migration/hr")
                .load();
    }

    @Bean(initMethod = "migrate")
    public Flyway payrollFlyway(DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .schemas("payroll")
                .locations("classpath:db/migration/payroll")
                .load();
    }

    @Bean(initMethod = "migrate")
    public Flyway accountsFlyway(DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .schemas("accounts")
                .locations("classpath:db/migration/accounts")
                .load();
    }

    @Bean(initMethod = "migrate")
    public Flyway notificationFlyway(DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .schemas("notification")
                .locations("classpath:db/migration/notification")
                .load();
    }

    @Bean(initMethod = "migrate")
    public Flyway reportingFlyway(DataSource ds) {
        return Flyway.configure().dataSource(ds)
                .schemas("reporting")
                .locations("classpath:db/migration/reporting")
                .load();
    }
}
