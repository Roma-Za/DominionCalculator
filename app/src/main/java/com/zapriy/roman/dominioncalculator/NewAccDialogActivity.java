package com.zapriy.roman.dominioncalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class NewAccDialogActivity extends AppCompatActivity implements Validator.ValidationListener {

    @NotEmpty
    private EditText editText;
    private Button button;
    private Validator validator;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        validator = new Validator(this);
        validator.setValidationListener(this);
        editText = (EditText)findViewById(R.id.editTextPercent);
        checkBox = (CheckBox)findViewById(R.id.checkBoxIsUseMeat);
        button = (Button)findViewById(R.id.button_new_acc_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        int percent = Integer.valueOf(editText.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(Consts.PERCENT, percent);
        boolean isUseMeat = checkBox.isChecked();
        intent.putExtra(Consts.START_IS_USE_MEAT, isUseMeat);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
