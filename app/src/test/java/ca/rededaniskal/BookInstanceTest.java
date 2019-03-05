package ca.rededaniskal;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;

import static org.junit.Assert.*;

import static org.junit.Assert.assertTrue;

public class BookInstanceTest {

    @Test
    public void TestRequests() {
        Request request = new Request("John", "Jack", "Friend");

        BookInstance book = new BookInstance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available");

        book.addRequest(request);
        ArrayList<Request> recievedRequest = book.getAllRequests();

        assert recievedRequest.contains(request);

        book.deleteRequest(request);

        ArrayList<Request> allRequests = book.getAllRequests();

        assertEquals(0, allRequests.size());
    }

    @Test
    public void TestOwner() {
        BookInstance book = new BookInstance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available");

        String owner = book.getOwner();

        assertEquals("Jill", owner);
    }

    @Test
    public void TestPossessor() {

        BookInstance book = new BookInstance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available");

        String possessor = book.getPossessor();

        assertEquals("Jill", possessor);

        book.setPossessor("Rose Edmond");

        possessor = book.getPossessor();

        assertNotEquals("Jill", possessor);
    }

    @Test
    public void TestCondition() {

        BookInstance book = new BookInstance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available");

        String condition = book.getCondition();

        assertEquals("Perfect!", condition);

        book.setCondition("Cover torn");

        condition = book.getCondition();

        assertNotEquals("Perfect!", condition);
    }

    @Test
    public void TestStatus() {

        BookInstance book = new BookInstance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available");

        String status = book.getStatus();

        assertEquals("Available", status);

        book.setStatus("Borrowed");

        status = book.getStatus();

        assertNotEquals("Available", status);
    }

    @Test
    public void isRequestedBy(){
        BookInstance book = new BookInstance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available");

        User User = new User("John", "email", "location");
        User User2 = new User("Jill", "email", "location");
        Request request = new Request("John", "Jack", "Friend");

        book.addRequest(request);

        assertTrue(book.isRequestedBy(User));
        assertFalse(book.isRequestedBy(User2));
    }
}