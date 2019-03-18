package com.lvb.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages={"com.lvb.rest","com.lvb.service"})
@EntityScan(basePackages={"com.lvb.model"})
@EnableJpaRepositories(basePackages={"com.lvb.dao"})
public class MainApplication {
   public static void main(String n[]){
	   SpringApplication.run(MainApplication.class, n);
   }
}
