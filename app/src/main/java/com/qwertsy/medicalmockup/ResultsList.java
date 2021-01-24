package com.qwertsy.medicalmockup;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultsList extends ArrayAdapter<String> {

  private final Activity context;
  private final String[] date;
  private final String[] status;

  public ResultsList(Activity context, String[] date, String[] status) {
    super(context, R.layout.results_list, date);

    this.context = context;
    this.date = date;
    this.status = status;

  }

  public View getView(int position, View view, ViewGroup parent) {
    LayoutInflater inflater = context.getLayoutInflater();
    View rowView = inflater.inflate(R.layout.results_list, null, true);

    TextView dateText = (TextView) rowView.findViewById(R.id.dateText);
    TextView statusText = (TextView) rowView.findViewById(R.id.statusText);

    dateText.setText(date[position]);
    statusText.setText(status[position]);

    if(statusText.getText() == "Negative")
    {
      statusText.setBackgroundResource(R.drawable.border_negative);
    }
    else
    {
      statusText.setBackgroundResource(R.drawable.border_pending);
    }

    return rowView;

  }

  ;

}
