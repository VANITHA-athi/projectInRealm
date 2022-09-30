package com.example.projectinrealm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectinrealm.Helper.logRegModel;

import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;


public class RegisterPage extends AppCompatActivity {
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 6 characters
                    "$");
    EditText Email,pass,ConPass;
    Button signup;
    TextView text ,sText;
    Realm realm;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        realm=Realm.getDefaultInstance();
        setContentView(R.layout.activity_register_page);

        Email=findViewById(R.id.editText);
        pass=findViewById(R.id.editTextTextPersonName);
        ConPass=findViewById(R.id.editTextTextPassword);
        signup = findViewById(R.id.button);
        text = findViewById(R.id.text1);
        sText=findViewById(R.id.sText);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=Email.getText().toString();
                String pas=pass.getText().toString();
                if (email.isEmpty()&& Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Email.setError("Enter the Email");
                    Email.requestFocus();
                    return;
                }if (pas.isEmpty()&&!PASSWORD_PATTERN.matcher(pas).matches()){
                    pass.setError("Enter the password");
                    pass.requestFocus();
                    return;
                }if (pass.equals(ConPass)){
                    ConPass.setError("Enter the confirm password");
                    ConPass.requestFocus();
                    return;
                }
                RealmResults<logRegModel> models=realm.where(logRegModel.class).findAll();
                if (models.size()>0){
                    logRegModel regModel=models.get(models.size()-1);
                    id=regModel.getId()+1;
                    Log.v("id",String.valueOf(id));
                    registerStatus(id);
                }else {
                    id=1;
                    Log.e("id",String.valueOf(id));
                    registerStatus(id);
                }

            }
        });

        sText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmQuery<logRegModel> query=realm.where(logRegModel.class);
                RealmResults<logRegModel> models=realm.where(logRegModel.class).findAll();
                Log.e("list",models.toString());
                Toast.makeText(getApplicationContext(), models.get(0).getConfirmPassword(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerStatus(int id) {
        realm.beginTransaction();
        logRegModel model=realm.createObject(logRegModel.class);
        model.setId(id);
        model.setUsername(Email.getText().toString());
        model.setPassword(pass.getText().toString());
        model.setConfirmPassword(ConPass.getText().toString());
        realm.commitTransaction();
        Toast.makeText(getApplicationContext(), "register success", Toast.LENGTH_SHORT).show();
        RegisterPage.this.finish();
    }



}



