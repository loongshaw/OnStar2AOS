package com.autonavi.services;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class RefreshFileListService {

	/**
	 * @note 刷新指定文件夹路径下的所有文件
	 */
	static Logger logger = Logger.getLogger(RefreshFileListService.class); //RefreshFileListService为类名
	// public static int num = 0;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<String> getFileList(String path){
		
		LinkedList list = new LinkedList();
		List<String> filelist = new ArrayList<String>();
        File dir = new File(path);
        File file[] = dir.listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isDirectory())
                list.add(file[i]);
            else{
            	filelist.add(file[i].getAbsolutePath());
                // System.out.println(file[i].getAbsolutePath());
                // num++;
            }
        }
        File tmp;
        while (!list.isEmpty()) {
            tmp = (File)list.removeFirst();								//首个目录
            if (tmp.isDirectory()) {
                file = tmp.listFiles(); 
                if (file == null)
                    continue;
                for (int i = 0; i < file.length; i++) {
                    if (file[i].isDirectory())
                        list.add(file[i]);								//目录则加入目录列表，关键
                    else{
                    	filelist.add(file[i].getAbsolutePath());
                        // System.out.println(file[i]);
                        // num++;
                    }
                }
            } else {
            	filelist.add(tmp.getAbsolutePath());
                // System.out.println(tmp);
                // num++;
            }
        }
        
        /**
        for(int i=0;i<filelist.size();i++){
        	System.out.println(filelist.get(i));
        }
        **/
        
        return filelist;
	}
	
	// 将处理后的文件重命名！
	public static void renameFile(String filepath){
		
		 File file = new File(filepath);   								//指定文件名及路径   		 
		 file.renameTo(new File(filepath +".bat"));
		 
		 logger.info("RefreshFileListService.renameFile Result: OK");		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// renameFile("D://CSVfile//csv//123.txt");
		// getFileList("D://CSVfile");
	} 

}
