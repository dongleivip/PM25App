package com.thoughtworks.myapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.thoughtworks.myapp.domain.AQI;
import com.thoughtworks.myapp.service.serviceclient.AirQualityServiceClient;

import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by dl on 2015/12/25.
 */
public class AqiActivity extends AppCompatActivity {

    private  String TAG = "AqiActivity";

    private ProgressDialog loadingDialog;
    private TextView messageTextView;
    private Button refreshButton;

    private ListView listView;

    private List<AQI> aqiList = null;

    private String city = "";

    public List<AQI> getAqiList() {
        return aqiList;
    }

    public void setAqiList(List<AQI> aqiList) {
        this.aqiList = aqiList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aqiactivity_layout);

        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        Log.e(TAG,"city -> " + city);

        initView();

        initData();
    }

    private void initView(){
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage(getString(R.string.loading_message));

        messageTextView = (TextView)findViewById(R.id.tv_aqi_message);
        refreshButton = (Button)findViewById(R.id.button_aqi_refresh);

        listView = (ListView)findViewById(R.id.aqi_listView);

    }

    private void initData(){
        queryData();
    }

    private void queryData(){
        Log.e(TAG,"queryData()");
        showLoading();
        Log.e(TAG,"query city -> " + city);
        AirQualityServiceClient.getInstance().requestAQI(city, new Callback<List<AQI>>() {
            @Override
            public void onResponse(Response<List<AQI>> response, Retrofit retrofit) {
                Log.e(TAG,"onResponse()");
                setAqiList(response.body());
                showSuccessScreen();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG,"onFailure()");
                showErrorScreen();
            }
        });
    }

    public void onRefreshClick(View view){
        queryData();
    }

    private void showSuccessScreen() {
        hideLoading();
        messageTextView.setText("");
        messageTextView.setVisibility(View.GONE);
        refreshButton.setVisibility(View.GONE);
        populate();
    }

    private void populate() {
        MyAdapter adapter = new MyAdapter(this);
        listView.setAdapter(adapter);
    }

    private void showErrorScreen() {
        hideLoading();
        messageTextView.setVisibility(View.VISIBLE);
        messageTextView.setText(R.string.error_message_query_aqi);
        refreshButton.setVisibility(View.VISIBLE);
        Toast.makeText(AqiActivity.this, R.string.error_message_query_aqi, Toast.LENGTH_SHORT).show();
    }


    public class MyAdapter extends BaseAdapter
    {
        private LayoutInflater mInflater = null;
        private MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            //How many items are in the data set represented by this Adapter.
            return aqiList.size();
        }
        @Override
        public Object getItem(int position) {
            // Get the data item associated with the specified position in the data set.
            if(aqiList!= null)
                return aqiList.get(position);
            else
                return null;
        }
        @Override
        public long getItemId(int position) {
            //Get the row id associated with the specified position in the list.
            return position;
        }

        //Get a View that displays the data at the specified position in the data set.
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null)
            {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.list_item, null);
                holder.tv_positionName = (TextView)convertView.findViewById(R.id.tv_positionName);
                holder.tv_quality = (TextView)convertView.findViewById(R.id.tv_quality);
                holder.tv_pollutant = (TextView)convertView.findViewById(R.id.tv_pollutant);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.tv_positionName.setText(aqiList.get(position).getPositionName());
            holder.tv_quality.setText(aqiList.get(position).getQuality());
            holder.tv_pollutant.setText(aqiList.get(position).getPrimaryPollutant());
            return convertView;
        }

    }

    static class ViewHolder
    {
        public ImageView img;
        public TextView tv_positionName;
        public TextView tv_quality;
        public TextView tv_pollutant;
    }

    private void showLoading() {
        loadingDialog.show();
    }

    private void hideLoading() {
        loadingDialog.dismiss();
    }

}
