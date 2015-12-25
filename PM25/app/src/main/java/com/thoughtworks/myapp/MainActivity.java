package com.thoughtworks.myapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtworks.myapp.domain.CityCollection;
import com.thoughtworks.myapp.domain.PM25;
import com.thoughtworks.myapp.service.serviceclient.AirServiceClient;
import com.thoughtworks.myapp.service.serviceclient.CitiesServiceClient;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private EditText cityEditText;
    private TextView pm25TextView;
    private ProgressDialog loadingDialog;

    private CityCollection cityCollection = null;

    @Override
    protected void onStart() {
        super.onStart();
        initCityData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityEditText = (EditText) findViewById(R.id.edit_view_input);
        pm25TextView = (TextView) findViewById(R.id.text_view_pm25);
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage(getString(R.string.loading_message));

        findViewById(R.id.button_query_pm25).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onQueryPM25Click();
            }
        });

        findViewById(R.id.button_query_citis).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 onShowCitiesClick();
             }
        });

        findViewById(R.id.button_open_citylist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Start CityListActivity");
               startActivity(new Intent(MainActivity.this,CityListActivity.class));
            }
        });
    }

    private void initCityData(){
        //showLoading();
        CitiesServiceClient.getInstance().requestCities(new Callback<CityCollection>() {
            @Override
            public void onResponse(Response<CityCollection> response, Retrofit retrofit) {
                setCityCollection(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, R.string.error_message_query_city, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public CityCollection getCityCollection() {
        return cityCollection;
    }

    public void setCityCollection(CityCollection cityCollection) {
        this.cityCollection = cityCollection;
    }

    private void onShowCitiesClick(){
        StringBuffer buffer = new StringBuffer();
        for(int i = 0;i < 10 ;i++){
            buffer.append(getCityCollection().getCities().get(i) + ",");
        }
        pm25TextView.setText(buffer.toString());
    }


    private void onQueryPM25Click() {
        final String city = cityEditText.getText().toString();
        if (!TextUtils.isEmpty(city)) {
            showLoading();
            AirServiceClient.getInstance().requestPM25(city, new Callback<List<PM25>>() {
                @Override
                public void onResponse(Response<List<PM25>> response, Retrofit retrofit) {
                    showSuccessScreen(response);
                }

                @Override
                public void onFailure(Throwable t) {
                    showErrorScreen();
                }
            });
        }
    }

    private void showSuccessScreen(Response<List<PM25>> response) {
        hideLoading();
        if (response != null) {
            populate(response.body());
        }
    }

    private void showErrorScreen() {
        hideLoading();
        pm25TextView.setText(R.string.error_message_query_pm25);
    }

    private void populate(List<PM25> data) {
        if (data != null && !data.isEmpty()) {
            PM25 pm25 = data.get(0);
            pm25TextView.setText(pm25.getPositionName() + pm25.getQuality());
        }
    }

    private void showLoading() {
        loadingDialog.show();
    }

    private void hideLoading() {
        loadingDialog.dismiss();
    }

}
