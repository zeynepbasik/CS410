package Project1;

import java.util.HashSet;
import java.util.Set;

public class Transition {
    private State to;
    private Set<State> dest;
    private String element;
    
    public Transition(State to, String element) {
    	this.to = to;
    	this.element = element;
    }
    
    public Transition(Set<State> dest, String element) {
    	this.dest = dest;
    	this.element = element;
    }
    
    public Set<State> convertToDest(){
    	Set<State> x = new HashSet<State>();
    	x.add(this.to);
    	return x;
    }
    public String toStrNFA() {
    	return "element: " + element + " , to: " + to.getName();
    }
    
    public String toStrDFA() {
    	String str = "{";
    	int count = 0;
    	for(State s: dest) {
    		if(count == dest.size()-1) {
    			str += s.getName();
    		}
    		else {
    			str += s.getName() + ", ";
    		}
    		count++;
    	}
    	return element + " " + str + "}";
    }
    
	public State getTo() {
		return to;
	}
	public void setTo(State to) {
		this.to = to;
	}
	
	public void setDest(Set<State> dest){
		this.dest = dest;
	}
	
	public Set<State> getDest() {
		return dest;
	}


	public String getElement() {
		return element;
	}


	public void setElement(String element) {
		this.element = element;
	}
}
