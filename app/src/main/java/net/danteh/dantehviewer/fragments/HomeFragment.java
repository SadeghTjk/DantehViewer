package net.danteh.dantehviewer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.danteh.dantehviewer.R;
import net.danteh.dantehviewer.adapters.NewsAdapter;

public class HomeFragment extends Fragment {

    RecyclerView newsRv;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        newsRv = v.findViewById(R.id.news_rv);

        newsRv.setLayoutManager(new LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false));
        newsRv.setAdapter(new NewsAdapter(requireContext()));
        return v;
    }
}
