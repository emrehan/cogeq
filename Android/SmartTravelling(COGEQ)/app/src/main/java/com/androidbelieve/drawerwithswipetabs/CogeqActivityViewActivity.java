package com.androidbelieve.drawerwithswipetabs;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

public class CogeqActivityViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cogeq_activity_view);
        int pos = getIntent().getIntExtra( "cogeqActivity", 0);
        TextView name = (TextView)findViewById(R.id.nameTextView);
        TextView explanation = (TextView)findViewById(R.id.explanationText);
        name.setText( PrimaryFragment.getInstance().getActivities().get(pos).getName() );
        explanation.setText(PrimaryFragment.getInstance().getActivities().get(pos).getExplanation());
        ImageView iv = (ImageView)findViewById( R.id.activityViewImageView);
        Picasso.with(this).load( PrimaryFragment.getInstance().getActivities().get(pos).getImageUrl())
                .resize(500, 500).centerCrop().error(R.drawable.cross).into(iv);
    }

}
