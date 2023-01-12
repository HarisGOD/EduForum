package ru.eduforum.challenge.units;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;


@Entity
@Table(name="images")
public class indexImage {
	
	public indexImage() {
		super();
	}
	public indexImage(String pinTo, byte[] binImg,boolean isPost) {
		super();
		this.pinTo = pinTo;
		this.binImg = binImg;
		this.isPost=isPost;
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int ID;
	
	private boolean isPost;
	private String pinTo;
	@Lob
	//@Type(type = "org.hibernate.type.ImageType")
    private byte[] binImg;
	
	public int getID() {
		return ID;
	}
	public String getPinTo() {
		return pinTo;
	}
	public byte[] getBinImg() {
		return binImg;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public void setPinTo(String pinTo) {
		this.pinTo = pinTo;
	}
	public void setBinImg(byte[] binImg) {
		this.binImg = binImg;
	}
	public boolean isPost() {
		return isPost;
	}
	public void setPost(boolean isPost) {
		this.isPost = isPost;
	}
}