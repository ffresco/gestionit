package com.gestionit.base.domain;

import org.hibernate.envers.RevisionListener;

public class MyRevisionListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		MyRevision rev = (MyRevision) revisionEntity;
		rev.setUserName("PEPE");
	}


}