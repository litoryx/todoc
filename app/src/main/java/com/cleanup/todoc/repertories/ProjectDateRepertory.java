package com.cleanup.todoc.repertories;

import com.cleanup.todoc.InterfaceDAO.ProjectDAO;
import com.cleanup.todoc.model.Project;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ProjectDateRepertory {

   private ProjectDAO projectDAO;

   public ProjectDateRepertory(ProjectDAO projectDAO){this.projectDAO = projectDAO;}

    public LiveData<List<Project>> getProjects(){ return this.projectDAO.getProjects();}

    public void createProject(Project project){projectDAO.createProject(project);}
}
