package com.example.quananvat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quananvat.obj.NguoiDung;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private ImageView iv_back;
    private RelativeLayout btn_gg, btn_fb;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    public static int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        mapping();
        iv_back.setOnClickListener(v -> {
            onBackPressed();
        });
        loginWithGoogle();

        loginWithFacebook();
    }



    private void loginWithFacebook() {
        FacebookSdk.sdkInitialize(LoginActivity.this);
//        AppEventsLogger.activateApp(LoginActivity.this);
        mCallbackManager = CallbackManager.Factory.create();
        btn_fb.setOnClickListener(v -> {

            LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
            LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    handleFacebookAccessToken(loginResult.getAccessToken());
                }

                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {

                }
            });
        });

    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser mUser = mAuth.getCurrentUser();
                            DatabaseReference data = FirebaseDatabase.getInstance().getReference();
                            data.child("nguoi_dung").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (!snapshot.hasChild(mUser.getUid())){
                                        NguoiDung nguoiDung = new NguoiDung();
                                        nguoiDung.setTennguoidung(mUser.getDisplayName());
                                        nguoiDung.setSodienthoai(mUser.getPhoneNumber()+"");
                                        nguoiDung.setEmail(mUser.getEmail());
                                        nguoiDung.setHinhanh(mUser.getPhotoUrl().toString()+"?height=500");
                                        nguoiDung.setLoaidangnhap("facebook");
                                        nguoiDung.setUID(mUser.getUid());

                                        nguoiDung.setTrangthai(true);
                                        data.child("nguoi_dung").child(mUser.getUid()).setValue(nguoiDung);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void loginWithGoogle() {
        createRequest();
        btn_gg.setOnClickListener(v -> {
            signIn();
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void createRequest(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                // ...
            }
        }


    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            FirebaseUser mUser = mAuth.getCurrentUser();
                            DatabaseReference data = FirebaseDatabase.getInstance().getReference();
                            data.child("nguoi_dung").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (!snapshot.hasChild(mUser.getUid())){
                                        NguoiDung nguoiDung = new NguoiDung();
                                        nguoiDung.setTennguoidung(mUser.getDisplayName());
                                        nguoiDung.setSodienthoai(mUser.getPhoneNumber()+"");
                                        nguoiDung.setEmail(mUser.getEmail());
                                        nguoiDung.setHinhanh(mUser.getPhotoUrl().toString());
                                        nguoiDung.setLoaidangnhap("google");
                                        nguoiDung.setUID(mUser.getUid());
                                        nguoiDung.setTrangthai(true);
                                        data.child("nguoi_dung").child(mUser.getUid()).setValue(nguoiDung);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void mapping(){
        iv_back = (ImageView) findViewById(R.id.iv_back);
        btn_gg = (RelativeLayout) findViewById(R.id.btn_gg);
        btn_fb = (RelativeLayout) findViewById(R.id.btn_fb);
    }
}