package com.herokuapp.booker_restful.herokuappinfo;





import com.herokuapp.booker_restful.constants.EndPoints;

import com.herokuapp.booker_restful.model.BookerPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

/**
 * Created by Jay
 */
public class HerokuappSteps {

    @Step("Creating booking with checkin : {0}, checkout: {1}, firstname: {2}, lastname: {3} totalprice : {4}, depositpaid: {5}, bookingdates :{6}")
    public ValidatableResponse createbooking( String firstname, String lastname,
                                           int totalprice, boolean depositpaid,  HashMap<Object, Object> bookingdates) {

        BookerPojo bookerPojo = new BookerPojo();

        bookerPojo.setFirstname(firstname);
        bookerPojo.setLastname(lastname);
        bookerPojo.setTotalprice(totalprice);
        bookerPojo.setDepositpaid(depositpaid);
        bookerPojo.setBookingdates(bookingdates);


        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .auth().preemptive().basic("admin","password123")
                .body(bookerPojo)
                .when()
                .post(EndPoints.CREATE_SINGLE_BOOKING)
                .then();
    }

    @Step("Getting the Booking information with ID: {0}")
    public HashMap<Object, Object> getBookingInfoByID(int bookingID) {

        HashMap<Object, Object> bookerMap = SerenityRest.given().log().all()
                .when()
                .pathParam("bookingID", bookingID)
                .get(EndPoints.GET_SINGLE_BOOKING_BY_ID)
                .then()
                .statusCode(200)
                .extract()
                .path("");

        return bookerMap;
    }

    @Step("Updating Booker information with bookingID:{0},  firstname: {1} lastname : {2}, totalprice: {3}, depositpaid{4}, bookingdates{5}")
    public ValidatableResponse updatebooking(int bookingID, String firstname, String lastname,
                                          int totalprice, boolean depositpaid,  HashMap<Object, Object> bookingdates) {

        BookerPojo bookerPojo = new BookerPojo();

        bookerPojo.setFirstname(firstname);
        bookerPojo.setLastname(lastname);
        bookerPojo.setTotalprice(totalprice);
        bookerPojo.setDepositpaid(depositpaid);
        bookerPojo.setBookingdates(bookingdates);


        return SerenityRest.given().log().all()
                .pathParam("bookingID", bookingID)
                .contentType(ContentType.JSON)
                .body(bookingID)
                .when()
                .put(EndPoints.UPDATE_BOOKING_BY_ID)
                .then();


    }
    @Step("Deleting booker information with storeID: {0}")
    public ValidatableResponse deletebooker(int bookingID){
        return SerenityRest.given().log().all()
                .pathParam("bookingID", bookingID)
                .when()
                .delete(EndPoints.DELETE_BOOKING_BY_ID)
                .then();
    }

    @Step("Getting store information with productId: {0}")
    public ValidatableResponse getbookerById(int bookingID){
        return SerenityRest.given().log().all()
                .pathParam("bookingID", bookingID)
                .when()
                .get(EndPoints.GET_SINGLE_BOOKING_BY_ID)
                .then();
    }


}
