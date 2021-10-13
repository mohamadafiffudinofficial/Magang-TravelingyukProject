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

public class UpdateProjectActivity extends AppCompatActivity {

    private Link link;
    private TextInputEditText textNamaProject, textIdProject;
    private MaterialButton buttonUpdate, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_project);

        link = new Link();

        Intent intentAmbil = getIntent();

        textNamaProject = findViewById(R.id.textNamaProject);
        textNamaProject.setText(intentAmbil.getStringExtra("name"));
        textIdProject = findViewById(R.id.textIdProject);
        textIdProject.setText(intentAmbil.getStringExtra("id_project"));
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(view -> {
            startActivity(new Intent(UpdateProjectActivity.this, MainActivity.class));
            finish();
        });
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(view -> updateProjects());
    }

    public void updateProjects(){
        final StringRequest request = new StringRequest(Request.Method.PUT, link.updateProjects + textIdProject.getText().toString(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(UpdateProjectActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        startActivity(new Intent(UpdateProjectActivity.this, MainActivity.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(UpdateProjectActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
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
        startActivity(new Intent(UpdateProjectActivity.this, MainActivity.class));
        finish();
    }
}