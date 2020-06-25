package com.swapnil.moviefinder;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView Title, Genre, Director, Actor, Time, Release, Desh, Office, Plot, Rate;
    private EditText movie_name;
    private ImageView poster;
    private Button btn_find;
    private static final String BASE_URL = "http://omdbapi.com/?apikey=7c01d414+&t=";
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Title = findViewById(R.id.txt_title);
        Time = findViewById(R.id.txt_time);
        Genre = findViewById(R.id.txt_genre);
        Director = findViewById(R.id.txt_director);
        Desh = findViewById(R.id.txt_desh);
        Actor = findViewById(R.id.txt_act);
        Plot = findViewById(R.id.txt_plot);
        Release = findViewById(R.id.txt_release);
        Office = findViewById(R.id.txt_box);
        poster = findViewById(R.id.poster);
        btn_find = findViewById(R.id.btn_search);
        movie_name = findViewById(R.id.movie);
        Rate = findViewById(R.id.txt_rating);
        mQueue = Volley.newRequestQueue(this);
        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (movie_name.getText().toString().isEmpty()) {
                    movie_name.setError("INPUT A MOVIE NAME");
                } else {
                    final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, BASE_URL + movie_name.getText().toString(), null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String movie_find = response.getString("Title");
                                String movie_actor = response.getString("Actors");
                                String movie_time = response.getString("Runtime");
                                String movie_director = response.getString("Director");
                                String movie_release = response.getString("Released");
                                String movie_desh = response.getString("Country");
                                String movie_collection = response.getString("BoxOffice");
                                String movie_plot = response.getString("Plot");
                                String movie_genre = response.getString("Genre");
                                String movie_rate = response.getString("imdbRating");
                                String movie_poster = response.getString("Poster");
                                Picasso.get().load(movie_poster).into(poster);
                                Title.setText(movie_find);
                                Time.setText(movie_time);
                                Genre.setText(movie_genre);
                                Director.setText(movie_director);
                                Actor.setText(movie_actor);
                                Release.setText(movie_release);
                                Office.setText(movie_collection);
                                Desh.setText(movie_desh);
                                Plot.setText(movie_plot);
                                Rate.setText(movie_rate);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
                    mQueue.add(request);
                }
            }
        });

    }
}
