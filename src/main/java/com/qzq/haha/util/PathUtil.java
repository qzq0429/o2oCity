package com.qzq.haha.util;

import org.apache.jasper.tagplugins.jstl.core.If;

public class PathUtil {
	private static String seperator = System.getProperty("file.separator");
	public static String getImgBasePath(){
      String os = System.getProperty("os.name");
      String basePath = "";
      if(os.toLowerCase().startsWith("win")){
    	  basePath="D:/projectdev/image";
      }else{
    		 basePath="/root" ;
    	  }
      basePath = basePath.replace("/", seperator);
      return basePath;
	}
public static String getShopImagePath(long shopId){
	String imagePath = "/upload/images/item/shop/"+shopId+"/";
	return imagePath.replace("/", seperator);
}
}
