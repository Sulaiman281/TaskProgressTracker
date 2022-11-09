package org.example.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Project {

    private String name;

    public ArrayList<Tasks> tasks = new ArrayList<>();

    public Project(String _name){
        this.name = _name;
    }


    public File getFile(){
        if(name.contains(".sesp"))
            return new File(name);
        return new File(name.concat(".sesp"));
    }
    // create file with the project name.
    public boolean create_file(){
        try{
            File file = getFile();
            if(!file.exists())
                return file.createNewFile();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public void updateFile(){
        File file = getFile();
        try{
            if(file.exists()) {
                FileWriter fileWriter = new FileWriter(file,false);
                for (Tasks task : tasks) {
                    fileWriter.write(task.toString());
                }
                fileWriter.flush();
                fileWriter.close();
            }else{
                System.err.println("File not exists");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile(){
        File file = getFile();
        try{
            if(file.exists()){
                Scanner scanner = new Scanner(file);
                while(scanner.hasNext()){
                    String line = scanner.nextLine();
                    Tasks t = extractLists(line);
                    if(t != null)
                        tasks.add(extractLists(line));
                }
                scanner.close();
            }
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private Tasks extractLists(String str){
        try {
            String name = str.substring(0, str.indexOf("("));
            String content = str.substring(str.indexOf("(") + 1, str.indexOf(")"));
            Tasks tasks = new Tasks();
            tasks.setList_name(name);
            tasks.setMyTasks(content);
            return tasks;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
