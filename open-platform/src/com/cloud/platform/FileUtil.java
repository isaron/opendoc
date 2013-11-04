package com.cloud.platform;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class FileUtil {
	
	public static final String LINE = "\r\n";

	/**
	 * copy single file
	 * 
	 * @param srcAddress
	 * @param destAddress
	 * @throws IOException 
	 */
	public static void copyFile(String srcFileAddr, String destFileAddr) throws IOException {
		
		File srcFile = new File(srcFileAddr);
		File destFile = new File(destFileAddr);
		
		// copy file
		copyFile(srcFile, destFile);
	}
	
	/**
	 * copy single file
	 * 
	 * @param srcFile
	 * @param destFile
	 * @throws IOException
	 */
	public static void copyFile(File srcFile, File destFile) throws IOException {
		
		// check source and dest file if is exist
		if(!srcFile.exists() || destFile.exists()) {
			return;
		}
		
		// check dest file dir if is exist, if not, create dest dir
		String destAddress = destFile.getAbsolutePath();
		String destDir = destAddress.substring(0, destAddress.lastIndexOf(File.separator));
		
		File dir = new File(destDir);
		
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		
		try {
			// init input stream
			bis = new BufferedInputStream(new FileInputStream(srcFile));
			
			// init output stream
			bos = new BufferedOutputStream(new FileOutputStream(destFile));
			
			// init buffer byte array
            byte[] b = new byte[1024 * 5];
            int len;

            // write data
            while ((len = bis.read(b)) != -1) {
            	bos.write(b, 0, len);
            }
            
            // flush output stream to dist io
            bos.flush();
			
		} finally {
			// make sure close stream
			if(bis != null) {
				bis.close();
			}
			if(bos != null) {
				bos.close();
			}
		}
	}
	
	/**
	 * copy dir files
	 * 
	 * @param srcDir
	 * @param destDir
	 * @throws IOException
	 */
	public static void copyDir(String srcDir, String destDir) throws IOException {
		
		File src = new File(srcDir);
		File dest = new File(destDir);
		
		// check source and dest dir if is exist
		if(!src.exists() || !src.isDirectory() || dest.exists()) {
			return;
		}
		
		// get files under dir
		File[] files = src.listFiles();
		
		// copy files
		for(File f : files) {
			
			if(f.isDirectory()) {
				copyDir(f.getAbsolutePath(), destDir + "\\" + f.getName() + "\\");
			}
			else {
				copyFile(f.getAbsolutePath(), destDir + f.getName());
			}
		}
	}
	
	/**
	 * read file datas
	 * 
	 * @param fileAddr
	 * @return
	 * @throws IOException
	 */
	public static String readFile(String fileAddr) throws IOException {
		
		File file = new File(fileAddr);
		
		if(!file.exists()) {
			return "";
		}
		
		BufferedReader fr = null;
		String line;
		StringBuffer datas = new StringBuffer();
		
		try {
			// init file reader
			fr = new BufferedReader(new FileReader(fileAddr));
			
			// iterate read line
			while((line = fr.readLine()) != null) {
				datas.append(line + "\n");
			}
			
		} finally {
			if(fr != null) {
				fr.close();
			}
		}
		
		return datas.toString();
	}
	
	/**
	 * write datas to file
	 * 
	 * @param destFileAddr
	 * @param datas
	 * @throws IOException
	 */
	public static void writeFile(String destFileAddr, String datas) throws IOException {
		
		// check dest file dir exist
		String destDir = destFileAddr.substring(0, destFileAddr.lastIndexOf(File.separator));
		File dir = new File(destDir);
		
		if(!dir.exists()) {
			dir.mkdirs();
		}
		
		PrintWriter pw = null;
		
		try {
			// write datas to file
			pw = new PrintWriter(new BufferedWriter(new FileWriter(destFileAddr)));
			
			pw.print(datas);
			pw.flush();
			
		} finally {
			if(pw != null) {
				pw.close();
			}
		}
	}
	
	/**
	 * delete file
	 * 
	 * @param fileAddr
	 */
	public static void deleteFile(String fileAddr) {
		
		File file = new File(fileAddr);
		
		if(file.exists()) {
			file.delete();
		}
	}
	
	/**
	 * delete dir files
	 * 
	 * @param dirAddr
	 */
	public static void deleteDir(String dirAddr) {
		
		File dir = new File(dirAddr);
		
		if(!dir.exists()) {
			return;
		}
		
		// get dir files
		File[] files = dir.listFiles();
		
		// iterate dir files
		for(File f : files) {
			
			if(f.isDirectory()) {
				deleteDir(f.getAbsolutePath());
			}
			else {
				deleteFile(f.getAbsolutePath());
			}
		}
	}
	
	/**
	 * zip dir files without inner dirs
	 * 
	 * @param dirAddr
	 * @param zipPath
	 * @throws IOException
	 */
	public static void zipDir(String dirAddr, String zipPath) throws IOException {
		
		File dir = new File(dirAddr);
		
		if(!dir.exists()) {
			return;
		}
		
		// check dest zip file exist
		String destZipDir = zipPath.substring(0, zipPath.lastIndexOf(File.separator));
		File zipDir = new File(destZipDir);
		
		if(!zipDir.exists()) {
			zipDir.mkdirs();
		}
		
		ZipOutputStream zos = null;
		
		try {
			// init zip file output stream
			zos = new ZipOutputStream(new CheckedOutputStream(
					new FileOutputStream(zipPath), new CRC32()));
			
			zip(dir, zos, "");
			
			zos.flush();
			
		} finally {
			if(zos != null) {
				zos.close();
			}
		}
	}
	
	/**
	 * zip file
	 * 
	 * @param dir
	 * @param zos
	 * @param baseDir
	 * @throws IOException
	 */
	private static void zip(File dir, ZipOutputStream zos, String baseDir) throws IOException {
		
		BufferedInputStream bis = null;
		
		try {
			// get dir files
			File[] files = dir.listFiles();
			
			// iterate dir files
			for(File f : files) {
				
				if(f.isDirectory()) {
					zip(f, zos, baseDir + f.getName() + "\\");
				}
				else {
					// init file input stream
					bis = new BufferedInputStream(new FileInputStream(f));
					
					// put file to zip output stream
					ZipEntry entry = new ZipEntry(baseDir + f.getName());
					zos.putNextEntry(entry);
					
					// init buffer byte array
		            byte[] b = new byte[1024 * 5];
		            int len;

		            // write data
		            while ((len = bis.read(b)) != -1) {
		            	zos.write(b, 0, len);
		            }
				}
			}
			
		} finally {
			if(bis != null) {
				bis.close();
			}
		}
	}
	
	/**
	 * ===============================  Test  ===============================
	 */
	public static void main(String[] args) {
		
		try {
			zipDir("E:\\test\\", "E:\\test.zip");
			
		} catch(Exception e) {}
	}
}
