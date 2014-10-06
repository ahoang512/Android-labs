package edu.ucsb.cs.cs185.a_hoang.hw5.hw5;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;



public class MainActivity extends ActionBarActivity {

    private static final int SELECT_PICTURE = 1;
    private static TouchView tv;
    private static float x;
    private static float y;
    private String imageSelectedPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View main = (View) findViewById(R.id.main_layout);
        String path = Environment.getExternalStorageDirectory().toString();
        path += "/ucsbmap.png";
        Drawable bg = Drawable.createFromPath(path);
        main.setBackground(bg);


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

        switch(item.getItemId()){
            case (R.id.action_picture):
                // from stackoverflow given by hw description
                // in onCreate or any event where your want the user to
                // select a file
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, SELECT_PICTURE);
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //http://viralpatel.net/blogs/pick-image-from-galary-android-app/
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            if(requestCode == SELECT_PICTURE && null != data){
                Uri selectedImageUri = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImageUri,filePathColumn,null,null,null);
                cursor.moveToFirst();
                //imageSelectedPath = getPath(selectedImageUri);
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imageSelectedPath = cursor.getString(columnIndex);
                cursor.close();

                tv = new TouchView(this, imageSelectedPath);
                //tv.setVisibility(View.VISIBLE);



                RelativeLayout rL = (RelativeLayout)findViewById(R.id.main_layout);
                rL.addView(tv);

            }
        }
    }


}
