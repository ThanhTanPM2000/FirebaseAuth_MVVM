package com.example.firebaseauth_mvvm.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.example.firebaseauth_mvvm.model.AppRepository;

public class LoginRegisterViewModel extends AndroidViewModel {

    private AppRepository appRepository;
    private FirebaseAuth firebaseAuth;
    private Application application;
    private MutableLiveData<FirebaseUser> userMutableLiveData;


    public LoginRegisterViewModel(@NonNull Application application) {
        super(application);

        appRepository = new AppRepository(application);
        userMutableLiveData = appRepository.getUserMutableLiveData();
        this.firebaseAuth = appRepository.getFirebaseAuth();
        this.application = appRepository.getApplication();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    public void register(String email, String password){
        appRepository.register(email, password);
    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public AppRepository getAppRepository() {
        return appRepository;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }
}
