/*Name: Sakshi Maheshwari
NetID: smahes20*/

import java.io.PrintWriter;
import java.util.*;
import java.util.Random;  //adds Random utility from java library

import javax.swing.DefaultListModel;
import javax.swing.*; // Added by Joe for HW5

public abstract class Answer {

    protected Answer(){
    }
    public Answer(Scanner sc){
    }
    public abstract void print();
    public abstract void print(DefaultListModel listModel, String answerNum);
    public abstract double getCredit(Answer rightAnswer);
    public abstract void save(PrintWriter p);

        // Added by Joe for HW5
    public abstract void printGUI(JTextArea area);

}