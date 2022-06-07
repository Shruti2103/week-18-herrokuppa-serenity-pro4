package com.herokuapp.booker_restful.herokuppainfo;





import com.herokuapp.booker_restful.testbase.TestBase;

import com.herokuapp.booker_restful.herokuappinfo.HerokuappSteps;

import com.herokuapp.booker_restful.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class HerokuappCURDTestWithSteps extends TestBase {




    static String firstname = "James" + TestUtils.getRandomValue();
    static String lastname = "Brown" + TestUtils.getRandomValue();
    static int totalprice = 111 ;
    static boolean depositpaid = false;



    static int bookingID;

    @Steps
    HerokuappSteps herokuappSteps;

    @Title("This will create a new booker")
    @Test
    public void test001() {
        HashMap<Object, Object> bookingdates = new HashMap<>();
        bookingdates.put("checkin", 2018-01-01);
        bookingdates.put("checkout", 2019-01-01);

        ValidatableResponse response = herokuappSteps.createbooking(firstname,  lastname,
         totalprice, depositpaid,  bookingdates);
        response.log().all().statusCode(201);
        bookingID = response.log().all().extract().path("id");
        System.out.println(bookingID);
    }

    @Title("Verify if the booking was added to the application")
    @Test
    public void test002() {
        HashMap<Object, Object> bookingvarrify = herokuappSteps.getBookingInfoByID(bookingID);
        Assert.assertThat(bookingvarrify, hasValue(bookingID));
        System.out.println(bookingvarrify);
    }
    @Title("Update the booking information and verify the updated information")
    @Test
    public void test003() {

        firstname = firstname + "_updated";
        HashMap<Object, Object> bookingdates = new HashMap<>();
        bookingdates.put("checkin", 6);
        bookingdates.put("checkout", 7);

        herokuappSteps.updatebooking( bookingID,firstname,  lastname,  totalprice,depositpaid,
                bookingdates) .log().all().statusCode(200);


        HashMap<Object, Object> bookMap = herokuappSteps.getBookingInfoByID(bookingID);
        Assert.assertThat(bookMap, hasValue(firstname));

    }
    @Title("Delete the booking and verify if the booking is deleted!")
    @Test
    public void test004() {
        herokuappSteps.deletebooker(bookingID).statusCode(200);
        herokuappSteps.getbookerById(bookingID).statusCode(404);
    }


}
