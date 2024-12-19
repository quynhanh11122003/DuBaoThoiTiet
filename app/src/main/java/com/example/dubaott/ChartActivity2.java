package com.example.dubaott;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.NavUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ChartActivity2 extends AppCompatActivity {

    private LineChart lineChart;
    private String city;

    ImageView imgback2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart2);

        city = getIntent().getStringExtra("name");

        lineChart = findViewById(R.id.chart);

        imgback2 = findViewById(R.id.imageviewBack2);

        fetchHourlyTemperatureData(city);

        imgback2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavUtils.navigateUpFromSameTask(ChartActivity2.this);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchHourlyTemperatureData(city);
    }

    private void fetchHourlyTemperatureData(String city) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&units=metric&appid=c8a36291833e586791153811c77e3b7a";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<Entry> entries = new ArrayList<>();
                            ArrayList<String> labels = new ArrayList<>();

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("list");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                JSONObject main = jsonObject1.getJSONObject("main");

                                double temp = main.getDouble("temp");
                                String time = jsonObject1.getString("dt_txt");

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                Date date = sdf.parse(time);
                                SimpleDateFormat newSdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
                                String formattedTime = newSdf.format(date);

                                entries.add(new Entry(i, (float) temp));
                                labels.add(formattedTime);
                            }

                            LineDataSet lineDataSet = new LineDataSet(entries, "Hourly Temperature");
                            lineDataSet.setDrawValues(false);
                            lineDataSet.setDrawCircles(false);
                            lineDataSet.setLineWidth(2f);
                            lineDataSet.setColor(getResources().getColor(R.color.colorPrimary));

                            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                            dataSets.add(lineDataSet);

                            LineData lineData = new LineData(dataSets);

                            XAxis xAxis = lineChart.getXAxis();
                            xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                            xAxis.setGranularity(1f);
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                            YAxis yAxisRight = lineChart.getAxisRight();
                            yAxisRight.setEnabled(false);

                            lineChart.getDescription().setEnabled(false);
                            lineChart.setData(lineData);
                            lineChart.invalidate();

                        } catch (JSONException | java.text.ParseException e) {
                            e.printStackTrace();
                            Toast.makeText(ChartActivity2.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        Toast.makeText(ChartActivity2.this, "City not found or no data available", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
    }
}
