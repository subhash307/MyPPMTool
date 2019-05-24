package com.subhbash.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subhbash.ppmtool.domain.Backlog;
import com.subhbash.ppmtool.domain.ProjectTask;
import com.subhbash.ppmtool.repository.BacklogRepository;
import com.subhbash.ppmtool.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask) {
		
		// Exceptions : Project not found
		
		// PTs to be added to a specific project, Project != null, BL exists
		Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
		
		//set the BL to PT
		projectTask.setBacklog(backlog);
		// We want our project sequence to be like this: IDPRO-1, IDPRO-2 
		Integer BacklogSequence = backlog.getPTSequence();
		// Update the BL sequence
		BacklogSequence++;
		
		// Add sequence to ProjectTask
		projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
		projectTask.setProjectIdentifier(projectIdentifier);
		
		//INITIAL Priority when priority  null
//		if(projectTask.getPriority() == 0 || projectTask.getPriority()==null) {
//			projectTask.setPriority(3);
//			
//		}
		if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
			projectTask.setStatus("TO_DO");
		}
		
		return projectTaskRepository.save(projectTask);
		//
	}
}
