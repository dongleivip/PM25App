package com.thoughtworks.myapp;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtworks.myapp.domain.CityCollection;
import com.thoughtworks.myapp.service.serviceclient.CitiesServiceClient;

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
        reloadButton = (Button)findViewById(R.id.button_city_refresh);
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
                Intent intent = new Intent(CityListActivity.this,AqiActivity.class);
                intent.putExtra("city",tv.getText());
                startActivity(intent);
            }
        });

    }


    private void queryCityData(){
        Log.d(TAG, "queryCityData()");

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

        Log.d(TAG, "populateCityList()");

        if(getCityCollection()!= null && getCityCollection().getCities() == null)
        {
            Toast.makeText(CityListActivity.this,R.string.error_api_exceeded,Toast.LENGTH_SHORT).show();
        } else if(getCityCollection()!= null && getCityCollection().getCities() != null){
            Log.d(TAG,"cities not null");

            MyAdapter adapter = new MyAdapter(this);
            setListAdapter(adapter);
        }
    }



    private void showLoading() {
        loadingDialog.show();
    }

    private void hideLoading() {
        loadingDialog.dismiss();
    }


    public class MyAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            if(getCityCollection() != null && getCityCollection().getCities() != null)
                return getCityCollection().getCities().size();
            else
                return 0;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if (convertView == null) {

                Log.d(TAG,"new viewloder");

                holder = new ViewHolder();
                convertView = mInflater.inflate(android.R.layout.simple_list_item_1, null);
                holder.tv_city = (TextView)convertView.findViewById(android.R.id.text1);
                convertView.setTag(holder);

            }else {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.tv_city.setText(getCityCollection().getCities().get(position).toString());

            return convertView;
        }

    }

    public final class ViewHolder{
        public TextView tv_city;
    }

}

