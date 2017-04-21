package ru.otus.dobrovolsky.collections;

import ru.otus.dobrovolsky.exceptions.MethodNotImplementedException;

import java.util.*;

/**
 * Created by ketaetc on 18.04.17.
 */
public class MyArrayList<T> implements List<T> {
    private final int DEF_SIZE = 10;
    private int size = 0;
    private Object[] data = new Object[DEF_SIZE];

    public MyArrayList() {
    }

    public MyArrayList(int initSize) {
        if (initSize > 0) {
            data = new Object[initSize];
        } else {
            throw new IllegalArgumentException("Incorrect argument");
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super T> c) {
        Arrays.sort((T[]) data, 0, size, c);
    }

    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i])) {
                return true;
            }
        }
        return false;
    }

    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {
        private int cursor = 0;

        public boolean hasNext() {
            return size - cursor > 0;
        }

        @SuppressWarnings("unchecked")
        public T next() {
            if (this.hasNext()) {
                T current = (T) data[cursor];
                cursor++;
                return current;
            }
            throw new NoSuchElementException();
        }

        public void remove() {
            try {
                MyArrayList.this.remove(cursor - 1);
                cursor--;
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    }

    public Object[] toArray() {
        return this.stream().toArray();
    }

    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        a = (T1[]) data.clone();
        return a;
    }

    @SuppressWarnings("unchecked")
    private void grow(int tmpSize) {
        int oldSize = data.length;
        int newSize = (oldSize * 3) / 2 + 1;
        if (newSize < tmpSize) {
            newSize = tmpSize;
        }
        Object[] tmpArr = new Object[newSize];
        for (int i = 0; i < oldSize; i++) {
            tmpArr[i] = data[i];
        }
        data = new Object[newSize];
        for (int i = 0; i < oldSize; i++) {
            data[i] = tmpArr[i];
        }
    }

    public boolean add(T t) {
        if ((size + 1) > data.length) {
            this.grow(size + 1);
        }
        data[size++] = t;
        return false;
    }

    public boolean remove(Object o) {
        int index = indexOf(o);
        remove(index);
        throw new NoSuchElementException();
    }

    public boolean containsAll(Collection<?> c) {
        for (Object object : c) {
            if (!this.contains(object))
                return false;
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> c) {
        Object[] objects = c.toArray();
        int objLength = objects.length;
        this.grow(data.length + objLength);
        for (int i = size; i < data.length; i++) {
            data[i] = objects[data.length - objLength - 1];
        }
        return true;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        if ((index > size) || index < 0) {
            throw new IllegalArgumentException("Index can't be less than 0 or current MyArrayList instance size.");
        }
        Object[] objects = c.toArray();
        int objLength = objects.length;
        Object[] newArr = new Object[size + objLength];
        int cursor = 0;
        for (int i = 0; i < index; i++) {
            newArr[i] = data[i];
            cursor++;
        }
        for (Object object : objects) {
            newArr[cursor] = object;
            cursor++;
            size++;
        }
        for (int i = index; i < data.length - 1; i++) {
            newArr[cursor] = data[i];
            cursor++;
        }
        data = new Object[newArr.length];
        data = newArr.clone();
        return true;
    }

    public boolean removeAll(Collection<?> c) {
        throw new MethodNotImplementedException();
    }

    public boolean retainAll(Collection<?> c) {
        int index = 0;
        boolean flag = false;

        while (index < size) {
            if (!c.contains(data[index])) {
                this.remove(index);
                flag = true;
            } else {
                index++;
            }
        }
        return flag;
    }

    public void clear() {
        this.forEach(t -> t = null);
        data = new Object[DEF_SIZE];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T) data[index];
    }

    @SuppressWarnings("unchecked")
    public T set(int index, T element) {
        T tmp = (T) data[index];
        data[index] = element;

        return tmp;
    }

    public void add(int index, T element) {
        if ((index > size) || index < 0) {
            throw new IllegalArgumentException("Index can't be less than 0 or current MyArrayList instance size.");
        }
        Object[] tmpArr = new Object[size + 1];
        int cursor = 0;
        for (int i = 0; i < index; i++) {
            tmpArr[i] = data[i];
            cursor++;
        }
        tmpArr[index] = element;
        cursor++;
        size++;
        for (int i = index; i < data.length - 1; i++) {
            tmpArr[cursor] = data[i];
            cursor++;
        }
        data = new Object[tmpArr.length];
        data = tmpArr.clone();
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        @SuppressWarnings("unchecked")
        T removedElement = (T) data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[size--] = null;
        this.trunk();

        return removedElement;
    }

    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (get(i).equals(o)) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (data[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    public ListIterator<T> listIterator() {
        return new ListIterator<T>() {

            int index;
            int lastReturn = -1;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                if (index >= size) {
                    throw new NoSuchElementException();
                }

                @SuppressWarnings("unchecked")
                T element = (T) data[index];
                lastReturn = index;
                index++;
                return element;
            }

            @Override
            public boolean hasPrevious() {
                return index >= 0;
            }

            @Override
            public T previous() {
                if (index < 0) {
                    throw new NoSuchElementException();
                }

                @SuppressWarnings("unchecked")
                T element = (T) data[index];
                lastReturn = index;
                index--;
                return element;
            }

            @Override
            public int nextIndex() {
                return index;
            }

            @Override
            public int previousIndex() {
                return index - 1;
            }

            @Override
            public void remove() {
                MyArrayList.this.remove(index);
                index = lastReturn;
                lastReturn = -1;
            }

            @Override
            public void set(T t) {
                MyArrayList.this.set(lastReturn, t);
            }

            @Override
            public void add(T t) {
                MyArrayList.this.add(index, t);
                index++;
                lastReturn = -1;
            }
        };
    }

    public ListIterator<T> listIterator(int index) {
        throw new MethodNotImplementedException();
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new MethodNotImplementedException();
    }

    @SuppressWarnings("unchecked")
    private void trunk() {
        if (size < data.length) {
            if (size == 0) {
                data = new Object[DEF_SIZE];
            } else {
                T[] tmpArr = (T[]) new Object[size];
                for (int i = 0; i < size; i++) {
                    tmpArr[i] = (T) data[i];
                }
                data = new Object[size];
                data = tmpArr.clone();
            }
        }
    }
}
