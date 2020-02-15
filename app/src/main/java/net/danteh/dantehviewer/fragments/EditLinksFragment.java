package net.danteh.dantehviewer.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.danteh.dantehviewer.Links;
import net.danteh.dantehviewer.R;
import net.danteh.dantehviewer.adapters.LinkRVAdapter;

import java.util.ArrayList;
import java.util.List;

public class EditLinksFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    LinkRVAdapter adapter;
    List<Links> testlink = new ArrayList<>();
    public EditLinksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_edit_links, container, false);

        recyclerView = v.findViewById(R.id.links_rv);

        testlink.add(new Links(1,1,"صفحه اصلی","some url"));
        testlink.add(new Links(2,1,"صفحه محصولات","some url"));
        testlink.add(new Links(3,1,"لباسشویی اسنوا","some url"));
        testlink.add(new Links(3,1,"لباسشویی ال جی ","some url"));
        testlink.add(new Links(3,1,"یخچال ساید","some url"));
        testlink.add(new Links(3,1,"گوشی سامسونگ","some url"));

        adapter = new LinkRVAdapter(requireActivity(),testlink);

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

         recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false));
         recyclerView.setAdapter(adapter);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
