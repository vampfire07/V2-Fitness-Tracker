package com.example.managers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.example.entities.Exercise;

public class ExerciseManager {
//	@PersistenceContext
//	EntityManager em;
//	
//	public List<Exercise> getExercises() {
//		TypedQuery<Exercise> getExercises = em.createQuery("SELECT e FROM Exercise e", Exercise.class);
//		return getExercises.getResultList();
//	}
//	
//	public Exercise getExercise(int id) {
//		return em.find(Exercise.class, id);
//	}
//	
//	public Exercise createExercise(Exercise e) {
//		em.persist(e);
//		return e;
//	}
//	
//	public Exercise updateExercise(Exercise e) {
//		em.merge(e);
//		return e;
//	}
//	
//	public void delete(int id) {
//		em.remove(getExercise(id));
//	}
}
