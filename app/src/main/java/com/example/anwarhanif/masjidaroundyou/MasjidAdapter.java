package com.example.anwarhanif.masjidaroundyou;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MasjidAdapter extends RecyclerView.Adapter<MasjidAdapter.MasjidViewHolder> {

    private Context context;
    private ArrayList<MasjidPOJO> masjidDetails;
    private ArrayList<Double> masjidDistance;

    public MasjidAdapter(Context ctx, ArrayList<MasjidPOJO> masjidDetails, ArrayList<Double> masjidDistance){
        this.context = ctx;
        this.masjidDetails = masjidDetails;
        this.masjidDistance = masjidDistance;
    }

    @NonNull
    @Override
    public MasjidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.masjid_details, parent, false);
        return new MasjidViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MasjidViewHolder holder, int position) {
        holder.mMasjidName.setText(masjidDetails.get(position).getName());
        holder.mLongitude.setText("Lat: "+masjidDetails.get(position).getLongitude());
        holder.mLatitude.setText("Long: "+masjidDetails.get(position).getLatitude());
        holder.mFajrTiming.setText((masjidDetails.get(position).getPrayerTimimg().getFajr()));
        holder.mZohrTiming.setText((masjidDetails.get(position).getPrayerTimimg().getZohr()));
        holder.mAsrTiming.setText((masjidDetails.get(position).getPrayerTimimg().getAsr()));
        holder.mMaghribTiming.setText((masjidDetails.get(position).getPrayerTimimg().getMaghrib()));
        holder.mIshaTiming.setText((masjidDetails.get(position).getPrayerTimimg().getIsha()));
        holder.mDistance.setText(String.format("Distance: %.2f Meters", masjidDistance.get(position)*1000));

    }

    @Override
    public int getItemCount() {
        return masjidDetails.size();
    }

    public class MasjidViewHolder extends RecyclerView.ViewHolder{

        TextView mMasjidName, mLatitude, mLongitude, mFajrTiming,
                mZohrTiming, mAsrTiming, mMaghribTiming, mIshaTiming, mDistance;


        public MasjidViewHolder(View itemView) {
            super(itemView);

            mMasjidName = (TextView) itemView.findViewById(R.id.masjid_name);
            mLatitude = (TextView) itemView.findViewById(R.id.latitude);
            mLongitude = (TextView) itemView.findViewById(R.id.longitude);
            mFajrTiming = (TextView) itemView.findViewById(R.id.fajr);
            mZohrTiming = (TextView) itemView.findViewById(R.id.zohr);
            mAsrTiming = (TextView) itemView.findViewById(R.id.asar);
            mMaghribTiming = (TextView) itemView.findViewById(R.id.maghrib);
            mIshaTiming = (TextView) itemView.findViewById(R.id.isha);
            mDistance = (TextView) itemView.findViewById(R.id.distance);

        }
    }
}
