package com.tnedutsledom.modelstudent;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class UserSelectActivity extends AppCompatActivity {

    GoogleSignInOptions gso;                // 앱에 필요한 사용자 데이터를 요청할 수 있도록 하는 로그인 옵션
    GoogleSignInClient mGoogleSignInClient; // 구글 로그인 객체
    private final int RC_SIGN_IN = 123;     // 구글 로그인 고유값 (신경안써도 ㄱㅊ)
    SignInButton sign_in_button;            // 구글 로그인 버튼
    String user_email;                      // 유저 gmail 정보

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select);
        init();
        signInGoogle();
    }
    void init(){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail() // email addresses 요청
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(UserSelectActivity.this, gso);
        sign_in_button = findViewById(R.id.btn_google_login);
    }

    void signInGoogle(){
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    void startSignUpActivity(String user_email) {
        Intent intent_view_change = new Intent(getApplicationContext(),SignUpActivity.class);
        intent_view_change.putExtra("user_email",user_email);
        startActivity(intent_view_change);
        intent_view_change.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            startSignUpActivity(user_email);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acct = completedTask.getResult(ApiException.class);
            if (acct != null) {
                user_email = acct.getEmail();
            }
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e("구글 로그인 에러", "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
