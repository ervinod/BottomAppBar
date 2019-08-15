package com.vp.bottomappbar.fragment;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import com.vp.bottomappbar.R;
import com.vp.bottomappbar.interfaces.SearchListener;
import com.vp.bottomappbar.activity.HomeActivity;
import com.vp.bottomappbar.adapter.ContactDatabase;
import com.vp.bottomappbar.adapter.SelectUserAdapter;
import com.vp.bottomappbar.model.SelectUser;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment implements SearchListener {

    Activity activity;
    List<SelectUser> mainInfo=new ArrayList<>();
    ContactDatabase mydb;
    SelectUserAdapter suAdapter;

    RecyclerView recyclerView;


    WeakReference<Context> mContextWeakReference;

    String regex = "^[a-z A-Z]+$";
    Pattern pattern = Pattern.compile(regex);
    private static final String TAG = "MainActivity";

    ArrayList<SelectUser> specialcharlist= new ArrayList<>();
    ArrayList<SelectUser> alphabeticalcharlist = new ArrayList<>();

    private ArrayList<SelectUser> mSectionList;

    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    SearchListener searchListener;
    ProgressBar progressBar;

    public ContactsFragment() {

    }


    public static ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=getActivity();
        searchListener = this;


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[grantResults.length-1] == PackageManager.PERMISSION_GRANTED) {
            Log.d("permission", "Contact permission granted.");
            loadContacts();
        } else {
            Log.i("Permission", "Contact permission NOT granted.");
            Toast.makeText(activity,"Permission is not Granted",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_contacts, container, false);

        ((HomeActivity)getActivity()).setSetSearchListener(searchListener);

        recyclerView = view.findViewById(R.id.contacts_list);
        progressBar = view.findViewById(R.id.progressBar);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSectionList = new ArrayList<>();
        mydb  = new ContactDatabase(activity);

        checkContactPermission();

    }

    private void checkContactPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(activity,Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }else{
            loadContacts();
        }

    }
    private void loadContacts() {
        progressBar.setVisibility(View.VISIBLE);
        new ConttactLoader().execute();
    }

    @Override
    public void textChanged(String query) {
        suAdapter.filter(query);


    }

    public class ConttactLoader extends AsyncTask<Void, Void, List<SelectUser>> {

        @Override
        protected List<SelectUser> doInBackground(Void... voids) {
            List<SelectUser> MHList = mydb.getData();
            return MHList;
        }

        @Override
        protected void onPostExecute(List<SelectUser> selectUsers) {
            if (selectUsers.isEmpty()==false){

                for(int i=0;i< selectUsers.size();i++){
                    if(pattern.matcher(String.valueOf(selectUsers.get(i).getName().charAt(0)).toUpperCase()).matches()){
                        alphabeticalcharlist.add(selectUsers.get(i));
                    }else{
                        specialcharlist.add(selectUsers.get(i));
                        specialcharlist.get(i).setSection(false);
                    }
                }

                // Log.e(TAG, "onCreate: "+selectUsers.size() );
                // Log.e(TAG, "onCreate: "+alphabeticalcharlist.size());
                // Log.e(TAG, "onCreate: "+specialcharlist.size() );

                getHeaderListLatter(alphabeticalcharlist);

                suAdapter = new SelectUserAdapter(activity,  mSectionList,mContextWeakReference,alphabeticalcharlist);

                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                recyclerView.setAdapter(suAdapter);
                progressBar.setVisibility(View.GONE);

            }
        }
    }



    private void getHeaderListLatter(ArrayList<SelectUser> alphabeticalcharlist) {

        Collections.sort(alphabeticalcharlist, new Comparator<SelectUser>() {
            @Override
            public int compare(SelectUser user1, SelectUser user2) {
                return String.valueOf(user1.getName().charAt(0)).toUpperCase().compareTo(String.valueOf(user2.getName().charAt(0)).toUpperCase());
            }
        });

        String lastHeader = "";

        int size = alphabeticalcharlist.size();

        for (int i = 0; i < size; i++) {

            SelectUser user = alphabeticalcharlist.get(i);
            String header = String.valueOf(user.getName().charAt(0)).toUpperCase();

            if (!TextUtils.equals(lastHeader, header)) {
                lastHeader = header;
                mSectionList.add(new SelectUser(header,true));
            }
            user.setSection(false);
            mSectionList.add(user);

        }


        if (specialcharlist.size()!=0){
            String lastHeaderend = "";
            for (int i = 0; i < specialcharlist.size(); i++) {

                SelectUser user1 = specialcharlist.get(i);
                String header1 = "#";

                if (!TextUtils.equals(lastHeaderend, header1)) {
                    lastHeaderend = header1;
                    mSectionList.add(new SelectUser(header1,true));
                }
                user1.setSection(false);
                mSectionList.add(user1);

            }

        }
    }
}
