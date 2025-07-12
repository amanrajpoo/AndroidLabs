package com.example.androidlabs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "SWAPI";
    private static final String SWAPI_INFO = "https://swapi.info/api/people";
    private static final String SWAPI_DEV  = "https://swapi.dev/api/people/?format=json";

    private ArrayList<StarWarsCharacter> characters = new ArrayList<>();
    private CharacterAdapter adapter;

    @Override
    protected void onCreate(Bundle saved) {
        super.onCreate(saved);

        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        setContentView(isTablet
                ? R.layout.activity_main_constraint
                : R.layout.activity_main);

        ListView list = findViewById(R.id.listViewCharacters);
        adapter = new CharacterAdapter(this, characters);
        list.setAdapter(adapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            StarWarsCharacter c = characters.get(position);
            if (isTablet) {
                // replace fragment in-place
                DetailsFragment frag = new DetailsFragment();
                Bundle args = new Bundle();
                args.putString(DetailsFragment.ARG_NAME,   c.name);
                args.putString(DetailsFragment.ARG_HEIGHT, c.height);
                args.putString(DetailsFragment.ARG_MASS,   c.mass);
                frag.setArguments(args);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_details_container, frag);
                ft.commit();
            } else {
                // launch detail activity
                Intent i = new Intent(this, EmptyActivity.class);
                i.putExtra(DetailsFragment.ARG_NAME,   c.name);
                i.putExtra(DetailsFragment.ARG_HEIGHT, c.height);
                i.putExtra(DetailsFragment.ARG_MASS,   c.mass);
                startActivity(i);
            }
        });

        // kick off the fetch
        new FetchTask().execute();
    }

    private class FetchTask extends AsyncTask<Void,Void,ArrayList<StarWarsCharacter>> {
        @Override
        protected ArrayList<StarWarsCharacter> doInBackground(Void... args) {
            try {
                URL url = new URL(SWAPI_INFO);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setConnectTimeout(5000);
                conn.connect();
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line=rd.readLine())!=null) sb.append(line);
                rd.close();
                String resp = sb.toString().trim();

                JSONArray arr;
                if (resp.startsWith("[")) {
                    arr = new JSONArray(resp);
                } else {
                    JSONObject root = new JSONObject(resp);
                    arr = root.getJSONArray("results");
                }

                ArrayList<StarWarsCharacter> out = new ArrayList<>();
                for (int i=0; i<arr.length(); i++) {
                    JSONObject o = arr.getJSONObject(i);
                    out.add(new StarWarsCharacter(
                            o.getString("name"),
                            o.getString("height"),
                            o.getString("mass")
                    ));
                }
                return out;

            } catch (Exception e) {
                Log.e(TAG, "Fetch failed", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<StarWarsCharacter> result) {
            if (result == null) {
                Toast.makeText(MainActivity.this,
                        "Failed to load data", Toast.LENGTH_SHORT).show();
            } else {
                characters.clear();
                characters.addAll(result);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
