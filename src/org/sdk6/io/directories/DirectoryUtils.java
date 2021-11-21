package org.sdk6.io.directories;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import org.sdk6.data.types.Strings;
import org.sdk6.io.base.DirectorySystem;
import org.sdk6.tools.ExternalTools;

public class DirectoryUtils implements DirectorySystem {
	protected Path path;

	public DirectoryUtils(String path) {
		if (path.trim().equals(".")) {
			this.path = Paths.get(System.getProperty("user.dir"));
		} else {
			this.path = Paths.get(path.trim());
		}
	}

	@Override
	public String getName() {
		return path.getFileName().toString();
	}

	@Override
	public String getPath() {
		return path.toAbsolutePath().toString();
	}

	@Override
	public File getFile() {
		return path.toFile();
	}

	@Override
	public String getDirectoryPath() throws IOException {
		return path.toRealPath().getParent().toString();
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
	public long getSize() {
		return FileUtils.sizeOfDirectory(path.toFile());
	}

	@Override
	public String getReadableSize() {
		return ExternalTools.toReadableSize(FileUtils.sizeOfDirectory(path.toFile()));
	}

	@Override
	public String getModifyDate() throws IOException {
		BasicFileAttributes attrs = java.nio.file.Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
		FileTime time = attrs.lastModifiedTime();

		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.toMillis()));
	}

	@Override
	public String getCreateDate() throws IOException {
		BasicFileAttributes attrs = java.nio.file.Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
		FileTime time = attrs.creationTime();

		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.toMillis()));
	}

	@Override
	public String getAccessDate() throws IOException {
		BasicFileAttributes attrs = java.nio.file.Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
		FileTime time = attrs.lastAccessTime();

		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(time.toMillis()));
	}

	@Override
	public String getModifyTime() throws IOException {
		BasicFileAttributes attrs = java.nio.file.Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
		FileTime time = attrs.lastModifiedTime();

		return new SimpleDateFormat("HH:mm:ss").format(new Date(time.toMillis()));
	}

	@Override
	public String getCreateTime() throws IOException {
		BasicFileAttributes attrs = java.nio.file.Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
		FileTime time = attrs.creationTime();

		return new SimpleDateFormat("HH:mm:ss").format(new Date(time.toMillis()));
	}

	@Override
	public String getAccessTime() throws IOException {
		BasicFileAttributes attrs = java.nio.file.Files.readAttributes(Paths.get(getPath()), BasicFileAttributes.class);
		FileTime time = attrs.lastAccessTime();

		return new SimpleDateFormat("HH:mm:ss").format(new Date(time.toMillis()));
	}

	@Override
	public int countFiles() throws IOException {
		int count = 0;
		DirectoryStream<Path> directoryStream = java.nio.file.Files.newDirectoryStream(Paths.get(getPath()));

		for (Path pth : directoryStream) {
			if (java.nio.file.Files.exists(pth) && java.nio.file.Files.isRegularFile(pth)) {
				count++;
			}

		}
		return count;
	}

	@Override
	public int countDirectories() throws IOException {
		int count = 0;
		DirectoryStream<Path> directoryStream = java.nio.file.Files.newDirectoryStream(Paths.get(getPath()));

		for (Path pth : directoryStream) {
			if (java.nio.file.Files.exists(pth) && java.nio.file.Files.isDirectory(pth)) {
				count++;
			}

		}
		return count;
	}

	@Override
	public int countSubFiles() throws IOException {
		return (int) java.nio.file.Files.walk(path).parallel().filter(p -> p.toFile().isFile()).count();
	}

	@Override
	public int countSubDirectories() throws IOException {
		return (int) java.nio.file.Files.walk(path).parallel().filter(p -> p.toFile().isDirectory()).count() - 1;
	}

	@Override
	public String getDirectoryInfo() {
		return null;
	}

	@Override
	public String getOwner() throws IOException {
		return java.nio.file.Files.getOwner(path).getName();
	}

	@Override
	public List<File> getSubFiles() throws IOException {
		return java.nio.file.Files.walk(path).filter(java.nio.file.Files::isRegularFile).map(Path::toFile)
				.collect(Collectors.toList());
	}

	@Override
	public List<File> getSubDirectories() throws IOException {
		return java.nio.file.Files.walk(path).filter(java.nio.file.Files::isDirectory).map(Path::toFile)
				.collect(Collectors.toList());
	}

	@Override
	public List<File> getFiles() throws IOException {
		List<File> files = new ArrayList<>();

		java.nio.file.Files.list(path).forEach(file -> {
			if (java.nio.file.Files.exists(file) && java.nio.file.Files.isRegularFile(file)) {
				files.add(file.toFile());
			}
		});

		return files;
	}

	@Override
	public List<File> getDirectories() throws IOException {
		List<File> directories = new ArrayList<>();

		java.nio.file.Files.list(path).forEach(file -> {
			if (java.nio.file.Files.exists(file) && java.nio.file.Files.isDirectory(file)) {
				directories.add(file.toFile());
			}
		});

		return directories;
	}

	@Override
	public List<File> getDirectoryContent() {
		File[] content = new File(getPath()).listFiles();
		List<File> files = new ArrayList<>();

		Collections.addAll(files, content != null ? content : new File[0]);
		return files;
	}

	@Override
	public long countFilesLines() {
		return 0;
	}

	/**
	 * Check to see if the path is a directory or not.
	 * 
	 * @return True if it is a directory and false if not.
	 */
	public boolean exists() {
		return java.nio.file.Files.exists(path) && java.nio.file.Files.isDirectory(path);
	}

	/**
	 * Copy a directory to another location in computer.
	 * 
	 * @param destination The destination path.
	 * @return True if it copy was successful and false if not.
	 */
	public boolean copy(String destination) throws IOException {
		try {
			FileUtils.copyDirectory(path.toFile(), new File(destination));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Copy a directory to another location in computer.
	 * 
	 * @param destination The destination path.
	 * @param replace     If true it will replace current directory with last one.
	 * @return True if it copy was successful and false if not.
	 */
	public boolean copy(String destination, boolean replace) {
		try {
			DirectoryUtils dp = new DirectoryUtils(destination);
			if (!replace && dp.exists() && this.getName().equals(dp.getName())) {
				return true;
			}

			FileUtils.copyDirectory(path.toFile(), new File(destination));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Move a directory to another location in computer.
	 * 
	 * @param destination The destination path.
	 * @return True if it move was successful and false if not.
	 */
	public boolean move(String destination) {
		try {
			FileUtils.moveDirectory(path.toFile(), new File(destination));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Move a directory to another location in computer.
	 * 
	 * @param destination The destination path.
	 * @param replace     If true it will replace current directory with last one.
	 * @return True if it move was successful and false if not.
	 */
	public boolean move(String destination, boolean replace) {
		try {
			DirectoryUtils dp = new DirectoryUtils(destination);
			if (!replace && dp.exists() && this.getName().equals(dp.getName())) {
				return true;
			}

			FileUtils.moveDirectory(path.toFile(), new File(destination));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Rename the directory to new name.
	 * 
	 * @param name The directory new name.
	 * @return True if it rename was successful and false if not.
	 */
	public boolean rename(String name) {
		try {
			String directoryPath = path.toRealPath().getParent().toString();
			java.nio.file.Files.move(path, path.resolveSibling(name));

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
	 * Check if directory is empty or not.
	 * 
	 * @return True if directory is empty or not.
	 */
	public boolean isEmpty() {
		return FileUtils.sizeOfDirectory(path.toFile()) == 0;
	}

	/**
	 * Delete a directory from computer.
	 * 
	 * @return True if delete was successful and false if not.
	 */
	public boolean delete() {
		try {
			FileUtils.deleteDirectory(path.toFile());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Create new directory with specific name.
	 * 
	 * @return True if it creation was successful.
	 */
	public boolean create() {
		try {
			java.nio.file.Files.createDirectories(path);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Erase all files from a directory.
	 * 
	 * @param subs If true it will erase all sub files too.
	 * @return True if it erase was successful and false if not.
	 */
	public boolean eraseFiles(boolean subs) {
		try {
			List<File> files = null;

			if (subs) {
				files = getSubFiles();
			} else {
				files = getFiles();
			}

			for (File file : files) {
				java.nio.file.Files.deleteIfExists(file.toPath());
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Erase all directories from a directory.
	 * 
	 * @return True if it erase was successful and false if not.
	 */
	public boolean eraseDirectories() {
		try {
			for (File file : getDirectories()) {
				if (!file.getAbsolutePath().equals(path.toAbsolutePath().toString())) {
					FileUtils.deleteDirectory(file);
				}
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Erase all directory content.
	 * 
	 * @param subs If true it will erase all sub files too.
	 * @return True if it erase was successful and false if not.
	 */
	public boolean eraseAll(boolean subs) {
		try {
			return eraseDirectories() && eraseFiles(subs);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Search a directory for a file (file/directory).
	 * 
	 * @param file The file want to search in a directory.
	 * @return True if found and false if not.
	 */
	public boolean search(File file) {
		try {
			if (Objects.isNull(file)) {
				return false;
			}

			List<File> files = getSubFiles();

			for (File f : files) {
				if (f.getName().equals(file.getName())) {
					return true;
				}
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Search a directory for a file (file name).
	 * 
	 * @param file The file want to search in a directory.
	 * @return True if found and false if not.
	 */
	public boolean search(String name) {
		try {
			if (new Strings().isNullOrEmpty(name)) {
				return false;
			}

			List<File> files = getSubFiles();

			for (File f : files) {
				if (f.getName().equals(name)) {
					return true;
				}
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
