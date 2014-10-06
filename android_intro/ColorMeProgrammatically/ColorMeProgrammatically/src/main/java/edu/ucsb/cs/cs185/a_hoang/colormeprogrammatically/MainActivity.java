package edu.ucsb.cs.cs185.a_hoang.colormeprogrammatically;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


public class MainActivity extends ActionBarActivity {

    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainLayout = new LinearLayout(this);
        mainLayout.setBackgroundColor(Color.RED);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout tabs = new LinearLayout(this);
        tabs.setOrientation(LinearLayout.HORIZONTAL);





        double totalWidth = (double)getDeviceScreenWidth();
        double tabWidth = totalWidth/3;

        Button red = new Button(this);
        red.setWidth((int) tabWidth);
        red.setText(getResources().getString(R.string.red_label));
        red.setBackgroundColor(getResources().getColor(R.color.red));
        red.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mainLayout.setBackgroundColor(getResources().getColor(R.color.red));
            }
        });

        Button green = new Button(this);
        green.setWidth((int) tabWidth);
        green.setText(getResources().getString(R.string.green_label));
        green.setBackgroundColor(getResources().getColor(R.color.green));
        green.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mainLayout.setBackgroundColor(getResources().getColor(R.color.green));
            }
        });


        Button blue = new Button(this);
        blue.setWidth((int) tabWidth + 3);
        blue.setText(getResources().getString(R.string.blue_label));
        blue.setBackgroundColor(getResources().getColor(R.color.blue));
        blue.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mainLayout.setBackgroundColor(getResources().getColor(R.color.blue));
            }
        });


        tabs.addView(red);
        tabs.addView(green);
        tabs.addView(blue);


        mainLayout.addView(tabs);

        setContentView(mainLayout);


    }

    @SuppressLint("NewApi")
    public int getDeviceScreenWidth() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
