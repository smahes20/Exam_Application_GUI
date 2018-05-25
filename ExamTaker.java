import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class ExamTaker extends JFrame {

	CardLayout cardLayout;
	JPanel contentPanel;
	String examName, studentName;
	Exam currentExam;
	
	public static void main(String[] args) throws FileNotFoundException {
    	System.out.println("ExamTaker by Joseph Campanotto; netID: jcampa5");
    	System.out.println("Group Mates: Alessandra Laudando and Sakshi Maheshwari");
    	System.out.print("\n\n\n");

		new ExamTaker().setVisible(true);
	}

	public void createCard(int index) {
		JPanel q = new JPanel();
		JTextArea qTextArea = new JTextArea(15, 20);
		String cardName = new StringBuilder("Q").append(index+1).toString();
		qTextArea.setText(cardName + " ");
		currentExam.printQGUI(index, qTextArea);
		qTextArea.setEditable(false);
		qTextArea.setLineWrap(true);
		qTextArea.setWrapStyleWord(true);
		JTextField qTextField = new JTextField(20);
		JButton qNextButton = new JButton("Next");
		qNextButton.addActionListener(new NextScreen());
		qNextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ans = qTextField.getText();
				currentExam.getAnsGUI(index, ans);
			}
		});

		q.add(qTextArea);
		q.add(qTextField);
		q.add(qNextButton);
		contentPanel.add(q, cardName);

	}

	public void createExamCards() throws FileNotFoundException {
		int numQuestions = currentExam.getNumQuestions();
		for (int x=0; x < numQuestions; x++) {
			createCard(x);
		}

		createSkipScreen();
		createSaveScreen();
	}

	public void createSkipScreen() {
		JPanel skipScreen = new JPanel();

		JTextArea skipTextArea = new JTextArea(15, 20);
		skipTextArea.setEditable(false);
		skipTextArea.setLineWrap(true);
		skipTextArea.setWrapStyleWord(true);
		skipTextArea.setText("Enter a number below to go to that question and then hit next. Leave the field blank to go to the save screen.");
		JTextField skipTextField = new JTextField(20);
		JButton skipButton = new JButton("Next");
		skipButton.addActionListener(new NextScreen());
		skipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!skipTextField.getText().equals("")) {
					int qNum = Integer.parseInt(skipTextField.getText());
					String cardName = new StringBuilder("Q").append(qNum).toString();
					cardLayout.show(contentPanel, cardName);
				}
			}



		});

		skipScreen.add(skipTextArea);
		skipScreen.add(skipTextField);
		skipScreen.add(skipButton);
		contentPanel.add(skipScreen, "skipScreen");
	}

	public void createSaveScreen() throws FileNotFoundException {
		JPanel saveScreen = new JPanel();

		JLabel saveLabel = new JLabel("Enter the answer file name and hit save!");
		JTextField saveTextField = new JTextField(20);
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        try {
					String answerFileName = saveTextField.getText();
			        File answerFile = new File(answerFileName);
			        PrintWriter answerFileOut = new PrintWriter(answerFileName);
			        answerFileOut.println(studentName);
			        currentExam.saveStudentAnswers(answerFileOut, examName);
			        answerFileOut.close(); // Close out so it actually saves the file
				} catch (FileNotFoundException ex) {
					System.out.println("Error: " + ex.getMessage());
				}
		    }
		});

		saveScreen.add(saveLabel);
		saveScreen.add(saveTextField);
		saveScreen.add(saveButton);
		contentPanel.add(saveScreen, "saveScreen");
	}

	public ExamTaker() throws FileNotFoundException {
		// Setting up the JFrame and CardLayout
		this.setSize(400,400);
		this.setLocationRelativeTo(null); // centers frame
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Exam Taker");

		cardLayout = new CardLayout();
		contentPanel = new JPanel();
		contentPanel.setLayout(cardLayout);
		this.setContentPane(contentPanel);


		/* Screen 1 */
		JPanel infoScreen = new JPanel();

		JLabel examLabel = new JLabel("Enter Exam Name: ");
		JTextField examText = new JTextField(20);
		JLabel studentLabel = new JLabel("Enter Student Name: ");
		JTextField studentText = new JTextField(20);
		JButton contButton = new JButton("Continue");
		contButton.addActionListener(new NextScreen());
		contButton.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				examName = examText.getText();
				studentName = studentText.getText();
				try {
					Scanner examFileInp = new Scanner(new BufferedReader(new FileReader(examName)));
					currentExam = new Exam(examFileInp);
				} catch (FileNotFoundException ex) {
					System.out.println("Error: " + ex.getMessage());
				}
			}
		});

		infoScreen.add(examLabel);
		infoScreen.add(examText);
		infoScreen.add(studentLabel);
		infoScreen.add(studentText);
		infoScreen.add(contButton);
		contentPanel.add(infoScreen, "infoScreen");

		/* Screen 2 */
		JPanel instructionsScreen = new JPanel();

		JTextArea iTextArea = new JTextArea(15, 20);
		iTextArea.setEditable(false);
		iTextArea.setLineWrap(true);
		iTextArea.setWrapStyleWord(true);
		iTextArea.setText("For multiple-choice multi-answer questions, if you want to select answer A and B, type your answer like: 'A,B' without quotes! Leave an answer blank to skip!");
		JButton beginButton = new JButton("Begin Exam");
		beginButton.addActionListener(new NextScreen());
		beginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					createExamCards(); 
				} catch (FileNotFoundException ex) {
					System.out.println("Error: " + ex.getMessage());
				}
			}
		});

		instructionsScreen.add(iTextArea);
		instructionsScreen.add(beginButton);
		contentPanel.add(instructionsScreen, "instructionsScreen");

	}


	public class NextScreen implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			cardLayout.next(contentPanel);
		}
	}
}
