package com.netcracker.wizardapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class WizardAppStarter {
    public static void main(String[] args) {
        SpringApplication.run(WizardAppStarter.class, args);

    }
}
