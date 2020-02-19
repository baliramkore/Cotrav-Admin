package com.cotrav.cotrav_admin.model.home_model.flight_model.view_flight;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cotrav.cotrav_admin.local_database.Converter;
import com.cotrav.cotrav_admin.local_database.FlightConverter;
import com.cotrav.cotrav_admin.model.home_model.Passanger;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "flight_bookings_detail")
public class FlightBookingDetails {

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "did")
    private Integer id;

    @SerializedName("reference_no")
    @Expose
    private String referenceNo;
    @SerializedName("vendor_booking_id")
    @Expose
    private String vendorBookingId;
    @SerializedName("voucher_no")
    @Expose
    private String voucherNo;
    @SerializedName("booking_email")
    @Expose
    private String bookingEmail;
    @SerializedName("usage_type")
    @Expose
    private String usageType;
    @SerializedName("journey_type")
    @Expose
    private String journeyType;
    @SerializedName("flight_class")
    @Expose
    private String flightClass;
    @SerializedName("from_location")
    @Expose
    private String fromLocation;
    @SerializedName("to_location")
    @Expose
    private String toLocation;
    @SerializedName("booking_datetime")
    @Expose
    private String bookingDatetime;
    @SerializedName("departure_datetime")
    @Expose
    private String departureDatetime;
    @SerializedName("preferred_flight")
    @Expose
    private String preferredFlight;
    @SerializedName("assessment_code")
    @Expose
    private String assessmentCode;
    @SerializedName("no_of_seats")
    @Expose
    private String noOfSeats;
    @SerializedName("last_action_by")
    @Expose
    private String lastActionBy;
    @SerializedName("flight_type")
    @Expose
    private String flightType;
    @SerializedName("seat_type")
    @Expose
    private String seatType;
    @SerializedName("trip_type")
    @Expose
    private String tripType;
    @SerializedName("fare_type")
    @Expose
    private String fareType;
    @SerializedName("meal_is_include")
    @Expose
    private String mealIsInclude;
    @SerializedName("no_of_stops")
    @Expose
    private String noOfStops;
    @SerializedName("ticket_no")
    @Expose
    private String ticketNo;
    @SerializedName("status_client")
    @Expose
    private String statusClient;
    @SerializedName("status_cotrav")
    @Expose
    private int statusCotrav;
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
    @SerializedName("reason_booking")
    @Expose
    private String reasonBooking;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;
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
    @SerializedName("cotrav_agent_name")
    @Expose
    private String cotravAgentName;
    @SerializedName("Passangers")
    @Expose
    @TypeConverters(Converter.class)
    private List<Passanger> passangers = null;

    @SerializedName("Flights")
    @Expose
    @TypeConverters(FlightConverter.class)
    private List<Flight> flights = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getJourneyType() {
        return journeyType;
    }

    public void setJourneyType(String journeyType) {
        this.journeyType = journeyType;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getBookingDatetime() {
        return bookingDatetime;
    }

    public void setBookingDatetime(String bookingDatetime) {
        this.bookingDatetime = bookingDatetime;
    }

    public String getDepartureDatetime() {
        return departureDatetime;
    }

    public void setDepartureDatetime(String departureDatetime) {
        this.departureDatetime = departureDatetime;
    }

    public String getPreferredFlight() {
        return preferredFlight;
    }

    public void setPreferredFlight(String preferredFlight) {
        this.preferredFlight = preferredFlight;
    }

    public String getAssessmentCode() {
        return assessmentCode;
    }

    public void setAssessmentCode(String assessmentCode) {
        this.assessmentCode = assessmentCode;
    }

    public String getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(String noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public String getLastActionBy() {
        return lastActionBy;
    }

    public void setLastActionBy(String lastActionBy) {
        this.lastActionBy = lastActionBy;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getFareType() {
        return fareType;
    }

    public void setFareType(String fareType) {
        this.fareType = fareType;
    }

    public String getMealIsInclude() {
        return mealIsInclude;
    }

    public void setMealIsInclude(String mealIsInclude) {
        this.mealIsInclude = mealIsInclude;
    }

    public String getNoOfStops() {
        return noOfStops;
    }

    public void setNoOfStops(String noOfStops) {
        this.noOfStops = noOfStops;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getStatusClient() {
        return statusClient;
    }

    public void setStatusClient(String statusClient) {
        this.statusClient = statusClient;
    }

    public int getStatusCotrav() {
        return statusCotrav;
    }

    public void setStatusCotrav(int statusCotrav) {
        this.statusCotrav = statusCotrav;
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

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

}
