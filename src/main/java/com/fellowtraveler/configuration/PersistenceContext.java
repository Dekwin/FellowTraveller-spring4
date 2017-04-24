package com.fellowtraveler.configuration;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.rmi.server.RemoteServer;

import org.neo4j.ogm.config.Configuration;
/**
 * Created by igorkasyanenko on 26.03.17.
 */
import org.neo4j.ogm.compiler.MultiStatementCypherCompiler;

@org.springframework.context.annotation.Configuration
@EnableNeo4jRepositories(basePackages = "com.fellowtraveler.repository")
@EnableTransactionManagement
public class PersistenceContext {

    @Bean(name = "neo4j-transaction")
    public Neo4jTransactionManager transactionManager() throws Exception {
        return new Neo4jTransactionManager(sessionFactory());
    }

    @Bean
    public Configuration getConfiguration() {
        Configuration config = new Configuration();
        config
                .driverConfiguration()
                .setDriverClassName
                        ("org.neo4j.ogm.drivers.http.driver.HttpDriver")
                .setURI("http://neo4j:root@localhost:7474");
        return config;
    }

    @Bean
    public SessionFactory sessionFactory() {

        return new SessionFactory(getConfiguration(), "com.fellowtraveler.model.map");
    }
//
//    @Bean
//    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
//    public Session getSession() throws Exception {
//        return sessionFactory().openSession();
//    }
}