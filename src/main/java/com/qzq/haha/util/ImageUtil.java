package com.qzq.haha.util;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.geometry.Size;

public class ImageUtil {
    private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    public static String generateThumbnail(CommonsMultipartFile shopImg,String targetAddr){
    	String realFileName = getRandomFileName();
    	String extension = getFileExtension(shopImg);
    	makeDirPath(targetAddr);
    	String relativeAddr = targetAddr + realFileName + extension;
    	File dest = new File(PathUtil.getImgBasePath()+relativeAddr);
    	try{
    		Thumbnails.of(shopImg.getInputStream()).size(200, 200).
    		watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/waterprint.jpg")), 0.25f)
    		.outputQuality(0.8f).toFile(dest);   		
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	return relativeAddr;
    }
    private static void makeDirPath(String targetAddr) {
		// TODO Auto-generated method stub
		String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
	    File dirPath = new File(realFileParentPath);
	    if(!dirPath.exists()){
	    	dirPath.mkdirs();
	    }
    }
    /**
     * 獲取拓展名
     * @param shopImg
     * @return
     */
	private static String getFileExtension(CommonsMultipartFile shopImg) {
		// TODO Auto-generated method stub
    	String originalFileName = shopImg.getOriginalFilename();
    	
		return originalFileName.substring(originalFileName.lastIndexOf("."));
	}
	/**
     * 生成隨機文件名
     * @return
     */
	public static String getRandomFileName() {
		// TODO Auto-generated method stub
		int rannum = r.nextInt(89999)+10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr+rannum;
	}
	public static void deleteFileOrPath(String storePath){
		File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
	    if(fileOrPath.exists()){
	    	if(fileOrPath.isDirectory()){
	    		File files[] = fileOrPath.listFiles();
	    		for(int i=0;i<files.length;i++)
	    			files[i].delete();
	    	}
	    	fileOrPath.delete();
	    }
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
     Thumbnails.of(new File("D:/Imges/back.jpg"))
     .size(1024, 1024).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"waterprint.jpg")),0.25f).outputQuality(0.8f)
     .toFile("D:/Imges/back2.jpg");
	}

}
