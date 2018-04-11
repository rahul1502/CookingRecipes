package com.example.rahul.cookingrecipes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRecipe extends AppCompatActivity {


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
    private Button addRecipe;
    private RatingBar ratingBar;

    // get uid
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String uid = firebaseAuth.getCurrentUser().getUid();


    // Database
    private DatabaseReference mDatabaseReference;

    private String recipeId;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

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
        addRecipe = (Button)findViewById(R.id.addRecipe);
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

        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // submit to database
                final FirebaseUser mUser = firebaseAuth.getCurrentUser();
                mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(mUser.getUid()).push();
                recipeId = mDatabaseReference.getKey();
                mDatabaseReference.child("recipe_name").setValue(recipe_name.getText().toString());
                mDatabaseReference.child("rating").setValue(ratingBar.getRating());
                mDatabaseReference.child("recipe_description").setValue(recipe_description.getText().toString());
                mDatabaseReference.child("recipe_ingredients").setValue(recipe_ingredients.getText().toString());
                mDatabaseReference.child("recipe_instructions").setValue(recipe_instructions.getText().toString());
                url = recipe_url.getText().toString();
                if(url.contains("www.")){
                    mDatabaseReference.child("recipe_url").setValue(recipe_url.getText().toString());
                }
                else{
                    mDatabaseReference.child("recipe_url").setValue("None");
                }
                Toast.makeText(AddRecipe.this, "Recipe Saved",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddRecipe.this, Dashboard.class));

            }
        });
    }

}
