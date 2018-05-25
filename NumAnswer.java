//sakshi 
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.*; // Added by Joe for HW5

import static java.lang.Math.abs;

public class NumAnswer extends Answer {
    protected double value;
    protected double tolerance;

    public NumAnswer(double value){
        this.value = value;
    }

    public NumAnswer(double value, double tolerance){
        this.tolerance = tolerance;
        this.value = value;
    }

    public NumAnswer(Scanner sc){
        super(sc);
        value = sc.nextDouble();
        tolerance = sc.nextDouble();
    }

    public void print(){
        System.out.println(value);
    }

    public void print(DefaultListModel listModel, String answerNum){
        listModel.addElement(answerNum + value);
    }

    public double getCredit(Answer rightAnswer){
        double correctAnswer = ((NumAnswer)(rightAnswer)).value;
        double diff = abs(correctAnswer - value);

        if ((diff < tolerance)||(diff == tolerance) ||(correctAnswer == value)){ // as we compare
            return 1.0;
        }
        else{
            return 0.0;
        }
    }

    public void save(PrintWriter p){
        p.println(value);
        p.println(tolerance);
    }

        // Added by Joe for HW5; Shouldn't need it but defining it avoid compile errors
    public void printGUI(JTextArea area) {
        area.append(value + "\n");
    }
}