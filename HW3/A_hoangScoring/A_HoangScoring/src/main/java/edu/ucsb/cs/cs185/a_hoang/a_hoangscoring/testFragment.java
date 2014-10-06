package edu.ucsb.cs.cs185.a_hoang.a_hoangscoring;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;



public class testFragment extends Fragment {


    /*public testFragment() {
        // Required empty public constructor
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        LinearLayout linLayout = new LinearLayout(getActivity());
        Button b = new Button(getActivity());
        b.setText("hello");
        linLayout.addView(b);

        final TextView tv = new TextView(getActivity());
        tv.setText("Text Entry");
        linLayout.addView(tv);

        View.OnClickListener onclick = new View.OnClickListener(){
            @Override
        public void onClick(View view){
                Button bt=(Button) view;
                tv.setText(bt.getText());
            }
        };

        b.setOnClickListener(onclick);

        return linLayout;
    }


}
