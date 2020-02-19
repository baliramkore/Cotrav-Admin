package com.cotrav.cotrav_admin.model.home_model.taxi_model.show_taxi;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cotrav.cotrav_admin.local_database.Converter;
import com.cotrav.cotrav_admin.model.home_model.Passanger;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "taxi_bookings")
public class TaxiBooking {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    @NonNull
    private String id;
    @SerializedName("reference_no")
    @Expose
    private String referenceNo;

    @SerializedName("booking_type")
    @Expose
    private String bookingType;

    @SerializedName("vendor_booking_id")
    @Expose
    private String vendorBookingId;

    @SerializedName("voucher_no")
    @Expose
    private String voucherNo;

    @SerializedName("booking_email")
    @Expose
    private String bookingEmail;

    @SerializedName("operator_id")
    @Expose
    private String operatorId;

    @SerializedName("taxi_id")
    @Expose
    private String taxiId;

    @SerializedName("driver_id")
    @Expose
    private String driverId;

    @SerializedName("tour_type")
    @Expose
    private String tourType;

    @SerializedName("city_id")
    @Expose
    private String cityId;

    @SerializedName("drop_location")
    @Expose
    private String dropLocation;

    @SerializedName("pickup_location")
    @Expose
    private String pickupLocation;

    @SerializedName("booking_date")
    @Expose
    private String bookingDate;

    @SerializedName("pickup_datetime")
    @Expose
    private String pickupDatetime;

    @SerializedName("assessment_code")
    @Expose
    private String assessmentCode;

    @SerializedName("assessment_city_id")
    @Expose
    private String assessmentCityId;

    @SerializedName("no_of_seats")
    @Expose
    private String noOfSeats;

    @SerializedName("status_client")
    @Expose
    private String statusClient;

    @SerializedName("status_cotrav")
    @Expose
    private Integer statusCotrav;

    @SerializedName("last_action_by")
    @Expose
    private String lastActionBy;

    @SerializedName("days")
    @Expose
    private String days;

    @SerializedName("rate_id")
    @Expose
    private String rateId;

    @SerializedName("is_invoice")
    @Expose
    private String isInvoice;

    @SerializedName("invoice_id")
    @Expose
    private String invoiceId;

    @SerializedName("group_id")
    @Expose
    private String groupId;

    @SerializedName("subgroup_id")
    @Expose
    private String subgroupId;

    @SerializedName("spoc_id")
    @Expose
    private String spocId;

    @SerializedName("corporate_id")
    @Expose
    private String corporateId;

    @SerializedName("billing_entity_id")
    @Expose
    private String billingEntityId;

    @SerializedName("ass_code")
    @Expose
    private String assCode;

    @SerializedName("reason_booking")
    @Expose
    private String reasonBooking;

    @SerializedName("created")
    @Expose
    private String created;

    @SerializedName("modified")
    @Expose
    private String modified;

    @SerializedName("taxi_type_name")
    @Expose
    private String taxiTypeName;

    @SerializedName("city_name")
    @Expose
    private String cityName;

    @SerializedName("client_status")
    @Expose
    private String clientStatus;

    @SerializedName("cotrav_status")
    @Expose
    private String cotravStatus;

    @SerializedName("user_name")
    @Expose
    private String userName;

    @SerializedName("user_contact")
    @Expose
    private String userContact;

    @SerializedName("operator_name")
    @Expose
    private String operatorName;

    @SerializedName("operator_contact")
    @Expose
    private String operatorContact;

    @SerializedName("cotrav_agent_name")
    @Expose
    private String cotravAgentName;

    @SerializedName("Passangers")
    @Expose
    @TypeConverters(Converter.class)
    private List<Passanger> passangers = null;


    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getVendorBookingId() {
        return vendorBookingId;
    }

    public void setVendorBookingId(String vendorBookingId) {
        this.vendorBookingId = vendorBookingId;
    }

    public String getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(String voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getBookingEmail() {
        return bookingEmail;
    }

    public void setBookingEmail(String bookingEmail) {
        this.bookingEmail = bookingEmail;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(String taxiId) {
        this.taxiId = taxiId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getTourType() {
        return tourType;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPickupDatetime() {
        return pickupDatetime;
    }

    public void setPickupDatetime(String pickupDatetime) {
        this.pickupDatetime = pickupDatetime;
    }

    public String getAssessmentCode() {
        return assessmentCode;
    }

    public void setAssessmentCode(String assessmentCode) {
        this.assessmentCode = assessmentCode;
    }

    public String getAssessmentCityId() {
        return assessmentCityId;
    }

    public void setAssessmentCityId(String assessmentCityId) {
        this.assessmentCityId = assessmentCityId;
    }

    public String getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(String noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public String getStatusClient() {
        return statusClient;
    }

    public void setStatusClient(String statusClient) {
        this.statusClient = statusClient;
    }

    public Integer getStatusCotrav() {
        return statusCotrav;
    }

    public void setStatusCotrav(Integer statusCotrav) {
        this.statusCotrav = statusCotrav;
    }

    public String getLastActionBy() {
        return lastActionBy;
    }

    public void setLastActionBy(String lastActionBy) {
        this.lastActionBy = lastActionBy;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getRateId() {
        return rateId;
    }

    public void setRateId(String rateId) {
        this.rateId = rateId;
    }

    public String getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(String isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getSubgroupId() {
        return subgroupId;
    }

    public void setSubgroupId(String subgroupId) {
        this.subgroupId = subgroupId;
    }

    public String getSpocId() {
        return spocId;
    }

    public void setSpocId(String spocId) {
        this.spocId = spocId;
    }

    public String getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(String corporateId) {
        this.corporateId = corporateId;
    }

    public String getBillingEntityId() {
        return billingEntityId;
    }

    public void setBillingEntityId(String billingEntityId) {
        this.billingEntityId = billingEntityId;
    }

    public String getAssCode() {
        return assCode;
    }

    public void setAssCode(String assCode) {
        this.assCode = assCode;
    }

    public String getReasonBooking() {
        return reasonBooking;
    }

    public void setReasonBooking(String reasonBooking) {
        this.reasonBooking = reasonBooking;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getTaxiTypeName() {
        return taxiTypeName;
    }

    public void setTaxiTypeName(String taxiTypeName) {
        this.taxiTypeName = taxiTypeName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public String getCotravStatus() {
        return cotravStatus;
    }

    public void setCotravStatus(String cotravStatus) {
        this.cotravStatus = cotravStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorContact() {
        return operatorContact;
    }

    public void setOperatorContact(String operatorContact) {
        this.operatorContact = operatorContact;
    }

    public String getCotravAgentName() {
        return cotravAgentName;
    }

    public void setCotravAgentName(String cotravAgentName) {
        this.cotravAgentName = cotravAgentName;
    }

    public List<Passanger> getPassangers() {
        return passangers;
    }

    public void setPassangers(List<Passanger> passangers) {
        this.passangers = passangers;
    }
}
