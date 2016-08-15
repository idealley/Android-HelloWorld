package com.example.samuel.helloworld;


import java.text.NumberFormat;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int coffeePrice = 5;
    int creamPrice = 2;
    int chocolatePrice = 1;
    boolean cream = false;
    boolean chocolate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {
        quantity++;
        display(quantity);
        setPriceTitle("Price", "#666666");
        displayPrice(calculatePrice(quantity, coffeePrice));
    }
    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {
        if(quantity >= 1){
            quantity--;
        }

        display(quantity);
        setPriceTitle("Price", "#666666");
        displayPrice(calculatePrice(quantity, coffeePrice));

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {


        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
        cream = whippedCreamCheckBox.isChecked();
        chocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(quantity, coffeePrice);

        String message = createOrderSummary(quantity, price, cream, chocolate);



        if(quantity >= 1 && !getEmail().toString().trim().equals("") && !getName().toString().trim().equals("")) {
            setPriceTitle("Order Summary", "#666666");
            //displayMessage(message);
            sendEmail(getEmail(),"Coffee order", message);
        } else {
            setPriceTitle("Error", "#FF0000");
            displayMessage("The email and the name are mandatory for the order");
        }

        if(quantity == 0){
            setPriceTitle("Error", "#FF0000");
            displayMessage("Please order at least a coffee");
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price value on the screen.
     */

    private void displayPrice(int number){
        TextView priceTextView = (TextView) findViewById(
                R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));

    }

    private String getName(){
        EditText name = (EditText) findViewById(R.id.name);
        String nameString = name.getText().toString();
        return nameString;
    }

    private String getEmail(){
        EditText name = (EditText) findViewById(R.id.email);
        String emailString = name.getText().toString();
        return emailString;
    }

    /**
     * This method displays the given price value on the screen.
     */

    private void displayMessage(String message){
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(message);

    }

    /**
     * This method calculate the total of the order
     * @param quantity
     * @param price
     * @return
     */

    private int calculatePrice(int quantity, int price){

        int total = 0 ;
        total = quantity * price;

        if(cream) {

            total = quantity * price + creamPrice;

        }

        if(chocolate) {

            total = quantity * price + chocolatePrice;

        }

        if(cream && chocolate) {
            total = quantity * price + creamPrice + chocolatePrice;
        }


        return total ;
    }

    /**
     * This method formats the order summary
     * @param price
     * @return
     */

    private String createOrderSummary( int quantity, int price, boolean cream, boolean chocolate){

        String summary = "";

        if(cream){
            summary += "You have chosen to add whipped cream\nThe price of it is: " + creamPrice + "$\n";
        }
        if(chocolate){
            summary += "You have chosen to add some chocolate!\nThe price of it is: " + chocolatePrice + "$";
        }

        summary += "\nQuantity: " + quantity + "\n" + "Total price: " + price + "$\n" + "Thank you!";

        return summary;
    }

    public void setPriceTitle(String title, String color){
        TextView priceTitle = (TextView) findViewById(R.id.price_title);
        priceTitle.setText(title);
        priceTitle.setTextColor(Color.parseColor(color));
    }

    private void sendEmail(String email, String subject, String textMessage){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + email));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, textMessage);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

    }
}