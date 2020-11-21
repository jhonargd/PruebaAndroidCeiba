package co.com.ceiba.mobile.pruebadeingreso.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import java.util.ArrayList;
import co.com.ceiba.mobile.pruebadeingreso.businessObject.DataBaseBO;
import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.adapter.UserAdapter;
import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.model.UserInterface;
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints;
import co.com.ceiba.mobile.pruebadeingreso.rest.Networking;
import co.com.ceiba.mobile.pruebadeingreso.util.ProgressView;
import co.com.ceiba.mobile.pruebadeingreso.util.Util;

public class MainActivity extends Activity implements UserInterface {
    private ArrayList<User> users;
    private UserAdapter userAdapter;
    RecyclerView recyclerViewSearchResults;
    EditText editTextSearch;
    TextView txtListEmpty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        Util.checkPermission(MainActivity.this);
        getUsers();
    }
    private void init() {
        recyclerViewSearchResults = (RecyclerView)findViewById(R.id.recyclerViewSearchResults);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        txtListEmpty = (TextView) findViewById(R.id.txtListEmpty);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {
            }
            @Override
            public void afterTextChanged(final Editable s) {
                filterSearch(editTextSearch.getText().toString());
            }
        });
    }
    private void getUsers() {
        if(DataBaseBO.existDataBase()){
            loadListUser(DataBaseBO.getListUserDb(editTextSearch.getText().toString()));
        }else{
            loadRestUser();
        }
    }
    private void filterSearch(String search) {
        if(DataBaseBO.getListUserDb(search).size() > 0){
            loadListUser(DataBaseBO.getListUserDb(search));
            txtListEmpty.setVisibility(View.GONE);
        }else{
            if(DataBaseBO.getListUserDb("").size() == 0){
                saveUsers(users);
            }else {
                txtListEmpty.setVisibility(View.VISIBLE);
            }
            loadListUser(DataBaseBO.getListUserDb(search));
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
    private void loadRestUser() {
        if (!Util.checkInternet(MainActivity.this))
        {
            Util.makeToast("No hay conexion a internet", MainActivity.this);
            return;
        }
        ProgressView.Show(MainActivity.this);
        users = new ArrayList<>();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        String url = Endpoints.URL_BASE.concat(Endpoints.GET_USERS);
        Networking.get(url, null, new Networking.ICallback() {
            @Override
            public void onFail(String error) {
                ProgressView.Dismiss();
            }
            @Override
            public void onSuccess(String response) {
                String json = response.toString();
                users =
                        new Gson().fromJson(response.toString(),
                                new TypeToken<ArrayList<User>>(){}.getType());
                if(users.size() > 0){
                    loadListUser(users);
                    ProgressView.Dismiss();
                    saveUsers(users);
                }else{
                    txtListEmpty.setVisibility(View.VISIBLE);
                    ProgressView.Dismiss();
                }
            }
        });
    }
    private void loadListUser(ArrayList<User> users) {
        recyclerViewSearchResults.setHasFixedSize(true);
        recyclerViewSearchResults.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        userAdapter = new UserAdapter(getApplicationContext(), users, MainActivity.this);
        recyclerViewSearchResults.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
    }
    private void saveUsers(ArrayList<User> users) {
        if(DataBaseBO.createDataBase()){
            DataBaseBO.saveUsers(users);
        }
    }
    @Override
    public void loadUser(User user) {
        Intent intent = new Intent(MainActivity.this, PostActivity.class);
        intent.putExtra("user",user);
        startActivity(intent);
    }
}