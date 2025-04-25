package com.example.soundmusik;


import android.app.Notification;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import com.example.soundmusik.controller.SettingsController;
import com.example.soundmusik.model.UserSettings;


/**
 * Activity for managing application settings.
 *  SearchResultsActivity manages and displays the song recommendations.
 *  @author Ashley Adeniyi, Luis Saenz
 */
public class SettingsActivity extends AppCompatActivity {

    View view;
    /**
     * Initializes the SettingsActivity.
     * @param savedInstanceState the saved state of the activity
     */
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SettingsController settingsController = new SettingsController(this);

        view=this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.white);

        Button settingsMainView = findViewById(R.id.settingsMainView);
        Button settingsResultsView = findViewById(R.id.settingsResultsView);
        Button settingsLikedView = findViewById(R.id.settingsLikedView);

        settingsMainView.setOnClickListener(settingsController);
        settingsResultsView.setOnClickListener(settingsController);
        settingsLikedView.setOnClickListener(settingsController);

        updateTextSize();

    }

    /**
     * Changes the background color to blue and saves the color preference.
     * @param v the view that triggered the action
     */
    public void pickBlue(View v){
        int color;
        color = getResources().getColor(R.color.blue, getTheme());
        view.setBackgroundResource(R.color.blue);
        saveColorPreference(color);
    }

    /**
     * Changes the background color to red and saves the color preference.
     * @param v the view that triggered the action
     */
    public void pickRed(View v){
        int color;
        color = getResources().getColor(R.color.red, getTheme());
        view.setBackgroundResource(R.color.red);
        saveColorPreference(color);
    }

    /**
     * Changes the background color to green and saves the color preference.
     * @param v the view that triggered the action
     */
    public void pickGreen(View v){
        int color;
        color = getResources().getColor(R.color.green, getTheme());
        view.setBackgroundResource(R.color.green);
        saveColorPreference(color);
    }

    /**
     * Changes the background color to white and saves the color preference.
     * @param v the view that triggered the action
     */
    public void pickWhite(View v){
        view.setBackgroundResource(R.color.white);
        saveColorPreference(Color.WHITE);
    }


    /**
     * Saves the selected color preference in SharedPreferences and updates the text color
     * of various TextViews based on the user's preference.
     * @param color The selected color to be saved.
     */
    private void saveColorPreference(int color){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("selected_color", color);
        editor.apply();
        updateTextColor();
    }

    /**
     * Updates the text color of various TextViews in the activity based on the user's color preference.
     */
    private void updateTextColor(){
        int textColor = UserSettings.getTextColot(this);

        TextView textView1 = findViewById(R.id.settingsTitle);
        TextView textView2 = findViewById(R.id.subtitleBackgroundColor);
        TextView textView3 = findViewById(R.id.textColor);
        TextView textView4 = findViewById(R.id.textSize);

        textView1.setTextColor(textColor);
        textView2.setTextColor(textColor);
        textView3.setTextColor(textColor);
        textView4.setTextColor(textColor);
    }

    /**
     * Changes the text color to black and saves the color preference.
     * @param view the view that triggered the action
     */
    public void changeTextColorBlack(View view) {
        UserSettings.setColorText(this, Color.BLACK);
        updateTextColor();
    }

    /**
     * Changes the text color to white and saves the color preference.
     * @param view the view that triggered the action
     */
    public void changeTextColorWhite(View view){
        UserSettings.setColorText(this, Color.WHITE);
        updateTextColor();
    }


    /**
     * Updates the text size of various TextViews based on the user's preference.
     */
    private void updateTextSize(){
        // Retrieve the text size from user settings

        float textSize = UserSettings.getTextSize(this);

        // Find the TextViews by their IDs

        TextView textView1 = findViewById(R.id.settingsTitle);
        TextView textView2 = findViewById(R.id.subtitleBackgroundColor);
        TextView textView3 = findViewById(R.id.textColor);
        TextView textView4 = findViewById(R.id.textSize);

        // Set the text size for each TextView

        textView1.setTextSize(textSize);
        textView2.setTextSize(textSize);
        textView3.setTextSize(textSize);
        textView4.setTextSize(textSize);
    }

    /**
     * Increases the text size and saves the size preference.
     * @param view the view that triggered the action
     */
    public void increaseTextSize(View view){
        float newSize = UserSettings.getTextSize(this) + 2.0f;
        UserSettings.setTextSize(this,newSize);
        updateTextSize();
    }

    /**
     * Decreases the text size and saves the size preference.
     * @param view the view that triggered the action
     */
    public void decreaseTextSize(View view){
        float newSize = UserSettings.getTextSize(this) - 2.0f;
        UserSettings.setTextSize(this,newSize);
        updateTextSize();
    }

}