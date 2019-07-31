package com.advance.reptile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Configuration
@MapperScan("com.advance.reptile.*.mapper")
public class ReptileApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReptileApplication.class, args);
	}

}
