package net.danteh.dantehviewer.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import net.danteh.dantehviewer.DataLinks;
import net.danteh.dantehviewer.Links;
import net.danteh.dantehviewer.R;

import java.util.ArrayList;
import java.util.List;

import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellAdShowListener;
import ir.tapsell.sdk.TapsellShowOptions;
import retrofit2.Retrofit;

import static net.danteh.dantehviewer.MainActivity.TAG;

public class WebViewFragment extends Fragment {

    public Retrofit retrofit = null;
    private WebView webView;
    NavigationView navigationView;
    ProgressDialog progressBar;
    MutableLiveData<DataLinks> liveLinks;
    MaterialButton refresh_btn;
    List<Links> linksList = new ArrayList<>();
    TextView headerCoin;
    List<ParseObject> linksObject = new ArrayList<>();
    int i = 0;
    int point = 1;
    String AD_ID;
    private OnFragmentInteractionListener mListener;

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        progressBar = new ProgressDialog(getActivity());
//        progressBar.setMessage("دریافت لینک ها...");
//        progressBar.show();
        //ApiCaller.getLinks(getActivity(),retrofit,progressBar);

//        model.getNewsRepository().observe(requireActivity(), linksResponse -> {
//
//            linksList= linksResponse.getRecords();
//            Toast.makeText(requireActivity(), "SALAM:  "+linksList.size()+ linksResponse.getRecords(), Toast.LENGTH_LONG).show();
//        });
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("کسب امتیاز");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_web_view, container, false);
        webView = v.findViewById(R.id.wv);
        webView.getSettings().setJavaScriptEnabled(true);
        refresh_btn = v.findViewById(R.id.refresh_btn);

        navigationView = getActivity().findViewById(R.id.navigation);
        View headerView = navigationView.getHeaderView(0);
        headerCoin = headerView.findViewById(R.id.point_counter);

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Links");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null)
                    linksObject = objects;
                else
                    Toast.makeText(getActivity(), "" + e.getCode() + " : " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
//        LinksViewModel linksViewModel = ViewModelProviders.of(requireActivity()).get(LinksViewModel.class);
////        linksViewModel.getLinks().observe(requireActivity(), links -> {
////            linksList.addAll(links.getRecords());
////            Toast.makeText(requireActivity(), "link added?? "+links.getRecords().size() + "\n" + linksList.size(), Toast.LENGTH_LONG).show();
////        });


        // Toast.makeText(getActivity(), "SALAM:  "+linksList.size(), Toast.LENGTH_LONG).show();
        Tapsell.requestAd(getActivity(),
                "5e4fd6aa7e2d1e000164265a",
                new TapsellAdRequestOptions(),
                new TapsellAdRequestListener() {
                    @Override
                    public void onAdAvailable(String adId) {
                        AD_ID = adId;
                        Log.e(TAG, "onAdAvailable: " );
                    }

                    @Override
                    public void onError(String message) {
                        Log.e(TAG, "onAdError: " );
                    }
                });
        TapsellShowOptions showOptions = new TapsellShowOptions();
        showOptions.setBackDisabled(true);
        showOptions.setImmersiveMode(true);
        showOptions.setShowDialog(true);
        headerCoin.setTextSize(25);

        refresh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tapsell.showAd(getActivity(),
                        "5e4fd6aa7e2d1e000164265a",
                        AD_ID,
                        showOptions,
                        new TapsellAdShowListener() {
                            @Override
                            public void onOpened() {
                            }

                            @Override
                            public void onClosed() {
                            }

                            @Override
                            public void onError(String message) {
                            }

                            @Override
                            public void onRewarded(boolean completed) {
                                if (completed)
                                    headerCoin.setText("999 امتیاز");

                            }
                        });
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                Log.e(TAG, "onPageFinished: LOADED");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        point++;
                        if (i < linksObject.size()) {
                            //ApiCaller.updatePoints(getActivity(),retrofit,1,point);
                            Toast.makeText(getActivity(), "یک امتیاز اضافه شد!", Toast.LENGTH_SHORT).show();
                            headerCoin.setText(point + " امتیاز ");
                            webView.loadUrl(linksObject.get(i).getString("URL") + "/?utm_source=dantehView&utm_medium=app");
                            i++;
//                            if (mListener != null) {
//                                mListener.onCoinUpdates(coin);
//                            }
                        }
                    }

                }, 3000);
            }
        });

//
        String number = headerCoin.getText().toString().replaceAll("\\D+", "");
        point = Integer.parseInt(number) + 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //linksList = ApiCaller.dataLinks;
                webView.loadUrl(linksObject.get(i).getString("URL") + "/?utm_source=dantehview&utm_medium=app");
                i++;
            }
        }, 1000);


        return v;
    }


    @Override
    public void onAttach(@NonNull Context context) {
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
