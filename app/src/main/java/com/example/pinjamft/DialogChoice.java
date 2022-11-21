package com.example.pinjamft;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogChoice extends DialogFragment {

    int position;
    SharedPreferences settings;

    public interface DialogChoiceListener{
        boolean onOptionsItemSelected(MenuItem item);

        void onPositiveButtonClicked(String[] list, int position);
        void onNegativeButtonClicked();
    }

    DialogChoiceListener mListener;

    @Override
    public void onAttach(Context context) {
        settings = context.getSharedPreferences("currentPosition", 0);
        position = settings.getInt("currentPosition", 0);

        super.onAttach(context);

        try {
            mListener = (DialogChoiceListener) context;
        }catch (Exception e){
            throw new ClassCastException(getActivity().toString()+"viewData");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final String[] list = getActivity().getResources().getStringArray(R.array.choice_sort);

        builder.setTitle("Urut Bedasarkan")
                .setSingleChoiceItems(list, position, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        position = i;
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        mListener.onPositiveButtonClicked(list, position);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("currentPosition", position);
                        editor.apply();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        mListener.onNegativeButtonClicked();
                    }
                });
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_BACK))
                {
                    dismiss();
                    return true;
                }
                else return false;
            }
        });
    }
}
