package com.autonavi.httpactions;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.autonavi.constants.Constants;
import com.autonavi.dto.base.BaseDtoRsp;
import com.autonavi.dto.rsp.UploadRspDto;
import com.autonavi.httpactions.base.ReqBaseAction;
import com.autonavi.services.RequestParaService;

public class UploadAction extends ReqBaseAction<UploadRspDto>{

	/**
	 * @param Uplaod请求:参数(title,points,description)
	 */
	static Logger logger = Logger.getLogger(UploadAction.class); //UploadAction为类名
	public final static String UpLoad_HOST = Constants.SERVER_ROOT_URL_UPLOAD; 
	
	@SuppressWarnings("rawtypes")
	public static UploadRspDto uploadInfo(HashMap<String,String> map_temp){
		UploadRspDto uploadrsp = new UploadRspDto();
		
		try{
			// ReqBaseAction.Status = 0;
			String requestPara = RequestParaService.getRequestParameter(map_temp);	
			// logger.info("UploadAction Upload RequestPara: "+ requestPara);	
			BaseDtoRsp  dtoRsp = new ReqBaseAction().doHttpForObject(requestPara,0,UpLoad_HOST);		
			// logger.info("DtoRsp Result: "+ dtoRsp);	
				
			JSONObject obj = new JSONObject(dtoRsp.getResponseInfo());
			int status = Integer.parseInt(obj.getString("code"));
			String message = obj.getString("message");
			
			if(status == 1){
				// 上传成功！
				uploadrsp.setCode(0);				
			} else {
				// 上传失败！
				uploadrsp.setCode(1);
			}
			
			uploadrsp.setMessage(message);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			uploadrsp.setCode(1);
			uploadrsp.setMessage(e.toString());
			logger.error("UploadAction Upload Error: "+ e);	
		}
		
		return uploadrsp;
		// logger.info("UploadAction Upload Result: "+ uploadrsp.getCode());		
	}	

}
