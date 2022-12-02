package Project1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
	
	private static String [] alphabetName;
	private static String [] statesName;
	private static String startStateName;
	private static String [] finalStateName;
	private static String [] transitionsDefs;
	
	public static void printNFA() {
		System.out.println("ALPHABET: ");
		for(int i = 0; i < alphabetName.length;i++) {
			System.out.print(alphabetName[i] + " ");
		}
		System.out.println(" ");
		System.out.println("STATES: ");
		for (int i = 0; i<statesName.length;i++) {
			System.out.print(statesName[i] + " ");
		}
		System.out.println(" ");
		System.out.println("START STATE: " + startStateName);
		System.out.println("FINAL STATE: " + finalStateName);
		System.out.println("TRANSITIONS: ");
		for (int i = 0; i<transitionsDefs.length; i++) {
			System.out.print(transitionsDefs[i] + ", ");
		}
	}
	
	public static void addStateSetToState(Set<State> stateSet, String el, State x, DFA dfa) {
		Transition t = new Transition(stateSet, el);
		x.getTransitions().add(t);
	}
	
	public static void startConvertToDFA(NFA nfa, DFA dfa) {
		
		dfa.setAlphabet(nfa.getAlphabet());
		ArrayList<State> dfastates = new ArrayList<State>();
		dfa.setStates(dfastates);
		State start = nfa.getStartState();
		Set<Transition> startTrans = start.getTransitions();
		Set<Transition> initialTrans = new HashSet<Transition>();
		String r = "{" + start.getName()+ "}";
		State x = new State(r, initialTrans);
		for(int i = 0; i < nfa.getAlphabet().length; i++) {
			String el = nfa.getAlphabet()[i];
			Set<State> stateSet = new HashSet<State>();
			for (Transition t: startTrans) {
				if(t.getElement().equals(el)) {
					stateSet.add(t.getTo());
				}
			}
			addStateSetToState(stateSet, el, x, dfa);
		}
		dfa.getStates().add(x);
		dfa.setStartState(x);
		continueConvert(dfa, nfa);
	}
	
	public static void continueConvert(DFA dfa, NFA nfa) {
		Set<Transition> firstTransitions = dfa.getStates().get(0).getTransitions();
		ArrayList<Set<State>> generatedStates = new ArrayList<Set<State>>();
		for(Transition f: firstTransitions) {
			generatedStates.add(f.getDest());
		}
		ArrayList<Set> statesToAdd = new ArrayList<Set>();
		for(Set s: generatedStates) {
			String str="";
			for(Object f : s) {
				State firstEl = (State) f;
				str += firstEl.getName();
			}
			Boolean equal = false;
			for(int i = 0; i < nfa.getStates().length; i++) {
				if(nfa.getStates()[i].getName().equals(str)) {
					equal = true;
				}
			}
			if(!equal) {
				statesToAdd.add(s);
			}
			
		}
		
			for(int i = 0; i < statesToAdd.size(); i++) {
				Boolean yes = null;
				Set<State> stateSet = statesToAdd.get(i);
				String stateSetName = "{";
				int count = 0;
				for(State x:stateSet) {
					if(count == stateSet.size()-1) {
						stateSetName+= x.getName() + "}";
						break;
					}
					stateSetName+= x.getName() + ", ";
					count ++;
				}
				Set<Transition> nn = new HashSet<Transition>();
				int alf = 0;
				int u = 0;
				for(String alp: alphabetName) {
					Set<State> statesToUnion = new HashSet<State>();
					for(State s: stateSet) {
						for(State a: nfa.getStates()) {
							if(s == a) {
								for(Transition t: a.getTransitions()) {
									if(t.getElement().equals(alp)) {
										Boolean h = false;
										for(State p: statesToUnion) {
											if(p.getName().equals(t.getTo().getName())) {
												h= true;
											}
										}
										if(h == true) {
										}
										else {
											statesToUnion.add(t.getTo());
										}
										
									}
								}
							}
						}
					}
					Transition tr = null;
					String de = "no";
					if(statesToUnion.isEmpty()) {
						Boolean hasit = false;
						for(int n = 0; n < dfa.getStates().size(); n++) {
							if(dfa.getStates().get(n).getName().equals("Death")) {
								statesToUnion.add(dfa.getStates().get(i));
								hasit = true;
							}
						}
						if(hasit== false) {
							String d = "Death";
							Set<Transition> emptyTrans = new HashSet<Transition>();
							State death = new State(d, emptyTrans);
							Set<State> mydest = new HashSet<State>();
							mydest.add(death);
							Transition zero = new Transition(mydest, "0");
							Transition one = new Transition(mydest, "1");
							death.getTransitions().add(one);
							death.getTransitions().add(zero);
							dfa.getStates().add(death);
							statesToUnion.add(death);
							de = "uu";
						}
						
					}
					String stateUnionName = "{";
					int counts = 0;
					for(State x:statesToUnion) {
						if(counts == statesToUnion.size()-1) {
							stateUnionName += x.getName() + "}";
							break;
						}
						stateUnionName+= x.getName() + ", ";
						counts ++;
					}
					
					
					yes = false;
					for(State e: dfa.getStates()) {
						if(e.getName().equals(stateUnionName)) {
							yes = true;
							u++;
							tr = new Transition(statesToUnion, alp);
						}
					}
					if(yes == false) {
						tr = new Transition(statesToUnion, alp);
						if(de == "no") {
							statesToAdd.add(statesToUnion);
						}
					}
					nn.add(tr);
					alf ++;
				}
				
					if(u != alphabetName.length) {
						dfa.getStates().add(new State(stateSetName, nn));
					}
			}
		}
	
	public static Set<State> findFinalState(DFA dfa) {
		Set<State> fin = new HashSet<State>();
		for(int i = 0; i < dfa.getStates().size(); i++) {
			String str = dfa.getStates().get(i).getName();
			for(int x = 0; x < str.length(); x++) {
				for(int y = 0; y < finalStateName.length; y++) {
					if(finalStateName[y].equals(str.charAt(x) + "")) {
						fin.add(dfa.getStates().get(i));
					}
				}
				
			}
		}
		
		return fin;
	}
	
	public static void main(String [] args) throws FileNotFoundException {
		File file = new File("/Users/ecezeynepbasik/Desktop/NFA2.txt");
	    Scanner sc = new Scanner(file);
	    
	    ArrayList<String> Rows = new ArrayList<String>();
	 
	    while (sc.hasNextLine()) {
	    	Rows.add(sc.nextLine());
	    }
	    
	    int alphabet_length = Rows.indexOf("STATES") - Rows.indexOf("ALPHABET") - 1;
	    alphabetName = new String [alphabet_length];
	    
	    for (int i = 0; i < alphabet_length ; i++) {
	    	alphabetName[i] = Rows.get(Rows.indexOf("ALPHABET") + i + 1);
	    }
	    
	    int states_length = Rows.indexOf("START") - Rows.indexOf("STATES") - 1;
	    statesName = new String [states_length];
	    
	    for (int i = 0; i < states_length ; i++) {
	    	statesName[i] = Rows.get(Rows.indexOf("STATES") + i + 1);
	    }
	    
	    startStateName = Rows.get(Rows.indexOf("START") + 1);
	    int finalStates_length = Rows.indexOf("TRANSITIONS") - Rows.indexOf("FINAL") - 1;
	    finalStateName = new String[finalStates_length];
	    for(int i = 0; i < finalStates_length; i++) {
	    	finalStateName[i]= (Rows.get(Rows.indexOf("FINAL") + i + 1));
	    }
	    
	    
	    int transitions_length = Rows.indexOf("END") - Rows.indexOf("TRANSITIONS") - 1;
	    transitionsDefs = new String [transitions_length];
	    for (int i = 0; i < transitions_length ; i++) {
	    	transitionsDefs[i] = Rows.get(Rows.indexOf("TRANSITIONS") + i + 1);
	    }
	    
	    NFA myNFA = new NFA();
	    
	    myNFA.setAlphabet(alphabetName);
	    State statess [] = new State[states_length];
	    for(int i = 0; i < states_length; i++) {
		    Set<Transition> nullTrans = new HashSet<Transition>();
	    	State s = new State(statesName[i], nullTrans);
	    	statess[i] = s;
	    }
	  
	    myNFA.setStates(statess);
	    
	    for (int i = 0; i < transitions_length; i++) {
	    	String src = transitionsDefs[i].substring(0,1);
	    	String elem = transitionsDefs[i].substring(2,3);
	    	String dest = transitionsDefs[i].substring(4);
		    Set<Transition> nullTrans = new HashSet<Transition>();
	    	State ds = new State(src, nullTrans);
	    	for(int x = 0; x < states_length; x++) {
	    		if(dest.equals(myNFA.getStates()[x].getName())) {
	    			ds = myNFA.getStates()[x];
	    		}
	    	}
	    	Transition tr = new Transition(ds, elem);
	    	
	    	for(int x = 0; x < states_length; x++) {
	    		if(src.equals(myNFA.getStates()[x].getName())) {
	    			ds = myNFA.getStates()[x];
	    			myNFA.getStates()[x].getTransitions().add(tr);
	    		}
	    	}
	    	
	    }
	    Set<State> f = new HashSet<State>();
	    myNFA.setFinalState(f);
	    for(int i = 0; i < states_length; i++) {
	    	if(startStateName.equals(myNFA.getStates()[i].getName())) {
	    		myNFA.setStartState(myNFA.getStates()[i]);
	    	}
	    	for(int x = 0; x < finalStates_length; x++) {
	    		if(finalStateName[x].equals(myNFA.getStates()[i].getName())) {
		    		myNFA.getFinalState().add(myNFA.getStates()[i]);
		    	}
	    	}
	    	
	    }
	    
	    System.out.println("");
	    
	    DFA dfa = new DFA();
	    startConvertToDFA(myNFA, dfa);
	    Set<State> fin = findFinalState(dfa);
	    dfa.setFinalState(fin);
	    dfa.printGoodDFA();
	    
	}

}
