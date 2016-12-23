package com.example.guest999.spinner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.guest999.spinner.R.id.spin_work_dept;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    public static ArrayList<String> Service = null;
    public static ArrayList<String> Service_Id = null;
    String service_id;
    Boolean chk_work_dept = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(spin_work_dept);
        Service = new ArrayList<String>();
        Service_Id = new ArrayList<String>();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,Service_Id.get(position),Toast.LENGTH_LONG).show();
                if (spinner.getSelectedItem() == "Please select Work Department") {
                    chk_work_dept = true;
                    //Do nothing.
                } else {
                    chk_work_dept = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        getData();
    }

    private void getData() {
        StringRequest stringRequest = new StringRequest(Config.SPINNER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Service");
                    Log.e("onResponse: ", jsonObject + "");
                    for (int i = 0; i <= jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        String service = jsonObject1.getString(Config.SPINNER_TEXT);
                        service_id =jsonObject1.getString(Config.SPINNER_ID);
                        Service.add(jsonObject1.getString(Config.SPINNER_TEXT));
                        Service_Id.add(jsonObject1.getString(Config.SPINNER_ID));
                       // Service.add("Please select Work Department");
                        spinner.setPrompt("Please select Work Department");
                        spinner.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, Service));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
