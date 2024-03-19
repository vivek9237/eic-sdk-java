package com.github.vivek9237.eic.restsdk.accounts;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAccountRequest {
    @SerializedName("endpoint")
    @Expose
    private String endpoint;
    @SerializedName("endpointKey")
    @Expose
    private String endpointKey;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("advsearchcriteria")
    @Expose
    private Advsearchcriteria advsearchcriteria;
    @SerializedName("accountQuery")
    @Expose
    private String accountQuery;
    @SerializedName("isPAM")
    @Expose
    private String isPAM;
    @SerializedName("max")
    @Expose
    private String max;
    @SerializedName("offset")
    @Expose
    private String offset;

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getEndpointKey() {
        return endpointKey;
    }

    public void setEndpointKey(String endpointKey) {
        this.endpointKey = endpointKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Advsearchcriteria getAdvsearchcriteria() {
        return advsearchcriteria;
    }

    public void setAdvsearchcriteria(Advsearchcriteria advsearchcriteria) {
        this.advsearchcriteria = advsearchcriteria;
    }

    public String getAccountQuery() {
        return accountQuery;
    }

    public void setAccountQuery(String accountQuery) {
        this.accountQuery = accountQuery;
    }

    public String getIsPAM() {
        return isPAM;
    }

    public void setIsPAM(String isPAM) {
        this.isPAM = isPAM;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public class Advsearchcriteria {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("comments")
        @Expose
        private String comments;
        @SerializedName("accounttype")
        @Expose
        private String accounttype;
        @SerializedName("accountclass")
        @Expose
        private String accountclass;
        @SerializedName("usergroup")
        @Expose
        private String usergroup;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("validfrom")
        @Expose
        private String validfrom;
        @SerializedName("validthrough")
        @Expose
        private String validthrough;
        @SerializedName("userlock")
        @Expose
        private String userlock;
        @SerializedName("incorrectlogons")
        @Expose
        private String incorrectlogons;
        @SerializedName("creator")
        @Expose
        private String creator;
        @SerializedName("createdon")
        @Expose
        private String createdon;
        @SerializedName("lastlogondate")
        @Expose
        private String lastlogondate;
        @SerializedName("lastpasswordchange")
        @Expose
        private String lastpasswordchange;
        @SerializedName("targetlastpasswordchange")
        @Expose
        private String targetlastpasswordchange;
        @SerializedName("passwordlockdate")
        @Expose
        private String passwordlockdate;
        @SerializedName("jobid")
        @Expose
        private String jobid;
        @SerializedName("privileged")
        @Expose
        private String privileged;
        @SerializedName("passwordchangestatus")
        @Expose
        private String passwordchangestatus;
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
        @SerializedName("updatedate")
        @Expose
        private String updatedate;
        @SerializedName("updateUser")
        @Expose
        private String updateUser;
        @SerializedName("accountID")
        @Expose
        private String accountID;
        @SerializedName("lockedState")
        @Expose
        private String lockedState;
        @SerializedName("accountowner")
        @Expose
        private List<Accountowner> accountowner;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getAccounttype() {
            return accounttype;
        }

        public void setAccounttype(String accounttype) {
            this.accounttype = accounttype;
        }

        public String getAccountclass() {
            return accountclass;
        }

        public void setAccountclass(String accountclass) {
            this.accountclass = accountclass;
        }

        public String getUsergroup() {
            return usergroup;
        }

        public void setUsergroup(String usergroup) {
            this.usergroup = usergroup;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getValidfrom() {
            return validfrom;
        }

        public void setValidfrom(String validfrom) {
            this.validfrom = validfrom;
        }

        public String getValidthrough() {
            return validthrough;
        }

        public void setValidthrough(String validthrough) {
            this.validthrough = validthrough;
        }

        public String getUserlock() {
            return userlock;
        }

        public void setUserlock(String userlock) {
            this.userlock = userlock;
        }

        public String getIncorrectlogons() {
            return incorrectlogons;
        }

        public void setIncorrectlogons(String incorrectlogons) {
            this.incorrectlogons = incorrectlogons;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getCreatedon() {
            return createdon;
        }

        public void setCreatedon(String createdon) {
            this.createdon = createdon;
        }

        public String getLastlogondate() {
            return lastlogondate;
        }

        public void setLastlogondate(String lastlogondate) {
            this.lastlogondate = lastlogondate;
        }

        public String getLastpasswordchange() {
            return lastpasswordchange;
        }

        public void setLastpasswordchange(String lastpasswordchange) {
            this.lastpasswordchange = lastpasswordchange;
        }

        public String getTargetlastpasswordchange() {
            return targetlastpasswordchange;
        }

        public void setTargetlastpasswordchange(String targetlastpasswordchange) {
            this.targetlastpasswordchange = targetlastpasswordchange;
        }

        public String getPasswordlockdate() {
            return passwordlockdate;
        }

        public void setPasswordlockdate(String passwordlockdate) {
            this.passwordlockdate = passwordlockdate;
        }

        public String getJobid() {
            return jobid;
        }

        public void setJobid(String jobid) {
            this.jobid = jobid;
        }

        public String getPrivileged() {
            return privileged;
        }

        public void setPrivileged(String privileged) {
            this.privileged = privileged;
        }

        public String getPasswordchangestatus() {
            return passwordchangestatus;
        }

        public void setPasswordchangestatus(String passwordchangestatus) {
            this.passwordchangestatus = passwordchangestatus;
        }

        public String getCustomproperty1() {
            return customproperty1;
        }

        public void setCustomproperty1(String customproperty1) {
            this.customproperty1 = customproperty1;
        }

        public String getCustomproperty2() {
            return customproperty2;
        }

        public void setCustomproperty2(String customproperty2) {
            this.customproperty2 = customproperty2;
        }

        public String getCustomproperty3() {
            return customproperty3;
        }

        public void setCustomproperty3(String customproperty3) {
            this.customproperty3 = customproperty3;
        }

        public String getCustomproperty4() {
            return customproperty4;
        }

        public void setCustomproperty4(String customproperty4) {
            this.customproperty4 = customproperty4;
        }

        public String getCustomproperty5() {
            return customproperty5;
        }

        public void setCustomproperty5(String customproperty5) {
            this.customproperty5 = customproperty5;
        }

        public String getCustomproperty6() {
            return customproperty6;
        }

        public void setCustomproperty6(String customproperty6) {
            this.customproperty6 = customproperty6;
        }

        public String getCustomproperty7() {
            return customproperty7;
        }

        public void setCustomproperty7(String customproperty7) {
            this.customproperty7 = customproperty7;
        }

        public String getCustomproperty8() {
            return customproperty8;
        }

        public void setCustomproperty8(String customproperty8) {
            this.customproperty8 = customproperty8;
        }

        public String getCustomproperty9() {
            return customproperty9;
        }

        public void setCustomproperty9(String customproperty9) {
            this.customproperty9 = customproperty9;
        }

        public String getCustomproperty10() {
            return customproperty10;
        }

        public void setCustomproperty10(String customproperty10) {
            this.customproperty10 = customproperty10;
        }

        public String getCustomproperty11() {
            return customproperty11;
        }

        public void setCustomproperty11(String customproperty11) {
            this.customproperty11 = customproperty11;
        }

        public String getCustomproperty12() {
            return customproperty12;
        }

        public void setCustomproperty12(String customproperty12) {
            this.customproperty12 = customproperty12;
        }

        public String getCustomproperty13() {
            return customproperty13;
        }

        public void setCustomproperty13(String customproperty13) {
            this.customproperty13 = customproperty13;
        }

        public String getCustomproperty14() {
            return customproperty14;
        }

        public void setCustomproperty14(String customproperty14) {
            this.customproperty14 = customproperty14;
        }

        public String getCustomproperty15() {
            return customproperty15;
        }

        public void setCustomproperty15(String customproperty15) {
            this.customproperty15 = customproperty15;
        }

        public String getCustomproperty16() {
            return customproperty16;
        }

        public void setCustomproperty16(String customproperty16) {
            this.customproperty16 = customproperty16;
        }

        public String getCustomproperty17() {
            return customproperty17;
        }

        public void setCustomproperty17(String customproperty17) {
            this.customproperty17 = customproperty17;
        }

        public String getCustomproperty18() {
            return customproperty18;
        }

        public void setCustomproperty18(String customproperty18) {
            this.customproperty18 = customproperty18;
        }

        public String getCustomproperty19() {
            return customproperty19;
        }

        public void setCustomproperty19(String customproperty19) {
            this.customproperty19 = customproperty19;
        }

        public String getCustomproperty20() {
            return customproperty20;
        }

        public void setCustomproperty20(String customproperty20) {
            this.customproperty20 = customproperty20;
        }

        public String getCustomproperty21() {
            return customproperty21;
        }

        public void setCustomproperty21(String customproperty21) {
            this.customproperty21 = customproperty21;
        }

        public String getCustomproperty22() {
            return customproperty22;
        }

        public void setCustomproperty22(String customproperty22) {
            this.customproperty22 = customproperty22;
        }

        public String getCustomproperty23() {
            return customproperty23;
        }

        public void setCustomproperty23(String customproperty23) {
            this.customproperty23 = customproperty23;
        }

        public String getCustomproperty24() {
            return customproperty24;
        }

        public void setCustomproperty24(String customproperty24) {
            this.customproperty24 = customproperty24;
        }

        public String getCustomproperty25() {
            return customproperty25;
        }

        public void setCustomproperty25(String customproperty25) {
            this.customproperty25 = customproperty25;
        }

        public String getCustomproperty26() {
            return customproperty26;
        }

        public void setCustomproperty26(String customproperty26) {
            this.customproperty26 = customproperty26;
        }

        public String getCustomproperty27() {
            return customproperty27;
        }

        public void setCustomproperty27(String customproperty27) {
            this.customproperty27 = customproperty27;
        }

        public String getCustomproperty28() {
            return customproperty28;
        }

        public void setCustomproperty28(String customproperty28) {
            this.customproperty28 = customproperty28;
        }

        public String getCustomproperty29() {
            return customproperty29;
        }

        public void setCustomproperty29(String customproperty29) {
            this.customproperty29 = customproperty29;
        }

        public String getCustomproperty30() {
            return customproperty30;
        }

        public void setCustomproperty30(String customproperty30) {
            this.customproperty30 = customproperty30;
        }

        public String getCustomproperty31() {
            return customproperty31;
        }

        public void setCustomproperty31(String customproperty31) {
            this.customproperty31 = customproperty31;
        }

        public String getCustomproperty32() {
            return customproperty32;
        }

        public void setCustomproperty32(String customproperty32) {
            this.customproperty32 = customproperty32;
        }

        public String getCustomproperty33() {
            return customproperty33;
        }

        public void setCustomproperty33(String customproperty33) {
            this.customproperty33 = customproperty33;
        }

        public String getCustomproperty34() {
            return customproperty34;
        }

        public void setCustomproperty34(String customproperty34) {
            this.customproperty34 = customproperty34;
        }

        public String getCustomproperty35() {
            return customproperty35;
        }

        public void setCustomproperty35(String customproperty35) {
            this.customproperty35 = customproperty35;
        }

        public String getCustomproperty36() {
            return customproperty36;
        }

        public void setCustomproperty36(String customproperty36) {
            this.customproperty36 = customproperty36;
        }

        public String getCustomproperty37() {
            return customproperty37;
        }

        public void setCustomproperty37(String customproperty37) {
            this.customproperty37 = customproperty37;
        }

        public String getCustomproperty38() {
            return customproperty38;
        }

        public void setCustomproperty38(String customproperty38) {
            this.customproperty38 = customproperty38;
        }

        public String getCustomproperty39() {
            return customproperty39;
        }

        public void setCustomproperty39(String customproperty39) {
            this.customproperty39 = customproperty39;
        }

        public String getCustomproperty40() {
            return customproperty40;
        }

        public void setCustomproperty40(String customproperty40) {
            this.customproperty40 = customproperty40;
        }

        public String getCustomproperty41() {
            return customproperty41;
        }

        public void setCustomproperty41(String customproperty41) {
            this.customproperty41 = customproperty41;
        }

        public String getCustomproperty42() {
            return customproperty42;
        }

        public void setCustomproperty42(String customproperty42) {
            this.customproperty42 = customproperty42;
        }

        public String getCustomproperty43() {
            return customproperty43;
        }

        public void setCustomproperty43(String customproperty43) {
            this.customproperty43 = customproperty43;
        }

        public String getCustomproperty44() {
            return customproperty44;
        }

        public void setCustomproperty44(String customproperty44) {
            this.customproperty44 = customproperty44;
        }

        public String getCustomproperty45() {
            return customproperty45;
        }

        public void setCustomproperty45(String customproperty45) {
            this.customproperty45 = customproperty45;
        }

        public String getCustomproperty46() {
            return customproperty46;
        }

        public void setCustomproperty46(String customproperty46) {
            this.customproperty46 = customproperty46;
        }

        public String getCustomproperty47() {
            return customproperty47;
        }

        public void setCustomproperty47(String customproperty47) {
            this.customproperty47 = customproperty47;
        }

        public String getCustomproperty48() {
            return customproperty48;
        }

        public void setCustomproperty48(String customproperty48) {
            this.customproperty48 = customproperty48;
        }

        public String getCustomproperty49() {
            return customproperty49;
        }

        public void setCustomproperty49(String customproperty49) {
            this.customproperty49 = customproperty49;
        }

        public String getCustomproperty50() {
            return customproperty50;
        }

        public void setCustomproperty50(String customproperty50) {
            this.customproperty50 = customproperty50;
        }

        public String getCustomproperty51() {
            return customproperty51;
        }

        public void setCustomproperty51(String customproperty51) {
            this.customproperty51 = customproperty51;
        }

        public String getCustomproperty52() {
            return customproperty52;
        }

        public void setCustomproperty52(String customproperty52) {
            this.customproperty52 = customproperty52;
        }

        public String getCustomproperty53() {
            return customproperty53;
        }

        public void setCustomproperty53(String customproperty53) {
            this.customproperty53 = customproperty53;
        }

        public String getCustomproperty54() {
            return customproperty54;
        }

        public void setCustomproperty54(String customproperty54) {
            this.customproperty54 = customproperty54;
        }

        public String getCustomproperty55() {
            return customproperty55;
        }

        public void setCustomproperty55(String customproperty55) {
            this.customproperty55 = customproperty55;
        }

        public String getCustomproperty56() {
            return customproperty56;
        }

        public void setCustomproperty56(String customproperty56) {
            this.customproperty56 = customproperty56;
        }

        public String getCustomproperty57() {
            return customproperty57;
        }

        public void setCustomproperty57(String customproperty57) {
            this.customproperty57 = customproperty57;
        }

        public String getCustomproperty58() {
            return customproperty58;
        }

        public void setCustomproperty58(String customproperty58) {
            this.customproperty58 = customproperty58;
        }

        public String getCustomproperty59() {
            return customproperty59;
        }

        public void setCustomproperty59(String customproperty59) {
            this.customproperty59 = customproperty59;
        }

        public String getCustomproperty60() {
            return customproperty60;
        }

        public void setCustomproperty60(String customproperty60) {
            this.customproperty60 = customproperty60;
        }

        public String getUpdatedate() {
            return updatedate;
        }

        public void setUpdatedate(String updatedate) {
            this.updatedate = updatedate;
        }

        public String getUpdateUser() {
            return updateUser;
        }

        public void setUpdateUser(String updateUser) {
            this.updateUser = updateUser;
        }

        public String getAccountID() {
            return accountID;
        }

        public void setAccountID(String accountID) {
            this.accountID = accountID;
        }

        public String getLockedState() {
            return lockedState;
        }

        public void setLockedState(String lockedState) {
            this.lockedState = lockedState;
        }

        public List<Accountowner> getAccountowner() {
            return accountowner;
        }

        public void setAccountowner(List<Accountowner> accountowner) {
            this.accountowner = accountowner;
        }
        public class Accountowner {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("value")
            @Expose
            private String value;
            @SerializedName("rank")
            @Expose
            private String rank;
    
            public String getType() {
                return type;
            }
    
            public void setType(String type) {
                this.type = type;
            }
    
            public String getValue() {
                return value;
            }
    
            public void setValue(String value) {
                this.value = value;
            }
    
            public String getRank() {
                return rank;
            }
    
            public void setRank(String rank) {
                this.rank = rank;
            }
        }
    }
}
