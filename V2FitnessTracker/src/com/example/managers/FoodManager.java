package com.example.managers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.example.entities.Food;

public class FoodManager {
//	@PersistenceContext
//	EntityManager em;
//	
//	public List<Food> getFoods() {
//		TypedQuery<Food> getFoods = em.createQuery("SELECT f FROM Food f", Food.class);
//		return getFoods.getResultList();
//	}
//	
//	public Food getFood(int id) {
//		return em.find(Food.class, id);
//	}
//	
//	public Food createFood(Food f) {
//		em.persist(f);
//		return f;
//	}
//	
//	public Food updateFood(Food f) {
//		em.merge(f);
//		return f;
//	}
//	
//	public void delete(int id) {
//		em.remove(getFood(id));
//	}
}
