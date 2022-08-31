package com.tnedutsledom.modelstudent.intro_activitys;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.tnedutsledom.modelstudent.R;

public class UserSelectActivity extends AppCompatActivity {

    GoogleSignInOptions gso;                // 앱에 필요한 사용자 데이터를 요청할 수 있도록 하는 로그인 옵션
    GoogleSignInClient mGoogleSignInClient; // 구글 로그인 객체
    private final int RC_SIGN_IN = 123;     // 구글 로그인 고유값 (신경안써도 ㄱㅊ)
    SignInButton sign_in_button;            // 구글 로그인 버튼

    private FirebaseAuth mAuth = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select);
        init();
        signInGoogle();
    }
    void init(){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail() // email addresses 요청
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(UserSelectActivity.this, gso);
        sign_in_button = findViewById(R.id.btn_google_login);
        mAuth = FirebaseAuth.getInstance();
    }

    private void signInGoogle() {
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.e("구글 로그인 오류", "onActivityResult: ", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Log.d("구글 로그인 성공", "onComplete: ");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startSignUpActivity(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("구글 로그인 오류", "onComplete: ");
                            startSignUpActivity(null);
                        }
                    }
                });
    }

    void startSignUpActivity(FirebaseUser user) {
        if (user != null) {
            Intent intent_view_change = new Intent(getApplicationContext(), SignUpActivity.class);
            intent_view_change.putExtra("user_email", user.getEmail());
            startActivity(intent_view_change);
            intent_view_change.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }
    }

}
