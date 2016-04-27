package com.example.android.exampal;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {


    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        Button Fetch = (Button) view.findViewById(R.id.fetchbutton);
        final Spinner spinYear = (Spinner)view.findViewById(R.id.spinner1);
        final Spinner spinBranch = (Spinner) view.findViewById(R.id.spinner2);

        Fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posYear=spinYear.getSelectedItemPosition();
                int posBranch=spinBranch.getSelectedItemPosition();

                switch(posYear){

                    case 1:switch (posBranch)
                        {
                            case 1:
                                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.ncuindia.edu/images/pdf/News_Letter/Vol-53-March%202016.pdf"));
                                startActivity(intent1);
                                break;

                                default:Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                                    startActivity(intent2);
                                    break;
                        }
                        break;
                        default:Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                            startActivity(intent2);
                            break;

                }
            }
        });
        return view;
    }

}
