package xiang.bbpro.Notify;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.util.List;

import xiang.bbpro.R;

/**
 * Created by xiang on 17-6-16.
 */

public class TNotifyAdapter extends SwipeMenuAdapter<TNotifyAdapter.ResultViewHolder> {

    private List<Notify> resultList;
    private String user;
    private Context context;

    public TNotifyAdapter(String user, List<Notify> result, Context context){
        this.resultList = result;
        this.context = context;
        this.user = user;
    }

    static class ResultViewHolder extends RecyclerView.ViewHolder{
        CardView notifyView;
        TextView notifyCaption;
        TextView notifyDescription;
        TextView notifyDate;

        public ResultViewHolder(final View itemView){
            super(itemView);
            notifyView = (CardView) itemView.findViewById(R.id.card_notify);
            notifyCaption = (TextView) itemView.findViewById(R.id.notify_caption);
            notifyDate = (TextView) itemView.findViewById(R.id.notify_date);
            notifyDescription = (TextView) itemView.findViewById(R.id.notify_description);
        }
    }

    @Override
    public View onCreateContentView(ViewGroup viewGroup, int i) {
        View v;
        switch (i){
            default:
                v = LayoutInflater.from(context).inflate(R.layout.cardview_notify,viewGroup,false);
                break;
        }
        return v;
    }

    @Override
    public TNotifyAdapter.ResultViewHolder onCompatCreateViewHolder(View realContentView, int viewType){
        ResultViewHolder rvh = new ResultViewHolder(realContentView);
        return rvh;
    }

    @Override
    public void onBindViewHolder(TNotifyAdapter.ResultViewHolder mViewHolder, int i){
        mViewHolder.notifyCaption.setText(resultList.get(i).Caption);
        mViewHolder.notifyDescription.setText(resultList.get(i).Description);
        mViewHolder.notifyDate.setText(resultList.get(i).Time.toString());
    }

    @Override
    public int getItemCount(){
        return resultList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

}
