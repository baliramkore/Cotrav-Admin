package com.cotrav.cotrav_admin.ui.home;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cotrav.cotrav_admin.R;
import com.cotrav.cotrav_admin.model.home_model.DashBoardData;
import com.cotrav.cotrav_admin.show_booking.ShowBusBookingActivity;
import com.cotrav.cotrav_admin.show_booking.ShowFlightBookingActivity;
import com.cotrav.cotrav_admin.show_booking.ShowHotelBookingActivity;
import com.cotrav.cotrav_admin.show_booking.ShowTaxiBookingActivity;
import com.cotrav.cotrav_admin.show_booking.ShowTrainBookingActivity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import me.grantland.widget.AutofitTextView;

public class HomeFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    LifecycleRegistry lifecycleRegistry;
    private HomeViewModel homeViewModel;
    ImageView taxiImagebtn,busImageBtn,hotelImageBtn,trainImageBtn,flightImageBtn;
    SharedPreferences loginpref;
    private String Token, adminId,userId,adminName, adminEmail, Authorization, userType,corporateId;
    PieChartView pieChartView;
    private static final int REQUEST_CALL = 1;
    private List<DashBoardData> dashBoardData;
    private SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog progressDialog;
    TextView networkError;

    private AutofitTextView countTotal,
            totalInvoice,pedingInvoice,upcommingTrips,pendingTrips,activeUsers,totalBookings;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        askPhoneCallPermission();
        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        loginpref=getActivity().getSharedPreferences("login_info", Context.MODE_PRIVATE);
        Token = loginpref.getString("access_token", "n");
        Authorization="Token "+Token;
        adminId = loginpref.getString("admin_id", "n");
        adminName = loginpref.getString("user_name", "n");
        adminEmail = loginpref.getString("email", "n");
        userType = loginpref.getString("usertype", "n");
        corporateId=loginpref.getString("corporate_id", "n");
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        homeViewModel.initViewModelDashBoard(Authorization,userType,corporateId);
        homeViewModel.getDashBoardData(Authorization,userType,corporateId);

        networkError = root.findViewById(R.id.network_error);

        taxiImagebtn = root.findViewById(R.id.taxiimage);        taxiImagebtn.setOnClickListener(this);
        busImageBtn = root.findViewById(R.id.busimage);        busImageBtn.setOnClickListener(this);
        hotelImageBtn = root.findViewById(R.id.hotelimage);        hotelImageBtn.setOnClickListener(this);
        trainImageBtn = root.findViewById(R.id.trainimage);        trainImageBtn.setOnClickListener(this);
        flightImageBtn = root.findViewById(R.id.flightimage);        flightImageBtn.setOnClickListener(this);
        pieChartView = root.findViewById(R.id.chart);

         countTotal=root.findViewById(R.id.total_bookings_pi);
         totalInvoice=root.findViewById(R.id.text_totalInvoice);
         pedingInvoice=root.findViewById(R.id.text_pedingInvoice);
         upcommingTrips=root.findViewById(R.id.text_upcommingTrips);
         pendingTrips=root.findViewById(R.id.text_pendingTrips);
         activeUsers=root.findViewById(R.id.text_activeUsers);
         totalBookings=root.findViewById(R.id.text_totalBookings);
         progressDialog = new ProgressDialog(getActivity());

         if(isNetworkConnected()==true)
         {
             networkError.setVisibility(View.GONE);
             homeViewModel.initViewModelDashBoard(Authorization,userType,corporateId);

         }else
             {
                 progressDialog.setMessage("Connecting To Server");
                 progressDialog.show();
                 final Dialog dialog = new Dialog(getActivity());
                 dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                 dialog.setCancelable(false);
                 dialog.setContentView(R.layout.custom_dialog_box);
                 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                 TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
                 mDialogmsg.setText("Check Your Internet Connection.");

                 Button myes = dialog.findViewById(R.id.yes_txt);
                 Button mNo = dialog.findViewById(R.id.no_txt);

                 mNo.setVisibility(View.GONE);

                 myes.setText("Okey");
                 myes.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         dialog.dismiss();
                     }
                 });

                 dialog.show();
                 progressDialog.dismiss();
                 networkError.setVisibility(View.VISIBLE);
                 swipeRefreshLayout.setRefreshing(false);
             }
        dashBoardData =new ArrayList<>();
        homeViewModel.getDashBoardData(Authorization,userType,corporateId).observe(this, new Observer<List<DashBoardData>>() {
            @Override
            public void onChanged(List<DashBoardData> busBookingList) {
                if (busBookingList!=null && busBookingList.size()>0){
                    dashBoardData.clear();
                    dashBoardData.addAll(busBookingList);
                }
                if (dashBoardData != null && dashBoardData.size() > 0) {
                    List<SliceValue> pieData = new ArrayList<>();
                    pieData.add(new SliceValue(dashBoardData.get(0).getTaxiBookingsCount(), getResources().getColor(R.color.darkblue)));
                    pieData.add(new SliceValue(dashBoardData.get(0).getBusBookingsCount(), getResources().getColor(R.color.colorDivider)));
                    pieData.add(new SliceValue(dashBoardData.get(0).getHotelBookingsCount(), getResources().getColor(R.color.colorPrimaryLight)));
                    pieData.add(new SliceValue(dashBoardData.get(0).getTrainBookingsCount(), getResources().getColor(R.color.colorPrimary)));
                    pieData.add(new SliceValue(dashBoardData.get(0).getFlightBookingsCount(), getResources().getColor(R.color.colorSecondaryText)));
                    PieChartData pieChartData = new PieChartData(pieData);
                    pieChartData.setHasCenterCircle(true).setCenterText2("");
                    pieChartView.setPieChartData(pieChartData);
                    countTotal.setText(dashBoardData.get(0).getTotalBookingsCount().toString());
                    totalInvoice.setText(dashBoardData.get(0).getTotalInvoiceAmount().toString());
                    pedingInvoice.setText(dashBoardData.get(0).getPiTotalBookingsCount().toString());
                    upcommingTrips.setText(dashBoardData.get(0).getUcTotalBookingsCount().toString());
                    pendingTrips.setText(dashBoardData.get(0).getTotalInvoiceAmount().toString());
                    activeUsers.setText(dashBoardData.get(0).getTotalActiveUsers().toString());
                    totalBookings.setText(dashBoardData.get(0).getTotalBookingsCount().toString());
                }
                progressDialog.dismiss();
                swipeRefreshLayout.setRefreshing(false);

            }
        });



        return root;
    }

    @Override
    public void onClick(View view) {
        int id  = view.getId();



        switch (id){

                case R.id.taxiimage:
                Intent intent=new Intent(getContext(), ShowTaxiBookingActivity.class);
                startActivity(intent);
                break;

                case R.id.busimage:
                Intent int1=new Intent(getContext(), ShowBusBookingActivity.class);
                startActivity(int1);
                break;

                case R.id.hotelimage:
                Intent int2=new Intent(getContext(), ShowHotelBookingActivity.class);
                startActivity(int2);
                break;

                case R.id.trainimage:
                Intent int3=new Intent(getContext(), ShowTrainBookingActivity.class);
                startActivity(int3);
                break;

                case R.id.flightimage:
                Intent int4=new Intent(getContext(), ShowFlightBookingActivity.class);
                startActivity(int4);
                break;

        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        if (isNetworkConnected()) {
            networkError.setVisibility(View.GONE);
            homeViewModel.initViewModelDashBoard(Authorization,userType,corporateId);
        } else {
            final Dialog dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_dialog_box);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView mDialogmsg = dialog.findViewById(R.id.dialog_message);
            mDialogmsg.setText("Check Your Internet Connection.");

            Button myes = dialog.findViewById(R.id.yes_txt);
            Button mNo = dialog.findViewById(R.id.no_txt);

            mNo.setVisibility(View.GONE);

            myes.setText("Okey");
            myes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
            progressDialog.dismiss();
            networkError.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        return true;
                    }
                }
            } else {

                try {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        Log.i("update_statut", "Network is available : true");
                        return true;
                    }
                } catch (Exception e) {
                    Log.i("update_statut", "" + e.getMessage());
                }
            }
        }
        Log.i("update_statut", "Network is available : FALSE ");
        return false;

        // return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    private void askPhoneCallPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL
            );
        } else {

            // Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CALL
            );
        } else {

            //Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                askPhoneCallPermission();
            } else {

                Toast.makeText(getActivity(), "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}