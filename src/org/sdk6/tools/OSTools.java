package org.sdk6.tools;

import javax.imageio.ImageIO;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.im.InputContext;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Native;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;
import java.util.Timer;

public class OSTools {

	private String osName;
	private String home;
	private String path;
	private String username;

	private FileSystemView fsv;
	private InputContext context;

	public OSTools() {
		this.osName = System.getProperty("os.name").toLowerCase();

		this.home = System.getProperty("user.home");
		this.path = System.getProperty("user.dir");
		this.username = System.getProperty("user.name");

		this.fsv = FileSystemView.getFileSystemView();
		this.context = InputContext.getInstance();
	}

	/**
	 * Check current operating system.
	 *
	 * @return True if operating system is Windows and false if not.
	 */
	public boolean isWindows() {
		return osName.contains("win");
	}

	/**
	 * Check current operating system.
	 *
	 * @return True if operating system is Mac and false if not.
	 */
	public boolean isMacOS() {
		return osName.contains("mac");
	}

	/**
	 * Check current operating system.
	 *
	 * @return True if operating system is Linux and false if not.
	 */
	public boolean isLinux() {
		return osName.contains("linux");
	}

	/**
	 * Check current operating system.
	 *
	 * @return True if operating system is Unix and false if not.
	 */
	public boolean isUnix() {
		return (osName.contains("nix") || osName.contains("nux") || osName.contains("aix"));
	}

	/**
	 * Detect the current operating system and return number.
	 *
	 * @return 1 for Windows, 2 for Mac, 3 for Linux, 4 for Unix.
	 */
	public int detectOS() {
		if (isWindows()) {
			return 1;
		}

		if (isMacOS()) {
			return 2;
		}

		if (isLinux()) {
			return 3;
		}

		if (isUnix()) {
			return 4;
		}

		return -1;
	}

	/**
	 * Get the current operating system name.
	 *
	 * @return Operating system name.
	 */
	public String getOSName() {
		if (isWindows()) {
			return "Microsoft Windows";
		} else if (isMacOS()) {
			return "Mac OS";
		} else if (isLinux()) {
			return "GNU Linux";
		} else if (isUnix()) {
			return "Unix";
		} else {
			return "Unknown";
		}
	}

	/**
	 * Get system home user directory.
	 *
	 * @return The directory.
	 */
	public String getHomeUser() {
		return home;
	}

	/**
	 * Get the path application is executed.
	 *
	 * @return The path.
	 */
	public String getExecutePath() {
		return path;
	}

	/**
	 * Get system username.
	 *
	 * @return The system username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Get all system partitions as File.
	 *
	 * @return All the partitions.
	 */
	public File[] getPartitions() {
		return File.listRoots();
	}

	/**
	 * Get all system partitions information.
	 *
	 * @param mode The information mode,select what you want to know about
	 *             informations.
	 * @return The system partitions information.
	 */
	public String[] getPartitionsInfo(String mode) {
		File[] files = getPartitions();

		if (files != null) {
			String[] names = new String[files.length];

			for (int i = 0; i < files.length; i++) {
				switch (mode.toLowerCase()) {
				case "letter":
					names[i] = files[i].toString();
					break;

				case "type":
					names[i] = fsv.getSystemTypeDescription(files[i]);
					break;

				case "total":
					names[i] = ExternalTools.toReadableSize(files[i].getTotalSpace());
					break;

				case "free":
					names[i] = ExternalTools.toReadableSize(files[i].getFreeSpace());
					break;

				default:
					return new String[] {};
				}

			}
			return names;
		}
		return new String[] {};
	}

	/**
	 * Get system time.
	 *
	 * @return System time.
	 */
	public String getTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH : mm : ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	/**
	 * Get system date.
	 *
	 * @return System date.
	 */
	public String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	/**
	 * Get system time and date.
	 *
	 * @return System time and date.
	 */
	public String getTimeDate() {
		return getDate() + " - " + getTime();
	}

	/**
	 * Copy a text to clipboard.
	 *
	 * @param text The text to copy to clipboard.
	 */
	public void copyText(String text) {
		try {
			StringSelection stringSelection = new StringSelection(text);
			java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String pasteClipboard() {
		try {
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			DataFlavor flavor = DataFlavor.stringFlavor;
			return clipboard.isDataFlavorAvailable(flavor) ? clipboard.getData(flavor).toString() : new String();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * Open default system file explorer.
	 *
	 * @param path The directory to open.
	 */
	public void openFileExplorer(String path) {
		try {
			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();

				Path pth = Paths.get(path);

				if (Files.exists(pth) && Files.isDirectory(pth)) {
					desktop.open(pth.toFile());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Take screenshot from desktop.
	 *
	 * @param path The path to save the file.
	 * @return True if take was successful and false if not.
	 */
	public boolean takeScreenshot(String path) {
		try {
			BufferedImage image = new Robot()
					.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "png", new File(path));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Take screenshot from desktop.
	 *
	 * @param path    The path to save the file.
	 * @param seconds After seconds start to capture screen.
	 * @return True if take was successful and false if not.
	 */
	public boolean takeScreenshot(String path, int seconds) {
		try {
			if (seconds > 0) {
				Thread th = new Thread(new ScreenShot(path, seconds));
				th.start();
				return true;
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Print all current operating system processes.
	 *
	 */
	public void printProcesses() {
		try {
			Process process = null;

			if (isWindows()) {
				Process p = new ProcessBuilder("tasklist.exe").start();
				new Thread(() -> {
					Scanner sc = new Scanner(p.getInputStream());
					if (sc.hasNextLine()) {
						sc.nextLine();
					}

					while (sc.hasNextLine()) {
						String line = sc.nextLine();
						System.out.println(line);
					}
				}).start();
				p.waitFor();
			} else if (isLinux() || isMacOS()) {
				String p;

				process = Runtime.getRuntime().exec("ps -few");
				BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while ((p = input.readLine()) != null) {
					System.out.println(p);
				}

				input.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open a file.
	 *
	 * @param path The file path.
	 */
	public void openFile(String path) {
		try {
			Path pth = Paths.get(path);

			if (Desktop.isDesktopSupported()) {
				Desktop desktop = Desktop.getDesktop();

				if (Files.exists(pth) && Files.isRegularFile(pth)) {
					desktop.open(pth.toFile());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Execute a command in terminal and print the output.
	 * 
	 * @param command The commands to be executed.
	 * @param console The console class object.
	 */
	public void executeCommand(String[] command, Console console) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(command);

		try {
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = null;
			while ((line = reader.readLine()) != null) {
				if (Objects.isNull(console)) {
					System.out.println(line);
				} else {
					console.print(line, true);
				}
			}

			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Execute a command in terminal and print output to text area.
	 * 
	 * @param command The commands to be executed.
	 * @param area    The JTextArea object.
	 */
	public static void executeCommand(String[] command, JTextArea area) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command(command);

		try {
			Process process = processBuilder.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line = null;
			while ((line = reader.readLine()) != null) {
				if (Objects.isNull(area)) {
					System.out.println(line);
				} else {
					area.setText(area.getText() + line + "\n");
				}
			}

			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check current keyboard layout.
	 * 
	 * @return True if layout is persian and false if not.
	 */
	public boolean isKeyboardPersian() {
		return context.getLocale().toString().equalsIgnoreCase("fa_IR");
	}

	/**
	 * Check current keyboard layout.
	 * 
	 * @return True if layout is english and false if not.
	 */
	public boolean isKeyboardEnglish() {
		return context.getLocale().toString().equalsIgnoreCase("en_US");
	}

	/**
	 * Get the name of system current keyboard layout.
	 * 
	 * @param toLowerCase Convert layout to lower case.
	 * @return The system current keyboard layout.
	 */
	public String getKeyboardLayout(boolean toLowerCase) {
		if (toLowerCase) {
			return context.getLocale().toString().toLowerCase();
		}

		return context.getLocale().toString();
	}

}