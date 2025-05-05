package com.example.soundmusik;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.soundmusik.controller.SearchResultsController;
import com.example.soundmusik.model.Song;
import com.example.soundmusik.model.UserSettings;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    private ListView listViewNearMatches;
    private SearchResultsController searchResultsController;
    private static ArrayList<Song> currentResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresults);

        // Set custom background from preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedColor = preferences.getInt("selected_color", Color.WHITE);
        findViewById(android.R.id.content).setBackgroundColor(selectedColor);

        // Initialize controller
        searchResultsController = new SearchResultsController(this);

        // Get list of near matches from Intent
        ArrayList<Song> newResults = (ArrayList<Song>) getIntent().getSerializableExtra("NEAR_MATCHES");
        if (newResults != null && !newResults.isEmpty()) {
            currentResults = newResults;
        }

        // Populate the ListView with controller logic
        listViewNearMatches = findViewById(R.id.searchResultsList);
        searchResultsController.populateListView(listViewNearMatches, currentResults);

        // Apply user UI preferences
        updateTextColor();
        updateTextSize();
    }

    private void updateTextColor() {
        int textColor = UserSettings.getTextColot(this);
        TextView textView = findViewById(R.id.textView3);
        textView.setTextColor(textColor);
    }

    private void updateTextSize() {
        float textSize = UserSettings.getTextSize(this);
        TextView textView = findViewById(R.id.textView3);
        textView.setTextSize(textSize);
    }

    public void changeTextColorBlack(View view) {
        UserSettings.setColorText(this, Color.BLACK);
        updateTextColor();
    }

    public void changeTextColorWhite(View view) {
        UserSettings.setColorText(this, Color.WHITE);
        updateTextColor();
    }

    public void increaseTextSize(View view) {
        float newSize = UserSettings.getTextSize(this) + 2.0f;
        UserSettings.setTextSize(this, newSize);
        updateTextSize();
    }

    public void decreaseTextSize(View view) {
        float newSize = UserSettings.getTextSize(this) - 2.0f;
        UserSettings.setTextSize(this, newSize);
        updateTextSize();
    }
}
