package sg.edu.rp.c346.expensetracker2;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddExpense extends AppCompatActivity {

    EditText etmoney, etcategory, etdate, etdescription;
    TextView tvBalance;
    Button btnSave, btnCancel;
    FirebaseFirestore db;
    ImageView ivDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        db = FirebaseFirestore.getInstance();

        etmoney = findViewById(R.id.money);
        etcategory = findViewById(R.id.etCategory);
        etdescription = findViewById(R.id.etDescription);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
        etdate = findViewById(R.id.etDate);
        tvBalance = findViewById(R.id.tvBalance);
        ivDate = findViewById(R.id.ivDate);

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatePickerDialog.OnDateSetListener myDate = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etdate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                };
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog myDialog = new DatePickerDialog(AddExpense.this, myDate, mYear, mMonth, mDay);
                myDialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent for tv Balance
                Intent i = new Intent();
                int moneyIntent = Integer.parseInt(etmoney.getText().toString());
                i.putExtra("dollor", moneyIntent);
                setResult(1, i);
                finish();

                final String money2 = etmoney.getText().toString().trim();
                final String category2 = etcategory.getText().toString().trim();
                final String date2 = etdate.getText().toString().trim();
                final String description2 = etdescription.getText().toString().trim();
                final EditText date = findViewById(R.id.etDate);

                final Map<String, String> expense = new HashMap<>();
                expense.put("money", money2);
                expense.put("category", category2);
                expense.put("date", date2);
                expense.put("description", description2);

                if (money2.isEmpty() || category2.isEmpty() || date2.isEmpty() || description2.isEmpty()) {
                    Toast.makeText(AddExpense.this, "All Fields are required!", Toast.LENGTH_LONG).show();
                } else {

                    CollectionReference dbExpense = db.collection("expense");
                    dbExpense.add(expense)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(AddExpense.this, "Expense Added", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddExpense.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
                }
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(AddExpense.this, MainActivity.class);
                startActivity(l);
                finish();
            }
        });
    }
}