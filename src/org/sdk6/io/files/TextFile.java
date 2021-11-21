package org.sdk6.io.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.sdk6.data.types.Strings;

public class TextFile extends FileUtils {

	/**
	 * Constructor of TextFile class.
	 *
	 * @path The file path.
	 */
	public TextFile(String path) {
		super(path);
	}

	/**
	 * Read file content and save it to a String.
	 *
	 * @return The file content.
	 */
	public String read() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path.toFile()));
		StringBuilder sb = new StringBuilder();

		String st;
		while ((st = br.readLine()) != null) {
			sb.append(st).append("\n");
		}

		return sb.toString();
	}

	/**
	 * Read all file lines.
	 *
	 * @return The file content.
	 */
	public List<String> readAllLines() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path.toFile()));
		List<String> lines = new ArrayList<>();

		String st;
		while ((st = br.readLine()) != null) {
			lines.add(st);
		}

		return lines;
	}

	/**
	 * Read file only first line.
	 *
	 * @param text Skip lines until to detect not empty line.
	 * @return The first line of file.
	 */
	public String readFirstLine(boolean text) throws IOException {
		List<String> content = readAllLines();
		if (content.isEmpty()) {
			return new String();
		}

		if (text) {
			for (String s : content) {
				if (!s.isEmpty() && !s.equals("\n")) {
					return s;
				}
			}
		} else {
			return content.get(0);
		}

		return new String();
	}

	/**
	 * Read file only last line.
	 *
	 * @param text Skip lines until to detect not empty line.
	 * @return The last line of file.
	 */
	public String readLastLine(boolean text) throws IOException {
		List<String> content = readAllLines();
		if (content.isEmpty()) {
			return new String();
		}

		if (text) {
			List<String> list = new ArrayList<>();
			list.addAll(Arrays.asList(content.toArray(new String[0])));

			Collections.reverse(list);

			for (int i = 0; i < list.size(); i++) {
				if (!list.get(i).isEmpty() && !list.get(i).equals("\n")) {
					return list.get(i);
				}
			}
		} else {
			return content.get(content.size() - 1);
		}

		return new String();
	}

	/**
	 * Write String to a file in hard disk.
	 * 
	 * @param text The text want to write to the file.
	 * @return True if write was successful and false if not.
	 */
	public boolean write(String text) {
		try {
			FileWriter wr = new FileWriter(getPath());
			wr.write(text);

			wr.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Write String to a file in hard disk.
	 * 
	 * @param text The text want to write to the file.
	 * @return True if write was successful and false if not.
	 */
	public boolean write(String[] text) {
		if (new Strings().isNullOrEmpty(text)) {
			return false;
		}

		try {
			FileWriter wr = new FileWriter(getPath());
			for (String line : text) {
				wr.write(line);
				wr.write("\n");
			}

			wr.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Append text to end of an existing file.
	 * 
	 * @param text The text want to append to end of the file.
	 * @return True if append was successful and false if not.
	 */
	public boolean append(String text) {
		try {
			FileWriter fw = new FileWriter(getPath(), true);
			fw.write(text.concat(System.lineSeparator()));

			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Append text to end of an existing file.
	 * 
	 * @param text The text want to append to end of the file.
	 * @return True if append was successful and false if not.
	 */
	public boolean append(String[] text) {
		for (String line : text) {
			append(line.concat("\n"));
		}

		return true;
	}

	/**
	 * Search the file for a key.
	 * 
	 * @param key   The key to search the file.
	 * @param match If true it will search exact match of the key.
	 * @return True if key found and false if not.
	 */
	public boolean search(String key, boolean match) {
		try {
			List<String> content = readAllLines();

			for (String s : content) {
				if (match) {
					if (s.equals(key)) {
						return true;
					}
				} else {
					if (s.contains(key)) {
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Clear the file content.
	 * 
	 * @return True if clear was successful and false if not.s
	 */
	public boolean clear() {
		try {
			FileWriter wr = new FileWriter(getPath());
			wr.write(new String());

			wr.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Delete a text from file.
	 * 
	 * @param key The text want to delete from file.
	 * @return True if delete write to file was successful and false if not.
	 */
	public boolean delete(String key) throws IOException {
		List<String> content = readAllLines();
		content.remove(key);

		return write(content.toArray(new String[0]));
	}

	/**
	 * Count file all lines.
	 * 
	 * @return Number of file lines.
	 */
	public int countLines() throws IOException {
		return java.nio.file.Files.readAllLines(path).size();
	}

	/**
	 * Count file all empty lines.
	 * 
	 * @return Number of file empty lines.
	 */
	public int countEmptyLines() throws IOException {
		List<String> content = readAllLines();
		int count = 0;

		for (String line : content) {
			if (new Strings().isNullOrEmpty(line) && !line.equals("\n")) {
				count++;
			}
		}

		return count;
	}

	/**
	 * Count file all words.
	 * 
	 * @return Number of file words.
	 */
	public int countWords() throws IOException {
		return read().split("\\s+").length;
	}

	/**
	 * Count file all characters.
	 * 
	 * @param empty Contains empty characters or not.
	 * @return Number of file characters.
	 */
	public int countCharacters(boolean empty) throws IOException {
		int total = read().toCharArray().length;

		if (empty) {
			return total;
		} else {
			char[] content = read().toCharArray();
			int count = 0;

			for (char c : content) {
				if (String.valueOf(c).equals(" ")) {
					count++;
				}
			}

			return total - count;
		}
	}

	/**
	 * Search the file content and count matches in the file.
	 * 
	 * @param key   The key want to search in the file.
	 * @param match Exact match in the file.
	 * @return Number of file words.
	 */
	public int countMatches(String key, boolean match) throws IOException {
		return new Strings().countMatches(read(), key, "\\s+", match);
	}
}