package edu.iastate.cs2280.hw2;

public class SelectionSorter extends AbstractSorter {

    /**
     * This constructor accepts an array of students as input. It creates a deep copy
     * of the array to prevent modification of the original data. In addition, it sets
     * the object's algorithm type to "SelectionSort" using the ENUM class.
     *
     * @param students input array of students
     * @throws IllegalArgumentException if students is null or its length is 0.
     */
    public SelectionSorter(Student[] students) throws IllegalArgumentException {
        super(students);
        this.algorithm = String.valueOf(Algorithm.SelectionSort);
    }

    /**
     * Sort Algorithm for SelectionSorter: Represents a selection sorting algorithm, where
     * student objects that meet certain comparison criteria are swapped amongst one another
     * until the array of student objects is sorted.
     * <p>
     * Comparison criteria is dependent on the order of which studentComparator is set to:
     * - setComparator(0) -> Sort by GPA (descending), by Credits (tie-breaker; descending)
     * - setComparator(1) -> Sort by Credits (ascending), by GPA (tie-breaker; descending)
     */
    @Override
    public void sort() {
        //Iterates through each element (except last element) in students array
        for (int i = 0; i < students.length - 1; i++) {
            //Initializes and assigns int variable "minIndex" to index of current element
            int minIndex = i;
            //Iterates through all elements to the right of current element in array
            for (int j = i; j < students.length; j++) {
                //If comparison returns 1 (current student is greater than compared student), minIndex becomes j
                if (studentComparator.compare(students[minIndex], students[j]) > 0) {
                    minIndex = j;
                }
            }
            //At the end of all comparisons for each element in the array, elements at index i and minIndex swap
            swap(i, minIndex);
        }
    }
}
