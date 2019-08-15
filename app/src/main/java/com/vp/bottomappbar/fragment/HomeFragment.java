package com.vp.bottomappbar.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vp.bottomappbar.R;
import com.vp.bottomappbar.activity.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

private TextView tv_find_relation;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(){
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity)getActivity()).changeToolbar("Home");
        ((HomeActivity)getActivity()).manageSearchView();

        initViews(view);
    }

    private void initViews(View view) {

        tv_find_relation=view.findViewById(R.id.tv_find_relation);

        bindControls();
    }

    private void bindControls() {
        tv_find_relation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((HomeActivity)getActivity()).replaceFragment(AddMemberFragment.newInstance());

            }
        });

    }
}
