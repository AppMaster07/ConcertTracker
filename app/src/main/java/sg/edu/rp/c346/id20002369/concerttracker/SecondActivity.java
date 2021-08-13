package sg.edu.rp.c346.id20002369.concerttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Concerts> ConcertList;
    CustomAdapter caConcert;
    int requestCode = 9;
    Button btn5Stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        setTitle(getTitle().toString() + " ~ " +  getResources().getText(R.string.title_activity_second));

        lv = (ListView) this.findViewById(R.id.lv);
        btn5Stars = (Button) this.findViewById(R.id.btnShow5Stars);

        DBHelper dbh = new DBHelper(this);
        ConcertList = dbh.getAllConcerts();
        dbh.close();

        caConcert = new CustomAdapter(this, R.layout.row, ConcertList);
        lv.setAdapter(caConcert);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("concert", ConcertList.get(position));
                startActivityForResult(i, requestCode);
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                ConcertList.clear();
                ConcertList.addAll(dbh.getAllConcertsByRatings(5));
                caConcert.notifyDataSetChanged();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == this.requestCode && resultCode == RESULT_OK){
            DBHelper dbh = new DBHelper(this);
            ConcertList.clear();
            ConcertList.addAll(dbh.getAllConcerts());
            dbh.close();
            caConcert.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
