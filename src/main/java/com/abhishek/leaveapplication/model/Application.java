package com.abhishek.leaveapplication.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "APPLICATION")
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "APPLICATION_ID")
	private long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User from;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "MANAGER_ID")
	private User to;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date submissionDate;

	@Column(name = "STATUS")
	private char status;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}
	
	
}
