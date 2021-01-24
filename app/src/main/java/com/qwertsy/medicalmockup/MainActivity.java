package com.qwertsy.medicalmockup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Set statusbar color
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      getWindow().setStatusBarColor(getResources().getColor(R.color.darkGreen, this.getTheme()));
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().setStatusBarColor(getResources().getColor(R.color.darkGreen));
    }

    // Find views
    TextView headingTextView = findViewById(R.id.heading);
    TextView descriptionTextView = findViewById(R.id.description);
    TextView resultsTitleTextView = findViewById(R.id.resultsTitle);
    TextView barcodeUserNameTextView = findViewById(R.id.barcodeUserName);
    ImageView barcodeImageView = findViewById(R.id.barcode);
    ListView resultsList = findViewById(R.id.resultsList);

    /*
    Set list dummy data, this data should be retrieved from an API call
     and will most likely be an array of objects, instead of two seperate arrays
     */
    String[] resultsDate = {
      "July 8, 2020",
      "July 1, 2020",
      "July 22, 2020"
    };

    String[] resultsStatus = {
      "Results pending",
      "Negative",
      "Negative"
    };

    // Set the list adapter
    ResultsList adapter = new ResultsList(this, resultsDate, resultsStatus);
    resultsList.setAdapter(adapter);

    // Make some API calls and then update text views based on date returned..
    // Text value should be returned from call and should NOT be hardcoded like this..
    headingTextView.setText("You are past due for a medical test");
    descriptionTextView.setText("Your current medical test was valid until Aug 13,2020. You will need to have a new COVID-19 viral test administered through MIT medical today to ensure continued access to Campus.");
    resultsTitleTextView.setText("Your results");
    barcodeUserNameTextView.setText("John Smith");

    // Generate barcode
    try {
      // Managed to replicate the value more or less with the use of my Scanny app
      String productId = "6009880243013";
      Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
      hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
      Writer codeWriter;
      codeWriter = new Code128Writer();
      BitMatrix byteMatrix = codeWriter.encode(productId, BarcodeFormat.CODE_128, 1500, 200, hintMap);
      int width = byteMatrix.getWidth();
      int height = byteMatrix.getHeight();
      Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
      for (int i = 0; i < width; i++) {
        for (int j = 0; j < height; j++) {
          bitmap.setPixel(i, j, byteMatrix.get(i, j) ? Color.BLACK : Color.WHITE);
        }
      }
      barcodeImageView.setImageBitmap(bitmap);
    } catch (Exception e) {
      Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
    }
  }
}
