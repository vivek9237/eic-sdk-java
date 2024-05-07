package io.github.vivek9237.eic.restsdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.net.URLEncoder;
import javax.naming.AuthenticationException;

import io.github.vivek9237.eic.restsdk.accounts.AccountStatus;
import io.github.vivek9237.eic.restsdk.accounts.AssignEntitlementToAccountRequest;
import io.github.vivek9237.eic.restsdk.accounts.AssignEntitlementToAccountResponse;
import io.github.vivek9237.eic.restsdk.accounts.AssignAccountToUserRequest;
import io.github.vivek9237.eic.restsdk.accounts.AssignAccountToUserResponse;
import io.github.vivek9237.eic.restsdk.accounts.CreateAccountRequest;
import io.github.vivek9237.eic.restsdk.accounts.CreateAccountResponse;
import io.github.vivek9237.eic.restsdk.accounts.GetAccountRequest;
import io.github.vivek9237.eic.restsdk.accounts.GetAccountResponse;
import io.github.vivek9237.eic.restsdk.accounts.UpdateAccountRequest;
import io.github.vivek9237.eic.restsdk.accounts.UpdateAccountResponse;
import io.github.vivek9237.eic.restsdk.accounts.GetAccountResponse.Accountdetail;
import io.github.vivek9237.eic.restsdk.accounts.RemoveEntitlementFromAccountRequest;
import io.github.vivek9237.eic.restsdk.accounts.RemoveEntitlementFromAccountResponse;
import io.github.vivek9237.eic.restsdk.core.EicAccessToken;
import io.github.vivek9237.eic.restsdk.core.EicRequest;
import io.github.vivek9237.eic.restsdk.core.EicResponse;
import io.github.vivek9237.eic.restsdk.core.EicRefreshToken;
import io.github.vivek9237.eic.restsdk.utils.EicClientUtils;
import io.github.vivek9237.eic.security.EicEncryptionUtils;
import io.github.vivek9237.eic.utils.EicCommonUtils;
import io.github.vivek9237.eic.utils.EicJsonUtils;
import com.google.gson.Gson;

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
				String eicPropertiesFile = (String) EicJsonUtils.parseJsonFileToMap("/properties.json")
						.get("eic_properties_file_name");
				Properties prop = EicCommonUtils.readPropertyFile(eicPropertiesFile);
				put("tenant", prop.getProperty("io.github.vivek9237.eic.tenant"));
				put("username", prop.getProperty("io.github.vivek9237.eic.username"));
				String password = EicEncryptionUtils.decrypt(prop.getProperty("io.github.vivek9237.eic.password"),
						prop.getProperty("io.github.vivek9237.eic.secret"));
				put("password", password);
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
		if (EicClientUtils.isStringEmpty(tenant)) {
			throw new Exception("Tenant value cannot be null!");
		}
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
		eicRestApiConfig = EicJsonUtils.parseJsonFileToMap("/internal/eic_rest_api.json");
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
			// Object userDetailsObj =
			// EicJsonUtils.jsonToMap(eicResponse.getBody()).get("userdetails");
			Object userDetailsObj = EicJsonUtils.jsonObjectStringToMap(eicResponse.getBody()).get("userdetails");
			if (userDetailsObj != null && userDetailsObj instanceof Map) {
				userDetailsList.add((Map<String, Object>) userDetailsObj);
			} else if (userDetailsObj != null && userDetailsObj instanceof List) {
				Integer totalCount = Integer.parseInt(eicResponse.getBodyAsJson().get("totalcount").getAsString());
				Integer displaycount = Integer.parseInt(eicResponse.getBodyAsJson().get("displaycount").getAsString());
				userDetailsList = (List<Map<String, Object>>) EicJsonUtils.jsonObjectStringToMap(eicResponse.getBody())
						.get("userdetails");
				if (totalCount > displaycount && userDetailsList != null && userDetailsList.size() > 0) {
					while (true) {
						body.put("offset", ((Integer) body.get("offset")) + ((Integer) body.get("max")));
						EicResponse eicResponseSubsequent = getUsers(body, true);
						displaycount = Integer
								.parseInt(eicResponseSubsequent.getBodyAsJson().get("displaycount").getAsString());
						if (displaycount != 0) {
							userDetailsList.addAll((List<Map<String, Object>>) EicJsonUtils
									.jsonObjectStringToMap(eicResponse.getBody()).get("userdetails"));
						} else {
							break;
						}
					}
				}
			} else {
				Integer totalCount = Integer.parseInt(eicResponse.getBodyAsJson().get("totalcount").getAsString());
				Integer displaycount = Integer.parseInt(eicResponse.getBodyAsJson().get("displaycount").getAsString());
				userDetailsList = (List<Map<String, Object>>) EicJsonUtils.jsonObjectStringToMap(eicResponse.getBody())
						.get("userlist");
				if (totalCount > displaycount && userDetailsList != null && userDetailsList.size() > 0) {
					while (true) {
						body.put("offset", ((Integer) body.get("offset")) + ((Integer) body.get("max")));
						EicResponse eicResponseSubsequent = getUsers(body, true);
						displaycount = Integer
								.parseInt(eicResponseSubsequent.getBodyAsJson().get("displaycount").getAsString());
						if (displaycount != 0) {
							userDetailsList.addAll((List<Map<String, Object>>) EicJsonUtils
									.jsonObjectStringToMap(eicResponse.getBody()).get("userlist"));
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

	/**
	 * This method is deprecated and should not be used anymore.
	 */
	@Deprecated
	@SuppressWarnings({ "unchecked", "unused" })
	private List<Map<String, Object>> getUsers(Map<String, Object> body) throws Exception {
		List<Map<String, Object>> userDetailsList;
		Map<String, Object> apiConfig = getApiConfigMap("Users", "Get_User_Details");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = EicJsonUtils.mapToJsonObjectString(body);
		Map<String, String> headers = (Map<String, String>) apiConfig.get("HEADER");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicRequest eicRequest = new EicRequest(apiUrl, method, headers, requestBody);
		EicResponse eicResponse = EicClientUtils.sendRequest(eicRequest);
		if (eicResponse.getResponseCode() >= 200 && eicResponse.getResponseCode() <= 299) {
			Object userDetailsObj = EicJsonUtils.jsonObjectStringToMap(eicResponse.getBody()).get("userdetails");
			if (userDetailsObj != null) {
				userDetailsList = (List<Map<String, Object>>) userDetailsObj;
			} else {
				userDetailsList = (List<Map<String, Object>>) EicJsonUtils.jsonObjectStringToMap(eicResponse.getBody())
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
		String requestBody = EicJsonUtils.mapToJsonObjectString(body);
		@SuppressWarnings("unchecked")
		Map<String, String> headers = (Map<String, String>) apiConfig.get("HEADER");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicRequest eicRequest = new EicRequest(apiUrl, method, headers, requestBody);
		EicResponse eicResponse = EicClientUtils.sendRequest(eicRequest);
		if (eicResponse.getResponseCode() >= 200 && eicResponse.getResponseCode() <= 299) {
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
			datasetValues = (List<Map<String, Object>>) EicJsonUtils.jsonObjectStringToMap(eicResponse.getBody())
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
		if (eicResponse.getResponseCode() >= 200 && eicResponse.getResponseCode() <= 299) {
			return eicResponse;
		} else {
			throw new Exception(eicResponse.toString());
		}
	}

	/* Get Accounts */
	/**
	 * Retrieves a list of account details associated with the given username.
	 * 
	 * @param username the username for which to retrieve account details
	 * @return a list of {@code Accountdetail} objects representing the account
	 *         details
	 * @throws AuthenticationException if authentication fails
	 * @throws IOException             if an I/O error occurs while making the
	 *                                 request
	 * @throws Exception               if an unexpected error occurs
	 */
	public List<Accountdetail> getAccounts(String username)
			throws AuthenticationException, IOException, Exception {
		GetAccountRequest getAccountRequest = new GetAccountRequest();
		getAccountRequest.setUsername(username);
		return getAccounts(getAccountRequest);
	}

	/**
	 * Retrieves a list of account details associated with the given username.
	 * 
	 * @param username the username for which to retrieve account details
	 * @return a list of {@code Accountdetail} objects representing the account
	 *         details
	 * @throws AuthenticationException if authentication fails, indicating that the
	 *                                 provided username is not authenticated
	 * @throws IOException             if an I/O error occurs while making the
	 *                                 request, such as network failure
	 * @throws Exception               if an unexpected error occurs during the
	 *                                 account retrieval process
	 */
	public List<Accountdetail> getAccounts(String username, Boolean active)
			throws AuthenticationException, IOException, Exception {
		GetAccountRequest getAccountRequest = new GetAccountRequest();
		getAccountRequest.setUsername(username);
		GetAccountRequest.Advsearchcriteria advsearchcriteria = getAccountRequest.new Advsearchcriteria();
		advsearchcriteria
				.setStatus(active ? AccountStatus.ACTIVESTATUS.getValue() : AccountStatus.INACTIVESTATUS.getValue());
		getAccountRequest.setAdvsearchcriteria(advsearchcriteria);
		return getAccounts(getAccountRequest);
	}

	/**
	 * Retrieves a list of account details associated with the given username and
	 * endpoint.
	 * 
	 * @param username     the username for which to retrieve account details
	 * @param endpointName the name of the endpoint from which to retrieve account
	 *                     details
	 * @return a list of {@code Accountdetail} objects representing the account
	 *         details
	 * @throws AuthenticationException if authentication fails, indicating that the
	 *                                 provided username is not authenticated
	 * @throws IOException             if an I/O error occurs while making the
	 *                                 request, such as network failure
	 * @throws Exception               if an unexpected error occurs during the
	 *                                 account retrieval process
	 */

	public List<Accountdetail> getAccounts(String username, String endpointName)
			throws AuthenticationException, IOException, Exception {
		GetAccountRequest getAccountRequest = new GetAccountRequest();
		getAccountRequest.setUsername(username);
		getAccountRequest.setEndpoint(endpointName);
		return getAccounts(getAccountRequest);
	}

	/**
	 * Retrieves a list of account details associated with the given username and
	 * endpoint,
	 * filtered by the specified active status.
	 * 
	 * @param username     the username for which to retrieve account details
	 * @param endpointName the name of the endpoint from which to retrieve account
	 *                     details
	 * @param active       a {@code Boolean} value indicating whether to retrieve
	 *                     active accounts (true) or inactive accounts (false)
	 * @return a list of {@code Accountdetail} objects representing the account
	 *         details
	 * @throws AuthenticationException if authentication fails, indicating that the
	 *                                 provided username is not authenticated
	 * @throws IOException             if an I/O error occurs while making the
	 *                                 request, such as network failure
	 * @throws Exception               if an unexpected error occurs during the
	 *                                 account retrieval process
	 */

	public List<Accountdetail> getAccounts(String username, String endpointName, Boolean active)
			throws AuthenticationException, IOException, Exception {
		GetAccountRequest getAccountRequest = new GetAccountRequest();
		getAccountRequest.setUsername(username);
		getAccountRequest.setEndpoint(endpointName);
		GetAccountRequest.Advsearchcriteria advsearchcriteria = getAccountRequest.new Advsearchcriteria();
		advsearchcriteria
				.setStatus(active ? AccountStatus.ACTIVESTATUS.getValue() : AccountStatus.INACTIVESTATUS.getValue());
		getAccountRequest.setAdvsearchcriteria(advsearchcriteria);
		return getAccounts(getAccountRequest);
	}

	/**
	 * Retrieves a list of account details based on the provided
	 * {@code GetAccountRequest}.
	 * 
	 * @param getAccountRequest the request object containing parameters for account
	 *                          retrieval
	 * @return a list of {@code Accountdetail} objects representing the account
	 *         details
	 * @throws AuthenticationException if authentication fails, indicating that the
	 *                                 provided username is not authenticated
	 * @throws IOException             if an I/O error occurs while making the
	 *                                 request, such as network failure
	 * @throws Exception               if an unexpected error occurs during the
	 *                                 account retrieval process
	 */

	public List<Accountdetail> getAccounts(GetAccountRequest getAccountRequest)
			throws AuthenticationException, IOException, Exception {
		EicResponse eicResponse = getAccounts(getAccountRequest, true);
		GetAccountResponse getAccountResponse = new Gson().fromJson(eicResponse.getBody(), GetAccountResponse.class);
		String errorCode = getAccountResponse.getErrorCode();
		List<Accountdetail> accounts = null;
		if (errorCode.equals("0")) {
			accounts = getAccountResponse.getAccountdetails();
		} else {
			throw new Exception(eicResponse.getBodyAsJson().get("msg").getAsString());
		}
		return accounts;
	}

	private EicResponse getAccounts(GetAccountRequest getAccountRequest, Boolean test)
			throws AuthenticationException, IOException, Exception {
		Map<String, Object> apiConfig = getApiConfigMap("Accounts", "Get_Account_Details");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = new Gson().toJson(getAccountRequest);
		@SuppressWarnings("unchecked")
		Map<String, String> headers = (Map<String, String>) apiConfig.get("HEADER");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicRequest eicRequest = new EicRequest(apiUrl, method, headers, requestBody);
		EicResponse eicResponse = EicClientUtils.sendRequest(eicRequest);
		if (eicResponse.getResponseCode() >= 200 && eicResponse.getResponseCode() <= 299) {
			System.out.println(eicRequest.toString());
			return eicResponse;
		} else {
			throw new Exception(eicResponse.toString());
		}
	}

	/**
	 * Creates an account based on the provided {@code CreateAccountRequest}.
	 * 
	 * @param createAccountRequest the request object containing parameters for
	 *                             account creation
	 * @return {@code true} if the account creation is successful, {@code false}
	 *         otherwise
	 * @throws AuthenticationException if authentication fails, indicating that the
	 *                                 user is not authenticated
	 * @throws IOException             if an I/O error occurs while making the
	 *                                 request, such as network failure
	 * @throws Exception               if an unexpected error occurs during the
	 *                                 account creation process
	 */

	public boolean createAccount(CreateAccountRequest createAccountRequest)
			throws AuthenticationException, IOException, Exception {
		EicResponse eicResponse = createAccount(createAccountRequest, true);
		CreateAccountResponse createAccountResponse = new Gson().fromJson(eicResponse.getBody(),
				CreateAccountResponse.class);
		String errorCode = createAccountResponse.getErrorCode();
		if (errorCode.equals("0")) {
			return true;
		} else {
			throw new Exception(createAccountResponse.getMessage());
		}
	}

	private EicResponse createAccount(CreateAccountRequest createAccountRequest, Boolean test)
			throws AuthenticationException, IOException, Exception {
		Map<String, Object> apiConfig = getApiConfigMap("Accounts", "Create_Accounts");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = new Gson().toJson(createAccountRequest);
		@SuppressWarnings("unchecked")
		Map<String, String> headers = (Map<String, String>) apiConfig.get("HEADER");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicRequest eicRequest = new EicRequest(apiUrl, method, headers, requestBody);
		EicResponse eicResponse = EicClientUtils.sendRequest(eicRequest);
		if (eicResponse.getResponseCode() >= 200 && eicResponse.getResponseCode() <= 299) {
			return eicResponse;
		} else {
			throw new Exception(eicResponse.toString());
		}
	}

	/**
	 * Updates an existing account based on the provided
	 * {@code UpdateAccountRequest}.
	 * 
	 * @param updateAccountRequest the request object containing parameters for
	 *                             updating the account
	 * @return {@code true} if the account update is successful, {@code false}
	 *         otherwise
	 * @throws AuthenticationException if authentication fails, indicating that the
	 *                                 user is not authenticated
	 * @throws IOException             if an I/O error occurs while making the
	 *                                 request, such as network failure
	 * @throws Exception               if an unexpected error occurs during the
	 *                                 account update process
	 */

	public boolean updateAccount(UpdateAccountRequest updateAccountRequest)
			throws AuthenticationException, IOException, Exception {
		EicResponse eicResponse = updateAccount(updateAccountRequest, true);
		UpdateAccountResponse updateAccountResponse = new Gson().fromJson(eicResponse.getBody(),
				UpdateAccountResponse.class);
		String errorCode = updateAccountResponse.getErrorCode();
		if (errorCode.equals("0")) {
			return true;
		} else {
			throw new Exception(updateAccountResponse.getMessage());
		}
	}

	private EicResponse updateAccount(UpdateAccountRequest updateAccountRequest, Boolean test)
			throws AuthenticationException, IOException, Exception {
		Map<String, Object> apiConfig = getApiConfigMap("Accounts", "Update_Accounts");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = new Gson().toJson(updateAccountRequest);
		@SuppressWarnings("unchecked")
		Map<String, String> headers = (Map<String, String>) apiConfig.get("HEADER");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicRequest eicRequest = new EicRequest(apiUrl, method, headers, requestBody);
		EicResponse eicResponse = EicClientUtils.sendRequest(eicRequest);
		if (eicResponse.getResponseCode() >= 200 && eicResponse.getResponseCode() <= 299) {
			return eicResponse;
		} else {
			throw new Exception(eicResponse.toString());
		}
	}

	/**
	 * Assigns an account to users based on the provided
	 * {@code AssignAccountToUserRequest}.
	 * 
	 * @param assignAccountToUserRequest the request object containing parameters
	 *                                   for assigning the account to users
	 * @return {@code true} if the account assignment is successful, {@code false}
	 *         otherwise
	 * @throws AuthenticationException if authentication fails, indicating that the
	 *                                 user is not authenticated
	 * @throws IOException             if an I/O error occurs while making the
	 *                                 request, such as network failure
	 * @throws Exception               if an unexpected error occurs during the
	 *                                 account assignment process
	 */

	public boolean assignAccountToUsers(AssignAccountToUserRequest assignAccountToUserRequest)
			throws AuthenticationException, IOException, Exception {
		EicResponse eicResponse = assignAccountToUsers(assignAccountToUserRequest, true);
		AssignAccountToUserResponse assignAccountToUserResponse = new Gson().fromJson(eicResponse.getBody(),
				AssignAccountToUserResponse.class);
		String errorCode = assignAccountToUserResponse.getErrorCode();
		if (errorCode.equals("0")) {
			return true;
		} else {
			throw new Exception(assignAccountToUserResponse.getMessage());
		}
	}

	private EicResponse assignAccountToUsers(AssignAccountToUserRequest assignAccountToUserRequest, Boolean test)
			throws AuthenticationException, IOException, Exception {
		Map<String, Object> apiConfig = getApiConfigMap("Accounts", "Assign_Account_To_User");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = EicClientUtils.xWwwFormUrlencoder(assignAccountToUserRequest);
		@SuppressWarnings("unchecked")
		Map<String, String> headers = (Map<String, String>) apiConfig.get("HEADER");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicRequest eicRequest = new EicRequest(apiUrl, method, headers, requestBody);
		EicResponse eicResponse = EicClientUtils.sendRequest(eicRequest);
		if (eicResponse.getResponseCode() >= 200 && eicResponse.getResponseCode() <= 299) {
			return eicResponse;
		} else {
			throw new Exception(eicResponse.toString());
		}
	}

	/**
	 * Assigns an entitlement to an account based on the provided
	 * {@code AssignEntitlementToAccountRequest}.
	 * 
	 * @param assignEntitlementToAccountRequest the request object containing
	 *                                          parameters for assigning the
	 *                                          entitlement to the account
	 * @return {@code true} if the entitlement assignment is successful,
	 *         {@code false} otherwise
	 * @throws AuthenticationException if authentication fails, indicating that the
	 *                                 user is not authenticated
	 * @throws IOException             if an I/O error occurs while making the
	 *                                 request, such as network failure
	 * @throws Exception               if an unexpected error occurs during the
	 *                                 entitlement assignment process
	 */

	public boolean assignEntitlementToAccount(AssignEntitlementToAccountRequest assignEntitlementToAccountRequest)
			throws AuthenticationException, IOException, Exception {
		EicResponse eicResponse = assignEntitlementToAccount(assignEntitlementToAccountRequest, true);

		AssignEntitlementToAccountResponse assignEntitlementToAccountResponse = new Gson()
				.fromJson(eicResponse.getBody(), AssignEntitlementToAccountResponse.class);
		String errorCode = assignEntitlementToAccountResponse.getErrorCode();
		if (errorCode.equals("0")) {
			return true;
		} else {
			throw new Exception(assignEntitlementToAccountResponse.getMessage());
		}
	}

	private EicResponse assignEntitlementToAccount(AssignEntitlementToAccountRequest assignEntitlementToAccountRequest,
			Boolean test)
			throws AuthenticationException, IOException, Exception {
		Map<String, Object> apiConfig = getApiConfigMap("Accounts", "Assign_Entitlement_To_Account");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = EicClientUtils.xWwwFormUrlencoder(assignEntitlementToAccountRequest);
		@SuppressWarnings("unchecked")
		Map<String, String> headers = (Map<String, String>) apiConfig.get("HEADER");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicRequest eicRequest = new EicRequest(apiUrl, method, headers, requestBody);
		EicResponse eicResponse = EicClientUtils.sendRequest(eicRequest);
		if (eicResponse.getResponseCode() >= 200 && eicResponse.getResponseCode() <= 299) {
			return eicResponse;
		} else {
			throw new Exception(eicResponse.toString());
		}
	}

	/**
	 * Removes an entitlement from an account based on the provided
	 * {@code RemoveEntitlementFromAccountRequest}.
	 * 
	 * @param removeEntitlementFromAccountRequest the request object containing
	 *                                            parameters for removing the
	 *                                            entitlement from the account
	 * @return {@code true} if the entitlement removal is successful, {@code false}
	 *         otherwise
	 * @throws AuthenticationException if authentication fails, indicating that the
	 *                                 user is not authenticated
	 * @throws IOException             if an I/O error occurs while making the
	 *                                 request, such as network failure
	 * @throws Exception               if an unexpected error occurs during the
	 *                                 entitlement removal process
	 */

	public boolean removeEntitlementFromAccount(RemoveEntitlementFromAccountRequest removeEntitlementFromAccountRequest)
			throws AuthenticationException, IOException, Exception {
		EicResponse eicResponse = removeEntitlementFromAccount(removeEntitlementFromAccountRequest, true);
		RemoveEntitlementFromAccountResponse removeEntitlementFromAccountResponse = new Gson()
				.fromJson(eicResponse.getBody(), RemoveEntitlementFromAccountResponse.class);
		String errorCode = removeEntitlementFromAccountResponse.getErrorCode();
		if (errorCode.equals("0")) {
			return true;
		} else {
			throw new Exception(removeEntitlementFromAccountResponse.getMessage());
		}
	}

	private EicResponse removeEntitlementFromAccount(
			RemoveEntitlementFromAccountRequest removeEntitlementFromAccountRequest, Boolean test)
			throws AuthenticationException, IOException, Exception {
		Map<String, Object> apiConfig = getApiConfigMap("Accounts", "Remove_Entitlement_From_Account");
		String apiUrl = EIC_BASE_URL + apiConfig.get("URL");
		String method = (String) apiConfig.get("METHOD");
		String requestBody = EicClientUtils.xWwwFormUrlencoder(removeEntitlementFromAccountRequest);
		@SuppressWarnings("unchecked")
		Map<String, String> headers = (Map<String, String>) apiConfig.get("HEADER");
		headers.put("Authorization", "Bearer " + getAccessToken());
		EicRequest eicRequest = new EicRequest(apiUrl, method, headers, requestBody);
		EicResponse eicResponse = EicClientUtils.sendRequest(eicRequest);
		if (eicResponse.getResponseCode() >= 200 && eicResponse.getResponseCode() <= 299) {
			return eicResponse;
		} else {
			throw new Exception(eicResponse.toString());
		}
	}
}