package com.example.school;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class BloggerAPI {
    public static final String key = "AIzaSyBNaIk7r2aBN-WLPI5_T9R5TEIJRWibo00";
    public static final String url = "https://www.googleapis.com/blogger/v3/blogs/6868406295895725270/posts/";

    public static PostService postService =null;
    public static PostService getService() {
        if (postService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            postService = retrofit.create(PostService.class);
        }
        return postService;
    }

    public interface PostService
    {
        @GET
        Call<PostList> getPostList(@Url String url);

    }
}
