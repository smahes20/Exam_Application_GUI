/* Sakshi Maheshwari
 Smahes20*/
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;


public class ExamGraderGui{
    public ExamGraderGui(){
        JFrame f = new JFrame("ExamGrader");

    // using absolute positioning

        /*************EXAM FILE**************/
        //submit button for Exam file
        JButton exambutton = new JButton("Submit Exam file!");
        
        exambutton.setBounds(100,100,140, 40);

        //input exam file by user
        JLabel examlabel = new JLabel();
        examlabel.setText("Enter Exam File:");
        examlabel.setBounds(10, 10, 100, 100);

        //empty label which will show event after button clicked
        JLabel label1 = new JLabel();
        label1.setBounds(10, 110, 200, 100);

        //textfield to enter name
        JTextField examtxt = new JTextField();
        examtxt.setBounds(110, 50, 130, 30);

        /*************ANSWER FILE**************/
        //submit button for answer file
        JButton ansbutton = new JButton("Submit Ans file!");
        ansbutton.setBounds(100,245,140, 40);
        ansbutton.setVisible(false); // initally invisible

        //input exam file by user
        JLabel anslabel = new JLabel();
        anslabel.setText("Enter Ans File:");
        anslabel.setBounds(10, 165, 100, 100);
        anslabel.setVisible(false); // initally invisible

        //textfield to enter name
        JTextField anstxt = new JTextField();
        anstxt.setBounds(110, 198, 130, 30);
        anstxt.setVisible(false); // initally invisible

        //empty label which will show event after button clicked
        JLabel label2 = new JLabel();
        label2.setBounds(10, 250, 200, 100);
        label2.setVisible(false); // initally invisible

        /*************GRADE**************/
        JButton grade = new JButton("GRADE!");
        grade.setBounds(330,50,140, 40);
        grade.setVisible(false); // initally invisible

        /***********create a new pane************/
        // to represent everything in a list in the pannel
        DefaultListModel listModel = new DefaultListModel();

        //Create the list and put it in a scroll pane.
        JList list = new JList(listModel);

        //just to clear the selection by the nouse
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                list.clearSelection();
            }
        });

        JScrollPane p = new JScrollPane(list);
        //scroll panel -> list-> list model
        p.setVisible(false);
        p.setBounds(310,120,190,170);
        p.setVisible(false);


        //ADD to frame
        //for exam
        f.add(label1);
        f.add(examtxt);
        f.add(examlabel);
        f.add(exambutton);

        // for ans
        f.add(label2);
        f.add(anstxt);
        f.add(anslabel);
        f.add(ansbutton);

        //to grade
        f.add(grade);
        f.add(p);//show te grdes

        //for the frame
        f.setSize(600,400);
        f.setResizable(false);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //action listener for Exam Button
        exambutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                label1.setText("Examfile has been submitted.");
                String Examfile = examtxt.getText();
                System.out.println("Input Exam file: " + Examfile);
                //make the secondbutton visible
                ansbutton.setVisible(true);
                anslabel.setVisible(true);
                anstxt.setVisible(true);
                label2.setVisible(true);

            }
        });

        //action listener for Ans Button
        ansbutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                label2.setText("Answerfile has been submitted.");
                String Ansfile = anstxt.getText();
                System.out.println("Input Ans file: " + Ansfile);
                grade.setVisible(true);

            }
        });

        //action listener for Grade Button
        grade.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                p.setVisible(true);

                String examFile = examtxt.getText();
                String answerFile = anstxt.getText();

                Scanner examScan = null;
                Scanner answerScan = null;

                // load the exam file
                try{
                    examScan = new Scanner(new BufferedReader(new FileReader(examFile)));
                }
                catch (FileNotFoundException e) {
                    //listModel.addElement("\nError: Exam file not found."); // to print it on the label
                    JOptionPane.showMessageDialog(f, "Error: Exam file not found.");
                    //e.printStackTrace();
                }

                // load the answer file
                try{
                    answerScan = new Scanner(new BufferedReader(new FileReader(answerFile)));
                }
                catch (FileNotFoundException e) {
                    //listModel.addElement("\nError: Answer file not found");
                    JOptionPane.showMessageDialog(f, "Error: Answer file not found.");
                    //e.printStackTrace();
                }

                // Retreive the examfile name from the answerfile
                String studentName = answerScan.nextLine();
                String examFileName = answerScan.nextLine();

                // compare the name of the exam file and the second line of the exam file
                if (!(examFile.equals(examFileName))){
                    //listModel.addElement("No Match");
                    JOptionPane.showMessageDialog(f, "No Match!");
                    System.exit(0);
                }

                Exam e1 = new Exam(examScan);
                e1.restoreStudentAnswers(answerScan);
                PrintWriter pw = null;
                // create csv file
                try {
                    pw = new PrintWriter(new File("save.csv"));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                double totalScore = e1.reportQuestionValues();
                e1.reportQuestionValues(listModel);
                String total = Double.toString(totalScore);

                // write student name to csv file
                pw.append(studentName);
                pw.append('\n');

                // write total score to csv file
                pw.append("Total Score:");
                pw.append(',');
                pw.append(total);
                pw.append('\n');
                e1.questionCreditToCsv(pw);

                pw.close();
                listModel.addElement("Grades are saved into save.csv!");

            }
        });
    }


    public static void main(String[] args) {

        ExamGraderGui grader = new ExamGraderGui();
        System.out.println("*** Exam Grader ***" +
                            "\nBy: Sakshi Maheshwari" +
                             "\nGroup members: Joseph Camanotto, Alessandra Laudando");
    }
}
