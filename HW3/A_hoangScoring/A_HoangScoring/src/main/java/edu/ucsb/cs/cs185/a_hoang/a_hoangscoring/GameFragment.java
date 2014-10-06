package edu.ucsb.cs.cs185.a_hoang.a_hoangscoring;



import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;


public class GameFragment extends DialogFragment implements View.OnClickListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);


        dialog.setTitle(getResources().getString(R.string.enter_game));

        //return new instance of DatePickerDialog
        return dialog;
    }


    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button myButton;
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_game, container, false);
        myButton = (Button) rootView.findViewById(R.id.done_button);
        myButton.setOnClickListener(this);




        String [] teams = getResources().getStringArray(R.array.team_names_array);
        final AutoCompleteTextView autoCompleteTextViewTeam1 = (AutoCompleteTextView) rootView.findViewById(R.id.team1_auto);
        final AutoCompleteTextView autoCompleteTextViewTeam2 = (AutoCompleteTextView) rootView.findViewById(R.id.team2_auto);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, teams);

        autoCompleteTextViewTeam1.setAdapter(adapter);
        autoCompleteTextViewTeam1.setThreshold(1);
        autoCompleteTextViewTeam2.setAdapter(adapter);
        autoCompleteTextViewTeam2.setThreshold(1);

        return rootView;

    }


    @Override
    public void onClick(View view) {
        MainActivity main = (MainActivity)getActivity();
        assert main != null;
        AutoCompleteTextView team_1 = (AutoCompleteTextView)getView().findViewById(R.id.team1_auto);
        AutoCompleteTextView team_2 = (AutoCompleteTextView)getView().findViewById(R.id.team2_auto);
        TextView score_1 = (TextView)getView().findViewById(R.id.score1_edit);
        TextView score_2 = (TextView)getView().findViewById(R.id.score2_edit);

        main.updateTeams(team_1.getText().toString(),team_2.getText().toString());
        main.updateScores(score_1.getText().toString(),score_2.getText().toString());
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();

    }


}

