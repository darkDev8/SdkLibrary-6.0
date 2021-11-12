package org.sdk6.data.structures;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.sdk6.data.types.Strings;

import java.util.Comparator;

public class StringList implements Iterable<String>, Serializable {
	private ArrayList<String> list;
	private boolean duplicates;

	private static final long serialVersionUID = 1L;
	private int index; // to iterate over list.

	/**
	 * Constructor of String list, default duplicates are allowed.
	 */
	public StringList() {
		duplicates = true;
		list = new ArrayList<>();
		index = 0;
	}

	/**
	 * Constructor of String list.
	 * 
	 * @param duplicates Allow or not duplicate items to save in the list.
	 */
	public StringList(boolean duplicates) {
		this.duplicates = duplicates;
		list = new ArrayList<>();
		index = 0;
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
			list = new ArrayList<>(new HashSet<>(list));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String get(int index) {
		return list.get(index);
	}

	public void set(String value, int index) {
		list.set(index, value);
	}

	public String[] toArray() {
		return list.toArray(new String[0]);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

	public void clear() {
		list.clear();
	}

	public int size() {
		return list.size();
	}

	public boolean contains(String value) {
		return list.contains(value);
	}

	/**
	 * Add new value to the list.
	 * 
	 * @param value New String value to add.
	 * @return The current list object.
	 */
	public StringList add(Object value) {
		list.add(value.toString());
		if (!duplicates) {
			eraseDuplicates();
		}

		return this;
	}

	/**
	 * Add new String array to the list.
	 * 
	 * @param input The array want to add to the list.
	 * @return The current list object.
	 */
	public StringList add(String[] input) {
		list.addAll(Arrays.asList(input));
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
	public StringList addAll(StringList list) {
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
	 * @param key The element value want to remove.
	 * @return True if remove was successful and false if not.
	 */
	public boolean remove(String key) {
		return list.remove(key);
	}

	/**
	 * Sort list in ascending order.
	 */
	public void sort() {
		Collections.sort(list);
	}

	/**
	 * Get the element position by value.
	 * 
	 * @param key The element want to search in list and get it's position.
	 * @return The element position.
	 */
	public int indexOf(String key) {
		return list.indexOf(key);
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
				sb.append(list.get(i));

				if (!Objects.isNull(separator)) {
					if ((i + 1) < list.size()) {
						sb.append(separator);
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
	 * @param path  The file path.
	 * @param erase Erase current list elements before adding or not.
	 * @return True if read was successful and false if not.
	 */
	public boolean readFromFile(String path, boolean erase) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(path));

			if (!lines.isEmpty()) {
				if (erase) {
					list.clear();
				}
			}

			lines.forEach(line -> {
				add(line);
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
				sb.append(list.get(i));

				if (!Objects.isNull(separator)) {
					if ((i + 1) < list.size()) {
						sb.append(separator);
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
	public String getBiggest() {
		return list.stream().max(Comparator.comparingInt(String::length)).get();
	}

	/**
	 * Get smallest element from the list.
	 * 
	 * @return The smallest element in the list.
	 */
	public String getSmallest() {
		return list.stream().min(Comparator.comparingInt(String::length)).get();
	}

	/**
	 * Get biggest element position from the list.
	 * 
	 * @return The biggest element position in the list.
	 */
	public int getBiggestIndex() {
		return list.indexOf(list.stream().max(Comparator.comparingInt(String::length)).get());
	}

	/**
	 * Get smallest element position from the list.
	 * 
	 * @return The smallest element position in the list.
	 */
	public int getSmallestIndex() {
		return list.indexOf(list.stream().min(Comparator.comparingInt(String::length)).get());
	}

	/**
	 * Convert all list elements to lower case.
	 */
	public void toLowerCase() {
		for (int i = 0; i < list.size(); i++) {
			list.set(i, list.get(i).toLowerCase());
		}
	}

	/**
	 * Convert all list elements to upper case.
	 */
	public void toUpperCase() {
		for (int i = 0; i < list.size(); i++) {
			list.set(i, list.get(i).toUpperCase());
		}
	}

	/**
	 * Concat all list elements with a String.
	 * 
	 * @param value The input value want to concat.
	 */
	public void concat(String value) {
		if (new Strings().isNullOrEmpty(value)) {
			return;
		}

		for (int i = 0; i < list.size(); i++) {
			list.set(i, list.get(i).concat(value));
		}
	}

	/**
	 * Concat a list element with a String.
	 * 
	 * @param value The input value want to concat.
	 * @param index The specific position in the list.
	 * @return The smallest element position in the list.
	 */
	public void concat(String value, int index) {
		if (new Strings().isNullOrEmpty(value)) {
			return;
		}

		list.set(index, list.get(index).concat(value));
	}

	/**
	 * Get the length of an element in the list.
	 * 
	 * @param index The specific position of element.
	 */
	public int getLength(int index) {
		return list.get(index).length();
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
	 * Convert all list elements to capital.
	 */
	public void capitalize() {
		for (int i = 0; i < list.size(); i++) {
			list.set(i, new Strings().capitalize(list.get(i)));
		}
	}

	/**
	 * Check this list with another StringList to see if their items are equal.
	 * 
	 * @param obj The object must be a StringList class.
	 * @return True if they are equal and false if not.
	 */
	@Override
	public boolean equals(Object obj) {
		if (Objects.isNull(obj)) {
			return false;
		}

		if (obj.getClass() != getClass()) {
			return false;
		}

		String[] firstList = ((StringList) obj).toArray(), secondList = toArray();

		Arrays.sort(firstList);
		Arrays.sort(secondList);

		return Arrays.equals(firstList, secondList);
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
	public Iterator<String> iterator() {
		return new Iterator<String>() {
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
			@Override
			public String next() {
				return list.get(index++);
			}
		};
	}

	public boolean isDuplicates() {
		return duplicates;
	}

	public void setDuplicates(boolean duplicates) {
		this.duplicates = duplicates;
	}

	public ArrayList<String> getList() {
		return list;
	}
}
