package com.github.bilak.poc.controller.schema;

import java.util.List;

/**
 * Created by lvasek on 10/02/2017.
 */
public class Accessor {

	private String id;
	private String name;
	private List<String> accounts;

	public String getId() {
		return id;
	}

	public Accessor setId(String id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Accessor setName(String name) {
		this.name = name;
		return this;
	}

	public List<String> getAccounts() {
		return accounts;
	}

	public Accessor setAccounts(List<String> accounts) {
		this.accounts = accounts;
		return this;
	}

	@Override
	public String toString() {
		return "Accessor{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", accounts=" + accounts +
				'}';
	}
}
