package net.danteh.dantehviewer.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

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
import net.danteh.dantehviewer.DataLinks;
import net.danteh.dantehviewer.Links;
import net.danteh.dantehviewer.LinksViewModel;
import net.danteh.dantehviewer.MainActivity;
import net.danteh.dantehviewer.R;
import net.danteh.dantehviewer.test.TestViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;

import static net.danteh.dantehviewer.MainActivity.TAG;

public class WebViewFragment extends Fragment {

    public Retrofit retrofit = null;
    private WebView webView;
    NavigationView navigationView;
    ProgressDialog progressBar;
    MutableLiveData<DataLinks> liveLinks;
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
        View v =inflater.inflate(R.layout.fragment_web_view, container, false);
        webView = v.findViewById(R.id.wv);
        webView.getSettings().setJavaScriptEnabled(true);
        TestViewModel testViewModel = ViewModelProviders.of(requireActivity()).get(TestViewModel.class);
        TestViewModel model = new ViewModelProvider(requireActivity()).get(TestViewModel.class);
        model.init();
        final Observer<DataLinks> nameObserver = new Observer<DataLinks>() {
            @Override
            public void onChanged(@Nullable final DataLinks linksResponse) {
                // Update the UI, in this case, a TextView.
                Toast.makeText(requireActivity(), "SALAM:  "+linksResponse.getRecords().size(), Toast.LENGTH_LONG).show();
            }
        };
        model.getNewsRepository().observe(requireActivity(), nameObserver);

        navigationView = getActivity().findViewById(R.id.navigation);
        View headerView = navigationView.getHeaderView(0);
        headerCoin = headerView.findViewById(R.id.point_counter);

//        LinksViewModel linksViewModel = ViewModelProviders.of(requireActivity()).get(LinksViewModel.class);
////        linksViewModel.getLinks().observe(requireActivity(), links -> {
////            linksList.addAll(links.getRecords());
////            Toast.makeText(requireActivity(), "link added?? "+links.getRecords().size() + "\n" + linksList.size(), Toast.LENGTH_LONG).show();
////        });



       // Toast.makeText(getActivity(), "SALAM:  "+linksList.size(), Toast.LENGTH_LONG).show();

//        webView.setWebViewClient(new WebViewClient() {
//            public void onPageFinished(WebView view, String url) {
//                Log.e(TAG, "onPageFinished: LOADED");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        point++;
//                        if (i < linksList.size()) {
//                            ApiCaller.updatePoints(getActivity(),retrofit,1,point);
//                            Toast.makeText(getActivity(), "یک امتیاز اضافه شد!", Toast.LENGTH_SHORT).show();
//                            headerCoin.setText(point+ " امتیاز ");
//                            webView.loadUrl(linksList.get(i).getUrl()+"/?utm_source=dantehView&utm_medium=app");
//                            i++;
////                            if (mListener != null) {
////                                mListener.onCoinUpdates(coin);
////                            }
//                        }
//                    }
//
//                }, 3000);
//            }
//        });
//
//
//        String number = headerCoin.getText().toString().replaceAll("\\D+","");
//        point = Integer.parseInt(number)+1;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                //linksList = ApiCaller.dataLinks;
//                webView.loadUrl(linksList.get(i).getUrl()+"/?utm_source=dantehview&utm_medium=app");
//                i++;
//            }
//        },1000);


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
