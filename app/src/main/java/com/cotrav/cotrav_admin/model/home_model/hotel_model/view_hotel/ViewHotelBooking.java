package com.cotrav.cotrav_admin.model.home_model.hotel_model.view_hotel;



import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cotrav.cotrav_admin.local_database.Converter;
import com.cotrav.cotrav_admin.model.home_model.Passanger;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "hotel_bookings_detail")
public class ViewHotelBooking {

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
    @SerializedName("from_city_id")
    @Expose
    private String fromCityId;
    @SerializedName("from_area_id")
    @Expose
    private String fromAreaId;
    @SerializedName("preferred_area")
    @Expose
    private String preferredArea;
    @SerializedName("checkin_datetime")
    @Expose
    private String checkinDatetime;
    @SerializedName("checkout_datetime")
    @Expose
    private String checkoutDatetime;
    @SerializedName("room_type_id")
    @Expose
    private String roomTypeId;
    @SerializedName("bucket_priority_1")
    @Expose
    private String bucketPriority1;
    @SerializedName("bucket_priority_2")
    @Expose
    private String bucketPriority2;
    @SerializedName("preferred_hotel")
    @Expose
    private String preferredHotel;
    @SerializedName("booking_datetime")
    @Expose
    private String bookingDatetime;
    @SerializedName("assessment_code")
    @Expose
    private String assessmentCode;
    @SerializedName("assessment_city_id")
    @Expose
    private String assessmentCityId;
    @SerializedName("no_of_seats")
    @Expose
    private String noOfSeats;
    @SerializedName("assign_hotel_id")
    @Expose
    private String assignHotelId;
    @SerializedName("assign_room_type")
    @Expose
    private String assignRoomType;
    @SerializedName("is_ac_room")
    @Expose
    private String isAcRoom;
    @SerializedName("daily_brakefast")
    @Expose
    private String dailyBrakefast;
    @SerializedName("is_prepaid")
    @Expose
    private String isPrepaid;
    @SerializedName("agent_booking_id")
    @Expose
    private String agentBookingId;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("last_action_by")
    @Expose
    private String lastActionBy;
    @SerializedName("portal_used")
    @Expose
    private String portalUsed;
    @SerializedName("total_room_price")
    @Expose
    private String totalRoomPrice;
    @SerializedName("voucher_number")
    @Expose
    private String voucherNumber;
    @SerializedName("commission_earned")
    @Expose
    private String commissionEarned;
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
    @SerializedName("hotel_type_name")
    @Expose
    private String hotelTypeName;
    @SerializedName("room_type_name")
    @Expose
    private String roomTypeName;
    @SerializedName("operator_name")
    @Expose
    private String operatorName;
    @SerializedName("operator_contact")
    @Expose
    private String operatorContact;
    @SerializedName("from_city_name")
    @Expose
    private String fromCityName;
    @SerializedName("from_area_id_name")
    @Expose
    private String fromAreaIdName;
    @SerializedName("preferred_area_name")

    @Expose
    private String preferredAreaName;
    @SerializedName("Passangers")
    @Expose
    @TypeConverters(Converter.class)
    private List<Passanger> passangers = null;

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

    public String getFromCityId() {
        return fromCityId;
    }

    public void setFromCityId(String fromCityId) {
        this.fromCityId = fromCityId;
    }

    public String getFromAreaId() {
        return fromAreaId;
    }

    public void setFromAreaId(String fromAreaId) {
        this.fromAreaId = fromAreaId;
    }

    public String getPreferredArea() {
        return preferredArea;
    }

    public void setPreferredArea(String preferredArea) {
        this.preferredArea = preferredArea;
    }

    public String getCheckinDatetime() {
        return checkinDatetime;
    }

    public void setCheckinDatetime(String checkinDatetime) {
        this.checkinDatetime = checkinDatetime;
    }

    public String getCheckoutDatetime() {
        return checkoutDatetime;
    }

    public void setCheckoutDatetime(String checkoutDatetime) {
        this.checkoutDatetime = checkoutDatetime;
    }

    public String getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(String roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getBucketPriority1() {
        return bucketPriority1;
    }

    public void setBucketPriority1(String bucketPriority1) {
        this.bucketPriority1 = bucketPriority1;
    }

    public String getBucketPriority2() {
        return bucketPriority2;
    }

    public void setBucketPriority2(String bucketPriority2) {
        this.bucketPriority2 = bucketPriority2;
    }

    public String getPreferredHotel() {
        return preferredHotel;
    }

    public void setPreferredHotel(String preferredHotel) {
        this.preferredHotel = preferredHotel;
    }

    public String getBookingDatetime() {
        return bookingDatetime;
    }

    public void setBookingDatetime(String bookingDatetime) {
        this.bookingDatetime = bookingDatetime;
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

    public String getAssignHotelId() {
        return assignHotelId;
    }

    public void setAssignHotelId(String assignHotelId) {
        this.assignHotelId = assignHotelId;
    }

    public String getAssignRoomType() {
        return assignRoomType;
    }

    public void setAssignRoomType(String assignRoomType) {
        this.assignRoomType = assignRoomType;
    }

    public String getIsAcRoom() {
        return isAcRoom;
    }

    public void setIsAcRoom(String isAcRoom) {
        this.isAcRoom = isAcRoom;
    }

    public String getDailyBrakefast() {
        return dailyBrakefast;
    }

    public void setDailyBrakefast(String dailyBrakefast) {
        this.dailyBrakefast = dailyBrakefast;
    }

    public String getIsPrepaid() {
        return isPrepaid;
    }

    public void setIsPrepaid(String isPrepaid) {
        this.isPrepaid = isPrepaid;
    }

    public String getAgentBookingId() {
        return agentBookingId;
    }

    public void setAgentBookingId(String agentBookingId) {
        this.agentBookingId = agentBookingId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLastActionBy() {
        return lastActionBy;
    }

    public void setLastActionBy(String lastActionBy) {
        this.lastActionBy = lastActionBy;
    }

    public String getPortalUsed() {
        return portalUsed;
    }

    public void setPortalUsed(String portalUsed) {
        this.portalUsed = portalUsed;
    }

    public String getTotalRoomPrice() {
        return totalRoomPrice;
    }

    public void setTotalRoomPrice(String totalRoomPrice) {
        this.totalRoomPrice = totalRoomPrice;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public String getCommissionEarned() {
        return commissionEarned;
    }

    public void setCommissionEarned(String commissionEarned) {
        this.commissionEarned = commissionEarned;
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

    public String getHotelTypeName() {
        return hotelTypeName;
    }

    public void setHotelTypeName(String hotelTypeName) {
        this.hotelTypeName = hotelTypeName;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
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

    public String getFromCityName() {
        return fromCityName;
    }

    public void setFromCityName(String fromCityName) {
        this.fromCityName = fromCityName;
    }

    public String getFromAreaIdName() {
        return fromAreaIdName;
    }

    public void setFromAreaIdName(String fromAreaIdName) {
        this.fromAreaIdName = fromAreaIdName;
    }

    public String getPreferredAreaName() {
        return preferredAreaName;
    }

    public void setPreferredAreaName(String preferredAreaName) {
        this.preferredAreaName = preferredAreaName;
    }

    public List<Passanger> getPassangers() {
        return passangers;
    }

    public void setPassangers(List<Passanger> passangers) {
        this.passangers = passangers;
    }

}