package com.example.managers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.example.entities.Entry;
import com.example.entities.Journal;

public class JournalManager {
//	@PersistenceContext
//	EntityManager em;
//	
//	public List<Journal> getJournals() {
//		TypedQuery<Journal> getJournals = em.createQuery("SELECT j FROM Journal j", Journal.class);
//		return getJournals.getResultList();
//	}
//	
//	public Journal getJournal(int id) {
//		return em.find(Journal.class, id);
//	}
//	
//	public Journal createJournal(Journal j) {
//		em.persist(j);
//		return j;
//	}
//	
//	public Journal updateJournal(Journal j) {
//		em.merge(j);
//		return j;
//	}
//	
//	public void delete(int id) {
//		em.remove(getJournal(id));
//	}
}
