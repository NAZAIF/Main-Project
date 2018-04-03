package com.example.android.projtest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ScoreActivity extends AppCompatActivity {

    ProgressBar progressBar;
    DatabaseReference teamsRef = FirebaseDatabase.getInstance().getReference().child("teams");
    ImageView teamImage;
    TextView textAway, textHome;
    String homePerc, awayPerc, teamName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        teamImage = (ImageView) findViewById(R.id.teamImage);
        textAway = (TextView) findViewById(R.id.textAwayPerc);
        textHome = (TextView) findViewById(R.id.textHomePerc);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        int indexNo = getIntent().getIntExtra("index", 0);
        teamName = getIntent().getStringExtra(getString(R.string.team_name_str));
        setTitle(teamName);


        setImageView(indexNo);

        if (!isOnline())
            Toast.makeText(this, getString(R.string.checkNetStr), Toast.LENGTH_SHORT).show();


        setValues();
        progressBar.setVisibility(View.GONE);

    }

    private void setValues() {
        DatabaseReference indiTeam = teamsRef.child(teamName);
        indiTeam.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                homePerc = dataSnapshot.child("home").getValue().toString();
                awayPerc = dataSnapshot.child("away").getValue().toString();

                textAway.setText(awayPerc + " %");
                textHome.setText(homePerc + " %");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void setImageView(int index) {
        Picasso.with(this)
                .load(LogoAddress.imageUrls[index])
                .placeholder(R.drawable.placeholder_land)
                .error(R.drawable.error)
                .into(teamImage);
    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
