package com.ikubinfo.project.repository;

import javax.persistence.EntityManager;

import com.ikubinfo.project.entity.Something;
import com.ikubinfo.project.util.PersistenceSingleton;

public class SomethingRepository {

	private EntityManager entityManager;

	public SomethingRepository() {

		this.entityManager = PersistenceSingleton.INSTANCE.getEntityManagerFactory().createEntityManager();
	}

	public Something getSomething(int employeeId) {
		return entityManager.find(Something.class, employeeId);
	}
}