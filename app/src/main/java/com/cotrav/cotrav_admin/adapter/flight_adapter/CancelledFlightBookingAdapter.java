package com.cotrav.cotrav_admin.adapter.flight_adapter;

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
import com.cotrav.cotrav_admin.model.home_model.flight_model.show_flight.FlightBooking;
import com.cotrav.cotrav_admin.ui.home.flight.flight_fragments.DetailFlightBookingFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CancelledFlightBookingAdapter extends RecyclerView.Adapter<CancelledFlightBookingAdapter.MyViewHolder>
{

    private FragmentActivity activity;
    private List<FlightBooking> flightBookingList;
    private String bookingStatus;
    private Context context;

    Passanger flightPassanger;

    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public CancelledFlightBookingAdapter(FragmentActivity activity, List<FlightBooking> flightBookingList, String bookingStatus) {
        this.activity = activity;
        this.flightBookingList = flightBookingList;
        this.bookingStatus = bookingStatus;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_flight_booking_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position)
    {
        Log.d("Taxi info", GsonStringConvertor.gsonToString(flightBookingList.get(position)));

        if (flightBookingList.get(position).getPassangers().size()<=0){

            viewHolder.bookingSpocName.setText("No Emp");
        }else
        {
            for (int i=0;i<=flightBookingList.get(position).getPassangers().size();i++)
            {

                flightPassanger= flightBookingList.get(position).getPassangers().get(0);


            }
            viewHolder.bookingSpocName.setText(flightPassanger.getEmployeeName());


        }
        viewHolder.bookingId.setText(flightBookingList.get(position).getReferenceNo());
        viewHolder.bookingStatus.setText(flightBookingList.get(position).getCotravStatus());
        //viewHolder.bookingSpocName.setText(flightBookingList.get(position).getUserName());
        viewHolder.bookingDate.setText(flightBookingList.get(position).getBookingDatetime());

        String pickupDate=flightBookingList.get(position).getDepartureDatetime();


        try {
            Date date1 = df.parse(pickupDate);
            //viewHolder.bookingPickupDate.setText(dateFormat.format(date1));
            //viewHolder.bookingPickupTime.setText(timeFormat.format(date1));
            //viewHolder.bookingDropDate.setText(dateFormat.format(date1));
            //viewHolder.bookingDropTime.setText(timeFormat.format(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dropDate=flightBookingList.get(position).getBookingDatetime();

        try {
            Date date11 = df.parse(dropDate);
            viewHolder.bookingDate.setText(dateFormat.format(date11)+" "+timeFormat.format(date11));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String pickupstr = flightBookingList.get(position).getFromLocation();
        String[] pickupSplit = pickupstr.split(", ");
        viewHolder.bookingPickupCity.setText(pickupSplit[0]);

        String dropstr = flightBookingList.get(position).getToLocation();
        String[] dropSplit = dropstr.split(", ");
        viewHolder.bookingDropCity.setText(dropSplit[0]);


        viewHolder.bookingBoardingPoint.setText(flightBookingList.get(position).getFromLocation());
        viewHolder.bookingDropPoint.setText(flightBookingList.get(position).getToLocation());
        if (flightBookingList.get(position).getStatusCotrav()<=1){
            viewHolder.bookingStatus.setText(flightBookingList.get(position).getClientStatus());
            //viewHolder.bookingDropDate.setText("Not Assigned");
        }
        else {
            if (flightBookingList.get(position).getStatusCotrav() == 3||flightBookingList.get(position).getStatusCotrav() == 2)
              //  viewHolder.bookingDropDate.setText("Not Assigned");

            viewHolder.bookingStatus.setText(flightBookingList.get(position).getCotravStatus());
        }
        if (flightBookingList.get(position).getStatusCotrav()>=4){

            try {
                Date date1 = viewHolder.df.parse(flightBookingList.get(position).getDepartureDatetime());
                //viewHolder.bookingDropDate.setText(viewHolder.dateFormat.format(date1));
                //viewHolder.bookingDropTime.setText(viewHolder.timeFormat.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return flightBookingList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView bookingId;
        private TextView bookingStatus;
        private TextView bookingSpocName;
        private TextView bookingPickupCity;
        private TextView bookingDropCity;
        //private TextView bookingPickupTime;
        //private TextView bookingPickupDate;
        //private TextView bookingDropTime;
        //private TextView bookingDropDate;
        private TextView bookingBoardingPoint;
        private TextView bookingDropPoint;
        private TextView bookingDate;
        private Button btnDetails;

        private ImageView imageViewCall,imageViewWhatsApp,imageViewDetails,imageViewCancel;

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

            //bookingPickupTime = itemView.findViewById(R.id.pickup_time);
            //bookingDropTime = itemView.findViewById(R.id.drop_time);

            //bookingPickupDate = itemView.findViewById(R.id.pickup_date);
            //bookingDropDate = itemView.findViewById(R.id.drop_date);

            bookingBoardingPoint = itemView.findViewById(R.id.pickup_boardingpoint);
            bookingDropPoint = itemView.findViewById(R.id.drop_droping_point);

            imageViewDetails=(ImageView) itemView.findViewById(R.id.imageDetails);
            imageViewDetails.setOnClickListener(this);

            imageViewCall=(ImageView) itemView.findViewById(R.id.imv_call);
            imageViewCall.setVisibility(View.INVISIBLE);

/*
            imageViewWhatsApp=(ImageView) itemView.findViewById(R.id.imv_whatsapp);
            imageViewWhatsApp.setVisibility(View.INVISIBLE);
*/
            //imageViewWhatsApp.setOnClickListener(this);

            imageViewCancel=(ImageView) itemView.findViewById(R.id.imv_cancel);
            imageViewCancel.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onClick(View view) {
            int id=view.getId();
            final int position=getAdapterPosition();
            switch (id){
                case R.id.imageDetails:
                    Toast.makeText(context, "Detail btn", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Detail btn", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Detail btn", Toast.LENGTH_SHORT).show();
                    DetailFlightBookingFragment detailBusBookingFragment = new DetailFlightBookingFragment();
                    Bundle args=new Bundle();
                    args.putString("details", GsonStringConvertor.gsonToString(flightBookingList.get(position)));
                    args.putString("bookingId",flightBookingList.get(position).getId().toString());
                    args.putString("booking_status","Past");
                    detailBusBookingFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.pager_container, detailBusBookingFragment).
                            addToBackStack(null).commit();
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
                        alertDialogBuilder.setMessage("Are you sure you want to call " + flightBookingList.get(position).getUserName() + " ?");
                        alertDialogBuilder.setCancelable(true);
                        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @SuppressLint("MissingPermission")
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                context.startActivity(new Intent(Intent.ACTION_CALL,
                                        Uri.parse("tel:" + flightBookingList.get(position).getUserName())));
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

            }

        }
    }
}
