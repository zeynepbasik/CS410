package Project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TuringMachine {
	private static String [] inputAlp;
	private static String [] tapeInput;
	private static String blank;
	private static String [] states;
	private static String startState;
	private static String acceptState;
	private static String rejectState;
	private static List<String[]> rules = new ArrayList<String[]>();
	private static String inputStr;
	
	public static void TestString() {
		int charat = 1;
		String nextState = startState;
		System.out.print("ROUT:   " + startState + " ");
		boolean check = true;
		while(check) {
			for(int i = 0; i < rules.size(); i++) {
				if(rules.get(i)[0].equals(nextState)) {
					if(rules.get(i)[1].equals(inputStr.substring(charat, charat+1))) {
						inputStr = inputStr.substring(0, charat) + rules.get(i)[2] + inputStr.substring(charat+1);
						if(rules.get(i)[3].equals("R")) {
							charat = charat + 1;
						}
						else {
							charat = charat - 1;
						}
						nextState = rules.get(i)[4];
						System.out.print(nextState + " ");
						if(nextState.equals(acceptState) || nextState.equals(rejectState)) {
							check = false;
						}
						break;
					}
				}
			}
		}
		System.out.println();
		if(nextState.equals(rejectState)) {
			System.out.println("RESULT:   rejected");
		}
		else {
			System.out.println("RESULT:   accepted");
		}
		
	}
	
	public static void main(String [] args) throws FileNotFoundException {
		File file = new File("/Users/ecezeynepbasik/Desktop/turing.txt");
		Scanner sc = new Scanner(file);
		int inputNum = sc.nextInt();
		inputAlp = new String[inputNum];
		sc.nextLine();
		String inputalp = sc.nextLine();
		if(inputNum == 1) {
			inputAlp[0] = inputalp;
		}
		else {
			inputAlp = inputalp.split(" ");
		}
		
		
		int tapeInputNum = sc.nextInt();
		tapeInput = new String[tapeInputNum];
		sc.nextLine();
		String tapealp = sc.nextLine();
		tapeInput = tapealp.split(" ");
		
		
		
		blank = sc.nextLine();
		if(blank.contains(" ")) {
			blank = blank.substring(0, blank.indexOf(' '));
		}
		
		int stateNum = sc.nextInt();
		states = new String[stateNum];
		sc.nextLine();
		String st = sc.nextLine();
		states = st.split(" ");
		
		
		startState = sc.nextLine();
		if(startState.contains(" "))
			startState = startState.substring(0, startState.indexOf(' '));
		acceptState = sc.nextLine();
		if(acceptState.contains(" "))
			acceptState = acceptState.substring(0, acceptState.indexOf(' '));
		rejectState = sc.nextLine();
		if(rejectState.contains(" "))
			rejectState = rejectState.substring(0, rejectState.indexOf(' '));
		
		List<String> rule = new ArrayList<String>();
		while(sc.hasNextLine()) {
			rule.add(sc.nextLine());
		}
		
		String inputRule = rule.get(rule.size()-1);
		if(inputRule.contains(" "))
			inputRule = inputRule.substring(0, inputRule.indexOf(" "));
		inputStr = blank + inputRule + blank;
		
		rule.remove(rule.size()-1);
		
		for(int i = 0; i < rule.size(); i++) {
			String r [] = rule.get(i).split(" ");
			rules.add(r);
		}
		
		
		
		TestString();
		
	}

}
