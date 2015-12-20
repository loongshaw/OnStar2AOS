package com.autonavi.constants;

public class Constants {

	/**
	 * @param 记录静态参数
	 */
	
	// 请求url地址-提交
	// public static String SERVER_ROOT_URL_UPLOAD = "http://12345/ws/feedback/report";				// 测试接口
	public static String SERVER_ROOT_URL_UPLOAD = “12345/ws/feedback/report";						// ###正式接口
	// 请求url地址-下载
	// public static String SERVER_ROOT_URL_DOWNLOAD = "http://12345:8080/gongdan/GetOnstar";				// 测试接口
	public static String SERVER_ROOT_URL_DOWNLOAD = "http://12345:8080/gongdan/GetOnstar";					// ###正式接口
	
	// 运行模式：0测试，1正式
	// public static int MODE = 0;																						// 测试模式
	public static int MODE = 1;																						// ###正式模式
	
	// 分页查询最大分页量
	public static int MaxPage =;
	
	// channel地址
	public static String CHANNEL = "";
	
	// key值
	public static String KEY = "";
	
	// dip
	public static int dip = ;
	
	// type
	public static int type = ;
	
	// 所需要的关键字,length=3,channel, type, description
	public static String[] KEY_NEEDS = {"onstar",type+"",""};
	
	// @key
	public static String ATKEY = "@"+KEY;
	
	// 签名SIGN
	public static String SIGN = "";
	
	// 网络请求超时30秒
	public static final int REQUEST_TIMEOUT = 200 * 1000; 
	
	// 在 网络请求失败次数,10次
	public static final int REQUEST_FAILURE_TIMES = 10;
	
	// 生产地址--输入EXCEL存放路径
	public static String filePath_Production = "/alidata/www/wwwroot/input";	
	
	// 对比排重txt存放结果路径
	public static String filePath_Txt = "/alidata/www/wwwroot/txtfiles/compareData.txt";
	
	// 对比排重txt存放结果路径
	
	public static String filePath_Txt_AOS = "/alidata/www/wwwroot/txtfiles/AOSID.txt";
	
	// 对比排重txt存放结果路径
	
	public static String filePath_Txt_CountRecord = "/alidata/www/wwwroot/txtfiles/EveryDayCountRecord.txt";
	
	
	public static String filePath= "/alidata/www/wwwroot/txtfiles/";
	// public static String filePath= "D://";
	
	public static String fileName = "compareData.txt";
	
	// OnStar反馈结果存放路径
	
	public static String filePath_Xlsx_Output = "/alidata/www/wwwroot/output/";												// ###正式模式
	
	// 配置文件路径

	public static String filePath_Config = "/alidata/www/wwwroot/conf/Records.xml";
	
	// 文件锁路径
	
	public static String fileLock_Path = "/alidata/www/wwwroot/conf/fileLock.txt";
}
