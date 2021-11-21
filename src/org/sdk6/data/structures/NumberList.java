package org.sdk6.data.structures;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.sdk6.data.types.Numbers;
import org.sdk6.data.types.Strings;

public class NumberList<E extends Number> implements Iterable<E>, Serializable {

	private ArrayList<Number> list;
	private boolean duplicates;

	private static final long serialVersionUID = 1L;
	private int index; // to iterate over list.

	/**
	 * Constructor of number list, default duplicates are allowed.
	 */
	public NumberList() {
		duplicates = true;
		list = new ArrayList<>();
		index = 0;
	}

	/**
	 * Constructor of number list.
	 * 
	 * @param duplicates Allow or not duplicate items to save in the list.
	 */
	public NumberList(boolean duplicates) {
		this.duplicates = duplicates;
		list = new ArrayList<>();
	}

	/**
	 * Get an element by it's position in list.
	 * 
	 * @param index Index of element in the list.
	 * @return The element in the position.
	 */
	public Number get(int index) {
		return list.get(index);
	}

	/**
	 * Change element value in the specified position.
	 * 
	 * @param value New value for the element.
	 * @param index Index of element in the list.
	 */
	public void set(Number value, int index) {
		list.set(index, value);
	}

	/**
	 * Converts the list to a numbers array.
	 * 
	 * @return The converted list to array.
	 */
	public Number[] toArray() {
		return list.toArray(new Number[0]);
	}

	/**
	 * Check the list if is empty or not.
	 * 
	 * @return True if is empty and false if not.
	 */
	public boolean isEmpty() {
		return list.isEmpty();
	}

	/**
	 * Clears entire list.
	 */
	public void clear() {
		list.clear();
	}

	/**
	 * Get the number of list elements.
	 * 
	 * @return The list size.
	 */
	public int size() {
		return list.size();
	}

	/**
	 * Check the list for specific value.
	 * 
	 * @param value The element want to search in list.
	 * @return True if exists and false if not.
	 */
	public boolean contains(Number value) {
		return list.contains(value);
	}

	/**
	 * Count duplicate elements in the list by key.
	 * 
	 * @param key The key want to search in the list.
	 * @return Number of duplicate elements.
	 */
	public int countDuplicates(String key) {
		return Collections.frequency(list, key);
	}

	/**
	 * Erase duplicate items in the list.
	 * 
	 * @return True if clear was successful and false if not.
	 */
	public boolean eraseDuplicates() {
		try {
			list = new ArrayList<>(new HashSet<>(list)); // hash set object will remove duplicates
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Add new number to the list.
	 * 
	 * @param value New number value to add.
	 * @return The current list object.
	 */
	public NumberList<E> add(Number value) {
		list.add(value);
		if (!duplicates) {
			eraseDuplicates();
		}

		return this;
	}

	/**
	 * Add new number array to the list.
	 * 
	 * @param numbers The array want to add to the list.
	 * @return The current list object.
	 */
	public NumberList<E> add(Number[] numbers) {
		list.addAll(Arrays.asList(numbers));
		if (!duplicates) {
			eraseDuplicates();
		}

		return this;
	}

	/**
	 * Add another list to current list with all it's elements.
	 * 
	 * @param list The second list want to add to this list.
	 * @return The current list object.
	 */
	public NumberList<E> addAll(NumberList<E> list) {
		this.list.addAll(Arrays.asList(list.toArray()));
		if (!duplicates) {
			eraseDuplicates();
		}

		return this;
	}

	/**
	 * Remove an element from list by it's position.
	 * 
	 * @param index The element position.
	 * @return True if remove was successful and false if not.
	 */
	public boolean remove(int index) {
		try {
			list.remove(index);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Remove an element from list by element value.
	 * 
	 * @param value The element value want to remove.
	 * @return True if remove was successful and false if not.
	 */
	public boolean remove(Number value) {
		return list.remove(value);
	}

	/**
	 * Sort list in ascending order.
	 */
	public void sort() {
		list.sort(Collections.reverseOrder());
		Collections.reverse(list);
	}

	/**
	 * Get the element position by value.
	 * 
	 * @param value The element want to search in list and get it's position.
	 * @return The element position.
	 */
	public int indexOf(Number value) {
		return list.indexOf(value);
	}

	/**
	 * Sum all elements in the list.
	 * 
	 * @return Sum of all elements calculated from list.
	 */
	public double sum() {
		double sum = 0;

		for (Number number : list) {
			sum += Double.parseDouble(String.valueOf(number));
		}

		return sum;
	}

	/**
	 * Get only even numbers from the list.
	 * 
	 * @return Even numbers in the list.
	 */
	public Number[] getEvens() {
		List<Number> evens = new ArrayList<>();
		list.forEach(number -> {
			if (number.longValue() % 2 == 0) { // check if element is even % 2
				evens.add(number);
			}
		});

		return evens.toArray(new Number[0]);
	}

	/**
	 * Get only odd numbers from the list.
	 * 
	 * @return Odd numbers in the list.
	 */
	public Number[] getOdds() {
		List<Number> odds = new ArrayList<>();
		list.forEach(number -> {
			if (number.longValue() % 2 != 0) { // check if element is even % 2
				odds.add(number);
			}
		});

		return odds.toArray(new Number[0]);
	}

	/**
	 * Get only prime numbers from the list.
	 * 
	 * @return Prime numbers in the list.
	 */
	public Number[] getPrimes() {
		List<Number> primes = new ArrayList<>();

		for (Number number : list) {
			if (new Numbers().isPrime(number.doubleValue())) {
				primes.add(number);
			}
		}

		return primes.toArray(new Number[0]);
	}

	/**
	 * Get only perfects numbers from the list.
	 * 
	 * @return Perfect numbers in the list
	 */
	public Number[] getPerfects() {
		List<Number> perfects = new ArrayList<>();

		for (Number number : list) {
			if (new Numbers().isPerfect(number.doubleValue())) {
				perfects.add(number);
			}
		}

		return perfects.toArray(new Number[0]);
	}

	/**
	 * Power all list numbers to specific number.
	 * 
	 * @param value The specific value to power all list elements.
	 */
	public void pow(double value) {
		for (int i = 0; i < list.size(); i++) {
			list.set(i, Math.pow(list.get(i).doubleValue(), value));
		}
	}

	/**
	 * Sum all list numbers to specific number.
	 * 
	 * @param value The specific value to sum all list elements.
	 */
	public void sum(double value) {
		for (int i = 0; i < list.size(); i++) {
			list.set(i, list.get(i).doubleValue() + 1);
		}
	}

	/**
	 * Reverse all list elements.
	 */
	public void reverse() {
		Collections.reverse(list);
	}

	/**
	 * Write all list elements to a file in hard disk.
	 * 
	 * @param path      The file path.
	 * @param separator The separator to separate elements in the file.
	 * @return True if write was successful and false if not.
	 */
	public boolean writeToFile(String path, String separator) {
		try {
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i)); // append all list elements to string builder to write.

				if (!Objects.isNull(separator)) {
					if ((i + 1) < list.size()) {
						sb.append(separator); // add separator to each element.
					}
				}
			}

			Files.write(Paths.get(path), sb.toString().getBytes());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Read from file in hard disk and add elements to the list.
	 * 
	 * @param path        The file path.
	 * @param erase       Erase current list elements before adding or not.
	 * @param validNumber Reads only valid numbers or not,false may cause
	 *                    NumberFormatException.
	 * @return True if read was successful and false if not.
	 */
	public boolean readFromFile(String path, boolean erase, boolean validNumber) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(path));

			if (!lines.isEmpty()) {
				if (erase) {
					list.clear();
				}
			}

			lines.forEach(line -> {
				if (validNumber) {
					if (new Strings().isNumber(line)) {
						add(Double.parseDouble(line));
					}
				} else {
					add(Double.parseDouble(line));
				}
			});

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Append all list elements to a file in hard disk.
	 * 
	 * @param path      The file path.
	 * @param separator The separator to separate elements in the file.
	 * @return True if append was successful and false if not.
	 */
	public boolean appendToFile(String path, String separator) {
		try {
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < list.size(); i++) {
				sb.append(list.get(i)); // append all list elements to string builder to write.

				if (!Objects.isNull(separator)) {
					if ((i + 1) < list.size()) {
						sb.append(separator); // add separator to each element.
					}
				}
			}

			Files.write(Paths.get(path), sb.toString().getBytes(), StandardOpenOption.APPEND);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Get biggest element from the list.
	 * 
	 * @return The biggest element in the list.
	 */
	public Number getBiggest() {
		return list.stream().max(Comparator.comparingDouble(Number::doubleValue)).get();
	}

	/**
	 * Get smallest element from the list.
	 * 
	 * @return The smallest element in the list.
	 */
	public Number getSmallest() {
		return list.stream().min(Comparator.comparingDouble(Number::doubleValue)).get();
	}

	/**
	 * Get biggest element position from the list.
	 * 
	 * @return The biggest element position in the list.
	 */
	public int getBiggestIndex() {
		return list.indexOf(list.stream().max(Comparator.comparingDouble(Number::doubleValue)).get());
	}

	/**
	 * Get smallest element position from the list.
	 * 
	 * @return The smallest element position in the list.
	 */
	public int getSmallestIndex() {
		return list.indexOf(list.stream().min(Comparator.comparingDouble(Number::doubleValue)).get());
	}

	/**
	 * Get specific number in range of numbers.
	 * 
	 * @param start From this number.
	 * @param end   To this number.
	 * @param sort  Sort list of items.
	 * @return The list of items in specific range.
	 */
	public Number[] inRangeOf(Number start, Number end, boolean sort) {
		List<Number> numbers = new ArrayList<>();

		for (Number number : list) {
			if (number.doubleValue() >= start.doubleValue() && number.doubleValue() <= end.doubleValue()) {
				numbers.add(number); // check the numbers range.
			}
		}

		if (sort) {
			numbers.sort(Collections.reverseOrder());
			Collections.reverse(numbers);
		}

		return numbers.toArray(new Number[0]); // convert list to array.
	}

	/**
	 * Print all list elements in console window.
	 * 
	 * @param start  The start string for each element.
	 * @param number Enable or disable numbers to show number in start of each
	 *               element.
	 */
	public void printList(String start, boolean number) {
		for (int i = 0; i < list.size(); i++) {
			if (!new Strings().isNullOrEmpty(start)) {
				System.out.print(start);
			}

			if (number) {
				System.out.print("[" + (i + 1) + "] ");
			}

			System.out.println(list.get(i));
		}
	}

	/**
	 * Check this list with another NumberList to see if their items are equal.
	 * 
	 * @param obj The object must be a NumberList class.
	 * @return True if they are equal and false if not.
	 */
	@Override
	public boolean equals(Object obj) {
		if (Objects.isNull(obj)) { // check if object is null.
			return false;
		}

		if (obj.getClass() != getClass()) { // check if object is in the same class.
			return false;
		}

		List<Number> firstList = Arrays.asList(((NumberList<?>) obj).toArray()),
				secondList = Arrays.asList(this.toArray());

		if (firstList.size() != secondList.size()) { // check current list size to input list size.
			return false;
		}

		for (Number n : firstList) { // check if both list has same elements.
			if (!secondList.contains(n)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Convert all list elements to single string separated by "," at the end of
	 * each element.
	 * 
	 * @return The converted String object.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");

		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i));

			if ((i + 1) < list.size()) {
				sb.append(",");
			}
		}

		return sb.append("]").toString();
	}

	/**
	 * Return the object hashCode from JVM.
	 * 
	 * @return The hash number.
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * Enable list iteration through each element.
	 * 
	 * @return The iterator access to move from each element to next.
	 */
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			/**
			 * Check to see if this iterator has next element or not.
			 * 
			 * @return True if they it has and false if not.
			 */
			@Override
			public boolean hasNext() {
				return index < list.size();
			}

			/**
			 * Get the next element from iterator.
			 * 
			 * @return The specific element from iterator.
			 */
			@SuppressWarnings("unchecked")
			@Override
			public E next() {
				return (E) list.get(index++);
			}
		};
	}
}
