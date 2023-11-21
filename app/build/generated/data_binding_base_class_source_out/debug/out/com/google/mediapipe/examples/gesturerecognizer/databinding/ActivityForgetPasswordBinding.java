// Generated by view binder compiler. Do not edit!
package com.google.mediapipe.examples.gesturerecognizer.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.mediapipe.examples.gesturerecognizer.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityForgetPasswordBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextInputEditText email;

  @NonNull
  public final TextInputLayout inputemail;

  @NonNull
  public final MaterialButton resetpasswordbtn;

  @NonNull
  public final TextView txtforgetpassword;

  private ActivityForgetPasswordBinding(@NonNull RelativeLayout rootView,
      @NonNull TextInputEditText email, @NonNull TextInputLayout inputemail,
      @NonNull MaterialButton resetpasswordbtn, @NonNull TextView txtforgetpassword) {
    this.rootView = rootView;
    this.email = email;
    this.inputemail = inputemail;
    this.resetpasswordbtn = resetpasswordbtn;
    this.txtforgetpassword = txtforgetpassword;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityForgetPasswordBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityForgetPasswordBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_forget_password, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityForgetPasswordBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.email;
      TextInputEditText email = ViewBindings.findChildViewById(rootView, id);
      if (email == null) {
        break missingId;
      }

      id = R.id.inputemail;
      TextInputLayout inputemail = ViewBindings.findChildViewById(rootView, id);
      if (inputemail == null) {
        break missingId;
      }

      id = R.id.resetpasswordbtn;
      MaterialButton resetpasswordbtn = ViewBindings.findChildViewById(rootView, id);
      if (resetpasswordbtn == null) {
        break missingId;
      }

      id = R.id.txtforgetpassword;
      TextView txtforgetpassword = ViewBindings.findChildViewById(rootView, id);
      if (txtforgetpassword == null) {
        break missingId;
      }

      return new ActivityForgetPasswordBinding((RelativeLayout) rootView, email, inputemail,
          resetpasswordbtn, txtforgetpassword);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}