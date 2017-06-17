package xiang.bbpro.Notify;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import xiang.bbpro.BaseActivity;
import xiang.bbpro.R;

/**
 * Created by xiang on 17-6-16.
 */

public class TNotifyActivity extends BaseActivity{
    private SwipeMenuRecyclerView mRecyclerView;
    private TNotifyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Activity mContext;
    private List<Notify> queryResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        //getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.activity_notify);
        mRecyclerView = (SwipeMenuRecyclerView) findViewById(R.id.scheduleList);
        mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        mRecyclerView.setSwipeMenuItemClickListener(menuItemClickListener);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Intent i = getIntent();
        user = i.getStringExtra("email");
        //初始化视图
        initializeToolbar();
        queryResult = NotifyCommunication.Query(user);
        mAdapter = new TNotifyAdapter(user,queryResult,TNotifyActivity.this);
        mRecyclerView.setAdapter(mAdapter);
    }



    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = 150;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.color.colorPrimaryDark)
                        .setImage(R.drawable.ic_delete_forever_black_24dp)
                        //.setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        //.setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeLeftMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。aaaa
            }
        }
    };

    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView
         *                        #RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, final int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。
            // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();
            if (menuPosition == 0) {// 删除按钮被点击。
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Remove");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove(queryResult.get(adapterPosition));
                        queryResult.remove(adapterPosition);
                        mAdapter.notifyItemRemoved(adapterPosition);
                        //Toast.makeText(mContext, "现在的第" + position + "条被删除。", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        }
    };

    private void remove(Notify n){

    }

}
