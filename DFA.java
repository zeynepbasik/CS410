package Project1;

import java.util.ArrayList;
import java.util.Set;

public class DFA {
	private static String [] alphabet;
	private static ArrayList<State> states;
	private static State startState;
	private static Set<State> finalState;
	private static Transition[] transitions;
	
	public DFA() {
		setAlphabet(alphabet);
		setStates(states);
		setStartState(startState);
		setFinalState(finalState);
		setTransitions(transitions);
		
	}
	
	public void printGoodDFA(){
		System.out.println("ALPHABET");
		for(int i = 0; i < alphabet.length; i++) {
			System.out.println(alphabet[i]);
		}
		System.out.println("STATES");
		for(int i = 0; i < states.size(); i++) {
			System.out.println(states.get(i).getName());
		}
		System.out.println("START");
		System.out.println(startState.getName());
		System.out.println("FINAL");
		for(State s : finalState) {
			System.out.println(s.getName());
		}
		System.out.println("TRANSITIONS");
		for(State i: states) {
			for(Transition t : i.getTransitions()) {
				System.out.println(i.getName() + " " + t.toStrDFA());
			}
		}
		System.out.println("END");
	}

	public static String [] getAlphabet() {
		return alphabet;
	}

	public static void setAlphabet(String [] alphabet) {
		DFA.alphabet = alphabet;
	}

	public static ArrayList<State> getStates() {
		return states;
	}

	public static void setStates(ArrayList<State> states2) {
		DFA.states = states2;
	}

	public static State getStartState() {
		return startState;
	}

	public static void setStartState(State startState) {
		DFA.startState = startState;
	}

	public static Transition[] getTransitions() {
		return transitions;
	}

	public static void setTransitions(Transition[] transitions) {
		DFA.transitions = transitions;
	}

	public static Set<State> getFinalState() {
		return finalState;
	}

	public static void setFinalState(Set<State> finalState) {
		DFA.finalState = finalState;
	}
}
