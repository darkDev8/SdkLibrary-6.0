package org.sdk6.io.files;

import org.sdk6.io.base.AudioSystem;
import org.sdk6.tools.ExternalTools;

import ir.sdk.audio.Mp3File;

public class AudioFile extends FileUtils implements AudioSystem {

	private Mp3File mp3file;

	/**
	 * Constructor of AudioFile class.
	 * 
	 * @param path Audio file path in computer.
	 */
	public AudioFile(String path, boolean printStackTrace) {
		super(path);

		try {
			if (super.exists()) {
				mp3file = new Mp3File(super.path);
				if (!mp3file.hasId3v2Tag()) {
					throw new Exception("No meta tag available.");
				}
			}
		} catch (Exception e) {
			if (printStackTrace) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getTitle() {
		return mp3file.getId3v2Tag().getTitle();
	}

	@Override
	public String getArtist() {
		return mp3file.getId3v2Tag().getArtist();
	}

	@Override
	public String getComment() {
		return mp3file.getId3v2Tag().getComment();
	}

	@Override
	public String getYear() {
		return mp3file.getId3v2Tag().getYear();
	}

	@Override
	public String getGenre() {
		return mp3file.getId3v2Tag().getGenre() + " (" + mp3file.getId3v2Tag().getGenreDescription() + ")";
	}

	@Override
	public int getDuration() {
		return (int) mp3file.getLengthInSeconds();
	}

	@Override
	public String getRedableDuration() {
		return ExternalTools.secondsToTime((int) mp3file.getLengthInSeconds());
	}

	@Override
	public String getQuality() {
		return mp3file.getBitrate() + " kbps " + (mp3file.isVbr() ? "(VBR)" : "(CBR)");
	}

	@Override
	public String getSampleRate() {
		return mp3file.getSampleRate() + " hz";
	}

	@Override
	public String getTrack() {
		return mp3file.getId3v2Tag().getTrack();
	}

	@Override
	public String getUrl() {
		return mp3file.getId3v2Tag().getUrl();
	}

	@Override
	public String getCopyright() {
		return mp3file.getId3v2Tag().getCopyright();
	}

	@Override
	public String getLyrics() {
		return mp3file.getId3v2Tag().getLyrics();
	}

	@Override
	public String getEncoder() {
		return mp3file.getId3v2Tag().getEncoder();
	}
}
