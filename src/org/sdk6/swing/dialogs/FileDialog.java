package org.sdk6.swing.dialogs;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.plaf.FileChooserUI;

import org.sdk6.data.types.Strings;
import org.sdk6.swing.Swing;

import java.util.Objects;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileDialog {

	private String title;
	private String startPath;
	private String selectType;

	private FileNameExtensionFilter[] filters;

	/**
	 * Constructor of FileDialog class.
	 * @param title The dialog title.
	 * @param startPath The path dialog starts in.
	 * @param selectType The dialog selection type.
	 */
	public FileDialog(String title, String startPath, String selectType) {
		super();

		this.startPath = startPath;
		this.title = title;
		this.selectType = selectType;
	}

	/**
	 * Get dialog title.
	 *
	 * @return Dialog title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set dialog title.
	 *
	 * @param title Dialog title.
	 */
	public FileDialog setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Get dialog start path.
	 *
	 * @return Dialog start path.
	 */
	public String getStartPath() {
		return startPath;
	}

	/**
	 * Set dialog start path.
	 *
	 * @param startPath Dialog start path.
	 */
	public FileDialog setStartPath(String startPath) {
		this.startPath = startPath;
		return this;
	}

	/**
	 * Get dialog filters.
	 *
	 * @return Dialog title.
	 */
	public FileNameExtensionFilter[] getFilters() {
		return filters;
	}

	/**
	 * Set dialog filters.
	 *
	 * @param filters Dialog filters.
	 */
	public FileDialog setFilters(FileNameExtensionFilter[] filters) {
		this.filters = filters;
		return this;
	}

	/**
	 * Show a save dialog to save a file.
	 *
	 * @return Selected file.
	 */
	public File showSaveDialog() {
		JFileChooser fileChooser = new JFileChooser();
		Strings strings = new Strings();

		if (!strings.isNullOrEmpty(startPath)) {
			fileChooser.setCurrentDirectory(new File(startPath));
		}
		
		if (!strings.isNullOrEmpty(title)) {
			fileChooser.setDialogTitle(title);
		}
		
		if (!Objects.isNull(filters)) {
			Objects.requireNonNull(fileChooser);
			Arrays.stream(filters).forEach(fileChooser::addChoosableFileFilter);
		}

		fileChooser.setMultiSelectionEnabled(false);
		if (selectType.equalsIgnoreCase("f")) {
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		} else if (selectType.equalsIgnoreCase("d")) {
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		
		return fileChooser.showSaveDialog(null) == 0 ? fileChooser.getSelectedFile() : null;
	}

	/**
	 * Show an open dialog to open a file.
	 *
	 * @return Selected file.
	 */
	public File showOpenDialog() {
		JFileChooser fileChooser = new JFileChooser();
		Strings strings = new Strings();

		if (!strings.isNullOrEmpty(startPath)) {
			fileChooser.setCurrentDirectory(new File(startPath));
		}
		
		if (!strings.isNullOrEmpty(title)) {
			fileChooser.setDialogTitle(title);
		}
		
		if (!Objects.isNull(filters)) {
			Objects.requireNonNull(fileChooser);
			Arrays.stream(filters).forEach(fileChooser::addChoosableFileFilter);
		}

		fileChooser.setMultiSelectionEnabled(false);
		if (selectType.equalsIgnoreCase("f")) {
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		} else if (selectType.equalsIgnoreCase("d")) {
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		
		return fileChooser.showOpenDialog(null) == 0 ? fileChooser.getSelectedFile() : null;
	}

	/**
	 * Show a directory select dialog.
	 *
	 * @return Selected directory file.
	 */
	public File selectDirectory() {
		JFileChooser fileChooser = new JFileChooser();
		Strings strings = new Strings();
		
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (!strings.isNullOrEmpty(startPath)) {
			fileChooser.setCurrentDirectory(new File(startPath));
		}
		
		if (!strings.isNullOrEmpty(title)) {
			fileChooser.setDialogTitle(title);
		}

		return fileChooser.showOpenDialog(null) == 0 ? fileChooser.getSelectedFile() : null;
	}

	/**
	 * Show an open dialog to open multiple files.
	 *
	 * @return Selected files.
	 */
	public List<File> showMultipleOpenDialog() {
		JFileChooser fileChooser = new JFileChooser();
		Strings strings = new Strings();
		List<File> files = new ArrayList<>();

		if (!strings.isNullOrEmpty(startPath)) {
			fileChooser.setCurrentDirectory(new File(startPath));
		}
		
		if (!strings.isNullOrEmpty(title)) {
			fileChooser.setDialogTitle(title);
		}
		
		if (!Objects.isNull(filters)) {
			Objects.requireNonNull(fileChooser);
			Arrays.stream(filters).forEach(fileChooser::addChoosableFileFilter);
		}

		fileChooser.setMultiSelectionEnabled(true);
		if (selectType.equalsIgnoreCase("f")) {
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		} else if (selectType.equalsIgnoreCase("d")) {
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		
		return fileChooser.showOpenDialog(null) == 0 ? 
				Arrays.asList(fileChooser.getSelectedFiles()) : files;
	}
}
