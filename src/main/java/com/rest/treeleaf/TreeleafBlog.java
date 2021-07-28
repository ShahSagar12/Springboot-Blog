package com.rest.treeleaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TreeleafBlog {

    public static void main(String[] args) {
        SpringApplication.run(TreeleafBlog.class, args);
    }

}
