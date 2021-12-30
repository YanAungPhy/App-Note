package com.neatroots.footballnote.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.neatroots.footballnote.db.AppDatabase;
import com.neatroots.footballnote.db.UserInfo;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<List<UserInfo>> listOfUser;
    private AppDatabase appDatabase;

    public MainActivityViewModel(Application application) {
        super(application);
        listOfUser = new MutableLiveData<>();

        appDatabase = AppDatabase.getDBInstance(getApplication().getApplicationContext());
    }

    public MutableLiveData<List<UserInfo>> getListUserObserver() {
        return listOfUser;
    }

    public void getAllUserList() {
        List<UserInfo> userList = appDatabase.userDetailDao().getUserList();
        if (userList.size() > 0) {
            listOfUser.postValue(userList);
        } else {
            listOfUser.postValue(null);
        }
    }

    public void insertUser(String userName, String amount, String date, boolean isPaid) {
        UserInfo userInfo = new UserInfo(userName, amount, date, isPaid);
        userInfo.userName = userName;
        userInfo.amount = amount;
        userInfo.date = date;
        userInfo.isPaid = isPaid;
        appDatabase.userDetailDao().insertUser(userInfo);
        getAllUserList();
    }

    public void updateUser(UserInfo userInfo) {
        appDatabase.userDetailDao().updateUser(userInfo);
        getAllUserList();
    }

    public void deleteUser(UserInfo userInfo) {
        appDatabase.userDetailDao().deleteUser(userInfo);
        getAllUserList();
    }

    public int totalAmount(boolean isPaid) {
        return appDatabase.userDetailDao().getTotalAmount(isPaid);
    }
}
