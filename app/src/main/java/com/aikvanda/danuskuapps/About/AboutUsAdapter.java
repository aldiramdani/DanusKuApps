package com.aikvanda.danuskuapps.About;

/**
 * Created by Satria on 4/25/2018.
 */
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import com.aikvanda.danuskuapps.R;

        import java.util.List;


public class AboutUsAdapter extends ArrayAdapter<AboutUs> {

    public AboutUsAdapter(Context context, List<AboutUs> about) {
        super(context, 0, about);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.aboutus_listitem, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        AboutUs currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
        TextView namaTextView = (TextView) listItemView.findViewById(R.id.aboutnama);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        namaTextView.setText(currentWord.getNama());

        // Find the TextView in the list_item.xml layout with the ID default_text_view.
        TextView jobTextView = (TextView) listItemView.findViewById(R.id.aboutjob);
        // Get the default translation from the currentWord object and set this text on
        // the default TextView.
        jobTextView.setText(currentWord.getJob());

        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }

}
