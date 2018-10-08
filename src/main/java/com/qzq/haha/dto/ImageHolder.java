package com.qzq.haha.dto;

import java.io.InputStream;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class ImageHolder {
   private String imageName;
   private CommonsMultipartFile image;
   
   public ImageHolder(String imageName,CommonsMultipartFile image){
	   this.image = image;
	   this.imageName = imageName;
   }

public String getImageName() {
	return imageName;
}

public void setImageName(String imageName) {
	this.imageName = imageName;
}

public CommonsMultipartFile getImage() {
	return image;
}

public void setImage(CommonsMultipartFile image) {
	this.image = image;
}


   
}
