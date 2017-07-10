package be.mneli.rushhour;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import be.mneli.rushhour.model.helper.web.HttpHandler;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnHomeGame;
    private Button btnHomeScores;
    private Button btnHomeWiki;
    private Button btnHomeSettings;
    private TextView tvHomeCity;

    private FusedLocationProviderClient mFusedLocationClient;

    private double latitude = 50.850346;
    private double longitude = 4.351721;
    private String apiKey;
    private String partialUrl = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
        new GetCityName().execute();
        initView();
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            }
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                });
    }

    private void initView() {
        btnHomeGame = (Button) findViewById(R.id.btn_home_game);
        btnHomeScores = (Button) findViewById(R.id.btn_home_scores);
        btnHomeWiki = (Button) findViewById(R.id.btn_home_wiki);
        btnHomeSettings = (Button) findViewById(R.id.btn_home_settings);
        tvHomeCity = (TextView) findViewById(R.id.tv_home_city);

        btnHomeGame.setOnClickListener(this);
        btnHomeScores.setOnClickListener(this);
        btnHomeWiki.setOnClickListener(this);
        btnHomeSettings.setOnClickListener(this);

        setCityName();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_home_game:
                goToActivity(PlayActivity.class);
                break;
            case R.id.btn_home_scores:
                //TODO
                relaxGringo();
                break;
            case R.id.btn_home_wiki:
                goToActivity(WikiActivity.class);
                break;
            case R.id.btn_home_settings:
                //TODO
                relaxGringo();
                break;
            default:
        }
    }

    private void relaxGringo() {
        Toast.makeText(this, getString(R.string.relax_gringo), Toast.LENGTH_SHORT).show();
    }

    private void goToActivity(Class activity) {
        Intent callIntent = new Intent(this, activity);
        startActivity(callIntent);
    }

    private String getCityName() {

        return cityName == null ? getString(R.string.default_city_name) : cityName;
    }

    private void setCityName() {
        tvHomeCity.setText(getCityName());

    }

    private class GetCityName extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setCityName();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            apiKey = getResources().getString(R.string.google_api_key);
            String url = partialUrl + latitude + "," + longitude + "&key=" + apiKey;
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray results = jsonObj.getJSONArray("results");
                    JSONObject addresses = results.getJSONObject(results.length() - 2);
                    JSONArray addressComponents = addresses.getJSONArray("address_components");
                    JSONObject address = addressComponents.getJSONObject(0);
                    cityName = address.getString("short_name");

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }
    }
}
