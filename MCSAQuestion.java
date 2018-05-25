// Joe 

import java.util.Scanner;
import java.io.*;

public class MCSAQuestion extends MCQuestion {

	public MCSAQuestion(String text, double maxValue) {
		super(text, maxValue);
	}

	public MCSAQuestion(Scanner inp) {
		super(inp);
		maxValue = inp.nextDouble();

		// take in the next line ------------ ADDED
		inp.nextLine();

		text = inp.nextLine();
	}

	public Answer getNewAnswer() {
		return new MCSAAnswer("Default Answer", 0);
	}

	public Answer getNewAnswer(String text, double creditIfSelected) {
		return new MCSAAnswer(text, creditIfSelected);
	}

	public void getAnswerFromStudent() {
		Scanner sc = ScannerFactory.getScanner();
		String scannerString = sc.nextLine();
		if (scannerString.isEmpty()) return;
		scannerString = scannerString.toUpperCase();
		char ans = scannerString.charAt(0);
		int answerPos = Character.getNumericValue(ans) - 10; // go to 0 if A, 1 if B, etc.
		studentAnswer = answers.get(answerPos);
		return;
	}

    public double getValue() {
        return super.getValue((MCAnswer)studentAnswer);
    }

    public void save(PrintWriter pw) {
		pw.println();		  	       
		pw.println("MCSAQuestion");  
    	pw.println(super.maxValue);
    	pw.println(super.text);
    	pw.println(super.answers.size());
    	for (int i=0; i < super.answers.size(); i++) {
    		super.answers.get(i).save(pw);
    	}
    }

	public void saveStudentAnswers(PrintWriter printWriter) {

        printWriter.print("\n");
        printWriter.println("MCSAAnswer");
        printWriter.println(((MCAnswer)studentAnswer).text.trim());
    }

    public void restoreStudentAnswers(Scanner scan) {

        String fileAnswer = scan.nextLine();
        String questionAnswer;

        for(Answer a: answers) {
            questionAnswer = ((MCAnswer)a).text;

            if( (questionAnswer.trim()).equals(fileAnswer) ) {
                // set studentAnswer equal to a reference of the answer they chose
                studentAnswer = a;
            }
        }
    }

    	// Added by Joe for HW5
	public void getAnsGUI(String text) {
		if (text.equals("")) {
			studentAnswer = new MCSAAnswer("", 0);
			return;
		}

		text = text.toUpperCase();
		char ans = text.charAt(0);
		int answerPos = Character.getNumericValue(ans) - 10;
		studentAnswer = answers.get(answerPos);
		return;
	}
	
}