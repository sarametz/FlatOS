package nz.co.smetz.flatos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sara on 9/3/2015.
 */
public class UpdateArrayAdapter extends RecyclerView.Adapter<UpdateHolder> {
    private List<Update> updateList;
    private Context context;

    public UpdateArrayAdapter(Context ctx) {
        updateList = new ArrayList<Update>();
        this.context = ctx;
    }

    @Override
    public int getItemCount() {
        return updateList.size();
    }

    @Override
    public void onBindViewHolder(UpdateHolder updateHolder, int i) {
        Update u = updateList.get(i);
        updateHolder.vMessage.setText(u.getMessage());
        updateHolder.vTime.setText(u.getTime());
    }

    @Override
    public UpdateHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.update_list_item, viewGroup, false);

        return new UpdateHolder(itemView);
    }

    public void setData(List<Update> itemList) {
        updateList.clear();
        if (itemList != null) {
            for(Update u: itemList){
                updateList.add(u);
            }
            this.notifyDataSetChanged();
        }
    }

}
