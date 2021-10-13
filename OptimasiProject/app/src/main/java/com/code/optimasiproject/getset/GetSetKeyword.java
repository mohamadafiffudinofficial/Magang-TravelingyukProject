package com.code.optimasiproject.getset;

public class GetSetKeyword {
    String id, user_id, project_id, keyword, explored, explored_at, created_at, updated_at;

    public GetSetKeyword(String id, String user_id, String project_id, String keyword, String explored, String explored_at, String created_at, String updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.project_id = project_id;
        this.keyword = keyword;
        this.explored = explored;
        this.explored_at = explored_at;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getExplored() {
        return explored;
    }

    public void setExplored(String explored) {
        this.explored = explored;
    }

    public String getExplored_at() {
        return explored_at;
    }

    public void setExplored_at(String explored_at) {
        this.explored_at = explored_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
