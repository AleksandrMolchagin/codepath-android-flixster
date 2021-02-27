package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class DetailActivity extends YouTubeBaseActivity {

    public static final String YOUTUBE_API_KEY = "AIzaSyD_iBfg_9Qin-Gg5diOCS6pgWb05Jb5pag";
    public static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    TextView tvDetTitle;
    TextView tvDetOverview;
    TextView releaseDate;
    RatingBar ratingBar;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvDetTitle = findViewById(R.id.tvDetTitle);
        tvDetOverview = findViewById(R.id.tvDetOverview);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView = findViewById(R.id.player);
        releaseDate = findViewById(R.id.releaseDate);


        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvDetTitle.setText(movie.getTitle());
        tvDetOverview.setText(movie.getOverview());
        releaseDate.setText(movie.getReleaseDate().replaceAll("-","."));
        ratingBar.setRating((float) movie.getRating());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEOS_URL, movie.getMovieID()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");
                    if (results.length() == 0){
                        return;
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");
                    Log.d("DetailActivityKey",  youtubeKey);
                    initializeYoutube(youtubeKey);
                } catch (JSONException e) {
                    Log.e("DetailActivity", "Failed to Parse JSON", e);
                    e.printStackTrace();
                }
            }

            private void initializeYoutube(String youtubeKey) {
                youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        Log.d("DetailActivity", "onSuccess");
                        youTubePlayer.cueVideo(youtubeKey);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        Log.d("DetailActivity", "onFailure");

                    }
                });
            }
            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {

            }
        });



    }
}