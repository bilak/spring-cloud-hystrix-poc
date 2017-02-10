package com.github.bilak.poc.controller;

import com.github.bilak.poc.controller.schema.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created by lvasek on 10/02/2017.
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@GetMapping("/{accountId}")
	public Account getAccountById(@PathVariable("accountId") String accountId) throws InterruptedException {
		int timeOut = ThreadLocalRandom.current().nextInt(200, 500);
		if (timeOut > 400)
			throw new RuntimeException("Timeout should be less than 400 milliseconds");

		TimeUnit.MILLISECONDS.sleep(timeOut);
		logger.debug("Getting account {}", accountId);
		return new Account().setId(accountId).setName("name " + accountId);
	}
}
