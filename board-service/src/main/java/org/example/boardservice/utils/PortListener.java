package org.example.boardservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PortListener implements CommandLineRunner {

    @Autowired
    private Environment environment;

    public String getPort() {
        return environment.getProperty("local.server.port");
    }

    @Override
    public void run(String... args) throws Exception {

    }
}