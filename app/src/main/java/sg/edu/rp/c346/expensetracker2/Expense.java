package sg.edu.rp.c346.expensetracker2;

import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;

public class Expense {

    public String money;
    public String category;
    public String date;
    public String description;
    public String balance;

//    public Expense(int id, String money, String category, String date, String description) {
//        this.id = id;
//    }

    public Expense() {

    }

    public Expense(String money, String category, String date, String description) {
        this.money = money;
        this.category = category;
        this.date = date;
        this.description = description;
       // this.balance = balance;
    }
//
//    public Expense(EditText money, EditText category, EditText date, EditText description) {
//
//    }

    public String getMoney() {
        return money;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
//    public String getBalance() {
//        return balance;
//    }
}
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

