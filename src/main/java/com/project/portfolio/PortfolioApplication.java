package com.project.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableJpaRepositories("com.project.portfolio.*")
//@ComponentScan(basePackages = {"com.project.portfolio.*"})
//@ComponentScan(basePackages ={"com.project.portfolio.security.service"})
@SpringBootApplication
public class PortfolioApplication{

	public static void main(String[] args) {
		SpringApplication.run(PortfolioApplication.class, args);
	}
}
