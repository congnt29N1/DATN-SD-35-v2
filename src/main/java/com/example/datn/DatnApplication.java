package com.example.datn;

import com.example.datn.giaohangnhanhservice.DiaChiAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories
@EnableScheduling
@SpringBootApplication
public class DatnApplication {

	public static void main(String[] args) throws Exception {
		DiaChiAPI.callGetTinhThanhAPI();
		SpringApplication.run(DatnApplication.class, args);
	}


}
