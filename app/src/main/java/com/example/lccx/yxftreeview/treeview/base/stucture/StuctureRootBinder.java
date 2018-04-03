package com.example.lccx.yxftreeview.treeview.base.stucture;

import android.view.View;

import com.example.lccx.yxftreeview.R;
import com.example.lccx.yxftreeview.treeview.lib.TreeNode;
import com.example.lccx.yxftreeview.treeview.lib.TreeViewBinder;

/**
 * Created by LCCX on 2018/4/3.
 */

public class StuctureRootBinder extends TreeViewBinder<TreeViewBinder.ViewHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.layout_project_root;
    }

    @Override
    public ViewHolder provideViewHolder(View itemView) {
        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.ivArrow=viewHolder.findViewById(R.id.iv_arrow);
        viewHolder.tvName=viewHolder.findViewById(R.id.tv_name);
        return viewHolder;
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        StuctureRootNode stuctureRootNode = (StuctureRootNode) node.getContent();
        holder.tvName.setText(stuctureRootNode.getName());
        holder.ivArrow.animate().rotation(0);
        int rotateDegree=node.isExpand()?90: 0;
        holder.ivArrow.animate().rotation(rotateDegree);
    }
}
