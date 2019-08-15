package com.vp.bottomappbar.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.vp.bottomappbar.util.Consts;
import com.vp.bottomappbar.R;
import com.vp.bottomappbar.interfaces.SearchListener;
import com.vp.bottomappbar.fragment.AddMemberFragment;
import com.vp.bottomappbar.fragment.ChatFragment;
import com.vp.bottomappbar.fragment.HierarchyFragment;
import com.vp.bottomappbar.fragment.HomeFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    public static FragmentManager manager;
    boolean doubleBackToExitPressedOnce = false;
    private BottomAppBar bottom_appbar;
    private Context mContext;
    private Toolbar layout_toolBar;
    private LinearLayout ll_bottom_home, ll_bottom_member, ll_bottom_list, ll_bottom_chat;
    private ImageView iv_bottom_home, iv_bottom_member, iv_bottom_list, iv_bottom_chat;
    private TextView tv_bottom_home, tv_bottom_member, tv_bottom_list, tv_bottom_chat;
    private TextView tvToolbarTitle;
    SearchView search;
    SearchListener mSearchListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mContext = getApplicationContext();
        manager = getSupportFragmentManager();

        initViews();

        bindControls();

    }

    private void initViews(){
        bottom_appbar = findViewById(R.id.bottom_appbar);

        layout_toolBar = findViewById(R.id.layout_toolBar);
        tvToolbarTitle = findViewById(R.id.tvToolbarTitle);
        search = findViewById(R.id.searchView);

        ll_bottom_home = findViewById(R.id.layout_bottom_home);
        ll_bottom_member = findViewById(R.id.layout_bottom_member);
        ll_bottom_list = findViewById(R.id.layout_bottom_list);
        ll_bottom_chat = findViewById(R.id.layout_bottom_chat);

        iv_bottom_home = findViewById(R.id.iv_bottom_home);
        iv_bottom_member = findViewById(R.id.iv_bottom_member);
        iv_bottom_list = findViewById(R.id.iv_bottom_list);
        iv_bottom_chat = findViewById(R.id.iv_bottom_chat);

        tv_bottom_home = findViewById(R.id.tv_bottom_home);
        tv_bottom_member = findViewById(R.id.tv_bottom_member);
        tv_bottom_list = findViewById(R.id.tv_bottom_list);
        tv_bottom_chat = findViewById(R.id.tv_bottom_chat);
    }


    private void selectBottomItem(String title) {

        iv_bottom_home.setBackground(getResources().getDrawable(R.drawable.bottom_home));
        tv_bottom_home.setTextColor(getResources().getColor(R.color.grey));

        iv_bottom_member.setBackground(getResources().getDrawable(R.drawable.bottom_add_members));
        tv_bottom_member.setTextColor(getResources().getColor(R.color.grey));

        iv_bottom_list.setBackground(getResources().getDrawable(R.drawable.bottom_hierarchy_list));
        tv_bottom_list.setTextColor(getResources().getColor(R.color.grey));

        iv_bottom_chat.setBackground(getResources().getDrawable(R.drawable.bottom_chat));
        tv_bottom_chat.setTextColor(getResources().getColor(R.color.grey));


        switch (title) {
            case Consts.HOME:

                iv_bottom_home.setBackground(getResources().getDrawable(R.drawable.bottom_home_active));
                tv_bottom_home.setTextColor(getResources().getColor(R.color.selected_color));

                break;
            case Consts.ADD_MEMBERS:

                iv_bottom_member.setBackground(getResources().getDrawable(R.drawable.bottom_add_members_active));
                tv_bottom_member.setTextColor(getResources().getColor(R.color.selected_color));
                break;

            case Consts.HIRARCHY_LIST:

                iv_bottom_list.setBackground(getResources().getDrawable(R.drawable.bottom_hierarchy_list_active));
                tv_bottom_list.setTextColor(getResources().getColor(R.color.selected_color));

                break;

            case Consts.CHAT:

                iv_bottom_chat.setBackground(getResources().getDrawable(R.drawable.bottom_chat_active));
                tv_bottom_chat.setTextColor(getResources().getColor(R.color.selected_color));

                break;
        }
    }

    private void bindControls() {

        ll_bottom_home.setOnClickListener(this);
        ll_bottom_member.setOnClickListener(this);
        ll_bottom_list.setOnClickListener(this);
        ll_bottom_chat.setOnClickListener(this);

        addFragment(HomeFragment.newInstance());
        selectBottomItem(Consts.HOME);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String stext) {

                if(mSearchListener!=null){
                    mSearchListener.textChanged(stext);

                }
                return false;
            }
        });



        search.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tvToolbarTitle.setVisibility(View.GONE);
                } else {
                    search.setIconified(true);
                    tvToolbarTitle.setVisibility(View.VISIBLE);
                }
            }
        });

    }


    public void replaceFragment(Fragment fragment) {
        Log.d("replaceFragment", "replaceFragment: " + getSupportFragmentManager().getBackStackEntryCount());
        String backStateName = fragment.getClass().getSimpleName();
        String fragmentTag = backStateName;

        FragmentManager manager = this.getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(fragmentTag) == null) {
            //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.container_main, fragment, fragmentTag);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }


    public void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.container_main, fragment);
        ft.commit();
    }

    public void popAllStack() {
        FragmentManager fm = this.getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); i++) {
            fm.popBackStack();
        }
    }

    public void popBackStack() {
        FragmentManager fm = this.getSupportFragmentManager();
        if(fm.getBackStackEntryCount()>0){
            fm.popBackStack();
        }
    }


    public void changeToolbar(String title) {

        tvToolbarTitle.setText(title);
        //setSupportActionBar(layout_toolBar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        layout_toolBar.setTitle(title);
        // layout_toolBar.setVisibility(View.VISIBLE);
    }

    public void manageToolbar() {
        layout_toolBar.setVisibility(View.GONE);
    }

    public void manageSearchView(){
        Fragment fragment = manager.findFragmentById(R.id.container_main);

        if (fragment != null && fragment instanceof AddMemberFragment) {
            search.setVisibility(View.VISIBLE);

        }else {
            search.setVisibility(View.GONE);

        }
    }

    public void CustomBackPressed() {
        //Checking for fragment count on backstack
        if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            finish();
            return;
        }
    }


    @Override
    public void onBackPressed() {

        Fragment fragment = manager.findFragmentById(R.id.container_main);

        if (fragment != null && fragment instanceof AddMemberFragment) {
            popBackStack();
            selectBottomItem(Consts.HOME);

        }else if (fragment!=null && fragment instanceof HierarchyFragment) {
            popBackStack();
            selectBottomItem(Consts.HOME);

        }else if (fragment!=null && fragment instanceof ChatFragment  ) {
            popBackStack();
            selectBottomItem(Consts.HOME);

        } else if (fragment != null && fragment instanceof HomeFragment) {

            CustomBackPressed();
        } else

            super.onBackPressed();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.layout_bottom_home:
                selectBottomItem(Consts.HOME);
                showFragment("1");
                break;

            case R.id.layout_bottom_member:
                selectBottomItem(Consts.ADD_MEMBERS);
                showFragment("2");
                break;
            case R.id.layout_bottom_list:
                selectBottomItem(Consts.HIRARCHY_LIST);
                showFragment("3");

                break;

            case R.id.layout_bottom_chat:
                selectBottomItem(Consts.CHAT);
                showFragment("4");
                break;

        }

    }

    public void showFragment(String frag) {
        Fragment fragment = manager.findFragmentById(R.id.container_main);

        switch (frag) {

            case "1":
                if (fragment != null && !(fragment instanceof HomeFragment)) {

                    replaceFragment(HomeFragment.newInstance());
                }

                break;
            case "2":
                if (fragment != null && !(fragment instanceof AddMemberFragment)) {

                    replaceFragment(AddMemberFragment.newInstance());
                }

                break;
            case "3":
                if (fragment != null && !(fragment instanceof HierarchyFragment)) {

                    replaceFragment(HierarchyFragment.newInstance());
                }
                break;
            case "4":
                if (fragment != null && !(fragment instanceof ChatFragment)) {

                    replaceFragment(ChatFragment.newInstance());
                }
                break;
        }

    }

    public void setSetSearchListener(SearchListener mSearchListener) {

        this.mSearchListener = mSearchListener;

    }
}
