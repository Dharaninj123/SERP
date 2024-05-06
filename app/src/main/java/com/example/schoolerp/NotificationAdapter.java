
package com.example.schoolerp;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import java.util.List;



public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {



        private Context context;
        private List<Notification> notificationList;

        public NotificationAdapter(Context context, List<Notification> notificationList) {
            this.context = context;
            this.notificationList = notificationList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Notification notification = notificationList.get(position);
            holder.topicTextView.setText(notification.getTopic());
            holder.subjectTextView.setText(notification.getSubject());
        }

        @Override
        public int getItemCount() {
            return notificationList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView topicTextView;
            TextView subjectTextView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                topicTextView = itemView.findViewById(R.id.topicTextView);
                subjectTextView = itemView.findViewById(R.id.subjectTextView);
            }
        }
    }
