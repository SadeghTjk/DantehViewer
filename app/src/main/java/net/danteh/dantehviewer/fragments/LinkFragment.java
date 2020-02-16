package net.danteh.dantehviewer.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import net.danteh.dantehviewer.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LinkFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LinkFragment} factory method to
 * create an instance of this fragment.
 */
public class LinkFragment extends Fragment {

    MaterialButton submit_btn;
    TextInputEditText urlname_input, url_input, linkShowCount;
    TextInputLayout linkCounterInput;
    public String urlname, url, strEnteredVal;
    int num;


    private OnFragmentInteractionListener mListener;

    public LinkFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_link, container, false);

        submit_btn = v.findViewById(R.id.sendLink_btn);
        url_input = v.findViewById(R.id.url_input);
        urlname_input = v.findViewById(R.id.urlname_input);
        linkShowCount = v.findViewById(R.id.link_show_count);
        linkCounterInput = v.findViewById(R.id.link_counter_input);

        //check for view number between 0-200 per day
        linkShowCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                strEnteredVal = linkShowCount.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!strEnteredVal.equals("")) {
                    num = Integer.parseInt(strEnteredVal);
                    if (num < 201) {
                        linkShowCount.setTextColor(Color.BLACK);
                        linkCounterInput.setError(null);
                    } else {
                        linkShowCount.setTextColor(Color.RED);
                        linkCounterInput.setError("0 تا 200");
                    }
                }
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urlname = urlname_input.getText().toString();
                url = url_input.getText().toString();
//                if (mListener != null) {
//                    mListener.onFragmentInteraction(urlname, url);
//                }
                Log.e("link submit: ", "onClick: "+url+"\n"+urlname );
                ParseObject alink = new ParseObject("Links");
                alink.put("urlName",urlname);
                alink.put("URL",url);
                alink.put("userId", ParseUser.getCurrentUser().getObjectId());
                alink.put("viewCount",1);
                alink.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            Toast.makeText(requireActivity(), "لینک شما اضافه شد", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(requireActivity(), ""+e.getCode() +" : " +e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String urlname, String url);
    }
}
