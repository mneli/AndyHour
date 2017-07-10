package be.mneli.rushhour;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnHomeGame;
    private Button btnHomeScores;
    private Button btnHomeWiki;
    private Button btnHomeSettings;
    private TextView tvHomeCity;

    private FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
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
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            Log.e("LOCATION_GET_LOCATION", location.toString());
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
        return getString(R.string.default_city_name);
    }

    private void setCityName() {
        tvHomeCity.setText(getCityName());
    }
}
