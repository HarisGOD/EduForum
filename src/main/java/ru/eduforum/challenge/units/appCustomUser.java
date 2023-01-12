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
@Table(name="users")
public class appCustomUser {
	public appCustomUser(String login, String usernAme, String password, boolean isEnabled,
			Date publicationDate, int profileImgId) {
		super();
		this.login = login;
		username = usernAme;
		this.password = password;
		this.isEnabled = isEnabled;
		this.publicationDate = publicationDate;
		this.profileImgId=profileImgId;
	}
	public appCustomUser() {
		
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ID;
	private String login;
	private String username;
	private String password;
	private boolean isEnabled;
	private int profileImgId;
	
	@Temporal(TemporalType.DATE)
	private Date publicationDate;

	public String getLogin() {
		return login;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setUsername(String usernAme) {
		username = usernAme;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	public int getProfileImgId() {
		return profileImgId;
	}
	public void setProfileImgId(int profileImgId) {
		this.profileImgId = profileImgId;
	}
	
	
	
	
}