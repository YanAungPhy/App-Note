package com.neatroots.footballnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.LocaleDisplayNames;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.neatroots.footballnote.adapter.UserAdapter;
import com.neatroots.footballnote.db.UserDetailDao;
import com.neatroots.footballnote.db.UserInfo;
import com.neatroots.footballnote.viewmodel.MainActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private boolean isPaid = false;
   // private UserInfo editUserInfo = null;

    EditText userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.black));

        recyclerView = findViewById(R.id.recycler);

        FloatingActionButton addUser = findViewById(R.id.add_fab);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddUserDialog();
            }
        });
        initViewModel();
        initRecyclerView();
        viewModel.getAllUserList();


    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(this,viewModel);
        recyclerView.setAdapter(userAdapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel.getListUserObserver().observe(this, new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> userInfos) {
                userAdapter.setUserList(userInfos);

            }
        });
    }

    private void showAddUserDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        View dialogView = getLayoutInflater().inflate(R.layout.floating_action_dialog, null);
        userName = dialogView.findViewById(R.id.user_name);
        EditText amount = dialogView.findViewById(R.id.amount);
        EditText date = dialogView.findViewById(R.id.date);
        RadioGroup radioGroup = dialogView.findViewById(R.id.isPaid);
        TextView confim = dialogView.findViewById(R.id.confirm);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yy");
        String dateTime = simpleDateFormat.format(calendar.getTime()).toString();
        date.setText(dateTime);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_btn1) {
                    isPaid = true;
                    Log.i("CHECKED", isPaid + "RB1");


                } else if (checkedId == R.id.radio_btn2) {
                    isPaid = false;
                    Log.i("CHECKED", isPaid + "RB2");

                }
            }
        });

        confim.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String name = userName.getText().toString();
                String userAmount = amount.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(MainActivity.this, "Enter category name", Toast.LENGTH_SHORT).show();
                    return;
                }
                //UserInfo userInfo=new UserInfo(name,userAmount,userDate,isPaid);
                viewModel.insertUser(name, userAmount, dateTime, isPaid);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(dialogView);
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if(item.getItemId() == R.id.total_amount){
        showTotalAmountDialog();
    }
        return super.onOptionsItemSelected(item);
    }

    private void showTotalAmountDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.total_amount_dialog);
        TextView tvTotal = dialog.findViewById(R.id.total_amount_tv);
        tvTotal.setText(viewModel.totalAmount(true)+"Ks");
        dialog.setCancelable(true);
        dialog.show();
    }

}