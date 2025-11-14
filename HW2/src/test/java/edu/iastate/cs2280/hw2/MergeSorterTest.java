package edu.iastate.cs2280.hw2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MergeSorterTest {

    //Initializes various arrays used in their corresponding test case

    Student[] testArrayOdd;

    Student[] testArrayOddSorted;

    Student[] testArrayOddReverseSorted;

    Student[] testArrayEven;

    Student[] testArrayEvenSorted;

    Student[] testArrayEvenReverseSorted;

    Student[] testArrayEmpty;

    Student[] testArrayNull;

    Student[] testArrayOne;

    MergeSorter testSorter;


    /**
     * Before Each: Initializes specific-sized Student arrays to be used in various test cases.
     *
     */
    @BeforeEach
    public void testCaseSetup(){

        testArrayOdd = new Student[5];

        testArrayOddSorted = new Student[5];

        testArrayOddReverseSorted = new Student[5];

        testArrayEven = new Student[4];

        testArrayEvenSorted = new Student[4];

        testArrayEvenReverseSorted = new Student[4];

        testArrayEmpty = new Student[0];

        testArrayNull = null;

        testArrayOne = new Student[1];

        testArrayOne[0] = new Student(4.0, 75);
    }


    /**
     * Test Case #1: Tests the merge sorting algorithm with an empty list of students as in input,
     * seeing if it will return a IllegalArgumentException.
     */
    @Test
    public void mergeSortWithEmptyArray(){
        //Checks to see if an IllegalArgument exception is thrown upon call to constructor
        assertThrows(IllegalArgumentException.class, () -> {
            new MergeSorter(testArrayEmpty);
        });

    }


    /**
     * Test Case #2: Tests that the merge sorting algorithm by GPA with a student list containing only 1 element
     * will keep the singular student element.
     */
    @Test public void mergeSortByGpaWithOneElemArray(){

        //Assigns testSorter to a new MergeSorter object, passing in testArrayOne
        testSorter = new MergeSorter(testArrayOne);

        //Initializes Student variable onlyStudent and assigns it to the singular student element in the list.
        Student onlyStudent = testArrayOne[0];

        //Sets the comparator to order 0 (gpa)
        testSorter.setComparator(0);

        //Sorts the array accordingly
        testSorter.sort();

        //Checks to see if the singular student element remained the same
        assertEquals(onlyStudent, testSorter.students[0]);


    }

    /**
     * Test Case #3: Tests that the merge sorting algorithm by credits with a student list containing only 1 element
     * will keep the singular student element.
     */
    @Test public void mergeSortByCreditsWithOneElemArray(){

        //Assigns testSorter to a new MergeSorter object, passing in testArrayOne
        testSorter = new MergeSorter(testArrayOne);

        //Initializes Student variable onlyStudent and assigns it to the singular student element in the list.
        Student onlyStudent = testArrayOne[0];

        //Sets the comparator to order 1 (credits)
        testSorter.setComparator(1);

        //Sorts the array accordingly
        testSorter.sort();

        //Checks to see if the singular student element remained the same
        assertEquals(onlyStudent, testSorter.students[0]);


    }

    /**
     * Test Case #4: Tests that the merge sorting algorithm by GPA with a student list with an odd length
     * will properly sort the array descendingly.
     */
    @Test
    public void mergeSortByGPAWithOddArray(){

        //Assigns elements to testArrayOdd
        testArrayOdd[0] = new Student(4.0, 75);
        testArrayOdd[1] = new Student(1.7, 98);
        testArrayOdd[2] = new Student(3.9, 12);
        testArrayOdd[3] = new Student(2.7, 120);
        testArrayOdd[4] = new Student(0.2, 41);

        //Assigns testSorter to a new MergeSorter object, passing in testArrayOdd
        testSorter = new MergeSorter(testArrayOdd);

        //Sets the comparator to order 0 (gpa)
        testSorter.setComparator(0);

        //Sorts the array accordingly
        testSorter.sort();

        //Initializes boolean variable equalArray and assigns it to true.
        boolean equalArray = true;

        //Iterates through testSorter.students (Array of students post-sort)
        for(int i = 0; i < testArrayOdd.length - 1; i++){
            //If the student element's gpa at index i is less than the next student, sets equalArray to false
            if(testSorter.students[i].getGpa() < testSorter.students[i+1].getGpa()){
                equalArray = false;
            }
        }

        //Checks to see if equalArray is true (If elements match up where they should be)
        assertTrue(equalArray);

    }

    /**
     * Test Case #5: Tests that the merge sorting algorithm by credits with a student list with an odd length
     * will properly sort the array ascendingly.
     */
    @Test
    public void mergeSortByCreditsWithOddArray(){

        //Assigns elements to testArrayOdd
        testArrayOdd[0] = new Student(4.0, 75);
        testArrayOdd[1] = new Student(1.7, 98);
        testArrayOdd[2] = new Student(3.9, 12);
        testArrayOdd[3] = new Student(2.7, 120);
        testArrayOdd[4] = new Student(0.2, 41);

        //Assigns testSorter to a new MergeSorter object, passing in testArrayOdd
        testSorter = new MergeSorter(testArrayOdd);

        //Sets to the comparator to order 1 (credits)
        testSorter.setComparator(1);

        //Sorts the array accordingly
        testSorter.sort();

        //Initializes boolean variable equalArray and assigns it to true.
        boolean equalArray = true;

        //Iterates through testSorter.students (Array of students post-sort)
        for(int i = 0; i < testArrayOdd.length - 1; i++){
            //If the student element's credits at index i is greater than the next student, sets equalArray to false.
            if(testSorter.students[i].getCreditsTaken() > testSorter.students[i+1].getCreditsTaken()){
                equalArray = false;
            }
        }

        //Checks to see if equalArray is true (If elements match up where they should be)
        assertTrue(equalArray);

    }

    /**
     * Test Case #6: Tests that the merge sorting algorithm by gpa with a SORTED student list with an odd length
     * will properly sort the array descendingly.
     */
    @Test
    public void mergeSortByGpaWithOddSortedArray(){

        //Assigns elements to testArrayOddSorted
        testArrayOddSorted[0] = new Student(4.0, 123);
        testArrayOddSorted[1] = new Student(3.5, 23);
        testArrayOddSorted[2] = new Student(2.7, 92);
        testArrayOddSorted[3] = new Student(1.6, 35);
        testArrayOddSorted[4] = new Student(1.2, 82);

        //Assigns testSorter to a new MergeSorter object, passing in testArrayOddSorted
        testSorter = new MergeSorter(testArrayOddSorted);

        //Sets comparator to order 0 (gpa)
        testSorter.setComparator(0);

        //Sorts the array accordingly
        testSorter.sort();

        //Initializes boolean variable equalArray and assigns it to true.
        boolean equalArray = true;

        //Iterates through and compares testSorter.students and testArrayOddSorted
        for(int i = 0; i < testArrayOddSorted.length; i++){
            //If the student elements at index i are NOT equal, array is not the same, and sets equalArray to false.
            if(!(testSorter.students[i].getGpa() == testArrayOddSorted[i].getGpa())){
                equalArray = false;
            }
        }

        //Checks to see if equalArray is true (If elements match up where they should be)
        assertTrue(equalArray);
    }

    /**
     * Test Case #7: Tests that the merge sorting algorithm by credits with a SORTED student list with an odd length
     * will properly sort the array ascendingly.
     */
    @Test
    public void mergeSortByCreditsWithOddSortedArray(){

        //Assigns elements to testArrayOddSorted
        testArrayOddSorted[0] = new Student(4.0, 23);
        testArrayOddSorted[1] = new Student(2.7, 35);
        testArrayOddSorted[2] = new Student(1.2, 82);
        testArrayOddSorted[3] = new Student(1.6, 92);
        testArrayOddSorted[4] = new Student(3.5, 123);

        //Assigns testSorter to a new MergeSorter object, passing in testArrayOddSorted
        testSorter = new MergeSorter(testArrayOddSorted);

        //Sets comparator to order 1 (credits)
        testSorter.setComparator(1);

        //Sorts the array accordingly
        testSorter.sort();

        //Initializes boolean variable equalArray and assigns it to true.
        boolean equalArray = true;

        //Iterates through and compares testSorter.students and testArrayOddSorted
        for(int i = 0; i < testArrayOddSorted.length; i++){
            //If both student elements at index i are not equal, meaning arrays are not the same, sets equalArray to false
            if(!(testSorter.students[i].getCreditsTaken() == testArrayOddSorted[i].getCreditsTaken())){
                equalArray = false;
            }
        }

        //Checks to see if equalArray is true (If elements match up where they should be)
        assertTrue(equalArray);
    }

    /**
     * Test Case #8: Tests that the merge sorting algorithm by GPA with a REVERSE-SORTED student list with an
     * odd length will properly sort the array descendingly.
     */
    @Test
    public void mergeSortByGPAWithOddReverseSortedArray(){


        //Assigns elements to testArrayOddReverseSorted
        testArrayOddReverseSorted[0] = new Student(1.6, 23);
        testArrayOddReverseSorted[1] = new Student(1.9, 17);
        testArrayOddReverseSorted[2] = new Student(2.7, 125);
        testArrayOddReverseSorted[3] = new Student(3.5, 75);
        testArrayOddReverseSorted[4] = new Student(4.0, 60);

        //Assigns testSorter to a new MergeSorter object, passing in testArrayOddReverseSorted
        testSorter = new MergeSorter(testArrayOddReverseSorted);

        //Sets comparator to order 0 (gpa)
        testSorter.setComparator(0);

        //Sorts the array accordingly
        testSorter.sort();

        //Initializes boolean variable equalArray and assigns it to true.
        boolean equalArray = true;

        //Initializes increment int variable j and assigns it the last index value in array
        int j = testArrayOddReverseSorted.length - 1;

        //Iterates through and compares testSorter.students and testArrayOddReversedSorted
        for(int i = 0; i < testArrayOddReverseSorted.length; i++){
            //If student elements at index i and j do not have the same gpa, sets equalArray to false.
            if(!(testSorter.students[i].getGpa() == testArrayOddReverseSorted[j].getGpa())){
                equalArray = false;
            }
            //Decrements j by 1
            j--;
        }

        //Checks to see if equalArray is true (If elements match up where they should be)
        assertTrue(equalArray);
    }

    /**
     * Test Case #9: Tests that the merge sorting algorithm by credits with a REVERSE-SORTED student list with an
     * odd length will properly sort the array ascendingly.
     */
    @Test
    public void mergeSortByCreditsWithOddReverseSortedArray(){

        //Assigns elements to testArrayOddReverseSorted
        testArrayOddReverseSorted[0] = new Student(4.0, 125);
        testArrayOddReverseSorted[1] = new Student(3.5,75);
        testArrayOddReverseSorted[2] = new Student(2.7, 60);
        testArrayOddReverseSorted[3] = new Student(1.2, 23);
        testArrayOddReverseSorted[4] = new Student(0.6, 4);

        //Assigns testSorter to a new MergeSorter object, passing in testArrayOddReversedSorted
        testSorter = new MergeSorter(testArrayOddReverseSorted);

        //Sets comparator to order 1 (credits)
        testSorter.setComparator(1);

        //Sorts the array accordingly
        testSorter.sort();

        //Initializes boolean variable equalArray and assigns it to true.
        boolean equalArray = true;

        //Initializes increment int variable j and assigns it to 0
        int j = 0;

        //Iterates through and compares testSorter.students and testArrayOddReversedSorted
        for(int i = testArrayOddReverseSorted.length - 1; i >= 0; i--){
            //If student elements at index i and j do not have the same gpa, sets equalArray to false.
            if(!(testSorter.students[i].getCreditsTaken() == testArrayOddReverseSorted[j].getCreditsTaken())){
                equalArray = false;
            }
            //Increments j by 1
            j++;
        }

        //Checks to see if equalArray is true (If elements match up where they should be)
        assertTrue(equalArray);
    }

    /**
     * Test Case #10: Tests that the merge sorting algorithm by GPA with a student list with an
     * even length will properly sort the array descendingly.
     */
    @Test
    public void mergeSortByGPAWithEvenArray(){

        //Assigns elements to testArrayEven
        testArrayEven[0] = new Student(4.0, 75);
        testArrayEven[1] = new Student(1.7, 98);
        testArrayEven[2] = new Student(3.9, 12);
        testArrayEven[3] = new Student(2.7, 120);

        //Assigns testSorter to a new MergeSorter object, passing in testArrayEven
        testSorter = new MergeSorter(testArrayEven);

        //Sets comparator to order 0 (gpa)
        testSorter.setComparator(0);

        //Sorts the array accordingly
        testSorter.sort();

        //Initializes boolean variable equalArray and assigns it to true.
        boolean equalArray = true;

        //Iterates through testSorter.students (Array of students post-sort);
        for(int i = 0; i < testArrayEven.length - 1; i++){
            //If student at index i's gpa is less than the next student, sets equalArray to false
            if(testSorter.students[i].getGpa() < testSorter.students[i+1].getGpa()){
                equalArray = false;
            }
        }

        //Checks to see if equalArray is true (If elements match up where they should be)
        assertTrue(equalArray);

    }

    /**
     * Test Case #11: Tests that the merge sorting algorithm by credits with a student list with an
     * even length will properly sort the array ascendingly.
     */
    @Test
    public void mergeSortByCreditsWithEvenArray(){

        //Assigns elements to testArrayEven
        testArrayEven[0] = new Student(4.0, 75);
        testArrayEven[1] = new Student(1.7, 98);
        testArrayEven[2] = new Student(3.9, 12);
        testArrayEven[3] = new Student(2.7, 120);

        //Assigns testSorter to a new MergeSorter object, passing in testArrayEven
        testSorter = new MergeSorter(testArrayEven);

        //Sets comparator to order 1 (credits)
        testSorter.setComparator(1);

        //Sorts the array accordingly
        testSorter.sort();

        //Initializes boolean variable equalArray and assigns it to true.
        boolean equalArray = true;

        //Iterates and compares testSorter.students and testArrayEven
        for(int i = 0; i < testArrayEven.length - 1; i++){
            //If student at index i's credits are greater than the next student, sets equalArray to false
            if(testSorter.students[i].getCreditsTaken() > testSorter.students[i+1].getCreditsTaken()){
                equalArray = false;
            }
        }

        //Checks to see if equalArray is true (If elements match up where they should be)
        assertTrue(equalArray);

    }

    /**
     * Test Case #12: Tests that the merge sorting algorithm by GPA with a SORTED student list with an
     * even length will properly sort the array descendingly.
     */
    @Test
    public void mergeSortByGPAWithEvenSortedArray(){

        //Assigns elements to testArrayEvenSorted
        testArrayEvenSorted[0] = new Student(4.0, 17);
        testArrayEvenSorted[1] = new Student(3.5, 76);
        testArrayEvenSorted[2] = new Student(2.7, 25);
        testArrayEvenSorted[3] = new Student(1.0, 75);

        //Assigns testSorter to a new MergeSorter object, passing in testArrayEvenSorted
        testSorter = new MergeSorter(testArrayEvenSorted);

        //Sets comparator to order 0 (gpa)
        testSorter.setComparator(0);

        //sorts algorithm accordingly
        testSorter.sort();

        //Initializes boolean variable equalArray and assigns it to true.
        boolean equalArray = true;

        //Iterates and compares testSorter.students and testArrayEvenSorted
        for(int i = 0; i < testArrayEvenSorted.length; i++){
            //If students' GPA at index i don't match, meaning arrays aren't equal, sets equalArray to false.
            if(!(testSorter.students[i].getGpa() == testArrayEvenSorted[i].getGpa())){
                equalArray = false;
            }
        }

        //Checks to see if equalArray is true (If elements match up where they should be)
        assertTrue(equalArray);
    }

    /**
     * Test Case #13: Tests that the merge sorting algorithm by credits with a SORTED student list with an
     * even length will properly sort the array ascendingly.
     */
    @Test
    public void mergeSortByCreditsWithEvenSortedArray(){

        //Assigns elements to testArrayEvenSorted
        testArrayEvenSorted[0] = new Student(4.0, 17);
        testArrayEvenSorted[1] = new Student(2.7, 25);
        testArrayEvenSorted[2] = new Student(3.5, 75);
        testArrayEvenSorted[3] = new Student(1.0, 76);

        //Assigns testSorter to a new mergeSort object, passing in testArrayEvenSorted
        testSorter = new MergeSorter(testArrayEvenSorted);

        //Sets comparator to order 1 (credits)
        testSorter.setComparator(1);

        //Sorts the array accordingly
        testSorter.sort();

        //Initializes boolean variable equalArray and assigns it to true.
        boolean equalArray = true;

        //Iterates and compares testSorter.students and testArrayEvenSorted
        for(int i = 0; i < testArrayEvenSorted.length; i++){
            //If students' credits at index i don't match, meaning arrays aren't equal, sets equalArray to false.
            if(!(testSorter.students[i].getCreditsTaken() == testArrayEvenSorted[i].getCreditsTaken())){
                equalArray = false;
            }
        }

        //Checks to see if equalArray is true (If elements match up where they should be)
        assertTrue(equalArray);
    }

    /**
     * Test Case #14: Tests that the merge sorting algorithm by GPA with a REVERSE-SORTED student list with an
     * even length will properly sort the array descendingly.
     */
    @Test
    public void mergeSortByGPAWithEvenReverseSortedArray(){

        //Assigns elements to testArrayEvenReverseSorted
        testArrayEvenReverseSorted[0] = new Student(1.0, 17);
        testArrayEvenReverseSorted[1] = new Student(2.7, 25);
        testArrayEvenReverseSorted[2] = new Student(3.5, 75);
        testArrayEvenReverseSorted[3] = new Student(4.0, 76);

        //Assigns testSorter to a new MergeSorter object, passing in testArrayEvenReverseSorted
        testSorter = new MergeSorter(testArrayEvenReverseSorted);

        //Sets comparator to order 0 (gpa)
        testSorter.setComparator(0);

        //Sorts the array accordingly.
        testSorter.sort();

        //Initializes boolean variable equalArray and assigns it to true.
        boolean equalArray = true;

        //Initializes increment int variable j and assigns it to the last index in the array
        int j = testArrayEvenReverseSorted.length - 1;

        //Iterates and compares testSorter.students and testArrayEvenReversedSorted
        for(int i = 0; i < testArrayEvenReverseSorted.length; i++){
            //If students' gpa at index i and j don't match, meaning arrays aren't equal, sets equalArray to false.
            if(!(testSorter.students[i].getGpa() == testArrayEvenReverseSorted[j].getGpa())){
                equalArray = false;
            }
            //Decrements j by 1
            j--;
        }

        //Checks to see if equalArray is true (If elements match up where they should be)
        assertTrue(equalArray);
    }

    /**
     * Test Case #15: Tests that the merge sorting algorithm by credits with a REVERSE-SORTED student list with an
     * even length will properly sort the array descendingly.
     */
    @Test
    public void mergeSortByCreditsWithEvenReverseSortedArray(){

        //Assigns elements to testArrayEvenReverseSorted
        testArrayEvenReverseSorted[0] = new Student(1.0, 76);
        testArrayEvenReverseSorted[1] = new Student(2.7, 65);
        testArrayEvenReverseSorted[2] = new Student(3.5, 25);
        testArrayEvenReverseSorted[3] = new Student(4.0, 17);

        //Assigns testSorter to a new MergeSorter object, passing in testArrayEvenReverseSorted
        testSorter = new MergeSorter(testArrayEvenReverseSorted);

        //Sets comparator to order 1 (credits)
        testSorter.setComparator(1);

        //Sorts the array accordingly
        testSorter.sort();

        //Initializes boolean variable equalArray and assigns it to true.
        boolean equalArray = true;

        //Initializes increment int variable j and assigns it to the last index in the array
        int j = testArrayEvenReverseSorted.length - 1;

        //Iterates and compares testSorter.students and testArrayEvenReversedSorted
        for(int i = 0; i < testArrayEvenReverseSorted.length; i++){
            //If students' credits at index i and j don't match, meaning arrays aren't equal, sets equalArray to false.
            if(!(testSorter.students[i].getCreditsTaken() == testArrayEvenReverseSorted[j].getCreditsTaken())){
                equalArray = false;
            }
            //decrements j by 1
            j--;
        }

        //Checks to see if equalArray is true (If elements match up where they should be)
        assertTrue(equalArray);
    }

    /**
     * Test Case #16: Tests that an IllegalArgumentException will be thrown upon attempting to construct MergeSorter
     * object with a null array.
     */
    @Test
    public void mergeSortWithNullArray(){

        //Checks if constructing a MergeSorter object by passing in a null array throws an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            new MergeSorter(testArrayNull);
        });

    }
}