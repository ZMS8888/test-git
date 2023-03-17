package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("org.example.mapper")
//@EnableDiscoveryClient
public class EnvironmentApplication {


    public static void main( String[] args )
    {
        SpringApplication.run(EnvironmentApplication.class, args);
        System.out.println( "Hello World!" );
    }
}
