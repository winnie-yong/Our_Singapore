package sg.edu.rp.c346.id20013783.oursingapore;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<Island> islandList;
    CustomAdapter adapter;
    int requestCode = 9;
    Button btn5Stars;
    Button btnInsert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        setTitle(getTitle().toString() + " ~ " + getResources().getText(R.string.title_activity_second));

        lv = (ListView) this.findViewById(R.id.lv);
        btn5Stars = (Button) this.findViewById(R.id.btnShow5Stars);
        btnInsert = (Button) this.findViewById(R.id.btninsert);

        DBHelper dbh = new DBHelper(this);
        islandList = dbh.getAllIsland();
        dbh.close();

        adapter = new CustomAdapter(this, R.layout.row, islandList);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("island", islandList.get(position));
                startActivityForResult(i, requestCode);
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                islandList.clear();
                islandList.addAll(dbh.getAllIslandByStar(5));
                adapter.notifyDataSetChanged();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inflate the input.xml layout file
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.input,null);

                final EditText etInputTitle = viewDialog.findViewById(R.id.etInputTitle);
                final EditText etInputDescription = viewDialog.findViewById(R.id.etInputDescription);
                final EditText etInputArea = viewDialog.findViewById(R.id.etInputArea);
                final RatingBar etRating = viewDialog.findViewById(R.id.inputratingBar);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(SecondActivity.this);
                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Insert Island");
                myBuilder.setPositiveButton("INSERT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = etInputTitle.getText().toString().trim();
                        String description = etInputDescription.getText().toString().trim();
                        if (title.length() == 0 || description.length() == 0){
                            Toast.makeText(SecondActivity.this, "Incomplete data", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String area_str = etInputArea.getText().toString().trim();
                        int area = 0;
                        try {
                            area = Integer.valueOf(area_str);
                        } catch (Exception e) {
                            Toast.makeText(SecondActivity.this, "Invalid island", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        DBHelper dbh = new DBHelper(SecondActivity.this);
                        int rating = (int)etRating.getRating();
                        long result = dbh.insertIsland(title, description, area, rating);
                        adapter.notifyDataSetChanged();
                        dbh.close();
                        if (result != -1) {
                            Toast.makeText(SecondActivity.this, "Island inserted", Toast.LENGTH_LONG).show();
                            etInputTitle.setText("");
                            etInputDescription.setText("");
                            etInputArea.setText("");
                        } else {
                            Toast.makeText(SecondActivity.this, "Insert failed", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                myBuilder.setNegativeButton("CANCEL",null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == this.requestCode && resultCode == RESULT_OK){
            DBHelper dbh = new DBHelper(this);
            islandList.clear();
            islandList.addAll(dbh.getAllIsland());
            dbh.close();
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);


}
}