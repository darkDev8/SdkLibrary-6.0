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

	public static final FileNameExtensionFilter[] textFileFilters = { new FileNameExtensionFilter("Text files", "txt"),
			new FileNameExtensionFilter("Xml file", "xml"), new FileNameExtensionFilter("Json file", "json") };

	public static final FileNameExtensionFilter[] imageFileFilters = {
			new FileNameExtensionFilter("Joint photographic experts group", "jpeg"),
			new FileNameExtensionFilter("Portable network graphics", "png"),
			new FileNameExtensionFilter("Graphics interchange format", "gif"),
			new FileNameExtensionFilter("Tagged image file", "tiff"),
			new FileNameExtensionFilter("Photoshop document", "psd"),
			new FileNameExtensionFilter("Raw image formats", "raw"), };

	public static final FileNameExtensionFilter[] videoFileFilters = {
			new FileNameExtensionFilter("Windows media video", "wmv"),
			new FileNameExtensionFilter("MPEG-4 Part 14", "mp4"), new FileNameExtensionFilter("Flash video", "flv"),
			new FileNameExtensionFilter("Audio video interleave", "avi"),
			new FileNameExtensionFilter("Video transport stream", "ts"),
			new FileNameExtensionFilter("MPEG video file", "mpg"),
			new FileNameExtensionFilter("iTunes video file", "m4v"),
			new FileNameExtensionFilter("H.264 encoded video file", "h264"),
			new FileNameExtensionFilter("Digital video file", "dv"),
			new FileNameExtensionFilter("Windows media file", "wm"),
			new FileNameExtensionFilter("3GPP Media File", "3gpp"),
			new FileNameExtensionFilter("Netflix video file", "nfv"),
			new FileNameExtensionFilter("MPEG-2 video", "m2v") };

	public static final FileNameExtensionFilter[] documentFileFilters = {
			new FileNameExtensionFilter("Microsoft Word documents", "doc", "docx"),
			new FileNameExtensionFilter("Hypertext markup language", "html", "html"),
			new FileNameExtensionFilter("Openoffice word document", "odt"),
			new FileNameExtensionFilter("Portable document format", "pdf"),
			new FileNameExtensionFilter("Microsoft Excel file", "xls", "xlsx"),
			new FileNameExtensionFilter("Openoffice excel document ", "ods"),
			new FileNameExtensionFilter("Microsoft PowerPoint files", "ppt", "pptx") };

	public static final FileNameExtensionFilter[] windowsExecutableFilters = {
			new FileNameExtensionFilter("Batch file", "bat"), new FileNameExtensionFilter("Binary executable", "bin"),
			new FileNameExtensionFilter("Command script", "cmd"), new FileNameExtensionFilter("Command File", "com"),
			new FileNameExtensionFilter("Control panel extension", "cpl"),
			new FileNameExtensionFilter("Executable file", "exe"),
			new FileNameExtensionFilter("Microsoft silent installer", "msi"),
			new FileNameExtensionFilter("Windows gadget", "gadget"),
			new FileNameExtensionFilter("Internet communication settings", "ins"),
			new FileNameExtensionFilter("File shortcuts", "lnk") };

	public static final FileNameExtensionFilter[] linuxExecutableFilters = {
			new FileNameExtensionFilter("Runtime loaded library", "so", "o"),
			new FileNameExtensionFilter("Shell script", "sh"),
			new FileNameExtensionFilter("Installer package for Debian/Ubuntu releases", "deb"),
			new FileNameExtensionFilter("Installer package for RedHat/CentOS releases.", "rpm"),
			new FileNameExtensionFilter("Drivers and kernel modules", "ko"),
			new FileNameExtensionFilter("Linux executable files", "run") };

	public static final FileNameExtensionFilter[] macExecutableFilters = {
			new FileNameExtensionFilter("Mac Os executable files", "app"),
			new FileNameExtensionFilter("Apple disk image file", "dmg") };

	/**
	 * Constructor of FileDialog class.
	 * 
	 * @param title      The dialog title.
	 * @param startPath  The path dialog starts in.
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

		return fileChooser.showOpenDialog(null) == 0 ? Arrays.asList(fileChooser.getSelectedFiles()) : files;
	}
}
