package com.example.rahul.cookingrecipes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
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

    // get uid
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String uid = firebaseAuth.getCurrentUser().getUid();


    // Database
    private DatabaseReference mDatabaseReference;

    String recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        recipe_name = (EditText)findViewById(R.id.recipe_name);
        recipe_description = (EditText)findViewById(R.id.recipe_description);
        recipe_ingredients = (EditText)findViewById(R.id.recipe_ingredients);
        recipe_instructions = (EditText)findViewById(R.id.recipe_instructions);
        recipe_url = (EditText)findViewById(R.id.recipe_url);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_recipe , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.submit_recipe){
            // submit to database
            final FirebaseUser mUser = firebaseAuth.getCurrentUser();
            mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(mUser.getUid()).push();
            recipeId = mDatabaseReference.getKey();
            mDatabaseReference.child("recipe_name").setValue(recipe_name.getText().toString());
            mDatabaseReference.child("recipe_description").setValue(recipe_description.getText().toString());
            mDatabaseReference.child("recipe_ingredients").setValue(recipe_ingredients.getText().toString());
            mDatabaseReference.child("recipe_instructions").setValue(recipe_instructions.getText().toString());
            if(recipe_url.getText().equals("                                                                          ")){
                mDatabaseReference.child("recipe_url").setValue("None");
            }
            else{
                mDatabaseReference.child("recipe_url").setValue(recipe_url.getText().toString());
            }
            Toast.makeText(AddRecipe.this, "Recipe Saved",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddRecipe.this, Dashboard.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
