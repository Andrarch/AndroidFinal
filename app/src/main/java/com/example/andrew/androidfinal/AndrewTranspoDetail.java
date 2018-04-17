package com.example.andrew.androidfinal;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class AndrewTranspoDetail extends Activity {
    ArrayList<DetailData> javaDetailData=new ArrayList<>();
    String StopNumber="3050";
    String BusNumber="95";
    DetailAdapter detailAdapter=new DetailAdapter(this);
    ListView detailList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andrew_transpo_detail);
        detailList=findViewById(R.id.octrasnpoDetailList);
        detailList.setAdapter(detailAdapter);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            StopNumber = extras.getString("StopNumber");
            BusNumber=extras.getString("BusBumber");
        }

    }

public class DetailAdapter extends ArrayAdapter<DetailData> {
    public DetailAdapter(Context ctx) {
        super(ctx, 0);
    }

    public int getCount() {

        return (javaDetailData.size());
    }

    public DetailData getItem(int position) {
        return (javaDetailData.get(position));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = getLayoutInflater();
        View result;

        result = inflater.inflate(R.layout.octranspo_details, null);

        TextView busType = (TextView) result.findViewById(R.id.andrewOCDetailBusType);
        TextView busSpeed = (TextView) result.findViewById(R.id.andrewOCDetailBusSpeed);
        TextView destination = (TextView) result.findViewById(R.id.andrewOCDetailDestination);
        TextView adjustedTime=result.findViewById(R.id.andrewOCDetailAdjustedTime);
        TextView time=result.findViewById(R.id.andrewOCDetailStartTime);
        TextView adjustAge=result.findViewById(R.id.andrewOCDetailAdjustedAge);
        TextView lastTrip=result.findViewById(R.id.andrewOCDetailLastTrip);

        DetailData temp = javaDetailData.get(position);

        busType.setText(temp.getBusType());
        busSpeed.setText(temp.getGPSSpeed());
        destination.setText(temp.getTripDestination());
        adjustedTime.setText(temp.getAdjustedScheduleTime());
        adjustAge.setText(temp.getAdjustmentAge());
        time.setText(temp.getTripStartTime());
        lastTrip.setText((temp.getLastTrip()));






        return result;

    }
}


public class StopInfoQuery extends AsyncTask<String, Integer, String> {
    ArrayList<DetailData> result=new ArrayList<>();

    @Override
    protected String doInBackground(String... strings) {

        Log.i("Execute Start","Execute start");
        HttpURLConnection conn;
        try {
            String urlString="https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=223eb5c3&&apiKey=ab27db5b435b8c8819ffb8095328e775&stopNo="+stopNumber;
            Log.i("URL",urlString);
            URL url = new URL(urlString);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.connect();
        } catch (Exception e) {
            ;
            Log.i("Error", "Failed to connect");
            return null;
        }
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(conn.getInputStream(), null);
            parser.nextTag();
            AndrewTranspoStopFragment.StopData temp=new AndrewTranspoStopFragment.StopData();
            Log.i("Parsing","Parsing");
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag
                if (name.equalsIgnoreCase("route")) {
                    if(temp.hasValues()){
                        Log.i("Added Value","Value Added to StopInfo");
                        result.add(temp);

                    }

                    temp=new AndrewTranspoStopFragment.StopData();
                }
                if (name.equalsIgnoreCase("RouteHeading")) {
                    parser.next();
                    String tempText=parser.getText();
                    Log.i("RouteHeading","Route "+tempText);
                    temp.setRouteHeading(tempText);
                }
                if (name.equalsIgnoreCase("routeNo")) {
                    parser.next();
                    String tempText=parser.getText();
                    Log.i("routeNo","routeNo "+tempText);
                    temp.setRouteNumber(tempText);
                }

                if (name.equalsIgnoreCase("Direction")) {
                    parser.next();
                    String tempText=parser.getText();
                    Log.i("Direction","Direction "+tempText);
                    temp.setRouteDirection(tempText);
                }
                Log.i("ParserInfo","ParserName "+name);




            }

        } catch (Exception e) {

        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //weatherProgress.setVisibility(View.VISIBLE);
        //weatherProgress.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        Log.i("Execute Complete", "Execute complete");
        for (DetailData temp:result){
            javaDetailData.add(temp);
            detailAdapter.notifyDataSetChanged();
        }
    }


}
public class DetailData {
    String TripDestination;
    String TripStartTime;
    String AdjustedScheduleTime;
    String AdjustmentAge;
    String BusType;
    String GPSSpeed;
    String Latitude;
    String Longitude;
    String LastTrip ;

    public String getLastTrip() {
        return LastTrip;
    }

    public void setLastTrip(String lastTrip) {
        LastTrip = lastTrip;
    }




    public String getTripDestination() {
        return TripDestination;
    }

    public void setTripDestination(String tripDestination) {
        TripDestination = tripDestination;
    }

    public String getTripStartTime() {
        return TripStartTime;
    }

    public void setTripStartTime(String tripStartTime) {
        TripStartTime = tripStartTime;
    }

    public String getAdjustedScheduleTime() {
        return AdjustedScheduleTime;
    }

    public void setAdjustedScheduleTime(String adjustedScheduleTime) {
        AdjustedScheduleTime = adjustedScheduleTime;
    }

    public String getAdjustmentAge() {
        return AdjustmentAge;
    }

    public void setAdjustmentAge(String adjustmentAge) {
        AdjustmentAge = adjustmentAge;
    }

    public String getBusType() {
        return BusType;
    }

    public void setBusType(String busType) {
        BusType = busType;
    }

    public String getGPSSpeed() {
        return GPSSpeed;
    }

    public void setGPSSpeed(String GPSSpeed) {
        this.GPSSpeed = GPSSpeed;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
}
}
