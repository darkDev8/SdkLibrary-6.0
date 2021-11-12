package org.sdk6.io.base;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface FileSystem {
	
	/**
	 * Get file full path.
	 * 
	 * @return File full path.
	 */
     String getPath();

 	/**
 	 * Get file name from path.
 	 * 
 	 * @return File name.
 	 */
     String getName();

 	/**
 	 * Get file File class object.
 	 * 
 	 * @return File class object.
 	 */
     File getFile();

 	/**
 	 * Get file size to bytes.
 	 * 
 	 * @return File size.
 	 */
     long getSize() throws IOException;

 	/**
 	 * Get file readable size.
 	 * 
 	 * @return File readable size.
 	 */
     String getReadableSize() throws IOException;

 	/**
 	 * Get file modify date.
 	 * 
 	 * @return Directory modify date.
 	 */
     String getModifyDate() throws IOException;

 	/**
 	 * Get file create date.
 	 * 
 	 * @return File create date.
 	 */
     String getCreateDate() throws IOException;

 	/**
 	 * Get file access date.
 	 * 
 	 * @return File access date.
 	 */
     String getAccessDate() throws IOException;

 	/**
 	 * Get file modify time.
 	 * 
 	 * @return File modify time.
 	 */
     String getModifyTime() throws IOException;

 	/**
 	 * Get file create time.
 	 * 
 	 * @return File create time.
 	 */
     String getCreateTime() throws IOException;

 	/**
 	 * Get file access time.
 	 * 
 	 * @return File access time.
 	 */
     String getAccessTime() throws IOException;

 	/**
 	 * Get parent directory name.
 	 * 
 	 * @return Parent directory name.
 	 */
     String getDirectoryName() throws IOException;

 	/**
 	 * Get parent directory full path.
 	 * 
 	 * @return Parent directory full path.
 	 */
     String getDirectoryPath() throws IOException;

 	/**
 	 * Get owner of file.
 	 * 
 	 * @return File owner (user).
 	 */
     String getOwner() throws IOException;

 	/**
 	 * Detect file type by it's extension.
 	 * 
 	 * @return File type. 
 	 */
     String getType();

  	/**
  	 * Get file base name without extension.
  	 * 
  	 * @return File base name.
  	 */
     String getBaseName();

  	/**
  	 * Get file extension. 
  	 * 
  	 * @return File extension.
  	 */
     String getExtension();

 	/**
 	 * Check if file is empty or not.
 	 * 
 	 * @return True if file is empty or not.
 	 */
     boolean isEmpty() throws IOException;
}
