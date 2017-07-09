package be.mneli.rushhour;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "WELCOME_AUTH";
    private Button btnWelcomeLogin;
    private Button btnWelcomeRegister;
    private Button btnWelcomeGuest;
    private EditText etWelcomeMail;
    private EditText etWelcomePassword;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
    }

    private void initView() {
        btnWelcomeLogin = (Button) findViewById(R.id.btn_welcome_login);
        btnWelcomeRegister = (Button) findViewById(R.id.btn_welcome_register);
        btnWelcomeGuest = (Button) findViewById(R.id.btn_welcome_guest);
        etWelcomeMail = (EditText) findViewById(R.id.et_welcome_username);
        etWelcomePassword = (EditText) findViewById(R.id.et_welcome_password);

        btnWelcomeLogin.setOnClickListener(this);
        btnWelcomeRegister.setOnClickListener(this);
        btnWelcomeGuest.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_welcome_login:
                //TODO
                relaxGringo();
                signIn(getEtToString(etWelcomeMail), getEtToString(etWelcomePassword));
                break;
            case R.id.btn_welcome_register:
                //TODO
                relaxGringo();
                createAccount(getEtToString(etWelcomeMail), getEtToString(etWelcomePassword));
                break;
            case R.id.btn_welcome_guest:
                goToActivity(HomeActivity.class);
                break;
            default:
        }
    }

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            //Toast.makeText(EmailPasswordActivity.this, R.string.auth_failed,
                            //Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

    private void signIn(String email, String password) {
        if (!validateForm()) {
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            //Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                            // Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            //mStatusTextView.setText(R.string.auth_failed);
                        }
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = etWelcomeMail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            etWelcomeMail.setError("Required.");
            valid = false;
        } else {
            etWelcomeMail.setError(null);
        }

        String password = etWelcomePassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            etWelcomePassword.setError("Required.");
            valid = false;
        } else {
            etWelcomePassword.setError(null);
        }

        return valid;
    }

    private String getEtToString(EditText et) {
        return et.getText().toString();
    }


    private void relaxGringo() {
        Toast.makeText(this, getString(R.string.relax_gringo), Toast.LENGTH_SHORT).show();
    }

    private void goToActivity(Class activity) {
        Intent callIntent = new Intent(this, activity);
        startActivity(callIntent);
    }
}
