package com.example.lccx.yxftreeview.treeview.base.stucture;

import com.example.lccx.yxftreeview.R;
import com.example.lccx.yxftreeview.treeview.lib.LayoutItemType;

/**
 * Created by LCCX on 2018/4/3.
 */

public class StuctureLeafNode implements LayoutItemType {
    String name;

    public String getName() {
        return name;
    }

    public StuctureLeafNode(String name) {

        this.name = name;
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_project_leaf;
    }
}
