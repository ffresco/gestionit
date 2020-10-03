package com.gestionit.base.domain;

import javax.persistence.Entity;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@RevisionEntity(MyRevisionListener.class)
public class MyRevision extends DefaultRevisionEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}