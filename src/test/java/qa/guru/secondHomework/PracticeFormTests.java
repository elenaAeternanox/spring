package qa.guru.secondHomework;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Selenide.*;

public class PracticeFormTests {

    private final static String WELCOME_MESSAGE = "Thanks for submitting the form";

    //the variables for setting form
    private String firstName = "Ivan",
            lastName = "Ivanov",
            email = "ivan@ivanov.gmail",
            genderMale = "Male",
            phoneNumber = "1234567890",
            birthdayYear = "1981",
            birthdayMonth = "January",
            birthdayDay = "1",
            firstSubjectPrefix = "co",
            firstSubject = "Computer Science",
            secondSubjectPrefix = "e",
            secondSubject = "English",
            hobbieSport = "Sports",
            hobbieRead = "Reading",
            hobbieMusic = "Music",
            fileSource = "src/test/resources/images/",
            fileName = "Tester.jpeg",
            currentAddress = "Tverskaya str, 29 - 34",
            statePrefix = "r",
            state = "NCR",
            cityPrefix = "d",
            city = "Delhi";

    //the titles of rows for checking submit form
    private String rowStudentName = "Student Name",
            rowStudentEmail = "Student Email",
            rowGender = "Gender",
            rowMobile = "Mobile",
            rowDateOfBirth= "Date of Birth",
            rowSubjects = "Subjects",
            rowHobbies = "Hobbies",
            rowPicture = "Picture",
            rowAddress = "Address",
            rowStateAndCity = "State and City";

    @BeforeAll
    static void maximizeWindow() {
        Configuration.startMaximized = true;
    }

    @Test
    @DisplayName("Check Practice form on the 'https://demoqa.com/automation-practice-form' page")
    void fillFormTest() {
        open("https://demoqa.com/automation-practice-form");

        //Set first name, last name, email, gender and phone number
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $("#genterWrapper").$(byText(genderMale)).click();
        $("#userNumber").setValue(phoneNumber);

        //Set 01 Jan 1991 in the calendar
        $("#dateOfBirthInput").click();
        $(byClassName("react-datepicker__year-select")).$(byValue(birthdayYear)).click();
        $(byClassName("react-datepicker__month-select")).$(byText(birthdayMonth)).click();
        $(byText(birthdayDay)).click();

        //Input 'co' and in drop-down list select 'Computer Science'
        $("#subjectsInput").setValue(firstSubjectPrefix);
        $(byText(firstSubject)).click();
        $("#subjectsInput").setValue(secondSubjectPrefix);
        $(byText(secondSubject)).click();

        //Select all hobbies
        $("#hobbiesWrapper").$(byText(hobbieSport)).click();
        $("#hobbiesWrapper").$(byText(hobbieRead)).click();
        $("#hobbiesWrapper").$(byText(hobbieMusic)).click();

        //Upload picture and set the current address
        $("#uploadPicture").uploadFile(new File(fileSource + fileName));
        $("#currentAddress").setValue(currentAddress);

        //State: input 'r' and select 'NCR'
        $("#react-select-3-input").setValue(statePrefix);
        $(byText(state)).click();

        //City: input 'd' and select 'Delhi'
        $("#react-select-4-input").setValue(cityPrefix);
        $(byText(city)).click();

        $("#submit").click();

        //Check the result form after submit
        $("#modal-dialog").isDisplayed();
        $("#example-modal-sizes-title-lg").shouldHave(text(WELCOME_MESSAGE));

        checkTable(rowStudentName, firstName + ' ' + lastName);
        checkTable(rowStudentEmail, email);
        checkTable(rowGender, genderMale);
        checkTable(rowMobile, phoneNumber);
        checkTable(rowDateOfBirth, birthdayDay + " " + birthdayMonth + "," + birthdayYear);
        checkTable(rowSubjects, firstSubject + ", " + secondSubject);
        checkTable(rowHobbies, hobbieSport + ", " + hobbieRead + ", " + hobbieMusic);
        checkTable(rowPicture, fileName);
        checkTable(rowAddress, currentAddress);
        checkTable(rowStateAndCity, state + " " + city);

        $("#closeLargeModal").click();

        //Check the result form isn't displayed
        $("#modal-dialog").shouldNotBe(visible);
        $("#practice-form-wrapper").isDisplayed();
        }

    private void checkTable(String parameterName, String parameterValue) {
        $$(".table-responsive tr").filterBy(text(parameterName)).shouldHave(texts(parameterValue));
    }
}
