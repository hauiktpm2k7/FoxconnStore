package com.foxconnstore;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by leo on 06/05/2017.
 */

public class NewApp extends Fragment {
    RecyclerView mRecyclerView;
    Context mContext;
    Button btn_Cancle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_app, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        setupAdapter();

        return view;
    }

    private void setupAdapter() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.hasCategory(Intent.CATEGORY_DEFAULT);
        PackageManager packageManager = getActivity().getPackageManager();

        final List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);

        Collections.sort(resolveInfos, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo o1, ResolveInfo o2) {
                PackageManager packageManager1 = getActivity().getPackageManager();
                return String.CASE_INSENSITIVE_ORDER.compare(o1.loadLabel(packageManager1).toString(), o2.loadLabel(packageManager1).toString());
            }
        });
        mRecyclerView.setAdapter(new MyAdapter(resolveInfos));
        Log.i(TAG, "Found " + resolveInfos.size() + " activities.");
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_name_app;
        ImageView ic_app_icon;
        ResolveInfo mResolveInfo;
        public MyViewHolder(View itemView) {
            super(itemView);
            tv_name_app = (TextView) itemView.findViewById(R.id.tv_name_app);
            ic_app_icon = (ImageView) itemView.findViewById(R.id.img_app_icon);
            itemView.setOnClickListener(this);
            itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    int pos = mRecyclerView.getChildAdapterPosition(v);
                    mRecyclerView.smoothScrollToPosition(pos+1);
                }
            });

        }
        private void binData(ResolveInfo mResolveInfo) {
            this.mResolveInfo = mResolveInfo;
            PackageManager packageManager = getActivity().getPackageManager();
            String appName = mResolveInfo.loadLabel(packageManager).toString();
            Drawable d = mResolveInfo.loadIcon(packageManager);
            ic_app_icon.setImageDrawable(d);
            tv_name_app.setText(appName);
        }
        @Override
        public void onClick(View v) {
            mContext = itemView.getContext();
            final Dialog dialog=new Dialog(mContext,R.style.ThemeDialogCustom);
            dialog.setContentView(R.layout.mydialog);
                    dialog.dismiss();
                    dialog.show();
        }
    }
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<ResolveInfo> resolveInfoList;
        public MyAdapter(List<ResolveInfo> resolveInfoList) {
            this.resolveInfoList = resolveInfoList;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.app_item, parent, false);
            view.setFocusable(true);
            return new MyViewHolder(view);
        }
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            ResolveInfo resolveInfo = resolveInfoList.get(position);
            holder.binData(resolveInfo);
        }
        @Override
        public int getItemCount() {
            return resolveInfoList.size();
        }
    }
}
