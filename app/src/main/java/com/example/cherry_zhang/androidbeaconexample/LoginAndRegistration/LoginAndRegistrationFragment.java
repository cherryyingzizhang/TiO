package com.example.cherry_zhang.androidbeaconexample.LoginAndRegistration;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cherry_zhang.androidbeaconexample.R;
import com.example.cherry_zhang.androidbeaconexample.TabActivity.TabActivity;
import com.example.cherry_zhang.androidbeaconexample.TabActivity.TabConstants;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginAndRegistrationFragment extends android.support.v4.app.Fragment {

    //a custom asynctask for user login and registration
    private UserLoginTask mAuthTask = null;

    //boolean that switches between login screen or register screen. true = register, false = login
    boolean RegisterOrLogin = true;

    //linking the views on xml into the java code
    EditText ET_email, ET_password, ET_confirmPassword,ET_Name;
    Button B_loginOrRegister, B_switchBetweenLoginOrRegister;
    LinearLayout LL_loginForm;
    ProgressBar PV_progressBar;
    TextView TV_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize PARSE
        Parse.initialize(getActivity(), "TsVbzF7jXzY1C0o86V2xxAxgSxvy4jmbyykOabPl",
                "VzamwWm4WswbDFxrxos2oSerQ2Av4RM6J5mNnNgr");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section_login_and_registration, container, false);

        //linking the views
        ET_email = (EditText) rootView.findViewById(R.id.ET_email);
        ET_password = (EditText) rootView.findViewById(R.id.ET_password);
        ET_confirmPassword = (EditText) rootView.findViewById(R.id.ET_confirmPassword);
        ET_Name = (EditText) rootView.findViewById(R.id.editName);
        B_loginOrRegister = (Button) rootView.findViewById(R.id.B_loginOrRegister);
        B_switchBetweenLoginOrRegister = (Button) rootView.findViewById(R.id.B_switchBetweenLoginOrRegister);
        LL_loginForm = (LinearLayout) rootView.findViewById(R.id.LL_loginForm);
        PV_progressBar = (ProgressBar) rootView.findViewById(R.id.PV_progressBar);

//        TV_title = (TextView) rootView.findViewById(R.id.tv_Title);

        //TODO: separate
        // When user clicks the login or register button (it will login or register the user)
        B_loginOrRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (RegisterOrLogin) //register
                {
                    attemptRegistration();
                } else //login
                {
                    attemptLogin();
                }
            }
        });

        // Switching between Login or Register screen layouts
        B_switchBetweenLoginOrRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //switch between login and register views
                if (RegisterOrLogin) //currently in register view... switch to login view
                {
                    ET_confirmPassword.setVisibility(View.GONE);
                    ET_Name.setVisibility(View.GONE);
                    B_loginOrRegister.setText("Log In");
                    RegisterOrLogin = !RegisterOrLogin;
//                    TV_title.setText("Log In");
                    B_switchBetweenLoginOrRegister.setText("Sign Up");
                } else //currently in login view... switch to register view
                {
                    ET_Name.setVisibility(View.VISIBLE);
                    ET_confirmPassword.setVisibility(View.VISIBLE);
                    B_loginOrRegister.setText("Sign Up");
                    RegisterOrLogin = !RegisterOrLogin;
//                    TV_title.setText("Create an Account");
                    B_switchBetweenLoginOrRegister.setText("Log In");
                }
            }
        });

        return rootView;
    }

    //TODO: uncomment this later
//    private boolean isEmailValid(String email) {
//        //TODO: Replace this with better logic
//        return email.contains("@");
//    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with better logic
        return password.length() > 5 && password.matches(".*\\d+.*");
    }

    public void attemptRegistration()
    {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        ET_email.setError(null);
        ET_password.setError(null);

        // Store values at the time of the login attempt.
        String email = ET_email.getText().toString();
        String password = ET_password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password
        if (!isPasswordValid(password))
        {
            ET_password.setError
                    ("Invalid password. Passwords need:\n•at least 1 number\n•more than five characters");
            focusView = ET_password;
            cancel = true;
        }

        // Check for if both passwords entered is correct
        if (!ET_password.getText().toString().contentEquals(ET_confirmPassword.getText().toString()))
        {
            if (ET_password.getError() == null)
                ET_confirmPassword.setError("Passwords are not the same");
            focusView = ET_confirmPassword;
            cancel = true;
        }

        //TODO: change this later
        // Check for a valid email address.
//        if (TextUtils.isEmpty(email)) {
//            ET_email.setError(getString(R.string.error_field_required));
//            focusView = ET_email;
//            cancel = true;
//        } else if (!isEmailValid(email)) {
//            ET_email.setError(getString(R.string.error_invalid_email));
//            focusView = ET_email;
//            cancel = true;
//        }

        if (cancel)
        {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else
        {
            // Show a progress spinner, and kick off a background task to
            // perform the user registration attempt
            showProgress(true);
            //mAuthTask = new UserLoginTask(email, password);
            //mAuthTask.execute((Void) null);


            final ParseUser user = new ParseUser();
            user.setUsername(ET_email.getText().toString());
            user.setPassword(ET_password.getText().toString());
            user.setEmail(ET_email.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // Hooray! Registration successful
                        Toast.makeText(getActivity().getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT)
                                .show();
                        // TODO: Change this intent or even erase it and do something else after registration successful
                        Intent goToApplication = new Intent(getActivity(),TabActivity.class);
                        goToApplication.putExtra(TabConstants.FREELANCE_OR_COMPANY_KEY,TabConstants.FOR_FREELANCE);
                        startActivity(goToApplication);
                        showProgress(false);
                        getActivity().finish();
                    } else {
                        // Sign up didn't succeed. Look at the ParseException
                        // to figure out what went wrong
                        Toast.makeText(getActivity().getApplicationContext(), "Signup unsuccessful: " + e.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                        Log.e("LoginAndRegistration",e.getMessage());
                        showProgress(false);
                    }
                }
            });
        }
    }

    public void attemptLogin()
    {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        ET_email.setError(null);
        ET_password.setError(null);

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!isPasswordValid(ET_password.getText().toString()))
        {
            ET_password.setError("Invalid password.");
            focusView = ET_password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(ET_email.getText().toString())) {
            ET_email.setError(getString(R.string.error_field_required));
            focusView = ET_email;
            cancel = true;
        }
        //TODO: omg uncomment
//      else if (!isEmailValid(ET_email.getText().toString())) {
//            ET_email.setError(getString(R.string.error_invalid_email));
//            focusView = ET_email;
//            cancel = true;
//        }

        if (cancel)
        {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else
        {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(ET_email, ET_password);
            mAuthTask.execute((Void) null);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    //As of right now this class is used only once, within this Fragment.
    //If it becomes used again we will make it generic and refactor it.
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final EditText mEmail;
        private final EditText mPassword;

        UserLoginTask(EditText email, EditText password) {
            mEmail = email;
            mPassword = password;
        }

        boolean returnStatement = true;

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            ParseUser.logInInBackground(mEmail.getText().toString(), mPassword.getText().toString(), new LogInCallback() {
                public void done(ParseUser user, ParseException e) {
                    if (user != null) {
                        // Hooray! User login success.




                        Intent successAndLogIn = new Intent(getActivity(), TabActivity.class);
                        successAndLogIn.putExtra(TabConstants.FREELANCE_OR_COMPANY_KEY,TabConstants.FOR_FREELANCE);
                        startActivity(successAndLogIn);
                        getActivity().finish();
                        showProgress(false);
                        LoginAndRegistrationActivity.pageIndicator.setVisibility(View.GONE);

                        Toast.makeText(getActivity().getApplicationContext(), "Login successful", Toast.LENGTH_SHORT)
                                .show();

                        returnStatement = true;
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Login unsuccessful" + e, Toast.LENGTH_SHORT)
                                .show();
                        showProgress(false);
                        returnStatement = false;
                    }
                }
            });

            return returnStatement;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            // TODO: change the getactivity.finish thing
            if (success) {
                //getActivity().finish();
            } else {
                mPassword.setError(getString(R.string.error_incorrect_password));
                mPassword.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.

        //TODO
        //com.example.cherry_zhang.juver.Activities.Application.LoginAndRegistration.LoginAndRegistrationActivity.pageIndicator.setVisibility(show ? View.GONE : View.VISIBLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            LoginAndRegistrationActivity.pageIndicator.setVisibility(show ? View.GONE : View.VISIBLE);

            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            LL_loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
            LL_loginForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    LL_loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            PV_progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            PV_progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    PV_progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            PV_progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            LL_loginForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
