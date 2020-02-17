package net.danteh.dantehviewer.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import net.danteh.dantehviewer.R;
import net.danteh.dantehviewer.adapters.LinkTabAdapter;

public class LinkHomeFragment extends Fragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    Fragment linkFragment,editLinkFragment;
   // private ViewPager2 viewPager;
    private OnFragmentInteractionListener mListener;

    public LinkHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_link_home, container, false);
        getActivity().setTitle("مدیریت لینک ها");
        viewPager = v.findViewById(R.id.viewpager);
        linkFragment = new LinkFragment();
        editLinkFragment = new EditLinksFragment();
        tabLayout = v.findViewById(R.id.result_tabs);

        LinkTabAdapter adapter = new LinkTabAdapter(getChildFragmentManager());
        adapter.addFragment(linkFragment, "ثبت لینک");
        adapter.addFragment(editLinkFragment, "ویرایش لینک ها");
        viewPager.setAdapter(adapter);
        // Set Tabs

        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.getTabAt(1).setIcon(R.drawable.ic_edit_tools);
//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_up);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                Toast.makeText(requireActivity(),
                        "Selected page position: " + tab.getPosition()+ viewPager.getCurrentItem()+getChildFragmentManager().getFragments().get(tab.getPosition()).toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    private void setupViewPager(ViewPager viewPager) {

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
