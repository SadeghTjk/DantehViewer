package net.danteh.dantehviewer.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import net.danteh.dantehviewer.ApiCaller;
import net.danteh.dantehviewer.Links;
import net.danteh.dantehviewer.MainActivity;
import net.danteh.dantehviewer.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

import static net.danteh.dantehviewer.MainActivity.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WebViewFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WebViewFragment#} factory method to
 * create an instance of this fragment.
 */
public class WebViewFragment extends Fragment {

    public Retrofit retrofit = null;
    private WebView webView;
    NavigationView navigationView;
    ProgressDialog progressBar;
    List<Links> linksList = new ArrayList<>();
    TextView headerCoin;
    int i = 0;
    int point = 1;
    private OnFragmentInteractionListener mListener;

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("دریافت لینک ها...");
        progressBar.show();
        ApiCaller.getLinks(getActivity(),retrofit,progressBar);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_web_view, container, false);
        webView = v.findViewById(R.id.wv);
        webView.getSettings().setJavaScriptEnabled(true);


        navigationView = getActivity().findViewById(R.id.navigation);
        View headerView = navigationView.getHeaderView(0);
        headerCoin = headerView.findViewById(R.id.point_counter);



       // Toast.makeText(getActivity(), "SALAM:  "+linksList.size(), Toast.LENGTH_LONG).show();

        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                Log.e(TAG, "onPageFinished: LOADED");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        point++;
                        if (i < linksList.size()) {
                            ApiCaller.updatePoints(getActivity(),retrofit,1,point);
                            Toast.makeText(getActivity(), "یک امتیاز اضافه شد!", Toast.LENGTH_SHORT).show();
                            headerCoin.setText(point+ " امتیاز ");
                            Log.e(TAG, "run: "+linksList.get(i).getUrl()+"/?utm_source=dantehView&utm_medium=app" );
                            webView.loadUrl(linksList.get(i).getUrl()+"/?utm_source=dantehView&utm_medium=app");
                            i++;
//                            if (mListener != null) {
//                                mListener.onCoinUpdates(coin);
//                            }
                        }
                    }

                }, 20000);
            }
        });


        String number = headerCoin.getText().toString().replaceAll("\\D+","");
        point = Integer.parseInt(number)+1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                linksList = ApiCaller.dataLinks;
                webView.loadUrl(linksList.get(i).getUrl()+"/?utm_source=dantehView&utm_medium=app");
                i++;
            }
        },1000);


        return v;
    }


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


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCoinUpdates(int coin);
    }
}
