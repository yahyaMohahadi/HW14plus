package com.example.hw14.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.hw14.database.WordRepository;

public class AlertLoadWord extends DialogFragment {

    public static AlertLoadWord newInstance() {
        AlertLoadWord add = new AlertLoadWord();

        return add;
    }



    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        builder.setTitle("Add all persian_english word to dictiounary ?");
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, new Intent());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        return builder.create();
    }
}


