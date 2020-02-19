package com.cotrav.cotrav_admin.model.entities_model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "entities")
public class Entities
{
    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "did")
    private Integer id=1;

    @SerializedName("corporate_id")
    @Expose
    private String corporateId;
    @SerializedName("entity_name")
    @Expose
    private String entityName;
    @SerializedName("billing_city_id")
    @Expose
    private int billingCityId;
    @SerializedName("contact_person_name")
    @Expose
    private String contactPersonName;
    @SerializedName("contact_person_email")
    @Expose
    private String contactPersonEmail;
    @SerializedName("contact_person_no")
    @Expose
    private String contactPersonNo;
    @SerializedName("address_line_1")
    @Expose
    private String addressLine1;
    @SerializedName("address_line_2")
    @Expose
    private String addressLine2;
    @SerializedName("address_line_3")
    @Expose
    private String addressLine3;
    @SerializedName("gst_id")
    @Expose
    private String gstId;
    @SerializedName("pan_no")
    @Expose
    private String panNo;
    @SerializedName("is_deleted")
 /*   @Expose
    private int isDeleted;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("corporate_name")
 */   @Expose
    private String corporateName;
    @SerializedName("billing_city")
    @Expose
    private String billingCity;

    public Entities(Integer id, String corporateId, String entityName, int billingCityId, String contactPersonName,
                    String contactPersonEmail, String contactPersonNo, String addressLine1, String addressLine2,
                    String addressLine3, String gstId, String panNo, String corporateName, String billingCity) {
        this.id = id;
        this.corporateId = corporateId;
        this.entityName = entityName;
        this.billingCityId = billingCityId;
        this.contactPersonName = contactPersonName;
        this.contactPersonEmail = contactPersonEmail;
        this.contactPersonNo = contactPersonNo;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.gstId = gstId;
        this.panNo = panNo;
        /*this.isDeleted = isDeleted;
        this.created = created;
        this.modified = modified;*/
        this.corporateName = corporateName;
        this.billingCity = billingCity;
    }

    public Entities(int i, int i1, String test, int i2, String contactPersonName, String contactPersonEmail, String contactPersonNo, String jfkasfasjhkhkhhashfashfkshf, String bkashkfas, String jhjkashf, String gst6464657, String pan12345, String test_corp, String pune) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCorporateId() {
        return corporateId;
    }

    public void setCorporateId(String corporateId) {
        this.corporateId = corporateId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public int getBillingCityId() {
        return billingCityId;
    }

    public void setBillingCityId(int billingCityId) {
        this.billingCityId = billingCityId;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getContactPersonEmail() {
        return contactPersonEmail;
    }

    public void setContactPersonEmail(String contactPersonEmail) {
        this.contactPersonEmail = contactPersonEmail;
    }

    public String getContactPersonNo() {
        return contactPersonNo;
    }

    public void setContactPersonNo(String contactPersonNo) {
        this.contactPersonNo = contactPersonNo;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getGstId() {
        return gstId;
    }

    public void setGstId(String gstId) {
        this.gstId = gstId;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

/*    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
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
    }*/

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getBillingCity() {
        return billingCity;
    }

    public void setBillingCity(String billingCity) {
        this.billingCity = billingCity;
    }
}
