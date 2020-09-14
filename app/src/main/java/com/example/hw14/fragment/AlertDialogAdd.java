package com.example.hw14.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.hw14.R;

public class AlertDialogAdd extends DialogFragment {
    private static Callbacks sCallbacks;
    EditText mEditTextAddRight;
    EditText mEditTextAddLeft;

    public static AlertDialogAdd newInstance(@NonNull Callbacks callbacks) {
        sCallbacks = callbacks;
        return new AlertDialogAdd();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.alart_add_new_word, null);
        builder.setTitle("Add new word");
        builder.setIcon(R.drawable.ic_action_new);
        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sCallbacks.setWord(
                        mEditTextAddRight.getText().toString(),
                        mEditTextAddLeft.getText().toString()
                );
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        findView(view);
        return builder.create();
    }

    private void findView(View view) {
        mEditTextAddLeft = view.findViewById(R.id.editText_add_left);
        mEditTextAddRight = view.findViewById(R.id.editText_add_right);
    }

    public interface Callbacks {
        void setWord(String stringRight, String stringLeft);
    }
}
