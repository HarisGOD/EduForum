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
@Table(name="secondaryComment")
public class secondaryComment {
	public secondaryComment(String login, int primaryCommentId, String comment, Date publicationDate) {
		super();
		this.login = login;
		this.primaryCommentId = primaryCommentId;
		this.comment = comment;
		this.publicationDate = publicationDate;
	}
	public secondaryComment() {
		super();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ID;
	
	private String login;
	private int primaryCommentId;
	private String comment;
	@Temporal(TemporalType.DATE)
	private Date publicationDate;
	public int getID() {
		return ID;
	}
	public int getPrimaryCommentId() {
		return primaryCommentId;
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
	public void setPrimaryCommentId(int primaryCommentId) {
		this.primaryCommentId = primaryCommentId;
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
	
}