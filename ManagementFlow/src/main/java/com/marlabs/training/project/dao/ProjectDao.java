package com.marlabs.training.project.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marlabs.training.project.entity.Project;

public interface ProjectDao extends JpaRepository<Project,Long>{

	List<Project> findByArchived(boolean archived);
}
