package com.example.android.projtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements LogoAdapter.ClickListener {

    LogoAdapter logoAdapter;
    RecyclerView rcv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcv = (RecyclerView) findViewById(R.id.rcv);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcv.setLayoutManager(layoutManager);
        rcv.setHasFixedSize(true);
        setRecyclerView(rcv);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void setRecyclerView(RecyclerView rcv) {
        if (!isOnline()) {
            Toast.makeText(this, getString(R.string.checkNetStr), Toast.LENGTH_SHORT).show();
        }
        logoAdapter = new LogoAdapter(this, this);
        rcv.setAdapter(logoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_info) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnViewClick(int index) {
        Intent scoreActivityIntent = new Intent(this, ScoreActivity.class);
        scoreActivityIntent.putExtra(getString(R.string.team_name_str), LogoAddress.teamNames[index]);
        scoreActivityIntent.putExtra("index",index);
        startActivity(scoreActivityIntent);
    }
}
