package be.mneli.rushhour;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnWelcomeLogin;
    private Button btnWelcomeRegister;
    private Button btnWelcomeGuest;
    private EditText etWelcomeMail;
    private EditText etWelcomePassword;


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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_welcome_login:
                //TODO
                relaxGringo();
                break;
            case R.id.btn_welcome_register:
                //TODO
                relaxGringo();
                break;
            case R.id.btn_welcome_guest:
                goToActivity(HomeActivity.class);
                break;
            default:
        }
    }

    private void relaxGringo() {
        Toast.makeText(this, getString(R.string.relax_gringo), Toast.LENGTH_SHORT).show();
    }

    private void goToActivity(Class activity) {
        Intent callIntent = new Intent(this, activity);
        startActivity(callIntent);
    }
}
