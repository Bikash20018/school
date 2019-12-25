package com.example.school;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LinearLayoutManager linearLayout;
    PostAdapter postAdapter;
    List<Item> items = new ArrayList<>();
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    String token ="";
    SpinKitView progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.postList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        linearLayout = new LinearLayoutManager(this);
        postAdapter = new PostAdapter(this,items);
        recyclerView.setAdapter(postAdapter);
        progress = findViewById(R.id.spin_kit);

         recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
             @Override
             public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                 super.onScrollStateChanged(recyclerView, newState);
                 if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                 {
                     isScrolling = true;
                 }
             }

             @Override
             public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                 super.onScrolled(recyclerView, dx, dy);
                 currentItems = linearLayout.getChildCount();
                 totalItems = linearLayout.getItemCount();
                 scrollOutItems = linearLayout.findFirstCompletelyVisibleItemPosition();

                 if(isScrolling && (currentItems + scrollOutItems == totalItems)) {
                     isScrolling = false;
                     getData();

                 }
             }
         });

        getData();
    }





    private void getData()
    {
        String url = BloggerAPI.url + "?key=" +BloggerAPI.key;
        if(token != "")
        {
            url = url+ "&pageToken=" + token;
        }
        if(token == null){
            return;
        }
        progress.setVisibility(View.VISIBLE);
         Call<PostList> postList = BloggerAPI.getService().getPostList(url);
        postList.enqueue(new Callback<PostList>() {
            @Override
            public void onResponse(Call<PostList> call, Response<PostList> response) {
             PostList list = response.body();
             token = list.getNextPageToken();
             items.addAll(list.getItems());
             postAdapter.notifyDataSetChanged();
          //   recyclerView.setAdapter(new PostAdapter(MainActivity.this,list.getItems()));
                Toast.makeText(MainActivity.this,"Sucess",Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<PostList> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error Occured",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

