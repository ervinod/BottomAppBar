package com.vp.bottomappbar.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vp.bottomappbar.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddedMemberFragment extends Fragment {


    public AddedMemberFragment() {

    }

    public static AddedMemberFragment newInstance() {

        AddedMemberFragment fragment = new AddedMemberFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_added_member, container, false);
    }

}
