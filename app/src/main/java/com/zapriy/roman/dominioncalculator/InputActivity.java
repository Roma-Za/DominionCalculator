package com.zapriy.roman.dominioncalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.squareup.picasso.Picasso;
import com.zapriy.roman.dominioncalculator.Entity.ArrUnits;
import com.zapriy.roman.dominioncalculator.Entity.Unit;

import java.util.List;

public class InputActivity extends AppCompatActivity implements Validator.ValidationListener {
    @NotEmpty
    private EditText editText;
    private Button button;
    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        Intent intent = getIntent();
        Boolean isUse = intent.getBooleanExtra("isUseMeat", false);
        int idx = intent.getIntExtra(Consts.UNIT_IDX, 0);
        ArrUnits arrUnits = new ArrUnits(isUse);
        Unit unit = arrUnits.getUnits().get(idx);
        ImageView imageView = (ImageView)findViewById(R.id.imageViewInput);
        Picasso.with(this)
                .load(unit.getIcon())
                .placeholder(R.drawable.ic_menu_gallery)
                .resize(110, 130)
                .into(imageView);
        TextView textView = (TextView)findViewById(R.id.textViewInput);
        textView.setText(unit.getName());
        validator = new Validator(this);
        validator.setValidationListener(this);
        editText = (EditText)findViewById(R.id.edit_amount);
        button = (Button)findViewById(R.id.btn_input_amount);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        int amount = Integer.valueOf(editText.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(Consts.AMOUNT, amount);
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
