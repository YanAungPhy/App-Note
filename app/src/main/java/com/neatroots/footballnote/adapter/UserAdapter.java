package com.neatroots.footballnote.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.neatroots.footballnote.MainActivity;
import com.neatroots.footballnote.R;
import com.neatroots.footballnote.db.UserInfo;
import com.neatroots.footballnote.viewmodel.MainActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {


    Context context;
    private List<UserInfo> userInfo;
    private boolean isPaid = false;
    private MainActivityViewModel viewModel;

    public UserAdapter(Context context, MainActivityViewModel viewModel) {
        this.context = context;
        this.viewModel = viewModel;
    }

    public void setUserList(List<UserInfo> userInfo) {
        this.userInfo = userInfo;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, null);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.userName.setText(this.userInfo.get(position).userName);
        holder.amount.setText(this.userInfo.get(position).amount + "ks");
        holder.date.setText(this.userInfo.get(position).date);

        if (userInfo.get(position).isPaid) {
            holder.isPaid.setText("Paid");
            holder.tvImage.setImageResource(R.drawable.check_mark);
        } else {
            holder.isPaid.setText("Unpaid");
            holder.tvImage.setImageResource(R.drawable.exc_mark);
        }

        holder.tvPopUpMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                UserInfo userInfo = new UserInfo();
//                viewModel.deleteUser(userInfo);

                PopupMenu popupMenu = new PopupMenu(context.getApplicationContext(), holder.tvPopUpMenu);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
//                                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
//                                View dialogView = new Inflater().inflate(R.layout.floating_action_dialog,);
                                Dialog dialog = new Dialog(context);
                                dialog.setCancelable(true);
                                dialog.setContentView(R.layout.floating_action_dialog);
                                dialog.show();

                                EditText userName = dialog.findViewById(R.id.user_name);
                                EditText amount = dialog.findViewById(R.id.amount);
                                EditText date = dialog.findViewById(R.id.date);
                                RadioGroup radioGroup = dialog.findViewById(R.id.isPaid);
                                TextView confim = dialog.findViewById(R.id.confirm);

                                userName.setText(userInfo.get(position).userName);
                                amount.setText(userInfo.get(position).amount);
                                date.setText(userInfo.get(position).date);
                                isPaid = userInfo.get(position).isPaid;

                                RadioButton paidBtn = dialog.findViewById(R.id.radio_btn1);
                                RadioButton notPaidBtn = dialog.findViewById(R.id.radio_btn2);
                                if (isPaid) {
                                    paidBtn.setChecked(true);
                                } else {
                                    notPaidBtn.setChecked(true);
                                }

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
                                        int uid = userInfo.get(position).uid;

                                        UserInfo userInfo = new UserInfo(uid,userName.getText().toString(), amount.getText().toString(), date.getText().toString(), isPaid);
                                        viewModel.updateUser(userInfo);
                                        dialog.dismiss();
                                        //clicklistener.editItem(userInfo.get(position));
                                    }
                                });

                                break;
                            case R.id.delete:
                                viewModel.deleteUser(userInfo.get(position));
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


    }


    @Override
    public int getItemCount() {
        if (userInfo == null || userInfo.size() == 0) {
            return 0;
        } else {
            return userInfo.size();
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView date;
        TextView amount;
        TextView isPaid;
        ImageView tvImage;
        ImageView tvPopUpMenu;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
            isPaid = itemView.findViewById(R.id.tvPaid);
            tvImage = itemView.findViewById(R.id.tvImage);
            tvPopUpMenu = itemView.findViewById(R.id.tvPopUpMenu);

        }

    }


}
