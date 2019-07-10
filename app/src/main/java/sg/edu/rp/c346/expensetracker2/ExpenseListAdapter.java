package sg.edu.rp.c346.expensetracker2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.util.ExecutorEventListener;


import java.util.List;

public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ViewHolder> {

    public List<Expense> expenseList;

    public ExpenseListAdapter(List<Expense> expenseList) {

        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new ViewHolder(view);
    }

//    public void deleteItem(int position) {
//        getSnapshots().getSnapshot(position).getReference().delete();
//    }

    public void removeItem(int position) {
        expenseList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.Money.setText(expenseList.get(position).getMoney());
        viewHolder.Cat.setText(expenseList.get(position).getCategory());
        viewHolder.Date.setText(expenseList.get(position).getDate());
        viewHolder.Des.setText(expenseList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        View mView;

        public TextView Money;
        public TextView Cat;
        public TextView Date;
        public TextView Des;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            Money = (TextView) mView.findViewById(R.id.tvMoney);
            Cat = (TextView) mView.findViewById(R.id.tvCat);
            Date = (TextView) mView.findViewById(R.id.tvDate);
            Des = (TextView) mView.findViewById(R.id.tvDes);
        }
    }
}
