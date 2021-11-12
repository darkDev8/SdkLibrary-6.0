package org.sdk6.io.tool;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.sdk6.data.types.Strings;
import org.sdk6.io.base.AudioSystem;
import org.sdk6.io.base.FileSystem;
import org.sdk6.tools.ExternalTools;

import ir.sdk.audio.Mp3File;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Files {

	public class AudioFile extends FileUtils implements AudioSystem {

		private Mp3File mp3file;

		/**
		 * Constructor of AudioFile class.
		 * 
		 * @param path Audio file path in computer.
		 */
		public AudioFile(String path, boolean printStackTrace) {
			super(path);

			try {
				if (super.exists()) {
					mp3file = new Mp3File(super.path);
					if (!mp3file.hasId3v2Tag()) {
						throw new Exception("No meta tag available.");
					}
				}
			} catch (Exception e) {
				if (printStackTrace) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public String getTitle() {
			return mp3file.getId3v2Tag().getTitle();
		}

		@Override
		public String getArtist() {
			return mp3file.getId3v2Tag().getArtist();
		}

		@Override
		public String getComment() {
			return mp3file.getId3v2Tag().getComment();
		}

		@Override
		public String getYear() {
			return mp3file.getId3v2Tag().getYear();
		}

		@Override
		public String getGenre() {
			return mp3file.getId3v2Tag().getGenre() + " (" + mp3file.getId3v2Tag().getGenreDescription() + ")";
		}

		@Override
		public int getDuration() {
			return (int) mp3file.getLengthInSeconds();
		}

		@Override
		public String getRedableDuration() {
			return ExternalTools.secondsToTime((int) mp3file.getLengthInSeconds());
		}

		@Override
		public String getQuality() {
			return mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)");
		}

		@Override
		public String getSampleRate() {
			return mp3file.getSampleRate() + " hz";
		}

		@Override
		public String getTrack() {
			return mp3file.getId3v2Tag().getTrack();
		}

		@Override
		public String getUrl() {
			return mp3file.getId3v2Tag().getUrl();
		}

		@Override
		public String getCopyright() {
			return mp3file.getId3v2Tag().getCopyright();
		}

		@Override
		public String getLyrics() {
			return mp3file.getId3v2Tag().getLyrics();
		}

		@Override
		public String getEncoder() {
			return mp3file.getId3v2Tag().getEncoder();
		}
	}

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

	public class ArchiveFile extends FileUtils {

		private final byte[] EMPTY_ZIP = { 80, 75, 05, 06, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00, 00,
				00, 00, 00 };

		private CompressionLevel level;
		private final CompressionLevel[] COMPRESSION_LEVELS = { CompressionLevel.NORMAL, CompressionLevel.FAST,
				CompressionLevel.FASTEST, CompressionLevel.ULTRA, CompressionLevel.MAXIMUM };

		/**
		 * Constructor of ArchiveFile class.
		 * 
		 * @param path The output zip file path.
		 */
		public ArchiveFile(String path) {
			super(path);
			level = CompressionLevel.NORMAL;
		}

		/**
		 * Constructor of ArchiveFile class.
		 * 
		 * @param path  The output zip file path.
		 * @param level The compression level.
		 */
		public ArchiveFile(String path, CompressionLevel level) {
			super(path);
			this.level = level;
		}

		/**
		 * Generates new archive zip file.
		 * 
		 * @param password The file password.
		 * @param comment  The file comment.
		 * @param files    The selected files from computer.
		 * @param delete   Delete files after successful archive operation finished.
		 */
		public boolean generateArchive(String password, String comment, java.io.File[] files, boolean delete) {
			try {
				if (files.length == 0) {
					return false;
				}

				if (exists() && !delete()) {
					return false;
				}

				if (!exists()) {
					generateEmptyArchive();
				}

				ZipFile zipFile = new ZipFile(getName());

				ZipParameters parameters = new ZipParameters();
				parameters.setCompressionLevel(level);
				parameters.setCompressionMethod(CompressionMethod.DEFLATE);

				if (!password.isEmpty()) {
					parameters.setAesKeyStrength(AesKeyStrength.KEY_STRENGTH_256);
					parameters.setCompressionMethod(CompressionMethod.DEFLATE);
					parameters.setEncryptionMethod(EncryptionMethod.AES);

					zipFile.setPassword(password.toCharArray());
					parameters.setEncryptFiles(true);
				} else {
					parameters.setEncryptFiles(false);
				}

				if (!comment.isEmpty()) {
					zipFile.setComment(comment);
				}

				ArrayList<java.io.File> filesToAdd = new ArrayList<>();
				for (java.io.File file : files) {
					if (new FileUtils(file.getAbsolutePath()).exists()) {
						filesToAdd.add(file);
					} else {
						if (new DirectoryUtils(file.getAbsolutePath()).exists()) {
							zipFile.addFolder(file);
						}
					}
				}

				if (filesToAdd.isEmpty()) {
					return false;
				}

				zipFile.addFiles(filesToAdd, parameters);

				if (delete) {
					for (java.io.File f : files) {
						if (new FileUtils(f.getAbsolutePath()).exists()) {
							new FileUtils(f.getAbsolutePath()).delete();
						} else {
							if (new DirectoryUtils(f.getAbsolutePath()).exists()) {
								new DirectoryUtils(f.getAbsolutePath()).delete();
							}
						}
					}
				}

				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		/**
		 * Generates an empty zip archive file.
		 */
		public void generateEmptyArchive() {
			try (FileOutputStream fos = new FileOutputStream(getFile())) {
				fos.write(EMPTY_ZIP, 0, 22);
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Extract zip file archive into hard disk.
		 * 
		 * @param path     Extract path.
		 * @param password File password.
		 * @return True if extract was successful and false if not.
		 */
		public boolean extractArchive(String path, String password) {
			try {
				String pth = null;

				if (path.equals(".")) {
					pth = System.getProperty("user.dir");
				} else {
					pth = path;
				}

				ZipFile zipFile = new ZipFile(getName());
				if (zipFile.isEncrypted()) {
					zipFile.setPassword(password.toCharArray());
				}

				zipFile.extractAll(pth);
				return true;
			} catch (ZipException e) {
				e.printStackTrace();
				return false;
			}
		}

		/**
		 * Get the compression level
		 * 
		 * @return Compression level
		 */
		public CompressionLevel getLevel() {
			return level;
		}

		/**
		 * Set compression level
		 * 
		 * @param level Compression level.
		 */
		public void setLevel(CompressionLevel level) {
			this.level = level;
		}

		/**
		 * Get all compression levels
		 */
		public CompressionLevel[] getCOMPRESSION_LEVELS() {
			return COMPRESSION_LEVELS;
		}
	}

	public class ImageFile extends FileUtils {

		/**
		 * Constructor of ImageFile class.
		 *
		 * @path The file path.
		 */
		public ImageFile(String path) {
			super(path);
		}

		/**
		 * Get the width of image.
		 *
		 * @exception IOException File problems.
		 * @return The image width.
		 */
		public int getWidth() throws IOException {
			BufferedImage bimg = ImageIO.read(getFile());
			return bimg.getWidth();
		}

		/**
		 * Get the height of image.
		 *
		 * @exception IOException File problems.
		 * @return The image height.
		 */
		public int getHeight() throws IOException {
			BufferedImage bimg = ImageIO.read(getFile());
			return bimg.getHeight();
		}
	}

	private String path;

	public TextFile textFile;
	public ImageFile imageFile;
	public AudioFile audioFile;
	public ArchiveFile archiveFile;

	public Files(String path) {
		this.path = path;

		textFile = new TextFile(path);
		imageFile = new ImageFile(path);
		audioFile = new AudioFile(path, false);
		archiveFile = new ArchiveFile(path);
	}
}
