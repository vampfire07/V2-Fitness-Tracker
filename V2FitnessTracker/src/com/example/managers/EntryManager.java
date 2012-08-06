package com.example.managers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.example.entities.Entry;

public class EntryManager {
//	@PersistenceContext
//	EntityManager em;
//	
//	public List<Entry> getEntries() {
//		TypedQuery<Entry> getEntries = em.createQuery("SELECT e FROM Entry e", Entry.class);
//		return getEntries.getResultList();
//	}
//	
//	public Entry getEntry(int id) {
//		return em.find(Entry.class, id);
//	}
//	
//	public Entry createEntry(Entry e) {
//		em.persist(e);
//		return e;
//	}
//	
//	public Entry updateEntry(Entry e) {
//		em.merge(e);
//		return e;
//	}
//	
//	public void delete(int id) {
//		em.remove(getEntry(id));
//	}
	
}
