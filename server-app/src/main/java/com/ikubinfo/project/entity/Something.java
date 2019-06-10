package com.ikubinfo.project.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Employee")
public class Something {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true, length = 11)
	private int id;

	@Column(name = "NAME", length = 20)
	private String name;

	@Column(name = "insert_time")
	private long insertTime;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public long getInsertTime() {
		return insertTime;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}
}
