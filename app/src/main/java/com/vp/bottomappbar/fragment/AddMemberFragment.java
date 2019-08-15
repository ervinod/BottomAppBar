package com.vp.bottomappbar.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vp.bottomappbar.R;
import com.vp.bottomappbar.activity.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMemberFragment extends Fragment {

      private TabLayout tab_member;
      private Activity activity;


    public AddMemberFragment() {
        // Required empty public constructor
    }

    public static AddMemberFragment newInstance() {

        Bundle args = new Bundle();

        AddMemberFragment fragment = new AddMemberFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_member, container, false);
        tab_member=view.findViewById(R.id.tab_member);

        activity=getActivity();
        return view;
    }

    private void replaceFragment(Fragment fragment) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((HomeActivity)getActivity()).changeToolbar("Add Members");
        ((HomeActivity)getActivity()).manageSearchView();

        //create tabs title
        tab_member.addTab(tab_member.newTab().setText("Contacts"));
        tab_member.addTab(tab_member.newTab().setText("Added Member"));

        replaceFragment(ContactsFragment.newInstance());


        tab_member.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {

                    replaceFragment(ContactsFragment.newInstance());
                } else if(tab.getPosition() == 1){

                   replaceFragment(AddedMemberFragment.newInstance());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
