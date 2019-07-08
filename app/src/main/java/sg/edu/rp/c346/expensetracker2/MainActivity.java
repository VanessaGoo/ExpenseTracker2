package sg.edu.rp.c346.expensetracker2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.spec.ECField;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView tvMoney;
    ImageView ivAdd;
    ImageView ivSubtract;
    TextView tvBalance;
    // EditText etMoney, etCategory, etDate, etDescription;
    Button btnShow;
    FirebaseFirestore db;
    int balance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        tvMoney = findViewById(R.id.tvMoney);
        ivAdd = findViewById(R.id.ivAdd);
        ivSubtract = findViewById(R.id.ivSubtract);
        tvBalance = findViewById(R.id.tvBalance);
        btnShow = findViewById(R.id.buttonShow);

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(getBaseContext(), ShowExpenses.class);
                startActivity(j);
            }
        });

        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(MainActivity.this, AddExpense.class);
                startActivity(l);

            }
        });
        ivSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(MainActivity.this, SubstractExpense.class);
                startActivity(l);
            }
        });
    }
        @Override
        protected void onResume() {
            super.onResume();
            Intent intentRecived = getIntent();
            int dollar = intentRecived.getIntExtra("dollor", 0);
            //   int dollars = Integer.parseInt(dollar);
            balance += Integer.parseInt(tvBalance.getText().toString()) + dollar;

            tvBalance.setText(String.valueOf(balance));

    }
}
