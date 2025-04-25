package com.example.soundmusik.controller;

import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import com.example.soundmusik.LikedSongsActivity;
import com.example.soundmusik.MainActivity;
import com.example.soundmusik.R;
import com.example.soundmusik.SearchResultsActivity;
import com.example.soundmusik.SettingsActivity;
import com.example.soundmusik.model.Song;
import com.example.soundmusik.model.SongDB;


/**
 * Controller class for handling functionality in the MainActivity.
 * MainActivtyController
 *
 * @author Luis Saenz, Ryan Diaz
 */
public class MainActivityController implements View.OnClickListener {

    MainActivity mainActivity;
    AssetManager manager;

    /**
     * Constructor initializes the controller with a reference to the MainActivity.
     * @param activity the MainActivity associated with this controller
     */
    public MainActivityController(MainActivity activity) {
        mainActivity = activity;
        manager = mainActivity.getAssets();
    }



    /**
     * Handles click events on views within MainActivity.
     * @param view the view that was clicked
     */
    public void onClick(View view) {
        Intent intent;
        if(view.getId() == R.id.mainResultsView){
            intent = new Intent(view.getContext(), SearchResultsActivity.class);
            view.getContext().startActivity(intent);
        }
        else if(view.getId() == R.id.mainLikedView){
            intent = new Intent(view.getContext(), LikedSongsActivity.class);
            view.getContext().startActivity(intent);
        }
        else if(view.getId() == R.id.mainSettingsView){
            intent = new Intent(view.getContext(), SettingsActivity.class);
            view.getContext().startActivity(intent);
        }

    }

    /**
     * Searches for a song in the database and handles the results.
     * @param artistName the name of the artist
     * @param songName   the name of the song
     */
    public void searchForSong(String artistName, String songName) {
//        Log.d("MainActivityController", "Searching for - Artist Name: " + artistName + ", Song Name: " + songName);

//      Get song object from database
        Song searchResults = SongDB.getTopSongMatch(artistName, songName);
        Log.d("MainActivityController", "top song match: "+ searchResults);

//        get near matches
        ArrayList<Song> nearMatches = SongDB.findNearMatches(searchResults);

        if (nearMatches != null && !nearMatches.isEmpty()) {
//           send Nearmatches to searchresults activity
            Intent intent = new Intent(mainActivity, SearchResultsActivity.class);
            intent.putExtra("NEAR_MATCHES", nearMatches);
            mainActivity.startActivity(intent);
        } else {
            Toast.makeText(mainActivity, "No near matches found", Toast.LENGTH_SHORT).show();
        }
    }


    public void loadSongs() {
        try {
            SongDB.loadProperties(mainActivity, "musicDatabase.csv");
        } catch (IOException e) {
            Log.e("MainActivityController", "Error loading songs", e);
            // Delegate to View for displaying error message
            mainActivity.showErrorMessage("Failed to load songs");
        }
    }


}