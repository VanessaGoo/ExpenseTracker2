package sg.edu.rp.c346.expensetracker2;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SubstractExpense extends AppCompatActivity {

    EditText etmoney, etcategory, etdate, etdescription;
    Button btnSave, btnCancel;
    FirebaseFirestore db;
    ImageView ivDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substract_expense);

        db = FirebaseFirestore.getInstance();

        etmoney = findViewById(R.id.money);
        etcategory = findViewById(R.id.etCategory);
        etdate = findViewById(R.id.etDescription);
        etdescription = findViewById(R.id.etDate);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);
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

                DatePickerDialog myDialog = new DatePickerDialog(SubstractExpense.this, myDate, mYear, mMonth, mDay);
                myDialog.show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent l = new Intent(SubstractExpense.this, ShowExpenses.class);
                startActivity(l);

                // Intent for tv Balance
                Intent i = new Intent(SubstractExpense.this, MainActivity.class);
                int moneyIntent = Integer.parseInt(etmoney.getText().toString());
                i.putExtra("dollor", moneyIntent);
                startActivity(i);

                final String money2 = etmoney.getText().toString().trim();
                final String category2 = etcategory.getText().toString().trim();
                final String date2 = etdate.getText().toString().trim();
                final String description2 = etdescription.getText().toString().trim();
                final Map<String, String> expense = new HashMap<>();
                expense.put("money", money2);
                expense.put("category", category2);
                expense.put("date", date2);
                expense.put("description", description2);


                if (money2.isEmpty() || category2.isEmpty() || date2.isEmpty() || description2.isEmpty()) {
                    Toast.makeText(SubstractExpense.this, "All Fields are required!", Toast.LENGTH_LONG).show();
                } else {

                    CollectionReference dbExpense = db.collection("expense");
                    dbExpense.add(expense)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(SubstractExpense.this, "Expense Added", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SubstractExpense.this, e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent l = new Intent(
                        SubstractExpense.this, MainActivity.class);
                startActivity(l);
            }
        });
    }
}


