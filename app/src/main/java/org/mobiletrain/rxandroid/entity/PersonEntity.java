package org.mobiletrain.rxandroid.entity;

import java.util.List;

public class PersonEntity {
    private String name;
    private List<CouseEntity> couseList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CouseEntity> getCouseList() {
        return couseList;
    }

    public void setCouseList(List<CouseEntity> couseList) {
        this.couseList = couseList;
    }

    public class CouseEntity {
        private String courseName;

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }
    }

}
