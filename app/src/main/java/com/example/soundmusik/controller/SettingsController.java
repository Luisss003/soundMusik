package com.example.soundmusik.controller;

import android.content.Intent;
import android.content.res.AssetManager;
import android.view.View;

import com.example.soundmusik.LikedSongsActivity;
import com.example.soundmusik.MainActivity;
import com.example.soundmusik.R;
import com.example.soundmusik.SearchResultsActivity;
import com.example.soundmusik.SettingsActivity;


/**
 * Controller class for handling functionality in the SettingsActivity.
 *  SearchResultsActivity manages and displays the song recommendations.
 *  @author Luis Saenz, Ryan Diaz
 *
 */
public class SettingsController implements View.OnClickListener {
    SettingsActivity settingsActivity;
    AssetManager manager;


    /**
     * Constructor initializes the controller with a reference to the SettingsActivity.
     * @param activity the SettingsActivity associated with this controller
     */
    public SettingsController(SettingsActivity activity){
        settingsActivity = activity;
        manager = settingsActivity.getAssets();
    }

    /**
     * Handles click events on views within SettingsActivity.
     * @param view view that was clicked
     */
    public void onClick(View view) {
        Intent intent;
        if(view.getId() == R.id.settingsMainView){
            intent = new Intent(view.getContext(), MainActivity.class);
            view.getContext().startActivity(intent);
        }
        else if(view.getId() == R.id.settingsResultsView){
            intent = new Intent(view.getContext(), SearchResultsActivity.class);
            view.getContext().startActivity(intent);
        }
        else if(view.getId() == R.id.settingsLikedView){
            intent = new Intent(view.getContext(), LikedSongsActivity.class);
            view.getContext().startActivity(intent);
        }

    }
}