package com.subhbash.ppmtool.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.subhbash.ppmtool.domain.Backlog;
import com.subhbash.ppmtool.domain.Project;
import com.subhbash.ppmtool.domain.ProjectTask;
import com.subhbash.ppmtool.exceptions.ProjectNotFoundException;
import com.subhbash.ppmtool.repository.BacklogRepository;
import com.subhbash.ppmtool.repository.ProjectRepository;
import com.subhbash.ppmtool.repository.ProjectTaskRepository;

@Service
public class ProjectTaskService {

	@Autowired
	private BacklogRepository backlogRepository;
	
	@Autowired
	private ProjectTaskRepository projectTaskRepository;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private ProjectService projectService;
	
	
	public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {
		
		
			// PTs to be added to a specific project, Project != null, BL exists
			Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();//backlogRepository.findByProjectIdentifier(projectIdentifier);
			
			//set the BL to PT
			projectTask.setBacklog(backlog);
			// We want our project sequence to be like this: IDPRO-1, IDPRO-2 
			Integer BacklogSequence = backlog.getPTSequence();
			// Update the BL sequence
			BacklogSequence++;
			backlog.setPTSequence(BacklogSequence);
			// Add sequence to ProjectTask
			projectTask.setProjectSequence(projectIdentifier+"-"+BacklogSequence);
			projectTask.setProjectIdentifier(projectIdentifier);
			
			//INITIAL Priority when priority  null
			if(projectTask.getStatus() == "" || projectTask.getStatus() == null) {
				projectTask.setStatus("TO_DO");
			}
			
			if(projectTask.getPriority() == null || projectTask.getPriority()==0) {
				projectTask.setPriority(3);
				
			}
			
			return projectTaskRepository.save(projectTask);
		
		
		//
	}
	
	public Iterable<ProjectTask> findBacklogById(String id, String username) {
		
		projectService.findProjectByIdentifier(id, username);
//		Project project = projectRepository.findByProjectIdentifier(id, username);
//		if(project == null) {
//			throw new ProjectNotFoundException("Project with id: '" +id +"' does not exist");
//		}
//		
		return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
	}
	
	public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username) {
		
		// Make sure we are searching on an existing backlog
		projectService.findProjectByIdentifier(backlog_id, username);
		
		
		// Make sure our task exists
		ProjectTask projectTask = projectTaskRepository.findByProjectSequence(pt_id);
		if(projectTask == null) {
			throw new ProjectNotFoundException("Project task with id: '" + pt_id +"' not found");
		}
		
		// Make sure that the backlog/project id in the path corresponds to the right project
		if(!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project task with id: '" + pt_id +"' does not exists in project: '" +	backlog_id+"'.");
		}
			
		return projectTask;
		
	}
	
	public ProjectTask updateByProjectSequence(ProjectTask updateTask, String backlog_id, String pt_id, String username) {
		
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
		projectTask = updateTask;
		
		return projectTaskRepository.save(projectTask);
		
	}
	
	public void deletePTBySequence(String backlog_id, String pt_id, String username) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, username);
		projectTaskRepository.delete(projectTask);
	}
}
