package Project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;

public class NFA {
	
	private static String [] alphabet;
	private static State [] states;
	private static State startState;
	private static Set<State> finalState;
	private static Transition[] transitions;
	
	public NFA() {
		setAlphabet(alphabet);
		setStates(states);
		setStartState(startState);
		setFinalState(finalState);
		setTransitions(transitions);
		
	}
	
	public void printGoodNFA(){
		System.out.println("NFA States: ");
		for(int i = 0; i < states.length; i++) {
			System.out.println("name: " + states[i].getName() + ", transitions: ");
			for(Transition x:states[i].getTransitions()) {
			System.out.println(x.toStrNFA());
			}
			
		}
		System.out.println("Start state: " + startState.getName());
		System.out.print("Final state/s: ");
		for(State i:finalState) {
			System.out.print(i.getName() + " ");
		}
	}

	public static String [] getAlphabet() {
		return alphabet;
	}

	public static void setAlphabet(String [] alphabet) {
		NFA.alphabet = alphabet;
	}

	public static State [] getStates() {
		return states;
	}

	public static void setStates(State [] states) {
		NFA.states = states;
	}

	public static State getStartState() {
		return startState;
	}

	public static void setStartState(State startState) {
		NFA.startState = startState;
	}

	public static Transition[] getTransitions() {
		return transitions;
	}

	public static void setTransitions(Transition[] transitions) {
		NFA.transitions = transitions;
	}

	public static Set<State> getFinalState() {
		return finalState;
	}

	public static void setFinalState(Set<State> finalState) {
		NFA.finalState = finalState;
	}
}

	
