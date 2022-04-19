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

import java.io.File;

public class ServiceIncrementalTest {
    private Service service;

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

    @Test
    public void saveStudent() {
        int result = service.saveStudent("2", "paul", 936);

        Assert.assertEquals(1, result);
    }

    @Test
    public void saveStudent_saveTema() {
        service.saveStudent("2", "paul", 936);
        int result = service.saveTema("2", "descriere 1", 5, 3);

        Assert.assertEquals(1, result);
    }

    @Test
    public void saveStudent_saveTema_saveNota() {
        int result1 = service.saveTema("2", "descriere 1", 5, 3);
        int result2 = service.saveStudent("2", "paul", 936);
        int result3 = service.saveNota("2", "2", 10, 5, "fb");

        Assert.assertEquals(1, result1);
        Assert.assertEquals(1, result2);
        Assert.assertEquals(1, result3);
    }
}
