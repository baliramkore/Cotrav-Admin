package com.cotrav.cotrav_admin.model.employees_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmployeeApiResponse {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("Employees")
    @Expose
    private List<Employee> employees = null;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
