package ru.eduforum.challenge.units;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name="primaryComment")
public class primaryComment {
	
	public primaryComment(String login, int postID, String comment, Date publicationDate) {
		super();
		this.login = login;
		this.setPostID(postID);
		this.comment = comment;
		this.publicationDate = publicationDate;
	}
	public primaryComment() {
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ID;
	
	private String login;
	private int postID;
	private String comment;
	@Temporal(TemporalType.DATE)
	private Date publicationDate;
	public int getID() {
		return ID;
	}
	public String getComment() {
		return comment;
	}
	public Date getPublicationDate() {
		return publicationDate;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public int getPostID() {
		return postID;
	}
	public void setPostID(int postID) {
		this.postID = postID;
	}
	
}