package com.example.soundmusik;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soundmusik.controller.MainActivityController;
import com.example.soundmusik.model.SongDB;
import com.example.soundmusik.model.UserSettings;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText editTextArtistName;
    private EditText editTextSongName;
    private Button buttonSearch;
    private FirebaseAuth mAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        // Sign in anonymously if no user is logged in
        if (mAuth.getCurrentUser() == null) {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d("FirebaseAuth", "Anonymous login successful: " + user.getUid());

                        } else {
                            Log.e("FirebaseAuth", "Anonymous login failed", task.getException());
                        }
                    });
        }

        // Load song database
        try {
            SongDB.loadProperties(this, "musicDatabase.csv");
        } catch (IOException e) {
            Log.e("MainActivity", "Error loading songs", e);
            Toast.makeText(this, "Failed to load songs", Toast.LENGTH_LONG).show();
        }

        // UI setup
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int selectedColor = preferences.getInt("selected_color", Color.WHITE);
        findViewById(android.R.id.content).setBackgroundColor(selectedColor);

        editTextArtistName = findViewById(R.id.editTextArtistName);
        editTextSongName = findViewById(R.id.editTextSongName);
        buttonSearch = findViewById(R.id.buttonSearch);

        MainActivityController mainActivityController = new MainActivityController(this);
        mainActivityController.loadSongs();

        buttonSearch.setOnClickListener(v ->
                mainActivityController.searchForSong(
                        editTextArtistName.getText().toString(),
                        editTextSongName.getText().toString())
        );

        findViewById(R.id.mainResultsView).setOnClickListener(mainActivityController);
        findViewById(R.id.mainSettingsView).setOnClickListener(mainActivityController);
        findViewById(R.id.mainLikedView).setOnClickListener(mainActivityController);

        updateTextColor();
        updateTextSize();
    }

    private void updateTextColor(){
        int textColor = UserSettings.getTextColot(this);
        ((TextView)findViewById(R.id.textView2)).setTextColor(textColor);
    }

    public void changeTextColorBlack(View view) {
        UserSettings.setColorText(this, Color.BLACK);
        updateTextColor();
    }

    public void changeTextColorWhite(View view){
        UserSettings.setColorText(this, Color.WHITE);
        updateTextColor();
    }

    private void updateTextSize(){
        float textSize = UserSettings.getTextSize(this);
        ((TextView)findViewById(R.id.textView2)).setTextSize(textSize);
    }

    public void increaseTextSize(View view){
        UserSettings.setTextSize(this, UserSettings.getTextSize(this) + 2.0f);
        updateTextSize();
    }

    public void decreaseTextSize(View view){
        UserSettings.setTextSize(this, UserSettings.getTextSize(this) - 2.0f);
        updateTextSize();
    }

    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
