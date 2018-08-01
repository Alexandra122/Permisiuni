package com.example.user.permisiuni;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PERMISSION_REQUEST_CODE=200;
    private View view;

    Button btnPerm;
    Button btnPerm2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPerm = (Button) findViewById(R.id.btnPerm);
        btnPerm2 = (Button) findViewById(R.id.btnPerm2);
        btnPerm.setOnClickListener(this);
        btnPerm2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnPerm:
                if (checkPermission()) {
                    Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(MainActivity.this,"Request Permission",Toast.LENGTH_LONG).show();

                }
                break;
            case R.id.btnPerm2:
                if (!checkPermission()){
                    requestPermission(new String[]{ACCESS_FINE_LOCATION, CAMERA}, PERMISSION_REQUEST_CODE);


            }
            else {
                    Toast.makeText(MainActivity.this,"Permission already granted",Toast.LENGTH_LONG).show();

                }
                break;

        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String[] strings, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, CAMERA}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                           Toast.makeText(MainActivity.this,"Permission Granted, Now you can access location data and camera.",Toast.LENGTH_LONG).show();
                } else {
                       Toast.makeText(MainActivity.this,"Permission Denied, You cannot access location data and camera.",Toast.LENGTH_LONG).show();

                       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int wich) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermission(new String[]{ACCESS_FINE_LOCATION, CAMERA}, PERMISSION_REQUEST_CODE);
                                        }

                                    }
                                });
                                return;
                            }
                        }
                    }

                }
                break;
        }
    }
        private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener){
        new AlertDialog.Builder(MainActivity.this).setMessage(message).setPositiveButton("OK",okListener).setNegativeButton("Cancel",null).create().show();
    }
}

