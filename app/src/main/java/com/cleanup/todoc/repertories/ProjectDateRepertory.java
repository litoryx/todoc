package com.cleanup.todoc.repertories;

import com.cleanup.todoc.InterfaceDAO.ProjectDAO;
import com.cleanup.todoc.model.Project;

import androidx.lifecycle.LiveData;

public class ProjectDateRepertory {

   private ProjectDAO projectDAO;

   public ProjectDateRepertory(ProjectDAO projectDAO){this.projectDAO = projectDAO;}

    public LiveData<Project> createProject(Project project){return this.projectDAO.createProject(new Project(4L,"Boulet",0xFFEADAD1));}
}
