package com.example.changetheworld.model;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

public interface DataBaseInterface {
    void VerifyAndSavePrivateClient(Context context, PrivateClient user, Intent intent);
    void VerifyAndSaveBusiness(Context context, BusinessClient businessClient, Intent intent);
    void VerifyAndLogin(Context context, String user_name, String password, Intent intent, String type);
    void LoadProfilePhoto(ImageView profilPhoto, String user_name);
}
