package edu.iastate.cs2280.hw2;

public class InsertionSorter extends AbstractSorter {

    /**
     * This constructor accepts an array of students as input. It creates a deep copy
     * of the array to prevent modification of the original data. In addition, it sets
     * the object's algorithm type to "InsertionSort" using the ENUM class.
     *
     * @param students input array of students
     * @throws IllegalArgumentException if students is null or its length is 0.
     */
    public InsertionSorter(Student[] students) throws IllegalArgumentException {
        super(students);
        this.algorithm = String.valueOf(Algorithm.InsertionSort);
    }

    /**
     * Sort Algorithm for InsertionSorter: Represents an Insertion sorting algorithm, where
     * student objects that meet certain comparison criteria are placed in the correct spot in
     * the "sorted" portion of the array, until the entire array is sorted.
     * <p>
     * Comparison criteria is dependent on the order of which studentComparator is set to:
     * - setComparator(0) -> Sort by GPA (descending), by Credits (tie-breaker; descending)
     * - setComparator(1) -> Sort by Credits (ascending), by GPA (tie-breaker; descending)
     */
    @Override
    public void sort() {
        //Iterates through every element in array (except first as it is trivially sorted)
        for (int i = 1; i < students.length; i++) {
            //Initialize and assigns Student cur to Student element at index "i" in the array
            Student cur = students[i];
            //Initializes and assigns increment variable j to iterate through elements to the right of element at "i".
            int j = i - 1;
            //While loop to make sure j is greater than less than 0 (avoids ArrayOutOfBounds) and compares Student objects
            while (j >= 0 && studentComparator.compare(students[j], cur) > 0) {
                //Once position has been found, moves elements to the right of position over by 1 index
                students[j + 1] = students[j];
                //Decrements j to move to the next element to move over
                j--;
            }
            //Finally, places element at the proper position in the "sorted array" part of the Student array.
            students[j + 1] = cur;
        }
    }
}
