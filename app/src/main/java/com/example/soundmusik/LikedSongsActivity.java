package com.example.soundmusik;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.soundmusik.controller.LikedSongsController;
import com.example.soundmusik.controller.MainActivityController;
import com.example.soundmusik.model.UserSettings;


/**
 * Activity class representing the Liked Songs screen.
 *  LikedSongsActivity sets up and manages the Liked songs view.
 *  @author Ryan Diaz, Luis Saenz, Ashley Adeniyi
 *
 */
public class LikedSongsActivity extends AppCompatActivity {

    /**
     * Initializes the LikedSongsActivity when created.
     * @param savedInstanceState the saved instance state
     */
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likedsongs);

        // Set background color based on user preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedColor = preferences.getInt("selected_color", Color.WHITE);
        View rootView = findViewById(android.R.id.content);
        rootView.setBackgroundColor(selectedColor);

        // Set up the controller for handling button clicks
        LikedSongsController likedSongsController = new LikedSongsController(this);
        Button likedMainView = findViewById(R.id.likedSongsMainView);
        Button likedResultsView = findViewById(R.id.likedSongsResultsView);
        Button likedSettingsView = findViewById(R.id.likedSongsSettingsView);

        likedMainView.setOnClickListener(likedSongsController);
        likedResultsView.setOnClickListener(likedSongsController);
        likedSettingsView.setOnClickListener(likedSongsController);

        // Update text color and size based on user preferences
        updateTextColor();
        updateTextSize();

        // Populate the ListView with liked songs

//      Update the listview with likedSongs

        final ListView listView = findViewById(R.id.listView);
        if (listView != null) {
            likedSongsController.populateListView("likes.txt");
        }
    }


    /**
     * Updates the text color of relevant views based on user preferences.
     */
    private void updateTextColor(){
        int textColor = UserSettings.getTextColot(this);
        TextView textView1 = findViewById(R.id.likedSongsTitle);
        textView1.setTextColor(textColor);
    }

    /**
     * Changes the text color to black and updates the view.
     * @param view the clicked view
     */
    public void changeTextColorBlack(View view) {
        UserSettings.setColorText(this, Color.BLACK);
        updateTextColor();
    }

    /**
     * Changes the text color to white and updates the view.
     * @param view the clicked view
     */
    public void changeTextColorWhite(View view){
        UserSettings.setColorText(this, Color.WHITE);
        updateTextColor();
    }

    /**
     * Updates the text size of relevant views based on user preferences.
     */
    private void updateTextSize(){
        float textSize = UserSettings.getTextSize(this);

        TextView textView1 = findViewById(R.id.likedSongsTitle);
        textView1.setTextSize(textSize);
    }

    /**
     * Increases the text size by 2.0 and updates the view.
     * @param view the clicked view
     */
    public void increaseTextSize(View view){
        float newSize = UserSettings.getTextSize(this) + 2.0f;
        UserSettings.setTextSize(this,newSize);
        updateTextSize();
    }

    /**
     * Decreases the text size by 2.0 and updates the view.
     * @param view the clicked view
     */
    public void decreaseTextSize(View view){
        float newSize = UserSettings.getTextSize(this) - 2.0f;
        UserSettings.setTextSize(this,newSize);
        updateTextSize();
    }

}
