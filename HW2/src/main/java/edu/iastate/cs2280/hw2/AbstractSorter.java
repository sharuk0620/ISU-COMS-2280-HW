/**
 * @author
 */
package edu.iastate.cs2280.hw2;

import java.util.Comparator;

/**
 * AbstractSorter is an abstract class that provides a foundation for implementing
 * various sorting algorithms on an array of Student objects. It defines common
 * properties and methods, such as handling a comparator for sorting criteria
 * and utility methods for swapping elements.
 */
public abstract class AbstractSorter {
    /**
     * Array of students to be sorted.
     */
    protected Student[] students;
    /**
     * The name of the sorting algorithm implementation.
     * Should be set by concrete sorter classes.
     * The values can be "SelectionSort", "InsertionSort", "MergeSort", or "QuickSort".
     */
    protected String algorithm = null;
    /**
     * The comparator used to define the sorting order for students.
     */
    protected Comparator<Student> studentComparator = null;

    /**
     * This constructor accepts an array of students as input. It creates a deep copy
     * of the array to prevent modification of the original data.
     *
     * @param students input array of students
     * @throws IllegalArgumentException if students is null or its length is 0.
     */
    protected AbstractSorter(Student[] students) throws IllegalArgumentException {

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
    }

    /**
     * Generates a comparator for sorting students.
     * If order is 0, students are compared primarily by GPA (descending) with tie-breaker by credits (descending).
     * If order is 1, students are compared primarily by credits taken (ascending) with tie-breaker by GPA (descending).
     *
     * @param order 0 to sort by GPA, 1 to sort by credits taken.
     * @throws IllegalArgumentException if order is not 0 or 1.
     */
    public void setComparator(int order) throws IllegalArgumentException {
        //If inputted order is 0
        if (order == 0) {
            //Creates a lambda expression that shorthands custom compare method (Descending GPA, Descending Credits Tie-break)
            studentComparator = (s1, s2) -> {
                int comparison = Double.compare(s2.getGpa(), s1.getGpa());
                if (comparison == 0) {
                    comparison = Integer.compare(s2.getCreditsTaken(), s1.getCreditsTaken());
                }
                return comparison;
            };
        } else if (order == 1) {
            //Creates a lambda expression that shorthands custom compare method (Ascending Credits, Descending GPA Tie-break)
            studentComparator = (s1, s2) -> {
                int comparison = Integer.compare(s1.getCreditsTaken(), s2.getCreditsTaken());
                if (comparison == 0) {
                    comparison = Double.compare(s2.getGpa(), s1.getGpa());
                }
                return comparison;
            };
            //Else, if inputted Comparator order was not 0 or 1, throws IllegalArgumentException
        } else {
            throw new IllegalArgumentException("Invalid Comparator Setting: Must input 0 or 1!");
        }
    }

    /**
     * Abstract method for sorting the students array.
     * Each concrete sorter class must provide an implementation of this method.
     */
    public abstract void sort();

    /**
     * Returns the student at the median index of the sorted array.
     *
     * @return The median student.
     */
    public Student getMedian() {
        //Initializes int arrayLength to store the length of the students array
        int arrayLength = students.length;

        //If array length is not 0
        if(arrayLength != 0){
            //If array is an odd-length list, returns the student element at index (length / 2)
            if (arrayLength % 2 == 1) {
                return students[arrayLength / 2];
            //If array is an even-length list, returns the student element at index (length / 2)
            } else {
                return students[(arrayLength / 2)];
            }
        }
        //If length of sorter's students array is 0, returns null;
        return null;
    }

    /**
     * Swaps the students at two specified indices in the students array.
     *
     * @param i The index of the first student to swap.
     * @param j The index of the second student to swap.
     */
    protected void swap(int i, int j) {
        Student temp = students[i];
        students[i] = students[j];
        students[j] = temp;
    }
}
