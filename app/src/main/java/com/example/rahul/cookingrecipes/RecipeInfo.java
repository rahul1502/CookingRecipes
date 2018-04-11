package com.example.rahul.cookingrecipes;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecipeInfo extends AppCompatActivity {

    private String recipe_id;

    private TextView recipe_name;
    private TextView recipe_description;
    private TextView recipe_ingredients;
    private TextView recipe_instructions;
    private Button link;

    private RatingBar ratingBar;

    private DatabaseReference mDatabaseReference;

    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private String uid = mUser.getUid();
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);

        recipe_name = (TextView)findViewById(R.id.recipe_name);
        recipe_description = (TextView)findViewById(R.id.recipe_description);
        recipe_ingredients = (TextView)findViewById(R.id.recipe_ingredients);
        recipe_instructions = (TextView)findViewById(R.id.recipe_instructions);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);
        link = (Button)findViewById(R.id.link);

        ratingBar.setIsIndicator(true); // https://stackoverflow.com/questions/26441098/rating-bar-view-only-on-android

        Intent intent = getIntent();
        recipe_id = intent.getStringExtra("recipe_id");

        // set database values
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(uid).child(recipe_id);
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipe_name.setText(dataSnapshot.child("recipe_name").getValue().toString());
                ratingBar.setRating(dataSnapshot.child("rating").getValue(Float.class));
                recipe_description.setText(dataSnapshot.child("recipe_description").getValue().toString());
                recipe_ingredients.setText(dataSnapshot.child("recipe_ingredients").getValue().toString());
                recipe_instructions.setText(dataSnapshot.child("recipe_instructions").getValue().toString());
                url = dataSnapshot.child("recipe_url").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!url.contains("www.") && (!url.contains("http://") && (!url.contains("https://")))){
                    Toast.makeText(RecipeInfo.this, "Not a valid URL",Toast.LENGTH_SHORT).show();
                }
                else{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }

            }
        });



    }
}
