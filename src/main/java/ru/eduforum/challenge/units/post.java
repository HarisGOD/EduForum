package ru.eduforum.challenge.units;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="eduforum_post")
public class post {
	
	public post(String name, String path, String content, Date created, String creator) {
		super();
		this.name = name;
		this.path = path;
		this.content = content;
		this.created = created;
		this.creator=creator;
	}
	public post() {
		
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ID;
	private String name;
	private String path;
	@Column(name = "content", columnDefinition = "text")
	private String content;
	@Temporal(TemporalType.DATE)
	private Date created;
	private String creator;
	public int getID() {
		return ID;
	}
	public String getName() {
		return name;
	}
	public String getPath() {
		return path;
	}
	public String getContent() {
		return content;
	}
	public Date getCreated() {
		return created;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreatedBy(String creator) {
		this.creator = creator;
	}
	
	
}