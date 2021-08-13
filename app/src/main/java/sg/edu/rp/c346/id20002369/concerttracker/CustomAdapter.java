package sg.edu.rp.c346.id20002369.concerttracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Concerts> versionList;

    public CustomAdapter(Context context, int resource, ArrayList<Concerts> objects) {
        super(context, resource, objects);
        parent_context = context;
        layout_id = resource;
        versionList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvName = rowView.findViewById(R.id.tvName);
        TextView tvYear = rowView.findViewById(R.id.tvYear);
        TextView tvDesc = rowView.findViewById(R.id.tvDesc);
        TextView tvLoc = rowView.findViewById(R.id.tvLoc);
        RatingBar RBStars = rowView.findViewById(R.id.RBStar);
        ImageView imageView = rowView.findViewById(R.id.imageView);


        // Obtain the Android Version information based on the position
        Concerts currentConcert = versionList.get(position);

        // Set values to the TextView to display the corresponding information
        tvName.setText(currentConcert.getName());
        tvLoc.setText(currentConcert.getLoc());
        tvYear.setText(currentConcert.getYear() + "");
        tvDesc.setText(currentConcert.getDesc());

        if (currentConcert.getYear() >= 2019) {
            imageView.setImageResource(R.drawable.latest);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }


        if (currentConcert.getRating() == 1) {
            RBStars.setRating(1);
        }
        else if (currentConcert.getRating() == 2) {
            RBStars.setRating(2);
        }
        else if (currentConcert.getRating() == 3) {
            RBStars.setRating(3);
        }
        else if (currentConcert.getRating() == 4) {
            RBStars.setRating(4);
        }
        else if (currentConcert.getRating() == 5) {
            RBStars.setRating(5);
        }

        return rowView;
    }
}
