package com.cotrav.cotrav_admin.adapter.bus_adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.model.home_model.Passanger;
import com.cotrav.cotrav_admin.model.home_model.bus_model.show_bus.BusBooking;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TodaysBusBookingAdapter extends RecyclerView.Adapter<TodaysBusBookingAdapter.MyViewHolder>
{
    private FragmentActivity activity;
    private List<BusBooking> busBookingList;
    private String bookingStatus;
    Context context;
    Passanger taxiPassanger;
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public TodaysBusBookingAdapter(FragmentActivity activity, List<BusBooking> busBookingList, String bookingStatus)
    {
        this.activity=activity;
        this.busBookingList=busBookingList;
        this.bookingStatus=bookingStatus;
    }

    @Override
    public void onBindViewHolder(@NonNull TodaysBusBookingAdapter.MyViewHolder viewHolder, int position)
    {
        Log.d("Taxi info", GsonStringConvertor.gsonToString(busBookingList.get(position)));
        if (busBookingList.get(position).getPassangers().size()<=0){

            viewHolder.bookingSpocName.setText("No Emp");
        }else
        {
            for (int i=0;i<=busBookingList.get(position).getPassangers().size();i++)
            {

                taxiPassanger= busBookingList.get(position).getPassangers().get(0);


            }
            viewHolder.bookingSpocName.setText(taxiPassanger.getEmployeeName());


        }
        viewHolder.bookingId.setText(busBookingList.get(position).getReferenceNo());
        viewHolder.bookingStatus.setText(busBookingList.get(position).getCotravStatus());
        viewHolder.bookingDate.setText(busBookingList.get(position).getBookingDatetime());
        viewHolder.bookingBoardingPoint.setText(busBookingList.get(position).getPickupLocation());
        viewHolder.bookingDropPoint.setText(busBookingList.get(position).getDropLocation());

        String pickupstr = busBookingList.get(position).getPickupLocation();
        String[] pickupSplit = pickupstr.split(", ");
        viewHolder.bookingPickupCity.setText(pickupSplit[0]);

        String dropstr = busBookingList.get(position).getDropLocation();
        String[] dropSplit = dropstr.split(", ");
        viewHolder.bookingDropCity.setText(dropSplit[0]);

        String pickupDate=busBookingList.get(position).getPickupFromDatetime();

        try {
            Date date1 = df.parse(pickupDate);
            viewHolder.bookingPickupDate.setText(dateFormat.format(date1));
            viewHolder.bookingPickupTime.setText(timeFormat.format(date1));
            //viewHolder.bookingDropDate.setText(dateFormat.format(date1));
            //viewHolder.bookingDropTime.setText(timeFormat.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }



        String dropDate=busBookingList.get(position).getPickupToDatetime();

        try {
            Date date11 = df.parse(dropDate);
            viewHolder.bookingDate.setText(dateFormat.format(date11)+" "+timeFormat.format(date11));
            //viewHolder.bookingDropTime.setText(timeFormat1.format(date11));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(busBookingList.get(position).getStatusCotrav()<=1)
            viewHolder.bookingStatus.setText(busBookingList.get(position).getClientStatus());

        if (busBookingList.get(position).getStatusCotrav()<=1){
            viewHolder.bookingStatus.setText(busBookingList.get(position).getClientStatus());
            viewHolder.bookingDropDate.setText("Not Assigned");
        }
        else {
            if (busBookingList.get(position).getStatusCotrav() == 3||busBookingList.get(position).getStatusCotrav() == 2)
                viewHolder.bookingDropDate.setText("Not Assigned");

            viewHolder.bookingStatus.setText(busBookingList.get(position).getCotravStatus());
        }
        if (busBookingList.get(position).getStatusCotrav()>=4){

            try {
                Date date1 = viewHolder.df.parse(busBookingList.get(position).getPickupToDatetime());
                viewHolder.bookingDropDate.setText(viewHolder.dateFormat.format(date1));
                viewHolder.bookingDropTime.setText(viewHolder.timeFormat.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_booking_item, parent, false);


        return new TodaysBusBookingAdapter.MyViewHolder(itemView);
    }


    @Override
    public int getItemCount() {
        return busBookingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView bookingId;
        private TextView bookingStatus;
        private TextView bookingSpocName;
        private TextView bookingPickupCity;
        private TextView bookingDropCity;
        private TextView bookingPickupTime;
        private TextView bookingPickupDate;
        private TextView bookingDropTime;
        private TextView bookingDropDate;
        private TextView bookingBoardingPoint;
        private TextView bookingDropPoint;
        private TextView bookingDate;
        private Button btnDetails;

        private ImageView imageViewCall,imageViewCancel,imageViewDetails;

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookingId = itemView.findViewById(R.id.refrence_no);
            bookingStatus = itemView.findViewById(R.id.booking_status);
            bookingSpocName = itemView.findViewById(R.id.spoc_name);

            bookingDate=itemView.findViewById(R.id.booking_date);

            bookingPickupCity = itemView.findViewById(R.id.pickup_city);
            bookingDropCity = itemView.findViewById(R.id.drop_city);

            bookingPickupTime = itemView.findViewById(R.id.pickup_time);
            bookingDropTime = itemView.findViewById(R.id.drop_time);

            bookingPickupDate = itemView.findViewById(R.id.pickup_date);
            bookingDropDate = itemView.findViewById(R.id.drop_date);

            bookingBoardingPoint = itemView.findViewById(R.id.pickup_boardingpoint);
            bookingDropPoint = itemView.findViewById(R.id.drop_droping_point);


            imageViewDetails=(ImageView) itemView.findViewById(R.id.imageDetails);
            imageViewDetails.setOnClickListener(this);

            imageViewCall=(ImageView) itemView.findViewById(R.id.imv_call);
            imageViewCall.setVisibility(View.VISIBLE);
            imageViewCall.setOnClickListener(this);

            imageViewCancel=(ImageView)itemView.findViewById(R.id.imv_cancel);
            imageViewCancel.setImageResource(R.drawable.track);
            imageViewCancel.setVisibility(View.VISIBLE);
            imageViewCancel.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

            int id=view.getId();
            final int position=getAdapterPosition();
            switch (id){
                case R.id.imageDetails:
                    Toast.makeText(context, "Detail btn", Toast.LENGTH_SHORT).show();
               /*     BusDetailsFragment detailBusBookingFragment = new BusDetailsFragment();
                    Bundle args=new Bundle();
                    args.putString("details", GsonStringConvertor.gsonToString(busBookingList.get(position)));
                    args.putString("bookingId", String.valueOf(busBookingList.get(position).getId()));
                    args.putString("booking_status","Past");
                    detailBusBookingFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.pager_container, detailBusBookingFragment).
                            addToBackStack(null).commit();*/
                    break;

                case R.id.imv_call:

                    Toast.makeText(context, "Detail btn", Toast.LENGTH_SHORT).show();
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                            != PackageManager.PERMISSION_GRANTED) {
                        context.startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", context.getPackageName(), null)));
                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, R.style.CustomDialogTheme);
                        alertDialogBuilder.setTitle("Make a Call");
                        alertDialogBuilder.setIcon(R.drawable.phonecall);
                        alertDialogBuilder.setMessage("Are you sure you want to call " + busBookingList.get(position).getUserName() + " ?");
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                context.startActivity(new Intent(Intent.ACTION_CALL,
                                        Uri.parse("tel:" + busBookingList.get(position).getUserName())));
                            }
                        });
                        alertDialogBuilder.setNegativeButton(
                                "No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert11 = alertDialogBuilder.create();
                        alert11.getWindow().getAttributes().windowAnimations = R.style.DialogAnimationTheme;
                        alert11.show();
                    }

                    break;
                case R.id.imv_cancel:
                    Toast.makeText(context, "Tracking Comming Soon", Toast.LENGTH_SHORT).show();

                    break;

            }

        }
    }
}
