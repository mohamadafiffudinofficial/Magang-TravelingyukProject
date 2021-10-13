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

public class AddProjectActivity extends AppCompatActivity {

    private Link link;
    private TextInputEditText textNamaProject;
    private MaterialButton buttonAdd, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        link = new Link();

        textNamaProject = findViewById(R.id.textNamaProject);
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(view -> {
            startActivity(new Intent(AddProjectActivity.this, MainActivity.class));
            finish();
        });
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(view -> addProjects());
    }

    public void addProjects(){
        final StringRequest request = new StringRequest(Request.Method.POST, link.addProjects,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(AddProjectActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AddProjectActivity.this, MainActivity.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(AddProjectActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("project", textNamaProject.getText().toString());
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
        startActivity(new Intent(AddProjectActivity.this, MainActivity.class));
        finish();
    }
}