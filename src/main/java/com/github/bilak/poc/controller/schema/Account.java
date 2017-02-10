package com.github.bilak.poc.controller.schema;

/**
 * Created by lvasek on 10/02/2017.
 */
public class Account {

	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public Account setId(String id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Account setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		return "Account{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
