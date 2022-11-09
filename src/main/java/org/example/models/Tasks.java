package org.example.models;

public class Tasks {
    private String list_name;
    private String myTasks;
    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    public String getMyTasks() {
        return myTasks;
    }

    public void setMyTasks(String myTasks) {
        this.myTasks = myTasks;
    }

    @Override
    public String toString(){
        return list_name+"("+myTasks+")\n";
    }
}
