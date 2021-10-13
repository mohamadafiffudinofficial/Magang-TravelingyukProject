package com.code.optimasiproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.code.optimasiproject.adapter.AdapterProjects;
import com.code.optimasiproject.addon.RecyclerItemClickListener;
import com.code.optimasiproject.getset.GetSetProjects;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Link link;
    private RecyclerView recyclerView;
    private AdapterProjects adapterProjects;
    private List<GetSetProjects> getSetList;
    private MaterialButton buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        link = new Link();

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, AddProjectActivity.class));
            finish();
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(MainActivity.this, recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        int itemPosition = recyclerView.getChildLayoutPosition(view);
                        final GetSetProjects item = getSetList.get(itemPosition);
                        final String[] pilihan = new String[]{
                                "Update",
                                "List Keyword"
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("Pilihan");
                        builder.setSingleChoiceItems(
                                pilihan,
                                -1,
                                (dialogInterface, i) -> {
                                    if(i == -1){
                                        Toast.makeText(MainActivity.this, "Pilih salah satu item.", Toast.LENGTH_LONG).show();
                                    }else{
                                        String selectedItem = Arrays.asList(pilihan).get(i);

                                        Intent intent;
                                        if(selectedItem.equals("Update")){
                                            intent = new Intent(MainActivity.this, UpdateProjectActivity.class);
                                        }else{
                                            intent = new Intent(MainActivity.this, DetailProjectActivity.class);
                                        }
                                        intent.putExtra("id_project", item.getId());
                                        intent.putExtra("name", item.getNama());
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                        builder.setNegativeButton("Cancel", (dialogInterface, i) -> {

                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        int itemPosition = recyclerView.getChildLayoutPosition(view);
                        final GetSetProjects item = getSetList.get(itemPosition);

                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setCancelable(true);
                        builder.setMessage("Apakah Anda yakin ingin menghapus project ini?");
                        builder.setPositiveButton("Ya", (dialog, which) -> deleteProjects(item.getId()));
                        builder.setNegativeButton("Tidak", (dialog, which) -> {});
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                })
        );

        getProjects();
    }

    public void getProjects(){
        final StringRequest request = new StringRequest(Request.Method.GET, link.listProjects,
                response -> {
                    GetSetProjectsFunction(response);
                }, error -> {
            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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

    public List<GetSetProjects> GetSetProjectsFunction(String result) {
        getSetList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);
            int count = 0;
            String id, user_id, name, created_at, updated_at;
            while (count<jsonArray.length()){
                JSONObject JO = jsonArray.getJSONObject(count);
                id = JO.getString("id");
                user_id = JO.getString("user_id");
                name = JO.getString("name");
                created_at = JO.getString("created_at");
                updated_at = JO.getString("updated_at");

                getSetList.add(new GetSetProjects(id, user_id, name, created_at, updated_at));
                count++;
            }

            adapterProjects = new AdapterProjects(getSetList, MainActivity.this);
            recyclerView.setAdapter(adapterProjects);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getSetList;
    }

    public void deleteProjects(String id_project){
        final StringRequest request = new StringRequest(Request.Method.DELETE, link.deleteProjects + id_project,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this, MainActivity.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
}