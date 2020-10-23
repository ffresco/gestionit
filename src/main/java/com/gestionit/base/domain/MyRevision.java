package com.gestionit.base.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import javax.persistence.Entity;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@RevisionEntity(MyRevisionListener.class)
public class MyRevision extends DefaultRevisionEntity {

	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public LocalDateTime getRevisionDateTime() {
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(getTimestamp()), 
                TimeZone.getDefault().toZoneId()); 
		
	}
}