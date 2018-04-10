package com.example.rahul.cookingrecipes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Dashboard extends AppCompatActivity {

    private FloatingActionButton add_recipe;
    private TextView heading;

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        add_recipe = (FloatingActionButton) findViewById(R.id.add_recipe);
        heading = (TextView)findViewById(R.id.heading);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        String name = user.getDisplayName(); // https://stackoverflow.com/questions/42056333/getting-user-name-lastname-and-id-in-firebase
        String[] fullName = name.split("\\s+");
        heading.setText(fullName[0] + "'s Recipes");

    }

    public void addRecipe(View view) {
        startActivity(new Intent(Dashboard.this, AddRecipe.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_signout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Dashboard.this, MainActivity.class));
        }
        else if(item.getItemId() == R.id.search_recipe){
            // search for recipe
        }

        return super.onOptionsItemSelected(item);
    }

}
