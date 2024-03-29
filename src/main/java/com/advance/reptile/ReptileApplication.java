package com.advance.reptile;

import com.advance.reptile.socket.NettyService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@MapperScan({"com.advance.reptile.*.mapper","com.advance.reptile.wx.*.mapper"})
public class ReptileApplication extends SpringBootServletInitializer implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ReptileApplication.class, args);
	}
//
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ReptileApplication.class);
	}

	//实现CommandLineRunner 重写run方法 这里放了netty的启动
	@Override
	public void run(String... args) throws Exception {
		new NettyService();
	}
}
