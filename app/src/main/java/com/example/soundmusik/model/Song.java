package com.example.soundmusik.model;

import java.io.Serializable;

/**
 * Class representing a Song.
 * Song is a class that will hold all properties of data read; this includes a songs artist, name
 *  duration, etc.
 *
 * @author Luis Saenz, Ryan Diaz, Leif Anderson
 */
public class  Song implements Serializable {
    private String artistName;
    private String trackName;
    private int songDuration;
    private double danceRating;
    private int key;
    private int tempo;
    private int timeSig;


    /**
     * Constructor for creating a Song object.
     * @param artistName   the name of the artist
     * @param trackName    the name of the track
     * @param songDuration the duration of the song
     * @param danceRating  the dance rating of the song
     * @param key          the key of the song
     * @param tempo        the tempo of the song
     * @param timeSig      the time signature of the song
     */
    public Song(String artistName, String trackName, int songDuration, double danceRating, int key, int tempo, int timeSig) {
        this.artistName = artistName;
        this.trackName = trackName;
        this.songDuration = songDuration;
        this.danceRating = danceRating;
        this.key = key;
        this.tempo = tempo;
        this.timeSig = timeSig;

    }

    /**
     * Getter for the artist name.
     * @return the artist name
     */
    public String getArtistName() {
        return artistName;
    }

    /**
     * Setter for the artist name.
     * @param artistName the artist name to set
     */
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    /**
     * Getter for the track name.
     * @return the track name
     */
    public String getTrackName() {
        return trackName;
    }

    /**
     * Setter for the track name.
     * @param trackName the track name to set
     */
    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    /**
     * Getter for the song duration.
     * @return the song duration
     */
    public int getSongDuration() {
        return songDuration;
    }

    /**
     * Setter for the song duration.
     * @param songDuration the song duration to set
     */
    public void setSongDuration(int songDuration) {
        this.songDuration = songDuration;
    }

    /**
     * Getter for the dance rating.
     * @return the dance rating
     */
    public double getDanceRating() {
        return danceRating;
    }


    /**
     * Setter for the dance rating
     * @param danceRating the dance rating to set
     */
    public void setDanceRating(double danceRating) {
        this.danceRating = danceRating;
    }

    /**
     * Getter for the key.
     * @return the key
     */
    public int getKey() {
        return key;
    }

    /**
     * Setter for the key.
     * @param key the key to set
     */
    public void setKey(int key) {
        this.key = key;
    }

    /**
     * Getter for the tempo.
     * @return the tempo
     */
    public int getTempo() {
        return tempo;
    }

    /**
     * Setter for the tempo.
     * @param tempo the tempo to set
     */
    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    /**
     * Getter for the time signature.
     * @return the time signature
     */
    public int getTimeSig() {
        return timeSig;
    }

    /**
     * Setter for the time signature.
     * @param timeSig the time signature to set
     */
    public void setTimeSig(int timeSig) {
        this.timeSig = timeSig;
    }


    /**
     * Provides a string representation of the Song
     * @return a string representation of the Song
     */
    @Override
    public String toString() {
        return trackName +" by \n" + artistName;
    }
}