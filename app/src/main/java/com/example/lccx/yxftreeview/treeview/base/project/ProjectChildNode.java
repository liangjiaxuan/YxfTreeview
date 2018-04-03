package com.example.lccx.yxftreeview.treeview.base.project;

import com.example.lccx.yxftreeview.R;
import com.example.lccx.yxftreeview.treeview.lib.LayoutItemType;

/**
 * Created by LCCX on 2018/4/3.
 */

public class ProjectChildNode implements LayoutItemType {
    private String name;

    public ProjectChildNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_project_child;
    }
}
