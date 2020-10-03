package com.gestionit.base.domain;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;



public class MyRevisionListener implements RevisionListener {
	

	@Override
	public void newRevision(Object revisionEntity) {
		MyRevision rev = (MyRevision) revisionEntity;
		if(SecurityContextHolder.getContext().getAuthentication()!=null) {
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserDetails userDetails = null;
			if (principal instanceof UserDetails) {
				userDetails = (UserDetails) principal;
			}
			rev.setUserName(userDetails.getUsername());
		}
	}

}