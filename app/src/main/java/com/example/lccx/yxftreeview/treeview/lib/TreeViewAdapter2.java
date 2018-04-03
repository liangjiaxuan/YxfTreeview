package com.example.lccx.yxftreeview.treeview.lib;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by LCCX on 2018/3/29.
 */

public class TreeViewAdapter2<V extends TreeViewBinder.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String KEY_IS_EXPAND="IS_EXPAND";
    private final List<? extends TreeViewBinder>viewBinders;
    private List<TreeNode> displayNodes;
    private OnTreeNodeListener onTreeNodeListener;
    private OnCutListener onCutListener;
    private int padding=30;
    private boolean toCollapseChild;

    public TreeViewAdapter2(List<? extends TreeViewBinder> viewBinders) {
        this.viewBinders = viewBinders;
    }

    public TreeViewAdapter2( List<TreeNode> nodes,List<? extends TreeViewBinder> viewBinders) {
        displayNodes=new ArrayList<>();
        if (nodes!=null) {
            findDisplayNodes(nodes);
        }
        this.viewBinders = viewBinders;

    }

    private void findDisplayNodes(List<TreeNode>nodes){
        for (TreeNode node : nodes) {
            displayNodes.add(node);
            if (!node.isLeaf()&&node.isExpand()) {
                findDisplayNodes(node.getChildList());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return displayNodes.get(position).getContent().getLayoutId();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(viewType,parent,false);
        if (viewBinders.size()==1) {
            return viewBinders.get(0).provideViewHolder(v);
        }
        for (TreeViewBinder viewBinder : viewBinders) {
            if (viewBinder.getLayoutId()==viewType)
                return viewBinder.provideViewHolder(v);
        }
        return viewBinders.get(0).provideViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position,List<Object>payLoads) {
        if (payLoads!=null&&!payLoads.isEmpty()) {
            Bundle b= (Bundle) payLoads.get(0);
            for (String key: b.keySet()) {
                switch (key) {
                    case KEY_IS_EXPAND:
                        if (onTreeNodeListener!=null) {
                            onTreeNodeListener.onToggle(b.getBoolean(key),holder);
                        }
                        break;
                }
            }
        }
        super.onBindViewHolder(holder,position,payLoads);
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        holder.itemView.setPadding(displayNodes.get(position).getHeight()*padding,3,3,3);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TreeNode selectedNode=displayNodes.get(holder.getLayoutPosition());
                try{
                    long lastClickTime= (long) holder.itemView.getTag();
                    if (System.currentTimeMillis()-lastClickTime<500) {
                        return;
                    }
                }catch (Exception e){
                    holder.itemView.setTag(System.currentTimeMillis());
                }
                holder.itemView.setTag(System.currentTimeMillis());

                if (onTreeNodeListener!=null&&onTreeNodeListener.onClick(selectedNode,holder)) {
                    return;
                }
                if (selectedNode.isLeaf()) {
                    return;
                }
                boolean isExpand = selectedNode.isExpand();
                int positionStart=displayNodes.indexOf(selectedNode)+1;
                if (!isExpand) {
                    notifyItemRangeChanged(positionStart, addChildNodes(selectedNode, positionStart));
                } else {
                    notifyItemRangeRemoved(positionStart,removeChildNodes(selectedNode,true));
                }
            }
        });

        for (TreeViewBinder viewBinder: viewBinders) {
            if (viewBinder.getLayoutId()==displayNodes.get(position).getContent().getLayoutId()) {
                viewBinder.bindView(holder,position,displayNodes.get(position));
            }
        }
    }

    private int removeChildNodes(TreeNode pNode){
        return removeChildNodes(pNode,true);
    }

    private int removeChildNodes(TreeNode pNode, boolean sholdToggle) {
        if (pNode.isLeaf()) {
            return 0;
        }
        List<TreeNode>childList=pNode.getChildList();
        int removeChildCount=childList.size();
        displayNodes.removeAll(childList);
        for (TreeNode child : childList) {
            if (child.isExpand()) {
                if (toCollapseChild) {
                    child.toggle();
                    removeChildCount+=removeChildNodes(child,false);
                }
            }
        }
        if (sholdToggle) {
            pNode.toggle();
        }
        return removeChildCount;
    }

    private int addChildNodes(TreeNode pNode, int startIndex) {
        List<TreeNode>childList=pNode.getChildList();
        int addChildCount=0;
        for (TreeNode treeNode :
                childList) {
            displayNodes.add(startIndex+addChildCount++,treeNode);
            if (treeNode.isExpand()) {
                addChildCount+=addChildNodes(treeNode,startIndex+addChildCount);
            }
        }
        if (!pNode.isExpand()) {
            pNode.toggle();
        }
        return addChildCount;
    }

    @Override
    public int getItemCount() {
        return displayNodes!=null?displayNodes.size():0;
    }

    public void setPadding(int padding){
        this.padding=padding;
    }

    public void ifCollapseChildWhileCollapseParent(boolean toCollapseChild){
        this.toCollapseChild=toCollapseChild;
    }

    public void setOnTreeNodeListener(OnTreeNodeListener onTreeNodeListener){
        this.onTreeNodeListener=onTreeNodeListener;
    }

    public void setOnCutListener(OnCutListener onCutListener){
        this.onCutListener=onCutListener;
    }


    public interface OnTreeNodeListener {
        boolean onClick(TreeNode node,RecyclerView.ViewHolder holder);

        void onToggle(boolean isExpand,RecyclerView.ViewHolder holder);
    }

    public interface OnCutListener{
        boolean onClick(TreeNode node,RecyclerView.ViewHolder holder);
    }

    public void refresh(List<TreeNode>treeNodes){
        displayNodes.clear();
        findDisplayNodes(treeNodes);
        notifyDataSetChanged();
    }

    public Iterator<TreeNode> getDisplayNodesIterable(){
        return displayNodes.iterator();
    }

    private void notifyDiff(final List<TreeNode>temp){
        DiffUtil.DiffResult diffResult= DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return temp.size();
            }

            @Override
            public int getNewListSize() {
                return displayNodes.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return TreeViewAdapter2.this.areItemsTheSame(temp.get(oldItemPosition),displayNodes.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return TreeViewAdapter2.this.areContentsTheSame(temp.get(oldItemPosition),displayNodes.get(newItemPosition));
            }

            @Nullable
            @Override
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                return TreeViewAdapter2.this.getChangePayLoad(temp.get(oldItemPosition),displayNodes.get(newItemPosition));
            }
        });
    }

    private Object getChangePayLoad(TreeNode oldNode, TreeNode newNode) {
        Bundle diffBundle=new Bundle();
        if (newNode.isExpand()!=oldNode.isExpand()) {
            diffBundle.putBoolean(KEY_IS_EXPAND,newNode.isExpand());
        }
        if (diffBundle.size()==0) {
            return null;
        }
        return diffBundle;
    }

    private boolean areContentsTheSame(TreeNode oldNode, TreeNode newNode) {
        return oldNode.getContent()!=null&&oldNode.getContent().equals(newNode.getContent())
                &&oldNode.isExpand()==newNode.isExpand();
    }

    private boolean areItemsTheSame(TreeNode oldNode, TreeNode newNode) {
        return oldNode.getContent()!=null&&oldNode.getContent().equals(newNode.getContent());
    }

}
