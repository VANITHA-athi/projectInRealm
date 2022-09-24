package com.example.projectinrealm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.projectinrealm.Helper.logRegModel;
import com.example.projectinrealm.Helper.sharedPreference;
import com.example.projectinrealm.events.ViewEvents;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    EditText logName,logPass;
    Button singIn;
    Realm realm;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logName=findViewById(R.id.editText2);
        logPass=findViewById(R.id.editText3);
        singIn=findViewById(R.id.button2);
        text=findViewById(R.id.text1);
        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterPage.class);
                startActivity(intent);
            }
        });
        singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (logName.getText().length()==0){
                    logName.setError("Enter Valid Email");
                    logName.requestFocus();
                    return;
                }if (logPass.getText().length()==0){
                    logPass.setError("Enter valid password");
                    logPass.requestFocus();
                    return;
                }
                loginValidate(logName.getText().toString(),logPass.getText().toString());

            }
        });

    }

    private void loginValidate(String username, String password) {
        realm=Realm.getDefaultInstance();
        RealmResults<logRegModel> regModels = realm.where(logRegModel.class).equalTo("username", username).findAll();
        if (regModels.size() > 0) {
            RealmResults<logRegModel> listPass = realm.where(logRegModel.class).equalTo("username", username).equalTo("password", password).findAll();
            if (listPass.size() > 0) {
                sharedPreference preference=new sharedPreference(MainActivity.this);
                preference.saveLoginCredenetial(username);
                Intent main = new Intent(getApplicationContext(), ViewEvents.class);
                main.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(main);
                MainActivity.this.finish();
            } else {
                Toast.makeText(this, "username&password not match", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "username Not matched", Toast.LENGTH_SHORT).show();
        }
    }



}








