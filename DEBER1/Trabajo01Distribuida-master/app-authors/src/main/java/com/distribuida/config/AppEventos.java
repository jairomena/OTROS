package com.distribuida.config;

import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.flywaydb.core.Flyway;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class AppEventos {

    @Inject
    @ConfigProperty(name="quarkus.datasource.jdbc.url")
    String dbUrl;

    @Inject
    @ConfigProperty(name="quarkus.datasource.username")
    String dbUser;

    @Inject
    @ConfigProperty(name="quarkus.datasource.password")
    String dbPassword;

    @Inject
    Flyway flyway;

    public void init(@Observes StartupEvent event){
        /*System.out.println("*****++migrando base de datos");
        System.out.println(dbUrl);
        System.out.println(dbUser);
        System.out.println(dbPassword);
        var flyway = Flyway.configure()
                .dataSource(dbUrl, dbUser, dbPassword)
                .load();
        flyway.migrate();*/
        /*flyway.clean();
        flyway.migrate();
        System.out.println(flyway.info().current().getVersion().toString());*/
    }

}
