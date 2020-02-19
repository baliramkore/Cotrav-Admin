package com.cotrav.cotrav_admin.ui.utilities.employees;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.cotrav.cotrav_admin.model.employees_model.Employee;

import java.util.List;

public class EmployeesViewModel extends AndroidViewModel {
    private EmployeesRepository employeesRepository;
    private LiveData<List<Employee>> corporateEmployeesLiveData;
    private LiveData<String>  todaysConnectionError;

    public EmployeesViewModel(@NonNull Application application) {
        super(application);
        employeesRepository =new EmployeesRepository(this.getApplication());
    }
    public void InitEmployeesViewModel(String authorization, String usertype, String corporate_id){
        corporateEmployeesLiveData=employeesRepository.getAllEmployees(authorization,usertype,corporate_id);

    }

    public LiveData<List<Employee>> getCorporateEmployeesLiveData(String authorization, String usertype, String corporate_id) {
        employeesRepository.getAllEmployees(authorization,usertype,corporate_id);
        return corporateEmployeesLiveData;
    }
    public LiveData<String> getEmployeesConnectionError() {
        todaysConnectionError= employeesRepository.getEmployeeConnectionError();
        return todaysConnectionError;
    }


    public void refreshTodaysBooking(String authorization, String usertype,String corporate_id)
    {
        employeesRepository.getAllEmployees(authorization,usertype,corporate_id);
    }
}
