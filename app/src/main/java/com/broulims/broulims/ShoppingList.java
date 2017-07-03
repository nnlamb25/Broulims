package com.broulims.broulims;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ShoppingList extends AppCompatActivity {

    //ArrayList<Item> shoppingList = new ArrayList<>();

    private Button mButton;
    final Context c = this;
/*
* A lot of this came from http://www.viralandroid.com/2016/04/android-user-input-dialog-example.html
* It basically opens user_input_dialog_box.xml, which has a textview and an edittext
* need to change the edittext to a search bar and make the users choice go into a listview with
* some sort of checkboxes
 */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        mButton = (Button) findViewById(R.id.button_addItem);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo add search functionality, get user input, add it to an array, and display it
                            }
                        })

                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        dialogBox.cancel();
                                    }
                                });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();
            }
        });
    }

   public void addItem(View view){

   }

   /*

   public class ShoppingList extends AppCompatActivity {

    //ArrayList<Item> shoppingList = new ArrayList<>();

    private Button mButton;
    final Context c = this;
/*
* A lot of this came from http://www.viralandroid.com/2016/04/android-user-input-dialog-example.html
* It basically opens user_input_dialog_box.xml, which has a textview and an edittext
* need to change the edittext to a search bar and make the users choice go into a listview with
* some sort of checkboxes

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_shopping_list);

       mButton = (Button) findViewById(R.id.button_addItem);
       mButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
               View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
               AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
               alertDialogBuilderUserInput.setView(mView);

               final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
               alertDialogBuilderUserInput
                       .setCancelable(false)
                       .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialogBox, int id) {

                       })

                       .setNegativeButton("Cancel",
                               new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialogBox, int id) {
                                       dialogBox.cancel();
                                   }
                               });

               AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
               alertDialogAndroid.show();
           }
       });
   }

    public void addItem(View view){

    }

    */


}
