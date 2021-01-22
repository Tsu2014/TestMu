package com.tsu.testdb.lib;

@DBTable("User")
public class User {

    @DBField("account")
    String account;

    @DBField("pwd")
    String password;

    private Integer uid;

    public User(String account , String password){
        this.account = account;
        this.password = password;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }



    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
