package be.mneli.rushhour.model.async;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.TimeUnit;

import be.mneli.rushhour.HomeActivity;


/**
 * Created by mneli on 10/07/17.
 */

public class AsyncGetLatLong extends AsyncTask<Void, Void, Location> implements OnSuccessListener<Location> {
    private FusedLocationProviderClient mFusedLocationClient;
    private double latitude;
    private double longitude;
    public Location mylocation;
    public boolean noSuccess = true;


    //region callback
    public interface IAsyncGetLatLong {
        //Location getLocation();

        void updateLocation(Location position);
    }

    private IAsyncGetLatLong callback;

    public void setCallback(IAsyncGetLatLong callback) {
        this.callback = callback;
    }

    //endregion


    @Override
    protected Location doInBackground(Void... voids) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(HomeActivity.context);

        if (ActivityCompat.checkSelfPermission(HomeActivity.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }

        if (mFusedLocationClient.getLastLocation().addOnSuccessListener(this) != null) {

            while (noSuccess) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else mylocation = null;

        return mylocation;
    }

    @Override
    protected void onPostExecute(Location position) {
        callback.updateLocation(position);
    }

    @Override
    public void onSuccess(Location location) {
        Log.e("ON_SUCCES", location.toString());
        if (location != null) {

            latitude = location.getLatitude();
            longitude = location.getLongitude();
            mylocation = location;
            noSuccess = false;
        }
    }
}
