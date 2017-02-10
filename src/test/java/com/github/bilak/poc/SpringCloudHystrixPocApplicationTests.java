package com.github.bilak.poc;

import com.github.bilak.poc.controller.schema.Accessor;
import com.github.bilak.poc.controller.schema.Account;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class SpringCloudHystrixPocApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(SpringCloudHystrixPocApplicationTests.class);

	@LocalServerPort
	private int localPort;

	@Test
	public void contextLoads() {

	}

	@Test
	public void testAccessorWithAccounts() {
		HystrixCommand<Accessor> accessorCommand = new AccessorCommand("1");
		Accessor accessor = accessorCommand.execute();
		HystrixCommand<List<Account>> accountsBatchCommand = new AccountBatchCommand(accessor.getAccounts());
		List<Account> accounts = accountsBatchCommand.execute();
		assertEquals("Number of accounts should be equal", accessor.getAccounts().size(), accounts.size());
		logger.debug("accounts {}", accounts);
	}

	class AccessorCommand extends HystrixCommand<Accessor> {

		private String accessorId;

		protected AccessorCommand(String accessorId) {
			super(HystrixCommandGroupKey.Factory.asKey("Accessor"));
			this.accessorId = accessorId;
		}

		@Override
		protected Accessor run() throws Exception {
			String url = "http://localhost:%s/accessors/%s";
			return new RestTemplate().getForObject(String.format(url, localPort, accessorId), Accessor.class);
		}
	}

	class AccountCommand extends HystrixCommand<Account> {

		private String accountId;
		String url = "http://localhost:%s/accounts/%s";

		protected AccountCommand(String accountId) {
			super(HystrixCommandGroupKey.Factory.asKey("Account"));
			this.accountId = accountId;
		}

		@Override
		protected Account run() throws Exception {
			return new RestTemplate()
					.getForObject(String.format(url, localPort, accountId), Account.class);
		}

		@Override
		protected Account getFallback() {
			return new Account().setId(accountId);
		}
	}

	class AccountBatchCommand extends HystrixCommand<List<Account>> {

		private List<String> accountIds;

		protected AccountBatchCommand(List<String> accountIds) {
			super(HystrixCommandGroupKey.Factory.asKey("Account"));
			this.accountIds = accountIds;
		}

		@Override
		protected List<Account> run() throws Exception {
			return accountIds.stream()
					.parallel()
					.map(accountId -> {
						logger.debug("Going to get account {}", accountId);
						return new AccountCommand(accountId).execute();
					})
					.collect(Collectors.toList());
		}
	}

}




