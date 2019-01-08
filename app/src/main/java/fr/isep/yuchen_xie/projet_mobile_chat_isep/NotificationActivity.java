package fr.isep.yuchen_xie.projet_mobile_chat_isep;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.ArrayList;
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

public class NotificationActivity extends AppCompatActivity {
    private NotificationDataAdapter mAdapter;
    SwipeController swipeController = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        setNotificationDataAdapter();
        setupRecyclerView();
    }
    private void setNotificationDataAdapter() {
        List<Notification> notificationList = new ArrayList<>();

        Notification notification = new Notification("12/12/1121","ahmed","refuse","07775000","hellouser");
        notificationList.add(notification);
        mAdapter = new NotificationDataAdapter(notificationList);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerViewnotification);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                mAdapter.Notifications.remove(position);
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }
}
