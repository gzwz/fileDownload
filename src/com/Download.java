package com;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

public class Download extends HttpServlet {

	private static final String textUrl = "F:/JAVA/filedownload/WebRoot/resources/file/aaa.txt";
	private static final String imgUrl = "F:/JAVA/filedownload/WebRoot/resources/image/bb.jpg";
	private static final String wordUrl = "F:/JAVA/filedownload/WebRoot/resources/word/aaa.xls";
	private String text = "F:/file/a.txt";
	private String img = "F:/file/a.jpg";
	private String word = "F:/file/a.xls";
	
/*	@Test
	public void aa(){
		File dir = new File("F:/file/");
		if (dir.exists()) {
			System.out.println(	dir.delete());
		}
	}*/
	
	@Test
	public void zip(){
		List<File> files = new ArrayList<File>();
		files.add(new File(textUrl));
		files.add(new File(imgUrl));
		files.add(new File(wordUrl));
		String strZipName = "F:/file/Demo.zip"; 
		FileInputStream fis = null;
		ZipOutputStream outzip = null;
		try {
			outzip = new ZipOutputStream(new FileOutputStream(strZipName));
		
			for (int i = 0; i < files.size(); i++) {
				fis = new FileInputStream(files.get(i));
				outzip.putNextEntry(new ZipEntry("文件夹"+i+"/"+files.get(i).getName()));
			
			int len;  
			while((len = fis.read()) != -1) {  
				outzip.write(len);  
				outzip.flush();
			} 
			}
			outzip.close();
			fis.close();
			
			System.out.println("生成Demo.zip成功");   
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void test(){
		//读取文本文件
		File file = new File(imgUrl);
		
		File file2 = new File(img);
		
		File dir = new File("F:/file/");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
			fis = new FileInputStream(file);
			fos = new FileOutputStream(file2);
			int tempbyte;
            while ((tempbyte = fis.read()) != -1) {
                System.out.write(tempbyte);
                fos.write(tempbyte);
            }
            fos.flush();
            
            fos.close();
            fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)  {
		String strZipName = "Demo.zip"; 
		response.setContentType("application/zip");// 定义输出类型
		response.setHeader("Content-Disposition", "attachment; filename="+strZipName);
		OutputStream out = null;
		try {
			out=response.getOutputStream();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		Map<String, List<File>> map = new HashMap<>();
		
		List<File> files1 = new ArrayList<File>();
		files1.add(new File(textUrl));
		map.put("文本文件", files1);
		List<File> files2 = new ArrayList<File>();
		files2.add(new File(imgUrl));
		map.put("图片文件", files2);
		List<File> files3 = new ArrayList<File>();
		files3.add(new File(wordUrl));
		map.put("Office文件", files3);
		
		
		FileInputStream fis = null;
		ZipOutputStream outzip = null;
		try {
			outzip = new ZipOutputStream(response.getOutputStream());
		
			for(Iterator i= map.entrySet().iterator(); i.hasNext();){
				Map.Entry e=(Map.Entry)i.next();
				System.out.println(e.getKey());
				for (int j = 0; j < map.get(e.getKey()).size(); j++) {
					fis = new FileInputStream(map.get(e.getKey()).get(j));
					outzip.putNextEntry(new ZipEntry(e.getKey()+"/"+map.get(e.getKey()).get(j).getName()));
					int len;  
					while((len = fis.read()) != -1) {  
						outzip.write(len);  
						outzip.flush();
					} 
				}
				
				
				
				
			}
			
			outzip.close();
			fis.close();
			
			System.out.println("生成Demo.zip成功");   
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

}
