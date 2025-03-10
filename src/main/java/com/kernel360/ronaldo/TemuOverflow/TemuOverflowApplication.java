package com.kernel360.ronaldo.TemuOverflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TemuOverflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemuOverflowApplication.class, args);
	}

}
