package com.example.soundmusik.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class representing a database of songs.
 * SongDB acts as the class for operations relating to the song list which will
 * contain all songs, and song matches
 *
 * @author Ryan Diaz, Luis Saenz
 */
public class SongDB  {
    private static ArrayList<Song> songs = new ArrayList<>();


    /**
     * Constructs a SongDB object with the provided list of songs.
     * @param songs the ArrayList of songs to initialize the SongDB with
     */
    public SongDB(ArrayList<Song> songs) {
        this.songs = songs;
    }

    /**
     * Load properties from a CSV file and return a SongDB object.
     *
     * @param con  the application context
     * @param fileName the name of the CSV file
     * @return a SongDB object containing the loaded songs
     * @throws IOException if there is an issue reading the file
     */
    public static SongDB loadProperties  (Context con, String fileName) throws IOException {

//      If songs have already been loaded, return them
        if(songs.size() > 0) {
            SongDB songToReturn = new SongDB(songs);
            return songToReturn;
        }
//        songs.clear();
        //Prepare AssetManager tools
        AssetManager assetManager = con.getAssets();
        //Try to open and create an input stream from given filename
        try (InputStream inputStream = assetManager.open(fileName);
             //Create scanner
             Scanner scan = new Scanner(inputStream)) {
            //While there is data to be read
            if (scan.hasNextLine()) // Skip the header line
                scan.nextLine(); // Read and ignore the header if present

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] info = line.split(",");
                //If there is 10 items in a line
                if (info.length == 7) {
                    //Read properties of CSV
                    String artistName = info[0].trim();
                    String trackName = info[1];
                    int songDuration = Integer.parseInt(info[2].trim());
                    double danceRating = Double.parseDouble(info[3].trim());
                    int key = Integer.parseInt(info[4].trim());
                    int tempo = Integer.parseInt(info[5].trim());
                    int timeSig = Integer.parseInt(info[6].trim());
                    //Create GeneralSong object with info gathered, and add to SongDB ArrayList
                    Song songToReturn = new Song(artistName, trackName, songDuration, danceRating, key, tempo, timeSig);
                    songs.add(songToReturn);
                }

            }
            //At this point, SongDB ArrayList should be filled, so create the object, and return it back to the user
            SongDB songToReturn = new SongDB(songs);
            return songToReturn;
        }
    }

    /**
     * Get a specific song from the SongDB.
     * @param artistName the name of the artist
     * @param trackName  the name of the track
     * @return the requested Song object, or null if not found
     */
    public static Song getSong(String artistName, String trackName){
        for(Song song : songs){ //For each song object
            if((song.getArtistName().compareTo(artistName) == 0) && (song.getTrackName().compareTo(trackName) == 0)){
                return song; //Return song that is looked for
            }
        }
        return null;
    }

    /**
     * Get the top matching song based on artist name and song name.
     * @param artistName the name of the artist
     * @param songName   the name of the song
     * @return the top matching Song object, or null if not found
     */
    public static Song getTopSongMatch(String artistName, String songName) {
        Song bestMatch = null;
        int highestScore = -1;  // Initialize with a score that will be lower than any real score

        if ((artistName == null || artistName.isEmpty()) && (songName == null || songName.isEmpty())) {
            return null;
        }

        String artistNameLower = artistName != null ? artistName.toLowerCase() : "";
        String songNameLower = songName != null ? songName.toLowerCase() : "";

        for (Song song : songs) {
            String currentArtistName = song.getArtistName().toLowerCase();
            String currentSongName = song.getTrackName().toLowerCase();

            int score = 0;
            if (currentArtistName.equals(artistNameLower)) {
                score += 2;  // Higher score for exact artist name match
            } else if (currentArtistName.contains(artistNameLower)) {
                score += 1;  // Lower score for partial artist name match
            }

            if (currentSongName.equals(songNameLower)) {
                score += 2;  // Higher score for exact song name match
            } else if (currentSongName.contains(songNameLower)) {
                score += 1;  // Lower score for partial song name match
            }

            if (score > highestScore) {
                highestScore = score;
                bestMatch = song;
            }
        }

        return bestMatch;
    }



    /**
     * Return ArrayList of matching songs
     * @param songToFind
     * @return ArrayList of matching songs
     */
    public static ArrayList<Song> findNearMatches(Song songToFind){
        ArrayList<Song> matchingSongs = new ArrayList<Song>();
        if (songToFind == null) {
            return matchingSongs; // Return empty list if songToFind is null
        }
        for(Song song: songs){
            int songDur = Math.abs(song.getSongDuration() - songToFind.getSongDuration());
            int songKey = Math.abs(song.getKey() - songToFind.getKey());
            int songTempo = Math.abs(song.getTempo() - songToFind.getTempo());
            double songDance = Math.abs(song.getDanceRating() - songToFind.getDanceRating());
            int songTimeSig = Math.abs(song.getTimeSig() - songToFind.getTimeSig());


            if((songDur <= 25000) && (songKey <= 2 ) && (songTempo <= 15) && (songDance <= 0.05) && (songTimeSig <= 1))  {
                matchingSongs.add(song);
            }
        }

        Log.d("SongDB", "Found " + matchingSongs.size() + " near matches.");

        return matchingSongs;
    }

    /**
     * Return ArrayList of matching songs by string name
     * @param artistName
     * @param songName
     * @return ArrayList of matching songs by string name
     */
    public static ArrayList<Song> searchSongs(String artistName, String songName) {
        ArrayList<Song> foundSongs = new ArrayList<>();
        // Convert search strings to lower case for case-insensitive search
        String searchArtistName = artistName.toLowerCase();
        String searchSongName = songName.toLowerCase();

        for (Song song : songs) {
            // Check for matches - either or
            boolean matchesArtist = searchArtistName.isEmpty() || song.getArtistName().toLowerCase().contains(searchArtistName);
            boolean matchesSong = searchSongName.isEmpty() || song.getTrackName().toLowerCase().contains(searchSongName);

            if (matchesArtist && matchesSong) {
                foundSongs.add(song);
            }
        }

        return foundSongs;
    }


    /**
     * Returns the ArrayList of songs stored in the SongDB.
     * @return the ArrayList of songs
     */
    public static ArrayList<Song> getSongs() {
        return songs;
    }


    /**
     * Sets the ArrayList of songs in the SongDB.
     * @param songs the ArrayList of songs to set
     */
    public static void setSongs(ArrayList<Song> songs) {
        SongDB.songs = songs;
    }

    /**
     * Returns a string representation of the SongDB object.
     * @return a string representation of the SongDB
     */
    @Override
    public String toString() {
        return "SongDB{}";
    }
}


