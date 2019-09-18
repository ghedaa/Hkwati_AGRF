package sa.elm.hakuati.toolbar_mainactivity.storyActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import sa.elm.hakuati.toolbar_mainactivity.R;
import sa.elm.hakuati.toolbar_mainactivity.mainActivity.MainActivity;
import sa.elm.hakuati.toolbar_mainactivity.profileActivity.ProfileActivity;

import static android.content.ContentValues.TAG;

public class LoginActivity extends Activity implements View.OnClickListener{

    private FirebaseAuth auth;

    private EditText emailEditText,passwordEditText;
    private Button loginButton;
    private SignInButton googleSignInButton;
    private GoogleSignInClient googleSignInClient;
    private TextView forgetPass;
    private TextView createAccount;
    private EditText emailET;
    private EditText passwordED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        forgetPass = findViewById(R.id.forgetText);
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Forget account");
                final AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                alert.setTitle(getString(R.string.enteryourEmail));
                LinearLayout layout = new LinearLayout(LoginActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                final EditText messageIN = new EditText(LoginActivity.this);
                layout.addView(messageIN);
                alert.setView(layout);
                // Set up the buttons
                alert.setPositiveButton(getString(R.string.YES), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (isValidEmail(messageIN.getText().toString()) && messageIN != null) {

                            dialog.dismiss();

                            FirebaseAuth.getInstance().sendPasswordResetEmail(messageIN.getText().toString())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "Email sent.");
                                            }
                                        }
                                    });

                        } else {
                            messageIN.setError("Please wait a moment (:");

                        }
                    }
                });
                alert.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });//end onClick


        createAccount = findViewById(R.id.createAccountText);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);

            }//end onClick
        });

        loginButton = findViewById(R.id.loginbutton_login);
        emailET = findViewById(R.id.email_login);
        passwordED = findViewById(R.id.password_login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (isValidEmail(emailET.getText().toString())&& passwordED.getText().toString() != "") {

                    Toast.makeText(getApplicationContext(), "Success :)", Toast.LENGTH_LONG).show();



                }//end if
                else {
                    Toast.makeText(getApplicationContext(), "Filled :(", Toast.LENGTH_LONG).show();
                }

            }
        });



        //Views
        findViews();
        //fireBase
        initFirebase();

        googleSignInButton = findViewById(R.id.sign_in_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });



    }//end onCreate


    private void initFirebase() {
        auth = FirebaseAuth.getInstance();
    }//end initFirebase

    public void findViews(){
        emailEditText=(EditText)findViewById(R.id.email_login);
        passwordEditText=(EditText)findViewById(R.id.password_login);
        loginButton=(Button)findViewById(R.id.loginbutton_login);
    }//end findViews


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginbutton_login:
                if(isValidEmail(emailEditText.getText().toString()))
                    login();
                break;

        }
    }//end onClick

    private void login() {
        auth.signInWithEmailAndPassword(emailEditText.getText().toString(),passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        task.getException();
                        if(task.isSuccessful()){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                        else {
                            Toast.makeText(LoginActivity.this,"Not Successfull Signing",Toast.LENGTH_LONG);
                        }
                    }
                });
    }//end login
    public boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }// end isValid


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                        Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }
    }

    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(ProfileActivity.GOOGLE_ACCOUNT, googleSignInAccount);

        startActivity(intent);
        finish();
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (alreadyloggedAccount != null) {
            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
            onLoggedIn(alreadyloggedAccount);
        } else {
            Log.d(TAG, "Not logged in");
        }
    }

}//end Class

