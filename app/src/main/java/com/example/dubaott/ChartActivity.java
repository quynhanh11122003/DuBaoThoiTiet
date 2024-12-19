//package com.example.dubaott;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.content.Intent;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.github.mikephil.charting.charts.LineChart;
//import com.github.mikephil.charting.components.Description;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.components.YAxis;
//import com.github.mikephil.charting.data.Entry;
//import com.github.mikephil.charting.data.LineData;
//import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ChartActivity extends AppCompatActivity {
//
//    private LineChart lineChart;
//    ImageView imgback1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chart);
//        Anhxa();
//
//        lineChart = findViewById(R.id.lineChart);
//
//        // Nhận dữ liệu từ MainActivity2 thông qua Intent
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra("maxTemperatures") && intent.hasExtra("minTemperatures")) {
//            float[] maxTemperaturesArray = intent.getFloatArrayExtra("maxTemperatures");
//            float[] minTemperaturesArray = intent.getFloatArrayExtra("minTemperatures");
//
//            // Khởi tạo danh sách dữ liệu nhiệt độ tối đa và tối thiểu
//            ArrayList<Float> maxTemperatures = convertFloatArrayToList(maxTemperaturesArray);
//            ArrayList<Float> minTemperatures = convertFloatArrayToList(minTemperaturesArray);
//
//            // Khởi tạo danh sách xValues
//            ArrayList<String> xValues = new ArrayList<>();
//            xValues.add("Monday");
//            xValues.add("Tuesday");
//            xValues.add("Wednesday");
//            xValues.add("Thursday");
//            xValues.add("Friday");
//            xValues.add("Saturday");
//            xValues.add("Sunday");
//
//            if (maxTemperatures.size() >= 7 && minTemperatures.size() >= 7) {
//                List<Float> maxTemperaturesShortened = maxTemperatures.subList(0, 7);
//                List<Float> minTemperaturesShortened = minTemperatures.subList(0, 7);
//                setupChart(xValues, maxTemperaturesShortened, minTemperaturesShortened);
//            } else {
//                // Xử lý khi kích thước không đủ
//            }
//        } else {
//            // Xử lý khi không có dữ liệu truyền qua Intent
//        }
//
//        imgback1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//
//    }
//
//    private void Anhxa() {
//        imgback1 = (ImageView) findViewById(R.id.imageviewBack1);
//    }
//
//    // Phương thức chuyển đổi mảng float thành ArrayList<Float>
//    private ArrayList<Float> convertFloatArrayToList(float[] array) {
//        ArrayList<Float> list = new ArrayList<>();
//        for (float value : array) {
//            list.add(value);
//        }
//        return list;
//    }
//
//    // Phương thức thiết lập biểu đồ
//    private void setupChart(List<String> xValues, List<Float> maxTemperatures, List<Float> minTemperatures) {
//        Description description = new Description();
//        description.setText("Temperature Records");
//        description.setPosition(150f, 15f);
//        lineChart.setDescription(description);
//        lineChart.getAxisRight().setDrawLabels(false);
//
//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
//        xAxis.setLabelCount(xValues.size());
//        xAxis.setGranularity(1f);
//
//        YAxis yAxis = lineChart.getAxisLeft();
//        yAxis.setAxisMinimum(0f); // Chỉnh giá trị tối thiểu trên trục y
//        yAxis.setAxisMaximum(100f); // Chỉnh giá trị tối đa trên trục y
//        yAxis.setAxisLineWidth(2f);
//        yAxis.setAxisLineColor(Color.BLACK);
//        yAxis.setLabelCount(10);
//
//        List<Entry> maxEntries = new ArrayList<>();
//        for (int i = 0; i < maxTemperatures.size(); i++) {
//            maxEntries.add(new Entry(i, maxTemperatures.get(i)));
//        }
//
//        List<Entry> minEntries = new ArrayList<>();
//        for (int i = 0; i < minTemperatures.size(); i++) {
//            minEntries.add(new Entry(i, minTemperatures.get(i)));
//        }
//
//        LineDataSet maxDataSet = new LineDataSet(maxEntries, "Highest Temperature");
//        maxDataSet.setColor(Color.RED);
//
//        LineDataSet minDataSet = new LineDataSet(minEntries, "Lowest Temperature");
//        minDataSet.setColor(Color.BLUE);
//
//        LineData lineData = new LineData(maxDataSet, minDataSet);
//
//        lineChart.setData(lineData);
//
//        lineChart.invalidate();
//    }
//}

package com.example.dubaott;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChartActivity extends AppCompatActivity {

    private LineChart lineChart;
    ImageView imgback1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        Anhxa();

        lineChart = findViewById(R.id.lineChart);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("maxTemperatures") && intent.hasExtra("minTemperatures")) {
            float[] maxTemperaturesArray = intent.getFloatArrayExtra("maxTemperatures");
            float[] minTemperaturesArray = intent.getFloatArrayExtra("minTemperatures");

            ArrayList<Float> maxTemperatures = convertFloatArrayToList(maxTemperaturesArray);
            ArrayList<Float> minTemperatures = convertFloatArrayToList(minTemperaturesArray);

            ArrayList<String> dayOfWeekList = getDayOfWeekList();

            List<Float> maxTemperaturesShortened = maxTemperatures.subList(0, 7);
            List<Float> minTemperaturesShortened = minTemperatures.subList(0, 7);

            setupChart(dayOfWeekList, maxTemperaturesShortened, minTemperaturesShortened);
        } else {

        }

        imgback1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void Anhxa() {
        imgback1 = findViewById(R.id.imageviewBack1);
    }

    private ArrayList<Float> convertFloatArrayToList(float[] array) {
        ArrayList<Float> list = new ArrayList<>();
        for (float value : array) {
            list.add(value);
        }
        return list;
    }

    private ArrayList<String> getDayOfWeekList() {
        ArrayList<String> dayOfWeekList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        for (int i = 0; i < 7; i++) {
            dayOfWeekList.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
        return dayOfWeekList;
    }

    private void setupChart(List<String> xValues, List<Float> maxTemperatures, List<Float> minTemperatures) {
        Description description = new Description();
        description.setText("Temperature Records");
        description.setPosition(150f, 15f);
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setLabelCount(xValues.size());
        xAxis.setGranularity(1f);

        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(50f);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        List<Entry> maxEntries = new ArrayList<>();
        for (int i = 0; i < maxTemperatures.size(); i++) {
            maxEntries.add(new Entry(i, maxTemperatures.get(i)));
        }

        List<Entry> minEntries = new ArrayList<>();
        for (int i = 0; i < minTemperatures.size(); i++) {
            minEntries.add(new Entry(i, minTemperatures.get(i)));
        }

        LineDataSet maxDataSet = new LineDataSet(maxEntries, "Highest Temperature");
        maxDataSet.setColor(Color.RED);

        LineDataSet minDataSet = new LineDataSet(minEntries, "Lowest Temperature");
        minDataSet.setColor(Color.BLUE);

        LineData lineData = new LineData(maxDataSet, minDataSet);

        lineChart.setData(lineData);

        lineChart.invalidate();
    }
}

