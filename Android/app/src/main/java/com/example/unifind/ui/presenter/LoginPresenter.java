package com.example.unifind.ui.presenter;

import android.util.Log;

import com.example.unifind.ui.view.View;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginPresenter {
    View.LoginPage loginPage;

    public LoginPresenter(View.LoginPage loginPage) {
        this.loginPage = loginPage;
    }

    public void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            loginPage.Intent();
        } catch (ApiException e) {
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            loginPage.MakeToast("Failed");
        }

    }
}
