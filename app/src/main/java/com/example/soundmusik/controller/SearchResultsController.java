package com.example.soundmusik.controller;

import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.soundmusik.LikedSongsActivity;
import com.example.soundmusik.MainActivity;
import com.example.soundmusik.R;
import com.example.soundmusik.SearchResultsActivity;
import com.example.soundmusik.SettingsActivity;
import com.example.soundmusik.model.Song;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * SearchResultsController manages UI interactions in SearchResultsActivity,
 * handles navigation, and processes song likes with Firebase integration.
 */
public class SearchResultsController implements View.OnClickListener {

    private final SearchResultsActivity searchResultsActivity;
    private final LikedSongsController likedSongsController;
    private ArrayAdapter<Song> adapter;

    private final FirebaseUser currentUser;
    private final DatabaseReference database;

    /**
     * Constructs the controller and initializes Firebase.
     * @param activity the SearchResultsActivity associated with this controller
     */
    public SearchResultsController(SearchResultsActivity activity) {
        this.searchResultsActivity = activity;
        this.likedSongsController = new LikedSongsController(activity);
        this.currentUser = FirebaseAuth.getInstance().getCurrentUser();
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        if (view.getId() == R.id.searchResultsMainView) {
            intent = new Intent(view.getContext(), MainActivity.class);
        } else if (view.getId() == R.id.searchResultsLikedView) {
            intent = new Intent(view.getContext(), LikedSongsActivity.class);
        } else if (view.getId() == R.id.searchResultsSettingsView) {
            intent = new Intent(view.getContext(), SettingsActivity.class);
        }

        if (intent != null) {
            view.getContext().startActivity(intent);
        }
    }

    /**
     * Populates a ListView with the list of songs and sets up like functionality.
     * @param listView the ListView to populate
     * @param songs the list of Song objects to display
     */
    public void populateListView(ListView listView, ArrayList<Song> songs) {
        adapter = new ArrayAdapter<Song>(searchResultsActivity, R.layout.song_list_item, R.id.songTitle, songs) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                convertView = super.getView(position, convertView, parent);
                Song song = getItem(position);

                TextView songTitle = convertView.findViewById(R.id.songTitle);
                Button likeButton = convertView.findViewById(R.id.likeButton);

                songTitle.setText(song.getTrackName() + " by " + song.getArtistName());
                updateLikeButton(likeButton, song);

                likeButton.setOnClickListener(v -> {
                    String songDetails = song.getTrackName() + " - " + song.getArtistName();

                    if (!likedSongsController.readFileFromInternalStorage("likes.txt").contains(songDetails)) {
                        likedSongsController.addSongToLiked(songDetails);
                        updateLikeButton(likeButton, song);

                        if (currentUser != null) {
                            String userId = currentUser.getUid();
                            String songKey = sanitizeKey(song.getTrackName()) + "_" + sanitizeKey(song.getArtistName());

                            database.child("users")
                                    .child(userId)
                                    .child("likedSongs")
                                    .child(songKey)
                                    .setValue(songDetails);
                        }
                    }
                });

                return convertView;
            }

            private void updateLikeButton(Button button, Song song) {
                String songDetails = song.getTrackName() + " - " + song.getArtistName();
                if (likedSongsController.readFileFromInternalStorage("likes.txt").contains(songDetails)) {
                    button.setText("Liked");
                } else {
                    button.setText("Like");
                }
            }

            private String sanitizeKey(String input) {
                return input.replaceAll("[.#$\\[\\]]", "_");
            }
        };

        listView.setAdapter(adapter);
    }
}
