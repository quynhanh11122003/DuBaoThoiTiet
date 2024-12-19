//package com.example.dubaott;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.content.Intent;
//import android.text.GetChars;
//import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Button;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.lang.String;
//import java.util.Calendar;
//
//
//public class MainActivity2 extends AppCompatActivity {
//    String tenthanhpho = "";
//    ImageView imgback;
//    TextView txtName;
//    ListView lv;
//    Button buttonShowChart;
//    CustomAdapter customAdapter;
//    ArrayList<Thoitiet> mangThoitiet;
//    ArrayList<Float> maxTemperatures;
//    ArrayList<Float> minTemperatures;
//
//    // Phương thức chuyển đổi ArrayList<Float> thành mảng float
//    private float[] convertFloatListToArray(ArrayList<Float> list) {
//        if (list != null) {
//            float[] array = new float[list.size()];
//            for (int i = 0; i < list.size(); i++) {
//                array[i] = list.get(i);
//            }
//            return array;
//        } else {
//            // Xử lý trường hợp list là null
//            return new float[0]; // Trả về mảng float rỗng
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main2);
//        Anhxa();
//
//        maxTemperatures = new ArrayList<>();
//        minTemperatures = new ArrayList<>();
//
//
//        buttonShowChart = findViewById(R.id.buttonShowChart);
//        buttonShowChart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity2.this, ChartActivity.class);
//                intent.putExtra("maxTemperatures", convertFloatListToArray(maxTemperatures));
//                intent.putExtra("minTemperatures", convertFloatListToArray(minTemperatures));
//                startActivity(intent);
//            }
//        });
//
//        Intent intent = getIntent();
//        String city = intent.getStringExtra("name");
//        Log.d("ketqua", "Dữ liệu truyền qua: " + city);
//        if (city.equals("")) {
//            tenthanhpho = "Hanoi";
//            Get7DaysData(tenthanhpho);
//        } else {
//            tenthanhpho = city;
//            Get7DaysData(tenthanhpho);
//        }
//        imgback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
//    }
//
//    private void Anhxa() {
//        imgback = (ImageView) findViewById(R.id.imageviewBack);
//        txtName = (TextView) findViewById(R.id.textviewTenthanhpho);
//        lv = (ListView) findViewById(R.id.listview);
//        mangThoitiet = new ArrayList<Thoitiet>();
//        customAdapter = new CustomAdapter(MainActivity2.this, mangThoitiet);
//        lv.setAdapter(customAdapter);
//    }
//
//    private void Get7DaysData(String data) {
//        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + data + "&units=metric&cnt=40&appid=c8a36291833e586791153811c77e3b7a";
//        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
//                            String name = jsonObjectCity.getString("name");
//                            txtName.setText(name);
//
//                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
//
//                            // Xóa dữ liệu cũ trước khi thêm dữ liệu mới
//                            mangThoitiet.clear();
//                            maxTemperatures.clear(); // Xóa dữ liệu cũ
//                            minTemperatures.clear(); // Xóa dữ liệu cũ
//
//
//                            // Lấy thời điểm hiện tại
//                            long currentTime = System.currentTimeMillis();
//
//                            // Dùng một biến kiểm tra để đảm bảo chỉ lấy một mẫu dữ liệu cho mỗi ngày
//                            boolean[] addedDay = new boolean[7];
//                            int nextDay=0;
//                            for (int i = 0; i < jsonArrayList.length(); i++) {
//                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
//                                long ngayLong = jsonObjectList.getLong("dt") * 1000L; // Chuyển đổi từ giây sang mili giây
//
//                                // Chỉ lấy dữ liệu cho 7 ngày tiếp theo từ thời điểm hiện tại
//                                if (ngayLong >= currentTime && nextDay < 7) {
//                                    Date date = new Date(ngayLong);
//                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
//                                    String Day = simpleDateFormat.format(date);
//
//                                    int dayIndex = getDayIndex(date); // Lấy chỉ số của ngày trong tuần (0-6)
//
//                                    // Chỉ thêm một mẫu dữ liệu cho mỗi ngày
//                                    if (!addedDay[dayIndex]) {
//
//                                        JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("main");
//                                        String max = jsonObjectTemp.getString("temp_max");
//                                        String min = jsonObjectTemp.getString("temp_min");
//
//
//                                        JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
//                                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
//                                        String status = jsonObjectWeather.getString("description");
//                                        String icon = jsonObjectWeather.getString("icon");
//
//                                        mangThoitiet.add(new Thoitiet(Day, status, icon, max, min));
//                                        maxTemperatures.add(Float.parseFloat(max));
//                                        minTemperatures.add(Float.parseFloat(min));
//                                        addedDay[dayIndex] = true;
//                                        nextDay++;
//
//                                        currentTime = ngayLong;
//
//                                    }
//                                }
//
//                                // Khi đã lấy đủ dữ liệu cho 7 ngày tiếp theo, thoát vòng lặp
//                                if (mangThoitiet.size() >= 7) {
//                                    break;
//                                }
//                            }
//
//                            // Sau khi đã lấy dữ liệu từ API và xử lý
//                            // Lấy dữ liệu cho maxTemperatures và minTemperatures
//                            for (int i = 0; i < mangThoitiet.size(); i++) {
//                                Thoitiet thoiTiet = mangThoitiet.get(i);
//                                maxTemperatures.add(Float.parseFloat(thoiTiet.getMaxTemp()));
//                                minTemperatures.add(Float.parseFloat(thoiTiet.getMinTemp()));
//                            }
//
//                            customAdapter.notifyDataSetChanged();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Xử lý lỗi khi request gặp vấn đề
//                        Log.e("Volley Error", error.toString());
//                    }
//                });
//
//        // Thêm request vào hàng đợi để thực hiện
//        requestQueue.add(stringRequest);
//    }
//
//    // Phương thức này trả về chỉ số của ngày trong tuần (0-6)
//    private int getDayIndex(Date date) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        return calendar.get(Calendar.DAY_OF_WEEK) - 1; // Chỉ số của ngày trong tuần bắt đầu từ 1 (Chủ nhật) nên phải trừ đi 1
//    }
//
//}

package com.example.dubaott;

import androidx.appcompat.app.AppCompatActivity;

        import android.os.Bundle;
        import android.content.Intent;
        import android.text.GetChars;
        import android.util.Log;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Button;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.Date;
        import java.lang.String;
        import java.util.Calendar;


public class MainActivity2 extends AppCompatActivity {
    String tenthanhpho = "";
    ImageView imgback;
    TextView txtName;
    ListView lv;
    Button buttonShowChart;
    CustomAdapter customAdapter;
    ArrayList<Thoitiet> mangThoitiet;
    ArrayList<Float> maxTemperatures;
    ArrayList<Float> minTemperatures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Anhxa();

        maxTemperatures = new ArrayList<>();
        minTemperatures = new ArrayList<>();


        buttonShowChart = findViewById(R.id.buttonShowChart);
        buttonShowChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, ChartActivity.class);
                intent.putExtra("maxTemperatures", convertFloatListToArray(maxTemperatures));
                intent.putExtra("minTemperatures", convertFloatListToArray(minTemperatures));
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        String city = intent.getStringExtra("name");
        Log.d("ketqua", "Dữ liệu truyền qua: " + city);
        if (city.equals("")) {
            tenthanhpho = "Hanoi";
            Get7DaysData(tenthanhpho);
        } else {
            tenthanhpho = city;
            Get7DaysData(tenthanhpho);
        }
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void Anhxa() {
        imgback = (ImageView) findViewById(R.id.imageviewBack);
        txtName = (TextView) findViewById(R.id.textviewTenthanhpho);
        lv = (ListView) findViewById(R.id.listview);
        mangThoitiet = new ArrayList<Thoitiet>();
        customAdapter = new CustomAdapter(MainActivity2.this, mangThoitiet);
        lv.setAdapter(customAdapter);
    }

    private void Get7DaysData(String data) {
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + data + "&units=metric&cnt=40&appid=c8a36291833e586791153811c77e3b7a";
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity2.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name = jsonObjectCity.getString("name");
                            txtName.setText("Tên thành phố: " + name);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");

                            mangThoitiet.clear();
                            maxTemperatures.clear();
                            minTemperatures.clear();

                            long currentTime = System.currentTimeMillis();

                            boolean[] addedDay = new boolean[7];
                            int nextDay=0;
                            for (int i = 0; i < jsonArrayList.length(); i++) {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                                long ngayLong = jsonObjectList.getLong("dt") * 1000L;

                                if (ngayLong >= currentTime && nextDay < 7) {
                                    Date date = new Date(ngayLong);
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd");
                                    String Day = simpleDateFormat.format(date);

                                    int dayIndex = getDayIndex(date);

                                    if (!addedDay[dayIndex]) {
                                        JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("main");
                                        String max = jsonObjectTemp.getString("temp_max");
                                        String min = jsonObjectTemp.getString("temp_min");

                                        maxTemperatures.add(Float.parseFloat(max));
                                        minTemperatures.add(Float.parseFloat(min));

                                        JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                        JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                        String status = jsonObjectWeather.getString("description");
                                        String icon = jsonObjectWeather.getString("icon");

                                        mangThoitiet.add(new Thoitiet(Day, status, icon, max, min));
                                        addedDay[dayIndex] = true;
                                        nextDay++;
                                        currentTime = ngayLong;

                                    }
                                }
                                if (mangThoitiet.size() > 7) {
                                    break;
                                }
                            }

                            for (int i = 0; i < mangThoitiet.size(); i++) {
                                Thoitiet thoiTiet = mangThoitiet.get(i);
                                maxTemperatures.add(Float.parseFloat(thoiTiet.getMaxTemp()));
                                minTemperatures.add(Float.parseFloat(thoiTiet.getMinTemp()));
                            }

                            customAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Xử lý lỗi khi request gặp vấn đề
                        Log.e("Volley Error", error.toString());
                    }
                });
        requestQueue.add(stringRequest);
    }
    private int getDayIndex(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    private float[] convertFloatListToArray(ArrayList<Float> list) {
        if (list != null) {
            float[] array = new float[list.size()];
            for (int i = 0; i < list.size(); i++) {
                array[i] = list.get(i);
            }
            return array;
        } else {
            return new float[0];
        }
    }

}

