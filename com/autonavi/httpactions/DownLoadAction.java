package com.autonavi.httpactions;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.autonavi.constants.Constants;
import com.autonavi.dto.base.BaseDtoRsp;
import com.autonavi.dto.rsp.DownLoadRspDto;
import com.autonavi.dto.rsp.UploadRspDto;
import com.autonavi.httpactions.base.ReqBaseAction;
import com.autonavi.services.RequestParaService;
import com.autonavi.services.ResponseService;

public class DownLoadAction extends ReqBaseAction<UploadRspDto>{

	/**
	 * @param 获取DownLoad数据（反馈结果！）
	 */
	static Logger logger = Logger.getLogger(DownLoadAction.class); //DownLoadAction为类名
	public final static String DownLoad_HOST = Constants.SERVER_ROOT_URL_DOWNLOAD; 	
	
	public static List<DownLoadRspDto> downLoadInfo(int page,int day){
		
		List<DownLoadRspDto> downloadrsp_list = null;
		
		try {
			// ReqBaseAction.Status = 1;
			String requestPara = RequestParaService.getDownRequestParameter(page,day);;	
			logger.info("requestPara: "+ requestPara);	
			
			@SuppressWarnings("rawtypes")
			BaseDtoRsp dtoRsp = new ReqBaseAction().doHttpForObject(requestPara,1,DownLoad_HOST); 	
			logger.info("DtoRsp Result: "+ dtoRsp);	
				
			JSONObject obj = new JSONObject(dtoRsp.getResponseInfo());
			logger.info("DtoRsp Result: "+ obj);	
			downloadrsp_list = ResponseService.getResponseList(obj);			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			logger.error("DownLoadAction downLoadInfo Error: "+ e);	
			downloadrsp_list = new ArrayList<DownLoadRspDto>();
			return downloadrsp_list;
			
		}
		
		return downloadrsp_list;
	}

}
