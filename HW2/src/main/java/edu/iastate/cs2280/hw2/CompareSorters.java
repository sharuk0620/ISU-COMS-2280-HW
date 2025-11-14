/**
 * @author
 */
package edu.iastate.cs2280.hw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.io.PrintWriter;

/**
 * This class serves as the main driver for the sorting algorithm comparison application.
 * It provides a prompt interface for users to generate or load student data,
 * run four different sorting algorithms (Selection, Insertion, Merge, Quick),
 * and compare their performance based on execution time. The results can be
 * displayed in the console and optionally exported to a CSV file.
 */
public class CompareSorters {

    /**
     * The main entry point of the application. It presents a menu to the user to
     * choose between generating random student data, reading data from a file, or exiting.
     * For each set of data, it runs the sorting algorithms and prints their performance statistics.
     * Should handle InputMismatchExceptions and FileNotFoundExceptions gracefully.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        System.out.println("Sorting Algorithms Performance Analysis using Student Data\n");
        System.out.println("keys:  1 (random student data)  2 (file input)  3 (exit)");

        //Initializes Scanner object scan and assigns to a Scanner object reading from System.in; Handles user input
        Scanner scan = new Scanner(System.in);

        //Initializes StudentScanner[] array algoList and assigns to size 4; Holds the 4 algorithm StudentScanners for export
        StudentScanner[] algoList = new StudentScanner[4];

        // Write your logic here

        //Initializes int variable trialNum to hold the current trial number and increment accordingly at end of program
        int trialNum = 1;

        //While(true) loop to handle multiple trial calls until user inputs "3" to exit program
        while(true){
            //Initializes boolean variable errorFlag, which holds if an exception was ever thrown during program.
            boolean errorFlag = false;

            //Displays current trial #
            System.out.print("Trial " + trialNum + ": ");

            //Asks user for their input (1, 2, or 3), and stores as a string to avoid errors/crashing with invalid inputs
            String userMainOption = scan.nextLine();

            //Initializes StudentScanner references for the 4 sorting algorithms
            StudentScanner selectSort;
            StudentScanner insertSort;
            StudentScanner mergeSort;
            StudentScanner quickSort;

            //Switch case, depending on the user's program choice (1, 2, or 3)
            switch(userMainOption){
                //If the user inputted 1, meaning they would like a randomly generated list of students
                case "1":
                    try {
                        //Initializes Random object myRand to pass into generateRandomStudents static method
                        Random myRand = new Random();

                        //Asks user for the number of generated students, and stores their choice in numStudentChoice
                        System.out.print("Enter number of random students: ");
                        int numStudentChoice = scan.nextInt();
                        scan.nextLine(); //Clears scanner buffer to avoid messing with upcoming user inputs

                        //Initializes Student array userGenStudents, assigns it to call of generateRandomStudents
                        Student[] userGenStudentList = CompareSorters.generateRandomStudents(numStudentChoice, myRand);

                        //Assigns the 4 Student Scanner objects with the generated student list and respective algorithm
                        selectSort = new StudentScanner(userGenStudentList, Algorithm.SelectionSort);
                        insertSort = new StudentScanner(userGenStudentList, Algorithm.InsertionSort);
                        mergeSort = new StudentScanner(userGenStudentList, Algorithm.MergeSort);
                        quickSort = new StudentScanner(userGenStudentList, Algorithm.QuickSort);

                        //Assigns 4 Student Scanner objects to a position in algoList, the StudentScanner array
                        algoList[0] = selectSort;
                        algoList[1] = insertSort;
                        algoList[2] = mergeSort;
                        algoList[3] = quickSort;

                        //Calls .scan() on the Student Scanner objects to perform the 2-pass sort for each algorithm
                        selectSort.scan();
                        insertSort.scan();
                        mergeSort.scan();
                        quickSort.scan();;
                        System.out.println();

                        //Prints out the header for the algorithm stat message
                        System.out.printf("%-15s %-5s %-10s", "algorithm", "size" , "time (ns)\n");

                        //Dashed line for format
                        System.out.print("------------------------------------\n");

                        //Prints out the stats of each sorting algorithm (Name, size, and runtime in nanoseconds)
                        System.out.println(selectSort.stats());
                        System.out.println(insertSort.stats());
                        System.out.println(mergeSort.stats());
                        System.out.println(quickSort.stats());

                        //Dashed line for format
                        System.out.println("------------------------------------\n");

                        //Prints out the median student profile from the first sorting algorithm (all 4 share the same)
                        System.out.println("Median Student Profile: " + selectSort.getMedianStudent());

                    //Catches a possible InputMismatchException, and throws the corresponding error message
                    } catch (InputMismatchException e){
                        System.out.println("Error: " + e.getMessage());
                        errorFlag = true; //Sets error flag to true, as exception was thrown
                    //Catches a possible IllegalArgumentException, and throws the corresponding error message
                    } catch (IllegalArgumentException e){
                        System.out.println("Error: " + e.getMessage());
                        errorFlag = true; //Sets error flag to true, as exception was thrown
                    }
                    break; //Breaks out of the switch case
                //If the user inputted 2, meaning they would like to input Student data through a file.
                case "2":
                    //Asks the user for the file name and stores the file name in String variable userInputFile
                    System.out.print("File name: ");
                    String userInputFile = scan.nextLine();

                    //Try-Catch Block
                    try{
                        //Initializes Student array userFileStudentList and assigns to call from readStudentsFromFile with user file
                        Student[] userFileStudentList = CompareSorters.readStudentsFromFile(userInputFile);

                        //Assigns the 4 Student Scanner objects with the student list from the file and respective algorithm
                        selectSort = new StudentScanner(userFileStudentList, Algorithm.SelectionSort);
                        insertSort = new StudentScanner(userFileStudentList, Algorithm.InsertionSort);
                        mergeSort = new StudentScanner(userFileStudentList, Algorithm.MergeSort);
                        quickSort = new StudentScanner(userFileStudentList, Algorithm.QuickSort);

                        //Assigns 4 Student Scanner objects to a position in algoList, the StudentScanner array
                        algoList[0] = selectSort;
                        algoList[1] = insertSort;
                        algoList[2] = mergeSort;
                        algoList[3] = quickSort;

                        //Calls .scan() on the Student Scanner objects to perform the 2-pass sort for each algorithm
                        selectSort.scan();
                        insertSort.scan();
                        mergeSort.scan();
                        quickSort.scan();;
                        System.out.println();

                        //Prints out the header for the algorithm stat message
                        System.out.printf("%-15s %-5s %-10s", "algorithm", "size" , "time (ns)\n");

                        //Dashed line for format
                        System.out.print("------------------------------------\n");

                        //Prints out the stats of each sorting algorithm (Name, size, and runtime in nanoseconds)
                        System.out.println(selectSort.stats());
                        System.out.println(insertSort.stats());
                        System.out.println(mergeSort.stats());
                        System.out.println(quickSort.stats());

                        //Dashed line for format
                        System.out.println("------------------------------------\n");

                        //Prints out the median student profile from the first sorting algorithm (all 4 share the same)
                        System.out.println("Median Student Profile: " + selectSort.getMedianStudent());

                    //Catches a possible FileNotFoundException, and throws the corresponding error message
                    } catch (FileNotFoundException e) {
                        System.out.println("Error: " + e.getMessage());
                        errorFlag = true; //Sets errorFlag to true, as exception was thrown

                    //Catches a possible InputMismatchException, and throws the corresponding error message
                    } catch (InputMismatchException e){
                        System.out.println("Error: " + e.getMessage());
                        errorFlag = true; //Sets errorFlag to true, as exception was thrown

                    //Catches a possible IllegalArgumentException, and throws the corresponding error message
                    } catch (IllegalArgumentException e){
                        System.out.println("Error: " + e.getMessage());
                        errorFlag = true; //Sets errorFlag to true, as exception was thrown
                    }
                    break; //Breaks out of switch case

                //If the user inputted 3, meaning they would like to exit the program.
                case "3":
                    break; //Breaks out of switch case immediately

                //If the user inputted any other value (other numbers or invalid input types), Prints error message
                default:
                    System.out.println("Invalid choice: Please enter 1, 2, or 3.");
                    errorFlag = true; //Sets errorFlag to true as an error occurred during program
            }

            //If user's choice was 3 (to exit the program), breaks out of while loop, and exits program.
            if(userMainOption.equals("3")){
                break;
            }

            //If errorFlag is still false, meaning no exception or error occurred during program, prompts user for CSV export
            if(!errorFlag){
                CompareSorters.handleExportOption(scan, algoList);
            }

            //Increments trial # by 1 after current trial is finished (unless user decided to exit program)
            trialNum++;
        }

        //Prints out message to the user if they decide to exit program, and stops program
        System.out.println("Exiting program.");

        //Closes scan object
        scan.close();
    }

    /**
     * Handles the user prompt for exporting sorting performance results to a CSV file.
     * This method catches and handles a {@link FileNotFoundException} if the specified
     * output file cannot be created or written to, printing an error message to the console.
     *
     * @param scan     The Scanner object to read user input.
     * @param scanners An array of StudentScanner objects containing the performance stats.
     */
    private static void handleExportOption(Scanner scan, StudentScanner[] scanners) {
        //Prints out a message to the user in the console, asking if they'd like to export algorithm results to a CSV file
        System.out.print("Would you like to export the results of the program to a CSV file? (y/n): ");

        //Obtain's user's choice (y or n) with Scanner object "scan" and stores it in userExportChoice
        String userExportChoice = scan.nextLine().trim().toUpperCase();

        //Switch-Case for user's choice
        switch(userExportChoice){
            //If user types yes (inputs y --> converts to Y after .toUpperCase() method; non-case sensitive)
            case "Y":

                //Asks user to enter a CSV file that they would like the results to be exported to
                System.out.print("Please enter CSV file to export program results to (e.g results.csv): ");

                //Stores the file name in userExportFile
                String userExportFile = scan.nextLine().trim();

                //Try-Catch with Resources; Creates and automatically closes PrintWriter object fileWriter, passes in export file
                try(PrintWriter fileWriter = new PrintWriter(new File(userExportFile))) {

                    //Prints out header for CSV file
                    fileWriter.println("Algorithm,Size,Time(ns)");

                    //Iterates through passed in StudentScanner array (Will iterate 4 times; 4 algorithms)
                    for(int i = 0; i < scanners.length; i++){
                        /*
                        Writes data of accessed StudentScanner object to a new line in the following format:
                        --> Algorithm,Size,Time(ns),
                         */
                        fileWriter.println(scanners[i].getAlgorithm() + "," + scanners.length + "," + scanners[i].getScanTime() + ",");
                    }

                    //Message to user in console that the file was exported with no problem or errors
                    System.out.println("Successfully exported data to " + userExportFile);
                    System.out.println();

                //Catches a possible FileNotFound exception
                } catch (FileNotFoundException e){
                    //Prints out error message to user
                    System.out.println("Output file error: Inputted export file cannot be found or written to.");
                }
                //Break out of switch case
                break;
            //If user types no (inputs n --> converts to N after .toUpperCase() method; non-case sensitive)
            case "N":
                //Prints out a message
                System.out.println("Program results will not be exported to a CSV file.");
                System.out.println();
                //Simply breaks out of switch case, and method will end
                break;
        }

    }

    /**
     * Generates an array of Student objects with random GPA and credit values.
     *
     * @param numStudents The number of random students to generate.
     * @param rand        The Random object to use for generating values.
     * @return An array of randomly generated Student objects.
     * @throws IllegalArgumentException if numStudents is less than 1.
     */
    public static Student[] generateRandomStudents(int numStudents, Random rand) {
        //If numStudents is less than one, throws an illegalArgumentException
        if (numStudents < 1) {
            throw new IllegalArgumentException("Number of students cannot be less than 1!");
        }

        //Initializes and assigns Student array randStudentList of length numStudents
        Student[] randStudentList = new Student[numStudents];

        //Iterates through randStudentList
        for (int i = 0; i < randStudentList.length; i++) {
            //Initializes and assigns double variable randGPA to generate a double value 0.0-4.0 inclusive
            double randGPA = Math.round(rand.nextDouble(0.0, 2.0001) * 2.0 * 100.0) / 100.0;

            //Initializes and assigns int variable randCredits to generate an int value 0-131 exclusive
            int randCredits = rand.nextInt(0, 131);

            //Assigns element at index i in randStudentList to a new Student element of the median GPA and credits value.
            randStudentList[i] = new Student(randGPA, randCredits);
        }

        //Returns the newly generated list of Student elements
        return randStudentList;
    }

    /**
     * Helper Method: Reads student data from a text file. Checks to see if file contains data and isn't an
     * empty/blank file. If not, initializes and returns Student array to contain Student objects
     * from file.
     *
     * @param input A passed in file object that was initialized with the inputted file by user
     * @return An array of Student objects that has a size of however many lines the file contains
     * @throws FileNotFoundException  if the specified file does not exist.
     */
    private static Student[] checkFile(File input) throws FileNotFoundException {
        //If inputFile doesn't exist, meaning the file cannot be found, throws a FileNotFound exception
        if(!input.exists()){
            throw new FileNotFoundException("File not found: " + input.getName());
        }

        //Initializes and creates scanner objects lineCounter and readScanner and passes in inputFile into both.
        Scanner lineCounter = new Scanner(input);

        //Initializes int variable "count", which will hold the number of lines in passed in file, and assigns it to 0.
        int count = 0;

        //While lineCounter is able to read the next line in the inputted file (meaning the next line exists and isn't blank)
        while(lineCounter.hasNextLine()){
            //Initializes and assigns String variable curLine to the next line in the file
            String curLine = lineCounter.nextLine();

            //If curLine isn't empty, increments count by 1 as it is a line with data.
            if(!curLine.isEmpty()){
                count++;
            }
        }

        //After first iteration of file, if count == 0 (meaning file is empty), throws a InputMismatchException
        if(count == 0){
            throw new InputMismatchException("Input file format is incorrect. File is either empty or contains no valid student data.");
        }

        //Closes lineCounter Scanner object
        lineCounter.close();

        //Returns a new Student array of length count;
        return new Student[count];
    }


    /**
     * Reads student data from a text file. Each line of the file should contain a
     * GPA (double) followed by credits taken (int), separated by whitespace.
     *
     * @param filename The name of the file to read from.
     * @return An array of Student objects created from the file data.
     * @throws FileNotFoundException  if the specified file does not exist.
     * @throws InputMismatchException if the file content is not in the expected format
     *                                (e.g., non-numeric data, empty file).
     */
    private static Student[] readStudentsFromFile(String filename) throws FileNotFoundException, InputMismatchException {
        //Initializes and creates File object input file and passes in inputted file from method call.
        File inputFile = new File(filename);

        //Initializes Student[] array fileReadStudents and assigns it an empty array of size count. (If exception wasn't thrown)
        Student[] fileReadStudents = CompareSorters.checkFile(inputFile);

        //Initializes Scanner object readScanner, passing in inputFile.
        Scanner readScanner = new Scanner(inputFile);

        //Initializes and assigns int variable arrayIndex to keep index value of current space in fileReadStudents array
        int arrayIndex = 0;

        //Initializes double variable fileGpa, which holds a student GPA value read from a line of the file.
        double fileGpa;

        //Initializes double variable fileGpa, which holds a student GPA value read from a line of the file.
        int fileCredits;

        //While readScanner object is able to read another line in file
        while(readScanner.hasNextLine()){
            //Initializes and assigns String variable readLine to hold the next Line (without any excess white space)
            String readLine = readScanner.nextLine().trim();

            //If the current line is empty (No student data), throws a InputMismatchException
            if(readLine.isEmpty()){
                throw new InputMismatchException("Input file format is incorrect. File contains empty student value entry.");
            }

            //Initializes scanner Object lineScanner to read through the current line of file
            Scanner lineScanner = new Scanner(readLine);

            //Try-Catch-Block
            try{
                //Assigns fileGpa to the next readable double value in the line.
                fileGpa = lineScanner.nextDouble();

            //If scanner reads a non-double value, throws an InputMismatchError
            } catch(InputMismatchException e) {
                throw new InputMismatchException("Input file format is incorrect. Invalid GPA format. Expected a double.");
            }

            //Try-Catch-Block
            try{
            //Assigns fileCredits to the next readable integer value in the line.
                fileCredits = lineScanner.nextInt();

            //If scanner reads a non-integer value, throws an InputMismatchError
            } catch(InputMismatchException e){
                throw new InputMismatchException("Input file format is incorrect. Invalid credits format. Expected an integer.");
            }

            //Assigns a new Student object with read Gpa and read Credits to fileReadStudents at arrayIndex
            fileReadStudents[arrayIndex] = new Student(fileGpa, fileCredits);

            //Increments arrayIndex by 1 to prepare for the next student's assignment to array
            arrayIndex++;

            //Closes lineScanner scanner object.
            lineScanner.close();
        }
        //Closes readScanner scanner object.
        readScanner.close();

        //Returns fileReadStudents, the Student[] array containing Students read from file.
        return fileReadStudents;
    }
}
