package com.marlabs.training.project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marlabs.training.project.dao.ProjectDao;
import com.marlabs.training.project.entity.Project;

@Service
public class ProjectService {

	@Autowired
	private ProjectDao projectDao;

	// Create Project
	public Project createProject(Project project) {
		return projectDao.save(project);
	}

	// Get Project By Id
	public Project getProjectById(Long id) {
		Optional<Project> optionalEntity = projectDao.findById(id);
		Project project = optionalEntity.get();
		return project;
	}

	// Update Project By Id
	public Project updateProjectById(Long id) {
		Optional<Project> optionalEntity = projectDao.findById(id);
		Project project = optionalEntity.get();
		project.setArchived(true);
		return projectDao.save(project);
		}

	public List<Project> findByArchived(boolean archived) {
		// TODO Auto-generated method stub
		return projectDao.findByArchived(false);
	}

}
