package com.vivek9237.eic.restsdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;
import javax.naming.AuthenticationException;


public class EicClient {
	
	private String EIC_BASE_URL;
	private String EIC_USERNAME;
	private String EIC_PASSWORD;
	private AccessToken accessToken;
	private RefreshToken refreshToken;
	private Map<String,Object> eicRestApiConfig;

	public EicClient(String tenant, String refreshToken) throws Exception{
		this(new HashMap<String, String>() {{ put("tenant", tenant); put("refreshToken", refreshToken);}});
	}
	public EicClient(String tenant,String username,String password) throws Exception{
		this(new HashMap<String, String>() {{ put("tenant", tenant); put("username", username); put("password", password);}});
	}
	public EicClient(Map<String,String> configs) throws Exception{
		String protocol = configs.getOrDefault("protocol","https");
		String tenant = configs.getOrDefault("tenant",null);
		String domainName = configs.getOrDefault("domainName","saviyntcloud.com");
		String port = configs.getOrDefault("port","443");
		String refreshToken = configs.getOrDefault("refreshToken",null);
		String username = configs.getOrDefault("username",null);
		String password = configs.getOrDefault("password",null);
		Integer refreshTokenExpiryDate = Integer.parseInt(configs.getOrDefault("refreshTokenExpiryDate","300"));
		EIC_BASE_URL = protocol+"://" + tenant + "." + domainName + ":" + port;
		if(username!=null && password!=null){
			EIC_USERNAME = username;
			EIC_PASSWORD = password;
		} else if(refreshToken!=null){
			this.refreshToken = new RefreshToken(refreshToken, EicClientUtils.addDaysToDate(new Date(), refreshTokenExpiryDate));
		} else {
			throw new Exception("[username and password] or [refreshToken] is null.");
		}
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		eicRestApiConfig = EicClientUtils.parseYamlFile("/eic_rest_api.yml");
	}
	
	public String getAccessToken() throws AuthenticationException, IOException, Exception{
		if(this.accessToken!=null && this.accessToken.isValid()){
			return this.accessToken.getToken();
		} else{
			return getAccessToken(true);
		}
	}

	public String getRefreshToken(){
		if(this.refreshToken!=null){

		}
		return this.refreshToken.getToken();
	}

	public String getAccessToken(Boolean force) throws IOException, AuthenticationException,Exception {
		String accessToken;
		if(this.refreshToken==null){
			accessToken = getAccessTokenFromUsernameAndPassword();
		} else{
			accessToken = getAccessTokenFromRefreshToken();
		}
		return accessToken;
	}
	private Map<String, Object> getApiConfigMap(String category, String api){
		Map<String, Object> apiConfigMap = ((Map<String, Object>)((Map<String, Object>) eicRestApiConfig.get(category)).get(api));
		return apiConfigMap;
	}
	private String getAccessTokenFromUsernameAndPassword() throws Exception {
		Map<String,Object> apiConfig = getApiConfigMap("Authentication", "Get_Authorization_Token");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = "{\"username\":\"" + EIC_USERNAME + "\",\"password\":\"" + EIC_PASSWORD + "\"}";
		Map<String, String> headers = (Map<String, String>)apiConfig.get("HEADER");
		EicResponse eicResponse = EicClientUtils.sendRequest(apiUrl, method, headers, requestBody);
		if(eicResponse.getResponseCode()==401){
			throw new AuthenticationException("Bad username or password. Response:"+eicResponse.toString());
		} else if(eicResponse.getResponseCode()==400){
			throw new Exception("Bad request. Response:"+eicResponse.toString());
		} else if(eicResponse.getResponseCode()!=200){
			throw new Exception("Unknown exception. Response:"+eicResponse.toString());
		} else {
			String access_token = eicResponse.getBodyAsJson().get("access_token").getAsString();
			Integer expires_in = Integer.parseInt(eicResponse.getBodyAsJson().get("expires_in").getAsString());
			Date exipryDate = EicClientUtils.addSecondsToDate(new Date(), expires_in);
			String refresh_token = eicResponse.getBodyAsJson().get("refresh_token").getAsString();
			this.accessToken = new AccessToken(access_token, exipryDate);
			this.refreshToken = new RefreshToken(refresh_token);
		}
		return this.accessToken.getToken();
	}
	private String getAccessTokenFromRefreshToken() throws Exception {
		Map<String,Object> apiConfig = getApiConfigMap("Authentication", "Refresh_Authorization_Token");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = "grant_type="+URLEncoder.encode("refresh_token", "UTF-8")+"&refresh_token="+URLEncoder.encode(this.refreshToken.getToken(), "UTF-8");
		Map<String, String> headers = (Map<String, String>)apiConfig.get("HEADER");
		EicResponse eicResponse = EicClientUtils.sendRequest(apiUrl, method, headers, requestBody);
		if(eicResponse.getResponseCode()==401){
			throw new AuthenticationException("Bad username or password. Response:"+eicResponse.toString());
		} else if(eicResponse.getResponseCode()==400){
			throw new Exception("Bad request. Response:"+eicResponse.toString());
		} else if(eicResponse.getResponseCode()!=200){
			throw new Exception("Unknown exception. Response:"+eicResponse.toString());
		} else {
			String access_token = eicResponse.getBodyAsJson().get("access_token").getAsString();
			Integer expires_in = Integer.parseInt(eicResponse.getBodyAsJson().get("expires_in").getAsString());
			Date exipryDate = EicClientUtils.addSecondsToDate(new Date(), expires_in);
			String refresh_token = eicResponse.getBodyAsJson().get("refresh_token").getAsString();
			this.accessToken = new AccessToken(access_token, exipryDate);
			this.refreshToken = new RefreshToken(refresh_token);
		}
		return this.accessToken.getToken();
	}

	public Map<String,Object> getUserByUsername(String username)
			throws AuthenticationException, Exception {
		Map<String,Object> body = new HashMap<String, Object>();
		body.put("username",username);
		List<Map<String,Object>> listOfUsers = getAllUsers(body);
		if(listOfUsers!=null && listOfUsers.size()==1){
			return listOfUsers.get(0);
		} else{
			return null;
		}
	}
	public List<Map<String,Object>> getAllUsers(Map<String,Object> body) throws AuthenticationException, IOException, Exception{
		List<Map<String,Object>> userDetailsList = new ArrayList<>();
		Integer pageSize = 3;
		if(body.get("max")==null){
			body.put("max",pageSize);
		}
		if(body.get("offset")==null){
			body.put("offset",0);
		}
		EicResponse eicResponse =  getUsers(body,true);
		if(eicResponse!=null && eicResponse.getResponseCode()==200){
			Object userDetailsObj = EicClientUtils.jsonToMap(eicResponse.getBody()).get("userdetails");
			if(userDetailsObj!=null && userDetailsObj instanceof Map){
				userDetailsList.add((Map<String,Object>)userDetailsObj);
			}  else if (userDetailsObj!=null && userDetailsObj instanceof List){
				Integer totalCount = Integer.parseInt(eicResponse.getBodyAsJson().get("totalcount").getAsString());
				Integer displaycount = Integer.parseInt(eicResponse.getBodyAsJson().get("displaycount").getAsString());
				userDetailsList = (List<Map<String,Object>>)EicClientUtils.jsonToMap(eicResponse.getBody()).get("userdetails");
				if(totalCount>displaycount && userDetailsList!=null && userDetailsList.size()>0){
					while(true){
						body.put("offset", ((Integer)body.get("offset")) +((Integer)body.get("max")));
						EicResponse eicResponseSubsequent =  getUsers(body,true);
						displaycount = Integer.parseInt(eicResponseSubsequent.getBodyAsJson().get("displaycount").getAsString());
						if(displaycount!=0){
							userDetailsList.addAll((List<Map<String,Object>>)EicClientUtils.jsonToMap(eicResponse.getBody()).get("userdetails"));
						} else{
							break;
						}
					}
				}
			} else{
				Integer totalCount = Integer.parseInt(eicResponse.getBodyAsJson().get("totalcount").getAsString());
				Integer displaycount = Integer.parseInt(eicResponse.getBodyAsJson().get("displaycount").getAsString());
				userDetailsList = (List<Map<String,Object>>)EicClientUtils.jsonToMap(eicResponse.getBody()).get("userlist");
				if(totalCount>displaycount && userDetailsList!=null && userDetailsList.size()>0){
					while(true){
						body.put("offset", ((Integer)body.get("offset")) +((Integer)body.get("max")));
						EicResponse eicResponseSubsequent =  getUsers(body,true);
						displaycount = Integer.parseInt(eicResponseSubsequent.getBodyAsJson().get("displaycount").getAsString());
						if(displaycount!=0){
							userDetailsList.addAll((List<Map<String,Object>>)EicClientUtils.jsonToMap(eicResponse.getBody()).get("userlist"));
						} else{
							break;
						}
					}
				}
			}
		} else{
			userDetailsList = null;
		}
		return userDetailsList;
	}
	private List<Map<String,Object>> getUsers(Map<String,Object> body) throws Exception{
		List<Map<String,Object>> userDetailsList;
		Map<String,Object> apiConfig = getApiConfigMap("Users", "Get_User_Details");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = EicClientUtils.convertMapToJsonString(body);
		Map<String, String> headers = (Map<String, String>)apiConfig.get("HEADER");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicRequest eicRequest = new EicRequest(apiUrl, method, headers, requestBody);
		EicResponse eicResponse = EicClientUtils.sendRequest(eicRequest);
		if(eicResponse.getResponseCode()==200){
			Object userDetailsObj = EicClientUtils.jsonToMap(eicResponse.getBody()).get("userdetails");
			if(userDetailsObj!=null){
				userDetailsList = (List<Map<String,Object>>)userDetailsObj;
			} else{
				userDetailsList = (List<Map<String,Object>>)EicClientUtils.jsonToMap(eicResponse.getBody()).get("userlist");
			}
		} else{
			userDetailsList = new ArrayList<>();
		}
		return userDetailsList;
	}
	private EicResponse getUsers(Map<String,Object> body, Boolean test) throws AuthenticationException, IOException, Exception{
		List<Map<String,Object>> userDetailsList;
		Map<String,Object> apiConfig = getApiConfigMap("Users", "Get_User_Details");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = EicClientUtils.convertMapToJsonString(body);
		Map<String, String> headers = (Map<String, String>)apiConfig.get("HEADER");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicRequest eicRequest = new EicRequest(apiUrl, method, headers, requestBody);
		EicResponse eicResponse = EicClientUtils.sendRequest(eicRequest);
		if(eicResponse.getResponseCode()==200){
			return eicResponse;
		} else{
			return eicResponse;
		}
	}
	
	public String createUser(Map<String,Object> userParams, Map<String,String> securityQuestions, Map<String,Object> otherSettings)
			throws AuthenticationException, IOException, Exception {
		String apiUrl = EIC_BASE_URL + "/ECM/api/v5/createUser";
		String method = "POST";
		String requestBody = "";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Content-Type", "application/json");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicResponse eicResponse = EicClientUtils.sendRequest(apiUrl, method, headers, requestBody);
		return eicResponse.getBody();
	}
	public String createUser( Map<String,Object> userParams, Map<String,String> securityQuestions) throws AuthenticationException, IOException, Exception {
		return createUser(userParams, securityQuestions, null);
	}
	public String createUser(Map<String,Object> userParams) throws AuthenticationException, IOException, Exception {
		return createUser(userParams, null);
	}
	
	public List<Map<String,Object>> getDatasetValues(String datasetName) throws AuthenticationException, IOException, Exception{
		List<Map<String,Object>> datasetValues;
		Map<String,String> body = new HashMap<String,String>();
		body.put("datasetname", datasetName);
		EicResponse eicResponse = getDatasetValues(body);
		Integer errorCode = eicResponse.getBodyAsJson().get("errorCode").getAsInt();
		if(errorCode==0){
			datasetValues = (List<Map<String,Object>>)EicClientUtils.jsonToMap(eicResponse.getBody()).get("dataset_values");
		} else {
			throw new Exception(eicResponse.getBodyAsJson().get("msg").toString());
		}
		return datasetValues;
	}
	private EicResponse getDatasetValues(Map<String,String> body) throws AuthenticationException, IOException, Exception{
		Map<String,Object> apiConfig = getApiConfigMap("Datasets", "Get_Dataset_Values");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL")+"?"+EicClientUtils.convertToQueryParameters(body);
		String method = (String) apiConfig.get("METHOD");
		Map<String, String> headers = (Map<String, String>)apiConfig.get("HEADER");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicRequest eicRequest = new EicRequest(apiUrl, method, headers, null);
		EicResponse eicResponse = EicClientUtils.sendRequest(eicRequest);
		if(eicResponse.getResponseCode()==200){
			return eicResponse;
		} else{
			throw new Exception(eicResponse.toString());
		}
	}
}
