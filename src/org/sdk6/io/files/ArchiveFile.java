package org.sdk6.io.files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.sdk6.io.directories.DirectoryUtils;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.CompressionLevel;
import net.lingala.zip4j.model.enums.CompressionMethod;
import net.lingala.zip4j.model.enums.EncryptionMethod;

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