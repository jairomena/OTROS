package com.distribuida.dbconfig;

import io.helidon.config.Config;
import io.helidon.dbclient.DbClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class DBClientConfig {

    @Produces
    @ApplicationScoped
    public DbClient dbClient(){
        Config config = Config.create();
        DbClient db = DbClient.builder().config(config.get("db")).build();
        return db;
    }

}
