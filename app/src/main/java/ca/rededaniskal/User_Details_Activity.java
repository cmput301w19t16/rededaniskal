package ca.rededaniskal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class User_Details_Activity extends AppCompatActivity {

    private User user_received;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__details_);
        
        Intent intent = getIntent();
        Bundle bundle_extras = intent.getExtras();
        if (bundle_extras != null){
            user_received = (User) getIntent().getSerializableExtra("KEY");
        }
    }
}
