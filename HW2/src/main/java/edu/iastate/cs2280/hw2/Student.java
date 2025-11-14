/**
 * @author
 */
package edu.iastate.cs2280.hw2;

/**
 * Represents a student with a GPA and the total number of credits taken.
 * This class implements the Comparable interface to define a
 * natural ordering for students. The natural order is primarily by GPA
 * (descending), and secondarily by credits taken (descending) as a tie-breaker.
 */
public class Student implements Comparable<Student> {
    /**
     * The student's Grade Point Average.
     */
    private final double gpa;
    /**
     * The total number of credits the student has taken.
     */
    private final int creditsTaken;


    private int originalPositionIndex;

    /**
     * Constructs a new Student with a specified GPA and number of credits.
     *
     * @param gpa          The GPA of the student (must be between 0.0 and 4.0).
     * @param creditsTaken The number of credits taken (cannot be negative).
     * @throws IllegalArgumentException if GPA or creditsTaken are out of valid range.
     */
    public Student(double gpa, int creditsTaken) {
        //Checks to see if inputted GPA is in range, throws exception if not
        if (gpa > 4.0 || gpa < 0.0) {
            throw new IllegalArgumentException("Invalid GPA: Must be between 0.0 and 4.0!");
        }
        //Checks to see if inputted credits taken is in range, throws exception if not
        if (creditsTaken < 0) {
            throw new IllegalArgumentException("Invalid Credits: Student cannot have negative credits!");
        }

        //If an exception isn't thrown above, assigns object's gpa and creditsTaken fields to inputted values
        this.gpa = gpa;
        this.creditsTaken = creditsTaken;

    }


    /**
     * Copy constructor. Creates a new Student object by copying the data from another.
     *
     * @param other The Student object to copy.
     */
    public Student(Student other) {
        //Assigns fields of Student object copy to fields of inputted Student object (shallow copy w/ primitives)
        this.gpa = other.gpa;
        this.creditsTaken = other.creditsTaken;
    }

    /**
     * Gets the student's GPA.
     *
     * @return The GPA.
     */
    public double getGpa() {
        return gpa;
    }

    /**
     * Gets the number of credits the student has taken.
     *
     * @return The total credits taken.
     */
    public int getCreditsTaken() {
        return creditsTaken;
    }

    /**
     * Provides a string representation of the Student object.
     *
     * @return A formatted string showing the student's GPA and credits.
     */
    @Override
    public String toString() {
        return String.format("(GPA: %.2f, Credits: %d)", gpa, creditsTaken);
    }

    /**
     * Compares this student with another for natural ordering. The comparison is
     * based first on GPA in descending order, and then by credits taken in
     * descending order as a tie-breaker.
     *
     * @param other The other student to be compared.
     * @return A negative integer, zero, or a positive integer if this student is
     * greater than, equal to, or less than the specified student, respectively,
     * based on the defined natural order.
     */
    @Override
    public int compareTo(Student other) {
        //Initializes and assigns double variable gpaCompare to the difference of the two Student's GPA values
        int gpaCompare = Double.compare(other.gpa, this.gpa);

        //Checks if the GPA difference is 0 (meaning they have the same GPA)
        if (gpaCompare == 0) {
            //Initializes and assigns int variable creditCompare to the difference of the two Student's completed credits
            int creditCompare = Integer.compare(other.creditsTaken, this.creditsTaken);

            //Returns a -1, 0, or 1 if this Student's credits taken is greater than, equal to, or less than other Student
            return creditCompare;
        }

        //Returns a -1, 0, or 1 if this Student's GPA is greater than, equal to, or less than other Student
        return gpaCompare;
    }

    /**
     * Compares this Student object to another object for equality.
     * The comparison is based on GPA and the number of credits taken.
     *
     * @param other The object to be compared with this Student.
     * @return true if the given object is a Student and has the same GPA
     * and number of credits taken as this Student, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        //Checks to see if memory addresses are the same, returns true as they point to same object
        if (this == other) {
            return true;
        }
        //Checks if other is null, or if classes of both objects do not match - returns false if one condition is met
        if (other == null || this.getClass() != other.getClass()) {
            return false;
        }
        //Downcasts other to a Student named "secondStudent"
        Student secondStudent = (Student) other;

        //Returns whether GPA and credits taken of both Student Objects are equal
        return (this.gpa == secondStudent.gpa && this.creditsTaken == secondStudent.creditsTaken);
    }
}
