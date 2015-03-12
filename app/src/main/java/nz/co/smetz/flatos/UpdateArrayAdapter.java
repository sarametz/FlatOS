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

    public UpdateArrayAdapter(List<Update> itemList, Context ctx) {
        super(ctx, android.R.layout.simple_list_item_1, itemList);
        this.itemList = itemList;
        this.context = ctx;
    }

    public int getCount() {
        if (itemList != null)
            return itemList.size();
        return 0;
    }

    public Update getItem(int position) {
        if (itemList != null)
            return itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (itemList != null)
            return itemList.get(position).hashCode();
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.update_list_item, null);
        }

        Update u = itemList.get(position);
        TextView text = (TextView) v.findViewById(R.id.messageTextView);
        text.setText(u.getMessage());

        TextView text1 = (TextView) v.findViewById(R.id.timeTextView);
        text1.setText(u.getTime().toString());;

        return v;
    }

    public List<Update> getData() {
        return itemList;
    }

    public void setData(List<Update> itemList) {
        this.itemList = itemList;
    }

}
