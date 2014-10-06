package edu.ucsb.cs.cs185.a_hoang.a_hoanggpscam;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Anthony on 5/10/14.
 */
public class DialogFrag extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);


        dialog.setTitle(getResources().getString(R.string.help));

        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialogue_layout, container, false);
        TextView software = (TextView)rootView.findViewById(R.id.software_text);
        software.setText(rootView.getResources().getString(R.string.version) +Integer.toString(android.os.Build.VERSION.SDK_INT));
        return rootView;
    }

}
