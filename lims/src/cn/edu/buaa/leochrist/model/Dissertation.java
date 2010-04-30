package cn.edu.buaa.leochrist.model;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Dissertation implements Serializable {

	private Integer id;

	private Person uploader;

	private String tile;

	private String file;

	private String author;

	private Date pubDate;

	private Date uploadDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Person getUploader() {
		return uploader;
	}

	public void setUploader(Person uploader) {
		this.uploader = uploader;
	}

	public String getTile() {
		return tile;
	}

	public void setTile(String tile) {
		this.tile = tile;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

}
