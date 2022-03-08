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
        service.saveStudent("1", "Test Student", 936);

        Assert.assertEquals(1, Stream.of(service.findAllStudents().spliterator()).count());
    }

    @Test
    public void saveStudent_invalidData_validationException() {
        try {
            service.saveStudent("1", "Test Student", 9360);
            Assert.fail("Validation Exception not thrown");
        } catch (ValidationException ignored) {

        }
    }
}
