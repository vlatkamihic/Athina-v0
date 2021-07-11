package com.example.athina.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athina.R;
import com.example.athina.database_notifications.Notification;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.NotificationViewHolder> {


    private final Context context;
    private List<Notification> notificationList;
    //private OnItemRemoved deleteListener;

    public NotificationsListAdapter(Context context){
        this.context = context;
    }

    public void setNotificationList(List<Notification> notificationList){
        this.notificationList = notificationList;
        notifyDataSetChanged();
    }


    @NonNull
    @NotNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notification_cell, parent, false);

        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotificationsListAdapter.NotificationViewHolder holder, int position) {

        holder.nName.setText(this.notificationList.get(position).nTitle);
        String dateTime;

        int year = this.notificationList.get(position).nYear;
        int month = this.notificationList.get(position).nMonth;
        int day = this.notificationList.get(position).nDay;
        int hour = this.notificationList.get(position).nHour;
        int minute = this.notificationList.get(position).nMinute;

        dateTime = year + "-" + month + "-" + day + "  " + hour + ":" + minute + ":00";

        holder.nDateTime.setText(dateTime);

    }

    @Override
    public int getItemCount() {
        return this.notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView nName;
        TextView nDateTime;


        public NotificationViewHolder(View itemView) {
            super(itemView);

            nName = (TextView) itemView.findViewById(R.id.notifName);
            nDateTime = (TextView) itemView.findViewById(R.id.dateTime);
        }
    }
}
