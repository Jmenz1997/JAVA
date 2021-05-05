package vahdat.android.light.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText productName;
    EditText productPrice;
    View addButton;
    ListView resultListView;
    String[] test;
    HashMap<String, String> itemSubItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        productName= findViewById(R.id.ProductName);
        productPrice = findViewById(R.id.Price);
        addButton = findViewById(R.id.ADD);

        resultListView = findViewById(R.id.result_listView);


        //itemSubItem = new HashMap<String, String>();
        //List<HashMap<String,String>> listItem= new ArrayList<>();

        //SimpleAdapter adapter = new SimpleAdapter(this, listItem, R.layout.activity_main
                //, new String[]{"Item", "subItem"}, new int[]{R.id.Item, R.id.subItem});

        //HashMap<String,String> resultMap= new HashMap<>();
        //resultListView.setAdapter(adapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String items= productName.getText().toString();
                String prices= productPrice.getText().toString();
                //resultMap.put("Item",items);
                //resultMap.put("subItems",prices);
                //listItem.add(resultMap);
               // adapter.notifyDataSetChanged();


            }
        });

        /*resultMap.put("Item",productName.getText().toString());
        resultMap.put("subItem",productPrice.getText().toString());
        listItem.add(resultMap);


        //fillData();
        //adapter.notifyDataSetChanged();

        //itemSubItem.set(adapter);






        /*List<HashMap<String,String>> listItem = new ArrayList<>();
        SimpleAdapter adapter= new SimpleAdapter(this,listItem,R.layout.activity_main
        ,new String[] {"First Line","Second Line"}, new int[] {R.id.Item,R.id.subItem});*/


    }

    /*public void fillData() {
        HashMap<String, String> item1 = new HashMap<String, String>();
        item1.put("Item", "subItem");
        itemSubItem.add(item1);

    }*/

    /*public void onClick(View button) {
        switch (button.getId()) {
            case R.id.ADD:
                break;

        }

    }*/
}
