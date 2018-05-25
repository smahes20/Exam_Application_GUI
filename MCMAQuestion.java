// Joe

import java.util.*;
import java.io.*;

public class MCMAQuestion extends MCQuestion {

	protected ArrayList<Answer> studentAnswer;
	public double baseCredit;

	public MCMAQuestion(String text, double maxValue, double baseCredit) {
		super(text, maxValue);
		studentAnswer = new ArrayList<Answer>();
		this.baseCredit = baseCredit;
	}

	public MCMAQuestion(Scanner inp) {
		super(inp);
		maxValue = inp.nextDouble();

		inp.nextLine();
		
		text = inp.nextLine();
		baseCredit = inp.nextDouble();
		studentAnswer = new ArrayList<Answer>();
	}

	public Answer getNewAnswer() {
		return new MCMAAnswer("Default Answer", 0);
	}

	public Answer getNewAnswer(String text, double creditIfSelected) {
		return new MCMAAnswer(text, creditIfSelected);
	}

	public void getAnswerFromStudent() {
		System.out.println("This is a multiple answer question");
		System.out.println("Type a single answer, then hit enter.");
		System.out.println("Repeat that as much as needed!");
		System.out.println("Your answers will be saved when you enter a blank line.");
		ArrayList<Character> chars = new ArrayList<Character>();
		Scanner sc = ScannerFactory.getScanner();
		String scannerString;
		while (!(scannerString = sc.nextLine()).isEmpty()) {
			scannerString = scannerString.toUpperCase();
			char ans = scannerString.charAt(0);
			int answerPos = Character.getNumericValue(ans) - 10; // go to 0 if A, 1 if B, etc.
			studentAnswer.add(answers.get(answerPos));
		}
	}

    public double getValue() {
        double sum = 0;
        for (int i = 0; i < studentAnswer.size(); i++) {
            sum += super.getValue((MCAnswer)studentAnswer.get(i));
        }
        return sum + baseCredit;
    }

    public void save(PrintWriter pw) {
		pw.println();		  	       
		pw.println("MCMAQuestion");   
    	pw.println(super.maxValue);
    	pw.println(super.text);
    	pw.println(baseCredit);
    	pw.println(super.answers.size());
    	for (int i=0; i < super.answers.size(); i++) {
    		super.answers.get(i).save(pw);
    	}
    }

    public void saveStudentAnswers(PrintWriter pw) {	     // Added an s -------------- ADDED
    	pw.println();
    	pw.println("MCMAAnswer");
    	pw.println(studentAnswer.size());
    	for (int x = 0; x < studentAnswer.size(); x++) {
    		MCMAAnswer ans = (MCMAAnswer)studentAnswer.get(x);
    		String toPrint = ans.text;
    		pw.println(toPrint.trim());
    	}
    }

    public void restoreStudentAnswers(Scanner scan) {
		ArrayList<String> multipleAnswers = new ArrayList<String>();
		String answer;
        String questionAnswer;
        int i;

		int numAnswers = scan.nextInt();
		scan.nextLine();

		// read in student answer text and save them
		for(i = 0; i < numAnswers; i++) {
			answer = scan.nextLine();
			multipleAnswers.add(answer);
		}

		// loop through answers arrayList
		for(Answer a: answers){
			for(i = 0; i < multipleAnswers.size(); i++) {
				
				if((multipleAnswers.get(i)).equals( ((MCAnswer)a).text.trim())) {
					studentAnswer.add(a);
				}	
			}
		}
    }


    	// Added by Joe for HW5
	public void getAnsGUI(String text) {
		studentAnswer = new ArrayList<Answer>(); // wipe out all previous answers
		if (text.equals("")) return;
		text = text.toUpperCase();
		for (String str: text.split(",")) { // split at commas ,
			char ans = str.charAt(0);
			int answerPos = Character.getNumericValue(ans) - 10;
			studentAnswer.add(answers.get(answerPos));
		}
	}
}