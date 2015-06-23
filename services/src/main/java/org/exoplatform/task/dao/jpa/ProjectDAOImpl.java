/* 
* Copyright (C) 2003-2015 eXo Platform SAS.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU Lesser General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program. If not, see http://www.gnu.org/licenses/ .
*/
package org.exoplatform.task.dao.jpa;

import org.exoplatform.task.dao.ProjectHandler;
import org.exoplatform.task.domain.Project;
import org.exoplatform.task.service.jpa.DAOHandlerJPAImpl;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by The eXo Platform SAS
 * Author : Thibault Clement
 * tclement@exoplatform.com
 * 4/10/15
 */
public class ProjectDAOImpl extends GenericDAOImpl<Project, Long> implements ProjectHandler {

  private static final Logger LOG = Logger.getLogger("ProjectDAOImpl");

  public ProjectDAOImpl(DAOHandlerJPAImpl taskServiceJPAImpl) {
    super(taskServiceJPAImpl);
  }

  @Override
  public List<Project> findSubProjects(Project project) {
    EntityManager em = daoHandler.getEntityManager();
    Query query = em.createNamedQuery(project != null ? "Project.findSubProjects" : "Project.getRootProjects");
    if(project != null) {
      query.setParameter("projectId", project.getId());
    }
    return query.getResultList();
  }

  @Override
  public List<Project> findSubProjectsByMemberships(Project project, List<String> memberships) {
    EntityManager em = daoHandler.getEntityManager();
    Query query = em.createNamedQuery(project != null ?
        "Project.findSubProjectsByMemberships"
        : "Project.findRootProjectsByMemberships");
    if(project != null) {
      query.setParameter("projectId", project.getId());
    }
    query.setParameter("memberships", memberships);
    return query.getResultList();
  }

  @Override
  public List<Project> findAllByMemberships(List<String> memberships) {
    Query query = daoHandler.getEntityManager().createNamedQuery("Project.findAllByMembership", Project.class);
    query.setParameter("memberships", memberships);

    return query.getResultList();
  }

  @Override
  public List<Project> findAllByMembershipsAndKeyword(List<String> memberships, String keyword) {
    Query query = daoHandler.getEntityManager().createNamedQuery("Project.findAllByMembershipAndKeyword", Project.class);
    query.setParameter("memberships", memberships);
    if (keyword == null || keyword.isEmpty()) {
      keyword = "%";
    } else {
      keyword = "%" + keyword.toUpperCase() + "%";
    }
    query.setParameter("keyword", keyword);

    return query.getResultList();
  }
}

