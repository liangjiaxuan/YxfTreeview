package com.example.lccx.yxftreeview.treeview.base.project;

/**
 * Created by LCCX on 2018/4/3.
 */

public class ProjectBean {
    private String projectName;
    private String projectCode;

    public ProjectBean(String projectName, String projectCode) {
        this.projectName = projectName;
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
}
