package com.example.firebaseauth_mvvm.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.firebaseauth_mvvm.MyDialog;
import com.example.firebaseauth_mvvm.R;
import com.example.firebaseauth_mvvm.viewmodel.LoginRegisterViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginRegisterFragment extends Fragment {

    private EditText et_email;
    private EditText et_password;
    private Button btn_login;
    private Button btn_register;

    private LoginRegisterViewModel loginRegisterViewModel;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginRegisterViewModel = new ViewModelProvider(this).get(LoginRegisterViewModel.class);
        loginRegisterViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if(firebaseUser !=null){
                    loginRegisterViewModel.getFirebaseAuth().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            FirebaseUser user = loginRegisterViewModel.getFirebaseAuth().getCurrentUser();
                            if(user !=null){
                                sendVerificationEmail();
                            }else{

                            }
                        }
                    });
                }
            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_loginregister, container, false);

        et_email = (EditText) view.findViewById(R.id.fragment_loginregister_email);
        et_password = (EditText) view.findViewById(R.id.fragment_loginregister_password);
        btn_login = (Button) view.findViewById(R.id.fragment_loginregister_login);
        btn_register = (Button) view.findViewById(R.id.fragment_loginregister_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View v) {
                String str_email = et_email.getText().toString();
                String str_pass = et_password.getText().toString();
                if(str_email.length() >0 && str_pass.length() >0){
                    loginRegisterViewModel.register(str_email, str_pass);
                }
            }
        });

        return view;

    }

    private void sendVerificationEmail()
    {
        FirebaseUser user = loginRegisterViewModel.getFirebaseAuth().getCurrentUser();

        assert user != null;
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getContext(),"Link verification was sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(),"Cant send email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseUser user = loginRegisterViewModel.getFirebaseAuth().getCurrentUser();
        assert user != null : "user null";
        if(user.isEmailVerified()){
            Toast.makeText(getContext(), "Verification successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
