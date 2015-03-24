package nz.co.smetz.flatos;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Sara on 21/3/2015.
 */
public class UpdateHolder extends RecyclerView.ViewHolder{
    protected TextView vMessage;
    protected TextView vTime;

    public UpdateHolder(View v){
        super(v);
        vMessage = (TextView) v.findViewById(R.id.messageTextView);
        vTime = (TextView) v.findViewById(R.id.timeTextView);
    }
}
