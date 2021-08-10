package sg.edu.rp.c346.id20013783.oursingapore;

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
    ArrayList<Island> islandList;
    public CustomAdapter(Context context, int resource,ArrayList<Island> objects){
        super(context, resource,objects);

        parent_context = context;
        layout_id = resource;
        islandList = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvTitle = rowView.findViewById(R.id.textViewTitle);
        TextView tvArea = rowView.findViewById(R.id.textViewArea);
        TextView tvDescription = rowView.findViewById(R.id.textViewDescription);

        RatingBar ratingBar = rowView.findViewById(R.id.ratingBar);
        // Obtain the Android Version information based on the position
         Island currentIsland = islandList.get(position);

        // Set values to the TextView to display the corresponding information
        tvTitle.setText(currentIsland.getTitle());
        tvArea.setText(currentIsland.thearea());
        tvDescription.setText(currentIsland.getDescription());
        ratingBar.setRating(currentIsland.getStars());

        return rowView;
    }

}


