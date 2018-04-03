package com.example.lccx.yxftreeview.treeview.lib;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by LCCX on 2018/3/29.
 */

public abstract class TreeViewBinder<VH extends RecyclerView.ViewHolder> implements LayoutItemType {
    public abstract VH provideViewHolder(View itemView);

    public abstract void bindView(VH holder,int position,TreeNode node);

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivArrow;
        public TextView tvName;

        public ViewHolder(View rootView) {
            super(rootView);
        }

        public <T extends View>T findViewById(@IdRes int id){
            return itemView.findViewById(id);
        }

        public ImageView getIvArrow(){
            return ivArrow;
        }

        public TextView getTvName(){
            return tvName;
        }

    }
}
