package com.neatroots.footballnote.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserInfo {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "userName")
    public String userName;

    @ColumnInfo(name = "amount")
    public String amount;

    public UserInfo(int uid, String userName, String amount, String date, boolean isPaid) {
        this.uid = uid;
        this.userName = userName;
        this.amount = amount;
        this.date = date;
        this.isPaid = isPaid;
    }

    public UserInfo(String userName, String amount, String date, boolean isPaid) {
        this.userName = userName;
        this.amount = amount;
        this.date = date;
        this.isPaid = isPaid;
    }

    public UserInfo() {
    }

    public int getUid() {
        return uid;
    }

    public String getUserName() {
        return userName;
    }

    public String getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public boolean isPaid() {
        return isPaid;
    }

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "isPaid")
    public boolean isPaid;
}
