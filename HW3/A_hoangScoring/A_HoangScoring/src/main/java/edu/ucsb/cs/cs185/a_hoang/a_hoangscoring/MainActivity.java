package edu.ucsb.cs.cs185.a_hoang.a_hoangscoring;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.Date;


public class MainActivity extends FragmentActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showDatePickerDialog(View v){

           DialogFragment dateChooser = new DatePickerFragment();
           dateChooser.show(getFragmentManager(), "datePicker");
    }

    public void showGameDialog(View v){
        FragmentManager fm = getFragmentManager();
        GameFragment gf = new GameFragment();
        gf.show(fm,"gamePicker");
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

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("date", ((TextView)findViewById(R.id.date_textView)).getText().toString());
        savedInstanceState.putString("team1 name", ((TextView)findViewById(R.id.team1_label)).getText().toString());
        savedInstanceState.putString("team2 name", ((TextView)findViewById(R.id.team2_label)).getText().toString());
        savedInstanceState.putString("team1 score", ((TextView)findViewById(R.id.score1_label)).getText().toString());
        savedInstanceState.putString("team2 score", ((TextView)findViewById(R.id.score2_label)).getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        ((TextView)findViewById(R.id.date_textView)).setText(savedInstanceState.getString("date"));
        ((TextView)findViewById(R.id.team1_label)).setText(savedInstanceState.getString("team1 name"));
        ((TextView)findViewById(R.id.team2_label)).setText(savedInstanceState.getString("team2 name"));
        ((TextView)findViewById(R.id.score1_label)).setText(savedInstanceState.getString("team1 score"));
        ((TextView)findViewById(R.id.score2_label)).setText(savedInstanceState.getString("team2 score"));

    }

    public void updateTeams(String team1, String team2){
        TextView team1_label = (TextView)findViewById(R.id.team1_label);
        team1_label.setText(team1);

        TextView team2_label = (TextView)findViewById(R.id.team2_label);
        team2_label.setText(team2);
    }

    public void updateScores(String score1, String score2){
        TextView score1_label = (TextView)findViewById(R.id.score1_label);
        score1_label.setText(score1);
        TextView score2_label = (TextView)findViewById(R.id.score2_label);
        score2_label.setText(score2);
    }

    public void clearScreen(View v){
        TextView team1_label = (TextView)findViewById(R.id.team1_label);
        team1_label.setText(getResources().getString(R.string.Team_1_label));
        TextView team2_label = (TextView)findViewById(R.id.team2_label);
        team2_label.setText(getResources().getString(R.string.Team_2_label));
        TextView score1_label = (TextView)findViewById(R.id.score1_label);
        score1_label.setText(getResources().getString(R.string.zero));
        TextView score2_label = (TextView)findViewById(R.id.score2_label);
        score2_label.setText(getResources().getString(R.string.zero));
        TextView date = (android.widget.TextView)findViewById(R.id.date_textView);
        date.setText(getResources().getString(R.string.date));

    }



    public void updateDate( int i, int i2, int i3) {
        String formattedDate=null;
        TextView date = (android.widget.TextView)findViewById(R.id.date_textView);
        formattedDate = monthConverter(i2) + " " + i3 + ", "+ i;
        date.setText(formattedDate);
    }


    public String monthConverter(int month){
        String m=null;
        String [] months = getResources().getStringArray(R.array.months);

        m= months[month];
        return m;
    }







}
