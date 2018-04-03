package com.example.lccx.yxftreeview.treeview.lib;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LCCX on 2018/3/29.
 */

public class TreeNode <T extends LayoutItemType> implements Cloneable {
    private T content;
    private TreeNode parent;
    private List<TreeNode>childList;
    private boolean isExpand;
    //树高
    public static final int UNDEFINE=-1;
    private int height =UNDEFINE;

    public TreeNode(@NonNull T content) {
        this.content = content;
    }

    public int getHeight(){
        if (isRoot()) {
            height=0;
        } else if (height==UNDEFINE) {
            height=parent.getHeight()+1;
        }
        return height;
    }

    public boolean isRoot(){
        return parent==null;
    }

    public boolean isLeaf(){
        return childList==null||childList.isEmpty();
    }

    public void setContent(T content){this.content=content;}

    public T getContent(){return content;}

    public List<TreeNode>getChildList(){
        return childList;
    }

    public void setChildList(List<TreeNode>childList){
        this.childList=childList;
    }

    public TreeNode addChild(TreeNode node){
        if (childList==null) {
            childList=new ArrayList<>();
        }
        childList.add(node);  //为当前节点添加子节点
        node.parent=this;//给子节点添加父节点   子节点和父节点的互相关联
        return this;
    }

    public boolean toggle(){
        isExpand=!isExpand;
        return isExpand;
    }

    /**
     * 全部折叠
     */
    public void collapse(){
        if (isExpand) {
            isExpand=false;
        }
    }

    /**
     * 全部展开
     */
    public void exPand(){
        if (!isExpand) {
            isExpand=true;
        }
    }

    public boolean isExpand(){
        return isExpand;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "content=" + this.content +
                ", parent=" + (parent==null?"null":parent.getContent().toString()) +
                ", childList=" + childList +
                ", isExpand=" + isExpand +
                ", height=" + height +
                '}';
    }

    @Override
    protected TreeNode<T> clone() throws CloneNotSupportedException {
        TreeNode<T> clone=new TreeNode<>(this.content);
        clone.isExpand=this.isExpand;
        return clone;
    }
}
