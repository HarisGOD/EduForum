package ru.eduforum.challenge.units;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="appCustomRights")
public class rights {
	public rights(String login, String rightType) {
		super();
		this.login = login;
		this.rightType = rightType;
	}
	public rights() {
		
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ID;
	
	private String login;
	private String rightType;
	public int getID() {
		return ID;
	}
	public String getLogin() {
		return login;
	}
	public String getRightType() {
		return rightType;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public void setlogin(String login) {
		this.login = login;
	}
	public void setRightType(String rightType) {
		this.rightType = rightType;
	}
	
}