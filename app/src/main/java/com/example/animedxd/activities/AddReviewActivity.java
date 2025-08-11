package com.example.animedxd.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.animedxd.R;

public class AddReviewActivity extends DialogFragment {

    public interface ReviewListener {
        void onReviewSubmitted(String username, String review);
    }

    private ReviewListener listener;

    public void setReviewListener(ReviewListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.CustomDialog);
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_review, null);

        EditText etUsername = view.findViewById(R.id.etUsername);
        EditText etReview = view.findViewById(R.id.etReview);
        Button btnCancel = view.findViewById(R.id.btnCancel);
        Button btnSave = view.findViewById(R.id.btnSave);

        btnCancel.setOnClickListener(v -> dismiss());

        btnSave.setOnClickListener(v -> {
            if (listener != null) {
                String username = etUsername.getText().toString().trim();
                String review = etReview.getText().toString().trim();
                listener.onReviewSubmitted(username, review);
            }
            dismiss();
        });

        builder.setView(view);
        return builder.create();
    }
}
