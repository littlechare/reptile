package com.advance.reptile;

import com.advance.reptile.socket.NettyService;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@MapperScan({"com.advance.reptile.*.mapper","com.advance.reptile.wx.*.mapper"})
public class ReptileApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ReptileApplication.class, args);
	}

	//实现CommandLineRunner 重写run方法 这里放了netty的启动
	@Override
	public void run(String... args) throws Exception {
		new NettyService();
	}
//
//	@Bean
//	public ServletWebServerFactory servletContainer() {
//		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
//			@Override
//			protected void postProcessContext(Context context) {
//
//				SecurityConstraint securityConstraint = new SecurityConstraint();
//				securityConstraint.setUserConstraint("CONFIDENTIAL");
//				SecurityCollection collection = new SecurityCollection();
//				collection.addPattern("/*");
//				securityConstraint.addCollection(collection);
//				context.addConstraint(securityConstraint);
//			}
//		};
//		tomcat.addAdditionalTomcatConnectors(createHTTPConnector());
//		return tomcat;
//	}
//
//	private Connector createHTTPConnector() {
//		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//		//同时启用http（8080）、https（8443）两个端口
//		connector.setScheme("http");
//		connector.setSecure(false);
//		connector.setPort(3000);
//		connector.setRedirectPort(8443);
//		return connector;
//	}
}
