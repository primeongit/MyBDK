package id.go.kemenkeu.mybdk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText inEmail;
    private EditText inPass;
    private Button btnLogin;
    private ProgressDialog progressDialog;

    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

//        if(mAuth.getCurrentUser() != null){
////            finish();
////            startActivity(new Intent(getApplicationContext(), MainActivity.class));
////        }

        inEmail = (EditText) findViewById(R.id.inputNIP);
        inPass = (EditText) findViewById(R.id.inputPassword);
        btnLogin = (Button) findViewById(R.id.login);
        progressDialog = new ProgressDialog(this);

        progressDialog = new ProgressDialog(this);

        btnLogin.setOnClickListener(this);
    }
    private void signIn () {

        String email = inEmail.getText().toString().trim();
        String password = inPass.getText().toString();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Masukkan email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Masukkan password", Toast.LENGTH_SHORT).show();
        }

        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "sigIn:onComplete:" + task.isSuccessful());
                        progressDialog.dismiss();

                        if (task.isSuccessful()) {
                            Intent loginSuccess = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(loginSuccess);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            signIn();
        }
    }
}
