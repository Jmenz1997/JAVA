package vahdat.android.light.productcatalog_lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ListView itemListResult;

    Button addI;
    EditText name;
    EditText price;


    TextView item;
    TextView subItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.ProductName);
        price = (EditText) findViewById(R.id.Price);
        itemListResult = (ListView) findViewById(R.id.ItemList);
        addI = (Button) findViewById(R.id.ADD_Button);
        item = (TextView) findViewById(R.id.Item);
        subItem = (TextView) findViewById(R.id.SubItem);
        final HashMap<String, String> itemList = new HashMap<>();
        itemList.put("item", "subItem");
        itemList.put("1st", "2nd");
        final List<HashMap<String, String>> itemList2 = new ArrayList<>();
        final SimpleAdapter adapter = new SimpleAdapter(this, itemList2, R.layout.support_simple_spinner_dropdown_item
                , new String[]{"First Line", "Second Line"}, new int[]{R.id.Item, R.id.SubItem});
        final SimpleAdapter adapter1 = new SimpleAdapter(this, itemList2, R.layout.support_simple_spinner_dropdown_item
                , new String[]{"First Line", "Second Line"}, new int[]{R.id.ProductName, R.id.Price});
        final Iterator it = itemList.entrySet().iterator();
        while (it.hasNext()) {
            HashMap<String, String> resultMap = new HashMap<>();
            Map.Entry pair = (Map.Entry) it.next();
            resultMap.put("First line", pair.getKey().toString());
            resultMap.put("Second Line", pair.getValue().toString());
            itemList2.add(resultMap);
        }
        itemListResult.setAdapter(adapter);
        addI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String names = name.getText().toString();
                String subItem = price.getText().toString();
                HashMap<String, String> itemList = new HashMap<>();
                itemList.put("name", "Price");
                itemList.put(names, subItem);
                itemListResult.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
            }
        });


    }
}