package com.example.projet_novigrad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class AddService extends AppCompatActivity {

    private EditText name, rate;
    private Button addingServiceButt;
    private TextView errorMessage;
    private String m_Text = "";
    private String m_Text2 = "";
    private ArrayList<String> documentList;
    private ArrayList<String> informationList;

    //Buttons
    private Button deleteDoc;
    private Button clearDocs;
    private Button deleteInfo;
    private Button clearInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        name = findViewById(R.id.serviceNameText);
        errorMessage=findViewById(R.id.textView2);
        rate = findViewById(R.id.serviceWageText);
        addingServiceButt = findViewById(R.id.addingServiceButt);

        //Buttons for documents and information
        deleteDoc = findViewById(R.id.deleteDoc);
        clearDocs = findViewById(R.id.clearDocs);
        deleteInfo = findViewById(R.id.deleteInfo);
        clearInfo = findViewById(R.id.clearInfo);

        //Add documents amd information button
        Button addDocs = findViewById(R.id.addDocs);
        Button addInfo = findViewById(R.id.addInfo);

        //Documents and information ListView's
        final ListView docsList = findViewById(R.id.docsList);
        final ListView infoList = findViewById(R.id.infoList);

        // Create a List from String Array elements
        documentList = new ArrayList<String>();
        informationList = new ArrayList<String>();

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_multiple_choice, documentList);
        final ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_multiple_choice, informationList);

        //Fill documents and information list with examples
        documentList.add("Photo ID");
        documentList.add("Proof of residency");
        informationList.add("Full name");
        informationList.add("Date of birth");

        // DataBind ListView with items from ArrayAdapter
        docsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        docsList.setAdapter(arrayAdapter);
        infoList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        infoList.setAdapter(arrayAdapter1);

        //On Click listeners for adding documents and information *********************************************************

        //Adding the required documents to the list of documents.
        addDocs.setOnClickListener(new View.OnClickListener()
        {
            //On click ==> Ask the user for input
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddService.this);
                builder.setTitle("Add a required document:");

                // Set up the input
                final EditText input = new EditText(AddService.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(AddService.this);
                builder.setTitle("Add a required field:");

                // Set up the input
                final EditText input = new EditText(AddService.this);
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

        //ADD SERVICE BUTTON CODE *************************************************************************
        addingServiceButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (statusValidate()){
                    final String Name = name.getText().toString().trim();
                    final String Rate = rate.getText().toString().trim();
                    final ArrayList<String> Documents = new ArrayList<>(documentList);
                    final ArrayList<String> Information = new ArrayList<>(documentList);

                    Service user = new Service(
                            Name,
                            Double.parseDouble(Rate),
                            Documents,
                            Information
                    );

                    FirebaseDatabase.getInstance().getReference("Services")
                            .push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(AddService.this, "Service addition successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddService.this, AddService.class));
                            } else {
                                Toast.makeText(AddService.this, "Service addition failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }

    //ADDITIONAL METHODS ***********************************************************************************

    //UPDATE BUTTONS VISIBILITY FOR INFORMATION AND DOCUMENTS
    private void updateDocsButtonVisibility() {
        if ( documentList.isEmpty() ) {
            clearDocs.setVisibility(View.GONE);
            deleteDoc.setVisibility(View.GONE);
        } else {
            clearDocs.setVisibility(View.VISIBLE);
            deleteDoc.setVisibility(View.VISIBLE);
        }
    }

    private void updateInfoButtonVisibility() {
        if ( informationList.isEmpty() ) {
            clearInfo.setVisibility(View.GONE);
            deleteInfo.setVisibility(View.GONE);
        } else {
            clearInfo.setVisibility(View.VISIBLE);
            deleteInfo.setVisibility(View.VISIBLE);
        }
    }

    //Validate input
    public boolean statusValidate() {
        if (!(errorMessage.getText().toString().equals("")) ){
            errorMessage.setText(" ");
        }

        if (name.getText().toString().equals("")) {
            errorMessage.setText("! name is empty");
            return false;
        }
        if (rate.getText().toString().trim().equals("")) {
            errorMessage.setText("! hourly rate is empty");
            return false;
        }
        if (documentList.size()==0) {
            errorMessage.setText("! please enter a document");
            return false;
        }
        if (informationList.size()==0) {
            errorMessage.setText("! please enter an information");
            return false;
        }


        return true;
    }
}









