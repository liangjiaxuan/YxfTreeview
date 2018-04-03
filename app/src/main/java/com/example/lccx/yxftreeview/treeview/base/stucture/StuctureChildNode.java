package com.example.lccx.yxftreeview.treeview.base.stucture;

import com.example.lccx.yxftreeview.R;
import com.example.lccx.yxftreeview.treeview.lib.LayoutItemType;

/**
 * Created by LCCX on 2018/4/3.
 */

public class StuctureChildNode implements LayoutItemType {
    String name;

    public StuctureChildNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_project_child;
    }
}
