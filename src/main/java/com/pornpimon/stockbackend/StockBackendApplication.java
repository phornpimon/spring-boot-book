package com.pornpimon.stockbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockBackendApplication.class, args);
	}

	// @Bean
	// CommandLineRunner init(StoregeService storegeService) {
	// 	return args -> {
	// 		storegeService.init();
	// 	};
	// }
}
