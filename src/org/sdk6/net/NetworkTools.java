package org.sdk6.net;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Objects;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import org.sdk6.data.types.Strings;
import org.sdk6.tools.ExternalTools;

public class NetworkTools {

	private String url;
	
	/**
	 * Constructor of NetworkTools class.
	 * 
	 * @param url The website or file URL.
	 */
	public NetworkTools(String url) {
		this.url = url;
	}

	/**
	 * Check the machine Internet connection.
	 * 
	 * @return True if Internet is connected and false if not.
	 */
	public boolean isInternetConnected() {
		try {
			URLConnection conn = new URL("http://www.google.com").openConnection();
			conn.connect();

			conn.getInputStream().close();
			return true;
		} catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Check machine network interfaces to see if there is an interface available or
	 * not.
	 * 
	 * @return True if an interface is available and false if not.
	 */
	public boolean detectNetworkInterfaces() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface interf = interfaces.nextElement();
				if (interf.isUp() && !interf.isLoopback()) {
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
	 * Check the URL to see if link is correct or not.
	 * 
	 * @return True if link is correct and false if not.
	 */
	public boolean checkWebsite() {
		try {
			URLConnection conn = new URL(url).openConnection();
			conn.connect();

			conn.getInputStream().close();
			return true;
		} catch (MalformedURLException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * Get the URL file size to bytes.
	 * 
	 * @return The file size from Internet.
	 */
	public long getFileSize() throws IOException {
		try {
			HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
			urlConnection.setRequestMethod("HEAD");

			String lengthHeaderField = urlConnection.getHeaderField("content-length");
			Long result = lengthHeaderField == null ? null : Long.parseLong(lengthHeaderField);

			return result == null || result < 0L ? -1L : result;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * Get the URL file readable size.
	 * 
	 * @return The file readable size from Internet.
	 */
	public String getReadableSize() throws IOException {
		return ExternalTools.toReadableSize(getFileSize());
	}

	/**
	 * Get file name from URL.
	 * 
	 * @return The link file name.
	 */
	public String getFileName() throws MalformedURLException {
		return FilenameUtils.getName((new URL(url)).getPath());
	}

	/**
	 * Get the URL file base name without extension.
	 * 
	 * @return The file base name.
	 */
	public String getFileBaseName() throws MalformedURLException {
		return FilenameUtils.getBaseName((new URL(url)).getPath());
	}

	/**
	 * Get the file extension from URL.
	 * 
	 * @return The file extension .
	 */
	public String getFileExtension() throws MalformedURLException {
		return FilenameUtils.getExtension((new URL(url)).getPath());
	}

	/**
	 * Download a file from Internet.
	 * 
	 * @param progress Show character progress in console screen or not.
	 * @return True if download was successful and false if not.
	 */
	public boolean downloadFile(boolean progress) {
		try {
			if (progress) {
				Thread th = new Thread(new DownloadProgress(getFileSize(), getFileName()));

				th.start();
			}

			FileUtils.copyURLToFile(new URL(url), new File(getFileName()));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Download a file from Internet.
	 * 
	 * @param progress Show character progress in console screen or not.
	 * @param path     The download path from the computer.
	 * @return True if download was successful and false if not.
	 */
	public boolean downloadFile(boolean progress, String path) {
		try {
			if (progress) {
				Thread th = new Thread(new DownloadProgress(this.getFileSize(), path));
				th.start();
			}

			FileUtils.copyURLToFile(new URL(this.url), new File(path));
			return true;
		} catch (IOException var4) {
			var4.printStackTrace();
			return false;
		}
	}

	/**
	 * Convert Domain name to IP address.
	 * 
	 * @return Domain IP address.
	 */
	public String getServerIP() {
		try {
			InetAddress address = InetAddress.getByName(new URL(url).getHost());
			return address.getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Send TTL packages and ping URL.
	 * 
	 * @param timeout Time for server to wait to establish connection.
	 * @param repeat  Number of times to send request to the server.
	 * @param start   The start String to print in the console before ping.
	 * @param number  Enable or disable number at the first of each line.
	 * @return True if download was successful and false if not.
	 */
	public void ping(int timeout, int repeat, String start, boolean number) {
		try {

			for (int i = 0; i < repeat; i++) {
				HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
				con.setRequestMethod("GET");
				con.setConnectTimeout(timeout);

				con.connect();

				if (!new Strings().isNullOrEmpty(start)) {
					System.out.print(start);
				}

				if (number) {
					System.out.print("[" + (i + 1) + "]  ");
				}

				int code = con.getResponseCode();
				if (code == 200) {
					System.out.println("Replay from \"" + getServerIP() + "\" ok, return code (" + code + ")");
				} else {
					System.out.println("No response");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
