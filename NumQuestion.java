// Sakshi

import java.io.PrintWriter;
import java.util.Scanner;

public class NumQuestion extends Question  {

    protected double tolerance;

    public NumQuestion(String text, double maxValue){
        super(text, maxValue);
    }

    public NumQuestion(Scanner sc){
        super(sc);
        maxValue = sc.nextDouble();
        sc.nextLine();
        text = sc.nextLine();
    }

    public Answer getNewAnswer(){
        NumAnswer ans = new NumAnswer(0.0,0.0);//can also add tolerance by type casting it
        return ans;
    }

    public void getAnswerFromStudent(){
        Scanner scan =  ScannerFactory.getScanner();
        // double input
        Double ans = scan.nextDouble();
        scan.nextLine();
        studentAnswer = new NumAnswer(ans, ((NumAnswer)rightAnswer).tolerance);
    }

    public double getValue( ){
        return studentAnswer.getCredit(rightAnswer);
    }

    public void save(PrintWriter p){
        p.println();
        p.println("NumQuestion");
        p.println(maxValue);
        p.println(text);
        rightAnswer.save(p);
    }

    public void saveStudentAnswers(PrintWriter p){
        p.println();
        p.println("NumAnswer");
        p.println(((NumAnswer)(studentAnswer)).value);
    }
    
    public void restoreStudentAnswers(Scanner sc){
        double ans = sc.nextDouble();
        NumAnswer newans = new NumAnswer(ans, ((NumAnswer)rightAnswer).tolerance);
        studentAnswer = newans;
    }
        // Added by Joe for HW5
    public void getAnsGUI(String text) {
        if (text.equals("")) {
            studentAnswer = getNewAnswer();
            return;
        }
        double textDouble = Double.parseDouble(text);
        studentAnswer = new NumAnswer(textDouble, ((NumAnswer)rightAnswer).tolerance);
    }
}
