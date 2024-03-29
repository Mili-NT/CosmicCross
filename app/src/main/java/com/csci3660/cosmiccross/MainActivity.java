package com.csci3660.cosmiccross;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.csci3660.cosmiccross.viewmodels.ColorViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Random;

/*
TODO: HIGH PRIORITY: Allow for intersecting words
    - WordCells must be able to be part of two parent words
    - Selection logic not equipped yet to handle multi-word cells
TODO: UI polishing
    - Implement a good looking placeholder grid and maybe animate the swap
    - Figure out what to do with the FAB when it overlaps with the RecyclerView
        - hideFAB() disabled until then.
    - Animate word highlighting somehow?
TODO: Replace all usages of Math.random with Random.nextInt()
TODO: Implement variable length and count of words
TODO: Change the font and spacing for the word bank
TODO: Change the text color of the grid letters to complement the selected highlight color
TODO?: Variable grid sizes
*/
public class MainActivity extends AppCompatActivity {
    private int starCount = 0;
    private final Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ViewModelProvider(this).get(ColorViewModel.class);
        //nav bar
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(navView, navController);
        }
        addStarsWithDelay();
    }

    //Sporadically spread stars out
    private int getRandomPosition(int maximumPosition) {
        return (int) (Math.random() * maximumPosition);
    }

    //Get random duration for twinkling animation
    private int getRandomDuration() {
        return (int) ((Math.random() * 2000)+ 1000);
    }

    //Random delay so stars appear staggered
    private int getRandomDelay() {
        return random.nextInt(1000);
    }

    //Adds star with delay between stars
    private void addStarsWithDelay() {
        final Handler handler = new Handler(Looper.getMainLooper());
        final int delayMillis = 200;
        final FrameLayout container = findViewById(R.id.star_container); // Your FrameLayout

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addStar(container);
                starCount++;

                if (starCount < 100) {
                    handler.postDelayed(this, delayMillis);
                }
            }
        }, delayMillis);
    }

    //adds star to the background and makes it twinkle
    private void addStar(FrameLayout container) {
        ImageView star = new ImageView(this);
        star.setImageResource(R.drawable.star_shape); // Single star drawable

        int duration = getRandomDuration();
        int delay = getRandomDelay();

        //sets size of stars
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                WRAP_CONTENT,
                WRAP_CONTENT
        );
        View navHostFrag = this.findViewById(R.id.nav_host_fragment);
        params.setMargins(getRandomPosition(navHostFrag.getWidth()), getRandomPosition(navHostFrag.getHeight()), 0, 0);
        star.setLayoutParams(params);

        //Makes stars twinkle
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.star_fade);
        animation.setDuration(duration);
        animation.setStartOffset(delay);
        star.startAnimation(animation);

        container.addView(star);
    }
}

