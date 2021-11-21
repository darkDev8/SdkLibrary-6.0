package org.sdk6.tools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;
import java.util.Scanner;

import org.sdk6.data.types.Strings;

public class Console {
	private int time;
	private int line;
	private boolean animation;

	/**
	 * Constructor of Console class.
	 * 
	 * @param time      The animation time.
	 * @param line      The lines for screen clearing.
	 * @param animation Enable or disable animation.
	 */
	public Console(int time, int line, boolean animation) {
		this.time = time;
		this.line = line;
		this.animation = animation;
	}

	/**
	 * Constructor of Console class.
	 */
	public Console() {
		this.time = 1;
		this.line = 20;
		this.animation = false;
	}

	/**
	 * Get an exception stack trace.
	 * 
	 * @param e The input exception.
	 * @return The get stack trace.
	 */
	private String getPrintStackTrace(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);

		return sw.toString();
	}

	/**
	 * Print a single empty String.
	 */
	private void printLine() {
		if (this.animation) {
			this.print("\n", false);
		} else {
			System.out.println();
		}
	}

	/**
	 * Print text to screen with animation.
	 * 
	 * @param text     The input text to print.
	 * @param nextLine Detect to go to next line or not.
	 */
	private void printLine(String text, boolean nextLine) {
		if (this.animation) {
			this.print(text, nextLine);
		} else if (nextLine) {
			System.out.println(text);
		} else {
			System.out.print(text);
		}

	}

	/**
	 * Sleep current thread for a period time.
	 * 
	 * @param time The time want to sleep this thread.
	 */
	public void sleep(int time) {
		try {
			Thread.sleep((long) time);
		} catch (InterruptedException var3) {
			var3.printStackTrace();
		}

	}

	/**
	 * Sleep current thread for a period time.
	 * 
	 */
	public void sleep() {
		sleep(time);
	}

	/**
	 * Print couple characters to the console.
	 * 
	 * @param c        The specified character want to print.
	 * @param length   Number of times want to print this character.
	 * @param nextLine Detect to go to next line or not.
	 */
	public void printCharacters(char c, int length, boolean nextLine) {
		for (int i = 0; i < length; ++i) {
			System.out.print(c);
		}

		if (nextLine) {
			this.printLine();
		}

	}

	/**
	 * Clears the console screen with default lines.
	 */
	public void clear() {
		for (int i = 0; i < this.line; ++i) {
			this.printLine();
		}

	}

	/**
	 * Clears the console screen.
	 * 
	 * @param line The number of lines to clear.
	 */
	public void clear(int line) {
		for (int i = 0; i < line; ++i) {
			this.printLine();
		}

	}

	/**
	 * Show message and wait for user to press enter to continue.
	 * 
	 * @param before Go to next line before show message.
	 * @param after  Go to next line after show message.
	 */
	@SuppressWarnings("resource")
	public void pressEnter(boolean before, boolean after) {
		if (before) {
			this.printLine();
		}

		this.printLine("Press enter to continue!...", true);
		(new Scanner(System.in)).nextLine();
		if (after) {
			this.printLine();
		}

	}

	/**
	 * Print a message to console screen and ask user to answer yes or no.
	 * 
	 * @param message The message for the user.
	 * @param repeat  Enable or disable repeat if user answered wrong option.
	 * 
	 * @return The index of answered option.
	 */
	public int ask(String message, boolean repeat) {
		return this.ask(message, "Y", "N", repeat);
	}

	/**
	 * Print a message to console screen and ask user with options.
	 * 
	 * @param message The message for the user.
	 * @param first   The first option.
	 * @param second  The second option.
	 * @param repeat  Enable or disable repeat if user answered wrong option.
	 * 
	 * @return The index of answered option.
	 */
	public int ask(String message, String first, String second, boolean repeat) {
		String answer = null;

		do {
			this.printLine(message.concat("? [" + first + "/" + second + "]: "), false);
			answer = this.getInput(1, true);
			if (answer.equals((new String(first)).toLowerCase())) {
				return 1;
			}

			if (answer.equals((new String(second)).toLowerCase())) {
				return 0;
			}
		} while (repeat);

		return -1;
	}

	/**
	 * Print a message to console screen and ask user to answer options.
	 * 
	 * @param message The message for the user.
	 * @param options User options.
	 * @param repeat  Enable or disable repeat if user answered wrong option.
	 * 
	 * @return The index of answered option.
	 */
	public int ask(String message, String[] options, boolean repeat) {
		if (Objects.isNull(options)) {
			return -1;
		} else {
			do {
				String msg = message + " [";

				for (int i = 0; i < options.length; ++i) {
					msg = msg + options[i];
					if (i + 1 < options.length) {
						msg = msg + "/";
					}
				}

				msg = msg + "]: ";
				this.printLine(msg, false);
				String answer = this.getInput(1, true);

				for (int i = 0; i < options.length; i++) {
					options[i] = options[i].toLowerCase();
				}

				for (int i = 0; i < options.length; i++) {
					if (options[i].equals(answer)) {
						return i;
					}
				}
			} while (repeat);

			return -1;
		}
	}

	/**
	 * Print a message to console screen.
	 * 
	 * @param input    The input text to print to the screen.
	 * @param nextLine Go to next line after print finished.
	 * @param time     The animation time.
	 */
	public void print(String input, boolean nextLine, int time) {
		if (!input.isEmpty()) {
			char[] array = input.toCharArray();
			char[] var5 = array;
			int var6 = array.length;

			for (int var7 = 0; var7 < var6; ++var7) {
				char c = var5[var7];
				if (this.animation) {
					this.sleep(time);
				}

				System.out.print(c);
			}

			if (nextLine) {
				this.printLine();
			}

		}
	}

	/**
	 * Print a message to console screen with default animation time.
	 * 
	 * @param input    The input text to print to the screen.
	 * @param nextLine Go to next line after print finished.
	 */
	public void print(String input, boolean nextLine) {
		this.print(input, nextLine, this.time);
	}

	/**
	 * Print a String array to console screen with animation.
	 * 
	 * @param input    The input array to print.
	 * @param nextLine Go to next line after print finished.
	 * @param time     The animation time.
	 */
	public void printLines(String[] input, boolean nextLine, int time) {
		if (!Objects.isNull(input)) {
			String[] var4 = input;
			int var5 = input.length;

			for (int var6 = 0; var6 < var5; ++var6) {
				String s = var4[var6];
				if (this.animation) {
					this.sleep(time);
				}

				System.out.print(s);
			}

			if (nextLine) {
				this.printLine();
			}

		}
	}

	/**
	 * Print a String array to console screen with animation.
	 * 
	 * @param input    The input array to print.
	 * @param nextLine Go to next line after print finished.
	 */
	public void printLines(String[] input, boolean nextLine) {
		this.printLines(input, nextLine, this.time);
	}

	/**
	 * Get input String from user.
	 * 
	 * @param mode 1 for lower case, 2 for upper case and other for nothing.
	 * @param trim Cut spaces after it gets input from user.
	 * @return The input got from user.
	 */
	public String getInput(int mode, boolean trim) {
		return getInput("", mode, trim);
	}

	/**
	 * Get input String from user.
	 * 
	 * @param mode   1 for lower case, 2 for upper case and other for nothing.
	 * @param trim   Cut spaces after it gets input from user.
	 * @param length Cut input to specific length.
	 * @return The input got from user.
	 */
	public String getInput(int mode, boolean trim, int length) {
		return getInput("", mode, trim, length);
	}

	/**
	 * Get input String from user.
	 * 
	 * @param text Print a text to console before getting input.
	 * @param mode 1 for lower case, 2 for upper case and other for nothing.
	 * @param trim Cut spaces after it gets input from user.
	 * @return The input got from user.
	 */
	public String getInput(String text, int mode, boolean trim) {
		print(text, false);
		@SuppressWarnings("resource")
		String input = (new Scanner(System.in)).nextLine();
		switch (mode) {
		case 1:
			input = input.toLowerCase();
			break;
		case 2:
			input = input.toUpperCase();
		}

		if (trim) {
			input = input.trim();
		}

		return input;
	}

	/**
	 * Get input String from user.
	 * 
	 * @param text   Print a text to console before getting input.
	 * @param mode   1 for lower case, 2 for upper case and other for nothing.
	 * @param trim   Cut spaces after it gets input from user.
	 * @param length Cut input to specific length.
	 * @return The input got from user.
	 */
	public String getInput(String text, int mode, boolean trim, int length) {
		int max = 0;
		String input = this.getInput(text, mode, trim);
		StringBuilder buildString = new StringBuilder();
		char[] in = input.toCharArray();
		if (in.length == 0) {
			return new String();
		} else {
			if (length > input.length()) {
				max = input.length();
			} else {
				max = length;
			}

			for (int i = 0; i < max; ++i) {
				buildString.append(in[i]);
			}

			return buildString.toString();
		}
	}

	/**
	 * Print a left angled triangle to console screen.
	 * 
	 * @param lines  Number of maximum lines for this shape.
	 * @param number Enable or disable replacement of * with numbers(1,2,3,...).
	 */
	public void leftAngledTriangle(int lines, boolean number) {
		int tmp = 0;

		for (int i = 0; i <= lines; ++i) {
			for (int j = 0; j < i; ++j) {
				if (number) {
					if (tmp > 9) {
						tmp = 0;
					}

					this.printLine(String.valueOf(tmp++), false);
				} else {
					this.printLine("*", false);
				}
			}

			this.printLine();
		}

	}

	/**
	 * Print an isosceles triangle to console screen.
	 * 
	 * @param lines  Number of maximum lines for this shape.
	 * @param number Enable or disable replacement of * with numbers(1,2,3,...).
	 */
	public void isoscelesTriangle(int lines, boolean number) {
		int tmp = 0;

		for (int rowNumber = 1; rowNumber <= lines; ++rowNumber) {
			for (int j = 1; j <= lines - rowNumber; ++j) {
				this.printLine(" ", false);
			}

			for (int k = 1; k <= 2 * rowNumber - 1; ++k) {
				if (number) {
					if (tmp > 9) {
						tmp = 0;
					}

					this.printLine(String.valueOf(tmp++), false);
				} else {
					this.printLine("*", false);
				}
			}

			this.printLine();
		}

	}

	/**
	 * Print a right angled triangle to console screen.
	 * 
	 * @param lines  Number of maximum lines for this shape.
	 * @param number Enable or disable replacement of * with numbers(1,2,3,...).
	 */
	public void rightAngledTriangle(int lines, boolean number) {
		int tmp = 0;

		for (int i = 1; i <= lines; ++i) {
			int k;
			for (k = lines; k > i; --k) {
				this.printLine(" ", false);
			}

			for (k = 1; k <= i; ++k) {
				if (number) {
					if (tmp > 9) {
						tmp = 0;
					}

					this.printLine(String.valueOf(tmp++), false);
				} else {
					this.printLine("*", false);
				}
			}

			this.printLine();
		}

	}

	/**
	 * Print a reversed left angled triangle to console screen.
	 * 
	 * @param lines  Number of maximum lines for this shape.
	 * @param number Enable or disable replacement of * with numbers(1,2,3,...).
	 */
	public void reverseLeftAngledTriangle(int lines, boolean number) {
		int tmp = 0;

		for (int i = 1; i <= lines; ++i) {
			for (int j = lines - i; j >= 0; --j) {
				if (number) {
					if (tmp > 9) {
						tmp = 0;
					}

					this.printLine(String.valueOf(tmp++), false);
				} else {
					this.printLine("*", false);
				}
			}

			this.printLine();
		}

	}

	/**
	 * Print a diamond shape to console screen.
	 * 
	 * @param lines  Number of maximum lines for this shape.
	 * @param number Enable or disable replacement of * with numbers(1,2,3,...).
	 */
	public void diamond(int lines, boolean number) {
		int tmp = 0;

		int i;
		int j;
		for (i = 0; i < lines; ++i) {
			for (j = 0; j < lines - i - 1; ++j) {
				this.printLine(" ", false);
			}

			for (j = 0; j < 2 * i + 1; ++j) {
				if (number) {
					if (tmp > 9) {
						tmp = 0;
					}

					this.printLine(String.valueOf(tmp++), false);
				} else {
					this.printLine("*", false);
				}
			}

			this.printLine();
		}

		for (i = 0; i < lines - 1; ++i) {
			for (j = 0; j <= i; ++j) {
				this.printLine(" ", false);
			}

			for (j = 2 * lines - 2 * i - 3; j > 0; --j) {
				if (number) {
					if (tmp > 9) {
						tmp = 0;
					}

					this.printLine(String.valueOf(tmp++), false);
				} else {
					this.printLine("*", false);
				}
			}

			this.printLine();
		}

	}

	/**
	 * Print an empty square shape to console screen.
	 * 
	 * @param lines  Number of maximum lines for this shape.
	 * @param number Enable or disable replacement of * with numbers(1,2,3,...).
	 */
	public void squareEmpty(int lines) {
		int m = lines;

		for (int i = 1; i <= m; ++i) {
			for (int j = 1; j <= m; ++j) {
				if (i != 1 && i != m) {
					if (j != 1 && j != m) {
						this.printLine("   ", false);
					} else {
						this.printLine(" * ", false);
					}
				} else {
					this.printLine(" * ", false);
				}
			}

			this.printLine();
		}

	}

	/**
	 * Print a fill square shape to console screen.
	 * 
	 * @param lines  Number of maximum lines for this shape.
	 * @param number Enable or disable replacement of * with numbers(1,2,3,...).
	 */
	public void squareFill(int lines) {
		for (int i = 1; i <= lines; ++i) {
			for (int j = 1; j <= lines; ++j) {
				this.printLine(" * ", false);
			}

			this.printLine();
		}

	}

	/**
	 * Print a text to screen with red color.
	 * 
	 * @param text     The text to print to the screen.
	 * @param nextLine Go to next line after print finished.
	 */
	public void printError(String text, boolean nextLine) {
		this.printLine(ConsoleForeground.RED_BOLD + text + ConsoleForeground.RESET, nextLine);
	}

	/**
	 * Print a text to screen with green color.
	 * 
	 * @param text     The text to print to the screen.
	 * @param nextLine Go to next line after print finished.
	 */
	public void printSuccess(String text, boolean nextLine) {
		this.printLine(ConsoleForeground.GREEN_BOLD + text + ConsoleForeground.RESET, nextLine);
	}

	/**
	 * Print a text to screen with blue color.
	 * 
	 * @param text     The text to print to the screen.
	 * @param nextLine Go to next line after print finished.
	 */
	public void printInformation(String text, boolean nextLine) {
		this.printLine(ConsoleForeground.BLUE_BOLD + text + ConsoleForeground.RESET, nextLine);
	}

	/**
	 * Ask a question from user and print it with light blue color.
	 * 
	 * @param text   The text to print to the screen.
	 * @param repeat Repeat question if user input was incorrect or not.
	 */
	public int printQuestion(String text, boolean repeat) {
		return this.ask(ConsoleForeground.CYAN_BOLD + text, repeat);
	}

	/**
	 * Print a text to screen with yellow color.
	 * 
	 * @param text     The text to print to the screen.
	 * @param nextLine Go to next line after print finished.
	 */
	public void printWarning(String text, boolean nextLine) {
		this.printLine(ConsoleForeground.YELLOW_BOLD + text + ConsoleForeground.RESET, nextLine);
	}

	/**
	 * Print an exception stack trace to screen with red color.
	 * 
	 * @param e        Specified exception
	 * @param nextLine Go to next line after print finished.
	 */
	public void printExceptionStackTrace(Exception e, boolean nextLine) {
		this.printError(getPrintStackTrace(e), nextLine);
	}

	/**
	 * Print an exception message to screen with red color.
	 * 
	 * @param e        Specified exception
	 * @param nextLine Go to next line after print finished.
	 */
	public void printExceptionMessage(Exception e, boolean nextLine) {
		this.printError(e.getMessage(), nextLine);
	}

	/**
	 * Print list of items to console screen.
	 * 
	 * @param list     The input String array to print.
	 * @param start    The starter of each element of the array.
	 * @param number   Enable or disable numbers in the beginning.
	 * @param nextLine Go to next line after print finished.
	 */
	public void printList(String[] list, String start, boolean number, boolean nextLine) {
		if (!new Strings().isNullOrEmpty(list)) {
			for (int i = 0; i < list.length; i++) {
				if (!new Strings().isNullOrEmpty(start)) {
					this.printLine(start, false);
				}

				if (number) {
					this.printLine("[" + (i + 1) + "] ", false);
				}

				this.printLine(list[i], nextLine);
			}
		}
	}

	/**
	 * Get Console class animation time.
	 * 
	 * @return The animation time.
	 */
	public int getTime() {
		return this.time;
	}

	/**
	 * Set Console class animation time.
	 * 
	 * @param time The animation time.
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * Get Console class clear lines.
	 * 
	 * @return The clear lines.
	 */
	public int getLine() {
		return this.line;
	}

	/**
	 * Set Console class clear lines.
	 * 
	 * @param line The clear lines.
	 */
	public void setLine(int line) {
		this.line = line;
	}

	/**
	 * Get Console class animation status.
	 * 
	 * @return The animation status.
	 */
	public boolean isAnimation() {
		return this.animation;
	}

	/**
	 * Set Console class animation status.
	 * 
	 * @param animation The animation status.
	 */
	public void setAnimation(boolean animation) {
		this.animation = animation;
	}
}
