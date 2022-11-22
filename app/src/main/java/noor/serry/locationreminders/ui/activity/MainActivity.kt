package noor.serry.locationreminders.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import noor.serry.locationreminders.R



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       getPermissions()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
            Toast.makeText(this, "please allow permission", Toast.LENGTH_SHORT).show()
        else {
            openGps()
        }
    }

    private fun getPermissions() {
        var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION
            , Manifest.permission.ACCESS_COARSE_LOCATION)
        if(Build.VERSION_CODES.Q<=Build.VERSION.SDK_INT)
            permissions = permissions.plus( Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        requestPermissions(permissions, 1)
    }

    private fun openGps() {
        var locationRequest = LocationRequest.create().setInterval(1000).setFastestInterval(500)
        var locationSetting = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            .setAlwaysShow(true).build()
        var task = LocationServices.getSettingsClient(this).checkLocationSettings(locationSetting)
        task.addOnFailureListener(this::onGpsFailureListener)

    }
    @SuppressLint("RestrictedApi")
    private  fun onGpsFailureListener(exception: Exception){
        if (exception is ResolvableApiException)
            exception.startResolutionForResult(this,1)
    }

}