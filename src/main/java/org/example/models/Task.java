package org.example.models;

public class Task {
    private String name;
    private String description;
    private int points;
    private String type;
    private String created_dateTime;
    private String modified_dateTime;
    private String creator;
    private String est_efforts;
    private String est_duration;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        try {
            name = name.replace("!", "");
            description = description.replace("!", "");
            creator = creator.replace("!", "");
            est_efforts = est_efforts.replace("!", "");
            est_duration = est_duration.replace("!", "");
        } catch (Exception ignored) {

        }

        return name +"!"+description+"!"+points+"!"+type+"!"+created_dateTime+"!"+modified_dateTime+"!"+creator+"!"+est_efforts+"!"+est_duration+",";
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated_dateTime() {
        return created_dateTime;
    }

    public void setCreated_dateTime(String created_dateTime) {
        this.created_dateTime = created_dateTime;
    }

    public String getModified_dateTime() {
        return modified_dateTime;
    }

    public void setModified_dateTime(String modified_dateTime) {
        this.modified_dateTime = modified_dateTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getEst_efforts() {
        return est_efforts;
    }

    public void setEst_efforts(String est_efforts) {
        this.est_efforts = est_efforts;
    }

    public String getEst_duration() {
        return est_duration;
    }

    public void setEst_duration(String est_duration) {
        this.est_duration = est_duration;
    }
}
