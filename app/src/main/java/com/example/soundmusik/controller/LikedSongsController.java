package com.example.soundmusik.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.soundmusik.MainActivity;
import com.example.soundmusik.R;
import com.example.soundmusik.SearchResultsActivity;
import com.example.soundmusik.SettingsActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for handling liked songs functionality.
 *  LikedSongsController controls the users liked songs. Allows
 *  for adding, removing, and overall writing to users liked songs.
 *  @author Ryan Diaz
 *
 */
public class LikedSongsController implements View.OnClickListener {
    private Context context; // Use a generic Context
    private AssetManager manager;

    public LikedSongsController(Context context) {
        this.context = context;
        manager = context.getAssets();
//        populateListView("likes.txt");
    }
    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.likedSongsMainView) {
            intent = new Intent(view.getContext(), MainActivity.class);
            view.getContext().startActivity(intent);
        } else if (view.getId() == R.id.likedSongsResultsView) {
            intent = new Intent(view.getContext(), SearchResultsActivity.class);
            view.getContext().startActivity(intent);
        } else if (view.getId() == R.id.likedSongsSettingsView) {
            intent = new Intent(view.getContext(), SettingsActivity.class);
            view.getContext().startActivity(intent);
        }
    }

    /**
     * Reads the content of a file from internal storage and returns it as a list of strings.
     * @param fileName the name of the file to read
     * @return a List of strings containing the file content
     */
    public List<String> readFileFromInternalStorage(String fileName) {
        List<String> fileContent = new ArrayList<>();
        try (FileInputStream fis = context.openFileInput(fileName);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                fileContent.add(line);
            }
        } catch (IOException e) {
            Log.e("FileReadError", "Error reading file", e);
        }
        return fileContent;
    }

    /**
     * Populates the ListView in the given Context.
     * @param fileName the name of the file containing the list data
     */
    public void populateListView(String fileName) {
        List<String> fileContent = readFileFromInternalStorage(fileName);
        ListView listView = (ListView) ((Activity)context).findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_expandable_list_item_1,
                fileContent
        );
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedSong = adapter.getItem(position);
                if (selectedSong != null) {
                    removeSongFromLiked(selectedSong);
                    // Refresh ListView
                    adapter.remove(selectedSong);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Adds a song to the liked songs list.
     * @param song the song to add
     */
    public void addSongToLiked(String song) {
        List<String> fileContent = readFileFromInternalStorage("likes.txt");
        if (!fileContent.contains(song)) {
            fileContent.add(song);
            writeFileToInternalStorage("likes.txt", fileContent);
        }
    }

    /**
     * Removes a song from the liked songs list.
     * @param song the song to remove
     */
    public void removeSongFromLiked(String song) {
        List<String> fileContent = readFileFromInternalStorage("likes.txt");
        if (fileContent.contains(song)) {
            fileContent.remove(song);
            writeFileToInternalStorage("likes.txt", fileContent);
        }
    }

    /**
     * Writes a list of strings to a file in internal storage.
     * @param fileName the name of the file to write
     * @param content the content to write to the file
     */
    public void writeFileToInternalStorage(String fileName, List<String> content) {
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            for (String line : content) {
                fos.write((line + "\n").getBytes());
            }
        } catch (IOException e) {
            Log.e("FileWriteError", "Error writing file", e);
        }
    }
}