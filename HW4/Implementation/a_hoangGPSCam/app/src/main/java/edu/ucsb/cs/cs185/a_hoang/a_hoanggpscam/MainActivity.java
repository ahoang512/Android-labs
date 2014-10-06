package edu.ucsb.cs.cs185.a_hoang.a_hoanggpscam;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;



import java.io.File;
import java.io.FileOutputStream;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    private Uri fileUri; // file url to store image/video

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int REQUEST_TAKE_PHOTO=1;
    private int photoNum;
    private Location currentLocation;
    private String photoName;
    private File picList;

    private SharedPreferences sp;
    private SharedPreferences.Editor edit ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = findViewById(R.id.main_layout);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        edit = sp.edit();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();
        switch (item.getItemId()) {

            case R.id.action_takePhoto:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File photoFile = null;

                if(intent.resolveActivity(getPackageManager())!=null){
                    try{
                        photoFile=createImageFile();
                    }catch (IOException ex){
                        //error
                    }
                }
                if(photoFile !=null){
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                }
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                return true;

            case R.id.action_help:
                showHelpDialog(findViewById(R.id.main_layout));
                return true;

            case R.id.action_settings:
                Dialog dialog = new Dialog(this);
                dialog.setTitle(getResources().getString(R.string.no_settings));
                dialog.show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
            String p = sp.getString(getResources().getString(R.string.path), null);


            Drawable d = Drawable.createFromPath(p);
            View v = findViewById(R.id.main_layout);
            v.setBackground(d);
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

            final LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    // Called when a new location is found by the GPS location provider.

                }

                public void onStatusChanged(String provider, int status, Bundle extras) {}

                public void onProviderEnabled(String provider) {}

                public void onProviderDisabled(String provider) {}
            };

            // Register the listener with the Location Manager to receive location updates
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);




            saveLocationToFile(picList.getPath(), currentLocation.getLongitude(), currentLocation.getLatitude());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(getResources().getString(R.string.path), sp.getString(getResources().getString(R.string.path), null));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        String path = (String)savedInstanceState.get(getResources().getString(R.string.path));
        Drawable d = Drawable.createFromPath(path);
        View v = findViewById(R.id.main_layout);
        v.setBackground(d);

    }
    public void photoOnClick(View view) {
    //http://stackoverflow.com/questions/9802066/getoutputmediafileuri-method-is-not-accessible
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        if(intent.resolveActivity(getPackageManager())!=null){
            try{
                photoFile=createImageFile();
            }catch (IOException ex){
                //error
            }
        }

        if(photoFile !=null){
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        }

        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    private File createImageFile() throws IOException{
        int number;
        File dir = new File(Environment.getExternalStorageDirectory() + getResources().getString(R.string.folder_name));
        if(!dir.exists()){
            dir.mkdir();
        }
        picList= new File(dir,getResources().getString(R.string.pic_list));

        //check if list exist
        if(!picList.exists()){
            try {
                picList.createNewFile();
            }catch(IOException ex){
                //
            }
        }
        File[] counter = dir.listFiles();
        number = counter.length;


        photoName = getResources().getString(R.string.photo_)+ String.format(getResources().getString(R.string.digits),number)+ getResources().getString(R.string.extension);

        File image =new File(dir,photoName);
        String path = image.getAbsolutePath();
        edit.putString(getResources().getString(R.string.path), path);
        edit.commit();
        return image;
    }

    public void showHelpDialog(View v){
        FragmentManager fm = getFragmentManager();
        DialogFrag df = new DialogFrag();
        df.show(fm,"help");
    }

    public void saveLocationToFile(String path, double lon, double lat){
        File file = new File(path);
        FileOutputStream fos= null;
        Resources r = getResources();
        try {
            fos = new FileOutputStream(file, true);
            String string = r.getString(R.string.image_start)
                    +r.getString(R.string.name_start) +photoName+  r.getString(R.string.name_end)
                    +r.getString(R.string.lat_start) + lat + r.getString(R.string.lat_end)
                    +r.getString(R.string.long_start) + lon + r.getString(R.string.long_end)
                    + getResources().getString(R.string.image_end);

            byte[] imageBytes = string.getBytes();

            fos.write(imageBytes);
            fos.flush();
            fos.close();
        }catch(IOException ex){
            //
        }
    }

}
