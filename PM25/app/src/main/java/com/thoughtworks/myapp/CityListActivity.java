package com.thoughtworks.myapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtworks.myapp.domain.CityCollection;
import com.thoughtworks.myapp.service.serviceclient.CitiesServiceClient;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by dl on 2015/12/25.
 */
public class CityListActivity  extends ListActivity {

    private ProgressDialog loadingDialog;
    private TextView messageTextView;
    private Button reloadButton;

    private CityCollection cityCollection;

    public CityCollection getCityCollection() {
        return cityCollection;
    }

    public void setCityCollection(CityCollection cityCollection) {
        this.cityCollection = cityCollection;
    }


    @Override
    protected void onStart() {
        super.onStart();
        initCityData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.citylist_layout);
        initViews();
    }

    private void initViews(){
        messageTextView = (TextView)findViewById(R.id.text_view_message);
        reloadButton = (Button)findViewById(R.id.button_reloadcity);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCityData();
            }
        });

        if(cityCollection != null){
            populateCityList();
        }
    }

    private void initCityData(){
        showLoading();
        CitiesServiceClient.getInstance().requestCities(new Callback<CityCollection>() {
            @Override
            public void onResponse(Response<CityCollection> response, Retrofit retrofit) {
                setCityCollection(response.body());
                showSuccessScreen();
            }

            @Override
            public void onFailure(Throwable t) {
                showErrorScreen();
            }
        });
    }

    private void populateCityList(){

        ArrayList<String> cities = getCityCollection().getCities();
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,cities);
        setListAdapter(adapter);
    }

    private void showSuccessScreen() {
        hideLoading();
        messageTextView.setText("");
        messageTextView.setVisibility(View.GONE);
        reloadButton.setVisibility(View.GONE);
    }

    private void showErrorScreen() {
        hideLoading();
        messageTextView.setVisibility(View.VISIBLE);
        messageTextView.setText(R.string.error_message_query_city);
        Toast.makeText(CityListActivity.this, R.string.error_message_query_city, Toast.LENGTH_SHORT).show();
    }

    private void showLoading() {
        loadingDialog.show();
    }

    private void hideLoading() {
        loadingDialog.dismiss();
    }

}
