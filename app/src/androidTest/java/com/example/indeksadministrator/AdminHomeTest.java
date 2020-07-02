package com.example.indeksadministrator;

import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class AdminHomeTest {

    @Rule
    public ActivityTestRule<AdminHome> activityTestRule = new ActivityTestRule<AdminHome>(AdminHome.class);
    private AdminHome adminHome = null;

    private Button buttonUpdateSubject;
    private Button buttonUpdateData;
    private Button buttonUpdateGrades;
    private Button buttonSendEmail;
    private Button buttonLogOut;

    private FirebaseUser user;


    @Before
    public void setUp() throws Exception {

        adminHome = activityTestRule.getActivity();

        user = FirebaseAuth.getInstance().getCurrentUser();

        buttonUpdateSubject = adminHome.findViewById(R.id.buttonUpdateSubject);
        buttonUpdateData = adminHome.findViewById(R.id.buttonUpdateData);
        buttonUpdateGrades = adminHome.findViewById(R.id.buttonUpdateGrades);
        buttonSendEmail = adminHome.findViewById(R.id.buttonSendEmail);
        buttonLogOut = adminHome.findViewById(R.id.buttonLogOut);

    }

    @Test
    public void testLoginUser() {
        assertNotNull(user);
    }

    @Test
    public void initElements() {
        assertNotNull(buttonUpdateSubject);
        assertNotNull(buttonUpdateData);
        assertNotNull(buttonUpdateGrades);
        assertNotNull(buttonSendEmail);
        assertNotNull(buttonLogOut);
    }

    @After
    public void tearDown() throws Exception {
        adminHome = null;
    }
}