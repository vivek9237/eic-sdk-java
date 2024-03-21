package com.github.vivek9237.eic.restsdk.accounts;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Class representing a response for fetching accounts.
 */
public class GetAccountResponse {
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("displaycount")
    @Expose
    private Long displaycount;
    @SerializedName("total")
    @Expose
    private Long total;
    @SerializedName("Accountdetails")
    @Expose
    private List<Accountdetail> accountdetails;
    @SerializedName("errorCode")
    @Expose
    private String errorCode;

    public String getMsg() {
        return msg;
    }

    public Long getDisplaycount() {
        return displaycount;
    }

    public Long getTotal() {
        return total;
    }

    public List<Accountdetail> getAccountdetails() {
        return accountdetails;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public class Accountdetail {
        @SerializedName("passwordlockdate")
        @Expose
        private String passwordlockdate;
        @SerializedName("customproperty1")
        @Expose
        private String customproperty1;
        @SerializedName("customproperty2")
        @Expose
        private String customproperty2;
        @SerializedName("customproperty3")
        @Expose
        private String customproperty3;
        @SerializedName("customproperty4")
        @Expose
        private String customproperty4;
        @SerializedName("customproperty5")
        @Expose
        private String customproperty5;
        @SerializedName("customproperty6")
        @Expose
        private String customproperty6;
        @SerializedName("customproperty7")
        @Expose
        private String customproperty7;
        @SerializedName("customproperty8")
        @Expose
        private String customproperty8;
        @SerializedName("customproperty9")
        @Expose
        private String customproperty9;
        @SerializedName("customproperty10")
        @Expose
        private String customproperty10;
        @SerializedName("customproperty11")
        @Expose
        private String customproperty11;
        @SerializedName("customproperty12")
        @Expose
        private String customproperty12;
        @SerializedName("customproperty13")
        @Expose
        private String customproperty13;
        @SerializedName("customproperty14")
        @Expose
        private String customproperty14;
        @SerializedName("customproperty15")
        @Expose
        private String customproperty15;
        @SerializedName("customproperty16")
        @Expose
        private String customproperty16;
        @SerializedName("customproperty17")
        @Expose
        private String customproperty17;
        @SerializedName("customproperty18")
        @Expose
        private String customproperty18;
        @SerializedName("customproperty19")
        @Expose
        private String customproperty19;
        @SerializedName("customproperty20")
        @Expose
        private String customproperty20;
        @SerializedName("customproperty21")
        @Expose
        private String customproperty21;
        @SerializedName("customproperty22")
        @Expose
        private String customproperty22;
        @SerializedName("customproperty23")
        @Expose
        private String customproperty23;
        @SerializedName("customproperty24")
        @Expose
        private String customproperty24;
        @SerializedName("customproperty25")
        @Expose
        private String customproperty25;
        @SerializedName("customproperty26")
        @Expose
        private String customproperty26;
        @SerializedName("customproperty27")
        @Expose
        private String customproperty27;
        @SerializedName("customproperty28")
        @Expose
        private String customproperty28;
        @SerializedName("customproperty29")
        @Expose
        private String customproperty29;
        @SerializedName("customproperty30")
        @Expose
        private String customproperty30;
        @SerializedName("customproperty31")
        @Expose
        private String customproperty31;
        @SerializedName("customproperty32")
        @Expose
        private String customproperty32;
        @SerializedName("customproperty33")
        @Expose
        private String customproperty33;
        @SerializedName("customproperty34")
        @Expose
        private String customproperty34;
        @SerializedName("customproperty35")
        @Expose
        private String customproperty35;
        @SerializedName("customproperty36")
        @Expose
        private String customproperty36;
        @SerializedName("customproperty37")
        @Expose
        private String customproperty37;
        @SerializedName("customproperty38")
        @Expose
        private String customproperty38;
        @SerializedName("customproperty39")
        @Expose
        private String customproperty39;
        @SerializedName("customproperty40")
        @Expose
        private String customproperty40;
        @SerializedName("customproperty41")
        @Expose
        private String customproperty41;
        @SerializedName("customproperty42")
        @Expose
        private String customproperty42;
        @SerializedName("customproperty43")
        @Expose
        private String customproperty43;
        @SerializedName("customproperty44")
        @Expose
        private String customproperty44;
        @SerializedName("customproperty45")
        @Expose
        private String customproperty45;
        @SerializedName("customproperty46")
        @Expose
        private String customproperty46;
        @SerializedName("customproperty47")
        @Expose
        private String customproperty47;
        @SerializedName("customproperty48")
        @Expose
        private String customproperty48;
        @SerializedName("customproperty49")
        @Expose
        private String customproperty49;
        @SerializedName("customproperty50")
        @Expose
        private String customproperty50;
        @SerializedName("customproperty51")
        @Expose
        private String customproperty51;
        @SerializedName("customproperty52")
        @Expose
        private String customproperty52;
        @SerializedName("customproperty53")
        @Expose
        private String customproperty53;
        @SerializedName("customproperty54")
        @Expose
        private String customproperty54;
        @SerializedName("customproperty55")
        @Expose
        private String customproperty55;
        @SerializedName("customproperty56")
        @Expose
        private String customproperty56;
        @SerializedName("customproperty57")
        @Expose
        private String customproperty57;
        @SerializedName("customproperty58")
        @Expose
        private String customproperty58;
        @SerializedName("customproperty59")
        @Expose
        private String customproperty59;
        @SerializedName("customproperty60")
        @Expose
        private String customproperty60;
        @SerializedName("endpointKey")
        @Expose
        private Long endpointKey;
        @SerializedName("accounttype")
        @Expose
        private String accounttype;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("accountID")
        @Expose
        private String accountID;
        @SerializedName("endpoint")
        @Expose
        private String endpoint;
        @SerializedName("updatedate")
        @Expose
        private String updatedate;
        @SerializedName("passwordchangestatus")
        @Expose
        private String passwordchangestatus;
        @SerializedName("accountowner")
        @Expose
        private List<Accountowner> accountowner;
        @SerializedName("validthrough")
        @Expose
        private String validthrough;
        @SerializedName("incorrectlogons")
        @Expose
        private String incorrectlogons;
        @SerializedName("lastlogondate")
        @Expose
        private String lastlogondate;
        @SerializedName("creator")
        @Expose
        private String creator;
        @SerializedName("comments")
        @Expose
        private String comments;
        @SerializedName("updateUser")
        @Expose
        private String updateUser;
        @SerializedName("createdon")
        @Expose
        private String createdon;
        @SerializedName("userKey")
        @Expose
        private String userKey;
        @SerializedName("accountKey")
        @Expose
        private Long accountKey;
        @SerializedName("privileged")
        @Expose
        private String privileged;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("usergroup")
        @Expose
        private String usergroup;
        @SerializedName("validfrom")
        @Expose
        private String validfrom;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("username")
        @Expose
        private String username;

        public String getPasswordlockdate() {
            return passwordlockdate;
        }

        public String getCustomproperty1() {
            return customproperty1;
        }

        public String getCustomproperty2() {
            return customproperty2;
        }

        public String getCustomproperty3() {
            return customproperty3;
        }

        public String getCustomproperty4() {
            return customproperty4;
        }

        public String getCustomproperty5() {
            return customproperty5;
        }

        public String getCustomproperty6() {
            return customproperty6;
        }

        public String getCustomproperty7() {
            return customproperty7;
        }

        public String getCustomproperty8() {
            return customproperty8;
        }

        public String getCustomproperty9() {
            return customproperty9;
        }

        public String getCustomproperty10() {
            return customproperty10;
        }

        public String getCustomproperty11() {
            return customproperty11;
        }

        public String getCustomproperty12() {
            return customproperty12;
        }

        public String getCustomproperty13() {
            return customproperty13;
        }

        public String getCustomproperty14() {
            return customproperty14;
        }

        public String getCustomproperty15() {
            return customproperty15;
        }

        public String getCustomproperty16() {
            return customproperty16;
        }

        public String getCustomproperty17() {
            return customproperty17;
        }

        public String getCustomproperty18() {
            return customproperty18;
        }

        public String getCustomproperty19() {
            return customproperty19;
        }

        public String getCustomproperty20() {
            return customproperty20;
        }

        public String getCustomproperty21() {
            return customproperty21;
        }

        public String getCustomproperty22() {
            return customproperty22;
        }

        public String getCustomproperty23() {
            return customproperty23;
        }

        public String getCustomproperty24() {
            return customproperty24;
        }

        public String getCustomproperty25() {
            return customproperty25;
        }

        public String getCustomproperty26() {
            return customproperty26;
        }

        public String getCustomproperty27() {
            return customproperty27;
        }

        public String getCustomproperty28() {
            return customproperty28;
        }

        public String getCustomproperty29() {
            return customproperty29;
        }

        public String getCustomproperty30() {
            return customproperty30;
        }

        public String getCustomproperty31() {
            return customproperty31;
        }

        public String getCustomproperty32() {
            return customproperty32;
        }

        public String getCustomproperty33() {
            return customproperty33;
        }

        public String getCustomproperty34() {
            return customproperty34;
        }

        public String getCustomproperty35() {
            return customproperty35;
        }

        public String getCustomproperty36() {
            return customproperty36;
        }

        public String getCustomproperty37() {
            return customproperty37;
        }

        public String getCustomproperty38() {
            return customproperty38;
        }

        public String getCustomproperty39() {
            return customproperty39;
        }

        public String getCustomproperty40() {
            return customproperty40;
        }

        public String getCustomproperty41() {
            return customproperty41;
        }

        public String getCustomproperty42() {
            return customproperty42;
        }

        public String getCustomproperty43() {
            return customproperty43;
        }

        public String getCustomproperty44() {
            return customproperty44;
        }

        public String getCustomproperty45() {
            return customproperty45;
        }

        public String getCustomproperty46() {
            return customproperty46;
        }

        public String getCustomproperty47() {
            return customproperty47;
        }

        public String getCustomproperty48() {
            return customproperty48;
        }

        public String getCustomproperty49() {
            return customproperty49;
        }

        public String getCustomproperty50() {
            return customproperty50;
        }

        public String getCustomproperty51() {
            return customproperty51;
        }

        public String getCustomproperty52() {
            return customproperty52;
        }

        public String getCustomproperty53() {
            return customproperty53;
        }

        public String getCustomproperty54() {
            return customproperty54;
        }

        public String getCustomproperty55() {
            return customproperty55;
        }

        public String getCustomproperty56() {
            return customproperty56;
        }

        public String getCustomproperty57() {
            return customproperty57;
        }

        public String getCustomproperty58() {
            return customproperty58;
        }

        public String getCustomproperty59() {
            return customproperty59;
        }

        public String getCustomproperty60() {
            return customproperty60;
        }

        public Long getEndpointKey() {
            return endpointKey;
        }

        public String getAccounttype() {
            return accounttype;
        }

        public String getDescription() {
            return description;
        }

        public String getAccountID() {
            return accountID;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public String getUpdatedate() {
            return updatedate;
        }

        public String getPasswordchangestatus() {
            return passwordchangestatus;
        }

        public List<Accountowner> getAccountowner() {
            return accountowner;
        }

        public String getValidthrough() {
            return validthrough;
        }

        public String getIncorrectlogons() {
            return incorrectlogons;
        }

        public String getLastlogondate() {
            return lastlogondate;
        }

        public String getCreator() {
            return creator;
        }

        public String getComments() {
            return comments;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public String getCreatedon() {
            return createdon;
        }

        public String getUserKey() {
            return userKey;
        }

        public Long getAccountKey() {
            return accountKey;
        }

        public String getPrivileged() {
            return privileged;
        }

        public String getName() {
            return name;
        }

        public String getUsergroup() {
            return usergroup;
        }

        public String getValidfrom() {
            return validfrom;
        }

        public String getStatus() {
            return status;
        }

        public String getUsername() {
            return username;
        }
    }

    public class Accountowner {
        @SerializedName("rank")
        @Expose
        private Long rank;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("value")
        @Expose
        private String value;

        public Long getRank() {
            return rank;
        }

        public String getType() {
            return type;
        }

        public String getValue() {
            return value;
        }
    }
}