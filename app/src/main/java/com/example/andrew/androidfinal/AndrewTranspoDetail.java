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

/**
 * Detail shows bus stats
 */
public class AndrewTranspoDetail extends Activity {
    ArrayList<DetailData> javaDetailData=new ArrayList<>();
    String StopNumber="3050";
    String BusNumber="95";
    DetailAdapter detailAdapter;
    ListView detailList;

    /**
     * when activity starts link the adapter to the ListView, and load bundle data
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_andrew_transpo_detail);
        detailAdapter=new DetailAdapter(this);
        detailList=findViewById(R.id.octrasnpoDetailList);
        detailList.setAdapter(detailAdapter);
        Bundle extras = getIntent().getExtras();
        //Load the data passed to activity, and search using asynch task
        if (extras != null) {
            StopNumber = extras.getString("StopNumber");
            BusNumber=extras.getString("BusNumber");
            StopInfoQuery stopInfoQuery=new StopInfoQuery();
            stopInfoQuery.execute();
        }

    }

    /**
     * updates the listview when data is downloaded
     */
    public class DetailAdapter extends ArrayAdapter<DetailData> {
    public DetailAdapter(Context ctx) {
        super(ctx, 0);
    }

    public int getCount() { //number of objects in the array

        return (javaDetailData.size());
    }

    public DetailData getItem(int position) {
        return (javaDetailData.get(position));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = getLayoutInflater();
        View result;

        result = inflater.inflate(R.layout.octranspo_details, null);
        //This section links to texviews
        TextView busType = (TextView) result.findViewById(R.id.andrewOCDetailBusType);
        TextView busSpeed = (TextView) result.findViewById(R.id.andrewOCDetailBusSpeed);
        TextView destination = (TextView) result.findViewById(R.id.andrewOCDetailDestination);
        TextView adjustedTime=result.findViewById(R.id.andrewOCDetailAdjustedTime);
        TextView time=result.findViewById(R.id.andrewOCDetailStartTime);
        TextView adjustAge=result.findViewById(R.id.andrewOCDetailAdjustedAge);
        TextView lastTrip=result.findViewById(R.id.andrewOCDetailLastTrip);
        TextView longitude=result.findViewById(R.id.andrewOCLongitude);
        TextView latitude=result.findViewById(R.id.andrewOCLatitude);

        //get the data from the array
        DetailData temp = javaDetailData.get(position);
        //put the data into the current ListView item being inflated
        busType.setText(temp.getBusType());
        busSpeed.setText( " Bus SPeed: "+((temp.getGPSSpeed()==null)?"Unknown":temp.getGPSSpeed())  );
        destination.setText(" Dest: "+temp.getTripDestination());
        adjustedTime.setText(" Arrives in "+temp.getAdjustedScheduleTime()+" mins");
        adjustAge.setText(" updated "+temp.getAdjustmentAge()+" mins ago" );
        time.setText(" Scheduled for: "+temp.getTripStartTime());
        lastTrip.setText(((temp.getLastTrip().equalsIgnoreCase("true"))?"!Last bus on Route!":""));
        latitude.setText(" Latitude: "+((temp.getLatitude()==null)?"Unknown":temp.getLatitude()) );
        longitude.setText(" Longitude: "+((temp.getLongitude()==null)?"Unknown":temp.getLongitude()) );





        return result;

    }
}

    /**
     * uses Async to get bus details from server
     *
     */

public class StopInfoQuery extends AsyncTask<String, Integer, String> {
    ArrayList<DetailData> result=new ArrayList<>();

    /**
        Load the data from the OCTranspo URL
     based on CST2335 – Graphical Interface Programming Lab 6

     */
    @Override
    protected String doInBackground(String... strings) {

        Log.i("Execute Start","Execute start");
        HttpURLConnection conn;
        try {
            String urlString="https://api.octranspo1.com/v1.2/GetNextTripsForStop?appID=819a393b&&apiKey=a93a58aa1d62b564034eb4d10638f06c&stopNo=" +StopNumber+"&routeNo="+BusNumber;
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
            //Use xml parser to load the data

            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(conn.getInputStream(), null);
            parser.nextTag();
            DetailData temp=new DetailData();
            Log.i("Parsing","Parsing");

            //basically we cycle through the parser, we add data to our data object one piece at a time for each trip, at the end of each
            //trip we add that trip to our result array, then reset the data object for a new trip. Until we reach the end of our XML
            while (parser.next() != XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    if(parser.getEventType()==XmlPullParser.END_TAG){
                        // if we are in an end tag, and that tag is called trip, then add the trip to our result array
                        if (parser.getName().equalsIgnoreCase("trip")) {
                                result.add(temp);



                            temp=new DetailData(); //reset temp
                        }
                    }

                    continue;
                }
                String name = parser.getName();
                // Starts by looking for the entry tag

                if (name.equalsIgnoreCase("TripDestination")) {
                    parser.next();
                    String tempText=parser.getText();
                    Log.i("TripDestination","Route "+tempText);
                    temp.setTripDestination(tempText);
                }
                if (name.equalsIgnoreCase("TripStartTime")) {
                    parser.next();
                    String tempText=parser.getText();
                    Log.i("TripStartTime","TripStartTime "+tempText);
                    temp.setTripStartTime(tempText);
                }

                if (name.equalsIgnoreCase("AdjustedScheduleTime")) {
                    parser.next();
                    String tempText=parser.getText();
                    Log.i("AdjustedScheduleTime","AdjustedScheduleTime "+tempText);
                    temp.setAdjustedScheduleTime(tempText);
                }
                if (name.equalsIgnoreCase("AdjustmentAge")) {
                    parser.next();
                    String tempText=parser.getText();
                    Log.i("AdjustmentAge","AdjustmentAge "+tempText);
                    temp.setAdjustmentAge(tempText);
                }
                if (name.equalsIgnoreCase("LastTripOfSchedule")) {
                    parser.next();
                    String tempText=parser.getText();
                    Log.i("LastTripOfSchedule","LastTripOfSchedule "+tempText);
                    temp.setLastTrip(tempText);
                }

                if (name.equalsIgnoreCase("BusType")) {
                    parser.next();
                    String tempText=parser.getText();
                    Log.i("BusType","BusType "+tempText);
                    temp.setBusType(tempText);
                }
                if (name.equalsIgnoreCase("GPSSpeed")) {
                    parser.next();
                    String tempText=parser.getText();
                    Log.i("GPSSpeed","GPSSpeed "+tempText);
                    temp.setGPSSpeed(tempText);
                }
                if (name.equalsIgnoreCase("Latitude")) {
                    parser.next();
                    String tempText=parser.getText();
                    Log.i("Latitude","Latitude "+tempText);
                    temp.setLatitude(tempText);
                }
                if (name.equalsIgnoreCase("Longitude")) {
                    parser.next();
                    String tempText=parser.getText();
                    Log.i("Longitude","Longitude "+tempText);
                    temp.setLongitude(tempText);
                }



            }

        } catch (Exception e) {

        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }
/**
*   after the async task completes
*   adds the data downloaded to the activity so it can be put in to the inflator and shown
 */
    @Override
    protected void onPostExecute(String s) {
        Log.i("Execute Complete", "Execute complete");
        for (DetailData temp:result){
            javaDetailData.add(temp);
            detailAdapter.notifyDataSetChanged();
        }
    }


}

    /**
     * This class is used as a data object for the data recieved from OCTranspo
     * with getters and setters
     */

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

    public boolean hasData(){
        return ((LastTrip!=null)&&(Longitude!=null)&&(TripDestination!=null)&&(TripStartTime!=null)&&(AdjustedScheduleTime!=null)&&(AdjustmentAge!=null)&&(BusType!=null)&&(GPSSpeed!=null)&&(Latitude!=null));
    }

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
