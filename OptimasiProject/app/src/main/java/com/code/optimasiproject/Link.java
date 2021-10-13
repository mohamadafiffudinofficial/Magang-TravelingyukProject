package com.code.optimasiproject;

public class Link {
    String token = "43|c4x8NP3fMqGLQ9qyOfV1SRlWd8cQYsg0i7NEVdQS";
    String alamat = "https://optimasi.ai/app/api/";
    String listProjects = alamat + "projects";
    String addProjects = alamat + "project/store";
    String updateProjects = alamat + "project/update/"; //{{projectId}}
    String deleteProjects = alamat + "project/delete/"; //{{projectId}}
    String listKeyword = alamat + "project/show/"; //{{projectId}}
    String addKeyword = alamat + "keyword/store";
    String updateKeyword = alamat + "keyword/update/"; //{{projectId}}
    String deleteKeyword = alamat + "keyword/delete/"; //{{projectId}}
}
