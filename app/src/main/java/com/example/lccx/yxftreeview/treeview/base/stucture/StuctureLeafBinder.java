package com.example.lccx.yxftreeview.treeview.base.stucture;

import android.view.View;

import com.example.lccx.yxftreeview.R;
import com.example.lccx.yxftreeview.treeview.lib.TreeNode;
import com.example.lccx.yxftreeview.treeview.lib.TreeViewBinder;

/**
 * Created by LCCX on 2018/4/3.
 */

public class StuctureLeafBinder extends TreeViewBinder<TreeViewBinder.ViewHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.layout_project_leaf;
    }

    @Override
    public ViewHolder provideViewHolder(View itemView) {
        ViewHolder holder=new ViewHolder(itemView);
        holder.ivArrow=holder.findViewById(R.id.iv_arrow);
        holder.tvName=holder.findViewById(R.id.tv_name);
        return holder;
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        StuctureLeafNode stuctureLeafNode = (StuctureLeafNode) node.getContent();
        holder.tvName.setText(stuctureLeafNode.getName());
    }
}
