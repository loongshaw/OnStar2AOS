package com.autonavi.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

import org.apache.log4j.Logger;

import com.autonavi.constants.Constants;


public class ProtectionThreadService {

	/**
	 * @param 守护线程
	 */
	static Logger logger = Logger.getLogger(ProtectionThreadService.class); //ProtectionThreadService为类名
	
	public static FileLock getFileLock(){		
		
		File file = new File(Constants.fileLock_Path);		
		RandomAccessFile fi;
		FileLock fileLock = null;
		
		try {			
			fi = new RandomAccessFile(file, "rw");
			FileChannel fc = fi.getChannel();  
			fileLock = fc.tryLock(); 
	        	       
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			fileLock = null;
			logger.error("FileNotFoundException-文件未找到"+e);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			fileLock = null;
			logger.error("IOException-IO异常"+e);
		}  catch (OverlappingFileLockException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			fileLock = null;
			logger.error("OverlappingFileLockException-文件重复加锁"+e);
		}  
        
        return fileLock;
	}
	
	public static void main(String[] args){
		
		FileLock fileLock = getFileLock(); 
		if( fileLock!= null ){
			System.out.println("文件已加锁");
		}
		
		getFileLock();
	}

}
