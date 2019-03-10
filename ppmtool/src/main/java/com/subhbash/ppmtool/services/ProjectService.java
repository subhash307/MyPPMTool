package com.subhbash.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subhbash.ppmtool.domain.Project;
import com.subhbash.ppmtool.exceptions.ProjectIdException;
import com.subhbash.ppmtool.repository.ProjectRepository;

@Service
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;
	
	public Project saveOrUpdateProject(Project project) {
		
		// logic
		try {
			project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
			return projectRepository.save(project);
			
		} catch (Exception e) {
			throw new ProjectIdException("Project Id '"+project.getProjectIdentifier().toUpperCase()+"' already exists");

		}	
	}
	
	public Project findProjectByIdentifier(String projectId) {
		
		Project project  = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		
		if (project == null) {
			throw new ProjectIdException("Project Id '" + projectId+"' does not exists.");
		}
		return project;

	}
	
	public Iterable<Project> findAllProjects(){
		return projectRepository.findAll();
	}
}
