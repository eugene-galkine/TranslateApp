package com.translate.eg.translate;

/**
 * Created by Eugene Galkine on 9/1/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TranslatorFragment extends Fragment
{
    private ProgressBar progressBar;
    private TextView requestView;
    private EditText translationText;
    private Button submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_translator, container, false);

        //get all the views we need
        progressBar = (ProgressBar) rootView.findViewById(R.id.translatorProgressBar);
        requestView = (TextView) rootView.findViewById(R.id.translatorRequestView);
        translationText = (EditText) rootView.findViewById(R.id.translatorInput);
        submitButton = (Button) rootView.findViewById(R.id.translatorSubmitButton);

        //hide the spinning progress bar
        progressBar.setVisibility(View.GONE);

        //add button listeners
        rootView.findViewById(R.id.requestButton).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clickRequest();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clickSubmit();
            }
        });

        return rootView;
    }

    private void clickRequest()
    {
        //make the progress bar visible
        progressBar.setVisibility(View.VISIBLE);
    }

    private void clickSubmit()
    {
        //get the result to send back
        String input = translationText.getText().toString();


    }

}
