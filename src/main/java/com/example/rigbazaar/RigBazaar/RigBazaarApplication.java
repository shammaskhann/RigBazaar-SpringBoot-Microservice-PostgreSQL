package com.example.rigbazaar.RigBazaar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class RigBazaarApplication {

	public static void main(String[] args) {
		SpringApplication.run(RigBazaarApplication.class, args);
	}


    @Bean
    public PlatformTransactionManager transactionManager(MongoDatabaseFactory factory) {
        return new MongoTransactionManager(factory);

    }
}




