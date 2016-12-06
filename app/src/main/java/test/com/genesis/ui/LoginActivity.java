package test.com.genesis.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import test.com.genesis.App;
import test.com.genesis.R;
import test.com.genesis.repository.IAuthRepository;

public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.activity_login)
    View rootView;
    private IAuthRepository authRepository;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authRepository = App.app.getAppComponent().getAuthRepository();
        if (authRepository.isLoggedIn()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initFacebook();
    }

    @OnClick(R.id.btnLogin)
    void onLoginClick() {
        LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("email", "user_friends", "public_profile"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void initFacebook() {
        mCallbackManager = CallbackManager.Factory.create();
        final LoginManager instance = LoginManager.getInstance();
        instance.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                authRepository.setAuthToken(loginResult.getAccessToken().getToken());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                instance.logOut();
            }
        });
    }
}
