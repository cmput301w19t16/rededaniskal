package ca.rededaniskal.Database;


import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import ca.rededaniskal.BusinessLogic.myCallbackBookInstance;
import ca.rededaniskal.EntityClasses.Book;
import ca.rededaniskal.EntityClasses.Book_Instance;

public class BookInstanceDb extends Entity_Database {
    private DatabaseReference mDatabase;
    private DatabaseReference bookRef;
    private Book_Instance book;

    public BookInstanceDb() {
        super();
        mDatabase = db.getReference(References.BOOKINSTANCE.reference());
    }

    @Override
    public DatabaseReference getReference() {
        return mDatabase;
    }
    public DatabaseReference currentUserBooklist(){ return mDatabase.child(getUID());}

    public void getBookInstance(String ownerID, String bookID, final myCallbackBookInstance mcbbi){
        bookRef = mDatabase.child(ownerID).child(bookID);
        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    mcbbi.onCallback(dataSnapshot.getValue(Book_Instance.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public String getStorageId() {
        return mDatabase.child(getUID()).push().getKey();
    }

    public boolean addBookInstance(Book_Instance book_instance){

//         DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("all-books");
//         String key = mDatabase.push().getKey();
//         mDatabase.child(key).setValue(book_instance);
//         return mainRef.child(getUID())
        return mDatabase.child(getUID())
                .child(book_instance.getBookID())
                .setValue(book_instance)
                .isSuccessful()&&db.getReference(References.ALLBOOKS.reference())
                .child(book_instance.getBookID())
                .setValue(book_instance)
                .isSuccessful();
    }

    public void viewBookList(){}

    public void viewSingleBook(){}




}
