package net.danteh.dantehviewer.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import net.danteh.dantehviewer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFragment extends Fragment {
    WebView webView;

    public GuideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_guide, container, false);

        webView = v.findViewById(R.id.wv);

        WebViewClient client = new WebViewClient();
        webView.setWebViewClient(client);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.loadUrl("file:///android_asset/index.html");
        return v;
    }

}
