package com.juanroldanbrz.examples.banktransfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is a partial implementation of this use case: http://mongodb.github.io/node-mongodb-native/schema/chapter9/
 * @author juan.roldan.brz@gmail.com
 */
@SpringBootApplication
public class BankTransferApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankTransferApplication.class, args);
	}
}
