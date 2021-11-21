package org.sdk6.net;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class DownloadProgress implements Runnable {

	private long size;
	private String path;

	/**
	 * Constructor of DownloadProgress class.
	 *
	 * @param size The file ultimate size.
	 * @param path The download path in the computer.
	 */
	public DownloadProgress(long size, String path) {
		this.size = size;
		this.path = path;
	}

	/**
	 * Start to check the file and show progress.
	 */
	@Override
	public void run() {
		long currentProgress = 0, nextProgress = 0;
		System.out.print("[");

		try {
			Path pth = Paths.get(path);

			if (Objects.isNull(path)) {
				return;
			} else {
				if (path.isEmpty()) {
					return;
				}

				if (Files.exists(pth) && Files.isRegularFile(pth)) {
					if (Files.size(pth) == size) {
						for (int i = 0; i < 50; i++) {
							System.out.print("-");
						}

						System.out.println("]");
						return;
					}
				}
			}

			while (true) {
				if (Files.exists(pth) && Files.isRegularFile(pth)) {
					currentProgress = (Files.size(pth) * 100) / size;

					if (currentProgress != nextProgress) {
						nextProgress = currentProgress;

						if (nextProgress % 2 == 0) {
							System.out.print("-");
						}
					}

					if (size == Files.size(pth)) {
						break;
					}
				}
			}

			System.out.println("]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
