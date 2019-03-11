package com.example.subadivya.ambulanceservices;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView email;
    private Button signOut;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    TextView editTextName, editTextName1,editTextName2,editTextName3,editTextName4,editTextName5,editTextName6;
    Button buttonAdd;
    Spinner spinnersOpt, spinnersOpt1,spinnersOpt2,spinnersOpt3,spinnersOpt4;
    EditText edit,edit1;
    DatabaseReference ref;
    FirebaseDatabase database;
    questions q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle(getString(R.string.app_name));
        //setSupportActionBar(toolbar);
        //get firebase auth instance

        ref = FirebaseDatabase.getInstance().getReference("questions");
        q = new questions();
        editTextName = (TextView) findViewById(R.id.editTextName);
        editTextName1 = (TextView) findViewById(R.id.editTextName1);
        editTextName2= (TextView) findViewById(R.id.editTextName2);
        editTextName3 =(TextView) findViewById(R.id.editTextName3);
        editTextName4 = (TextView) findViewById(R.id.editTextName4);


        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        spinnersOpt = (Spinner) findViewById(R.id.spinnersOpt);
        spinnersOpt1 = (Spinner) findViewById(R.id.spinnersOpt1);
        spinnersOpt2 = (Spinner) findViewById(R.id.spinnersOpt2);
        spinnersOpt3 = (Spinner) findViewById(R.id.spinnersOpt3);
        spinnersOpt4 = (Spinner) findViewById(R.id.spinnersOpt4);

         edit = (EditText)findViewById(R.id.editText);
         edit1 = (EditText) findViewById(R.id.editText1);





        auth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));


                    finish();
                }
            }
        };

        signOut = (Button) findViewById(R.id.sign_out);
        email = (TextView) findViewById(R.id.email);

        email.setText(user.getEmail());
        //String username=user.getEmail();
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });



        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues();
                ref.child(user.getUid()).setValue(q);
                Toast.makeText(MainActivity.this, "Successfull", Toast.LENGTH_SHORT).show();


            }
        });


    }

    //sign out method
    public void getValues() {
        q.setAnswer1(spinnersOpt.getSelectedItem().toString());
        q.setAnswer2(spinnersOpt1.getSelectedItem().toString());
        q.setAnswer3(spinnersOpt2.getSelectedItem().toString());
        q.setAnswer4(spinnersOpt3.getSelectedItem().toString());
        q.setAnswer5(spinnersOpt4.getSelectedItem().toString());
        q.setAnswer6(edit.getText().toString());
        q.setAnswer7(edit1.getText().toString());

    }

    public void signOut() {
        auth.signOut();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}