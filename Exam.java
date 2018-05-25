/*
    Name: Alessandra Laudando
    ACCC: alaudand
*/

import java.util.*;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class Exam {

    private ArrayList<Question> questions;
    private String title;
    private String studentName;

    public Exam(String text) {
        this.title = text;
        questions = new ArrayList<Question>();
    }

    public Exam(Scanner scan) {

        questions = new ArrayList<Question>();

        // read in the title of the exam
        String str = scan.nextLine();
        title = str;

        while(scan.hasNext())
        {
            str = scan.nextLine();

            if(str.equals("MCSAQuestion")) {
                MCSAQuestion mcQ = new MCSAQuestion(scan);
                questions.add(mcQ);

                // number of answers
                int numAnswers = scan.nextInt();
                scan.nextLine();

                // create the answers for the question
                for(int i = 0; i < numAnswers; i++) {
                    MCSAAnswer answer = new MCSAAnswer(scan);
                    mcQ.answers.add(answer);
                }
            }
            else if(str.equals("MCMAQuestion")) {
                MCMAQuestion maQ = new MCMAQuestion(scan);
                questions.add(maQ);

                // number of answers
                int numAnswers = scan.nextInt();

                // create the answer, and set it to the right answer
                for(int i = 0; i < numAnswers; i++) {
                    // create the answers for the question
                    MCMAAnswer answer = new MCMAAnswer(scan);
                    maQ.answers.add(answer);
                }
            }
            else if(str.equals("SAQuestion")) {
                SAQuestion saQ = new SAQuestion(scan);
                questions.add(saQ);

                // create the answer, and set it to the right answer
                SAAnswer answer = new SAAnswer(scan);
                saQ.rightAnswer = answer;
            }
            else if(str.equals("NumQuestion")) {
                NumQuestion numQ = new NumQuestion(scan);
                questions.add(numQ);

                // create the answer, and set it to the right answer
                NumAnswer answer = new NumAnswer(scan);
                numQ.rightAnswer = answer;
            }
        }
    }

    public void print() {
        System.out.println("\n*** " + title + " ***");

        int index = 0;

        // print out items in the arrayList: questions
        for(Question q: questions) {
            System.out.print("\nQ" + (index + 1) + ": ");
            q.print();
            index++;
        }
    }

    public void print(DefaultListModel listModel) {
        listModel.addElement("\n*** " + title + " ***\n");

        int index = 0;

        // print out items in the arrayList: questions
        for(Question q: questions) {
            listModel.addElement("\n");
            String questionNum = "Q" + (index + 1) + ": ";
            q.print(listModel, questionNum);
            index++;
        }
    }

    public void reorderQuestions() {
        Collections.shuffle(questions);
    }

    public void reorderMCAnswers(int position) {

        if (position < 0)
        {
            for(Question q: questions) {
                if(q instanceof MCSAQuestion) {
                    ((MCSAQuestion)q).reorderAnswers();
                }
                else if(q instanceof MCMAQuestion) {
                    ((MCMAQuestion)q).reorderAnswers();
                }
            }
        }
        else
        {
            if(position <= questions.size())
            {
                int index = position - 1;

                if(questions.get(index) instanceof MCSAQuestion)
                {
                    ((MCSAQuestion)(questions.get(index))).reorderAnswers();
                }
                if(questions.get(index) instanceof MCMAQuestion)
                {
                    ((MCMAQuestion)(questions.get(index))).reorderAnswers();
                }
            }
        }
    }

    public double reportQuestionValues() {

        System.out.println("\n\n*** Exam Results ***");
        System.out.println("\n Question  |   Score");
        System.out.println("______________________");

        // print out each questions' value
        double examTotal = 0;
        Question q;

        for(int i = 0; i < questions.size(); i++) {
            q = questions.get(i);

            System.out.println( "\n    " + (i + 1) + "\t   |" + "\t" + q.getValue());

            examTotal = examTotal + q.getValue();
        }

        System.out.println("______________________");
        System.out.println("\n  Total    |   " + examTotal);

        return examTotal;
    }

    public void reportQuestionValues(DefaultListModel e) {

        e.addElement("\n\n*** Exam Results ***");
        e.addElement("\n Question  |   Score");
        e.addElement("______________________");

        // print out each questions' value
        double examTotal = 0;
        Question q;

        for(int i = 0; i < questions.size(); i++) {
            q = questions.get(i);

            e.addElement( "\n    " + (i + 1) + "\t   |   " + "\t" + q.getValue());

            examTotal = examTotal + q.getValue();
        }

        e.addElement("______________________");
        e.addElement("\n  Total    |   " + examTotal);

    }
    
    public void save(PrintWriter printWriter) {
        printWriter.println(title);
        printWriter.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        for(Question q: questions) {
            q.save(printWriter);
        }
    }

    public void saveToFile(PrintWriter printWriter) {
        printWriter.println("\n*** " + title + " ***");

        int index = 0;

        // print out items in the arrayList: questions
        for(Question q: questions) {
            printWriter.print("\nQ" + (index + 1) + ": ");
            printWriter.println(q.text);

            if(q instanceof MCQuestion) {
                ((MCQuestion)q).saveAnswersToFile(printWriter);
            }
            index++;
        }
    }

    public void getAnswerFromStudent(int index) {

        Question q = questions.get(index);
        q.getAnswerFromStudent();
    }

    public int getNumQuestions() {
        return questions.size();
    }

    public void saveStudentAnswers(PrintWriter printWriter, String fileName) {
        
        printWriter.println(fileName);
        printWriter.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        for(Question q: questions) {
            q.saveStudentAnswers(printWriter);
        }
    }

    public void restoreStudentAnswers(Scanner scan)
    {
        String str;
        int index = 0;

        while( scan.hasNext() ) {
            str = scan.nextLine();

            if (str.equals("MCSAAnswer") || str.equals("MCMAAnswer") || str.equals("SAAnswer") || str.equals("NumAnswer")){
                questions.get(index).restoreStudentAnswers(scan);
                index++;
            }
        }
    }

    public void addQuestion() {
        String questionText;
        String answerText;
        int questionType;
        String numAnswers = " ";
        String maxValue;
        String baseCredit;
        String creditIfSelected;
        String numberAnswer;
        String tolerance;

        String[] options = {"MC: Single answer", "MC: Multiple answer", "Essay:  Short answer (text)", "Essay:  Numerical answer"};
        questionType = JOptionPane.showOptionDialog(null, "Select one of the available question options:", "Add Question Options",  
                                                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // MCSAQuestion and MCMAQuestion
        if(questionType == 0 || questionType == 1) {
            questionText = JOptionPane.showInputDialog("Enter your question text: ");
            maxValue = JOptionPane.showInputDialog("Enter the maximum value for your question: ");

            // add answers to your question
            boolean isTrue = true;
            while(isTrue) {
                numAnswers = JOptionPane.showInputDialog("How many answers would you like to add? (min: 3 max: 5)");

                if(Integer.parseInt(numAnswers) > 5 && Integer.parseInt(numAnswers) < 3) {
                    System.out.println("The number of answers you have chosen is not between 3 and 5. Please choose another value.");
                    JOptionPane.showMessageDialog(null, "The number of answers you have chosen is not between 3 and 5. Please choose another value.", 
                                                  "Error", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    isTrue = false;
                }
            }

            // MC SA
            if(questionType == 0) {
                MCSAQuestion question = new MCSAQuestion(questionText, Double.parseDouble(maxValue));
                questions.add(question);

                for(int i = 0; i < Integer.parseInt(numAnswers); i++) {
                    answerText = JOptionPane.showInputDialog("Enter the text for answer number " + (i + 1));
                    creditIfSelected = JOptionPane.showInputDialog("Enter the amount of credit if this answer is selected:");

                    // create and add the answer to the question
                    MCSAAnswer answer = new MCSAAnswer(" " + answerText, Double.parseDouble(creditIfSelected));
                    question.addAnswer(answer);
                }
            }
            // MC MA
            else if(questionType == 1) {
                baseCredit = JOptionPane.showInputDialog("Enter the base credit for your question:");

                MCMAQuestion question = new MCMAQuestion(questionText, Double.parseDouble(maxValue), Double.parseDouble(baseCredit));
                questions.add(question);

                for(int i = 0; i < Integer.parseInt(numAnswers); i++) {
                    answerText = JOptionPane.showInputDialog("Enter the text for answer number " + (i + 1));
                    creditIfSelected = JOptionPane.showInputDialog("Enter the amount of credit if this answer is selected:");

                    // create and add the answer to the question
                    MCMAAnswer answer = new MCMAAnswer(" " + answerText, Double.parseDouble(creditIfSelected));
                    question.addAnswer(answer);
                }
            }
        }
        // SAQuestion and NumQuestion
        else if(questionType == 2 || questionType == 3) {
            questionText = JOptionPane.showInputDialog("Enter your question text: ");
            maxValue = JOptionPane.showInputDialog("Enter the maximum value for your question: ");

            // Essay Short Answer
            if(questionType == 2) {
                SAQuestion question = new SAQuestion(questionText, Double.parseDouble(maxValue));
                questions.add(question);

                // add answer
                answerText  = JOptionPane.showInputDialog("Enter the text for the answer: ");

                SAAnswer answer = new SAAnswer(answerText);
                question.rightAnswer = answer;
            }
            // Essay Numerical Answer
            else if(questionType == 3) {
                NumQuestion question = new NumQuestion(questionText, Double.parseDouble(maxValue));
                questions.add(question);

                // add answer
                numberAnswer = JOptionPane.showInputDialog("Enter the value for the answer:");
                tolerance = JOptionPane.showInputDialog("Enter the tolerance for the answer:");

                NumAnswer answer = new NumAnswer(Double.parseDouble(numberAnswer), Double.parseDouble(tolerance));
                question.rightAnswer = answer;
            }
        }
    }

    public void removeQuestion() {
        String questionNumber = " ";
        boolean isTrue = true;

        while(isTrue) {
            questionNumber = JOptionPane.showInputDialog("Which question would you like to remove? (choose numbers 1-" + questions.size() + ").");


            if(Integer.parseInt(questionNumber) < 1 && Integer.parseInt(questionNumber) > questions.size()) {
                JOptionPane.showMessageDialog(null, "Question does not exist. Please choose another question.", "Error", JOptionPane.WARNING_MESSAGE);
            }
            else {
                questions.remove(Integer.parseInt(questionNumber)-1);
                System.out.println("\nQuestion " + questionNumber + " has been removed.");
                isTrue = false;
            }
        }
    }

    public void questionCreditToCsv(PrintWriter pw) {

        String questionNumber;
        String questionCredit;

        for (int i = 0; i < questions.size(); i++){
            questionNumber = Integer.toString(i+1);
            questionCredit = Double.toString((questions.get(i)).getValue());

            pw.append("Question " + questionNumber + ":");
            pw.append(',');
            pw.append(questionCredit);
            pw.append('\n');
        }
    }

    // Added by Joe for HW5
    public void printQGUI(int index, JTextArea area) {

        Question q = questions.get(index);
        q.printGUI(area);
    }

    // Added by Joe for HW5
    public void getAnsGUI(int index, String ans) {
        questions.get(index).getAnsGUI(ans);
    }
}