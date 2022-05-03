package com.example.ht;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ht.user.User;
import com.example.ht.user.UserManager;

/**
 * A fragment in which the user can see their personal data
 */
public class ManageUserFragment extends Fragment {

    private User user;


    public ManageUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserManager userManager = UserManager.getUserManager();
        user = userManager.getCurrentUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_user, container, false);


        /*
        displays all user data onto the screen
        in reality NEVER DO THIS WITH A PASSWORD, more specifically UNENCRYPTED PASSWORD
         */
        TextView manage_username = view.findViewById(R.id.manage_username);
        TextView manage_password = view.findViewById(R.id.manage_password);
        TextView manage_firstname = view.findViewById(R.id.manage_firstname);
        TextView manage_lastmame = view.findViewById(R.id.manage_lastname);
        TextView manage_email = view.findViewById(R.id.manage_email);

        manage_username.setText(getString(R.string.manage_username) + user.getUsername());
        manage_password.setText(getString(R.string.manage_password) + user.getPassword());
        manage_firstname.setText(getString(R.string.manage_firstname) + user.getFirstName());
        manage_lastmame.setText(getString(R.string.manage_lastname) + user.getLastName());
        manage_email.setText(getString(R.string.manage_email) + user.getEmail());

        return view;
    }
}