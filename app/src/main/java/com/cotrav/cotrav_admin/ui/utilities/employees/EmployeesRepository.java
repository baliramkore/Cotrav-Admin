package com.cotrav.cotrav_admin.ui.utilities.employees;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.cotrav.cotrav_admin.local_database.AdminRoomDatabase;
import com.cotrav.cotrav_admin.model.employees_model.Employee;
import com.cotrav.cotrav_admin.model.employees_model.EmployeeApiResponse;
import com.cotrav.cotrav_admin.retrofit.ConfigRetrofit;
import com.cotrav.cotrav_admin.retrofit.GetUtilitiesApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeesRepository {
    private LiveData<List<Employee>> employeesDBLiveData;
    AdminRoomDatabase adminDb;
    private GetUtilitiesApi getUtilitiesApi;
    private MutableLiveData<List<Employee>> employeesLiveData;
    private MutableLiveData<String> employeeConnectionError;
    Application application;

    public EmployeesRepository(Application application){
        this.application = application;
        getUtilitiesApi = ConfigRetrofit.configRetrofit(GetUtilitiesApi.class);

        employeesLiveData = new MutableLiveData<>();
        employeeConnectionError = new MutableLiveData<>();


    }
    public LiveData<List<Employee>> getAllEmployees(String authorization, String usertype, String corporate_id)
    {
        getUtilitiesApi.getEmployees(authorization,usertype,corporate_id).enqueue(
                new Callback<EmployeeApiResponse>() {

                    @Override

                    public void onResponse(Call<EmployeeApiResponse> call, Response<EmployeeApiResponse> response)
                    {
                        if (response.isSuccessful()){
                            if (response.body().getEmployees()!=null && response.body().getEmployees().size()>0) {

                                employeesLiveData.setValue(response.body().getEmployees());


                            }
                            else {
                                employeeConnectionError.setValue("No Employee Available");
                            }
                        }else {
                            employeeConnectionError.setValue("Connection Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<EmployeeApiResponse> call, Throwable t)
                    {
                        employeeConnectionError.setValue("Please Check Internet Connection");

                    }
                });
        return employeesLiveData;
    }


    public MutableLiveData<String> getEmployeeConnectionError() {
        return employeeConnectionError;
    }
}
