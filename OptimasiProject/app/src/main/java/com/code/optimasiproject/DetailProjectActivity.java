package com.code.optimasiproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.code.optimasiproject.adapter.AdapterKeywords;
import com.code.optimasiproject.addon.RecyclerItemClickListener;
import com.code.optimasiproject.getset.GetSetKeyword;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailProjectActivity extends AppCompatActivity {

    private Link link;
    private RecyclerView recyclerView;
    private AdapterKeywords adapterKeywords;
    private List<GetSetKeyword> getSetList;
    private MaterialButton buttonAdd;
    private MaterialTextView textIdProject, textNamaProject;
    private Intent intentGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_keyword);

        link = new Link();

        intentGet = getIntent();

        textIdProject = findViewById(R.id.textIdProject);
        textIdProject.setText("ID Project : " + intentGet.getStringExtra("id_project"));
        textNamaProject = findViewById(R.id.textNamaProject);
        textNamaProject.setText("Project Name : " + intentGet.getStringExtra("name"));

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(view -> {
            Intent intent = new Intent(DetailProjectActivity.this, AddKeywordActivity.class);
            intent.putExtra("id_project", intentGet.getStringExtra("id_project"));
            intent.putExtra("name", intentGet.getStringExtra("name"));
            startActivity(intent);
            finish();
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailProjectActivity.this));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(DetailProjectActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        int itemPosition = recyclerView.getChildLayoutPosition(view);
                        final GetSetKeyword item = getSetList.get(itemPosition);
                        Intent intent = new Intent(DetailProjectActivity.this, UpdateKeywordActivity.class);
                        intent.putExtra("id_project", intentGet.getStringExtra("id_project"));
                        intent.putExtra("name_project", intentGet.getStringExtra("  name_project"));
                        intent.putExtra("id_keyword", item.getId());
                        intent.putExtra("name", item.getKeyword());
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        int itemPosition = recyclerView.getChildLayoutPosition(view);
                        final GetSetKeyword item = getSetList.get(itemPosition);

                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailProjectActivity.this);
                        builder.setCancelable(true);
                        builder.setMessage("Apakah Anda yakin ingin menghapus keyword ini?");
                        builder.setPositiveButton("Ya", (dialog, which) -> deleteKeywords(item.getId()));
                        builder.setNegativeButton("Tidak", (dialog, which) -> {});
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                })
        );

        getKeywords(intentGet.getStringExtra("id_project"));
    }

    public void getKeywords(String id_project){
        final StringRequest request = new StringRequest(Request.Method.GET, link.listKeyword + id_project,
                response -> {
                    GetSetKeywordFunction(response);
                }, error -> {
            Toast.makeText(DetailProjectActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + link.token);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public List<GetSetKeyword> GetSetKeywordFunction(String result) {
        getSetList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("keywords");
            int count = 0;
            String id, user_id, project_id, keyword, explored, explored_at, created_at, updated_at;
            while (count<jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                id = JO.getString("id");
                user_id = JO.getString("user_id");
                project_id = JO.getString("project_id");
                keyword = JO.getString("keyword");
                explored = JO.getString("explored");
                explored_at = JO.getString("explored_at");
                created_at = JO.getString("created_at");
                updated_at = JO.getString("updated_at");

                getSetList.add(new GetSetKeyword(id, user_id, project_id, keyword, explored, explored_at, created_at, updated_at));
                count++;
            }

            adapterKeywords = new AdapterKeywords(getSetList, DetailProjectActivity.this);
            recyclerView.setAdapter(adapterKeywords);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getSetList;
    }

    public void deleteKeywords(String id_keyword){
        final StringRequest request = new StringRequest(Request.Method.DELETE, link.deleteKeyword + id_keyword,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(DetailProjectActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(DetailProjectActivity.this, DetailProjectActivity.class);
                        intent.putExtra("id_project", intentGet.getStringExtra("id_project"));
                        intent.putExtra("name", intentGet.getStringExtra("name"));
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(DetailProjectActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + link.token);
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DetailProjectActivity.this, MainActivity.class));
        finish();
    }
}