package com.example.services;

import java.util.PriorityQueue;
import java.util.Queue;

import com.example.entities.User;
import com.example.v2fitnesstracker.HomeActivity;

public class ResponseBuilder {

	private User user;
	
	public ResponseBuilder(User user) {
		this.user = user;
	}
	
	public String generateResponse() {
		int age = user.getAge();
		int weight = user.getWeight();
		int goalWeight = user.getGoalWeight();
		String BMI_classification = HomeActivity.calculateBMIClassification(HomeActivity.calculateBMIIndex(user));
		String response = "";
		Queue<State> currentStates = new PriorityQueue<State>();
		currentStates.add(State.A);
		for(int i = 0; i < 4; i++) {
			for(State s : currentStates) {
				switch(s) {
				case A:
					currentStates.remove();
					// Age 5 to 12
					if(age >= 5 && age <= 12) {
						currentStates.add(State.B);
					}
					// Age 13 to 17
					else if(age >= 13 && age <= 17) {
						currentStates.add(State.C);
					}
					// Age 18 to 39
					else if(age >= 18 && age <= 39) {
						currentStates.add(State.D);
					}
					// Age 40 to 69
					else if(age >= 40 && age <= 69) {
						currentStates.add(State.E);
					}
					// Age 70 and up
					else if(age >= 70) {
						currentStates.add(State.F);
					}
					// If none of the above
					else return "Make sure you're at the right age to use this app!";
					break;
				case B:
					currentStates.remove();
					// Underweight
					if(BMI_classification.equals("Underweight")) {
						currentStates.add(State.G);
					}
					// Normal
					else if(BMI_classification.equals("Normal")) {
						currentStates.add(State.H);
					}
					// Overweight/Obese
					else if(BMI_classification.equals("Overweight") || BMI_classification.equals("Obese")) {
						currentStates.add(State.I);
					}
					else return "I could not classify your weight. Can you fix it for me?";
					break;
				case C:
					currentStates.remove();
					// Underweight
					if(BMI_classification.equals("Underweight")) {
						currentStates.add(State.J);
					}
					// Normal
					else if(BMI_classification.equals("Normal")) {
						currentStates.add(State.K);
					}
					// Overweight/Obese
					else if(BMI_classification.equals("Overweight") || BMI_classification.equals("Obese")) {
						currentStates.add(State.L);
					}
					else return "I could not classify your weight. Can you fix it for me?";
					break;
				case D:
					currentStates.remove();
					// Underweight
					if(BMI_classification.equals("Underweight")) {
						currentStates.add(State.M);
					}
					// Normal
					else if(BMI_classification.equals("Normal")) {
						currentStates.add(State.N);
					}
					// Overweight/Obese
					else if(BMI_classification.equals("Overweight") || BMI_classification.equals("Obese")) {
						currentStates.add(State.O);
					}
					else return "I could not classify your weight. Can you fix it for me?";
					break;
				case E:
					currentStates.remove();
					// Underweight
					if(BMI_classification.equals("Underweight")) {
						currentStates.add(State.P);
					}
					// Normal
					else if(BMI_classification.equals("Normal")) {
						currentStates.add(State.Q);
					}
					// Overweight/Obese
					else if(BMI_classification.equals("Overweight") || BMI_classification.equals("Obese")) {
						currentStates.add(State.R);
					}
					else return "I could not classify your weight. Can you fix it for me?";
					break;
				case F:
					currentStates.remove();
					// Underweight
					if(BMI_classification.equals("Underweight")) {
						currentStates.add(State.S);
					}
					// Normal
					else if(BMI_classification.equals("Normal")) {
						currentStates.add(State.T);
					}
					// Overweight/Obese
					else if(BMI_classification.equals("Overweight") || BMI_classification.equals("Obese")) {
						currentStates.add(State.U);
					}
					else return "I could not classify your weight. Can you fix it for me?";
					break;
				case G:
					currentStates.remove();
					// Lose weight
					if(weight > goalWeight) {
						currentStates.add(State.V);
					}
					// Maintain weight
					else if(weight == goalWeight) {
						currentStates.add(State.W);
					}
					// Gain weight
					else if(weight < goalWeight) {
						currentStates.add(State.X);
					}
					else return "I could not identify what your goal is. Please fix it for me.";
					break;
				case H:
					currentStates.remove();
					// Lose weight
					if(weight > goalWeight) {
						currentStates.add(State.Y);
					}
					// Maintain weight
					else if(weight == goalWeight) {
						currentStates.add(State.Z);
					}
					// Gain weight
					else if(weight < goalWeight) {
						currentStates.add(State.AA);
					}
					else return "I could not identify what your goal is. Please fix it for me.";
					break;
				case I:
					currentStates.remove();
					// Lose weight
					if(weight > goalWeight) {
						currentStates.add(State.AB);
					}
					// Maintain weight
					else if(weight == goalWeight) {
						currentStates.add(State.AC);
					}
					// Gain weight
					else if(weight < goalWeight) {
						currentStates.add(State.AD);
					}
					else return "I could not identify what your goal is. Please fix it for me.";
					break;
				case J:
					currentStates.remove();
					// Lose weight
					if(weight > goalWeight) {
						currentStates.add(State.AE);
					}
					// Maintain weight
					else if(weight == goalWeight) {
						currentStates.add(State.AF);
					}
					// Gain weight
					else if(weight < goalWeight) {
						currentStates.add(State.AG);
					}
					else return "I could not identify what your goal is. Please fix it for me.";
					break;
				case K:
					currentStates.remove();
					// Lose weight
					if(weight > goalWeight) {
						currentStates.add(State.AH);
					}
					// Maintain weight
					else if(weight == goalWeight) {
						currentStates.add(State.AI);
					}
					// Gain weight
					else if(weight < goalWeight) {
						currentStates.add(State.AJ);
					}
					else return "I could not identify what your goal is. Please fix it for me.";
					break;
				case L:
					currentStates.remove();
					// Lose weight
					if(weight > goalWeight) {
						currentStates.add(State.AK);
					}
					// Maintain weight
					else if(weight == goalWeight) {
						currentStates.add(State.AL);
					}
					// Gain weight
					else if(weight < goalWeight) {
						currentStates.add(State.AM);
					}
					else return "I could not identify what your goal is. Please fix it for me.";
					break;
				case M:
					currentStates.remove();
					// Lose weight
					if(weight > goalWeight) {
						currentStates.add(State.AN);
					}
					// Maintain weight
					else if(weight == goalWeight) {
						currentStates.add(State.AO);
					}
					// Gain weight
					else if(weight < goalWeight) {
						currentStates.add(State.AP);
					}
					else return "I could not identify what your goal is. Please fix it for me.";
					break;
				case N:
					currentStates.remove();
					// Lose weight
					if(weight > goalWeight) {
						currentStates.add(State.AQ);
					}
					// Maintain weight
					else if(weight == goalWeight) {
						currentStates.add(State.AR);
					}
					// Gain weight
					else if(weight < goalWeight) {
						currentStates.add(State.AS);
					}
					else return "I could not identify what your goal is. Please fix it for me.";
					break;
				case O:
					currentStates.remove();
					// Lose weight
					if(weight > goalWeight) {
						currentStates.add(State.AT);
					}
					// Maintain weight
					else if(weight == goalWeight) {
						currentStates.add(State.AU);
					}
					// Gain weight
					else if(weight < goalWeight) {
						currentStates.add(State.AV);
					}
					else return "I could not identify what your goal is. Please fix it for me.";
					break;
				case P:
					currentStates.remove();
					// Lose weight
					if(weight > goalWeight) {
						currentStates.add(State.AW);
					}
					// Maintain weight
					else if(weight == goalWeight) {
						currentStates.add(State.AX);
					}
					// Gain weight
					else if(weight < goalWeight) {
						currentStates.add(State.AY);
					}
					else return "I could not identify what your goal is. Please fix it for me.";
					break;
				case Q:
					currentStates.remove();
					// Lose weight
					if(weight > goalWeight) {
						currentStates.add(State.AZ);
					}
					// Maintain weight
					else if(weight == goalWeight) {
						currentStates.add(State.BA);
					}
					// Gain weight
					else if(weight < goalWeight) {
						currentStates.add(State.BB);
					}
					else return "I could not identify what your goal is. Please fix it for me.";
					break;
				case R:
					currentStates.remove();
					// Lose weight
					if(weight > goalWeight) {
						currentStates.add(State.BC);
					}
					// Maintain weight
					else if(weight == goalWeight) {
						currentStates.add(State.BD);
					}
					// Gain weight
					else if(weight < goalWeight) {
						currentStates.add(State.BE);
					}
					else return "I could not identify what your goal is. Please fix it for me.";
					break;
				case S:
					currentStates.remove();
					// Lose weight
					if(weight > goalWeight) {
						currentStates.add(State.BF);
					}
					// Maintain weight
					else if(weight == goalWeight) {
						currentStates.add(State.BG);
					}
					// Gain weight
					else if(weight < goalWeight) {
						currentStates.add(State.BH);
					}
					else return "I could not identify what your goal is. Please fix it for me.";
					break;
				case T:
					currentStates.remove();
					// Lose weight
					if(weight > goalWeight) {
						currentStates.add(State.BI);
					}
					// Maintain weight
					else if(weight == goalWeight) {
						currentStates.add(State.BJ);
					}
					// Gain weight
					else if(weight < goalWeight) {
						currentStates.add(State.BK);
					}
					else return "I could not identify what your goal is. Please fix it for me.";
					break;
				case U:
					currentStates.remove();
					// Lose weight
					if(weight > goalWeight) {
						currentStates.add(State.BL);
					}
					// Maintain weight
					else if(weight == goalWeight) {
						currentStates.add(State.BM);
					}
					// Gain weight
					else if(weight < goalWeight) {
						currentStates.add(State.BN);
					}
					else return "I could not identify what your goal is. Please fix it for me.";
					break;
				case V:
					currentStates.remove();
					return ResponseGenerator.generate(State.V);
				case W:
					currentStates.remove();
					return ResponseGenerator.generate(State.W);
				case X:
					currentStates.remove();
					return ResponseGenerator.generate(State.X);
				case Y:
					currentStates.remove();
					return ResponseGenerator.generate(State.Y);
				case Z:
					currentStates.remove();
					return ResponseGenerator.generate(State.Z);
				case AA:
					currentStates.remove();
					return ResponseGenerator.generate(State.AA);
				case AB:
					currentStates.remove();
					return ResponseGenerator.generate(State.AB);
				case AC:
					currentStates.remove();
					return ResponseGenerator.generate(State.AC);
				case AD:
					currentStates.remove();
					return ResponseGenerator.generate(State.AD);
				case AE:
					currentStates.remove();
					return ResponseGenerator.generate(State.AE);
				case AF:
					currentStates.remove();
					return ResponseGenerator.generate(State.AF);
				case AG:
					currentStates.remove();
					return ResponseGenerator.generate(State.AG);
				case AH:
					currentStates.remove();
					return ResponseGenerator.generate(State.AH);
				case AI:
					currentStates.remove();
					return ResponseGenerator.generate(State.AI);
				case AJ:
					currentStates.remove();
					return ResponseGenerator.generate(State.AJ);
				case AK:
					currentStates.remove();
					return ResponseGenerator.generate(State.AK);
				case AL:
					currentStates.remove();
					return ResponseGenerator.generate(State.AL);
				case AM:
					currentStates.remove();
					return ResponseGenerator.generate(State.AM);
				case AN:
					currentStates.remove();
					return ResponseGenerator.generate(State.AN);
				case AO:
					currentStates.remove();
					return ResponseGenerator.generate(State.AO);
				case AP:
					currentStates.remove();
					return ResponseGenerator.generate(State.AP);
				case AQ:
					currentStates.remove();
					return ResponseGenerator.generate(State.AQ);
				case AR:
					currentStates.remove();
					return ResponseGenerator.generate(State.AR);
				case AS:
					currentStates.remove();
					return ResponseGenerator.generate(State.AS);
				case AT:
					currentStates.remove();
					return ResponseGenerator.generate(State.AT);
				case AU:
					currentStates.remove();
					return ResponseGenerator.generate(State.AU);
				case AV:
					currentStates.remove();
					return ResponseGenerator.generate(State.AV);
				case AW:
					currentStates.remove();
					return ResponseGenerator.generate(State.AW);
				case AX:
					currentStates.remove();
					return ResponseGenerator.generate(State.AX);
				case AY:
					currentStates.remove();
					return ResponseGenerator.generate(State.AY);
				case AZ:
					currentStates.remove();
					return ResponseGenerator.generate(State.AZ);
				case BA:
					currentStates.remove();
					return ResponseGenerator.generate(State.BA);
				case BB:
					currentStates.remove();
					return ResponseGenerator.generate(State.BB);
				case BC:
					currentStates.remove();
					return ResponseGenerator.generate(State.BC);
				case BD:
					currentStates.remove();
					return ResponseGenerator.generate(State.BD);
				case BE:
					currentStates.remove();
					return ResponseGenerator.generate(State.BE);
				case BF:
					currentStates.remove();
					return ResponseGenerator.generate(State.BF);
				case BG:
					currentStates.remove();
					return ResponseGenerator.generate(State.BG);
				case BH:
					currentStates.remove();
					return ResponseGenerator.generate(State.BH);
				case BI:
					currentStates.remove();
					return ResponseGenerator.generate(State.BI);
				case BJ:
					currentStates.remove();
					return ResponseGenerator.generate(State.BJ);
				case BK:
					currentStates.remove();
					return ResponseGenerator.generate(State.BK);
				case BL:
					currentStates.remove();
					return ResponseGenerator.generate(State.BL);
				case BM:
					currentStates.remove();
					return ResponseGenerator.generate(State.BM);
				case BN:
					currentStates.remove();
					return ResponseGenerator.generate(State.BN);
				default: return "Based on your statistics, I can't suggest anything at the moment. Try fixing it.";
				}
			}	
		}
		return response;
	}
}
//	Queue<States> currentStates = new PriorityQueue();
//	currentStates.add(States.A);
//	for(int x = 0; x < in.length; x++) {
//		for(States s: currentStates) {
//			switch(s) {
//			case A:
//				currentStates.remove();
//				currentStates.add(States.B);
//				currentStates.add(States.F);
//				break;
//			case B:
//				currentStates.remove();
//				if(in[x] == 'h' || in[x] == 'H') currentStates.add(States.C);
//				else if(in[x] == ' ') currentStates.add(States.J);
//				else currentStates.add(States.K);
//				break;
//			case C:
//				currentStates.remove();
//				if(in[x] == 'i') currentStates.add(States.D);
//				else if(in[x] == ' ') currentStates.add(States.J);
//				else currentStates.add(States.K);
//				break;
//			case D:
//				currentStates.remove();
//				if(in[x] == ' ') currentStates.add(States.E);
//				else currentStates.add(States.K);
//				break;
//			case F:
//				currentStates.remove();
//				if(in[x] == 'h' || in[x] == 'H') currentStates.add(States.G);
//				else if(in[x] == ' ') currentStates.add(States.J);
//				else currentStates.add(States.K);
//				break;
//			case G:
//				currentStates.remove();
//				if(in[x] == 'o') currentStates.add(States.H);
//				else if(in[x] == ' ') currentStates.add(States.J);
//				else currentStates.add(States.K);
//				break;
//			case H:
//				currentStates.remove();
//				if(in[x] == ' ') currentStates.add(States.I);
//				else currentStates.add(States.K);
//				break;
//			}
//		}
//		return (currentStates.contains(States.D) || currentStates.contains(States.E) || currentStates.contains(States.H) || currentStates.contains(States.I));
//	}
//	return false;
//}
