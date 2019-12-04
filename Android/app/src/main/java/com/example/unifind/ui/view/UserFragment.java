package com.example.unifind.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.unifind.MainActivity;
import com.example.unifind.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;
import java.util.concurrent.Executor;

public class UserFragment extends Fragment //implements com.example.unifind.ui.view.View.UserView
 {

         android.view.View root;
    public android.view.View onCreateView(@NonNull LayoutInflater inflater,
                                          ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.user_page, container, false);
        return root;



     //    GoogleSignInClient mGoogleSignInClient;
//    Button sign_out;
//    TextView nameTV;
//    TextView emailTV;
//    TextView idTV;
//    ImageView photoIV;
//    android.view.View root;
//
//    public android.view.View onCreateView(@NonNull LayoutInflater inflater,
//                                          ViewGroup container, Bundle savedInstanceState) {
//        root = inflater.inflate(R.layout.fragment_notifications, container, false);
//
//        sign_out = root.findViewById(R.id.log_out);
//        nameTV = root.findViewById(R.id.name);
//        emailTV = root.findViewById(R.id.email);
//        idTV = root.findViewById(R.id.id);
//
//        // Configure sign-in to request the user's ID, email address, and basic
//        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestEmail()
//                .build();
//
//        // Build a GoogleSignInClient with the options specified by gso.
//        mGoogleSignInClient = GoogleSignIn.getClient(root.getContext(), gso);
//
//        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(root.getContext());
//        if (acct != null) {
//            String personName = acct.getDisplayName();
//            String personGivenName = acct.getGivenName();
//            String personFamilyName = acct.getFamilyName();
//            String personEmail = acct.getEmail();
//            String personId = acct.getId();
//
//            nameTV.setText("Name: " + personName);
//            emailTV.setText("Email: " + personEmail);
//            idTV.setText("ID: " + personId);
//        }
//        sign_out.setOnClickListener(new android.view.View.OnClickListener() {
//            @Override
//            public void onClick(android.view.View v) {
//                signOut();
//
//            }
//        });
//        return root;
//    }
//
//    private void signOut() {
//        mGoogleSignInClient.signOut();
//        Toast.makeText(root.getContext(), "Successfully signed out", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(getContext(), LoginPage.class));
//        Objects.requireNonNull(getActivity()).finish();
    }
}
