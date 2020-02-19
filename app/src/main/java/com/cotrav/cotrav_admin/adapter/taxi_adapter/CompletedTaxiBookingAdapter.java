package com.cotrav.cotrav_admin.adapter.taxi_adapter;

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
import com.cotrav.cotrav_admin.model.home_model.taxi_model.show_taxi.TaxiBooking;
import com.cotrav.cotrav_admin.ui.home.taxi.taxi_fragments.DetailTaxiBookingFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CompletedTaxiBookingAdapter extends RecyclerView.Adapter<CompletedTaxiBookingAdapter.MyViewHolder>
{

    private FragmentActivity activity;
    private List<TaxiBooking> taxiBookingList;
    private String bookingStatus;
    private Context context;
    Passanger taxiPassanger;
    DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    DateFormat timeFormat = new SimpleDateFormat("HH:mm");


    public CompletedTaxiBookingAdapter(FragmentActivity activity, List<TaxiBooking> taxiBookingList, String bookingStatus)
    {
        this.activity = activity;
        this.taxiBookingList = taxiBookingList;
        this.bookingStatus = bookingStatus;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        context=parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.show_booking_item, parent, false);

        return new CompletedTaxiBookingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position)
    {
        Log.d("Taxi info", GsonStringConvertor.gsonToString(taxiBookingList.get(position)));
        if (taxiBookingList.get(position).getPassangers().size()<=0){

            viewHolder.bookingSpocName.setText("No Emp");
        }else
        {
            for (int i=0;i<=taxiBookingList.get(position).getPassangers().size();i++)
            {

                taxiPassanger= taxiBookingList.get(position).getPassangers().get(0);


            }
            viewHolder.bookingSpocName.setText(taxiPassanger.getEmployeeName());


        }
        viewHolder.bookingId.setText(taxiBookingList.get(position).getReferenceNo());
        viewHolder.bookingStatus.setText(taxiBookingList.get(position).getCotravStatus());
        viewHolder.bookingDropPoint.setText(taxiBookingList.get(position).getDropLocation());
        viewHolder.bookingTourType.setText(taxiBookingList.get(position).getTourType());

        if (taxiBookingList.get(position).getTourType().equals("1"))
            viewHolder.bookingTourType.setText("Radio");
        if (taxiBookingList.get(position).getTourType().equals("2"))
            viewHolder.bookingTourType.setText("Local");
        if (taxiBookingList.get(position).getTourType().equals("3"))
            viewHolder.bookingTourType.setText("OutStation");

        String pickupstr = taxiBookingList.get(position).getPickupLocation();
        String[] pickupSplit = pickupstr.split(", ");
        viewHolder.bookingPickupCity.setText(pickupSplit[0]);

        String dropstr = taxiBookingList.get(position).getDropLocation();
        String[] dropSplit = dropstr.split(", ");
        viewHolder.bookingDropCity.setText(dropSplit[0]);

        String pickupDate=taxiBookingList.get(position).getPickupDatetime();
        try {
            Date date1 = df.parse(pickupDate);
            viewHolder.bookingPickupDate.setText(dateFormat.format(date1));
            viewHolder.bookingPickupTime.setText(timeFormat.format(date1));
                }
        catch (ParseException e) {
            e.printStackTrace();
        }
        String bookDate=taxiBookingList.get(position).getBookingDate();
        try {
            Date date11 = df.parse(bookDate);
            viewHolder.bookingDateTime.setText(dateFormat.format(date11)+" "+timeFormat.format(date11));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.bookingBoardingPoint.setText(taxiBookingList.get(position).getPickupLocation());
        viewHolder.bookingDropPoint.setText(taxiBookingList.get(position).getDropLocation());


        if(taxiBookingList.get(position).getStatusCotrav()<=1)
            viewHolder.bookingStatus.setText(taxiBookingList.get(position).getClientStatus());

        if (taxiBookingList.get(position).getStatusCotrav()<=1){
            viewHolder.bookingStatus.setText(taxiBookingList.get(position).getClientStatus());
            viewHolder.bookingDropDate.setText("Not Assigned");
        }
        else {
            if (taxiBookingList.get(position).getStatusCotrav() == 3||taxiBookingList.get(position).getStatusCotrav() == 2)
                viewHolder.bookingDropDate.setText("Not Assigned");

            viewHolder.bookingStatus.setText(taxiBookingList.get(position).getCotravStatus());
        }
        if (taxiBookingList.get(position).getStatusCotrav()>=4){

            try {
                Date date1 = viewHolder.df.parse(taxiBookingList.get(position).getPickupDatetime());
                viewHolder.bookingDropDate.setText(viewHolder.dateFormat.format(date1));
                viewHolder.bookingDropTime.setText(viewHolder.timeFormat.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return taxiBookingList.size();
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
        private TextView bookingTourType;
        private TextView bookingDateTime;

        private Button btnDetails;
        private ImageView imageViewCall,imageViewDetails,imageViewCancel;
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bookingId = itemView.findViewById(R.id.refrence_no);
            bookingStatus = itemView.findViewById(R.id.booking_status);
            bookingSpocName = itemView.findViewById(R.id.spoc_name);

            bookingDateTime=itemView.findViewById(R.id.booking_date);
            bookingTourType=itemView.findViewById(R.id.tour_type);
            bookingTourType.setVisibility(View.VISIBLE);

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


        }

        @Override
        public void onClick(View view) {

            int id=view.getId();
            final int position=getAdapterPosition();
            switch (id){
                case R.id.imageDetails:
                    Toast.makeText(context, "Detail btn", Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Detail btn", Toast.LENGTH_SHORT).show();
                    DetailTaxiBookingFragment detailBookingFragment = new DetailTaxiBookingFragment();
                    Bundle args=new Bundle();
                    args.putString("details", GsonStringConvertor.gsonToString(taxiBookingList.get(position)));
                    args.putString("bookingId",taxiBookingList.get(position).getId().toString());
                    args.putString("booking_status","Cancelled");
                    detailBookingFragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.pager_container, detailBookingFragment).
                            addToBackStack(null).commit();

                    break;

            }

        }
    }
}
