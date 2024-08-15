/*
 * Aim: This is the Code for calculating The Grade Of Student
 * Author: Prasham Togadiya
 * B.Tech. CSE Student At Darshan University
 * Rajkot, Gujrat, India
 * Date: 05-08-2024
 * 
 */

 import java.util.Scanner;

 public class StudentGradeCalculator {
 
     /**
      * This method calculates the total marks from an array of marks.
      *
      * @param marks The array of integers representing marks obtained in each subject.
      * @return The total marks obtained.
      */
     public static int calculateTotalMarks(int[] marks) {
         int total = 0;
         for (int mark : marks) {
             total += mark;
         }
         return total;
     }
 
     /**
      * This method calculates the average percentage from the total marks and the number of subjects.
      *
      * @param totalMarks The total marks obtained across all subjects.
      * @param numberOfSubjects The number of subjects.
      * @return The average percentage.
      */
     public static double calculateAveragePercentage(int totalMarks, int numberOfSubjects) {
         return (double) totalMarks / numberOfSubjects;
     }
 
     /**
      * This method assigns a grade based on the average percentage.
      *
      * @param averagePercentage The average percentage obtained.
      * @return The grade as a string.
      */
     public static String calculateGrade(double averagePercentage) {
         if (averagePercentage >= 90) {
             return "A+";
         } else if (averagePercentage >= 80) {
             return "A";
         } else if (averagePercentage >= 70) {
             return "B";
         } else if (averagePercentage >= 60) {
             return "C";
         } else if (averagePercentage >= 50) {
             return "D";
         } else {
             return "F";
         }
     }
 
     /**
      * Main method to take input from the user, calculate the total marks, average percentage, and assign a grade.
      *
      * @param args Command-line arguments (not used).
      */
     public static void main(String[] args) {
         Scanner scanner = new Scanner(System.in);
 
         // Input number of subjects
         System.out.print("Enter the number of subjects: ");
         int numberOfSubjects = scanner.nextInt();
 
         // Input marks for each subject
         int[] marks = new int[numberOfSubjects];
         System.out.println("Enter the marks obtained in each subject (out of 100): ");
         for (int i = 0; i < numberOfSubjects; i++) {
             System.out.print("Subject " + (i + 1) + ": ");
             marks[i] = scanner.nextInt();
         }
 
         // Calculate total marks, average percentage, and grade
         int totalMarks = calculateTotalMarks(marks);
         double averagePercentage = calculateAveragePercentage(totalMarks, numberOfSubjects);
         String grade = calculateGrade(averagePercentage);
 
         // Display results
         System.out.println("\nTotal Marks: " + totalMarks);
         System.out.println("Average Percentage: " + averagePercentage + "%");
         System.out.println("Grade: " + grade);
 
         scanner.close();
     }
 }
 