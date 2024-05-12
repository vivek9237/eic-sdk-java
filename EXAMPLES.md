## Sample snippets for SDK Implementation

- [User Management](#user-management)
  - [Get User by Username](#get-user-by-username)
  - [Get all Users](#get-all-users)
  - [Create User](#create-user)
- [Account Management](#account-management)
  - [Get Accounts associated to a user](#get-accounts-associated-to-a-user)
  - [Get Active Accounts associated to a user](#get-active-accounts-associated-to-a-user)
  - [Get Active Accounts associated to a user and Endpoint](#get-active-accounts-associated-to-a-user-and-endpoint)
  - [Create Account](#create-account)
  - [Update Account](#update-account)
  - [Assign Account to a User](#assign-account-to-a-user)
  - [Assign Account to an Entitlement](#assign-account-to-an-entitlement)
- [Dataset Management](#dataset-management)
  - [Get Dataset All \_Values for a particular Dataset](#get-all-dataset_values-for-a-particular-dataset)


### User Management
#### Get User by Username

```Java
EicClient eicClient = new EicClient("<tenant name>", "<refresh token>");
Map<String, Object> userAttrs = eicClient.getUserByUsername("admin");
        
```

#### Get all Users

```Java
EicClient eicClient = new EicClient("<tenant name>", "<refresh token>");
Map<String, Object> params = new HashMap<String, Object>();
params.put("customproperty1", "test");
List<Map<String, Object>> users = eicClient.getAllUsers(params);
        
```

#### Create User

```Java
EicClient eicClient = new EicClient("<tenant name>", "<refresh token>");
//To be updated
        
```

#### Update User

```Java
EicClient eicClient = new EicClient("<tenant name>", "<refresh token>");
//To be updated
        
```

### Account Management
#### Get Accounts associated to a user

```Java
EicClient eicClient = new EicClient("<tenant name>", "<refresh token>");
List<Accountdetail> accountList = eicClient.getAccounts("admin");
        
```

#### Get Active Accounts associated to a user

```Java
EicClient eicClient = new EicClient("<tenant name>", "<refresh token>");
List<Accountdetail> accountList = eicClient.getAccounts("admin", true);
        
```

#### Get Active Accounts associated to a user and Endpoint

```Java
EicClient eicClient = new EicClient("<tenant name>", "<refresh token>");
List<Accountdetail> accountList = eicClient.getAccounts("admin", "Active Directory");
        
```

#### Create Account

```Java
EicClient eicClient = new EicClient("<tenant name>", "<refresh token>");
//To be updated
        
```

#### Update Account

```Java
EicClient eicClient = new EicClient("<tenant name>", "<refresh token>");
//To be updated
        
```

#### Assign Account to a User

```Java
EicClient eicClient = new EicClient("<tenant name>", "<refresh token>");
//To be updated
        
```

#### Assign Account to an Entitlement

```Java
EicClient eicClient = new EicClient("<tenant name>", "<refresh token>");
//To be updated
        
```


### Dataset Management
#### Get all Dataset_Values for a particular Dataset

```Java
EicClient eicClient = new EicClient("<tenant name>", "<refresh token>");
List<Map<String, Object>> datasetValues = eicClient.getDatasetValues("Test_Dataset");
        
```

<br><hr>
[🔼 Back to top](#Sample-snippets-for-SDK-Implementation)
