package com.vuong.ebookapp;

public class ItemStoryList {
	
	private String CId;
 	private String CategoryName;
	private String CategoryImage;
	private String CatId;
	private String NewsHeading; //ten chuong
 	private String NewsDescription; //noi dung
	private String NewsImage;
	private String NewsDate; //tom tat
	
	public String getCId() {
		return CId;
	}

	public void setCId(String cid) {
		this.CId = cid;
	}
 	
	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String categoryname) {
		this.CategoryName = categoryname;
	}
	
	public String getCategoryImage() {
		return CategoryImage;
	}

	public void setCategoryImage(String categoryimage) {
		this.CategoryImage = categoryimage;
	}
	
	
	public String getCatId() {
		return CatId;
	}

	public void setCatId(String catid) {
		this.CatId = catid;
	}
	
 	public String getNewsHeading() {
		return NewsHeading;
	}

	public void setNewsHeading(String newsheading) {
		this.NewsHeading = newsheading;
	}
	
	public String getNewsDescription() {
		return NewsDescription;
	}

	public void setNewsDescription(String newsdescription) {
		this.NewsDescription = newsdescription;
	}
	public String getNewsImage() {
		return NewsImage;
	}

	public void setNewsImage(String newsimage) {
		this.NewsImage = newsimage;
	}
	public String getNewsDate() {
		return NewsDate;
	}

	public void setNewsDate(String newsdate) {
		this.NewsDate = newsdate;
	}

}
