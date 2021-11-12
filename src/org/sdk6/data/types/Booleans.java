package org.sdk6.data.types;

public class Booleans {

	/**
	 * Convert a boolean to integer value, 1 for true and 0 for false.
	 * 
	 * @param flag The boolean value.
	 * @return The integer value.
	 */
	public int booleanToNumber(boolean flag) {
		return flag ? 1 : 0;
	}

	/**
	 * Convert a boolean array to integer array, 1 for true and 0 for false.
	 * 
	 * @param flag The boolean array.
	 * @return The integer array.
	 */
	public int[] booleansToNumbers(boolean[] array) {
		int[] numbers = new int[array.length];

		for (int i = 0; i < array.length; i++) {
			numbers[i] = array[i] ? 1 : 0;
		}

		return numbers;
	}
}
