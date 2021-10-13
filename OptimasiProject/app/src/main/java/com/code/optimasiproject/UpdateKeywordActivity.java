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

public class UpdateKeywordActivity extends AppCompatActivity {

    private Link link;
    private TextInputEditText textNamaKeyword, textIdKeyword;
    private MaterialButton buttonUpdate, buttonBack;
    private Intent intentAmbil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_keyword);

        link = new Link();

        intentAmbil = getIntent();

        textNamaKeyword = findViewById(R.id.textNamaKeyword);
        textNamaKeyword.setText(intentAmbil.getStringExtra("name"));
        textIdKeyword = findViewById(R.id.textIdKeyword);
        textIdKeyword.setText(intentAmbil.getStringExtra("id_keyword"));
        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(view -> {
            startActivity(new Intent(UpdateKeywordActivity.this, MainActivity.class));
            finish();
        });
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonUpdate.setOnClickListener(view -> updateKeyword(intentAmbil.getStringExtra("id_project"), intentAmbil.getStringExtra("name")));
    }

    public void updateKeyword(String project_id, String nama_project){
        final StringRequest request = new StringRequest(Request.Method.PUT, link.updateKeyword + textIdKeyword.getText().toString(),
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        //Toast.makeText(UpdateKeywordActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        Toast.makeText(UpdateKeywordActivity.this, "Success", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(UpdateKeywordActivity.this, DetailProjectActivity.class);
                        intent.putExtra("id_project", project_id);
                        intent.putExtra("name", nama_project);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(UpdateKeywordActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("project_id", project_id);
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
        Intent intent = new Intent(UpdateKeywordActivity.this, DetailProjectActivity.class);
        intent.putExtra("id_project", intentAmbil.getStringExtra("id_project"));
        intent.putExtra("name", intentAmbil.getStringExtra("name"));
        startActivity(intent);
        finish();
    }
}