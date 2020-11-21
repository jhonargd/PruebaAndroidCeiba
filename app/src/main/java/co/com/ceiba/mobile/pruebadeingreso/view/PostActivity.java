package co.com.ceiba.mobile.pruebadeingreso.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import java.util.ArrayList;
import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.adapter.PostAdapter;
import co.com.ceiba.mobile.pruebadeingreso.businessObject.DataBaseBO;
import co.com.ceiba.mobile.pruebadeingreso.model.Post;
import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints;
import co.com.ceiba.mobile.pruebadeingreso.rest.Networking;
import co.com.ceiba.mobile.pruebadeingreso.util.ProgressView;
import co.com.ceiba.mobile.pruebadeingreso.util.Util;

public class PostActivity extends Activity {
    private User user;
    private ArrayList<Post> posts;
    private PostAdapter postAdapter;
    public TextView name;
    public TextView phone;
    public TextView email;
    RecyclerView recyclerViewPostsResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        getExtra();
        init();
    }
    private void init() {
        name = (TextView) findViewById(R.id.name);
        name.setText(user.getName().toString());
        phone = (TextView) findViewById(R.id.phone);
        phone.setText(user.getPhone().toString());
        email = (TextView) findViewById(R.id.email);
        email.setText(user.getEmail().toString());
        recyclerViewPostsResults = (RecyclerView) findViewById(R.id.recyclerViewPostsResults);
    }
    private void getExtra() {
        user = (User) getIntent().getSerializableExtra("user");
    }
    @Override
    protected void onStart() {
        super.onStart();
        getPostById();
    }
    private void getPostById() {
        if(DataBaseBO.getPostUser(user.getId()).size() > 0){
            loadListPost(DataBaseBO.getPostUser(user.getId()));
        }else{
            loadRestPost();
        }
    }
    private void loadRestPost() {
        if (!Util.checkInternet(PostActivity.this))
        {
            Util.makeToast("No hay conexion a internet", PostActivity.this);

            return;
        }
        ProgressView.Show(PostActivity.this);
        posts = new ArrayList<>();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = Endpoints.URL_BASE.concat(Endpoints.GET_POST_USER).concat("userId=").concat(user.getId());
        Networking.get(url, null, new Networking.ICallback() {
            @Override
            public void onFail(String error) {
                ProgressView.Dismiss();
            }
            @Override
            public void onSuccess(String response) {
                String json = response.toString();
                posts =
                        new Gson().fromJson(response.toString(),
                                new TypeToken<ArrayList<Post>>(){}.getType());
                if(posts.size() > 0){
                    loadListPost(posts);
                    ProgressView.Dismiss();
                    savePosts(posts);
                }else{
                    Util.makeToast("List is empty.", PostActivity.this);
                    ProgressView.Dismiss();
                }
            }
        });
    }
    private void savePosts(ArrayList<Post> posts) {
        if(DataBaseBO.createDataBase()){
            DataBaseBO.savePosts(posts);
        }
    }
    private void loadListPost(ArrayList<Post> posts) {
        recyclerViewPostsResults.setHasFixedSize(true);
        recyclerViewPostsResults.setLayoutManager(new LinearLayoutManager(PostActivity.this));
        postAdapter = new PostAdapter(getApplicationContext(), posts);
        recyclerViewPostsResults.setAdapter(postAdapter);
        postAdapter.notifyDataSetChanged();
    }
}
