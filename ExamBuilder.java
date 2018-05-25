/*
    Name: Alessandra Laudando
    ACCC: alaudand
*/

import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// swing imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ExamBuilder {
    
    // global variables
    private JFrame window;
    private JPanel gridContainer;
    private Exam exam;
    Scanner scan;

    public ExamBuilder() {
        intialize();
    }

    public static void main(String[] args) {
        new ExamBuilder();

    }

    public void intialize() {
        System.out.println("\n\nWelcome to Exam Builder!!" +
                            "\nBy: Alessandra Laudando" +
                            "\nGroup members: Sakshi Maheshwari, Joseph Camanotto");

        // Create a Border layout frame
        window = new JFrame("Exam Builder");
        window.setLayout(new BorderLayout(0, 20));
        window.setSize(500, 500);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 

        // Create a Grid layout panel ( GridLayout(roww, cols, horizontal gap, vertical gap) )
        gridContainer = new JPanel(new GridLayout(3, 2, 15, 15));
        gridContainer.setBackground(Color.pink);

        // create and add the text heading 
        JLabel heading = new JLabel("Exam Builder", SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); //top,left,bottom,right
        heading.setFont(heading.getFont().deriveFont(40.0f));
        window.add(heading, BorderLayout.PAGE_START);

        // set the buttons 
        setButtons();

        window.add(gridContainer);
        window.setVisible(true);

        // If the user exits the application 
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if(JOptionPane.showConfirmDialog(window, "Are you sure to close this window?", "Exiting?", 
                   JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } 
            }
        }); 
    }

    public void setButtons() {

        // Initialize an instance of Exam
        exam = null;

        // Initialize the scanner
        scan = ScannerFactory.getScanner();

        // Load Exam button 
        JButton loadButton = new JButton("Load Exam");
        loadButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                
                // create prompt text and text feild
                String examFileName = JOptionPane.showInputDialog("Enter the name of your exam file:");

                try {
                    scan = new Scanner(new BufferedReader(new FileReader(examFileName)));
                    System.out.println("\n*** File was successfully loaded ***\n");
                    JOptionPane.showMessageDialog(null, "Exam file was successfully loaded.", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // scan the exam file
                    exam = new Exam(scan);
                }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Exam file was not found.", "Error", JOptionPane.WARNING_MESSAGE);
                    System.out.println("\n*** Error: Exam file was not found ***\n");
                    ex.printStackTrace();
                }
            }
        });

        // Print Exam button 
        JButton printButton = new JButton("Print Exam");
        printButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(exam == null) {
                    System.out.println("\nThe exam does not exist. Please load a saved exam.");
                    JOptionPane.showMessageDialog(null, "The exam does not exist. Please load a saved exam.", "Error", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    String[] options = {"Print to the screen", "Print to a file"};
                    int printInput = JOptionPane.showOptionDialog(null, "Select one of the available printing options:", "Print Options",  
                                                                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, 
                                                                    options, options[0]);

                    // print exam to screen
                    if(printInput == 0) {

                        JFrame frame = new JFrame("Exam");
                        frame.setSize(500, 500);
                        frame.setLocation(300, 300);

                        DefaultListModel listModel = new DefaultListModel();
                        exam.print(listModel);

                        JList list = new JList(listModel);

                        list.addListSelectionListener(new ListSelectionListener(){
                            @Override
                            public void valueChanged(ListSelectionEvent e) {
                                list.clearSelection();
                            }
                        });
                        
                        JScrollPane scrollPane = new JScrollPane(list);
                        scrollPane.setVisible(true);

                        frame.add(scrollPane);
                        frame.setVisible(true);
                    }
                    // print exam to file
                    else if(printInput == 1) {
                        String examFile = "ExamPrint.txt";
                        PrintWriter printWriter = null;
                        try {
                            printWriter = new PrintWriter(examFile);
                        }
                        catch(FileNotFoundException ex) {
                            ex.printStackTrace();
                            System.out.println("*** Exam failed to be created ***");
                        }
                        exam.saveToFile(printWriter);
                        System.out.println("*** Exam printed to ExamPrint.txt ***");
                        JOptionPane.showMessageDialog(null, "Exam file was printed to file: ExamPrint.txt", "Success", JOptionPane.INFORMATION_MESSAGE);
                        printWriter.close();
                    }
                }
            }
        });

        // Add Question button 
        JButton addButton = new JButton("Add Question");
        addButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(exam == null) {
                    System.out.println("\nThe exam does not exist. Please load a saved exam.");
                    JOptionPane.showMessageDialog(null, "The exam does not exist. Please load a saved exam.", "Error", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    exam.addQuestion();
                    JOptionPane.showMessageDialog(null, "Question was added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        // Remove Question button 
        JButton removeButton = new JButton("Remove Question");
        removeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(exam == null) {
                    System.out.println("\nThe exam does not exist. Please load a saved exam.");
                    JOptionPane.showMessageDialog(null, "The exam does not exist. Please load a saved exam.", "Error", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    exam.removeQuestion();
                    JOptionPane.showMessageDialog(null, "Question was removed.", "Success", JOptionPane.INFORMATION_MESSAGE);

                }
            }
        });

        // Reorder Exam button 
        JButton reorderButton = new JButton("Reorder Exam");
        reorderButton .addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(exam == null) {
                    System.out.println("\nThe exam does not exist. Please load a saved exam.");
                    JOptionPane.showMessageDialog(null, "The exam does not exist. Please load a saved exam.", "Error", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    String[] options = {"Questions", "Answers", "Both"};
                    int reorderInput = JOptionPane.showOptionDialog(null, "Select one of the available reordering options:", "Reorder Options",  
                                                                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, 
                                                                        options, options[0]);
                        
                    if(reorderInput == 0) {
                        exam.reorderQuestions();
                    }
                    else if(reorderInput == 1) {
                        exam.reorderMCAnswers(-1);
                    }
                    else if(reorderInput == 2) {
                        exam.reorderQuestions();
                        exam.reorderMCAnswers(-1);
                    }
                }
            }
        });

        // Save Exam button 
        JButton saveButton = new JButton("Save Exam");
        saveButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(exam == null) {
                    System.out.println("\nThe exam does not exist. Please load a saved exam.");
                    JOptionPane.showMessageDialog(null, "The exam does not exist. Please load a saved exam.", "Error", JOptionPane.WARNING_MESSAGE);
                }
                else {
                    String examFile = "savedExamFile.txt";
                    PrintWriter printWriter = null;
                    try {
                        printWriter = new PrintWriter(examFile);
                    }
                    catch(FileNotFoundException ex) {
                        ex.printStackTrace();
                    }

                    System.out.println("\n *** Exam successfully saved in file: saveExamFile.txt ***");
                    JOptionPane.showMessageDialog(null, "Exam successfully saved in file: savedExamFile.txt", "Success", 
                                                  JOptionPane.INFORMATION_MESSAGE);

                    exam.save(printWriter);
                    printWriter.close();
                }
            }
        });
        
        // add buttons to the panel
        gridContainer.add(loadButton);
        gridContainer.add(printButton);
        gridContainer.add(addButton);
        gridContainer.add(removeButton);
        gridContainer.add(reorderButton);
        gridContainer.add(saveButton);
    }
}


 


