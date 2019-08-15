package com.vp.bottomappbar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.vp.bottomappbar.R;
import com.vp.bottomappbar.model.SelectUser;

/**
 * Created by Android Development on 9/3/2016.
 */
public class SelectUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<SelectUser> mainInfo= new ArrayList<>();
    private ArrayList<SelectUser> arraylist;
    Context context;


    public static final int SECTION_VIEW = 0;
    public static final int CONTENT_VIEW = 1;

    WeakReference<Context> mContextWeakReference;

    ArrayList<SelectUser> alphabeticalcharlist;


    public SelectUserAdapter(Context context, List<SelectUser> mainInfo, WeakReference<Context> mContextWeakReference, ArrayList<SelectUser> alphabeticalcharlist) {
        this.mainInfo = mainInfo;
        this.context = context;
        this.arraylist = new ArrayList<SelectUser>();
        this.mContextWeakReference=new WeakReference<Context>(context);
        this.arraylist.addAll(mainInfo);
        this.alphabeticalcharlist = alphabeticalcharlist;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = mContextWeakReference.get();
        if (viewType == SECTION_VIEW) {

            return new SectionHeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_title, parent, false));
        }
        return new MyContactListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public int getItemViewType(int position) {

        if (mainInfo.get(position).isSection) {

            return SECTION_VIEW;
        } else {

            return CONTENT_VIEW;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        int view = holder.getItemViewType();
        Context context = mContextWeakReference.get();
        if (context == null) {
            return;
        }

        if (view == SECTION_VIEW) {

            SectionHeaderViewHolder sectionHeaderViewHolder = (SectionHeaderViewHolder) holder;
            SelectUser sectionItem = mainInfo.get(position);
            sectionHeaderViewHolder.headerTitleTextview.setText(sectionItem.getName());
            return;
        } else if (view == CONTENT_VIEW) {

            MyContactListViewHolder myContactListViewHolder = (MyContactListViewHolder) holder;

            String imagepath = mainInfo.get(position).getImagepath();
            if (imagepath == null) {
                Picasso.with(context).load(R.drawable.image).into(myContactListViewHolder.imageViewUserImage);
            } else {
                Picasso.with(context).load(imagepath).into(myContactListViewHolder.imageViewUserImage);
            }
            myContactListViewHolder.textViewShowName.setText(mainInfo.get(position).getName());
            myContactListViewHolder.textViewPhoneNumber.setText(mainInfo.get(position).getPhone());
            myContactListViewHolder.checkBoxSelectItem.setChecked(mainInfo.get(position).getCheckedBox());
        }
    }


    @Override
    public int getItemCount() {
        return mainInfo.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mainInfo.clear();
        if (charText.length() == 0) {
            mainInfo.addAll(alphabeticalcharlist);
        } else {
            for (SelectUser wp : alphabeticalcharlist) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    mainInfo.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
    public class SectionHeaderViewHolder extends RecyclerView.ViewHolder {
        TextView headerTitleTextview;

        public SectionHeaderViewHolder(View itemView) {
            super(itemView);
            headerTitleTextview = (TextView) itemView.findViewById(R.id.headerTitleTextview);
        }
    }

    public class MyContactListViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewUserImage;
        TextView textViewShowName;
        TextView textViewPhoneNumber;
        CheckBox checkBoxSelectItem;

        public MyContactListViewHolder(View itemView) {
            super(itemView);

            textViewShowName = (TextView) itemView.findViewById(R.id.name);
            checkBoxSelectItem = (CheckBox) itemView.findViewById(R.id.check);
            textViewPhoneNumber = (TextView) itemView.findViewById(R.id.no);
            imageViewUserImage = (ImageView) itemView.findViewById(R.id.pic);
            checkBoxSelectItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final SelectUser selectUser = mainInfo.get(getAdapterPosition());
                    CheckBox checkBox = (CheckBox) view;
                    if (checkBox.isChecked()) {
                        selectUser.setCheckedBox(true);
                    } else {
                        selectUser.setCheckedBox(false);
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }
}


