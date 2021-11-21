package org.sdk6.io.files;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.sdk6.io.base.FileSystem;
import org.sdk6.tools.ExternalTools;

public class FileUtils implements FileSystem {
	protected Path path;

	public static final String[] WINEXEC = { "bat", "cmd", "exe", "msi" };
	public static final String[] LINEXEC = { "sh", "run" };
	public static final String[] MACEXEC = { "app", "dmg" };

	public static final String[] AUDIO = { "mp3", "wav", "ogg", "mpa", "aac", "au", "m4a", "m4b", "mpc", "oga", "tta",
			"wma", "wv" };
	public static final String[] IMAGE = { "jpg", "jpeg", "jfif", "webp", "exif", "bmp", "png", "svg", "fig", "tiff",
			"gif" };
	public static final String[] VIDEO = { "mp4", "avi", "mkv", "flv", "3gp", "nsv", "webm", "vob", "gifv", "mov", "qt",
			"wmv", "viv", "amv", "m4p", "m4v", "mpg", "mp2", "mpv", "svi", "3g2", "f4v", "f4p", "f4a", "f4b" };
	public static final String[] TEXT = { "txt", "xml", "fxml", "xmlns", "iml" };
	public static final String[] DOCUMENT = { "doc", "html", "html", " docx", "odt", "pdf", "xml", "xmnl", "fxml",
			"json" };

	public static final String[] LIBRARY = { "dll", "jar", "so", "pyd" };
	public static final String[] ARCHIVE = { "zip", "rar", "7zip", "tar.gz", "gz", "rar4", "tar.xz" };

	public static final String[] FONT = { "tf", "ttf" };

	public static final String JAVA_SOURCE = "java";
	public static final String CSHARP_SOURCE = "cs";
	public static final String C_SOURCE = "c";
	public static final String CPLUSPLUS_SOURCE = "cpp";
	public static final String PYTHON_SOURCE = "py";
	public static final String JAVASCRIPT_SOURCE = "js";
	public static final String PHP_SOURCE = "php";
	public static final String GOLANG_SOURCE = "go";
	public static final String RUBY_SOURCE = "rb";
	public static final String KOTLIN_SOURCE = "kt";

	public static final String C_HEADER = "h";
	public static final String CPLUSPLUS_HEADER = "hh";

	public FileUtils(String path) {
		if (path.trim().equals(".")) {
			this.path = Paths.get(System.getProperty("user.dir"));
		} else {
			this.path = Paths.get(path.trim());
		}
	}

	@Override
	public String getPath() {
		return path.toAbsolutePath().toString();
	}

	@Override
	public String getName() {
		return path.getFileName().toString();
	}

	@Override
	public File getFile() {
		return path.toFile();
	}

	@Override
	public long getSize() throws IOException {
		return Files.size(path);
	}

	@Override
	public String getReadableSize() throws IOException {
		return ExternalTools.toReadableSize(Files.size(path));
	}

	@Override
	public String getModifyDate() throws IOException {
		BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
		FileTime time = attrs.lastModifiedTime();

		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.toMillis()));
	}

	@Override
	public String getCreateDate() throws IOException {
		BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
		FileTime time = attrs.creationTime();

		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.toMillis()));
	}

	@Override
	public String getAccessDate() throws IOException {
		BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
		FileTime time = attrs.lastAccessTime();

		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.toMillis()));
	}

	@Override
	public String getModifyTime() throws IOException {
		BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
		FileTime time = attrs.lastModifiedTime();

		return new SimpleDateFormat("HH:mm:ss").format(new Date(time.toMillis()));
	}

	@Override
	public String getCreateTime() throws IOException {
		BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
		FileTime time = attrs.creationTime();

		return new SimpleDateFormat("HH:mm:ss").format(new Date(time.toMillis()));
	}

	@Override
	public String getAccessTime() throws IOException {
		BasicFileAttributes attrs = Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
		FileTime time = attrs.lastAccessTime();

		return new SimpleDateFormat("HH:mm:ss").format(new Date(time.toMillis()));
	}

	@Override
	public String getDirectoryName() throws IOException {
		try {
			return path.toRealPath().getParent().getFileName().toString();
		} catch (Exception e) {
			return path.toRealPath().getParent().toString();
		}
	}

	@Override
	public String getDirectoryPath() throws IOException {
		return path.toRealPath().getParent().toString();
	}

	@Override
	public String getOwner() throws IOException {
		return Files.getOwner(path).getName();
	}

	@Override
	public String getType() {
		String[][] mimeType = { WINEXEC, LINEXEC, MACEXEC, AUDIO, IMAGE, VIDEO, TEXT, DOCUMENT, LIBRARY, ARCHIVE,
				FONT };

		if (exists()) {
			String ext = getExtension().toLowerCase();

			for (int i = 0; i < mimeType.length; i++) {
				List<String> list = new ArrayList<>();
				list.addAll(Arrays.asList(mimeType[i]));

				if (list.contains(ext)) {
					switch (i) {
					case 0:
						return "Windows executable file";

					case 1:
						return "Linux executable file(script)";

					case 2:
						return "Mac application";

					case 3:
						return "Audio file";

					case 4:
						return "Image file";

					case 5:
						return "Video file";

					case 6:
						return "Plain text file";

					case 7:
						return "Document file";

					case 8:
						return "Library file";

					case 9:
						return "Archive file";

					case 10:
						return "Font file";
					}
				}

				list.clear();
			}

			if (ext.equals(JAVA_SOURCE)) {
				return "Java source file";
			} else if (ext.equals(CSHARP_SOURCE)) {
				return "cSharp source file";
			} else if (ext.equals(C_SOURCE)) {
				return "C source file";
			} else if (ext.equals(CPLUSPLUS_SOURCE)) {
				return "C++ source file";
			} else if (ext.equals(PYTHON_SOURCE)) {
				return "Python source file";
			} else if (ext.equals(JAVASCRIPT_SOURCE)) {
				return "Javascript source file";
			} else if (ext.equals(PHP_SOURCE)) {
				return "Php source file";
			} else if (ext.equals(GOLANG_SOURCE)) {
				return "GoLang Source file";
			} else if (ext.equals(RUBY_SOURCE)) {
				return "Ruby source file";
			} else if (ext.equals(C_HEADER)) {
				return "C header file";
			} else if (ext.equals(CPLUSPLUS_HEADER)) {
				return "C++ header file";
			} else if (ext.contentEquals(KOTLIN_SOURCE)) {
				return "Kotlin source file";
			}

			return "Unknown type";
		}

		return "No file";
	}

	@Override
	public String getBaseName() {
		return FilenameUtils.getBaseName(path.getFileName().toString());
	}

	@Override
	public String getExtension() {
		return FilenameUtils.getExtension(path.getFileName().toString());
	}

	@Override
	public boolean isEmpty() throws IOException {
		return Files.size(path) == 0;
	}

	/**
	 * Check to see if the path is a file or not.
	 * 
	 * @return True if it is a file and false if not.
	 */
	public boolean exists() {
		return Files.exists(path) && Files.isRegularFile(path);
	}

	/**
	 * Delete a file from computer.
	 * 
	 * @return True if delete was successful and false if not.
	 */
	public boolean delete() throws IOException {
		return Files.deleteIfExists(path);
	}

	/**
	 * Create new file with specific name.
	 * 
	 * @return True if it creation was successful.
	 */
	public boolean create() throws IOException {
		try {
			Files.createFile(path);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Copy a file to another location in computer.
	 * 
	 * @param destination The destination path.
	 * @return True if it copy was successful and false if not.
	 */
	public boolean copy(String destination) throws IOException {
		try {
			Files.copy(path, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Copy a file to another location in computer.
	 * 
	 * @param destination The destination path.
	 * @param replace     If true it will replace current file with last one.
	 * @return True if it copy was successful and false if not.
	 */
	public boolean copy(String destination, boolean replace) {
		try {
			FileUtils fp = new FileUtils(destination);
			if (!replace && fp.exists() && this.getName().equals(fp.getName())) {
				return true;
			}

			Files.copy(path, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Move a file to another location in computer.
	 * 
	 * @param destination The destination path.
	 * @return True if it move was successful and false if not.
	 */
	public boolean move(String destination) {
		try {
			Files.move(path, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Move a file to another location in computer.
	 * 
	 * @param destination The destination path.
	 * @param replace     If true it will replace current file with last one.
	 * @return True if it move was successful and false if not.
	 */
	public boolean move(String destination, boolean replace) {
		try {
			FileUtils fp = new FileUtils(destination);
			if (!replace && fp.exists() && this.getName().equals(fp.getName())) {
				return true;
			}

			Files.move(path, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Rename the file to new name.
	 * 
	 * @param name The file new name.
	 * @return True if it rename was successful and false if not.
	 */
	public boolean rename(String name) throws IOException {
		try {
			String directoryPath = path.toRealPath().getParent().toString();
			Files.move(path, path.resolveSibling(name));

			if (System.getProperty("os.name").contains("win")) {
				path = Paths.get(directoryPath.concat("\\").concat(name));
			} else {
				path = Paths.get(directoryPath.concat("/").concat(name));
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Fetch all file information in a single String.
	 * 
	 * @return The file information.
	 */
	public String getFileInfo() {
		try {
			StringBuilder sb = new StringBuilder();

			sb.append("[Name]             ").append(getName()).append("\n");
			sb.append("[Modify date]      ").append(getModifyDate()).append("\n");
			sb.append("[Path]             ").append(getPath()).append("\n");
			sb.append("[Modify time]      ").append(getModifyTime()).append("\n");
			sb.append("[Size]             ").append(getReadableSize()).append("\n");
			sb.append("[Create date]      ").append(getCreateDate()).append("\n");
			sb.append("[Directory name]   ").append(getDirectoryName()).append("\n");
			sb.append("[Create time]      ").append(getCreateTime()).append("\n");
			sb.append("[Directory path]   ").append(getDirectoryPath()).append("\n");
			sb.append("[access time]      ").append(getAccessTime()).append("\n");
			sb.append("[Base name]        ").append(getBaseName()).append("\n");
			sb.append("[access date]      ").append(getAccessDate()).append("\n");
			sb.append("[Extension]        ").append(getExtension()).append("\n");
			sb.append("[File type]        ").append(getType());

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Remove file extension.
	 * 
	 * @return The if remove was successful and false if not.
	 */
	public boolean removeExtension() {
		try {
			String name = new StringBuilder(path.getFileName().toString()).reverse().toString();
			int dotIndex = name.indexOf(".");
			if (dotIndex == -1) {
				return true;
			}

			return rename(new StringBuilder(name.substring(dotIndex + 1)).reverse().toString());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Add extension to file.
	 * 
	 * @return True if append was successful and false if not.
	 */
	public boolean addExtension(String extension) {
		try {
			if (getExtension().equals(extension)) {
				return true;
			}

			if (extension.equals(".")) {
				return false;
			}

			if (!extension.startsWith(".")) {
				extension = ".".concat(extension);
			}

			String name = path.getFileName().toString().concat(extension);
			return rename(name);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Completely erase the file content.
	 * 
	 * @return True if erase was successful and false if not.
	 */
	public boolean eraseFile() {
		try {
			return delete() && create();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Check if file is locked and used by another process in the system or not.
	 * 
	 * @param file The File object class from hard disk.
	 * @return True if file is used by another process and false if not.
	 */
	public boolean isFileLocked() {
		boolean blocked = false;

		try (RandomAccessFile fis = new RandomAccessFile(getFile(), "rw")) {
			FileLock lck = fis.getChannel().lock();
			lck.release();
		} catch (Exception ex) {
			blocked = true;
		}

		if (blocked) {
			return blocked;
		}

		String parent = getFile().getParent(), rnd = UUID.randomUUID().toString();

		File newName = new File(parent + "/" + rnd);
		if (getFile().renameTo(newName)) {
			newName.renameTo(getFile());
		} else {
			blocked = true;
		}

		return blocked;
	}
}
