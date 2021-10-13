package com.code.optimasiproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddKeywordActivity extends AppCompatActivity {

    private Link link;
    private TextInputEditText textNamaKeyword;
    private MaterialButton buttonAdd, buttonBack;
    private Intent intentGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_keyword);

        link = new Link();

        intentGet = getIntent();

        textNamaKeyword = findViewById(R.id.textNamaKeyword);
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(view -> {
            Intent intent = new Intent(AddKeywordActivity.this, DetailProjectActivity.class);
            intent.putExtra("id_project", intentGet.getStringExtra("id_project"));
            intent.putExtra("name", intentGet.getStringExtra("name"));
            startActivity(intent);
            finish();
        });
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(view -> addKeyword(intentGet.getStringExtra("id_project"), intentGet.getStringExtra("name")));
    }

    public void addKeyword(String id_project, String nama_project){
        final StringRequest request = new StringRequest(Request.Method.POST, link.addKeyword,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(AddKeywordActivity.this, "Sukses", Toast.LENGTH_LONG).show();
                        //Toast.makeText(AddKeywordActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddKeywordActivity.this, DetailProjectActivity.class);
                        intent.putExtra("id_project", id_project);
                        intent.putExtra("name", nama_project);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(AddKeywordActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("project_id", id_project);
                params.put("keyword", textNamaKeyword.getText().toString());
                params.put("sources", "https://myandroidapps.com/");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
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
        Intent intent = new Intent(AddKeywordActivity.this, DetailProjectActivity.class);
        intent.putExtra("id_project", intentGet.getStringExtra("id_project"));
        intent.putExtra("name", intentGet.getStringExtra("name"));
        startActivity(intent);
        finish();
    }
}