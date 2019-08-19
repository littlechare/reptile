package com.advance.reptile;

import com.advance.reptile.socket.NettyService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
public class ReptileApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ReptileApplication.class, args);
	}

	//实现CommandLineRunner 重写run方法 这里放了netty的启动
	@Override
	public void run(String... args) throws Exception {
		new NettyService();
	}
}
