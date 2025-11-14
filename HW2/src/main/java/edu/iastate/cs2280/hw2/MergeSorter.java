package edu.iastate.cs2280.hw2;

public class MergeSorter extends AbstractSorter {

    /**
     * This constructor accepts an array of students as input. It creates a deep copy
     * of the array to prevent modification of the original data. In addition, it sets
     * the object's algorithm type to "MergeSort" using the ENUM class.
     *
     * @param students input array of students
     * @throws IllegalArgumentException if students is null or its length is 0.
     */
    public MergeSorter(Student[] students) throws IllegalArgumentException {
        super(students);
        this.algorithm = String.valueOf(Algorithm.MergeSort);
    }

    /**
     * Sort Algorithm for MergeSorter: Represents a Merge sorting algorithm, where
     * Student elements in a student array are separated into individual arrays using recursive calls, and then merged
     * back into a new,"sorted" array using certain comparison criteria defined by studentComparator.
     * <p>
     * Comparison criteria is dependent on the order of which studentComparator is set to:
     * - setComparator(0) -> Sort by GPA (descending), by Credits (tie-breaker; descending)
     * - setComparator(1) -> Sort by Credits (ascending), by GPA (tie-breaker; descending)
     */
    @Override
    public void sort() {
        //Calls the sortMergeRec helper method, passing in students array as a parameter.
        mergeSortRec(0, students.length - 1);
    }

    /**
     * Helper Method: Deals with the recursive dividing of the student's array, and calls another
     * helper method sortMergeTogether at the end.
     *
     * @param leftIndex  the first element's index value of the subarray
     * @param rightIndex the last element's index value of the subarray
     */
    public void mergeSortRec(int leftIndex, int rightIndex) {

        //Termination case: leftIndex is equal or less than rightIndex, (means subarray contains 1 element) exists recursion
        if (leftIndex >= rightIndex) {
            return;
        }

        //Initializes and assigns int variable "m" to represent middle index of passed in array
        int m = (leftIndex + rightIndex) / 2;


        //Recursively calls sortMergeRec, passing in leftIndex, m, and rightIndex to define subarrays.
        mergeSortRec(leftIndex, m);
        mergeSortRec(m + 1, rightIndex);

        //Calls sortMergeTogether, a helper method, by passing leftIndex, m, and rightIndex
        merge(leftIndex, m, rightIndex);

    }

    /**
     * Helper Method: Merges arrays together utilizing comparator criteria that is set before
     * entire sort method is called. Merges single-element arrays together into a temporary array,
     * and then copies those values into the students array.
     */
    public void merge(int leftIndex, int midIndex, int rightIndex) {
        //Initializes and assigns int variables p and q to represent lengths of left and right sub arrays respectively
        int p = midIndex - leftIndex + 1;
        int q = rightIndex - midIndex;

        //Initializes Student[] arrays leftArr and rightArr and assigns them to new arrays of length p, and q (left and right)
        Student[] leftArr = new Student[p];
        Student[] rightArr = new Student[q];

        //Initializes Student[] array tempArr to store the sorted array before copying to students array. (Avoids accidental overriding)
        Student[] tempArr = new Student[p + q];

        //Iterates through leftArr and assigns corresponding element in students array (copies over)
        for (int i = 0; i < p; i++) {
            leftArr[i] = students[leftIndex + i];
        }

        //Iterates through rightArr and assigns corresponding element in students (copies over)
        for (int i = 0; i < q; i++) {
            rightArr[i] = students[midIndex + i + 1];
        }

        //Initializes and assigns int variables i, j, and k as increment variables for leftArr, rightArr, and tempArr respectively
        int i = 0;
        int j = 0;
        int k = 0;

        //While there are still elements in both the left and right sub arrays
        while (i < p && j < q) {
            //Calls compare on studentComparator object to see if left[i] is greater than right[i]
            if (studentComparator.compare(leftArr[i], rightArr[j]) < 0) {
                //Assigns tempArr at index k to leftArr at index i
                tempArr[k] = leftArr[i];

                //increments k and i by 1
                k++;
                i++;
            } else {
                //Assigns tempArr at index k to rightArr at index j
                tempArr[k] = rightArr[j];

                //Increments k and j by 1
                k++;
                j++;
            }
        }

        //Once one sub array runs out of elements, if left still contains elements, adds the rest of left elements to tempArr
        while (i < p) {
            tempArr[k] = leftArr[i];

            //Increments k and j by 1
            k++;
            i++;
        }

        //If rightArr still contains elements, adds the rest of right elements to tempArr
        while (j < q) {
            tempArr[k] = rightArr[j];

            //Increments k and j by 1
            k++;
            j++;
        }

        //Copies over student objects from tempArr to students array after sorting is finished.
        for (int s = 0; s < tempArr.length; s++) {
            students[leftIndex + s] = tempArr[s];
        }
    }
}
