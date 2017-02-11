package com.example.android.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public int noOfCoffees = 2;
    int quantity = 0;
    int price=5;
    public void submitOrder(View view) {
     String miniSummary = createOrderSummary(quantity,checkState1(),checkState2(),name());
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        // intent.setType("*/*");
        String subject= getString(R.string.header)+name();
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, miniSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        // }
//        displayMessage(miniSummary);
        //composeEmail(subject,miniSummary);

    }
   // public void composeEmail(String subject,String miniSummary) {

    public String createOrderSummary(int quantity, boolean check1, boolean check2, String name){
        String summary= getString(R.string.name1,name())+ "\n" +getString(R.string.whipped)+check1+"\n" +getString(R.string.chocolate1)+check2+"\n"+getString(R.string.quan)+quantity+"\n"+getString(R.string.total)+ calculatePrice() + "\n" + getString(R.string.thanks);
        return summary;
    }
    private int calculatePrice(){
        int basePrice=0;
        if(checkState1())
            basePrice=price+1;
        if(checkState2())
            basePrice=price+1;
        if(checkState1()&& checkState2())
            basePrice=price+2;
        if(!checkState2() && !checkState1())
            basePrice=price;
        return quantity*basePrice;
    }
    public void increment(View view) {
        quantity = quantity + 1;
        if (quantity>=100){
            quantity=100;
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.upper);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        display(quantity);
    }
    public void decrement(View view) {
        quantity = quantity - 1;
        if (quantity < 0) {
            quantity = 0;
            Context context = getApplicationContext();
            CharSequence text = getString(R.string.lower);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        display(quantity);
    }
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
        //quantityTextView.setTextSize(24);
    }
    private String name(){
        EditText entered = (EditText) findViewById(R.id.name);
        return entered.getText().toString();
    }
    private boolean checkState1(){
        CheckBox checkStateWhipped=(CheckBox) findViewById(R.id.whipped_cream_checkbox);
        return checkStateWhipped.isChecked();
    }
    private boolean checkState2(){
        CheckBox checkStateWhipped=(CheckBox) findViewById(R.id.chocolate_checkbox);
        return checkStateWhipped.isChecked();
    }
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//        orderSummaryTextView.setTextColor(Color.GREEN);
//    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}