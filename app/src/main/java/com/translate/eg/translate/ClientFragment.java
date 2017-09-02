package com.translate.eg.translate;

/**
 * Created by Eugene Galkine on 8/31/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class ClientFragment extends Fragment
{
    public static final String[] languages = new String[]{"English", "Spanish", "French"};

    private Spinner fspinner;
    private Spinner tspinner;
    private ProgressBar progressBar;
    private TextView outputText;
    private EditText inputText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //inflate and then we get everything we need out of the view here in the create function
        View rootView = inflater.inflate(R.layout.fragment_client, container, false);

        //get all the views we need
        fspinner = (Spinner) rootView.findViewById(R.id.fromSpinner);
        tspinner = (Spinner) rootView.findViewById(R.id.toSpinner);
        progressBar = (ProgressBar) rootView.findViewById(R.id.clientProgressBar);
        outputText = (TextView) rootView.findViewById(R.id.outputView);
        inputText = (EditText) rootView.findViewById(R.id.inputText);

        //fill the drop down with an adapter
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, languages);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        tspinner.setAdapter(spinnerArrayAdapter);
        fspinner.setAdapter(spinnerArrayAdapter);
        fspinner.setSelection(0);
        tspinner.setSelection(1);

        //make the spinning progress bar invisible because we don't need it right now
        progressBar.setVisibility(View.GONE);

        //add listener to the button
        rootView.findViewById(R.id.button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                clickSubmit();
            }
        });

        return rootView;
    }

    private void clickSubmit()
    {
        //get inputs
        int fromId = fspinner.getSelectedItemPosition();
        int toId = tspinner.getSelectedItemPosition();
        String input = inputText.getText().toString();

        //spin up the progress bar
        progressBar.setVisibility(View.VISIBLE);

        //hide the keyboard
        InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

        //connect to server and send the our request
        NetConnector.connectAsClient(this, fromId, toId, input);
    }

    public void setResult(final String in)
    {
        //run on UI thread
        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                //hide the progress bar
                progressBar.setVisibility(View.GONE);

                //set the result field to our result
                outputText.setText(getString(R.string.translation_message, in));
            }
        });
    }
}
