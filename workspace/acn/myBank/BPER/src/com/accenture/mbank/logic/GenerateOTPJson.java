
package com.accenture.mbank.logic;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.accenture.mbank.model.EntryModel;
import com.accenture.mbank.model.GenerateOTPResponseModel;
import com.accenture.mbank.model.LoginResponseModel;
import com.accenture.mbank.model.RequestPublicModel;
import com.accenture.mbank.util.Contants;
import com.accenture.mbank.util.LogManager;
import com.accenture.mbank.util.ServiceType;
import com.accenture.mbank.util.Utils;

public class GenerateOTPJson {

    /**
     * @param serviceType
     * @param bankName
     * @param enterpriseId
     * @param customerNumber
     * @param channel
     * @param otpChannel
     * @param userAgent
     * @param sessionId
     * @return
     */
	@Deprecated
	 public static String GenerateOTPReportProtocal(String otpChannel, RequestPublicModel publicModel) {
		return GenerateOTPReportProtocal(otpChannel, publicModel, "operationType", "beneficiary", 0);
	 }
	       
    /**
     * @param otpChannel
     * @param publicModel
     * @param operationType
     * @param beneficiary
     * @param amount
     * @return
     */
    public static String GenerateOTPReportProtocal(String otpChannel, RequestPublicModel publicModel,String operationType,String beneficiary,double amount) {
        String result = null;
        try {
            JSONObject jsonObj = new JSONObject();
            JSONObject generateOTPRequestObj = new JSONObject();

            generateOTPRequestObj.put("serviceType", ServiceType.generateOTP);
            generateOTPRequestObj.put("bankName", publicModel.getBankName());
            generateOTPRequestObj.put("enterpriseId", publicModel.getEnterpriseId());
            generateOTPRequestObj.put("customerNumber", publicModel.getCustomerNumber());
            generateOTPRequestObj.put("channel", publicModel.getChannel());
            generateOTPRequestObj.put("otpChannel", otpChannel);
            generateOTPRequestObj.put("userAgent", publicModel.getUserAgent());
            generateOTPRequestObj.put("sessionId", publicModel.getSessionId());
            generateOTPRequestObj.put("token", publicModel.getToken());
            generateOTPRequestObj.put("operationType",operationType);
            generateOTPRequestObj.put("beneficiary", beneficiary);
            generateOTPRequestObj.put("amount", Utils.generateMoney(amount));

            jsonObj.put("GenerateOTPRequest", generateOTPRequestObj);

            result = jsonObj.toString();
        } catch (Exception e) {
            LogManager.e("GenerateOTPReportProtocal is error" + e.getLocalizedMessage());
        }
        return result;
    }

    public static GenerateOTPResponseModel ParseGenerateOTPResponse(String json) {
        
        GenerateOTPResponseModel generateOTPResponse = new GenerateOTPResponseModel();
        if (json == null) {
            return null;
        }

        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONObject generateOTPObj = jsonObj.getJSONObject("GenerateOTPResponse");
            
            generateOTPResponse.responsePublicModel.setResultCode(generateOTPObj.optInt("resultCode"));
            generateOTPResponse.responsePublicModel.setResultDescription(generateOTPObj.optString("resultDescription"));
            if (generateOTPResponse.responsePublicModel.getResultCode() != 0) {
                JSONObject eventManagementObj = generateOTPObj.getJSONObject("eventManagement");
                generateOTPResponse.responsePublicModel.eventManagement.setErrorCode(eventManagementObj.optString("errorCode"));
                generateOTPResponse.responsePublicModel.eventManagement.setErrorDescription(eventManagementObj.optString("errorDescription"));
                return generateOTPResponse;
            }
            
            generateOTPResponse.responsePublicModel.setTransactionId(generateOTPObj.optString("transactionId"));

            JSONObject otpStateObj = generateOTPObj.getJSONObject("otpState");
            generateOTPResponse.setOtpAvailable(otpStateObj.optString("otpAvailable"));
            generateOTPResponse.setOtpErrorDescription(otpStateObj.optString("otpErrorDescription"));
            generateOTPResponse.setOtpKeySession(otpStateObj.optString("otpKeySession"));
            
        }catch(Exception e){
            LogManager.e("GenerateOTPReportProtocal is error" + e.getLocalizedMessage());
        }
        
        return generateOTPResponse;
    }
}