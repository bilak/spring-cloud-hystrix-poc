package com.github.bilak.poc.controller;

import com.github.bilak.poc.controller.schema.Accessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by lvasek on 10/02/2017.
 */
@RestController
@RequestMapping("/accessors")
public class AccessorController {

	@GetMapping("/{accessorId}")
	public Accessor getAccessorById(@PathVariable("accessorId") String id) {
		return new Accessor()
				.setId(id)
				.setName("name " + id)
				.setAccounts(generateAccountIds())
				;
	}

	private List<String> generateAccountIds() {
		int accountsCount = ThreadLocalRandom.current().nextInt(4, 10);
		return IntStream
				.range(0, accountsCount)
				.mapToObj((n) -> UUID.randomUUID().toString())
				.collect(Collectors.toList())
				;
	}
}
