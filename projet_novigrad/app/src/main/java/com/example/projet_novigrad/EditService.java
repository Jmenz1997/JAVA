package com.example.projet_novigrad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditService extends AppCompatActivity {

    private Button editServiceButt;
    private Service service;
    private EditText name;
    private EditText rate;
    private EditText baseName;
    private TextView errorMessage;
    private Spinner serviceList;


    //Documents and information:
    private String m_Text = "";
    private String m_Text2 = "";
    private ArrayList<String> documentList;
    private ArrayList<String> informationList;

    //Buttons for docs and info:
    private Button deleteDoc;
    private Button clearDocs;
    private Button deleteInfo;
    private Button clearInfo;



    List<String> services;
    DatabaseReference serviceRef;
    String ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);

        name = findViewById(R.id.editServiceNameText);
        rate = findViewById(R.id.editServiceWageText);
        baseName = findViewById(R.id.editBaseName);
        errorMessage = findViewById(R.id.errorMessageEdit);
        editServiceButt = findViewById(R.id.editServiceButt);
        serviceList = (Spinner)findViewById(R.id.spinner2);
        services = new ArrayList<String>();
        ref = "";

        //Buttons for documents and information
        deleteDoc = findViewById(R.id.editDeleteDoc);
        clearDocs = findViewById(R.id.editClearDocs);
        deleteInfo = findViewById(R.id.editDeleteInfo);
        clearInfo = findViewById(R.id.editClearInfo);

        //Add documents amd information button
        Button addDocs = findViewById(R.id.editDocs);
        Button addInfo = findViewById(R.id.editInfo);

        //Documents and information ListView's
        final ListView docsList = findViewById(R.id.editDocsList);
        final ListView infoList = findViewById(R.id.editInfoList);

        // Create a List from String Array elements
        documentList = new ArrayList<>();
        informationList = new ArrayList<>();

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_multiple_choice, documentList);
        final ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_multiple_choice, informationList);

        // DataBind ListView with items from ArrayAdapter
        docsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        docsList.setAdapter(arrayAdapter);
        infoList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        infoList.setAdapter(arrayAdapter1);

        updateDocsButtonVisibility();
        updateInfoButtonVisibility();

        //Documents and Information methods **************************************************************************

        //Adding the required documents to the list of documents.
        addDocs.setOnClickListener(new View.OnClickListener()
        {
            //On click ==> Ask the user for input
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditService.this);
                builder.setTitle("Add a required document:");

                // Set up the input
                final EditText input = new EditText(EditService.this);
                // Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    //Store the input in the ArrayList and add it to the dynamic ListView
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        documentList.add(m_Text);
                        updateDocsButtonVisibility();
                        arrayAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        //Adding the required information to the list of documents.
        addInfo.setOnClickListener(new View.OnClickListener()
        {
            //On click ==> Ask the user for input
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditService.this);
                builder.setTitle("Add a required field:");

                // Set up the input
                final EditText input = new EditText(EditService.this);
                // Specify the type of input expected
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    //Store the input in the ArrayList and add it to the dynamic ListView
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text2 = input.getText().toString();
                        informationList.add(m_Text2);
                        updateInfoButtonVisibility();
                        arrayAdapter1.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        //On click listeners for editing and clearing the selected items ***************************************************
        //Setting up the buttons for clearing the fields:
        clearDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                           //Clear documents list and update the adapter
                documentList.clear();
                updateDocsButtonVisibility();
                arrayAdapter.notifyDataSetChanged();
            }
        });
        clearInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                           //Clear information list and update the adapter
                informationList.clear();
                updateInfoButtonVisibility();
                arrayAdapter1.notifyDataSetChanged();
            }
        });

        //Now onto setting up the buttons for deleting the fields:
        deleteDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                           //Delete selected documents and update the adapter
                int count = docsList.getCount();
                SparseBooleanArray sparseBooleanArray = docsList.getCheckedItemPositions();
                ArrayList<String> save = new ArrayList<>();

                for ( int i = 0 ; i < count ; i ++ ) {
                    if ( !sparseBooleanArray.get(i) ) {
                        save.add(docsList.getItemAtPosition(i).toString());
                    }
                }
                documentList.clear();
                documentList.addAll(save);
                for ( int i = 0 ; i < docsList.getCount() ; i++ ) {
                    docsList.setItemChecked(i, false);
                }
                updateDocsButtonVisibility();
                arrayAdapter.notifyDataSetChanged();
            }
        });
        deleteInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                           //Delete selected documents and update the adapter
                int count = infoList.getCount();
                SparseBooleanArray sparseBooleanArray = infoList.getCheckedItemPositions();
                ArrayList<String> save = new ArrayList<>();

                for ( int i = 0 ; i < count ; i ++ ) {
                    if ( !sparseBooleanArray.get(i) ) {
                        save.add(infoList.getItemAtPosition(i).toString());
                    }
                }
                informationList.clear();
                informationList.addAll(save);
                for ( int i = 0 ; i < infoList.getCount() ; i++ ) {
                    infoList.setItemChecked(i, false);
                }
                updateInfoButtonVisibility();
                arrayAdapter1.notifyDataSetChanged();
            }
        });


        //END ********************************************************************************************************

        FirebaseDatabase.getInstance().getReference().child("Services").addListenerForSingleValueEvent( // fill the list with services
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            Service temp = new Service("",0);
                            temp.setServiceName(String.valueOf(dsp.child("serviceName").getValue()));
                            //temp.setHourlyRate(Integer.valueOf(String.valueOf(dsp.child("hourlyRate").getValue())));
                            services.add(temp.toString());
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }

                });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(                // fill the spinner in the page with contents
                this,android.R.layout.simple_spinner_item , services );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceList.setAdapter(adapter);
        editServiceButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                FirebaseDatabase.getInstance().getReference().child("Services").addListenerForSingleValueEvent( // fill the list with services
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                //Get map of users in datasnapshot
                                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                                    if (String.valueOf(dsp.child("serviceName").getValue()).equals(baseName.getText().toString().trim())) {
                                        ref = dsp.getKey();
                                        serviceRef = FirebaseDatabase.getInstance().getReference().child("Services").child(ref);

                                    }
                                }
                                if (statusValidate()) {
                                    service = new Service(name.getText().toString().trim(), Integer.valueOf(rate.getText().toString().trim()), documentList, informationList);
                                    serviceRef.child("serviceName").setValue(service.getServiceName());
                                    //serviceRef.child("hourlyRate").setValue(service.getHourlyRate());
                                    errorMessage.setText("Editing Successful!");
                                    ref = "";

                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //handle databaseError
                            }

                        });


            }
        });
    }

    //ADDITIONAL METHODS **********************************************************************************

    //UPDATE BUTTONS VISIBILITY FOR INFORMATION AND DOCUMENTS
    private void updateDocsButtonVisibility() {
        if ( documentList.isEmpty() ) {
            clearDocs.setVisibility(View.INVISIBLE);
            deleteDoc.setVisibility(View.INVISIBLE);
        } else {
            clearDocs.setVisibility(View.VISIBLE);
            deleteDoc.setVisibility(View.VISIBLE);
        }
    }

    private void updateInfoButtonVisibility() {
        if ( informationList.isEmpty() ) {
            clearInfo.setVisibility(View.INVISIBLE);
            deleteInfo.setVisibility(View.INVISIBLE);
        } else {
            clearInfo.setVisibility(View.VISIBLE);
            deleteInfo.setVisibility(View.VISIBLE);
        }
    }

    //Validate status
    private boolean statusValidate(){
        if (ref == ""){
            errorMessage.setText("! Current service does not exist");
            return false;
        }
        if (name.getText().toString().trim().equals("")) {
            errorMessage.setText("! name is empty");
            return false;
        }
        if (rate.getText().toString().trim().equals("")) {
            errorMessage.setText("! hourly rate is empty");
            return false;
        }
        if (!rate.getText().toString().trim().matches( "[0-9]+")) {
            errorMessage.setText("! wage is non-numeric");
            return false;
        }
        errorMessage.setText("");
        return true;
    }
}

