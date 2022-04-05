package service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

import java.io.File;
import java.util.stream.Stream;

public class ServiceTest {
    private Service service;

    @Test
    public void saveStudent_validData_studentSaved() {
        service.saveStudent("5", "paul", 936);

        Assert.assertEquals(1, Stream.of(service.findAllStudents().spliterator()).count());
    }

    @Test(expected = ValidationException.class)
    public void saveStudent_nullId_validationException() {
        service.saveStudent(null, "paul", 936);
    }

    @Test(expected = ValidationException.class)
    public void saveStudent_emptyId_validationException() {
        service.saveStudent("", "paul", 936);
    }

    @Test(expected = ValidationException.class)
    public void saveStudent_nullName_validationException() {
        service.saveStudent("5", null, 936);
    }

    @Test(expected = ValidationException.class)
    public void saveStudent_emptyName_validationException() {
        service.saveStudent("5", "", 936);
    }

    @Test(expected = ValidationException.class)
    public void saveStudent_groupTooLow_validationException() {
        service.saveStudent("5", "paul", 1);
    }

    @Test(expected = ValidationException.class)
    public void saveStudent_groupTooHigh_validationException() {
        service.saveStudent("5", "paul", 1000);
    }

    @Test(expected = ValidationException.class)
    public void saveStudent_groupSlightlyAboveUpperBound_validationException() {
        service.saveStudent("5", "paul", 938);
    }

    @Test
    public void saveStudent_groupSlightlyBelowUpperBound_validationException() {
        service.saveStudent("5", "paul", 936);

        Assert.assertEquals(1, Stream.of(service.findAllStudents().spliterator()).count());
    }

    @Test
    public void saveStudent_groupAtUpperBound_validationException() {
        service.saveStudent("5", "paul", 937);

        Assert.assertEquals(1, Stream.of(service.findAllStudents().spliterator()).count());
    }

    @Test
    public void saveStudent_groupSlightlyAboveLowerBound_validationException() {
        service.saveStudent("5", "paul", 112);

        Assert.assertEquals(1, Stream.of(service.findAllStudents().spliterator()).count());
    }

    @Test(expected = ValidationException.class)
    public void saveStudent_groupSlightlyBelowLowerBound_validationException() {
        service.saveStudent("5", "paul", 110);
    }

    @Test
    public void saveStudent_groupAtLowerBound_validationException() {
        service.saveStudent("5", "paul", 111);

        Assert.assertEquals(1, Stream.of(service.findAllStudents().spliterator()).count());
    }

    @Test
    public void saveTema_validData_returns1() {
        int result = service.saveTema("1", "Descriere", 7, 5);

        Assert.assertEquals(1, result);
    }

    @Test
    public void saveTema_duplicateId_returns0() {
        service.saveTema("1", "Descriere", 7, 5);
        int result = service.saveTema("1", "Descriere", 7, 5);

        Assert.assertEquals(0, result);
    }

    @Test
    public void saveTema_path1_returns1() {
        int result = service.saveTema("1", "descriere 1", 5, 3);

        Assert.assertEquals(1, result);
    }

    @Test
    public void saveTema_path2_returns0() {
        service.saveTema("2", "descriere 2", 6, 4);
        int result = service.saveTema("2", "descriere 2", 6, 4);

        Assert.assertEquals(0, result);
    }

    @Before
    public void cleanup() {
        new File("note_test.xml").delete();
        new File("studenti_test.xml").delete();
        new File("teme_test.xml").delete();

        service = new Service(
            new StudentXMLRepository(new StudentValidator(), "studenti_test.xml"),
            new TemaXMLRepository(new TemaValidator(), "teme_test.xml"),
            new NotaXMLRepository(new NotaValidator(), "note_test.xml")
        );
    }
}
