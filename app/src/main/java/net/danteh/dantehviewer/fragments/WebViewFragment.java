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
import android.widget.ProgressBar;
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
import com.parse.ParseUser;

import net.danteh.dantehviewer.DataLinks;
import net.danteh.dantehviewer.Links;
import net.danteh.dantehviewer.R;

import java.util.ArrayList;
import java.util.Collection;
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
    ProgressBar progressBar;
    MutableLiveData<DataLinks> liveLinks;
    MaterialButton start_btn;
    List<Links> linksList = new ArrayList<>();
    TextView headerCoin,linksCounter;
    List<ParseObject> linksObject = new ArrayList<>(), alreadyViewed = new ArrayList<>();
    int i = 0, count =0,b;
    int point = 1;
    String AD_ID;
    ParseUser user;
    private OnFragmentInteractionListener mListener;

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        user = ParseUser.getCurrentUser();
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
        start_btn = v.findViewById(R.id.start_btn);
        progressBar = v.findViewById(R.id.progress_refresh);
        linksCounter = v.findViewById(R.id.avaiable_links_count);

        navigationView = getActivity().findViewById(R.id.navigation);
        View headerView = navigationView.getHeaderView(0);
        headerCoin = headerView.findViewById(R.id.point_counter);

        updateSiteLinks();

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

        headerCoin.setOnClickListener(new View.OnClickListener() {
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
                            linksCounter.setText(String.valueOf(count));
                            count--;
                            user.getRelation("viewedLinks").add(linksObject.get(i));
                            user.saveInBackground();
                            webView.loadUrl(linksObject.get(i).getString("URL") + "/?utm_source=dantehView&utm_medium=app");
                            i++;
//                            if (mListener != null) {
//                                mListener.onCoinUpdates(coin);
//                            }
                        }//else i=0;
                    }

                }, 3000);
            }
        });

//
        String number = headerCoin.getText().toString().replaceAll("\\D+", "");
        point = Integer.parseInt(number) + 1;

        start_btn.setOnClickListener(view -> {
            //linksList = ApiCaller.dataLinks;
            if(i>linksObject.size()-1){
                progressBar.setVisibility(View.VISIBLE);
                updateSiteLinks();
                Toast.makeText(getActivity(), "لینک", Toast.LENGTH_SHORT).show();
            }
            else {
                progressBar.setVisibility(View.INVISIBLE);
                webView.loadUrl(linksObject.get(i).getString("URL") + "/?utm_source=dantehview&utm_medium=app");
                i++;
                count--;
            }
        });


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

    private void updateSiteLinks(){
        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query2 = user.getRelation("viewedLinks").getQuery();
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    alreadyViewed = objects;
                    Log.e(TAG, "done: "+alreadyViewed.size() + " : "+alreadyViewed.get(0).getString("URL") );
                }
                else
                    Toast.makeText(getActivity(), "query2 : " + e.getCode() + " : " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        ParseQuery<ParseObject> query = new ParseQuery<>("Links");
        query.whereNotEqualTo("createdBy", ParseUser.getCurrentUser());
        Collection<String> already = new ArrayList<>();

        for(int g=0;g<alreadyViewed.size();g++)
            already.add(alreadyViewed.get(g).getObjectId());

        query.whereNotContainedIn("viewedLinks",already);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    linksObject = objects;
                    count = objects.size()-1;
                    linksCounter.setText(String.valueOf(count));
                    progressBar.setVisibility(View.INVISIBLE);

                }
                else
                    Toast.makeText(getActivity(), "" + e.getCode() + " : " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}
