package org.sdk6.io.base;


public interface AudioSystem {
	/**
	 * Get audio file title.
	 * 
	 * @return Audio file title.
	 */
    String getTitle();
    
	/**
	 * Get audio file artist.
	 * 
	 * @return Audio file artist.
	 */
    String getArtist();
    
	/**
	 * Get audio file comment.
	 * 
	 * @return Audio file comment.
	 */
    String getComment();
    
	/**
	 * Get audio file year.
	 * 
	 * @return Audio file year.
	 */
    String getYear();
    
	/**
	 * Get audio file genre.
	 * 
	 * @return Audio file genre.
	 */
    String getGenre();
    
	/**
	 * Get audio file duration in seconds.
	 * 
	 * @return Audio file duration.
	 */
    int getDuration();
    
	/**
	 * Get audio file true duration.
	 * 
	 * @return Audio file duration.
	 */
    String getRedableDuration();
    
	/**
	 * Get audio file quality.
	 * 
	 * @return Audio file quality.
	 */
    String getQuality();
    
	/**
	 * Get audio file sample rate.
	 * 
	 * @return Audio file sample rate.
	 */
    String getSampleRate();
    
	/**
	 * Get audio file track.
	 * 
	 * @return Audio file track.
	 */
    String getTrack();
    
	/**
	 * Get audio file URL.
	 * 
	 * @return Audio file URL
	 */
    String getUrl();
    
	/**
	 * Get audio file copyright.
	 * 
	 * @return Audio file copyright.
	 */
    String getCopyright();
    
	/**
	 * Get audio file copyright.
	 * 
	 * @return Audio file copyright.
	 */
    String getLyrics();
    
	/**
	 * Get audio file encoder.
	 * 
	 * @return Audio file encoder.
	 */
    String getEncoder();
}
