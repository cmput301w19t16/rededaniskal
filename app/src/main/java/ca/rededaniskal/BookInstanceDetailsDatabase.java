package ca.rededaniskal;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class BookInstanceDetailsDatabase {

    String BOOK_INSTANCE = "book-instances";
    String MASTER_BOOK = "master-books";
    String USER_BOOK = "user-books";
    String BORROWED_BOOK= "borrowed-books";
    String USERS = "users";
    String ISBN = "isbn";
    String REQUEST = "requests";
    String BOOKREQUEST = "book-request";
    private BookInstance bookInstance;

    private DatabaseReference myRef;


    //Constructor
    public BookInstanceDetailsDatabase() {
        this.myRef = FirebaseDatabase.getInstance().getReference();

    }

    //Update Book Details
    //returns true if successful

    public boolean updateBookInstance(BookInstance bookInstance){

        DatabaseReference bookInstanceRef = this.myRef.child(BOOK_INSTANCE);


            HashMap<String, Object> bookChildUpdates = new HashMap<String, Object>();
            HashMap<String, Object> masterChildUpdates = new HashMap<String, Object>();
            bookChildUpdates
                    .put(USER_BOOK+bookInstance.getOwner()+bookInstance.getBookID(), bookInstance);
            if (bookInstance.getStatus().equals('b')) {
            bookChildUpdates
                    .put(BORROWED_BOOK+ bookInstance.getPossessor() + bookInstance.getBookID(), bookInstance);
        }

        bookChildUpdates
                .put(ISBN+bookInstance.getISBN()+bookInstance.getBookID(), bookInstance);

        return bookInstanceRef.updateChildren(bookChildUpdates).isSuccessful();

    }

    public boolean deleteBook(BookInstance bookInstance){
        DatabaseReference bookInstanceRef = this.myRef.child(BOOK_INSTANCE);


        HashMap<String, Object> bookChildUpdates = new HashMap<String, Object>();
        HashMap<String, Object> masterChildUpdates = new HashMap<String, Object>();
        bookChildUpdates
                .put(USER_BOOK+'/'+bookInstance.getOwner()+'/'+bookInstance.getBookID(), null);
        if (bookInstance.getStatus().equals('b')) {
            bookChildUpdates
                    .put(BORROWED_BOOK+'/' + bookInstance.getPossessor() +'/'+ bookInstance.getBookID(), null);
        }

        bookChildUpdates
                .put(ISBN+'/'+bookInstance.getISBN()+'/'+bookInstance.getBookID(), null);
        bookChildUpdates
                .put(REQUEST+'/'+BOOKREQUEST +'/'+ bookInstance.getBookID(), null);



        return bookInstanceRef.updateChildren(bookChildUpdates).isSuccessful();

    }



    //Read book details from the database by ID
    public BookInstance getBookInstanceByID(String bookID) {
        DatabaseReference bookRef = myRef.child(BOOK_INSTANCE).child(bookID);

        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setBookInstance((BookInstance) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return this.bookInstance;

    }





    public void setBookInstance(BookInstance bookInstance) {
        this.bookInstance = bookInstance;
    }



}
