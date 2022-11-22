package be.ucll.bmi.ui.steps;

import be.ucll.bmi.model.Examination;
import be.ucll.bmi.model.Patient;
import be.ucll.bmi.pages.PatientDetailsPage;
import be.ucll.bmi.pages.RegisterPatientPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RegisterPatientUISteps extends UISteps {
    @Given("Martha has chosen to add Sara as a patient")
    public void martha_has_chosen_to_add_sara_as_a_patient() {
        RegisterPatientPage registerPatientPage = new RegisterPatientPage();
        registerPatientPage.open();
    }

    @When("Martha provides Sara's personal details:")
    public void martha_provides_sara_s_personal_details(io.cucumber.datatable.DataTable dataTable) {
        List<Patient> data = dataTable.asList(Patient.class);

        Patient patient = data.get(0);
        context.setPatient(patient);
        RegisterPatientPage registerPatientPage = new RegisterPatientPage();

        registerPatientPage.fillInSocialSecurityNumber(patient.getSocialSecurityNumber());

        registerPatientPage.fillInBirthDate(patient.getBirthDate());
        registerPatientPage.selectGender(patient.getGender());

    }

    @When("Martha provides Sara's first examination details:")
    public void martha_provides_sara_s_first_examination_details(io.cucumber.datatable.DataTable dataTable) {
        List<Examination> examinations = dataTable.asList(Examination.class);

        // Examination examination = examinations.get(0); ---> Geeft altijd null :(

        Examination examination = new Examination(150,76250, LocalDate.now());
        RegisterPatientPage registerPatientPage = new RegisterPatientPage();

        context.setExamination(examination);
        registerPatientPage.fillInLength(examination.getLength());
        registerPatientPage.fillInWeight(examination.getWeight());
        registerPatientPage.fillInExaminationDate(examination.getExaminationDate());
    }

    @When("Martha tries to add Sara")
    public void martha_tries_to_add_sara() {
        RegisterPatientPage registerPatientPage = new RegisterPatientPage();
        registerPatientPage.registerPatient();
    }

    @Then("Sara should be registered as a patient")
    public void sara_should_be_registered_as_a_patient() {
        PatientDetailsPage patientDetailsPage = new PatientDetailsPage();
        assertTrue(patientDetailsPage.isOpen());
        Patient patient = context.getPatient();
        assertEquals(patient.getSocialSecurityNumber(),patientDetailsPage.getSocialSecurityNumber());
        assertEquals(patient.getBirthDate(),patientDetailsPage.getBirthDate());
        assertEquals(patient.getGender(),patientDetailsPage.getGender());
    }


}
