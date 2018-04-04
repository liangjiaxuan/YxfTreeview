package com.example.lccx.yxftreeview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.lccx.yxftreeview.treeview.base.project.ProjectChildBinder;
import com.example.lccx.yxftreeview.treeview.base.project.ProjectChildNode;
import com.example.lccx.yxftreeview.treeview.base.project.ProjectLeafBinder;
import com.example.lccx.yxftreeview.treeview.base.project.ProjectLeafNode;
import com.example.lccx.yxftreeview.treeview.base.project.ProjectRootBinder;
import com.example.lccx.yxftreeview.treeview.base.project.ProjectRootNode;
import com.example.lccx.yxftreeview.treeview.base.stucture.StuctureChildBinder;
import com.example.lccx.yxftreeview.treeview.base.stucture.StuctureChildNode;
import com.example.lccx.yxftreeview.treeview.base.stucture.StuctureLeafBinder;
import com.example.lccx.yxftreeview.treeview.base.stucture.StuctureLeafNode;
import com.example.lccx.yxftreeview.treeview.base.stucture.StuctureRootBinder;
import com.example.lccx.yxftreeview.treeview.base.stucture.StuctureRootNode;
import com.example.lccx.yxftreeview.treeview.lib.TreeNode;
import com.example.lccx.yxftreeview.treeview.lib.TreeViewAdapter;
import com.example.lccx.yxftreeview.treeview.lib.TreeViewAdapter2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TreeViewAdapter adapter;
    private List<TreeNode> nodes=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        initRootNodes();
        adapter=new TreeViewAdapter(nodes,Arrays.asList(new ProjectRootBinder(),new ProjectChildBinder(),new ProjectLeafBinder()));
//        initStuctureRootNodes();
//        adapter=new TreeViewAdapter(nodes,Arrays.asList(new StuctureRootBinder(),new StuctureChildBinder(),new StuctureLeafBinder()));
        recyclerview.setAdapter(adapter);

        adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
            @Override
            public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
                if (!node.isLeaf()) {
                    onToggle(!node.isExpand(),holder);
                }
                return false;
            }

            @Override
            public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
                if (holder instanceof ProjectRootBinder.ViewHolder) {
                    ProjectRootBinder.ViewHolder projectRootHolder = (ProjectRootBinder.ViewHolder) holder;
                    ImageView ivArrow = projectRootHolder.getIvArrow();
                    int rotateDegree = isExpand ? 90 : -90;
                    ivArrow.animate().rotationBy(rotateDegree)
                            .start();
                } else if (holder instanceof ProjectChildBinder.ViewHolder){
                    ProjectChildBinder.ViewHolder projectChildHolder = (ProjectChildBinder.ViewHolder) holder;
                    ImageView ivArrow = projectChildHolder.getIvArrow();
                    int rotateDegree=isExpand?90: -90;
                    ivArrow.animate().rotationBy(rotateDegree)
                            .start();
                }
            }
        });
    }



    private void initRootNodes() {
        for (int i = 0; i < 10; i++) {
            TreeNode<ProjectRootNode>rootNodeTreeNode=new TreeNode<>(new ProjectRootNode("测试工程名第"+i+"项目工程"));
            for (int j = 0; j < 5; j++) {
                TreeNode<ProjectChildNode>childNodeTreeNode=new TreeNode<>(new ProjectChildNode("子单位工程测试第"+i+"项子工程"));
                rootNodeTreeNode.addChild(childNodeTreeNode);
                for (int k = 0; k < 3; k++) {
                    TreeNode<ProjectLeafNode> leafNode=new TreeNode(new ProjectLeafNode("测试叶子节点第"+i+"个叶子节点"));
                    childNodeTreeNode.addChild(leafNode);
                }
            }
            nodes.add(rootNodeTreeNode);
        }

    }

    private void initStuctureRootNodes() {
        for (int i = 0; i < 10; i++) {
            TreeNode<StuctureRootNode>rootNodeTreeNode=new TreeNode<>(new StuctureRootNode("测试工程名第"+i+"项目工程"));
            for (int j = 0; j < 5; j++) {
                TreeNode<StuctureChildNode>childNodeTreeNode=new TreeNode<>(new StuctureChildNode("子单位工程测试第"+i+"项子工程"));
                rootNodeTreeNode.addChild(childNodeTreeNode);
                for (int k = 0; k < 3; k++) {
                    TreeNode<StuctureLeafNode> leafNode=new TreeNode(new StuctureLeafNode("测试叶子节点第"+i+"个叶子节点"));
                    childNodeTreeNode.addChild(leafNode);
                }
            }
            nodes.add(rootNodeTreeNode);
        }
    }
}
