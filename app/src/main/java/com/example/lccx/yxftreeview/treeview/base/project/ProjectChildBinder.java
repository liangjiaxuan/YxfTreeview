package com.example.lccx.yxftreeview.treeview.base.project;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lccx.yxftreeview.R;
import com.example.lccx.yxftreeview.treeview.lib.TreeNode;
import com.example.lccx.yxftreeview.treeview.lib.TreeViewBinder;

/**
 * Created by LCCX on 2018/4/3.
 */

public class ProjectChildBinder extends TreeViewBinder<ProjectChildBinder.ViewHolder> {
    @Override
    public int getLayoutId() {
        return R.layout.layout_project_child;
    }

    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        holder.ivArrow.setRotation(0);
        holder.ivArrow.setImageResource(R.mipmap.ic_tree_arrow_right);
        int rotateDegree=node.isExpand()?90:0;
        holder.ivArrow.setRotation(rotateDegree);
        ProjectChildNode projectChildNode = (ProjectChildNode) node.getContent();
        holder.tvName.setText(projectChildNode.getName());
        if (node.isLeaf())
            holder.ivArrow.setVisibility(View.INVISIBLE);
        else holder.ivArrow.setVisibility(View.VISIBLE);

    }

    public static class ViewHolder extends TreeViewBinder.ViewHolder{
        private ImageView ivArrow;
        private TextView tvName;

        public ViewHolder(View rootView) {
            super(rootView);
            this.ivArrow=rootView.findViewById(R.id.iv_arrow_right);
            this.tvName=rootView.findViewById(R.id.tv_name);
        }

        public ImageView getIvArrow(){
            return ivArrow;
        }

        public TextView getTvName(){
            return tvName;
        }
    }

}
