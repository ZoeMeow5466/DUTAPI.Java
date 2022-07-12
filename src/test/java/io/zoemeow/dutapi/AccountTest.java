package io.zoemeow.dutapi;

import io.zoemeow.dutapi.objects.AccountInformation;
import io.zoemeow.dutapi.objects.SubjectFeeItem;
import io.zoemeow.dutapi.objects.SubjectScheduleItem;
import io.zoemeow.dutapi.objects.customrequest.CustomResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

class AccountTest {
    @Test
    void finalTest() throws Exception {
        String sessionId = null;
        String user = "102190147";
        String pass = "cloney1301";
        Integer year = 21;
        Integer semester = 2;

        // Initialize Session ID
        sessionId = initialize();
        System.out.println(sessionId);

        // Login
        login(sessionId, user, pass);

        // Subject schedule
        getSubjectSchedule(sessionId, year, semester);

        // Subject fee
        getSubjectFee(sessionId, year, semester);

        // Account information
        getAccountInformation(sessionId);

        // Logout
        logout(sessionId);
    }

    static String initialize() throws IOException {
        CustomResponse response = Account.getSessionId();
        return response.getSessionId();
    }

    static void login(String sessionId, String user, String pass) throws Exception {
        Account.login(
                sessionId,
                user,
                pass
        );

        if (Account.isLoggedIn(sessionId)) {
            System.out.println("Logged in!");
        }
        else throw new Exception("This Session ID hasn't logged in!");
    }

    static void getSubjectSchedule(String sessionId, Integer year, Integer semester) throws Exception {
        ArrayList<SubjectScheduleItem> subjectScheduleList = Account.getSubjectSchedule(sessionId, year, semester);
    }

    static void getSubjectFee(String sessionId, Integer year, Integer semester) throws Exception {
        ArrayList<SubjectFeeItem> subjectFeeList = Account.getSubjectFee(sessionId, year, semester);
    }

    static void getAccountInformation(String sessionId) throws Exception {
        AccountInformation accInfo = Account.getAccountInformation(sessionId);
    }

    static void logout(String sessionId) throws Exception {
        Account.logout(sessionId);

        if (!Account.isLoggedIn(sessionId)) {
            System.out.println("Logged out!");
        }
        else throw new Exception("This Session ID hasn't logged out yet!");
    }
}