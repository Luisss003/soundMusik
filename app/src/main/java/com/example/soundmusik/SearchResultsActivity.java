package com.example.soundmusik;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.soundmusik.controller.SearchResultsController;
import com.example.soundmusik.model.Song;
import com.example.soundmusik.model.UserSettings;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    private ListView listViewNearMatches;
    private SearchResultsController searchResultsController;
    private static ArrayList<Song> currentResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresults);

        FirebaseApp.initializeApp(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedColor = preferences.getInt("selected_color", Color.WHITE);
        View rootView = findViewById(android.R.id.content);
        rootView.setBackgroundColor(selectedColor);

        searchResultsController = new SearchResultsController(this);
        listViewNearMatches = findViewById(R.id.searchResultsList);
        ArrayList<Song> newResults = (ArrayList<Song>) getIntent().getSerializableExtra("NEAR_MATCHES");

        if (newResults != null && !newResults.isEmpty()) {
            currentResults = newResults;
        }

        searchResultsController.populateListView(listViewNearMatches, currentResults);

        // Handle song "like" tap
        listViewNearMatches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song likedSong = currentResults.get(position);
                saveLikedSongToFirebase(likedSong);
            }
        });

        updateTextColor();
        updateTextSize();
    }

    private void saveLikedSongToFirebase(Song song) {
        String userId = "testUser123"; // TEMP user ID for testing

        // 🔍 Log the song being saved
        Log.d("FirebaseTest", "Saving song: " + song.getTrackName() + " by " + song.getArtistName());

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("likedSongs")
                .child(userId);

        String key = ref.push().getKey();

        if (key != null) {
            ref.child(key).setValue(song)
                    .addOnSuccessListener(aVoid ->
                            Toast.makeText(this, "Song liked!", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to like song", Toast.LENGTH_SHORT).show();
                        e.printStackTrace(); // Helpful for debugging in Logcat
                    });
        }
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
