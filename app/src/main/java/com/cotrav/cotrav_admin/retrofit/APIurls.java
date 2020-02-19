package com.cotrav.cotrav_admin.retrofit;

public class APIurls
{
    //static final String LIVE_URL="http://192.168.0.8:8000/api/";
    static final String LIVE_URL="http://cotrav.in/api/";
    public static final String BASE_URL = LIVE_URL;
    public static final String LOGIN="login";
    public static final String LOGOUT=BASE_URL+"logout";
    public static final String billingEntity=BASE_URL+"billing_entities";
    public static final String companyRates=BASE_URL+"company_rates";
    public static final String adminGroups=BASE_URL+"groups";
    public static final String adminGroupAuth=BASE_URL+"view_group_auth";
    public static final String adminSubGroupAuth=BASE_URL+"view_subgroup_auth";
    public static final String adminSubGroups=BASE_URL+"subgroups";
    public static final String assesmentCities=BASE_URL+"assessment_cities";
    public static final String assesmentCodes="assessment_codes";
    public static final String getAdmins=BASE_URL+"admins";
    public static final String getSpocs=BASE_URL+"spocs";
    public static final String getAllCities=BASE_URL+"cities";
    public static final String getEmployees=BASE_URL+"employees";
    public static String getPackages = BASE_URL + "corporate_package";
    static final String TAXITYPE = "taxi_types";


    public static final String addEntities=BASE_URL+"add_billing_entity";
    public static final String updateEntities=BASE_URL+"update_billing_entity";

    public static final String addAdmins=BASE_URL+"add_admin";
    public static final String updateAdmins=BASE_URL+"update_admin";

    public static final String addEmployee=BASE_URL+"add_employee";
    public static final String updateEmployee=BASE_URL+"update_employee";

    public static final String addSpocs=BASE_URL+"add_spoc";
    public static final String updateSpocs=BASE_URL+"update_spoc";

    public static final String addAssCode="add_assessment_codes";
    public static final String updateAssCode=BASE_URL+"update_assessment_codes";

    public static final String addAssCity=BASE_URL+"add_assessment_cities";
    public static final String updateAssCity=BASE_URL+"update_assessment_cities";

    public static final String addAdminGroup=BASE_URL+"add_group";
    public static final String updateAdminGroup=BASE_URL+"update_group";

    public static final String addAdminSubGroup=BASE_URL+"add_subgroup";
    public static final String updateAdminSubGroup=BASE_URL+"update_subgroup";

    public static final String deleteEntities=BASE_URL+"delete_billing_entity";
    public static final String deleteRates=BASE_URL+"delete_rate";

    public static final String deleteAssCity=BASE_URL+"delete_assessment_cities";
    public static final String deleteAssCode=BASE_URL+"delete_assessment_codes";

    public static final String deleteGroup=BASE_URL+"delete_group";
    public static final String deleteSubGroup=BASE_URL+"delete_subgroup";

    public static final String deleteAdmins=BASE_URL+"delete_admin";
    public static final String deleteSpocs=BASE_URL+"delete_spoc";

    public static final String deleteEmployees=BASE_URL+"delete_employee";
    static final String DASHBOARD="admin_dashboard";
    static final String ROOMTYPE = "room_types";
    static final String HOTELTYPE = "hotel_types";

    static final String TAXIBOOKINGS="admin_taxi_bookings";
    static final String BUSBOOKINGS="admin_bus_bookings";
    static final String TRAINBOOKINGS="admin_train_bookings";
    static final String HOTELBOOKINGS="admin_hotel_bookings";
    static final String FLIGHTBOOKINGS="admin_flight_bookings";
    static final String TAXIBOOKINGSDETAILS="view_taxi_booking";
    static final String BUSBOOKINGSDETAILS="view_bus_booking";
    static final String TRAINBOOKINGSDETAILS="view_train_booking";
    static final String HOTELBOOKINGSDETAILS="view_hotel_booking";
    static final String FLIGHTBOOKINGSDETAILS="view_flight_booking";

    static final String REJECTBOOKING_TAXI="admin_reject_taxi_booking";
    static final String REJECTBOOKING_TRAIN="admin_reject_train_booking";
    static final String REJECTBOOKING_BUS="admin_reject_bus_booking";
    static final String REJECTBOOKING_FLIGHT="admin_reject_flight_booking";
    static final String REJECTBOOKING_HOTEL="admin_reject_hotel_booking";

    public static final String addTaxiBooking ="add_taxi_booking";
    public static final String addBusBooking="add_bus_booking";
    public static final String addHotelBooking = BASE_URL + "add_hotel_booking";
    public static final String addTrainBooking = BASE_URL + "add_train_booking";
    public static final String addFlightBooking = BASE_URL + "add_flight_booking";
    public static final String TRAINTYPE = "train_types";
    public static final String STATIONCITIES = "railway_stations";

}
