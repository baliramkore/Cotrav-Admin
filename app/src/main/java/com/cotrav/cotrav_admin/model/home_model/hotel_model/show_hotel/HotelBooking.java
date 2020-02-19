package com.cotrav.cotrav_admin.model.home_model.hotel_model.show_hotel;

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

@Entity(tableName = "hotel_bookings")
public class HotelBooking  {

    @SerializedName("id")
    @Expose
    @PrimaryKey
    @NonNull
    private String id;

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
    private Integer fromCityId;

    @SerializedName("booking_type")
    @Expose
    private String bookingType;

    @SerializedName("from_area_id")
    @Expose
    private Integer fromAreaId;
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
    private Integer roomTypeId;

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
    private Integer assessmentCityId;
    @SerializedName("no_of_seats")
    @Expose
    private Integer noOfSeats;
    @SerializedName("assign_hotel_id")
    @Expose
    private Integer assignHotelId;
    @SerializedName("assign_room_type")
    @Expose
    private Integer assignRoomType;
    @SerializedName("is_ac_room")
    @Expose
    private Integer isAcRoom;
    @SerializedName("daily_brakefast")
    @Expose
    private Integer dailyBrakefast;
    @SerializedName("is_prepaid")
    @Expose
    private Integer isPrepaid;
    @SerializedName("agent_booking_id")
    @Expose
    private String agentBookingId;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("last_action_by")
    @Expose
    private Integer lastActionBy;
    @SerializedName("portal_used")
    @Expose
    private String portalUsed;
    @SerializedName("total_room_price")
    @Expose
    private Double totalRoomPrice;
    @SerializedName("voucher_number")
    @Expose
    private String voucherNumber;
    @SerializedName("commission_earned")
    @Expose
    private Double commissionEarned;
    @SerializedName("status_client")
    @Expose
    private Integer statusClient;
    @SerializedName("status_cotrav")
    @Expose
    private Integer statusCotrav;
    @SerializedName("is_invoice")
    @Expose
    private Integer isInvoice;
    @SerializedName("invoice_id")
    @Expose
    private String invoiceId;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;
    @SerializedName("subgroup_id")
    @Expose
    private Integer subgroupId;
    @SerializedName("spoc_id")
    @Expose
    private Integer spocId;
    @SerializedName("corporate_id")
    @Expose
    private Integer corporateId;
    @SerializedName("billing_entity_id")
    @Expose
    private Integer billingEntityId;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
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

    public Integer getFromCityId() {
        return fromCityId;
    }

    public void setFromCityId(Integer fromCityId) {
        this.fromCityId = fromCityId;
    }

    public Integer getFromAreaId() {
        return fromAreaId;
    }

    public void setFromAreaId(Integer fromAreaId) {
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

    public Integer getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(Integer roomTypeId) {
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

    public Integer getAssessmentCityId() {
        return assessmentCityId;
    }

    public void setAssessmentCityId(Integer assessmentCityId) {
        this.assessmentCityId = assessmentCityId;
    }

    public Integer getNoOfSeats() {
        return noOfSeats;
    }

    public void setNoOfSeats(Integer noOfSeats) {
        this.noOfSeats = noOfSeats;
    }

    public Integer getAssignHotelId() {
        return assignHotelId;
    }

    public void setAssignHotelId(Integer assignHotelId) {
        this.assignHotelId = assignHotelId;
    }

    public Integer getAssignRoomType() {
        return assignRoomType;
    }

    public void setAssignRoomType(Integer assignRoomType) {
        this.assignRoomType = assignRoomType;
    }

    public Integer getIsAcRoom() {
        return isAcRoom;
    }

    public void setIsAcRoom(Integer isAcRoom) {
        this.isAcRoom = isAcRoom;
    }

    public Integer getDailyBrakefast() {
        return dailyBrakefast;
    }

    public void setDailyBrakefast(Integer dailyBrakefast) {
        this.dailyBrakefast = dailyBrakefast;
    }

    public Integer getIsPrepaid() {
        return isPrepaid;
    }

    public void setIsPrepaid(Integer isPrepaid) {
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

    public Integer getLastActionBy() {
        return lastActionBy;
    }

    public void setLastActionBy(Integer lastActionBy) {
        this.lastActionBy = lastActionBy;
    }

    public String getPortalUsed() {
        return portalUsed;
    }

    public void setPortalUsed(String portalUsed) {
        this.portalUsed = portalUsed;
    }

    public Double getTotalRoomPrice() {
        return totalRoomPrice;
    }

    public void setTotalRoomPrice(Double totalRoomPrice) {
        this.totalRoomPrice = totalRoomPrice;
    }

    public String getVoucherNumber() {
        return voucherNumber;
    }

    public void setVoucherNumber(String voucherNumber) {
        this.voucherNumber = voucherNumber;
    }

    public Double getCommissionEarned() {
        return commissionEarned;
    }

    public void setCommissionEarned(Double commissionEarned) {
        this.commissionEarned = commissionEarned;
    }

    public Integer getStatusClient() {
        return statusClient;
    }

    public void setStatusClient(Integer statusClient) {
        this.statusClient = statusClient;
    }

    public Integer getStatusCotrav() {
        return statusCotrav;
    }

    public void setStatusCotrav(Integer statusCotrav) {
        this.statusCotrav = statusCotrav;
    }

    public Integer getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Integer isInvoice) {
        this.isInvoice = isInvoice;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getSubgroupId() {
        return subgroupId;
    }

    public void setSubgroupId(Integer subgroupId) {
        this.subgroupId = subgroupId;
    }

    public Integer getSpocId() {
        return spocId;
    }

    public void setSpocId(Integer spocId) {
        this.spocId = spocId;
    }

    public Integer getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(Integer corporateId) {
        this.corporateId = corporateId;
    }

    public Integer getBillingEntityId() {
        return billingEntityId;
    }

    public void setBillingEntityId(Integer billingEntityId) {
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