package com.example.andrew.androidfinal;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * This fragment displays stop information for the TranspoStop activity
 * Based on CST2335 – Graphical Interface Programming Lab 7

 */
public class AndrewTranspoStopFragment extends Fragment {

    public AndrewTranspoStopFragment() {
    }
    static ListView javaListView;

    static ArrayList<StopData> javaStopInfo = new ArrayList<StopData>();
    static Cursor cursor;
    static StopAdapter stopAdapter;
    static String stopNumber;
    static View fragmentView;

    /**
     * Creates an adapter and list
     * Runs an Async to get information from OCtranspo
     * Lab 4, Lab 6
     * @param savedInstanceState - not used contains data from previous run
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        javaStopInfo = new ArrayList<StopData>();
        stopAdapter = new StopAdapter(this.getActivity());

        Bundle getStuff=getArguments();
        if(getStuff!=null){
            stopNumber=getStuff.getString("StopNumber");
            StopInfoQuery stopquery=new StopInfoQuery();
            stopquery.execute();
        }


    }

    /**Links the adapter to the list so that changes to the list can be shown in the ListView object by the inflator
     * Creates a pseudo first entry to act as the header for the list.
     *
     * @param inflater - inflator for the activity
     * @param container - viewgroup the fragment is jammed into
     * @param savedInstanceState - previous state information
     * @return - returns the inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_andrew_transpo_stop, container, false);
        javaListView=result.findViewById(R.id.StopInfoList);
        javaListView.setAdapter(stopAdapter);
        StopData start=new StopData();
        start.setRouteDirection(" DIRECTION ");
        start.setRouteNumber("ROUTE # ");
        start.setRouteHeading(" HEADING ");
        start.setButton(false);
        javaStopInfo.add(start);
        return result;
    }

    /**
     * Stop adapter generates views when the arraylist it is attached to changes, and it is notified.
     * It generates the view objects for the give arraylist's data
     * Based on Lab 4
     */
    public class StopAdapter extends ArrayAdapter<StopData> {
        public StopAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {

            return (javaStopInfo.size());
        }

        public StopData getItem(int position) {
            return (javaStopInfo.get(position));
        }

        /**
         * This is the method that spits out the individual views
         * Based on Lab 4
         * @param position - integer showing the item number
         * @param convertView - no idea
         * @param parent - the parent view object
         * @return - returns the view object
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View result;

            result = inflater.inflate(R.layout.octranspo_stopinfo,parent,false );
            // Links the view objects to java data
            TextView direction = (TextView) result.findViewById(R.id.textOCDirection);
            TextView heading = (TextView) result.findViewById(R.id.textOCHeading);
            TextView route = (TextView) result.findViewById(R.id.textOCRoute);
            ImageView go=result.findViewById(R.id.imageOCGo);
            StopData temp=javaStopInfo.get(position);
            //puts the loaded data into the visual objects
            direction.setText(temp.getRouteDirection());
            heading.setText(temp.getRouteHeading());
            route.setText(temp.getRouteNumber());

            if(!temp.isButton()){ //Hides the button on the first entry (which is the header)
                go.setVisibility(View.INVISIBLE);
            }

            go.setOnClickListener((t)->{ //When you click the button it passes data and moves to the detail activity to show specific bus info
                Intent intent = new Intent( getActivity(), AndrewTranspoDetail.class);
                intent.putExtra("StopNumber", stopNumber);
                intent.putExtra("BusNumber", temp.getRouteNumber());
                startActivity(intent);
            });


            return result;

        }

    }

    /**
     * Based on Lab 6 Loads data for bus stop using async task
     */
    public class StopInfoQuery extends AsyncTask<String, Integer, String> {
        ArrayList<StopData> result=new ArrayList<>();

        /**
         * connects to octranspo to get xml data for a bus stop
         * based on Lab 6
         * @param strings
         * @return
         */
        @Override
        protected String doInBackground(String... strings) {

            Log.i("Execute Start","Execute start");
            HttpURLConnection conn;
            try {
                String urlString="https://api.octranspo1.com/v1.2/GetRouteSummaryForStop?appID=819a393b&&apiKey=a93a58aa1d62b564034eb4d10638f06c&stopNo="+stopNumber;
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
                //Parses the XML from OCTranspo
                //https://developer.android.com/reference/org/xmlpull/v1/XmlPullParser.html
                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(conn.getInputStream(), null);
                parser.nextTag();
                StopData temp=new StopData();
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

                        temp=new StopData();
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

        /**
         * Not used
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }

        /**
         * After the async task is done, the information it has loaded from OCTranspo is added to the arraylist, and the arrayadapter is notified
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            Log.i("Execute Complete", "Execute complete");
            for (StopData temp:result){
                javaStopInfo.add(temp);
                stopAdapter.notifyDataSetChanged();
            }
        }


    }

    /**
     * THis is a data object for the data gathered from OCtranspo for the bus stop.
     */

    public class StopData {
        String routeNumber, routeDirection, routeHeading;
        boolean button=true;

        public String getRouteDirection() {
            return routeDirection;
        }

        public String getRouteHeading() {
            return routeHeading;
        }

        public String getRouteNumber() {
            return routeNumber;
        }

        public void setRouteDirection(String routeDirection) {
            this.routeDirection = routeDirection;
        }

        public void setRouteHeading(String routeHeading) {
            this.routeHeading = routeHeading;
        }

        public void setRouteNumber(String routeNumber) {
            this.routeNumber = routeNumber;
        }
        public boolean hasValues(){
            return((routeDirection!=null)&&(routeHeading!=null)&&(routeNumber!=null));
        }

        public void setButton(boolean button) {
            this.button = button;
        }

        public boolean isButton() {
            return button;
        }
    }

}
