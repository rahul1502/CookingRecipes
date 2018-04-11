package com.example.rahul.cookingrecipes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class Dashboard extends AppCompatActivity {

    private FloatingActionButton add_recipe;
    //private TextView heading;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    List<Recipe> recipeList;

    private EditText searchBar;

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private String gname;
    private String description;
    private String recipeId;
    private Float rating;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        add_recipe = (FloatingActionButton) findViewById(R.id.add_recipe);
        //heading = (TextView)findViewById(R.id.heading);
        recyclerView = (RecyclerView)findViewById(R.id.recipe_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchBar = (EditText) findViewById(R.id.searchBar);

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = mUser.getUid();
        String name = mUser.getDisplayName(); // https://stackoverflow.com/questions/42056333/getting-user-name-lastname-and-id-in-firebase
        String[] fullName = name.split("\\s+");

        setTitle(fullName[0] + "'s Recipes");

        // search feature
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                // https://www.youtube.com/watch?v=OWwOSLfWboY
                filter(editable.toString());
            }
        });


        // add recipes to the list
        recipeList = new ArrayList<>();

        recipeAdapter = new RecipeAdapter(this, recipeList);
        recyclerView.setAdapter(recipeAdapter);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(uid);
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recipeList.clear();
                //https://stackoverflow.com/questions/45168071/android-firebase-retrieve-all-data-in-arraylist-and-randomly-select-string-fro
                for(DataSnapshot ds:dataSnapshot.getChildren()){

                    gname = ds.child("recipe_name").getValue(String.class);
                    description = ds.child("recipe_description").getValue(String.class);
                    rating = ds.child("rating").getValue(Float.class);
                    recipeId = ds.getKey();
                    try{
                        recipeList.add(new Recipe(gname,description,recipeId,rating));
                    }catch (NullPointerException e){
                   }

                }
                Collections.reverse(recipeList); //https://www.tutorialspoint.com/java/util/collections_reverse.htm
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void filter(String text){
        ArrayList<Recipe> filteredList = new ArrayList<>();
        for(Recipe item : recipeList){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
            recipeAdapter.filterList(filteredList);
        }
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

        return super.onOptionsItemSelected(item);
    }




}
