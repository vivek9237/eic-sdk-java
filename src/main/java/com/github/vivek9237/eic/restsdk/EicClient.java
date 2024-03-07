package com.github.vivek9237.eic.restsdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;
import javax.naming.AuthenticationException;

import com.github.vivek9237.eic.restsdk.core.EicAccessToken;
import com.github.vivek9237.eic.restsdk.core.EicRequest;
import com.github.vivek9237.eic.restsdk.core.EicResponse;
import com.github.vivek9237.eic.restsdk.core.EicRefreshToken;
import com.github.vivek9237.eic.restsdk.utils.EicClientUtils;
import com.github.vivek9237.eic.restsdk.utils.EicJsonUtils;

public class EicClient {
	private String EIC_BASE_URL;
	private String EIC_USERNAME;
	private String EIC_PASSWORD;
	private EicAccessToken accessToken;
	private EicRefreshToken refreshToken;
	private Map<String, Object> eicRestApiConfig;

	/**
	 * Constructs an EicClient with the specified tenant and refresh token.
	 * 
	 * @param tenant       The tenant identifier.
	 * @param refreshToken The refresh token for authentication.
	 * @throws Exception If an error occurs during client initialization.
	 */
	public EicClient(String tenant, String refreshToken) throws Exception {
		this(new HashMap<String, String>() {
			{
				put("tenant", tenant);
				put("refreshToken", refreshToken);
			}
		});
	}

	/**
	 * Constructs an EicClient with the specified tenant, username, and password.
	 * 
	 * @param tenant   The tenant identifier.
	 * @param username The username for authentication.
	 * @param password The password for authentication.
	 * @throws Exception If an error occurs during client initialization.
	 */
	public EicClient(String tenant, String username, String password) throws Exception {
		this(new HashMap<String, String>() {
			{
				put("tenant", tenant);
				put("username", username);
				put("password", password);
			}
		});
	}

	public EicClient() throws Exception {
		this(new HashMap<String, String>() {
			{
				put("tenant", "");
				put("username", "");
				put("password", "");
			}
		});
	}

	/**
	 * Constructs an EicClient with the provided configurations.
	 * 
	 * @param configs A map containing the configurations for initializing the
	 *                client.
	 *                Supported keys: "protocol", "tenant", "domainName", "port",
	 *                "refreshToken", "username", "password",
	 *                "refreshTokenExpiryDate".
	 *                Defaults are used for missing keys.
	 * @throws Exception If an error occurs during client initialization.
	 */
	public EicClient(Map<String, String> configs) throws Exception {
		String protocol = configs.getOrDefault("protocol", "https");
		String tenant = configs.getOrDefault("tenant", null);
		String domainName = configs.getOrDefault("domainName", "saviyntcloud.com");
		String port = configs.getOrDefault("port", "443");
		String refreshToken = configs.getOrDefault("refreshToken", null);
		String username = configs.getOrDefault("username", null);
		String password = configs.getOrDefault("password", null);
		Integer refreshTokenExpiryDate = Integer.parseInt(configs.getOrDefault("refreshTokenExpiryDate", "300"));
		EIC_BASE_URL = protocol + "://" + tenant + "." + domainName + ":" + port;
		if (username != null && password != null) {
			EIC_USERNAME = username;
			EIC_PASSWORD = password;
		} else if (refreshToken != null) {
			this.refreshToken = new EicRefreshToken(refreshToken,
					EicClientUtils.addDaysToDate(new Date(), refreshTokenExpiryDate));
		} else {
			throw new Exception("[username and password] or [refreshToken] is null.");
		}
		eicRestApiConfig = EicClientUtils.parseYamlFile("/eic_rest_api.yml");
	}

	/**
	 * Retrieves the access token. If the current access token is valid, it returns
	 * the token;
	 * otherwise, it fetches a new access token.
	 * 
	 * @return The valid access token.
	 * @throws AuthenticationException If authentication fails.
	 * @throws IOException             If an I/O error occurs.
	 * @throws Exception               If an error occurs during the token retrieval
	 *                                 process.
	 */
	public String getAccessToken() throws AuthenticationException, IOException, Exception {
		if (this.accessToken != null && this.accessToken.isValid()) {
			return this.accessToken.getToken();
		} else {
			return getAccessToken(true);
		}
	}

	/**
	 * Retrieves the access token, optionally forcing a refresh.
	 * 
	 * @param force Indicates whether to force a refresh of the access token.
	 * @return The access token.
	 * @throws IOException             If an I/O error occurs.
	 * @throws AuthenticationException If authentication fails.
	 * @throws Exception               If an error occurs during the token retrieval
	 *                                 process.
	 */
	public String getAccessToken(Boolean force) throws IOException, AuthenticationException, Exception {
		String accessToken;
		if (this.refreshToken == null) {
			accessToken = getAccessTokenFromUsernameAndPassword();
		} else {
			accessToken = getAccessTokenFromRefreshToken();
		}
		return accessToken;
	}

	private Map<String, Object> getApiConfigMap(String category, String api) {
		@SuppressWarnings("unchecked")
		Map<String, Object> apiConfigMap = ((Map<String, Object>) ((Map<String, Object>) eicRestApiConfig.get(category))
				.get(api));
		return apiConfigMap;
	}

	private String getAccessTokenFromUsernameAndPassword() throws Exception {
		Map<String, Object> apiConfig = getApiConfigMap("Authentication", "Get_Authorization_Token");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = "{\"username\":\"" + EIC_USERNAME + "\",\"password\":\"" + EIC_PASSWORD + "\"}";
		@SuppressWarnings("unchecked")
		Map<String, String> headers = (Map<String, String>) apiConfig.get("HEADER");
		EicResponse eicResponse = EicClientUtils.sendRequest(apiUrl, method, headers, requestBody);
		if (eicResponse.getResponseCode() == 401) {
			throw new AuthenticationException("Bad username or password. Response:" + eicResponse.toString());
		} else if (eicResponse.getResponseCode() == 400) {
			throw new Exception("Bad request. Response:" + eicResponse.toString());
		} else if (eicResponse.getResponseCode() != 200) {
			throw new Exception("Unknown exception. Response:" + eicResponse.toString());
		} else {
			String access_token = eicResponse.getBodyAsJson().get("access_token").getAsString();
			Integer expires_in = Integer.parseInt(eicResponse.getBodyAsJson().get("expires_in").getAsString());
			Date exipryDate = EicClientUtils.addSecondsToDate(new Date(), expires_in);
			String refresh_token = eicResponse.getBodyAsJson().get("refresh_token").getAsString();
			this.accessToken = new EicAccessToken(access_token, exipryDate);
			this.refreshToken = new EicRefreshToken(refresh_token);
		}
		return this.accessToken.getToken();
	}

	private String getAccessTokenFromRefreshToken() throws Exception {
		Map<String, Object> apiConfig = getApiConfigMap("Authentication", "Refresh_Authorization_Token");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = "grant_type=" + URLEncoder.encode("refresh_token", "UTF-8") + "&refresh_token="
				+ URLEncoder.encode(this.refreshToken.getToken(), "UTF-8");
		@SuppressWarnings("unchecked")
		Map<String, String> headers = (Map<String, String>) apiConfig.get("HEADER");
		EicResponse eicResponse = EicClientUtils.sendRequest(apiUrl, method, headers, requestBody);
		if (eicResponse.getResponseCode() == 401) {
			throw new AuthenticationException("Bad username or password. Response:" + eicResponse.toString());
		} else if (eicResponse.getResponseCode() == 400) {
			throw new Exception("Bad request. Response:" + eicResponse.toString());
		} else if (eicResponse.getResponseCode() != 200) {
			throw new Exception("Unknown exception. Response:" + eicResponse.toString());
		} else {
			String access_token = eicResponse.getBodyAsJson().get("access_token").getAsString();
			Integer expires_in = Integer.parseInt(eicResponse.getBodyAsJson().get("expires_in").getAsString());
			Date exipryDate = EicClientUtils.addSecondsToDate(new Date(), expires_in);
			String refresh_token = eicResponse.getBodyAsJson().get("refresh_token").getAsString();
			this.accessToken = new EicAccessToken(access_token, exipryDate);
			this.refreshToken = new EicRefreshToken(refresh_token);
		}
		return this.accessToken.getToken();
	}

	/* Get Users */
	/**
	 * Retrieves user details by username.
	 * 
	 * @param username The username to search for.
	 * @return A map containing user details if found, or null if not found.
	 * @throws AuthenticationException If authentication fails.
	 * @throws Exception               If an error occurs during the retrieval
	 *                                 process.
	 */
	public Map<String, Object> getUserByUsername(String username)
			throws AuthenticationException, Exception {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("username", username);
		List<Map<String, Object>> listOfUsers = getAllUsers(body);
		if (listOfUsers != null && listOfUsers.size() == 1) {
			return listOfUsers.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Retrieves all users based on provided criteria.
	 * 
	 * @param body A map containing criteria for user retrieval.
	 *             Expected keys: "max" (maximum number of users to retrieve),
	 *             "offset" (offset for pagination).
	 * @return A list of maps containing user details.
	 * @throws AuthenticationException If authentication fails.
	 * @throws IOException             If an I/O error occurs.
	 * @throws Exception               If an error occurs during the retrieval
	 *                                 process.
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAllUsers(Map<String, Object> body)
			throws AuthenticationException, IOException, Exception {
		List<Map<String, Object>> userDetailsList = new ArrayList<>();
		Integer pageSize = 3;
		if (body.get("max") == null) {
			body.put("max", pageSize);
		}
		if (body.get("offset") == null) {
			body.put("offset", 0);
		}
		EicResponse eicResponse = getUsers(body, true);
		if (eicResponse != null && eicResponse.getResponseCode() == 200) {
			Object userDetailsObj = EicJsonUtils.jsonToMap(eicResponse.getBody()).get("userdetails");
			if (userDetailsObj != null && userDetailsObj instanceof Map) {
				userDetailsList.add((Map<String, Object>) userDetailsObj);
			} else if (userDetailsObj != null && userDetailsObj instanceof List) {
				Integer totalCount = Integer.parseInt(eicResponse.getBodyAsJson().get("totalcount").getAsString());
				Integer displaycount = Integer.parseInt(eicResponse.getBodyAsJson().get("displaycount").getAsString());
				userDetailsList = (List<Map<String, Object>>) EicJsonUtils.jsonToMap(eicResponse.getBody())
						.get("userdetails");
				if (totalCount > displaycount && userDetailsList != null && userDetailsList.size() > 0) {
					while (true) {
						body.put("offset", ((Integer) body.get("offset")) + ((Integer) body.get("max")));
						EicResponse eicResponseSubsequent = getUsers(body, true);
						displaycount = Integer
								.parseInt(eicResponseSubsequent.getBodyAsJson().get("displaycount").getAsString());
						if (displaycount != 0) {
							userDetailsList.addAll((List<Map<String, Object>>) EicJsonUtils
									.jsonToMap(eicResponse.getBody()).get("userdetails"));
						} else {
							break;
						}
					}
				}
			} else {
				Integer totalCount = Integer.parseInt(eicResponse.getBodyAsJson().get("totalcount").getAsString());
				Integer displaycount = Integer.parseInt(eicResponse.getBodyAsJson().get("displaycount").getAsString());
				userDetailsList = (List<Map<String, Object>>) EicJsonUtils.jsonToMap(eicResponse.getBody())
						.get("userlist");
				if (totalCount > displaycount && userDetailsList != null && userDetailsList.size() > 0) {
					while (true) {
						body.put("offset", ((Integer) body.get("offset")) + ((Integer) body.get("max")));
						EicResponse eicResponseSubsequent = getUsers(body, true);
						displaycount = Integer
								.parseInt(eicResponseSubsequent.getBodyAsJson().get("displaycount").getAsString());
						if (displaycount != 0) {
							userDetailsList.addAll((List<Map<String, Object>>) EicJsonUtils
									.jsonToMap(eicResponse.getBody()).get("userlist"));
						} else {
							break;
						}
					}
				}
			}
		} else {
			userDetailsList = null;
		}
		return userDetailsList;
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getUsers(Map<String, Object> body) throws Exception {
		List<Map<String, Object>> userDetailsList;
		Map<String, Object> apiConfig = getApiConfigMap("Users", "Get_User_Details");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = EicJsonUtils.convertMapToJsonString(body);
		Map<String, String> headers = (Map<String, String>) apiConfig.get("HEADER");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicRequest eicRequest = new EicRequest(apiUrl, method, headers, requestBody);
		EicResponse eicResponse = EicClientUtils.sendRequest(eicRequest);
		if (eicResponse.getResponseCode() >= 200 || eicResponse.getResponseCode() <= 299) {
			Object userDetailsObj = EicJsonUtils.jsonToMap(eicResponse.getBody()).get("userdetails");
			if (userDetailsObj != null) {
				userDetailsList = (List<Map<String, Object>>) userDetailsObj;
			} else {
				userDetailsList = (List<Map<String, Object>>) EicJsonUtils.jsonToMap(eicResponse.getBody())
						.get("userlist");
			}
		} else {
			userDetailsList = new ArrayList<>();
		}
		return userDetailsList;
	}

	private EicResponse getUsers(Map<String, Object> body, Boolean test)
			throws AuthenticationException, IOException, Exception {
		Map<String, Object> apiConfig = getApiConfigMap("Users", "Get_User_Details");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = EicJsonUtils.convertMapToJsonString(body);
		@SuppressWarnings("unchecked")
		Map<String, String> headers = (Map<String, String>) apiConfig.get("HEADER");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicRequest eicRequest = new EicRequest(apiUrl, method, headers, requestBody);
		EicResponse eicResponse = EicClientUtils.sendRequest(eicRequest);
		if (eicResponse.getResponseCode() >= 200 || eicResponse.getResponseCode() <= 299) {
			return eicResponse;
		} else {
			return eicResponse;
		}
	}

	/* Create Users */
	/**
	 * Creates a new user with the provided parameters.
	 * 
	 * @param userParams A map containing parameters for user creation.
	 * @return The response body of the user creation request.
	 * @throws AuthenticationException If authentication fails.
	 * @throws IOException             If an I/O error occurs.
	 * @throws Exception               If an error occurs during the creation
	 *                                 process.
	 */
	public String createUser(Map<String, Object> userParams) throws AuthenticationException, IOException, Exception {
		return createUser(userParams, null);
	}

	/**
	 * Creates a new user with the provided parameters and security questions.
	 * 
	 * @param userParams        A map containing parameters for user creation.
	 * @param securityQuestions A map containing security questions for the user.
	 * @return The response body of the user creation request.
	 * @throws AuthenticationException If authentication fails.
	 * @throws IOException             If an I/O error occurs.
	 * @throws Exception               If an error occurs during the creation
	 *                                 process.
	 */
	public String createUser(Map<String, Object> userParams, Map<String, String> securityQuestions)
			throws AuthenticationException, IOException, Exception {
		return createUser(userParams, securityQuestions, null);
	}

	/**
	 * Creates a new user with the provided parameters, security questions, and
	 * other settings.
	 * 
	 * @param userParams        A map containing parameters for user creation.
	 * @param securityQuestions A map containing security questions for the user.
	 * @param otherSettings     A map containing other settings for user creation.
	 * @return The response body of the user creation request.
	 * @throws AuthenticationException If authentication fails.
	 * @throws IOException             If an I/O error occurs.
	 * @throws Exception               If an error occurs during the creation
	 *                                 process.
	 */
	public String createUser(Map<String, Object> userParams, Map<String, String> securityQuestions,
			Map<String, Object> otherSettings)
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

	/* Get DataValues */
	/**
	 * Retrieves values associated with the specified dataset.
	 * 
	 * @param datasetName The name of the dataset.
	 * @return A list of maps containing values from the dataset.
	 * @throws AuthenticationException If authentication fails.
	 * @throws IOException             If an I/O error occurs.
	 * @throws Exception               If an error occurs during the retrieval
	 *                                 process.
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getDatasetValues(String datasetName)
			throws AuthenticationException, IOException, Exception {
		List<Map<String, Object>> datasetValues;
		Map<String, String> body = new HashMap<String, String>();
		body.put("datasetname", datasetName);
		EicResponse eicResponse = getDatasetValues(body);
		Integer errorCode = eicResponse.getBodyAsJson().get("errorCode").getAsInt();
		if (errorCode == 0) {
			datasetValues = (List<Map<String, Object>>) EicJsonUtils.jsonToMap(eicResponse.getBody())
					.get("dataset_values");
		} else {
			throw new Exception(eicResponse.getBodyAsJson().get("msg").toString());
		}
		return datasetValues;
	}

	private EicResponse getDatasetValues(Map<String, String> body)
			throws AuthenticationException, IOException, Exception {
		Map<String, Object> apiConfig = getApiConfigMap("Datasets", "Get_Dataset_Values");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL") + "?" + EicClientUtils.convertToQueryParameters(body);
		String method = (String) apiConfig.get("METHOD");
		@SuppressWarnings("unchecked")
		Map<String, String> headers = (Map<String, String>) apiConfig.get("HEADER");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicRequest eicRequest = new EicRequest(apiUrl, method, headers, null);
		EicResponse eicResponse = EicClientUtils.sendRequest(eicRequest);
		if (eicResponse.getResponseCode() >= 200 || eicResponse.getResponseCode() <= 299) {
			return eicResponse;
		} else {
			throw new Exception(eicResponse.toString());
		}
	}

	/* Get Accounts */
	/**
	 * Retrieves all accounts associated with the specified username.
	 * 
	 * @param username The username of the user to retrieve accounts for.
	 * @return A list of maps containing account details.
	 * @throws AuthenticationException If authentication fails.
	 * @throws IOException             If an I/O error occurs.
	 * @throws Exception               If an error occurs during the retrieval
	 *                                 process.
	 */
	public List<Map<String, Object>> getAccounts(String username)
			throws AuthenticationException, IOException, Exception {
		return getAccounts(new HashMap<String, Object>() {
			{
				put("username", username);
			};
		});
	}

	/**
	 * Retrieves accounts associated with the specified username, and status.
	 * 
	 * @param username The username of the user to retrieve accounts for.
	 * @param active   Specifies whether to retrieve active or inactive accounts.
	 * @return A list of maps containing account details.
	 * @throws AuthenticationException If authentication fails.
	 * @throws IOException             If an I/O error occurs.
	 * @throws Exception               If an error occurs during the retrieval
	 *                                 process.
	 */
	public List<Map<String, Object>> getAccounts(String username, Boolean active)
			throws AuthenticationException, IOException, Exception {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("username", username);
		body.put("advsearchcriteria", new HashMap<String, String>() {
			{
				put("status", active ? "ACTIVE" : "INACTIVE");
			};
		});
		return getAccounts(body);
	}

	/**
	 * Retrieves all accounts associated with the specified username and endpoint
	 * name.
	 * 
	 * @param username     The username of the user to retrieve accounts for.
	 * @param endpointName The endpoint name for filtering accounts.
	 * @return A list of maps containing account details.
	 * @throws AuthenticationException If authentication fails.
	 * @throws IOException             If an I/O error occurs.
	 * @throws Exception               If an error occurs during the retrieval
	 *                                 process.
	 */
	public List<Map<String, Object>> getAccounts(String username, String endpointName)
			throws AuthenticationException, IOException, Exception {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("username", username);
		body.put("endpoint", endpointName);
		return getAccounts(body);
	}

	/**
	 * Retrieves accounts associated with the specified username, endpoint name, and
	 * active status.
	 * 
	 * @param username     The username of the user to retrieve accounts for.
	 * @param endpointName The endpoint name for filtering accounts.
	 * @param active       Specifies whether to retrieve active accounts.
	 * @return A list of maps containing account details.
	 * @throws AuthenticationException If authentication fails.
	 * @throws IOException             If an I/O error occurs.
	 * @throws Exception               If an error occurs during the retrieval
	 *                                 process.
	 */
	public List<Map<String, Object>> getAccounts(String username, String endpointName, Boolean active)
			throws AuthenticationException, IOException, Exception {
		Map<String, Object> body = new HashMap<String, Object>();
		body.put("username", username);
		body.put("endpoint", endpointName);
		body.put("advsearchcriteria", new HashMap<String, String>() {
			{
				put("status", active ? "ACTIVE" : "INACTIVE");
			};
		});
		return getAccounts(body);
	}

	/**
	 * Retrieves accounts based on the provided criteria.
	 * 
	 * @param body A map containing criteria for account retrieval.
	 *             Supported keys: "username", "endpoint", "advsearchcriteria".
	 * @return A list of maps containing account details.
	 * @throws AuthenticationException If authentication fails.
	 * @throws IOException             If an I/O error occurs.
	 * @throws Exception               If an error occurs during the retrieval
	 *                                 process.
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getAccounts(Map<String, Object> body)
			throws AuthenticationException, IOException, Exception {
		List<Map<String, Object>> accounts = null;
		EicResponse eicResponse = getAccounts(body, true);
		String errorCode = eicResponse.getBodyAsJson().get("errorCode").getAsString();
		System.out.println(errorCode);
		if (errorCode.equals("0")) {
			accounts = (List<Map<String, Object>>) EicJsonUtils.jsonToMap(eicResponse.getBody())
					.get("Accountdetails");
		} else {
			throw new Exception(eicResponse.getBodyAsJson().get("msg").getAsString());
		}
		return accounts;
	}

	private EicResponse getAccounts(Map<String, Object> body, Boolean test)
			throws AuthenticationException, IOException, Exception {
		Map<String, Object> apiConfig = getApiConfigMap("Accounts", "Get_Account_Details");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = EicJsonUtils.convertMapToJsonString(body);
		@SuppressWarnings("unchecked")
		Map<String, String> headers = (Map<String, String>) apiConfig.get("HEADER");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicRequest eicRequest = new EicRequest(apiUrl, method, headers, requestBody);
		EicResponse eicResponse = EicClientUtils.sendRequest(eicRequest);
		if (eicResponse.getResponseCode() >= 200 || eicResponse.getResponseCode() <= 299) {
			System.out.println(eicRequest.toString());
			return eicResponse;
		} else {
			throw new Exception(eicResponse.toString());
		}
	}
}