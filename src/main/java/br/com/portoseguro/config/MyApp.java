package br.com.portoseguro.config;

import org.glassfish.jersey.server.ResourceConfig;

public class MyApp extends ResourceConfig {
    public MyApp() {
        packages("br.com.portoseguro.controller");
    }
}
