package fr.isep.yuchen_xie.projet_mobile_chat_isep;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StaggeredRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "StaggeredRecyclerViewAd";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mStatus = new ArrayList<>();

    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;

    public StaggeredRecyclerViewAdapter(Context context, ArrayList<String> names, ArrayList<String> imageUrls,ArrayList<String> status) {
        mNames = names;
        mImageUrls = imageUrls;
        mStatus = status;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);
        int id = mContext.getResources().getIdentifier("fr.isep.yuchen_xie.projet_mobile_chat_isep:drawable/"+mImageUrls.get(position), null, null);

        Glide.with(mContext)
                .load(id)
                .apply(requestOptions)
                .into(holder.image);
           if(mStatus.get(position).equals("ACCEPTED")){
               holder.status.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
           }else if(mStatus.get(position).equals("APPLIED")){
               holder.status.setTextColor(ContextCompat.getColor(mContext, R.color.orange));

           }
           else if(mStatus.get(position).equals("AVAILABLE")){
               holder.status.setTextColor(ContextCompat.getColor(mContext, R.color.red));

           }
           else if(mStatus.get(position).equals("RETRIEVED")){
               holder.status.setTextColor(ContextCompat.getColor(mContext, R.color.green));

           }
        holder.status.setText(mStatus.get(position));
        holder.name.setText(mNames.get(position));


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on: " + mNames.get(position)+"   "+mStatus.get(position));
                Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name;
        TextView status;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.imageview_widget);
            this.name = itemView.findViewById(R.id.name_widget);
            this.status = itemView.findViewById(R.id.status);
        }
    }
}