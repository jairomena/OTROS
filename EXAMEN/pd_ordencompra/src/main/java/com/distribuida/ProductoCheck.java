package com.distribuida;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

    @ApplicationScoped
    @Liveness
    public class ProductoCheck implements HealthCheck {

        private static final String SERVICE_URL = System.getenv("PRODUCT_URL") + "/products";

        @Override
        public HealthCheckResponse call() {
            boolean isRunning = isServiceRunning();
            if (isRunning) {
                return HealthCheckResponse.up("El servicio producto está en funcionamiento");
            } else {
                return HealthCheckResponse.down("El servicio producto no está disponible");
            }
        }

        private boolean isServiceRunning() {
            try {
                URL serviceUrl = new URL(ProductoCheck.SERVICE_URL);
                HttpURLConnection connection = (HttpURLConnection) serviceUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                return (responseCode == 200);
            } catch (IOException e) {
                return false;
            }
        }
    }
