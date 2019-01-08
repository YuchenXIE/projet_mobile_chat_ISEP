package fr.isep.yuchen_xie.projet_mobile_chat_isep;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
/*
 *
 *
 * @Mdalel_ahmed
 *
 * ISEP
 * 2019
 *
 *
 */

public class NotificationDataAdapter  extends RecyclerView.Adapter<NotificationDataAdapter.NotificationViewHolder> {
    public List<Notification> Notifications;

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        private TextView time, status, fromuser, phonenumber, donname;

        public NotificationViewHolder(View view) {
            super(view);
            donname = (TextView) view.findViewById(R.id.donname);
            phonenumber = (TextView) view.findViewById(R.id.phonenumber);
            fromuser = (TextView) view.findViewById(R.id.fromuser);
            status = (TextView) view.findViewById(R.id.status);
            time = (TextView) view.findViewById(R.id.time);
        }
    }

    public NotificationDataAdapter(List<Notification> Notification) {
        this.Notifications = Notification;
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notifcationrow, parent, false);

        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification notification = Notifications.get(position);
        holder.donname.setText(notification.getDonname());
        holder.fromuser.setText(notification.fromuser);
        holder.phonenumber.setText(notification.telephonenumber);
        holder.status.setText(notification.getStatus());
        holder.time.setText(notification.getDate());
    }

    @Override
    public int getItemCount() {
        return Notifications.size();
    }
}
