package zad4;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

// XList is a custom list class that provides additional features for creating and operating on lists
public class XList<T> implements List<T> {

    // A private field to store the elements of the list as an array
    private Object[] elements;

    // A private field to store the size of the list
    private int size;

    // A public constructor that takes an int parameter as the initial capacity of the list
    public XList(int capacity) {
        elements = new Object[capacity];
        size = 0;
    }

    // A public constructor that takes a variable number of arguments of type T, and adds them to the list
    public XList(T... args) {
        elements = Arrays.copyOf(args, args.length);
        size = args.length;
    }

    // A public static method that takes a variable number of arguments of type T, and returns a new XList object containing those elements
    public static <T> XList<T> of(T... args) {
        return new XList<>(args);
    }

    // A public static method that takes a collection parameter of type Collection<? extends T>, and returns a new XList object containing the elements of the collection
    public static <T> XList<T> of(Collection<? extends T> collection) {
        return new XList<>((T[]) collection.toArray());
    }

    // A public static method that takes an array parameter of type T[], and returns a new XList object containing the elements of the array
    public static <T> XList<T> of(T[] array) {
        return new XList<>(Arrays.copyOf(array, array.length));
    }

    // A public static method that takes a string parameter, and returns a new XList object containing the characters of the string as strings
    public static XList<String> charsOf(String s) {
        XList<String> list = new XList<>(s.length());
        for (char c : s.toCharArray()) {
            list.add(String.valueOf(c));
        }
        return list;
    }

    // A public static method that takes a string parameter and an optional string parameter as the separator, and returns a new XList object containing the tokens of the string as strings
    public static XList<String> tokensOf(String s, String sep) {
        return XList.of(s.split(sep));
    }

    public static XList<String> tokensOf(String s) {
        return XList.of(s.split("\\s+"));
    }

    // A public method that takes a collection parameter of type Collection<? extends T>, and returns a new XList object containing the union of the elements of this list and the collection
    public XList<T> union(Collection<? extends T> collection) {
        XList<T> list = new XList<>(this);
        list.addAll(collection);
        return list;
    }

    // A public method that takes a collection parameter of type Collection<?>, and returns a new XList object containing the difference of the elements of this list and the collection
    public XList<T> diff(Collection<?> collection) {
        XList<T> list = new XList<>(this.size);
        for (T element : this) {
            if (!collection.contains(element)) {
                list.add(element);
            }
        }
        return list;
    }

    // A public method that returns a new XList object containing the unique elements of this list
    public XList<T> unique() {
        XList<T> list = new XList<>(this.size);
        Set<T> set = new HashSet<>();
        for (T element : this) {
            if (set.add(element)) {
                list.add(element);
            }
        }
        return list;
    }

    // A public method that returns a new XList object containing the list-combinations of the elements of the collections that are the elements of this list
    public XList<XList<T>> combine() {
        XList<XList<T>> list = new XList<>();
        if (this.isEmpty()) {
            return list;
        }
        // Use a recursive helper method to generate the combinations
        combineHelper(list, new XList<>(), 0);
        return list;
    }

    // A private helper method that recursively generates the combinations
    private void combineHelper(XList<XList<T>> list, XList<T> current, int index) {
        if (index == this.size) {
            // Base case: add the current combination to the list
            list.add(new XList<>(current));
        } else {
            // Recursive case: iterate over the elements of the collection at the current index, and add each one to the current combination
            Collection<T> collection = (Collection<T>) this.get(index);
            for (T element : collection) {
                current.add(element);
                combineHelper(list, current, index + 1);
                current.remove(current.size - 1); // backtrack
            }
        }
    }

    // A public method that takes a function parameter of type Function<T, R>, and returns a new XList object containing the results of applying the function to the elements of this list
    public <R> XList<R> collect(Function<T, R> function) {
        XList<R> list = new XList<>(this.size);
        for (T element : this) {
            list.add(function.apply(element));
        }
        return list;
    }

    // A public method that takes an optional string parameter as the separator, and returns a string that is the concatenation of the elements of this list, with the separator inserted between them
    public String join(String sep) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size; i++) {
            sb.append(this.get(i));
            if (i < this.size - 1) {
                sb.append(sep);
            }
        }
        return sb.toString();
    }

    public String join() {
        return join("");
    }

    // A public method that takes a consumer parameter of type BiConsumer<T, Integer>, and performs the action on each element of this list along with its index
    public void forEachWithIndex(BiConsumer<T, Integer> action) {
        for (int i = 0; i < this.size; i++) {
            action.accept(this.get(i), i);
        }
    }

    // The following methods are the implementation of the List interface methods
    // Some methods are omitted for brevity, but they can be easily implemented using the elements array and the size field

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new XListIterator();
    }

    // A private inner class that implements the Iterator interface for the XList class
    private class XListIterator implements Iterator<T> {

        // A private field to store the current position of the iterator
        private int position;

        // A public constructor that initializes the position to zero
        public XListIterator() {
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) elements[position++];
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public <R> R[] toArray(R[] a) {
        if (a.length < size) {
            return (R[]) Arrays.copyOf(elements, size, a.getClass());
        }
        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean add(T e) {
        ensureCapacity(size + 1);
        elements[size++] = e;
        return true;
    }

    // A private helper method that ensures that the elements array has enough capacity to store the given number of elements
    private void ensureCapacity(int capacity) {
        if (capacity > elements.length) {
            int newCapacity = Math.max(capacity, elements.length * 2);
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object element : c) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        ensureCapacity(size + c.size());
        for (T element : c) {
            elements[size++] = element;
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        checkIndex(index);
        ensureCapacity(size + c.size());
        System.arraycopy(elements, index, elements, index + c.size(), size - index);
        for (T element : c) {
            elements[index++] = element;
        }
        size += c.size();
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // ...continuing from previous code
        boolean modified = false;
        for (Object o : c) {
            if (remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean modified = false;
        for (int i = 0; i < size; i++) {
            if (!c.contains(elements[i])) {
                remove(i--);
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    @Override
    public T set(int index, T element) {
        checkIndex(index);
        T old = (T) elements[index];
        elements[index] = element;
        return old;
    }

    @Override
    public void add(int index, T element) {
        checkIndexForAdd(index);
        ensureCapacity(size + 1);
        System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    @Override
    public T remove(int index) {
        checkIndex(index);
        T removedElement = (T) elements[index];
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null; // clear to let GC do its work
        return removedElement;
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elements[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(elements[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new XListListIterator(0);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        checkIndexForAdd(index);
        return new XListListIterator(index);
    }

    // A private inner class that implements the ListIterator interface for the XList class
    private class XListListIterator extends XListIterator implements ListIterator<T> {

        // A public constructor that takes an int parameter as the initial position of the iterator
        public XListListIterator(int index) {
            super();
            this.position = index;
        }

        @Override
        public boolean hasPrevious() {
            return position > 0;
        }

        @Override
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            return (T) elements[--position];
        }

        @Override
        public int nextIndex() {
            return position;
        }

        @Override
        public int previousIndex() {
            return position - 1;
        }

        @Override
        public void set(T e) {
            if (position == 0 || position == size) {
                throw new IllegalStateException();
            }
            XList.this.set(position - 1, e);
        }

        @Override
        public void add(T e) {
            int i = position;
            XList.this.add(i, e);
            position = i + 1;
        }
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        checkSubListRange(fromIndex, toIndex);
        XList<T> subList = new XList<>(toIndex - fromIndex);
        for (int i = fromIndex; i < toIndex; i++) {
            subList.add(this.get(i));
        }
        return subList;
    }

    // A private helper method that checks if the index is in range for adding
    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // A private helper method that checks if the index is in range
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // A private helper method that checks if the subList range is valid
    private void checkSubListRange(int fromIndex, int toIndex) {
        if (fromIndex < 0 || toIndex > size || fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("fromIndex: " + fromIndex + ", toIndex: " + toIndex + ", Size: " + size);
        }
    }
}
