package fi.jamk.golfcourse;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private JSONArray locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        FetchDataTask fetchDataTask = new FetchDataTask();
        fetchDataTask.execute("http://ptm.fi/materials/golfcourses/golf_courses.json");
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
    }

    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        private final View mWindow;
        private final View mContents;

        CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.popup_info,null);
            mContents = getLayoutInflater().inflate(R.layout.popup_info,null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            render(marker,mWindow);
            return mWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            render(marker,mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            String title = marker.getTitle();
            TextView titleView = ((TextView) view.findViewById(R.id.popupTitle));
            titleView.setText(title);

            String snippet = marker.getSnippet();
            TextView snippetView = ((TextView) view.findViewById(R.id.popupDetails));
            snippetView.setText(snippet);
        }
    }

    class FetchDataTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            JSONObject json = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                json = new JSONObject(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
            }
            return json;
        }

        protected void onPostExecute(JSONObject json) {
            String Gcourse;
            double lat; double lng;
            float huecolor;
            try {
                locations = json.getJSONArray("courses");
                for (int i=0;i < locations.length(); i++) {
                    JSONObject locs = locations.getJSONObject(i);
                    lat = locs.getDouble("lat");
                    lng = locs.getDouble("lng");
                    Gcourse = locs.getString("type");
                    switch (Gcourse) {
                        case "Kulta":
                            huecolor = 100;
                            break;
                        case "Etu":
                            huecolor = 175;
                            break;
                        case "Kulta/Etu":
                            huecolor = 250;
                            break;
                        case "?":
                            huecolor = 340;
                            break;
                        default:
                            huecolor = 10;
                            break;
                    }
                    LatLng coords = new LatLng(lat,lng);
                    mMap.addMarker(new MarkerOptions()
                            .position(coords)
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(huecolor))
                            .title(locs.getString("course"))
                            .snippet(locs.getString("address") + "\n" + locs.getString("phone")  + "\n" + locs.getString("email")  + "\n" + locs.getString("web"))
                    );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
