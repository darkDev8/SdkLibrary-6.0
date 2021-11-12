package org.sdk6.data.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

import org.sdk6.data.structures.StringList;
import org.sdk6.tools.ExternalTools;

public class Strings {
	/**
	 * Check the String to see if it is not or has empty value or not.
	 * 
	 * @param input The input String.
	 * @return True if it is null and false if not.
	 */
	public boolean isNullOrEmpty(String input) {
		if (Objects.isNull(input)) {
			return true;
		}

		if (input.isEmpty()) {
			return true;
		}

		return false;
	}

	/**
	 * Check the String array to see if it is not or has empty value or not.
	 * 
	 * @param input The input array String.
	 * @return True if it is null and false if not.
	 */
	public boolean isNullOrEmpty(String[] input) {
		if (Objects.isNull(input)) {
			return true;
		}

		if (input.length == 0) {
			return true;
		}

		return false;
	}

	/**
	 * Get an empty String array.
	 * 
	 * @return Empty array.
	 */
	public String[] getEmptyArray() {
		return new String[] {};
	}

	/**
	 * Get an empty String.
	 * 
	 * @return Empty String.
	 */
	public String getEmptyString() {
		return new String();
	}

	/**
	 * Search a String and replace old String with new String.
	 * 
	 * @param input The input String.
	 * @param oldW  Old String.
	 * @param newW  New String.
	 * @return The new changed String.
	 */
	public String replace(String input, String oldW, String newW) {
		if (isNullOrEmpty(input)) {
			return getEmptyString();
		}

		return input.replaceAll(oldW, newW);
	}

	/**
	 * Count matches String in the input String.
	 * 
	 * @param input     The input String.
	 * @param key       The key you want to search and count matches.
	 * @param separator The separator of items.
	 * @param match     If true search exact string and false search substring
	 *                  string in the input.
	 * @return Number of find matches.
	 */
	public int countMatches(String input, String key, String separator, boolean match) {
		if (isNullOrEmpty(input)) {
			return -1;
		}

		if (match) {
			String[] array = input.split(separator);
			int count = 0;

			for (String s : array) {
				if (s.equals(key)) {
					count++;
				}
			}

			return count;
		}

		return StringUtils.countMatches(input, key);
	}

	/**
	 * Reverse input String
	 * 
	 * @param input The input String.
	 * @return The reversed String.
	 */
	public String reverse(String input) {
		if (isNullOrEmpty(input)) {
			return getEmptyString();
		}

		return new StringBuilder(input).reverse().toString();
	}

	/**
	 * Reverse an String array.
	 * 
	 * @param input The input array String.
	 * @return The reversed String array.
	 */
	public String[] reverse(String[] input) {
		List<String> list = new ArrayList<>(Arrays.asList(input));
		Collections.reverse(list);

		return list.toArray(new String[0]);
	}

	/**
	 * Clears a StringBuilder object.
	 * 
	 * @param sb The input StringBuilder.
	 */
	public void clearStringBuilder(StringBuilder sb) {
		sb.setLength(0);
	}

	/**
	 * Check if String contains only numbers or not.
	 * 
	 * @param input The input String.
	 * @return True if it's all number and false if not.
	 */
	public boolean isNumber(String input) {
		try {
			Double.parseDouble(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Check if String contains only text or not.
	 * 
	 * @param input The input String.
	 * @return True if it's all text and false if not.
	 */
	public boolean isText(String input) {
		char[] array = input.toCharArray();

		for (char item : array) {
			if (Character.isDigit(item)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Convert String array to a single String.
	 * 
	 * @param array Input String array.
	 * @param split The separator to split the array elements.
	 * @return New String from array.
	 */
	public String arrayToString(String[] array, String split) {
		if (Objects.isNull(array)) {
			return getEmptyString();
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);

			if (!Objects.isNull(split)) {
				sb.append(split);

				if (i + 1 < array.length) {
					sb.append(split);
				}
			}
		}

		return sb.toString();
	}

	/**
	 * Convert String to StringBuilder.
	 * 
	 * @param input The input String.
	 * @return The new StringBuilder.
	 */
	public StringBuilder toStringBuilder(String input) {
		return new StringBuilder(input);
	}

	/**
	 * Convert String array to StringBuilder object.
	 * 
	 * @param array The String array.
	 * @param split The separator to split the array elements.
	 * @return The new StringBuilder.
	 */
	public StringBuilder arrayToStringBuilder(String[] array, String split) {
		return toStringBuilder(arrayToString(array, split));
	}

	/**
	 * Removes an element from String array.
	 * 
	 * @param input The input String array.
	 * @param index The specific position want to remove from array.
	 * @return The new array.
	 */
	public String[] removeElement(String[] input, int index) {
		if (index > input.length) {
			index = input.length;
		}

		List<String> items = ExternalTools.toArrayList(input);
		items.remove(index);

		return items.toArray(new String[0]);
	}

	/**
	 * Removes an element from String array.
	 * 
	 * @param input The input String array.
	 * @param key   Search the array and if found remove it from array.
	 * @return The new array.
	 */
	public String[] removeElement(String[] input, String key) {
		List<String> items = ExternalTools.toArrayList(input);
		items.remove(key);
		
		return items.toArray(new String[0]);
	}

	/**
	 * Splits a String and make an array.
	 * 
	 * @param input             The input String.
	 * @param length            The length for each element.
	 * @param removeWhiteSpaces If true will remove all white spaces in the String
	 *                          and false not.
	 * @return The new array.
	 */
	public String[] split(String input, int length, boolean removeWhiteSpaces) {
		if (removeWhiteSpaces) {
			input = input.replace(" ", getEmptyString());
		}

		return Iterables.toArray(Splitter.fixedLength(length).split(input), String.class);
	}

	/**
	 * Make a String first letter capital.
	 * 
	 * @param input The input String.
	 * @return The new String with capital letter.
	 */
	public String capitalize(String input) {
		if (isNullOrEmpty(input)) {
			return getEmptyString();
		}

		if (input.length() == 1) {
			return input.toUpperCase();
		}

		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}

	/**
	 * Count specific items in the text(words, characters, lines and spaces).
	 * @param input The input text from user.
	 * @param type The type of items want to search and count.
	 * @param disableSpaceCheck True if want to remove all spaces in the text before checking.
	 * @return The number of specific item.
	 */
	public String[] countItemsInText(String input, String type, boolean disableSpaceCheck) {
		try {
			StringList list = new StringList(false).add(split(type.trim().toLowerCase(), 1, true)),
					items = new StringList();

			for (String item : list) {
				switch (item) {
				case "w":
				case "c":
				case "l":
				case "s":
					break;

				default:
					return getEmptyArray();
				}
			}
			
			if (disableSpaceCheck) {
				input = input.replace(" ", getEmptyString());
			}

			if (list.contains("w")) {
				items.add(input.split("\\s+").length);
			}
			
			if (list.contains("c")) {
				items.add(input.toCharArray().length);
			}
			
			if (list.contains("l")) {
				items.add(countMatches(input, "\n", null, false));
			}
			
			if (list.contains("s")) {
				items.add(countMatches(input, " ", null, false));
			}

			return items.toArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
