package com.example.colorgame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<ImageView> colorImageViews;
    private List<Integer> selectedColors;
    private Map<Integer, Integer> imageViewToColorMap;

    private int[] grayDrawables = {
            R.drawable.gray1,
            R.drawable.gray2,
            R.drawable.gray3,
            R.drawable.gray4,
            R.drawable.gray5,
            R.drawable.gray6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize lists and map
        colorImageViews = new ArrayList<>();
        selectedColors = new ArrayList<>();
        imageViewToColorMap = new HashMap<>();

        // Add color ImageViews to the list and map
        ImageView color1ImageView = findViewById(R.id.color1);
        colorImageViews.add(color1ImageView);
        imageViewToColorMap.put(color1ImageView.getId(), R.drawable.red);

        ImageView color2ImageView = findViewById(R.id.color2);
        colorImageViews.add(color2ImageView);
        imageViewToColorMap.put(color2ImageView.getId(), R.drawable.orange);

        ImageView color3ImageView = findViewById(R.id.color3);
        colorImageViews.add(color3ImageView);
        imageViewToColorMap.put(color3ImageView.getId(), R.drawable.yellow);

        ImageView color4ImageView = findViewById(R.id.color4);
        colorImageViews.add(color4ImageView);
        imageViewToColorMap.put(color4ImageView.getId(), R.drawable.green);

        ImageView color5ImageView = findViewById(R.id.color5);
        colorImageViews.add(color5ImageView);
        imageViewToColorMap.put(color5ImageView.getId(), R.drawable.blue);

        ImageView color6ImageView = findViewById(R.id.color6);
        colorImageViews.add(color6ImageView);
        imageViewToColorMap.put(color6ImageView.getId(), R.drawable.pink);
        
        // Set click listeners for color ImageViews
        for (final ImageView colorImageView : colorImageViews) {
            colorImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int color = getColorForImageView(colorImageView.getId());
                    // Check if the color is already selected
                    if (!selectedColors.contains(color)) {
                        // If less than 2 colors are selected, add the new color
                        if (selectedColors.size() < 2) {
                            selectedColors.add(color);
                            // Change image to selected color
                            colorImageView.setImageDrawable(getColorDrawable(color));
                        }
                        // If 2 colors are already selected, ignore the click
                    } else {
                        // If the color is already selected, deselect it
                        selectedColors.remove((Integer) color);
                        // Change image to gray
                        colorImageView.setImageDrawable(getColorDrawable(getRandomGrayDrawable()));
                    }
                }
            });
        }

        // Set click listener for the START button
        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startColorChanging();
            }
        });
    }

    private void startColorChanging() {
        // Randomize selected colors
        Collections.shuffle(selectedColors);

        // Change all gray images to the selected colors
        for (int i = 0; i < colorImageViews.size(); i++) {
            colorImageViews.get(i).setImageDrawable(getColorDrawable(selectedColors.get(i % selectedColors.size())));
        }

        // Schedule task to revert images to gray after 2 seconds
        findViewById(R.id.startButton).postDelayed(new Runnable() {
            @Override
            public void run() {
                for (ImageView colorImageView : colorImageViews) {
                    // Revert to gray drawable
                    colorImageView.setImageResource(getRandomGrayDrawable());
                }
            }
        }, 2000);
    }

    // Method to map ImageView IDs to color IDs
    private int getColorForImageView(int imageViewId) {
        Integer color = imageViewToColorMap.get(imageViewId);
        return color != null ? color : getRandomGrayDrawable();
    }

    // Method to get drawable resource for a given color
    private Drawable getColorDrawable(int colorId) {
        return getResources().getDrawable(colorId);
    }

    // Method to get a random gray drawable
    private int getRandomGrayDrawable() {
        return grayDrawables[new java.util.Random().nextInt(grayDrawables.length)];
    }
}
