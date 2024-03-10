package com.SoftwareprojektBackend.googlewalletpassbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GoogleWalletPassBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoogleWalletPassBackendApplication.class, args);
	}

}
