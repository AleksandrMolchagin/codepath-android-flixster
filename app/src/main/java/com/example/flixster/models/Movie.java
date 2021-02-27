package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {
    int movieID;
    String posterPath;
    String backdropPath;
    String title;
    String overview;
    String releaseDate;
    double rating;
    //empty constructor needed by Parcel Library
    public Movie(){}

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        movieID = jsonObject.getInt("id");
        releaseDate = jsonObject.getString("release_date");
    }
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }
    public String getPosterPath() {
        return String.format ("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format ("https://image.tmdb.org/t/p/w780/%s", backdropPath);
    }
    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }

    public int getMovieID() {
        return movieID;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
