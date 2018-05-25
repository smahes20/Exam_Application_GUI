CS 342: Homework 5
Project part 4: Developing a GUI Using Exam-Related Classes 

*************************************************************************
Description: 

    Up until this point we have been developing a set of Exam-related classes, and testing them with a test
    driver, ExamTester and various other applicatons like: ExamBuilder, ExamGrader and ExamTaker.
    now we will introduce a GUI for each of the application creasted above. 
            1. ExamBuilder – Used by an instructor to create and modify exams.
            2. ExamTaker – Used by students to take an exam and record their answers.
            3. ExamGrader – Used by an instructor to evaluate students’ answers and determine scores.


**************************************************************************
Files included in this Project: 

Answer.java
Question.java
Exam.java
MCAnswer.java
MCSAAnswer.java
SAAnswer.java
MCMAAnswer.java
NumAnswer.java
MCQuestion.java
MCSAQuestion.java
SAQuestion.java
MCMAQuestion.java
NumAnswer.java
ScannerFactory.java
Exam.txt
makefile
README

**************************************************************************
How to run the program:

The program can be run by using the makefile which uses ExamBuilder.java, ExamGrader.java and ExamTaker.java
and its dependencies Answer.java, Question.java Exam.java classes.
Given below is the general command to run the program:

        1. To run Exam Builder application:
                "make examB"
                "java ExamBuilder"

        2. To run Exam Taker application:
                "make ExamT"
                "java ExamTaker"

        3. To run Exam Grader application:
                "make examG"
                "java ExamGraderGui"

**************************************************************************
Division of Labor:

Name: Sakshi Maheshwari
ACCC ID: smahes20

    Application BUilt: Exam Grader GUI
    Classes Supplied: Answer, NumQuestion, NumAnswer, SAQuestion, SAAnswer

Name: Alessandra Laudando
ACCC ID: alauda2

    Application Built: Exam Builder GUI
    Classes Supplied: Exam, Question, and MCQuestion

Name: Joseph Campanotto
ACCC ID: jcampa5

    Application Built: Exam Taker GUI
    Classes Supplied: MCAnswer, MCSAQuestion, MCSAAnswer, MCMAQuestion, MCMAAnswer, and ScannerFactory

Together we worked on integrating the code and adding modifications where they were needed.

**************************************************************************
Description of each Application: 

    Exam Builder: builds up the exam by loading in saved exam from a file. This application 
                  interactively adds question, removes questions, and reorders questions, answers 
                  or both depending on user input.  It also prints the exam to the screen and saves it. 

    Exam Taker: takes the student input and saves it in a text file. This application gives access 
                to the student to skip answers and come back to them later, change their answers if 
                needed and leave the question if they wish too. 

    Exam Grader: uses the exam file to evaluate student answers. This application allows the 
                 grader to choose the exam and student answer file to grade the student input 
                 answers. The student grades are displayed on the screen and are also saved in 
                 a .csv file with student name, score and list of scores for each question. 

***************************************************************************