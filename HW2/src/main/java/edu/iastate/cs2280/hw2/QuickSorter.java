/**
 * @author
 */
package edu.iastate.cs2280.hw2;

/**
 * This class implements the quicksort algorithm for an array of Student objects.
 * It uses a median-of-three pivot selection strategy to improve performance and
 * avoid worst-case scenarios with already sorted or reverse-sorted data.
 */
public class QuickSorter extends AbstractSorter {
    /**
     * This constructor accepts an array of students as input. It creates a deep copy
     * of the array to prevent modification of the original data. In addition, it sets
     * the object's algorithm type to "QuickSort" using the ENUM class.
     *
     * @param students input array of students
     * @throws IllegalArgumentException if students is null or its length is 0.
     *
     */
    public QuickSorter(Student[] students) throws IllegalArgumentException {
        super(students);
        this.algorithm = String.valueOf(Algorithm.QuickSort);
    }

    /**
     * Sort Algorithm for QuickSorter: Represents a Quick sorting algorithm, where
     * a Student element is chosen (using Median Of Three Method) to be the pivot element. Based of the pivot element,
     * the other elements in the subarray will be moved around, using certain comparison criteria defined by studentComparator,
     * such that certain elements are to the left and right of the pivot element in the subarray. Recursive calls will
     * lead to subarrays with only 1 element, such that bringing the subarrays back together will result in the sorted
     * array.
     *
     * Comparison criteria is dependent on the order of which studentComparator is set to:
     * - setComparator(0) -> Sort by GPA (descending), by Credits (tie-breaker; descending)
     * - setComparator(1) -> Sort by Credits (ascending), by GPA (tie-breaker; descending)
     */
    @Override
    public void sort() {
        quickSortRec(0, students.length - 1);
    }


    /**
     * Selects a pivot using the median-of-three strategy. It considers the first,
     * middle, and last elements of the subarray, sorts them, and uses the median
     * as the pivot. The pivot is swapped to the last position (last)
     * to simplify the partition step.
     *
     * @param first The starting index of the subarray.
     * @param last  The ending index of the subarray.
     */
    private void medianOfThree(int first, int last) {
        //Initializes and assigns int variable "middle" to contain the index of the middle element of the subarray
        int middle = (first + last) / 2;

        //If the Student's value (GPA/Credits) at index first is less than the Student index middle, swaps the objects
        if (studentComparator.compare(students[first], students[middle]) > 0) {
            swap(first, middle);
        }
        //If the Student's value (GPA/Credits) at index first is less than the Student index last, swaps the objects
        if (studentComparator.compare(students[first], students[last]) > 0) {
            swap(first, last);
        }
        //If the Student's value (GPA/Credits) at index middle is less than the Student index last, swaps the objects
        if (studentComparator.compare(students[middle], students[last]) > 0) {
            swap(middle, last);
        }

        //Swaps Student object at index middle (contains the median student out of the 3) and swaps with Student at index (last)
        swap(middle, last);
    }

    /**
     * Helper Method: Begins to "partition" subarray so Student objects greater and less (GPA/Credits) than the Student
     * object at the pivot position are to the left and right, respectively, of the Student object at such pivot
     * position in the subarray.
     *
     * @param firstIndex the index value of the first element in the subarray
     * @param lastIndex  the index value of the last element in the subarray
     *
     * @return the index position where the pivot element is located in the subarray
     */
    private int partition(int firstIndex, int lastIndex) {

        //Calls medianOfThree to set subarray so that the median Student object of the (first, middle, and last) indexes
        //is set to the index (last) in the subarray
        medianOfThree(firstIndex, lastIndex);

        //Initializes and assigns int variable "pivotPosition" to contain the index where the pivot is located in subarray
        int pivotPosition = lastIndex;

        //Initializes and assigns int increment variable "i" to designate where pivot should be inserted after iteration
        int i = firstIndex - 1;

        //Iterates through entire subarray
        for (int j = firstIndex; j < lastIndex; j++) {
            //If studentComparator returns 1, swaps students at index i and j
            if (studentComparator.compare(students[pivotPosition], students[j]) > 0) {
                i++;
                swap(i, j);
            }
        }

        //Swaps student at index (i + 1) with pivot position so that pivot is in the correct spot in subarray.
        swap(i + 1, pivotPosition);

        //Returns index where pivot is currently located in subarray
        return i + 1;
    }

    /**
     * Helper Method: Handles the recursive calls of quicksort for the subarrays after partitioning until termination
     * condition is met.
     *
     * @param firstIndex the index of the first element in the subarray
     * @param lastIndex the idnex of the last element in the subarray
     */
    private void quickSortRec(int firstIndex, int lastIndex) {
        //TERMINATION CONDITION: If firstIndex is greater/equal to lastIndex (subarray only has 1 element), exits recursion
        if (firstIndex >= lastIndex) {
            return;
        }
        //Initializes and assigns int variable "pivot" to hold the pivot position index after calling partition
        int pivot = partition(firstIndex, lastIndex);

        //Recursively calls method on subarrays to the left and right of pivot until termination condition is met.
        quickSortRec(firstIndex, pivot - 1);
        quickSortRec(pivot + 1, lastIndex);
    }
}
