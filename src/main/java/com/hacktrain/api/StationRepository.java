package com.hacktrain.api;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@Repository
@Transactional(readOnly = true)
public class StationRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Station save(Station station) {
		entityManager.persist(station);
		return station;
	}
	
	public Station findByEmail(String email) {
		try {
//			return entityManager.createNamedQuery(Station.FIND_BY_EMAIL, Station.class)
//					.setParameter("email", email)
//					.getSingleResult();
		} catch (PersistenceException e) {
			return null;
		}
        return null;
	}

	
}
