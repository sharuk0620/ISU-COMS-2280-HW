/**
 * @author
 */
package edu.iastate.cs2280.hw2;

/**
 * This class orchestrates the process of finding a "median student" from an array
 * of Student objects. It does this by sorting the array twice: once by GPA to find
 * the median GPA, and once by credits taken to find the median number of credits.
 * It then constructs a new Student object with these median values. The class
 * also measures the total time taken for both sorting operations for a specific
 * sorting algorithm.
 */
public class StudentScanner {

    /**
     * A copy of the student data to be processed.
     */
    private final Student[] students;
    /**
     * The sorting algorithm to be used for finding the medians.
     */
    private final Algorithm sortingAlgorithm;
    /**
     * The total time in nanoseconds taken by the two sorting operations.
     */
    protected long scanTime;
    /**
     * The resulting student object with median GPA and median credits.
     */
    private Student medianStudent;

    /**
     * Constructs a StudentScanner. It takes an array of students and the sorting
     * algorithm to use. A deep copy of the students array is made to avoid
     * modifying the original array.
     *
     * @param students The array of students to scan.
     * @param algo     The sorting algorithm to use.
     * @throws IllegalArgumentException if the input students array is null or empty.
     */
    public StudentScanner(Student[] students, Algorithm algo) {
        //If students array points to null or if the length of the array is 0, throws a IllegalArgumentException
        if (students == null || students.length == 0) {
            throw new IllegalArgumentException("Input Error: Array of students points to null or contains 0 students!");
        }

        //Assigns students to a new Student array of the same length as the passed-in Student array
        this.students = new Student[students.length];

        //For loop that iterates through each element of the students array
        for (int i = 0; i < students.length; i++) {
            //If an element is null (meaning there's an empty student spot), throws an IllegalArgumentException
            if (students[i] == null) {
                throw new IllegalArgumentException("Input Error: Array of students contains null/empty student spots!");
                //Else, proceeds to deep copy array by assigning element index of copy to a new Student object with existing fields
            } else {
                this.students[i] = new Student(students[i].getGpa(), students[i].getCreditsTaken());
            }
        }
        this.sortingAlgorithm = algo;
    }

    /**
     * Executes the scanning process. It creates an appropriate sorter based on the
     * specified algorithm, sorts the students array by GPA, finds the median GPA,
     * then sorts by credits, and finds the median credits. It calculates the total
     * time and creates the medianStudent.
     */
    public void scan() {

        //Initializes an AbstractSorter named sorter to hold one of 4 different sorter objects
        AbstractSorter sorter;

        //Calls assignSorterObject, passing in sortingAlgorithm field, and assigns returned object to sorter
        sorter = assignSorterObject(sortingAlgorithm);

        //Sets sorter's comparator to 0 - Sorts by GPA (Descending), tie-breaks with Credits (Descending)
        sorter.setComparator(0);

        //Initializes and assigns long variable startTimer to begin timing (nm) first .sort() call
        long startTimer = System.nanoTime();

        //Calls the corresponding sort method depending on what sorter object the sorter reference variable has stored
        sorter.sort();

        //Initializes and assigns long variable endTimer to end the timer that was recording the length of the sort call (nm)
        long endTimer = System.nanoTime();

        //Assigns scanTime field to the difference of endTimer and startTimer to obtain runtime of the first sort (GPA).
        scanTime = endTimer - startTimer;

        //Initializes and assigns int variable n to hold the length of the students array field.
        int n = students.length;

        //Initializes double variable medianGPA to store the GPA of the median student element post-sort.
        double medianGPA;

        //Checks to see if length of sorter's students array is not 0
        if (n != 0) {
            //If the length of sorter's students array is odd, assigns medianGPA to gpa of middle Student in students array.
            if (n % 2 == 1) {
                medianGPA = sorter.getMedian().getGpa();//sorter.students[n / 2].getGpa();
                //If length is even, assigns medianGPA to average GPA of Student at (n/2) - 1 and (n/2).
            } else {
                medianGPA = sorter.getMedian().getGpa(); //(sorter.students[(n / 2) - 1].getGpa() + sorter.students[n / 2].getGpa()) / 2.0;
            }
            //If length of sorter's students array is 0, returns gracefully out of method.
        } else {
            return;
        }

        //Sets sorter's comparator to 1 - Sorts by Credits (Descending), tie-breaks with GPA (Descending)
        sorter.setComparator(1);

        //Initializes and assigns long variable startTimerTwo to begin timing (nm) the second .sort() call
        long startTimerTwo = System.nanoTime();

        //Calls the corresponding sort method depending on what sorter object the sorter reference variable has stored.
        sorter.sort();

        //Initializes and assigns long variable endTimerTwo to end the timer that was recording the length of the sort call (nm)
        long endTimerTwo = System.nanoTime();

        //Adds the difference of endTimerTwo and startTimerTwo to scanTime, which now will represent the total runtime.
        scanTime = scanTime + (endTimerTwo - startTimerTwo);

        //Initializes and assigns int variable medianCredits to store the credits of the median student element post-sort.
        int medianCredits;

        //Checks to see if length of sorter's students array is not 0
        if (n != 0) {
            //If the length of sorter's students array is odd, assigns medianCredits to credits of middle Student in students array.
            if (n % 2 == 1) {
                medianCredits = sorter.getMedian().getCreditsTaken(); //students[n / 2].getCreditsTaken();
                //If length is even, assigns medianCredits to average credits of Student at (n/2) - 1
            } else {
                medianCredits = sorter.getMedian().getCreditsTaken(); //(sorter.students[(n / 2) - 1].getCreditsTaken() + sorter.students[n / 2].getCreditsTaken()) / 2;
            }
            //If length of sorter's students array is 0, returns gracefully out of method.
        } else {
            return;
        }

        //Assigns medianStudent field to a new Student object, passing in the median GPA and median credits from above
        medianStudent = new Student(medianGPA, medianCredits);
    }

    /**
     * Helper Method: returns a new instance of the corresponding sorting object based on what the StudentScanner's
     * sortingAlgorithm field was instantiated to.
     *
     * @param sortingAlgorithm the sorting algorithm that was assigned by StudentScanner's constructor.
     * @return A sorter object of the corresponding type of the sortingAlgorithm field, passing in students array.
     */
    private AbstractSorter assignSorterObject(Algorithm sortingAlgorithm) {
        switch (sortingAlgorithm) {

            //If sortingAlgorithm field was initialized as SelectionSort, assigns sorter to new SelectionSorter object
            case SelectionSort:
                return new SelectionSorter(students); //passes students array into constructor
            //If sortingAlgorithm field was initialized as InsertionSort, assigns sorter to new InsertionSorter object
            case InsertionSort:
                return new InsertionSorter(students); //passes students array into constructor
            //If sortingAlgorithm field was initialized as MergeSort, assigns sorter to new MergeSorter object
            case MergeSort:
                return new MergeSorter(students); //passes students array into constructor
            //If sortingAlgorithm field was initialized as QuickSort, assigns sorter to new QuickerSorter object
            case QuickSort:
                return new QuickSorter(students); //passes students array into constructor
            //If sortingAlgorithm field was initialized with something other than the 4 algorithms, returns null;
            default:
                return null;
        }
    }

    /**
     * Returns a formatted string containing the performance statistics of the scan.
     *
     * @return A string with the algorithm name, data size, and total scan time.
     */
    public String stats() {
        return String.format("%-15s %-5d %-10d", sortingAlgorithm, students.length, scanTime);
    }

    /**
     * Gets the calculated median student after a scan has been performed.
     *
     * @return The median student.
     */
    public Student getMedianStudent() {
        return medianStudent;
    }

    /**
     * Gets the Algorithm the StudentScanner object was set with during initialization
     *
     * @return the Algorithm of the StudentScanner
     */
    public String getAlgorithm(){
        return String.valueOf(sortingAlgorithm);
    }

    /**
     * Gets the total scan time during the two-sort process using the Algorithm of the StudentScanner
     *
     * @return the total scan time from the two-sort sorting process
     */
    public long getScanTime(){
        return scanTime;
    }

    /**
     * Provides a string representation of the StudentScanner's result.
     *
     * @return A string indicating the median student profile.
     */
    @Override
    public String toString() {
        return "Median Student: " + medianStudent.toString();
    }
}
