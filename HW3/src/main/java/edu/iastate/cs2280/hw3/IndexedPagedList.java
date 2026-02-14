/**
 * @author YOUR NAME HERE
 */
package edu.iastate.cs2280.hw3;

import java.util.*;

/**
 * An implementation of the List interface that uses a doubly-linked list of
 * "pages" (nodes), where each page stores multiple items in an array.
 * * This structure is optimized for fast, $O(\log N_{\text{pages}})$
 * index-based access (get, set) and iterator initialization. It achieves this
 * using a separate `ArrayList` (`pageIndex`) that acts as a top-level index,
 * allowing binary search to quickly find the correct page for a given logical
 * index.
 * * Structural modifications (add(int, E), remove(int)) are
 * $O(N_{\text{pages}})$ in the worst case. While finding the target page is
 * fast ($O(\log N_{\text{pages}})$), these operations must update the logical
 * indices of all subsequent pages in the `pageIndex`, which requires a linear
 * scan of the index. This is still significantly faster than a standard
 * `LinkedList` ($O(N)$) for modifications in the middle of the list.
 * * This implementation includes page-splitting (when a page becomes full) and
 * page-merging/rebalancing (when a page becomes under-filled) to maintain
 * efficiency.
 *
 * @param <E> the type of elements in this list
 */
public class IndexedPagedList<E> extends AbstractSequentialList<E> implements List<E> {

    /**
     * Default capacity for each page (node).
     */
    private static final int PAGE_CAPACITY = 4;

    /**
     * The minimum number of items a page must have. If a page's count drops
     * below this, it will trigger a merge or rebalance.
     */
    private static final int HALF_CAPACITY = PAGE_CAPACITY / 2;
    /**
     * A static, reusable comparator for performing binary searches on the
     * `pageIndex`. It compares an `IndexEntry` from the list (the first argument)
     * with a logical index (the "key", or second argument).
     */
    private static final Comparator<Object> INDEX_COMPARATOR = new Comparator<Object>() {
        /**
         * Compares an IndexEntry (from the list) to a logical index (the key).
         *
         * @param entryObj The IndexEntry (cast from Object) from the list.
         * @param key      The logical index (Integer) we are searching for.
         * @return a negative integer, zero, or a positive integer as the
         *         entry's logical index is less than, equal to, or greater than the
         *         key.
         */
        @Override
        public int compare(Object entryObj, Object key) {
            @SuppressWarnings("unchecked") IndexEntry<?> entry = (IndexEntry<?>) entryObj;
            Integer logicalIndex = (Integer) key;
            return entry.logicalIndex.compareTo(logicalIndex);
        }
    };
    /**
     * The dummy head node of the doubly-linked list of pages.
     */
    private final Page<E> head;
    /**
     * The dummy tail node of the doubly-linked list of pages.
     */
    private final Page<E> tail;
    /**
     * The "fast-lane" index. This `ArrayList` stores an `IndexEntry` for
     * *every* data page in the list. It allows for $O(\log N_{\text{pages}})$
     * binary search
     * to find the page corresponding to a given logical index.
     * * This index *must* be kept in sync with the linked list of pages.
     */
    private final ArrayList<IndexEntry<E>> pageIndex;
    /**
     * The total number of elements stored in the list.
     */
    private int totalSize;
    /**
     * A counter for structural modifications (add, remove). Used by the
     * iterator to detect concurrent modifications and fail-fast.
     */
    private int modificationCount;

    /**
     * Constructs a new, empty IndexedPagedList.
     * Initializes the dummy head and tail nodes and the empty page index.
     */
    public IndexedPagedList() {
        // Initialize head and tail dummy nodes, link them.
        head = new Page<E>(null, null);
        tail = new Page<E>(null, null);
        head.next = tail;
        tail.prev = head;

        // Initialize totalSize, modificationCount.
        totalSize = 0;
        modificationCount = 0;

        // Initialize the pageIndex ArrayList.
        pageIndex = new ArrayList<IndexEntry<E>>();
    }

    /**
     * Returns the number of elements in this list.
     *
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return totalSize;
    }


    //HELPER METHOD SECTION (TEMPORARY)

    /**
     * Helper method: Splits the inputted page, current, in half by creating a new page after the inputted page
     * and shifting the 2nd half of the elements to the newly created page. Adds newly created to respective position
     * in pageIndex
     */
    private void split(Page<E> current) {
        //Creates Page<E> object newPage after current, and links next/prev page references accordingly
        Page<E> newPage = new Page<>(current, current.next);
        current.next.prev = newPage;
        current.next = newPage;

        //Shifts the 3rd and 4th element in page current to the 1st and 2nd element positions in newPage respectively.
        newPage.items[0] = current.items[2];
        newPage.items[1] = current.items[3];
        current.items[2] = null;
        current.items[3] = null;

        //Sets the count of both pages to 2 (Each now only has 2 elements post-split)
        current.count = 2;
        newPage.count = 2;

        //Initializes int variable currentPageIndex and assigns it the index value of it's corresponding IndexEntry in pageIndex
        int currentPageIndex = findIndexInPageIndex(current);

        //NEWLY ADDED LINE FOR REGRADE
        pageIndex.get(currentPageIndex).count -= 2;

        //Initializes int variable currentPageLogicalIndex and assigns it the logicalIndex of currentPage (from it's IndexEntry)
        int currentPageLogicalIndex = pageIndex.get(currentPageIndex).logicalIndex;

        //Creates a new IndexEntry object for the newly created page
        IndexEntry<E> newPageIndexEntry = new IndexEntry<>(newPage, currentPageLogicalIndex + 2, 2);

        //Adds the IndexEntry object newPageIndexEntry to the pageIndex arrayList, positioned right after currentPageIndex
        pageIndex.add(currentPageIndex + 1, newPageIndexEntry);

        //Calls updatePageIndex to update the logical index of each IndexEntry after (not including) newPageIndexEntry by 2
        updatePageIndex(currentPageIndex + 2, 2);
    }

    /**
     * Helper method: "Unlinks" a page by changing the .next and .prev fields of the pages before and after it to link
     * to one another instead to pageRemove. No incoming references to pageRemove causes pageRemove to get automatically
     * deleted by the JVM.
     */
    private void unlink(Page<E> pageRemove) {
        //Re-assigns next of the page before pageRemove to the page after pageRemove
        pageRemove.prev.next = pageRemove.next;

        //Re-assigns next of the page before pageRemove to the page after pageRemove
        pageRemove.next.prev = pageRemove.prev;

        //Initializes int variable removedPagedEntry and assigns it the index of pageRemove's corresponding IndexEntry in pageIndex
        int removedPagedEntry = findIndexInPageIndex(pageRemove);

        //If removedPagedEntry is not equal to -1, meaning removedPagedEntry is a valid index value (exists), removes the IndexEntry
        if (removedPagedEntry != -1) {
            pageIndex.remove(removedPagedEntry);
        }
    }


    /**
     * Appends the specified element to the end of this list.
     * * This operation is $O(N_{\text{pages}})$ in the worst case, identical to
     * add(size(), item).
     *
     * @param item element to be appended to this list
     * @return {@code true} (as specified by List#add)
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public boolean add(E item) { //Should call add(int pos, item) method.
        // TODO - Avoid code duplication if you can

        //If item is null, throws a NullPointerException
        if (item == null) {
            throw new NullPointerException();
        }

        //Calls the parameterized add() method of the list, passing in size() as pos and item as the item to be added
        add(size(), item);


        //Returns true (reaches this statement if there were no issues with add call above
        return true;
    }



    /**
     * Inserts the specified element at the specified position in this list.
     * * This operation is $O(N_{\text{pages}})$ in the worst case.
     * Finding the insertion point is fast ($O(\log N_{\text{pages}})$),
     * but updating the `pageIndex` after the insertion requires a
     * linear scan of the index ($O(N_{\text{pages}})$).
     * * This method performs the following steps:
     * 1. Finds the target page and offset for insertion using findPageForLogicalIndex.
     * 2. Increment modCount, totalSize.
     * 3. Handles three cases:
     * - List is empty: Creates a new page.
     * - Target page has space: Adds the item.
     * - Target page is full: Performs a <b>split</b> operation.
     * 4. Updates the `pageIndex` to reflect changes in page counts,
     * new pages, and subsequent logical indices.
     *
     * @param pos  the index at which the specified element is to be inserted
     * @param item the element to be inserted
     * @throws NullPointerException      if the specified element is null
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code pos < 0 || pos > size()})
     */
    @Override
    public void add(int pos, E item) {
        // TODO

        //If item is null, throws a NullPointerException
        if (item == null) {
            throw new NullPointerException();
        }
        //if pos is out of bounds of the list, throws an IndexOutOfBoundsException
        if (pos < 0 || pos > size()) {
            throw new IndexOutOfBoundsException();
        }

        //Increments modification count to show modification outside iterator was performed
        modificationCount++;

        //If number of elements currently in list is 0 (no pages)
        if (this.totalSize == 0) {
            //Creates a new page, and links the next and prev references to the head and tail nodes respectively
            Page<E> initializePage = new Page<E>(head, tail);
            head.next = initializePage;
            tail.prev = initializePage;

            //Adds the item to the first position (offset 0) in the page
            initializePage.addItem(0, item);

            //Increments totalSize
            totalSize++;

            //Initializes IndexEntry initializePageIndexEntry for the newly created page
            IndexEntry<E> initializePageIndexEntry = new IndexEntry<E>(initializePage, 0, 1);

            //Adds the newly created page's IndexEntry object to the pageIndex ArrayList
            pageIndex.add(initializePageIndexEntry);

            //Exits method gracefully
            return;
        }

        //Initializes PageInfo object insertPageInfo, and assigns it the result from findPageForLogicalIndex at pos
        PageInfo<E> insertPageInfo = findPageForLogicalIndex(pos);

        //Initializes Page object pageToInsert, which will contain the page that the item will be inserted into
        Page<E> pageToInsert;

        //Initialize int variable offsetToInsert, which will contain the position in the page where the item will be inserted.
        int offsetToInsert;


        //if the number of elements in pageToInsert page is equal to the capacity (4)
        if (insertPageInfo.page.count == PAGE_CAPACITY) {

            //Calls the split helper method on the page of insertPageInfo
            split(insertPageInfo.page);

            //Initializes int variable currentIndex and assigns it to the index of the page's IndexEntry in pageIndex
            int currentIndex = findIndexInPageIndex(insertPageInfo.page);

            //Initializes Page objects current and next and assigns them to the page of currentIndex and the next page
            Page<E> current = pageIndex.get(currentIndex).page;
            Page<E> next = current.next;

            //If the offset to isert the element at is less than 2, element is inserted in the current page
            if (insertPageInfo.offset < 2) {

                //pageToInsert is assigned page current
                pageToInsert = current;

                //offsetToInsert is assigned the offset of the page represented by the insertPageInfo IndexEntry object
                offsetToInsert = insertPageInfo.offset;

            //Othewise, offset is > 2, meaning it will be inserted in the newly created page
            } else {
                //Assigns pageToInsert to the page after current, which is the newly created page from the split
                pageToInsert = next;

                //Assigns offsetToInsert to the offset from the page represented by insertPageInfo - 2
                offsetToInsert = insertPageInfo.offset - 2;
            }

        //If the page has not reached the capacity
        } else {

            //Assigns pageToInsert to the page from the IndexEntry object insertPageInfo
            pageToInsert = insertPageInfo.page;

            //Assigns offsetToInsert to the page's offset position from the IndexEntry object insertPageInfo
            offsetToInsert = insertPageInfo.offset;
        }

        //Calls the addItem helper method to insert item at the position defined by offsetToInsert
        pageToInsert.addItem(offsetToInsert, item);

        //Increments totalSize
        totalSize++;

        //Initializes int variable insertedPageIndex and assigns it to the index value of pageToInsert's IndexEntry object in pageIndex
        int insertedPageIndex = findIndexInPageIndex(pageToInsert);

        //NEWLY ADDED LINE FOR REGRADE
        pageIndex.get(insertedPageIndex).count++;

        //Updates the logicalIndex's of every IndexEntry object after insertedPageIndex
        updatePageIndex(insertedPageIndex + 1, 1);


    }

    /**
     * Removes the element at the specified position in this list.
     * * This operation is $O(N_{\text{pages}})$ in the worst case.
     * Finding the element is fast ($O(\log N_{\text{pages}})$),
     * but updating the `pageIndex` after the removal (and any potential
     * merge) requires a linear scan of the index ($O(N_{\text{pages}})$).
     * * This method performs the following steps:
     * 1. Finds the target page and offset for removal using findPageForLogicalIndex.
     * 2. Increment modCount, decrement totalSize.
     * 2. Removes the item from the page's array.
     * 3. If the page's count drops below `HALF_CAPACITY`, it triggers:
     * - <b>Rebalance:</b> If the *successor* page has items to spare,
     * one item is moved to the current page.
     * - <b>Full Merge:</b> If the *successor* page does not have items
     * to spare, all items from the successor are moved to the
     * current page, and the successor page is removed.
     * 4. Updates the `pageIndex` to reflect all changes.
     * 5. Make sure to handle the edge case of page becoming empty AND it's the last page.
     *
     * @param pos the index of the element to be removed
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code pos < 0 || pos >= size()})
     */
    @Override
    public E remove(int pos) {
        // TODO

        //If pos is out of bounds of the list, throws an IndexOutOfBoundsException
        if (pos < 0 || pos >= size()) {
            throw new IndexOutOfBoundsException();
        }

        //Initializes PageInfo object and assigns it to the PageInfo object returned by findPageForLogicalIndex at pos.
        PageInfo<E> targetedPageInfo = findPageForLogicalIndex(pos);

        //Initializes Page object targetedPage and assigns it to the page stored by targetedPageInfo
        Page<E> targetedPage = targetedPageInfo.page;

        //Initializes int variable targetedOffset and assigns it to the offset stored by targetedPageInfo
        int targetedOffset = targetedPageInfo.offset;

        //Initializes elementToBeRemoved object of type E and assigns it to the removed element at targetedOffset
        E elementToBeRemoved = targetedPage.removeItem(targetedOffset);

        //NEWLY ADDED LINE FOR REGRADE
        pageIndex.get(findIndexInPageIndex(targetedPage)).count--;

        //Increments modificationCount to show modification outside iterator was done
        modificationCount++;

        //Decrements totalSize, as an element was removed
        totalSize--;

        //If the number of elements in targetedPage is 0, meaning it's now an empty page after element removal
        if (targetedPage.count == 0) {
            //Calls the unlink helper method to remove the page targetedPage entirely from list
            unlink(targetedPage);

            //Returns the removed element
            return elementToBeRemoved;
        }

        //If the number of elements in targetedPage is below half (2), initiates either rebalance or merge process
        if (targetedPage.count < HALF_CAPACITY) {

            //Initializes Page object successor and assigns it to the page after targetedPage
            Page<E> successor = targetedPage.next;

            //If successor's number of elements is greater than half capacity, (2) initiates the rebalance process
            if (successor.count > HALF_CAPACITY) {

                //Adds the first element in successor to the position to right of targetedOffset in targetedPage
                targetedPage.addItem(targetedOffset + 1, successor.items[0]);

                //Removes the shifted element from successor (So there isn't 2 of the element)
                successor.removeItem(0);

                //Initializes int variable successorIndex and assigns it to the index of successor's IndexEntry object in pageIndex
                int successorIndex = findIndexInPageIndex(successor);

                //NEWLY ADDED LINE FOR REGRADE
                pageIndex.get(findIndexInPageIndex(targetedPage)).count++;

                //NEWLY ADDED LINE FOR REGRADE
                pageIndex.get(successorIndex).count--;

                //Calls updatePageIndex to update logicalIndex values of all IndexEntry objects from successorIndex to the last
                updatePageIndex(successorIndex, -1);

            //Otherwise, successor's capacity is at or below half capacity, and initiates merge process
            } else {

                //Iterates through successor and shifts it's elements to the next available position in targetedPage
                for (int i = 0; i < successor.count; i++) {
                    targetedPage.items[targetedPage.count] = successor.items[i];
                    targetedPage.count++;
                }

                //NEWLY ADDED LINE FOR REGRADE
                pageIndex.get(findIndexInPageIndex(targetedPage)).count += successor.count;

                //After merge, successor is an empty page, and is removed with call to unlink
                unlink(successor);
            }

            //Initializes int variable targetedPageIndex and assigns it to the index of targetedPage's IndexEntry object in pageIndex
            int targetedPageIndex = findIndexInPageIndex(targetedPage);

            //if targetedPageIndex is not equal to -1, meaning the IndexEntry exists
            if (targetedPageIndex != -1) {

                //Updates the logicalIndex values of all IndexEntry objects after targetedPageIndex by -1
                updatePageIndex(targetedPageIndex + 1, -1);
            }
        }

        //Returns the element that was removed
        return elementToBeRemoved;
    }

    /**
     * Returns a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list.
     * * The iterator is initialized in $O(\log N_{\text{pages}})$ time by using
     * `findPageForLogicalIndex` to immediately seek to the correct starting
     * page and offset.
     *
     * @param pos the index of the first element to be returned from the
     *            list iterator (by a call to ListIterator#next)
     * @return a list iterator over the elements in this list (in proper
     * sequence), starting at the specified position in the list
     * @throws IndexOutOfBoundsException if the index is out of range
     *                                   ({@code pos < 0 || pos > size()})
     */
    @Override
    public ListIterator<E> listIterator(int pos) {

        //Returns a new instance of PagedListIterator, initialized at pos
        return new PagedListIterator(pos);
    }

    // --- Private Helper Methods and Classes ---

    /**
     * Finds the page and in-page offset for a given logical index.
     * * This method runs in $O(\log N_{\text{pages}})$ time by performing a
     * binary search on the `pageIndex`.
     * * This method performs the following steps:
     * 1. Preform a binary search on the `pageIndex` using Collections.binarySearch with INDEX_COMPARATOR.
     * 2. Handle cases for index >= 0, index < 0.
     * 3. Calculate the offset within the page's array.
     * 4. Return the page and offset.
     *
     * @param pos The logical index to find.
     * @return A PageInfo object containing the page and offset.
     */
    private PageInfo<E> findPageForLogicalIndex(int pos) {
        // TODO

        //If the number of elements in the list is equal to 0, meaning the list is empty
        if (this.totalSize == 0) {

            //Returns a PageInfo object, whose page it represents is null, and the offset equal 0 (placeholder value)
            return new PageInfo<E>(null, 0);
        }

        //Initializes int variable pagePosition to contain result of calling binary search on pageIndex for IndexEntry with pos
        int pagePosition = Collections.binarySearch(pageIndex, pos, INDEX_COMPARATOR);

        //Initializes IndexEntry object obtainedIndexEntry, which will contain corresponding IndexEntry whose logicalIndex = pos
        IndexEntry<E> obtainedIndexEntry;

        //Initializes int variable offsetToInsert, which will contain the position for a future element to be inserted to
        int offsetToInsert;

        //If pagePosition is >= 0, meaning a valid index was returned by the binary search
        if (pagePosition >= 0) {

            //Assigns obtainedIndexEntry to the corresponding IndexEntry object from pageIndex
            obtainedIndexEntry = pageIndex.get(pagePosition);

            //Sets offsetToInsert to 0, as since pos was equal to some logicalIndex, pos would also point to first element in the page
            offsetToInsert = 0;

            //Returns a PageInfo object containing the page from obtainedIndexEntry and the offsetToInsert at the beginning of page
            return new PageInfo<E>(obtainedIndexEntry.page, offsetToInsert);

        //Otherwise, if the binary search returned some negative value (pos wasn't found correspond to any IndexEntry's logicalIndex)
        } else {

            //Decodes negative value from Collections.binarySearch and stores it in int variable decodedEntryPoint
            int decodedEntryPoint = -pagePosition - 1;

            //If decodedEntryPoint is 0, this means pos is located in the first page
            if (decodedEntryPoint == 0) {

                //Assigns obtainedIndexEntry to first indexEntry in pageIndex (the IndexEntry object of the first page)
                obtainedIndexEntry = pageIndex.getFirst();

                //Assigns offsetToInsert to pos - logicalIndex of obtainedIndexEntry to determine proper offset of the page
                offsetToInsert = pos - obtainedIndexEntry.logicalIndex;

                //Returns a PageInfo object containing the page contained by obtainedIndexEntry and evaluated offsetToInsert
                return new PageInfo<E>(obtainedIndexEntry.page, offsetToInsert);
            }

            //Assigns obtainedIndexEntry to the IndexEntry object left of the supposed index of pos (decodedEntryPoint)
            obtainedIndexEntry = pageIndex.get(decodedEntryPoint - 1);

            //Assigns offsetToInsert to pos - the logicalIndex of obtainedIndexEntry to evaluate the proper offset position in page
            offsetToInsert = pos - obtainedIndexEntry.logicalIndex;

            //Returns a PageInfo object containing the page contained by obtainedIndexEntry and evaluated offsetToInsert
            return new PageInfo<E>(obtainedIndexEntry.page, offsetToInsert);

        }
    }

    /**
     * Finds the index (in the `pageIndex` `ArrayList`) of the `IndexEntry`
     * that corresponds to the given `page`.
     * * This is an $O(N_{\text{pages}})$ operation (linear scan). It is only
     * called by `add` and `remove`, which are already $O(N_{\text{pages}})$
     * due to `updatePageIndex`.
     *
     * @param page The page to find.
     * @return The index in `pageIndex`, or -1 if not found.
     */
    private int findIndexInPageIndex(Page<E> page) {
        // TODO

        //Initializes and assigns int variable count to 0
        int count = 0;

        //Iterates through every IndexEntry object in pageIndex
        for (IndexEntry<E> cur : pageIndex) {

            //if reference of page is equal to the reference of the page stored by IndexEnty cur, the IndexEntry object is found
            if (page == cur.page) {

                //Returns count, which will be the index of identified IndexEntry object in pageIndex
                return count;
            } else {

                //Otherwise, increments count by 1
                count++;
            }
        }

        //If no IndexEntry object has been found to contained the reference of page, returns -1
        return -1;
    }

    /**
     * Updates the `logicalIndex` of all `IndexEntry` objects in the
     * `pageIndex` starting from `startIndex`, adding `delta` to each.
     * * This is an $O(N_{\text{pages}})$ operation and is the reason why
     * `add` and `remove` are not $O(\log N)$.
     *
     * @param startIndex The index in `pageIndex` to start updating from.
     * @param delta      The amount to add to each `logicalIndex` (e.g., +1 or -1).
     */
    private void updatePageIndex(int startIndex, int delta) {
        // TODO

        //Iterates through the entire pageIndex ArrayList
        for (int i = startIndex; i < pageIndex.size(); i++) {
            //Gets the IndexEntry object at index i, and increments it's logicalIndex field by the value defined by delta
            pageIndex.get(i).logicalIndex += delta;
        }

    }

    /**
     * Returns a string representation of the internal structure of the list.
     * This string shows the contents of each page (node) and uses "-"
     * to represent empty slots in each page's array.
     *
     * @return A string in the format [(item1, item2, -, -), (item3, -, -, -)]
     */
    public String toStringInternal() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");

        Page<E> currentPage = head.next;
        while (currentPage != tail) {
            sb.append("(");
            for (int i = 0; i < PAGE_CAPACITY; i++) {
                if (i < currentPage.count) {
                    sb.append(currentPage.items[i]);
                } else {
                    sb.append("-");
                }

                if (i < PAGE_CAPACITY - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")");
            if (currentPage.next != tail) {
                sb.append(", ");
            }
            currentPage = currentPage.next;
        }

        sb.append("]");
        return sb.toString();
    }

    /**
     * Returns a string representation of the internal structure of the list,
     * including the position of a ListIterator's cursor.
     * <p>
     * The cursor position (represented by "|") is determined by the
     * iterator's `nextIndex()` method.
     *
     * @param iter The ListIterator to visualize.
     * @return A string showing page contents and the iterator's cursor position.
     */
    public String toStringInternal(ListIterator<E> iter) {
        // Find the logical position of the iterator's cursor
        int logicalIndex = iter.nextIndex();
        // Find the physical page and offset that correspond to that logical index
        PageInfo<E> cursorInfo = findPageForLogicalIndex(logicalIndex);
        Page<E> cursorPage = cursorInfo.page;
        int cursorOffset = cursorInfo.offset;

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        Page<E> currentPage = head.next;
        while (currentPage != tail) {
            sb.append("(");
            for (int i = 0; i < PAGE_CAPACITY; i++) {
                // Check if the cursor should be printed *before* this item
                if (currentPage == cursorPage && i == cursorOffset) {
                    sb.append("| ");
                }

                if (i < currentPage.count) {
                    sb.append(currentPage.items[i]);
                } else {
                    sb.append("-");
                }

                if (i < PAGE_CAPACITY - 1) {
                    sb.append(", ");
                }
            }
            sb.append(")");
            if (currentPage.next != tail) {
                sb.append(", ");
            }
            currentPage = currentPage.next;
        }

        // Handle case where cursor is at the very end of the list (pos == totalSize)
        if (cursorPage == tail) {
            sb.append(", (| -)");
        }

        sb.append("]");
        return sb.toString();
    }

    /**
     * A private static inner class representing a "page" (node) in the
     * doubly-linked list. Each page holds an array of items.
     */
    private static class Page<E> {
        /**
         * The array holding the data items.
         */
        final E[] items;

        /**
         * The number of items currently stored in this page.
         */
        int count;

        /**
         * The link to the previous page in the list.
         */
        Page<E> prev;

        /**
         * The link to the next page in the list.
         */
        Page<E> next;

        /**
         * Constructs a new page.
         *
         * @param prev The previous page.
         * @param next The next page.
         */
        @SuppressWarnings("unchecked")
        Page(Page<E> prev, Page<E> next) {
            this.items = (E[]) new Object[PAGE_CAPACITY];
            this.count = 0;
            this.prev = prev;
            this.next = next;
        }

        /**
         * Inserts an item into this page's array at a specific offset.
         * Shifts existing items to the right.
         *
         * @param offset The index in the `items` array to insert at.
         * @param item   The item to insert.
         */
        void addItem(int offset, E item) {
            if (offset < 0 || offset > count) {
                throw new IndexOutOfBoundsException("Internal add error: offset " + offset + ", count " + count);
            }
            System.arraycopy(items, offset, items, offset + 1, count - offset);
            items[offset] = item;
            count++;
        }

        /**
         * Removes an item from this page's array at a specific offset.
         * Shifts existing items to the left.
         *
         * @param offset The index in the `items` array to remove.
         * @return The item that was removed.
         */
        E removeItem(int offset) {
            if (offset < 0 || offset >= count) {
                throw new IndexOutOfBoundsException("Internal remove error: offset " + offset + ", count " + count);
            }
            E item = items[offset];
            int numToMove = count - offset - 1;
            if (numToMove > 0) {
                System.arraycopy(items, offset + 1, items, offset, numToMove);
            }
            items[count - 1] = null;
            count--;
            return item;
        }
    }

    /**
     * A private static inner class that holds information about a page,
     * used as an entry in the `pageIndex`.
     */
    private static class IndexEntry<E> {
        /**
         * A direct reference to the page object.
         */
        final Page<E> page;

        /**
         * The logical index of the *first* item on this page (items[0]).
         */
        Integer logicalIndex;

        /**
         * The number of items currently stored on this page.
         */
        int count;

        /**
         * Constructs a new index entry.
         *
         * @param page         The page to reference.
         * @param logicalIndex The logical index of the first item on this page.
         * @param count        The current item count of this page.
         */
        IndexEntry(Page<E> page, int logicalIndex, int count) {
            this.page = page;
            this.logicalIndex = logicalIndex;
            this.count = count;
        }
    }

    /**
     * A simple private inner class to return two values (page and offset)
     * from the `findPageForLogicalIndex` method.
     *
     * @param <T> The type of element held in the page.
     */
    private class PageInfo<T> {
        /**
         * The page found.
         */
        final Page<T> page;

        /**
         * The calculated offset within the page's array.
         */
        final int offset;

        /**
         * Constructs a new PageInfo.
         *
         * @param page   The page.
         * @param offset The offset.
         */
        PageInfo(Page<T> page, int offset) {
            this.page = page;
            this.offset = offset;
        }
    }

    /**
     * The private implementation of the `ListIterator` interface.
     * * This iterator is "fail-fast" and will throw a
     * ConcurrentModificationException if the list is structurally
     * modified by any method other than the iterator's own `add` or `remove`
     * methods.
     */
    private class PagedListIterator implements ListIterator<E> {

        /**
         * The page that contains the *next* element to be returned by `next()`.
         */
        private Page<E> currentPage;

        /**
         * The offset within `currentPage` of the *next* element to be
         * returned by `next()`.
         */
        private int pageOffset;

        /**
         * The logical index of the *next* element to be returned by `next()`.
         */
        private int logicalIndex;

        /**
         * The logical index of the element last returned by `next()` or
         * `previous()`. Used by `set()` and `remove()`.
         * Set to -1 if no such element exists.
         */
        private int lastItemIndex;

        /**
         * The direction of the last move, to validate `set()` and `remove()` calls.
         */
        private Direction lastDirection;

        /**
         * The value of `modificationCount` that this iterator expects.
         * Used to detect concurrent modifications.
         */
        private int expectedModificationCount;

        /**
         * Constructs a new iterator starting at the given logical position.
         * This operation is fast ($O(\log N_{\text{pages}})$) due to
         * `findPageForLogicalIndex`.
         * * This method performs the following steps:
         * 1. Use findPageForLogicalIndex(pos) to get the starting PageInfo.
         * 2. Initialize currentPage and pageOffset from the PageInfo.
         * 3. Initialize logicalIndex to pos.
         * 4. Initialize lastItemIndex to -1, lastDirection to NONE.
         * 5. Initialize expectedModificationCount to modificationCount.
         *
         * @param pos The logical index to start at.
         */
        PagedListIterator(int pos) {
            // TODO

            //Initializes PageInfo object startingPageInfo and assigns it the result of findPageForLogicalIndex at pos
            PageInfo<E> startingPageInfo = findPageForLogicalIndex(pos);

            //Assigns currentPage of PagedListIterator instance to be the page stored by startingPageInfo
            currentPage = startingPageInfo.page;

            //Assigns pageOffset to be the offset of the page stored by startingPageInfo
            pageOffset = startingPageInfo.offset;

            //Assigns logicalIndex to the pos passed into the iterator constructor
            logicalIndex = pos;

            //Assigns lastItemIndex to -1, as nothing has been iterated over yet (Safeguard for remove & set)
            lastItemIndex = -1;

            //Assigns lastDirection to the NONE enum, as the iterator has not called next or previous yet
            lastDirection = Direction.NONE;

            //Sets expectedModificationCount equal to modificationCount, used to determine outside-iterator modification
            expectedModificationCount = modificationCount;
        }

        /**
         * Checks for concurrent modification.
         *
         * @throws ConcurrentModificationException if the list has been modified
         *                                         externally.
         */
        private void checkComodification() {
            if (expectedModificationCount != modificationCount) {
                throw new ConcurrentModificationException();
            }
        }

        /**
         * Returns `true` if this list iterator has more elements when
         * traversing the list in the forward direction.
         * * This method performs the following steps:
         * 1. Check for comodification.
         * 2. Check if logicalIndex is less than totalSize.
         */
        @Override
        public boolean hasNext() {
            // TODO

            //Calls checkModification to determine if modification outside the iterator was performed
            checkComodification();

            //Returns if logicalIndex is less than total number of elements in the list, meaning it can safely call next()
            return logicalIndex < totalSize;
        }

        /**
         * Returns the next element in the list and advances the cursor
         * position. Remember to check for comodification.
         */

        @Override
        public E next() {
            // TODO

            //Calls checkModification to determine if modification outside the iterator was performed
            checkComodification();

            //If has next is false, meaning logicalIndex is equal to size (at the last element), throws a NoSuchElementException
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            //If the offset position is equal to the last element in the page, moves over to first element of the next page
            if (pageOffset == currentPage.count) {
                currentPage = currentPage.next;
                pageOffset = 0; //First element of the next page
            }

            //Initializes object data of type E to store the element at pageOffset in the proper page.
            E data = currentPage.items[pageOffset];

            //Assigns lastItemIndex to logicalIndex, as this is the index of the item returned by next.
            lastItemIndex = logicalIndex;

            //Increments logicalIndex and pageOffset by 1 to move iterator to next element
            logicalIndex++;
            pageOffset++;

            //Sets lastDirection to the NEXT enum, as now iterator has iterated over and is in moving in this direction
            lastDirection = Direction.NEXT;

            //Returns data, which was the element iterated over.
            return data;
        }


        /**
         * Returns `true` if this list iterator has more elements when
         * traversing the list in the reverse direction. Remember to check for comodification.
         */
        @Override
        public boolean hasPrevious() {
            // TODO

            //Calls checkModification to determine if modification outside the iterator was performed
            checkComodification();

            //Returns if logicalIndex is greater than 0, meaning if it still can move to the left.
            return logicalIndex > 0;
        }

        /**
         * Returns the previous element in the list and moves the cursor
         * position backwards. Remember to check for comodification.
         */

        @Override
        public E previous() {
            // TODO

            //Calls checkModification to determine if modification outside the iterator was performed
            checkComodification();

            //If has previous is false, meaning there is no more elements to the left to iterate over, throws a NoSuchElementException
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }


            //Decrements the page offset to position the iterator correctly
            --pageOffset;

            //If the page offset becomes less than zero post-decrement, the iterator moves to the previous page
            if (pageOffset < 0) {
                currentPage = currentPage.prev;
                pageOffset = currentPage.count - 1; //Sets offset to the last element in the previous page
            }

            //Decrements logicalIndex to point at the proper element
            --logicalIndex;

            //Initializes object data of Type E and stores the element at the offset of the current page
            E data = currentPage.items[pageOffset];

            //Assigns lastItemIndex to logicalIndex, as this is the index of the item returned by previous
            lastItemIndex = logicalIndex;

            //Sets the direction to  the PREVIOUS enum, as now the iterator is moving left
            lastDirection = Direction.PREVIOUS;

            //Returns the data obtained from the page at the proper offset
            return data;
        }


        /**
         * Returns the index of the element that would be returned by a
         * subsequent call to `next()`. Remember to check for comodification.
         */
        @Override
        public int nextIndex() {
            // TODO

            //Calls checkModification to determine if modification outside the iterator was performed
            checkComodification();

            //Returns the logicalIndex, because the iterator for a next() call is already pointing to the element
            return logicalIndex;
        }

        /**
         * Returns the index of the element that would be returned by a
         * subsequent call to `previous()`. Remember to check for comodification.
         */
        @Override
        public int previousIndex() {
            // TODO

            //Calls checkModification to determine if modification outside the iterator was performed
            checkComodification();

            //Returns logicalIndex - 1, as that is the index left of the current logicalIndex, which is what previous() returns
            return logicalIndex - 1;
        }

        /**
         * Removes from the list the last element that was returned by
         * `next()` or `previous()`.
         * * This operation delegates to the outer class's `remove(int)` method,
         * which runs in $O(N_{\text{pages}})$ time. The iterator then
         * re-synchronizes its position in $O(\log N_{\text{pages}})$ time.
         * * This method performs the following steps:
         * 1. Check for comodification.
         * 2. Check for IllegalStateException.
         * 3. Call the outer class's remove: IndexedPagedList.this.remove(lastItemIndex);
         * 4. Update iterator state
         * 5. Update modification counts
         * 6. Reset lastDirection to NONE and lastItemIndex to -1.
         *
         * @throws IllegalStateException if `next()` or `previous()` have not
         *                               been called, or `remove()` or `add()`
         *                               have been called since the last move.
         */
        @Override
        public void remove() {
            // TODO

            //Calls checkModification to determine if modification outside the iterator was performed
            checkComodification();
            if (lastItemIndex == -1) {
                throw new IllegalStateException();
            }

            //Calls the IndexPagedList's remove method, passing in lastItemIndex as the position of the element to be removed
            IndexedPagedList.this.remove(lastItemIndex); //Increments modificationCount by 1

            //If the direction is set to NEXT, decrements offset and logical index to reposition iterator (Not needed for PREVIOUS)
            if (lastDirection == Direction.NEXT) {
                pageOffset--;
                logicalIndex--;
            }

            //Updates expectedModificationCount, as list's remove method was called (increments modCount), but permitted.
            expectedModificationCount = modificationCount;

            //Sets lastItemIndex to -1, safeguard for avoiding additional removal calls until next/previous has been called
            lastItemIndex = -1;

        }

        /**
         * Replaces the last element returned by `next()` or `previous()`
         * with the specified element.
         * * This operation is $O(1)$ after finding the page in
         * $O(\log N_{\text{pages}})$ time.
         * * This method performs the following steps:
         * 1. Check for comodification.
         * 2. Check for Exceptions.
         * 3. Find the page/offset for lastItemIndex using findPageForLogicalIndex.
         * 4. Set the item in that page's array.
         * 5. Do NOT change modificationCount.
         *
         * @throws IllegalStateException if `next()` or `previous()` have not
         *                               been called, or `remove()` or `add()`
         *                               have been called since the last move.
         * @throws NullPointerException  if the specified element is null.
         */
        @Override
        public void set(E item) {
            // TODO

            //Calls checkModification to determine if modification outside the iterator was performed
            checkComodification();

            //If lastItemIndex is -1, meaning iterator has not iterated over an element, throws an IllegalStateException
            if (lastItemIndex == -1) {
                throw new IllegalStateException();
            }

            //If item to be added is null, throws a NullPointerException
            if (item == null) {
                throw new NullPointerException();
            }

            //Initializes PageInfo object modPage info and assigns it the result of findPageForLogicalIndex at lastItemIndex
            PageInfo<E> modPageInfo = findPageForLogicalIndex(lastItemIndex);

            //Initializes Page object modPage and assigns it the page stored in modPageInfo
            Page<E> modPage = modPageInfo.page;

            //Initializes int variable modPageOffset and assigns it the offset position of the page stored in modPageInfo
            int modPageOffset = modPageInfo.offset;

            //Inserts the item at the offset position in modPage
            modPage.items[modPageOffset] = item;
        }

        /**
         * Inserts the specified element into the list at the iterator's
         * current position.
         * * This operation delegates to the outer class's `add(int, E)` method,
         * which runs in $O(N_{\text{pages}})$ time. The iterator then
         * re-synchronizes its position in $O(\log N_{\text{pages}})$ time.
         * * This method performs the following steps:
         * 1. Check for comodification.
         * 2. Check for Exceptions.
         * 3. Call the outer class's add: IndexedPagedList.this.add(logicalIndex, item);
         * 4. Update iterator state
         * 5. Update modification counts
         * 6. Reset lastDirection to NONE and lastItemIndex to -1.
         *
         * @throws NullPointerException if the specified element is null.
         */
        @Override
        public void add(E item) {
            // TODO

            //Calls checkModification to determine if modification outside the iterator was performed
            checkComodification();

            //If item to be added is null, throws a NullPointerException
            if (item == null) {
                throw new NullPointerException();
            }

            //Calls IndexedPagedList's add method, passing in logicalIndex as the insert position and item
            IndexedPagedList.this.add(logicalIndex, item);

            //Increments logicalIndex and pageOffset by 1 to reposition iterator post-add
            logicalIndex++;

            //Incase a split occured during adding, properly sets currentPage and pageOffset to the proper page and position
            PageInfo<E> pageRepositionInfo = findPageForLogicalIndex(logicalIndex);
            currentPage = pageRepositionInfo.page;
            pageOffset = pageRepositionInfo.offset;

            //Updates expectedModificationCount, as list's add method was called (increments modCount), but permitted.
            expectedModificationCount = modificationCount;

            //Sets the direction the NONE enum
            lastDirection = Direction.NONE;

            //Sets lastItemIndex to -1 to avoid any calls to remove()/set() without a call to next()/previous()
            lastItemIndex = -1;
        }

        /**
         * Enum to track the last operation (`next` or `previous`)
         * for `remove()` and `set()` validation.
         */
        private enum Direction {
            NONE, NEXT, PREVIOUS
        }
    }


    public static void main(String[] args) {
        // Note: PAGE_CAPACITY is 4 in the skeleton


        IndexedPagedList<String> list = new IndexedPagedList<>();
        System.out.println("New list: " + list.toStringInternal());


        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        System.out.println(
                "Add A, B, C, D: " + list.toStringInternal() + " (Size: " + list.size() + ")"
        );

         

        // --- Test Split ---
        list.add("E");
        System.out.println(
                "Add E (split): " + list.toStringInternal() + " (Size: " + list.size() + ")"
        );
         

        list.add("F");
        System.out.println(
                "Add F: " + list.toStringInternal() + " (Size: " + list.size() + ")"
        );
         

        list.add("G");
        System.out.println(
                "Add G (split): " + list.toStringInternal() + " (Size: " + list.size() + ")"
        );
         

        // --- Test Rebalance ---
        list.remove(2);
        System.out.println(
                "Remove C @ 2: " + list.toStringInternal() + " (Size: " + list.size() + ")"
        );
        System.out.println("(Rebalance triggered)");
         

        // --- Test Merge ---
        list.remove(2);
        System.out.println(
                "Remove D @ 2: " + list.toStringInternal() + " (Size: " + list.size() + ")"
        );
        System.out.println("(Merge triggered)");
         

        // --- Test Iterator ---
        ListIterator<String> iter = list.listIterator(3);
        System.out.println("\nIterator @ 3: " + list.toStringInternal(iter));
         

        System.out.println("iter.next() -> " + iter.next());
         
        System.out.println("After next(): " + list.toStringInternal(iter));
         

        System.out.println("iter.previous() -> " + iter.previous());
         
        System.out.println("After previous(): " + list.toStringInternal(iter));

         
        iter.remove();
        System.out.println(
                "iter.remove(): " + list.toStringInternal(iter) + " (Size: " + list.size() + ")"
        );
         

        iter.add("Z");
        System.out.println(
                "iter.add(Z): " + list.toStringInternal(iter) + " (Size: " + list.size() + ")"
        );
         

        System.out.println("iter.next() -> " + iter.next());
         

        System.out.println(
                "Final state: " + list.toStringInternal(iter) + " (Size: " + list.size() + ")"
        );
         


    }
}
