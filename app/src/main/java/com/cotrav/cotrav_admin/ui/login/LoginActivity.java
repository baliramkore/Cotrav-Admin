package com.cotrav.cotrav_admin.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cotrav.cotrav_admin.MainActivity;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.model.login_model.UserViewModel;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{


    EditText emalIdEditText,passwordEdditText,otp;
    TextView resendOtp;
    private static String TAG="LoginActivity";
    Button loginBtn;
    UserViewModel userViewModel;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    LinearLayout otp_lay;
    TextView loginWithDifferentAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        progressDialog = new ProgressDialog(LoginActivity.this);

        loginWithDifferentAcc = findViewById(R.id.login_with_different_acc);
        resendOtp = findViewById(R.id.resendOtp);
        otp = findViewById(R.id.otp);
        otp_lay = findViewById(R.id.otp_lay);
        otp_lay.setVisibility(View.GONE);
        userViewModel= ViewModelProviders.of(this).get(UserViewModel.class);

        if (userViewModel.isLogin()) {
            Log.d(TAG,"access_token "+userViewModel.getAccessToken().getValue()+"  "+Boolean.toString(userViewModel.isLogin()));
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }else {
            Log.d(TAG,"LOGIN ACTIVITY STARTED");
            //Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
            // startActivity(intent);
        }

        userViewModel.getLoginSuccessful().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if (s.equals("Successful")){
                    progressBar.setVisibility(View.GONE);
                    //progressDialog.dismiss();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    LoginActivity.this.finish();
                }
                if (s.equals("Otp sent Successfully")){
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,"Otp sent successfully",Toast.LENGTH_LONG).show();
                    //Toasty.success(LoginActivity.this,"Otp sent successfully").show();
                    otp_lay.setVisibility(View.VISIBLE);
                    loginWithDifferentAcc.setVisibility(View.VISIBLE);
                    loginBtn.setText("Verify");

                    emalIdEditText.setFocusable(false);

                    passwordEdditText.setFocusable(false);

                    final Dialog dialog = new Dialog(LoginActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.verify_otp_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    final TextView dialogOtp  = dialog.findViewById(R.id.edt_text_otp);
                    TextView mYes = dialog.findViewById(R.id.yes_txt);
                    mYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (dialogOtp.getText().toString().length()==6){
                                otp.setText(dialogOtp.getText().toString());
                                dialog.dismiss();
                            }
                            else
                                dialogOtp.setError("Enter 6 digit Otp");
                        }
                    });
                    Button mNo = dialog.findViewById(R.id.no_txt);
                    mNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });


                    dialog.show();
                }
                if (s.equals("RESET_DATA")){
                    emalIdEditText.setText("");
                    emalIdEditText.setFocusable(true);
                    emalIdEditText.setFocusableInTouchMode(true);
                    emalIdEditText.clearFocus();
                    passwordEdditText.setText("");
                    passwordEdditText.setFocusable(true);
                    passwordEdditText.setFocusableInTouchMode(true);
                    passwordEdditText.clearFocus();


                    loginBtn.setText("LOGIN");
                    loginWithDifferentAcc.setVisibility(View.GONE);
                    resendOtp.setVisibility(View.GONE);
                    otp_lay.setVisibility(View.GONE);
                }
            }
        });

        userViewModel.getError().observe(this,  new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                progressBar.setVisibility(View.GONE);
                //progressDialog.dismiss();
                if (s.equals("")){
                    Toast.makeText(LoginActivity.this, s+"", Toast.LENGTH_SHORT).show();
                }else {
                    if (s.equals("0")){

                        final Dialog dialog = new Dialog(LoginActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        TextView mDialogtittle = dialog.findViewById(R.id.dialog_title);
                        mDialogtittle.setVisibility(View.GONE);
                        mDialogmsg.setText("Check Credentials");
                        TextView mYes = dialog.findViewById(R.id.yes_txt);
                        mYes.setText("Okey");
                        mYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        Button mNo = dialog.findViewById(R.id.no_txt);
                        mNo.setVisibility(View.GONE);

                        dialog.show();

                    }
                    if (s.equals("Connection Failed")){

                        final Dialog dialog = new Dialog(LoginActivity.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCancelable(false);
                        dialog.setContentView(R.layout.custom_dialog_box);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                        TextView mDialogtittle = dialog.findViewById(R.id.dialog_title);
                        mDialogtittle.setVisibility(View.GONE);
                        mDialogmsg.setText("Server Connection Failed");
                        TextView mYes = dialog.findViewById(R.id.yes_txt);
                        mYes.setText("Okey");
                        mYes.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        Button mNo = dialog.findViewById(R.id.no_txt);
                        mNo.setVisibility(View.GONE);



                        dialog.show();
                    }
                    else
                        Toast.makeText(LoginActivity.this, s+"", Toast.LENGTH_SHORT).show();

                }
                Toast.makeText(LoginActivity.this, s+"", Toast.LENGTH_SHORT).show();
            }
        });


        emalIdEditText = findViewById(R.id.emailId);
        passwordEdditText = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginBtn);
        emalIdEditText.setBackgroundColor(Color.TRANSPARENT);
        passwordEdditText.setBackgroundColor(Color.TRANSPARENT);
        loginBtn.setOnClickListener(this);
        resendOtp.setOnClickListener(this);
        loginWithDifferentAcc.setOnClickListener(this);


    }





    @Override
    public void onClick(View view) {
        int id  = view.getId();

        switch (id){
            case R.id.loginBtn:
                if (loginBtn.getText().toString().equals("LOGIN")){

                    if(emalIdEditText.getText().toString().equals(""))
                    {

                        emalIdEditText.setError("Enter Email ID");
                    }
                    if(passwordEdditText.getText().toString().equals(""))
                    {
                        passwordEdditText.setError("Enter Password");
                    }
                    if (emalIdEditText.getText().toString().length()>1&&passwordEdditText.getText().toString().length()>1)
                    {
                        progressBar.setVisibility(View.VISIBLE);
                        emalIdEditText.setError(null);
                        passwordEdditText.setRawInputType(InputType.TYPE_CLASS_TEXT);
                        //progressDialog.setMessage("Checking Credentials");
                        //progressDialog.show();
                        userViewModel.performLogin(emalIdEditText.getText().toString(),passwordEdditText.getText().toString());


                    }
                    else Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
                if (loginBtn.getText().toString().equals("Verify"))
                {
                    if (otp.getText().toString().length()==6)
                    {
                        Toast.makeText(this, "verifing", Toast.LENGTH_SHORT).show();
                        userViewModel.verifyCode(otp.getText().toString());
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"Check OTP",Toast.LENGTH_LONG).show();
                        //Toasty.error(this, "Check Otp", Toast.LENGTH_SHORT).show();
                        otp.setError("check 6 digit OTP");
                    }
                }
                break;
            case R.id.resendOtp:
                progressBar.setVisibility(View.VISIBLE);
                userViewModel.performLogin(emalIdEditText.getText().toString(),passwordEdditText.getText().toString());
                break;
            case R.id.login_with_different_acc:
                userViewModel.reSetText();

                break;

        }

    }

    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 1) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }

    }
}
