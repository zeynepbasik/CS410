package Project1;

import java.util.Set;

public class State {
	private String name;
	private Set<Transition> transitions;
	
	public State(String name, Set<Transition> transitions) {
		this.name = name;
		this.transitions = transitions;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Transition> getTransitions() {
		return transitions;
	}
	public void setTransitions(Set<Transition> transitions) {
		this.transitions = transitions;
	}
}
