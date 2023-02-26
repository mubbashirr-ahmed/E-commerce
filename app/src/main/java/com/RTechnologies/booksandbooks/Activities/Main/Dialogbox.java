package com.RTechnologies.booksandbooks.Activities.Main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.RTechnologies.booksandbooks.R;

public class Dialogbox extends AppCompatDialogFragment {



    private EditText password;
    private EditText confirmPass;
    private ExampleDialogListener listener;




    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        password = view.findViewById(R.id.edit_password);
        confirmPass=view.findViewById(R.id.edit_Confirmpassword);
//        editTextUsername.setText(keyword);


        builder.setView(view)
                .setTitle("Create a password")

                .setPositiveButton("NEXT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      if(password.getText().toString().equals(confirmPass.getText().toString())) {
                          String pass = password.getText().toString();
                          listener.applyTexts(pass);
                      }
                    }
                });




        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyTexts(String pass);
    }

}
