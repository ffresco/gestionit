package com.gestionit.base;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;


@SpringBootApplication
//@ImportResource(value="classpath:/hsql_cfg.xml")
public class BaseApplication implements CommandLineRunner {

    @Bean
    public Java8TimeDialect java8TimeDialect() {
        return new Java8TimeDialect();
    }
    
    public static void main(String[] args)  {
        SpringApplication.run(BaseApplication.class, args);
        System.out.println("App Lista para Ejecutar");
     }
 
    //para probar cosas
    @Override
    public void run(String... args) throws Exception {
        
    }
}
