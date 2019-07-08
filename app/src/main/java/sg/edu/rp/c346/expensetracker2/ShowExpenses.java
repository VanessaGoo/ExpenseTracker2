package sg.edu.rp.c346.expensetracker2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.util.ExecutorEventListener;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class ShowExpenses extends AppCompatActivity {

    ListView lv;
    ArrayAdapter aa;
    ArrayList<Expense> expenses;
    TextView money, date, cat, desc;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    FirebaseFirestore mFirestore;
    RecyclerView mMainList;
    private static final String TAG = "FireLog";
    private List<Expense> expenseList;
    private ExpenseListAdapter expenseListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        expenseList = new ArrayList<>();
        expenseListAdapter = new ExpenseListAdapter(expenseList);
        db = FirebaseFirestore.getInstance();

        mMainList = (RecyclerView) findViewById(R.id.main_list);
        mMainList.setHasFixedSize(true);
        mMainList.setLayoutManager(new LinearLayoutManager(this));
        mMainList.setAdapter(expenseListAdapter);

        mFirestore = FirebaseFirestore.getInstance();

        mFirestore.collection("expense").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.d(TAG, "Error :" + e.getMessage());
                }
                for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                    if (doc.getType() == DocumentChange.Type.ADDED) {

                        Expense expense = doc.getDocument().toObject(Expense.class);
                        expenseList.add(expense);

                        expenseListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                expenseListAdapter.removeItem(viewHolder.getAdapterPosition());

            }
        }).attachToRecyclerView(mMainList);
    }
}
