package fr.isep.yuchen_xie.projet_mobile_chat_isep;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

public class StaggeredRecyclerViewAdapter extends RecyclerView.Adapter<StaggeredRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "StaggeredRecyclerViewAd";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mStatus = new ArrayList<>();

    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;
   private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StaggeredRecyclerViewAdapter(Context context, ArrayList<String> names, ArrayList<String> imageUrls, ArrayList<String> status) {
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
        final int id = mContext.getResources().getIdentifier("fr.isep.yuchen_xie.projet_mobile_chat_isep:drawable/"+mImageUrls.get(position), null, null);

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
                ///8/8/activity itr
                Intent intent = new Intent(mContext, Item_reservation.class);
                intent.putExtra("name", mNames.get(position));
                intent.putExtra("photo", mImageUrls.get(position));
                mContext.startActivity(intent);

            }
        });
        /*
          ImageButton share = (ImageButton) findViewById(R.id.sharebtn);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                        + "/drawable/" + "ic_launcher");
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello");
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/jpeg");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "send"));
        }
        });
         */
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri imageUri = Uri.parse("android.resource://" + mContext.getPackageName()
                        + "/drawable/" + mImageUrls.get(position));
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Share your favorite geev");
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/jpg");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                mContext.startActivity(Intent.createChooser(shareIntent, "send"));            }
        });
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkConnection()) {
                    RequestQueue requestQueuereg2 = Volley.newRequestQueue(mContext);
                    StringRequest requestreg2 = new StringRequest(Request.Method.GET, "http://10.0.2.2:3000/annonces/", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONArray jsonResponse = null;
                            try {
                                jsonResponse = new JSONArray(response);
                                for(int i= 0;i<jsonResponse.length();i++) {
                                    if(jsonResponse.getJSONObject(i).getString("name").equals(mNames.get(position))&&jsonResponse.getJSONObject(i).getString("photo").equals(mImageUrls.get(position))){
                                        RequestQueue requestQueuereg = Volley.newRequestQueue(mContext);
                                        final StringRequest requestreg ;

                                        Map<String, String> params = new HashMap<String, String>();
                                        //////amine =>>>>> set id user
                                        params.put("id_user", "3");
                                        getannonceid(mNames.get(position),mImageUrls.get(position));
                                        params.put("id_article", jsonResponse.getJSONObject(i).getString("id_annonce"));

                                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                                "http://10.0.2.2:3000/favorite", new JSONObject(params),
                                                new Response.Listener<JSONObject>() {

                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        Log.d("JSONPost", response.toString());

                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                VolleyLog.d("JSONPost", "Error: " + error.getMessage());

                                                //pDialog.hide();
                                            }
                                        });
                                        requestQueuereg.add(jsonObjReq);                                    }
                                }
                                // mNames.add(jsonResponse.getString("name"));
                                // mDescription.add(jsonResponse.getString("description"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("err", error.toString());
                        }
                    });

                    requestQueuereg2.add(requestreg2);

                }
            }
        });
        holder.reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkConnection()) {
                    RequestQueue requestQueuereg2 = Volley.newRequestQueue(mContext);
                    StringRequest requestreg2 = new StringRequest(Request.Method.GET, "http://10.0.2.2:3000/annonces/", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONArray jsonResponse = null;
                            try {
                                jsonResponse = new JSONArray(response);
                                for(int i= 0;i<jsonResponse.length();i++) {
                                    if(jsonResponse.getJSONObject(i).getString("name").equals(mNames.get(position))&&jsonResponse.getJSONObject(i).getString("photo").equals(mImageUrls.get(position))){
                                        RequestQueue requestQueuereg = Volley.newRequestQueue(mContext);
                                        final StringRequest requestreg ;
                 /*
                    requestreg= new StringRequest(Request.Method.POST, "http://10.0.2.2:3000/reservation", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONArray jsonResponse = null;
                            try {
                                jsonResponse = new JSONArray(response);
                                // mNames.add(jsonResponse.getString("name"));
                                // mDescription.add(jsonResponse.getString("description"));
                                System.out.println("res     "+response);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("err", error.toString());
                        }
                    });*/
/*
                    StringRequest postRequest = new StringRequest(Request.Method.POST, "http://10.0.2.2:3000/reservation",
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    // response
                                    Log.d("Response", response);
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    Log.d("Error.Response", error.toString());
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("date_reservation", "12/12/1221");
                            params.put("id_user", "3");
                            params.put("id_annonce", "4");

                            return params;
                        }
                    };
                    */

                                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                        Date date = new Date();
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("date_reservation", dateFormat.format(date).toString());
                                        //////amine =>>>>> set id user
                                        params.put("id_user", "3");
                                        getannonceid(mNames.get(position),mImageUrls.get(position));
                                        params.put("id_annonce", jsonResponse.getJSONObject(i).getString("id_annonce"));

                                        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                                                "http://10.0.2.2:3000/reservation", new JSONObject(params),
                                                new Response.Listener<JSONObject>() {

                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        Log.d("JSONPost", response.toString());

                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                VolleyLog.d("JSONPost", "Error: " + error.getMessage());

                                                //pDialog.hide();
                                            }
                                        });
                                        requestQueuereg.add(jsonObjReq);                                    }
                                }
                                // mNames.add(jsonResponse.getString("name"));
                                // mDescription.add(jsonResponse.getString("description"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("err", error.toString());
                        }
                    });

                    requestQueuereg2.add(requestreg2);

                }
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
        ImageButton share,fav,reservation;

        public ViewHolder(View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.imageview_widget);
            this.name = itemView.findViewById(R.id.name_widget);
            this.status = itemView.findViewById(R.id.status);
            this.share = itemView.findViewById(R.id.sharebtn);
            this.reservation = itemView.findViewById(R.id.reservationbtn);
            this.fav = itemView.findViewById(R.id.favbtn);
        }
    }
    public boolean checkConnection(){
        ConnectivityManager connectivityManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        else
            return false;
    }
    public void getannonceid(final String name, final String imageurl){
        if(checkConnection()) {
            RequestQueue requestQueuereg2 = Volley.newRequestQueue(mContext);
            StringRequest requestreg2 = new StringRequest(Request.Method.GET, "http://10.0.2.2:3000/annonces/", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONArray jsonResponse = null;
                    try {
                        jsonResponse = new JSONArray(response);
                        for(int i= 0;i<jsonResponse.length();i++) {
                           if(jsonResponse.getJSONObject(i).getString("name").equals(name)&&jsonResponse.getJSONObject(i).getString("photo").equals(imageurl)){
                              setId(jsonResponse.getJSONObject(i).getString("id_annonce"));
                           }
                        }
                        // mNames.add(jsonResponse.getString("name"));
                        // mDescription.add(jsonResponse.getString("description"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("err", error.toString());
                }
            });

            requestQueuereg2.add(requestreg2);
        }
    }
}