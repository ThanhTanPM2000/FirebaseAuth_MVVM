package com.example.firebaseauth_mvvm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.firebaseauth_mvvm.viewmodel.LoginRegisterViewModel;
import com.example.firebaseauth_mvvm.views.LoginRegisterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MyDialog extends DialogFragment {

    private Button btn;
    private LoginRegisterViewModel loginRegisterViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_dialog, container, false);
        btn = (Button) view.findViewById(R.id.button);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginRegisterViewModel = new ViewModelProvider(this).get(LoginRegisterViewModel.class);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = loginRegisterViewModel.getFirebaseAuth().getCurrentUser();
                assert user != null : "user null";
                if(user.isEmailVerified()){
                    Toast.makeText(getContext(), "your email vertification", Toast.LENGTH_LONG).show();
                    NavHostFragment.findNavController(MyDialog.this).navigate(R.id.action_myDialog_to_loginRegisterFragment);
                }else{
                    Toast.makeText(getContext(), "please verification your email to continue", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
