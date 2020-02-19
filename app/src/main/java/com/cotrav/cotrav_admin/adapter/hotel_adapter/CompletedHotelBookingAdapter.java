package com.cotrav.cotrav_admin.adapter.hotel_adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.cotrav.cotrav_admin.GsonStringConvertor;
import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.model.home_model.Passanger;
import com.cotrav.cotrav_admin.model.home_model.hotel_model.show_hotel.HotelBooking;
import com.cotrav.cotrav_admin.ui.home.hotel.hotel_fragments.DetailHotelBookingFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CompletedHotelBookingAdapter extends RecyclerView.Adapter<CompletedHotelBookingAdapter.MyViewHolder>
{

    private FragmentActivity activity;
    private List<HotelBooking> hotelBookingList;
    private String bookingStatus;
    private Context context;
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    Passanger hotelPassanger;


    public CompletedHotelBookingAdapter(FragmentActivity activity, List<HotelBooking> hotelBookingList, String bookingStatus)
    {
        this.activity = activity;
        this.hotelBookingList = hotelBookingList;
        this.bookingStatus = bookingStatus;
    }

    @NonNull
    @Override
    public CompletedHotelBookingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        context=parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_hotel_booking_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position)
    {
        Log.d("Taxi info", GsonStringConvertor.gsonToString(hotelBookingList.get(position)));

        if (hotelBookingList.get(position).getPassangers().size()<=0){

            viewHolder.bookingEmpName.setText("No Emp");
        }else
        {
            for (int i=0;i<=hotelBookingList.get(position).getPassangers().size();i++)
            {

                hotelPassanger= hotelBookingList.get(position).getPassangers().get(0);


            }
            viewHolder.bookingEmpName.setText(hotelPassanger.getEmployeeName());


        }


        viewHolder.bookingId.setText(hotelBookingList.get(position).getReferenceNo());
        viewHolder.bookingStatus.setText(hotelBookingList.get(position).getCotravStatus());
        viewHolder.bookingPickupCity.setText(hotelBookingList.get(position).getFromCityName());
        viewHolder.bookingPrefLocation.setText(hotelBookingList.get(position).getPreferredArea());
        String pickupDate=hotelBookingList.get(position).getCheckinDatetime();
        String dropDate=hotelBookingList.get(position).getCheckoutDatetime();
         try {
            Date date1 = df.parse(pickupDate);
            Date date2 = df.parse(dropDate);

            viewHolder.pickupDate.setText(dateFormat.format(date1));
            viewHolder.pickupTime.setText(timeFormat.format(date1));
             viewHolder.dropDate.setText(dateFormat.format(date2));
             viewHolder.dropTime.setText(timeFormat.format(date2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String bookingDate=hotelBookingList.get(position).getBookingDatetime();
        try {
            Date date11 = df.parse(bookingDate);
            viewHolder.bookingDate.setText(dateFormat.format(date11)+" "+timeFormat.format(date11));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(hotelBookingList.get(position).getStatusCotrav()<=1)
            viewHolder.bookingStatus.setText(hotelBookingList.get(position).getClientStatus());
    }

    @Override
    public int getItemCount() {
        return hotelBookingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView bookingId;
        private TextView bookingStatus;
        private TextView bookingEmpName;
        private TextView bookingDate;
        private TextView bookingPickupCity;
        private TextView bookingPrefLocation;
        private TextView pickupTime;
        private TextView pickupDate;
        private TextView dropTime;
        private TextView dropDate;

        private Button btnDetails;
        private ImageView imageViewCall,imageViewCancel,imageViewDetails;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            bookingId=itemView.findViewById(R.id.refrence_no);
            bookingStatus=itemView.findViewById(R.id.booking_status);
            bookingDate=itemView.findViewById(R.id.booking_date);
            bookingEmpName=itemView.findViewById(R.id.emp_name);
            bookingPickupCity=itemView.findViewById(R.id.pickup_city);
            bookingPrefLocation=itemView.findViewById(R.id.pickup_boardingpoint);

            pickupTime=itemView.findViewById(R.id.pickup_time);
            pickupDate=itemView.findViewById(R.id.pickup_date);
            dropTime=itemView.findViewById(R.id.drop_time);
            dropDate=itemView.findViewById(R.id.drop_date);



            imageViewDetails=(ImageView) itemView.findViewById(R.id.imageDetails);
            imageViewDetails.setOnClickListener(this);

            /*imageViewCall=(ImageView)itemView.findViewById(R.id.imv_call);
            imageViewCall.setVisibility(View.VISIBLE);
            imageViewCall.setOnClickListener(this);

            imageViewCancel=(ImageView)itemView.findViewById(R.id.imv_cancel);
            imageViewCancel.setVisibility(View.VISIBLE);
            imageViewCancel.setOnClickListener(this);*/



        }

        @Override
        public void onClick(View view) {

            int id=view.getId();
            int position=getAdapterPosition();
            switch (id){
                case R.id.imageDetails:
                    Toast.makeText(context, "Detail btn", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Detail btn", Toast.LENGTH_SHORT).show();
                    DetailHotelBookingFragment detailBusBookingFragment = new DetailHotelBookingFragment();
                    Bundle args=new Bundle();
                    args.putString("details", GsonStringConvertor.gsonToString(hotelBookingList.get(position)));
                    args.putString("bookingId",hotelBookingList.get(position).getId().toString());
                    args.putString("booking_status","Past");
                    detailBusBookingFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.pager_container, detailBusBookingFragment).
                            addToBackStack(null).commit();
                    break;

            }

        }
    }
}
