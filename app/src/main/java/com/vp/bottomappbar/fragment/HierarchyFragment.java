package com.vp.bottomappbar.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.vp.bottomappbar.R;
import com.vp.bottomappbar.activity.HomeActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HierarchyFragment extends Fragment {


    public HierarchyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static HierarchyFragment newInstance(){
        HierarchyFragment fragment = new HierarchyFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hierarchy, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((HomeActivity)getActivity()).changeToolbar("Hierarchy List");
        ((HomeActivity)getActivity()).manageSearchView();

    }
}
