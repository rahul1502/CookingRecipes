package com.example.rahul.cookingrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditRecipe extends AppCompatActivity {

    private EditText recipe_name;
    private EditText recipe_description;
    private EditText recipe_ingredients;
    private EditText recipe_instructions;
    private EditText recipe_url;

    private Button clear_name;
    private Button clear_description;
    private Button clear_ingredients;
    private Button clear_instructions;
    private Button clear_url;
    private Button updateRecipe;

    private RatingBar ratingBar;

    private String recipe_id;

    private DatabaseReference mDatabaseReference;

    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private String uid = mUser.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        recipe_name = (EditText)findViewById(R.id.recipe_name);
        recipe_description = (EditText)findViewById(R.id.recipe_description);
        recipe_ingredients = (EditText)findViewById(R.id.recipe_ingredients);
        recipe_instructions = (EditText)findViewById(R.id.recipe_instructions);
        recipe_url = (EditText)findViewById(R.id.recipe_url);
        clear_name = (Button)findViewById(R.id.clear_name);
        clear_description = (Button)findViewById(R.id.clear_description);
        clear_ingredients = (Button)findViewById(R.id.clear_ingredients);
        clear_instructions = (Button)findViewById(R.id.clear_instructions);
        clear_url = (Button)findViewById(R.id.clear_url);
        updateRecipe = (Button)findViewById(R.id.updateRecipe);
        ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        recipe_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear_name.setVisibility(View.VISIBLE);
                clear_description.setVisibility(View.INVISIBLE);
                clear_ingredients.setVisibility(View.INVISIBLE);
                clear_instructions.setVisibility(View.INVISIBLE);
                clear_url.setVisibility(View.INVISIBLE);

            }
        });
        recipe_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear_name.setVisibility(View.INVISIBLE);
                clear_description.setVisibility(View.VISIBLE);
                clear_ingredients.setVisibility(View.INVISIBLE);
                clear_instructions.setVisibility(View.INVISIBLE);
                clear_url.setVisibility(View.INVISIBLE);
            }
        });
        recipe_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear_name.setVisibility(View.INVISIBLE);
                clear_description.setVisibility(View.INVISIBLE);
                clear_ingredients.setVisibility(View.VISIBLE);
                clear_instructions.setVisibility(View.INVISIBLE);
                clear_url.setVisibility(View.INVISIBLE);
            }
        });
        recipe_instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear_name.setVisibility(View.INVISIBLE);
                clear_description.setVisibility(View.INVISIBLE);
                clear_ingredients.setVisibility(View.INVISIBLE);
                clear_instructions.setVisibility(View.VISIBLE);
                clear_url.setVisibility(View.INVISIBLE);
            }
        });
        recipe_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear_name.setVisibility(View.INVISIBLE);
                clear_description.setVisibility(View.INVISIBLE);
                clear_ingredients.setVisibility(View.INVISIBLE);
                clear_instructions.setVisibility(View.INVISIBLE);
                clear_url.setVisibility(View.VISIBLE);
            }
        });


        clear_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe_name.setText("");
            }
        });
        clear_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe_description.setText("");
            }
        });
        clear_ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe_ingredients.setText("");
            }
        });
        clear_instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe_instructions.setText("");
            }
        });
        clear_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recipe_url.setText("");
            }
        });

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
                recipe_url.setText(dataSnapshot.child("recipe_url").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        updateRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(uid).child(recipe_id);
                mDatabaseReference.child("recipe_name").setValue(recipe_name.getText().toString());
                mDatabaseReference.child("rating").setValue(ratingBar.getRating());
                mDatabaseReference.child("recipe_description").setValue(recipe_description.getText().toString());
                mDatabaseReference.child("recipe_ingredients").setValue(recipe_ingredients.getText().toString());
                mDatabaseReference.child("recipe_instructions").setValue(recipe_instructions.getText().toString());
                mDatabaseReference.child("recipe_url").setValue(recipe_url.getText().toString());
                Toast.makeText(EditRecipe.this, "Recipe Updated",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditRecipe.this, Dashboard.class));
            }
        });


    }
}
