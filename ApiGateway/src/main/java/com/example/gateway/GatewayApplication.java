package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@RestController
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}


//	@Bean
//	public RouteLocator myRoutes(RouteLocatorBuilder builder){
//		System.out.println("rute: " + builder.routes());
//
//		return builder.routes()
//				.route(p -> p
//						.path("/books")
//						.filters(f -> f.addRequestHeader("Hello", "World"))
//						.uri("http://localhost:8080/api/bookcollection/books"))
//				.route(p -> p
//						.path("/get")
//						.filters(f -> f.addRequestHeader("Hello", "World"))
//						.uri("http://localhost:8080"))
//				.build();


//		return builder.routes()
//				.route(p -> p
//						.path("/get")
//						.uri("http://localhost:8080/api/bookcollection/books"))
//				//.uri("http://httpbin.org:80"))
//				.build();
//
//
//	}


}
