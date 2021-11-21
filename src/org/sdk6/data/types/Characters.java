package org.sdk6.data.types;

import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;

public class Characters {

	/**
	 * Get an empty char array.
	 * 
	 * @return The array.
	 */
	public char[] getEmptyArray() {
		return new char[] {};
	}

	/**
	 * Convert a character to lowerCase.
	 * 
	 * @param input The input character.
	 * @return The converted character.
	 */
	public char toLowerCase(char input) {
		return String.valueOf(input).toLowerCase().toCharArray()[0];
	}

	/**
	 * Convert a character to upperCase.
	 * 
	 * @param input The input character.
	 * @return The converted character.
	 */
	public char toUpperCase(char input) {
		return String.valueOf(input).toUpperCase().toCharArray()[0];
	}

	/**
	 * Reverse a character array.
	 * 
	 * @param input The input array.
	 * @return The reversed array.
	 */
	public char[] reverse(char[] input) {
		Character[] characters = ArrayUtils.toObject(input);

		Collections.reverse(Arrays.asList(characters));
		return ArrayUtils.toPrimitive(characters);
	}

	/**
	 * Reverse a character array.
	 * 
	 * @param input The input array.
	 * @return The reversed array.
	 */
	public char[] reverse(Character[] input) {
		Collections.reverse(Arrays.asList(input));
		return ArrayUtils.toPrimitive(input);
	}

	/**
	 * Convert a character array to single String object separated by user input.
	 * 
	 * @param array Character array.
	 * @param split Separate each array element and split into it.
	 * @return The converted String.
	 */
	public String arrayToString(char[] array, String split) {
		if (Objects.isNull(array)) {
			return new String();
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);

			if (!Objects.isNull(split)) {
				if (i + 1 < array.length) {
					sb.append(split);
				}
			}
		}

		return sb.toString();
	}

	/**
	 * Replace a specific character in the input array.
	 * 
	 * @param chars   The input array.
	 * @param oldChar Old character.
	 * @param newChar New character to replace.
	 * @return The changed array in the process.
	 */
	public char[] replaceCharacters(char[] chars, char oldChar, char newChar) {
		StringBuilder sb = new StringBuilder();

		for (char ch : chars) {
			if (ch == oldChar) {
				sb.append(newChar);
			} else {
				sb.append(ch);
			}
		}

		return sb.toString().toCharArray();
	}

	/**
	 * Get a single character ASCII code.
	 * 
	 * @param character The specific character.
	 * @return The character ASCII code.
	 */
	public int getCharacterAsciiCode(char character) {
		return (int) character;
	}
}
