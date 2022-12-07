package Project2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CFGtoCNM {
	
	static List<Character> nonTerminals = new ArrayList<Character>();
	static List<Character> Terminals = new ArrayList<Character>();
	static List<Map<Character, List<Character>>> rules = new ArrayList<Map<Character, List<Character>>>();
	static char startSymbol;
	
	public static void convertToOutput() {
		nonTerminals.clear();
		for(int i = 0; i < rules.size(); i++) {
			char key = rules.get(i).keySet().stream().findFirst().get();
			if(!nonTerminals.contains(key)) {
			nonTerminals.add(key);
			}
		}
		
		System.out.println("NON-TERMINAL");
		for(char n : nonTerminals) {
			System.out.println(n);
		}
		System.out.println("TERMINAL");
		for(char n : Terminals) {
			System.out.println(n);
		}
		System.out.println("RULES");
		for(int i = 0; i < rules.size(); i++) {
			String str = "";
			Map<Character, List<Character>> rule = rules.get(i);
			char key = rule.keySet().stream().findFirst().get();
			List<Character> value = rule.values().stream().findFirst().get();
			str+= key + ":";
			for(char val : value) {
				str+=val;
			}
			System.out.println(str);
		}
		System.out.println("START");
		System.out.println(startSymbol);
	}
	
	public static void ExtraStartState() {
		List<Character> c = new ArrayList<Character>();
		Map<Character, List<Character>> s = new HashMap<Character, List<Character>>();
		c.add(startSymbol);
		if(!nonTerminals.contains('Q')) {
			s.put('Q', c);
			startSymbol = 'Q';
		}
		else {
			char l = 'A';
			while(nonTerminals.contains(l)) {
				l++;
			}
			s.put(l, c);
			startSymbol = l;
		}
		rules.add(0, s);
	}
	
	public static void EliminateEpsilon(Boolean checkEpsilon) {
			for(int i = 0; i < rules.size(); i++) {
				char key = ' ';
				List<Character> value = null;
				Map<Character, List<Character>> rule = rules.get(i);
				key = rule.keySet().stream().findFirst().get();
				value = rule.values().stream().findFirst().get();
				if(value.size()==1 && value.get(0).equals('e')) {
					checkEpsilon = true;
					rules.remove(i);
				}
				else {
					checkEpsilon = false;
				}
				
				if(checkEpsilon == true) {
					for(int x = 0; x < rules.size(); x++) {
						Map<Character, List<Character>> ruleTemp = rules.get(x);
						char keyTemp = ruleTemp.keySet().stream().findFirst().get();
						List<Character> valueTemp = ruleTemp.values().stream().findFirst().get();
						if(valueTemp.size()==1) {
							if(valueTemp.get(0) == key) {
								Map<Character, List<Character>> newrule = new HashMap<Character, List<Character>>();
								List<Character> newc = new ArrayList<Character>();
								newc.add('e');
								newrule.put(keyTemp, newc);
								if(!rules.contains(newrule)) {
									rules.add(newrule);
								}
							}
						}
						else {
							for(int j = 0; j < valueTemp.size(); j++) {
								if(valueTemp.get(j) == key){
									Map<Character, List<Character>> newrule = new HashMap<Character, List<Character>>();
									List<Character> newc = new ArrayList<Character>();
									for(int k = 0; k < valueTemp.size(); k++) {
										newc.add(valueTemp.get(k));
									}
									newc.remove(j);
									newrule.put(keyTemp, newc);
									if(!rules.contains(newrule)) {
										rules.add(newrule);
									}
								}
							}
						}
					}
				}
			}
	}
	
	public static void EliminateUnitProduction() {
			for(int i = 0; i < rules.size(); i++) {
				char key = ' ';
				List<Character> value = null;
				Map<Character, List<Character>> rule = rules.get(i);
				key = rule.keySet().stream().findFirst().get();
				value = rule.values().stream().findFirst().get();
				if(value.size() == 1) {
					if(Character.isUpperCase(value.get(0))) {
						rules.remove(i);
						for(int j = 0; j < rules.size(); j++) {
							Map<Character, List<Character>> ruleTemp = rules.get(j);
							char keyTemp = ruleTemp.keySet().stream().findFirst().get();
							List<Character> valueTemp = ruleTemp.values().stream().findFirst().get();
							if(keyTemp == value.get(0)) {
								Map<Character, List<Character>> map = new HashMap<Character, List<Character>>();
								map.put(key, valueTemp);
								if(!rules.contains(map)) {
									rules.add(map);
								}
							}
						}
					}
				}
			}
	}
	
	public static void EliminateTerminalAndNonTerminals() {
		char c = 'A';
		for(int i = 0; i < rules.size(); i++) {
			char key = ' ';
			List<Character> value = null;
			for(int y = 0; y < rules.size(); y++) {
				if(rules.get(y).keySet().stream().findFirst().get().equals(c)) {
					char x = (char) (c + 1);
					if(x == '[') {
						c = 'A';
					}
					else {
						c++;
					}
				}
			}
			Map<Character, List<Character>> rule = rules.get(i);
			key = rule.keySet().stream().findFirst().get();
			value = rule.values().stream().findFirst().get();
			char d = ' ';
			if(value.size() > 1) {
				for(int x = 0; x < value.size(); x++) {
					if(!Character.isUpperCase(value.get(x))) {
						Map<Character, List<Character>> map = new HashMap<Character, List<Character>>();
						List<Character> ch = new ArrayList<Character>();
						ch.add(value.get(x));
						map.put(c, ch);
						if(!rules.contains(map)) {
							rules.add(map);
						}
						d = value.get(x);
						break;
					}
				}
				for(int j = 0; j < rules.size(); j++) {
					Map<Character, List<Character>> ruleTemp = rules.get(j);
					char keyTemp = ruleTemp.keySet().stream().findFirst().get();
					List<Character> valueTemp = ruleTemp.values().stream().findFirst().get();
					int k = 0;
					for(char val : valueTemp) {
						if(val == d && valueTemp.size() != 1) {
							rules.get(j).get(keyTemp).set(k, c);
						}
						k++;
					}
				}
					
			}
		}
	}
	
	public static void EliminateMoreThanTwoNonTerminals(){
		char c = 'A';
		for(int i = 0; i < rules.size(); i++) {
			char key = ' ';
			List<Character> value = null;
			for(int y = 0; y < rules.size(); y++) {
				if(rules.get(y).keySet().stream().findFirst().get().equals(c)) {
					char x = (char) (c + 1);
					if(x == '[') {
						c = 'A';
					}
					else {
						c++;
					}
				}
			}
			char mock = c;
			Map<Character, List<Character>> rule = rules.get(i);
			key = rule.keySet().stream().findFirst().get();
			value = rule.values().stream().findFirst().get();
			if(value.size() > 2) {
				char a = rules.get(i).get(key).get(0);
				char b = rules.get(i).get(key).get(1);
				Map<Character, List<Character>> map = new HashMap<Character, List<Character>>();
				List<Character> ch = new ArrayList<Character>();
				ch.add(a);
				ch.add(b);
				Boolean found = false;
				char foundKey = ' ';
				for(int y = 0; y < rules.size(); y++) {
					if(rules.get(y).values().stream().findFirst().get().equals(ch)) {
						found = true;
						foundKey = rules.get(y).keySet().stream().findFirst().get();
						mock = foundKey;
					}
				}
				if(!found) {
					map.put(c, ch);
					mock = c;
					if(!rules.contains(map)) {
						rules.add(map);
					}
				}
				
				for(int j = 0; j < rules.size(); j++) {
					Map<Character, List<Character>> ruleTemp = rules.get(j);
					char keyTemp = ruleTemp.keySet().stream().findFirst().get();
					List<Character> valueTemp = ruleTemp.values().stream().findFirst().get();
					for(int k = 0; k < valueTemp.size()-1; k++) {
						if(valueTemp.get(k) == a && valueTemp.get(k + 1) == b && valueTemp.size() != 2) {
							rules.get(j).get(keyTemp).remove(k);
							rules.get(j).get(keyTemp).set(k, mock);
						}
					}
				}
			}
		}
	}
	
	public static void convertToCNM() {
		ExtraStartState();
		
		Boolean checkEpsilon = false;
		
		for(int i = 0; i < rules.size(); i++) {
			Map<Character, List<Character>> rule = rules.get(i);
			List<Character> value = rule.values().stream().findFirst().get();
			if(value.size()==1 && value.get(0).equals('e')) {
				checkEpsilon = true;
			}
		}
		
		if(checkEpsilon) {
			EliminateEpsilon(checkEpsilon);
		}
		
		Boolean checkUnitProduction = false;
		
		for(int i = 0; i < rules.size(); i++) {
			Map<Character, List<Character>> rule = rules.get(i);
			List<Character> value = rule.values().stream().findFirst().get();
			if(value.size() == 1 && Character.isUpperCase(value.get(0))) {
				checkUnitProduction = true;
			}
		}
		
		if(checkUnitProduction) {
			EliminateUnitProduction();
		}
		
		Boolean checkTerminalAndNonTerminal = false;
		
		for(int i = 0; i < rules.size(); i++) {
			Map<Character, List<Character>> rule = rules.get(i);
			List<Character> value = rule.values().stream().findFirst().get();
			int nonterm = 0;
			int term = 0;
			for(char val : value) {
				if(Character.isUpperCase(val)) {
					nonterm = 1;
				}
				else {
					term = 1;
				}
			}
			if(nonterm == 1 && term == 1) {
				checkTerminalAndNonTerminal = true;
			}
		}
		
		if(checkTerminalAndNonTerminal) {
			EliminateTerminalAndNonTerminals();
		}
		
		Boolean checkMoreThanTwoNonTerminals = false;
		
		for(int i = 0; i < rules.size(); i++) {
			Map<Character, List<Character>> rule = rules.get(i);
			List<Character> value = rule.values().stream().findFirst().get();
			if(value.size() > 2) {
				checkMoreThanTwoNonTerminals = true;
			}
		}
		
		if(checkMoreThanTwoNonTerminals) {
			EliminateMoreThanTwoNonTerminals();
		}
		
	}
	
	
	public static void main(String [] args) throws FileNotFoundException {
		File file = new File("/Users/ecezeynepbasik/Desktop/G1.txt");
		Scanner sc = new Scanner(file);
		List<String> rows = new ArrayList<String>();
		while(sc.hasNextLine()) {
			rows.add(sc.nextLine());
		}
		List<String> nt = rows.subList(rows.indexOf("NON-TERMINAL")+1, rows.indexOf("TERMINAL"));
		for(String n : nt) {
			nonTerminals.add(n.charAt(0));
		}
		List<String> t = rows.subList(rows.indexOf("TERMINAL")+1, rows.indexOf("RULES"));
		for(String tt : t) {
			Terminals.add(tt.charAt(0));
		}
		List<String> ru = rows.subList(rows.indexOf("RULES")+1, rows.indexOf("START"));
		for(String r : ru) {
			List<Character> defs = new ArrayList<Character>();
			String str = r.substring(2);
			for(int i = 0; i < str.length(); i++) {
				defs.add(str.charAt(i));
			}
			Map<Character, List<Character>> tempMap = new HashMap<Character, List<Character>>();
			tempMap.put(r.charAt(0), defs);
			rules.add(tempMap);
		}
		startSymbol = rows.get(rows.size()-1).charAt(0);
		
		convertToCNM();
		
		convertToOutput();
	}

}
