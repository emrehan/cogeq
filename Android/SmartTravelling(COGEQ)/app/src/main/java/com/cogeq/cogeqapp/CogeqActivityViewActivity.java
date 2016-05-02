package com.cogeq.cogeqapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CogeqActivityViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cogeq_activity_view);
        int pos = getIntent().getIntExtra( "cogeqActivity", 0);
        TextView name = (TextView)findViewById(R.id.nameTextView);
        name.setText( PrimaryFragment.getInstance().getActivities().get(pos).getName() );
        ImageView iv = (ImageView)findViewById( R.id.activityViewImageView);
        //Picasso.with(this).load( PrimaryFragment.getInstance().getActivities().get(pos).getImageUrl())
        //        .resize(500, 500).centerCrop().error(R.drawable.cross).into(iv);
    }

}
