package org.sdk6.io.base;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface DirectorySystem {

	/**
	 * Get directory name from path.
	 * 
	 * @return Directory name.
	 */
    String getName();

	/**
	 * Get directory full path.
	 * 
	 * @return Directory full path.
	 */
    String getPath() throws IOException;

	/**
	 * Get directory File class object.
	 * 
	 * @return File class object.
	 */
    File getFile();

	/**
	 * Get parent directory full path.
	 * 
	 * @return Parent directory full path.
	 */
    String getDirectoryPath() throws IOException;

	/**
	 * Get parent directory name.
	 * 
	 * @return Parent directory name.
	 */
    String getDirectoryName() throws IOException;

	/**
	 * Get directory size to bytes.
	 * 
	 * @return Directory size.
	 */
    long getSize();

	/**
	 * Get directory readable size.
	 * 
	 * @return Directory readable size.
	 */
    String getReadableSize();

	/**
	 * Get directory modify date.
	 * 
	 * @return Directory modify date.
	 */
    String getModifyDate() throws IOException;

	/**
	 * Get directory modify time.
	 * 
	 * @return Directory modify time.
	 */
    String getModifyTime() throws IOException;

	/**
	 * Get directory create date.
	 * 
	 * @return Directory create date.
	 */
    String getCreateDate() throws IOException;

	/**
	 * Get directory create time.
	 * 
	 * @return Directory create time.
	 */
    String getCreateTime() throws IOException;

	/**
	 * Get directory access date.
	 * 
	 * @return Directory access date.
	 */
    String getAccessDate() throws IOException;

	/**
	 * Get directory access time.
	 * 
	 * @return Directory access time.
	 */
    String getAccessTime() throws IOException;

	/**
	 * Count only directory files.
	 * 
	 * @return Number of files in a directory.
	 */
    int countFiles() throws IOException;

	/**
	 * Count only directory directories.
	 * 
	 * @return Number of directories in a directory.
	 */
    int countDirectories() throws IOException;

	/**
	 * Count only sub files in a directory.
	 * 
	 * @return Number of sub files in a directory.
	 */
    int countSubFiles() throws IOException;

	/**
	 * Count only sub directories in a directory.
	 * 
	 * @return Number of sub directories in a directory.
	 */
    int countSubDirectories() throws IOException;

	/**
	 * Get all directory information in a single String.
	 * 
	 * @return Directory information. 
	 */
    public String getDirectoryInfo();

	/**
	 * Get owner of directory.
	 * 
	 * @return Directory owner (user).
	 */
    String getOwner() throws IOException;

	/**
	 * Get only sub files from a directory as list.
	 * 
	 * @return Directory sub files.
	 */
    List<File> getSubFiles() throws IOException;

	/**
	 * Get only sub directories from a directory as list.
	 * 
	 * @return Directory sub directory.
	 */
    List<File> getSubDirectories() throws IOException;

	/**
	 * Get files form a directory as list.
	 * 
	 * @return Get directory files.
	 */
    List<File> getFiles() throws IOException;

	/**
	 * Get directories form a directory as list.
	 * 
	 * @return Get directory directories.
	 */
    List<File> getDirectories() throws IOException;
    
	/**
	 * Get directory all files and directories as list.
	 * 
	 * @return Get entire directory content.
	 */
    List<File> getDirectoryContent();

	/**
	 * Count all files lines in a directory.
	 * 
	 * @return Number of lines in all files in a directory.
	 */
    long countFilesLines();
}
