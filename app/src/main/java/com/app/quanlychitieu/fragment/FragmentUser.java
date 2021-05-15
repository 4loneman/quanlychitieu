package com.app.quanlychitieu.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.quanlychitieu.MainActivity;
import com.app.quanlychitieu.R;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentUser extends Fragment {
    LoginButton loginButton;
    TextView ten, mail, ns;
    ProfilePictureView profilePictureView;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        anhXa(view);
        return view;
    }

    public void anhXa(View view) {
        sharedPreferences = getContext().getSharedPreferences("profile", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        profilePictureView = view.findViewById(R.id.image);
        ten = view.findViewById(R.id.ten);
        mail = view.findViewById(R.id.mail);
        ns = view.findViewById(R.id.birday);
        getprofile();
        loginButton = view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setReadPermissions("public_profile");
        loginButton.setReadPermissions("email");
        loginButton.setReadPermissions("user_birthday");
        loginButton.registerCallback(MainActivity.callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                editor.putString("id", loginResult.getAccessToken().getUserId());
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    String email = object.getString("email");
                                    String birthday = object.getString("birthday");
                                    String name = object.getString("name");
                                    editor.putString("mail", email);
                                    editor.putString("sn", birthday);
                                    editor.putString("ten", name);
                                    editor.commit();
                                    getprofile();
                                } catch (Exception ex) {

                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        getprofile();

    }

    public void getprofile() {
        String mail = sharedPreferences.getString("mail", "mail");
        String birday = sharedPreferences.getString("sn", "ns");
        String name = sharedPreferences.getString("name", "");
        String pro = sharedPreferences.getString("id", "");
        profilePictureView.setProfileId(pro);
        ten.setText(name);
        ns.setText(birday);
        this.mail.setText(mail);
    }

    ;
}
