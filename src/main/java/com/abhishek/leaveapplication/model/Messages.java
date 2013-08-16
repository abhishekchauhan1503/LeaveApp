package com.abhishek.leaveapplication.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MESSAGES")
public class Messages {

	@Id
	@Column(name = "MESSAGE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne
	@JoinColumn(name = "FROM_USER")
	private User from;

	@OneToOne
	@JoinColumn(name = "TO_USER")
	private User to;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "ISREAD")
	private boolean read;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}
}
