package com.example.mvp.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by hty on 2018/4/8.
 */

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    private Context mContext;
    private List<T> mList;
    private int mIndexPositive;
    private ViewHolder viewHolder;
    private @LayoutRes int mLayout;
    @Override
    public int getCount() {
        if(mList==null){
            return 0;
        }
        return mList.size()+mIndexPositive;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public BaseAdapter initView(Context context, @LayoutRes int layout , List<T> List, int indexPositive){
        mContext=context;
        mList = List;
        mIndexPositive = indexPositive;
        mLayout = layout;
        notifyDataSetChanged();
        return this;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (mContext == null) {
            return view;
        }
        ViewHolder viewHolder = null;
        if (view == null) {
            view = View.inflate(mContext, mLayout, null);
            viewHolder = getViewHolder();
            viewHolder.ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        initData(mContext,i, viewGroup, mList, viewHolder);
        return view;
    }

    public abstract void initData(Context context, int i, ViewGroup viewGroup, List<T> mList, ViewHolder viewHolder);

    public abstract  ViewHolder  getViewHolder();

    public abstract class ViewHolder {
      public void ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
