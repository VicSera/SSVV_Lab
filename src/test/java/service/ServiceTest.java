package service;

import org.junit.Assert;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.util.stream.Stream;

public class ServiceTest {
    private Service service = new Service(
            new StudentXMLRepository(new StudentValidator(), "studenti_test.xml"),
            new TemaXMLRepository(new TemaValidator(), "teme_test.xml"),
            new NotaXMLRepository(new NotaValidator(), "note_test.xml")
    );

    @Test
    public void saveStudent_validData_studentSaved() {
        service.saveStudent("5", "paul", 936);

        Assert.assertEquals(1, Stream.of(service.findAllStudents().spliterator()).count());
    }

    @Test
    public void saveStudent_nullId_validationException() {
        try {
            service.saveStudent(null, "paul", 936);
            Assert.fail("Validation Exception not thrown");
        } catch (ValidationException ignored) {

        }
    }

    @Test
    public void saveStudent_emptyId_validationException() {
        try {
            service.saveStudent("", "paul", 936);
            Assert.fail("Validation Exception not thrown");
        } catch (ValidationException ignored) {

        }
    }

    @Test
    public void saveStudent_nullName_validationException() {
        try {
            service.saveStudent("5", null, 936);
            Assert.fail("Validation Exception not thrown");
        } catch (ValidationException ignored) {

        }
    }

    @Test
    public void saveStudent_emptyName_validationException() {
        try {
            service.saveStudent("5", "", 936);
            Assert.fail("Validation Exception not thrown");
        } catch (ValidationException ignored) {

        }
    }

    @Test
    public void saveStudent_groupTooLow_validationException() {
        try {
            service.saveStudent("5", "paul", 1);
            Assert.fail("Validation Exception not thrown");
        } catch (ValidationException ignored) {

        }
    }

    @Test
    public void saveStudent_groupTooHigh_validationException() {
        try {
            service.saveStudent("5", "paul", 1000);
            Assert.fail("Validation Exception not thrown");
        } catch (ValidationException ignored) {

        }
    }
}
