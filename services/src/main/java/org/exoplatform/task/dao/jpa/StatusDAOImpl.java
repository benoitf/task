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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.exoplatform.task.dao.StatusHandler;
import org.exoplatform.task.domain.Status;
import org.exoplatform.task.service.jpa.DAOHandlerJPAImpl;

/**
 * Created by The eXo Platform SAS
 * Author : Thibault Clement
 * tclement@exoplatform.com
 * 4/10/15
 */
public class StatusDAOImpl extends GenericDAOImpl<Status, Long> implements StatusHandler {

  public StatusDAOImpl(DAOHandlerJPAImpl taskServiceJPAImpl) {
    super(taskServiceJPAImpl);
  }

  @Override
  public Status findLowestRankStatusByProject(Long projectId) {
    EntityManager em = daoHandler.getEntityManager();
    Query query = em.createNamedQuery("Status.findLowestRankStatusByProject", Status.class);
    query.setParameter("projectId", projectId);
    return (Status)query.getSingleResult();
  }
  
  @Override
  public Status findHighestRankStatusByProject(long projectId) {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("projectId", projectId);
    List<Status> sts = findByNamedQuery("Status.findHighestRankStatusByProject", params);
    if (!sts.isEmpty()) {
      return sts.get(0);
    } else {
      return null;
    }
  }
  
  @Override
  public Status findByName(String name, long projectID) {
    if (!StringUtils.isNotEmpty(name)) {
      return null;
    }
    
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("name", name);
    params.put("projectID", projectID);
    List<Status> sts = findByNamedQuery("Status.findByName", params);
    if (!sts.isEmpty()) {
      return sts.get(0);
    } else {
      return null;
    }
  }

}

