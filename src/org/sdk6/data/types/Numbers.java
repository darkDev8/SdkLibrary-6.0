package org.sdk6.data.types;

public class Numbers {

	/**
	 * Generate a random number in range of numbers.
	 * 
	 * @param min Minimum value.
	 * @param max Maximum value.
	 * @return The generated number.
	 */
	public long generateRandomNumber(long min, long max) {
		return (long) ((Math.random() * (max - min)) + min);
	}

	/**
	 * Check if the number is perfect.
	 * 
	 * @param number The input number.
	 * @return True if perfect and false if not.
	 */
	public boolean isPerfect(double number) {
		double sum = 1;

		for (int i = 2; i * i <= number; i++) {
			if (number % i == 0) {
				if (i * i != number)
					sum = sum + i + number / i;
				else
					sum = sum + i;
			}
		}

		return sum == number && number != 1;
	}

	/**
	 * Check if number is even or odd.
	 * 
	 * @param number The input number.
	 * @return True if input is odd and false if not.
	 */
	public boolean isOdd(int number) {
		return number % 2 != 0;
	}

	/**
	 * Check if number is even or odd.
	 * 
	 * @param number The input number.
	 * @return True if input is even and false if not.
	 */
	public boolean isEven(int number) {
		return number % 2 == 0;
	}

	/**
	 * Check if the number is prime.
	 * 
	 * @param number The input number.
	 * @return True if prime and false if not.
	 */
	public boolean isPrime(double number) {
		if (number <= 1) {
			return false;
		} else if (number == 2) {
			return true;
		} else if (number % 2 == 0) {
			return false;
		}

		for (int i = 3; i <= Math.sqrt(number); i += 2) {
			if (number % i == 0)
				return false;
		}

		return true;
	}
}
