package com.thoughtworks.myapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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

    private final String TAG = "CityListActivity";

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
        Log.d(TAG,"onStart");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");

        setContentView(R.layout.citylist_layout);

        initViews();

        queryCityData();
    }

    private void initViews(){
        Log.d(TAG, "initViews()");

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage(getString(R.string.loading_message));
        messageTextView = (TextView)findViewById(R.id.text_view_message);
        reloadButton = (Button)findViewById(R.id.button_reloadcity);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryCityData();
            }
        });

        ListView listView = (ListView)findViewById(android.R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView)view.findViewById(android.R.id.text1);
                Toast.makeText(CityListActivity.this,tv.getText(),Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void queryCityData(){
        Log.d(TAG, "initCityData()");

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

    private void showSuccessScreen() {
        hideLoading();
        messageTextView.setText("");
        messageTextView.setVisibility(View.GONE);
        reloadButton.setVisibility(View.GONE);

        populateCityList();
    }

    private void showErrorScreen() {
        hideLoading();
        messageTextView.setVisibility(View.VISIBLE);
        messageTextView.setText(R.string.error_message_query_city);
        reloadButton.setVisibility(View.VISIBLE);
        Toast.makeText(CityListActivity.this, R.string.error_message_query_city, Toast.LENGTH_SHORT).show();
    }

    private void populateCityList(){

        Log.d(TAG,"populateCityList()");

        if(getCityCollection()!= null)
        {
            ArrayList<String> cities = getCityCollection().getCities();
            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,cities);
            setListAdapter(adapter);
        }
    }

    private void showLoading() {
        loadingDialog.show();
    }

    private void hideLoading() {
        loadingDialog.dismiss();
    }

}

