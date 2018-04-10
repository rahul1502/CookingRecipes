package com.example.rahul.cookingrecipes;



// https://www.youtube.com/watch?v=a4o9zFfyIM4


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context mCtx;
    private List<Recipe> recipeList;

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String url;
    private String uid;

    public RecipeAdapter(Context mCtx, List<Recipe> recipeList) {
        this.mCtx = mCtx;
        this.recipeList = recipeList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mCtx);
        View view = layoutInflater.inflate(R.layout.recipe_layout, null);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, final int position) {
        Recipe recipe = recipeList.get(position);
        holder.recipeTitle.setText(recipe.getName());
        holder.recipeDescription.setText(recipe.getDescription());
        holder.recipeId.setText(recipe.getRecipeId());

        FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = mUser.getUid();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child(uid);

        holder.link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        url = dataSnapshot.child(holder.recipeId.getText().toString()).child("recipe_url").getValue().toString();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //https://stackoverflow.com/questions/2201917/how-can-i-open-a-url-in-androids-web-browser-from-my-application
                if(url != null)
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

            }
        });

        holder.deleteRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseReference.child(holder.recipeId.getText().toString()).removeValue();
                recipeList.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(view.getContext(), "Recipe Deleted",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder{

        TextView recipeTitle,recipeDescription,recipeId;
        Button editRecipe,deleteRecipe,link;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            recipeTitle = itemView.findViewById(R.id.recipeTitle);
            recipeDescription = itemView.findViewById(R.id.recipeDescription);
            recipeId = itemView.findViewById(R.id.recipeId);
            editRecipe = itemView.findViewById(R.id.editRecipe);
            deleteRecipe = itemView.findViewById(R.id.deleteRecipe);
            link = itemView.findViewById(R.id.link);
        }
    }

}
