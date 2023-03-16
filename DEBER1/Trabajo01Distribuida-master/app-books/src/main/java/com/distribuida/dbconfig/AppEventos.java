package com.distribuida.dbconfig;

import jakarta.enterprise.context.ApplicationScoped;
/*import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.flywaydb.core.Flyway;*/

@ApplicationScoped
public class AppEventos {

    /*@Inject
    @ConfigProperty(name="db.connection.url")
    private String dbUrl;

    @Inject
    @ConfigProperty(name="db.connection.username")
    private String dbUser;

    @Inject
    @ConfigProperty(name="db.connection.password")
    private String dbPassword;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object event){
        System.out.println("*****++migrando base de datos");
        var flyway = Flyway.configure()
                .dataSource(dbUrl, dbUser, dbPassword)
                .load();
        flyway.migrate();
    }*/

}
