package nz.co.smetz.flatos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sara on 9/3/2015.
 */
public class UpdateArrayAdapter extends ArrayAdapter<Update> {
    private List<Update> itemList;
    private Context context;

    public UpdateArrayAdapter(Context ctx) {
        super(ctx, R.layout.update_list_item);
        this.context = ctx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.update_list_item, null);
        }

        Update u = getItem(position);
        TextView text = (TextView) v.findViewById(R.id.messageTextView);
        text.setText(u.getMessage());

        TextView text1 = (TextView) v.findViewById(R.id.timeTextView);
        text1.setText(u.getTime().toString());;

        return v;
    }

    public void setData(List<Update> itemList) {
        clear();
        if (itemList != null) {
            for(Update u: itemList){
                add(u);
            }
        }
    }

}
