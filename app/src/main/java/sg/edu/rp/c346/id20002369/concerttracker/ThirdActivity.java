package sg.edu.rp.c346.id20002369.concerttracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {

    EditText etID, etName, etDesc, etLoc, etYear;
    Button btnCancel, btnUpdate, btnDelete;
    RatingBar rb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_third));

        rb = findViewById(R.id.ratingBarInput);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        etID = (EditText) findViewById(R.id.etID);
        etName = (EditText) findViewById(R.id.etName);
        etDesc = (EditText) findViewById(R.id.etDesc);
        etLoc = (EditText) findViewById(R.id.etLoc);
        etYear = (EditText) findViewById(R.id.etYear);

        Intent i = getIntent();
        final Concerts currentConcert = (Concerts) i.getSerializableExtra("concert");

        etID.setText(currentConcert.getId() + "");
        etName.setText(currentConcert.getName());
        etDesc.setText(currentConcert.getDesc());
        etLoc.setText(currentConcert.getLoc());
        etYear.setText(currentConcert.getYear() + "");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                currentConcert.setName(etName.getText().toString().trim());
                currentConcert.setDesc(etDesc.getText().toString().trim());
                currentConcert.setLoc(etLoc.getText().toString().trim());
                int year = 0;
                try {
                    year = Integer.valueOf(etYear.getText().toString().trim());
                } catch (Exception e) {
                    Toast.makeText(ThirdActivity.this, "Invalid year value", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentConcert.setYear(year);

                currentConcert.setRating(Math.round(rb.getRating()));
                int result = dbh.updateConcert(currentConcert);
                if (result > 0) {
                    Toast.makeText(ThirdActivity.this, "Concert updated", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent();
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(ThirdActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener((v) -> {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ThirdActivity.this);
            alertBuilder.setTitle("Danger");
            alertBuilder.setMessage("Are you sure you want to delete the Concert Information\n" + etName.getText().toString());
            alertBuilder.setCancelable(true);
            alertBuilder.setNegativeButton("Delete", (dialog, l) -> {
                DBHelper dbh = new DBHelper(ThirdActivity.this);
                int result = dbh.deleteConcert(currentConcert.getId());
                if (result > 0) {
                    Toast.makeText(ThirdActivity.this, "Concert deleted", Toast.LENGTH_LONG);
                    finish();
                } else {
                    Toast.makeText(ThirdActivity.this, "Delete failed", Toast.LENGTH_LONG);
                }

            });
            alertBuilder.setPositiveButton("Cancel", null);
            AlertDialog alertDialog = alertBuilder.create();
            alertBuilder.show();
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ThirdActivity.this);
                alertBuilder.setTitle("Danger");
                alertBuilder.setMessage("Are you sure you want to discard the changes\n");
                alertBuilder.setCancelable(true);
                alertBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alertBuilder.setPositiveButton("Do NOT Discard", null);
                AlertDialog alertDialog = alertBuilder.create();
                alertBuilder.show();
            };
        });
    }
}
