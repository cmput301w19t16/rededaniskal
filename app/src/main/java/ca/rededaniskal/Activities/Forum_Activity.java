package ca.rededaniskal.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ca.rededaniskal.BusinessLogic.ForumAdapter;
import ca.rededaniskal.EntityClasses.Master_Book;
import ca.rededaniskal.EntityClasses.Thread;

import ca.rededaniskal.EntityClasses.Forum;
import ca.rededaniskal.R;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Forum_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ForumAdapter forumAdapter;
    private Forum forum;
    private TextView title;
    private FloatingActionButton addTopic;
    private FirebaseAuth mAuth;
    private RatingBar myRating, avgRating;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_forum_);

        //TODO: get Forum from DB or something
        Master_Book b = new Master_Book("Happy Potter","JK Rowling","1234567890", null);


        forum = new Forum("1234567890"); //For testing

        title = findViewById(R.id.Title);
        addTopic = findViewById(R.id.addTopic);
        myRating = findViewById(R.id.rating2);
        avgRating = findViewById(R.id.rating);

        //Set the recycler view
        recyclerView = findViewById(R.id.ViewThreads);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        forumAdapter = new ForumAdapter(this, forum.getThreads());
        recyclerView.setAdapter(forumAdapter);
        forumAdapter.notifyDataSetChanged();
        //GetAllUsersDB db = new GetAllUsersDB(this); TODO something with this

        FirebaseUser currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();

        //Set the Rating bars

        //TODO: set the rating bars

        title.setText(forum.getIsbn());

        //Set the add topic on Click listener
        addTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.add_forum_thread, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                final Button add = popupView.findViewById(R.id.add);
                final EditText topic = popupView.findViewById(R.id.topic);
                final EditText text = popupView.findViewById(R.id.text);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Verify Fields have info
                        Boolean valid = TRUE;

                        if (topic.getText().toString().trim().equalsIgnoreCase("")){
                            topic.setError("Please add a topic title");
                            valid = FALSE;
                        }

                        if (text.getText().toString().trim().equalsIgnoreCase("")){
                            text.setError("Please body text for this topic");
                            valid = FALSE;
                        }

                        if (valid.equals(TRUE)){
                            //add to Forum

                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            String uid = currentUser.getUid();
                            String textStr  = text.getText().toString();
                            String topicStr  = topic.getText().toString();


                            Thread newThread = new Thread(uid, textStr, topicStr);

                            forum.addPost(newThread);

                            forumAdapter.notifyDataSetChanged();
                            popupWindow.dismiss();
                        }
                    }
                });
            }
        });

        //Add new rating if user clicks on the bar
        myRating.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                float rating = myRating.getRating();

                FirebaseUser currentUser = mAuth.getCurrentUser();
                String uid = currentUser.getUid();

                //TODO: add rating
                //forum.addRatingToBook(uid, rating);
                //avgRating.setRating(forum.getBook().getAvgRating());

                return myRating.onTouchEvent(event);
            }
        });
    }
}
