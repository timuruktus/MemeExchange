package ru.timuruktus.memeexchange.Utils;

//import android.content.Context;
//import android.content.res.TypedArray;
//import android.os.Build;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.AdapterView;
//import android.widget.FrameLayout;
//import android.widget.ListAdapter;
//import android.widget.ListView;
//
//import com.softw4re.views.InfiniteListAdapter;
//import com.softw4re.views.InfiniteListView;
//
//import java.util.List;
//
//import ru.timuruktus.memeexchange.FeedPart.FeedAdapter;
//import ru.timuruktus.memeexchange.POJO.Meme;
//import ru.timuruktus.memeexchange.R;
//
//
//public class MyInfiniteListView extends FrameLayout{
//    private SwipeRefreshLayout swipeRefreshLayout;
//    private ListView listView;
//    private boolean loading = false;
//    private View loadingView;
//    private boolean hasMore = false;
//    private FeedAdapter infiniteListAdapter;
//
//    public MyInfiniteListView(Context context) {
//        super(context);
//        this.init(context, (AttributeSet)null);
//    }
//
//    public MyInfiniteListView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        this.init(context, attrs);
//    }
//
//    public MyInfiniteListView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        this.init(context, attrs);
//    }
//
//    private void init(Context context, AttributeSet attrs) {
//        View view = inflate(context, com.softw4re.views.R.layout.infinitelistview, this);
//        this.loadingView = LayoutInflater.from(context).inflate(com.softw4re.views.R.layout.item_loading, (ViewGroup)null, false);
//        this.swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(com.softw4re.views.R.id.swipeRefreshLayout);
//        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            public void onRefresh() {
//                MyInfiniteListView.this.infiniteListAdapter.onRefresh();
//            }
//        });
//        this.listView = (ListView)view.findViewById(com.softw4re.views.R.id.listView);
//        this.listView.setFooterDividersEnabled(false);
//        if(attrs != null) {
//            TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, com.softw4re.views.R.styleable.InfiniteListView, 0, 0);
//
//            try {
//                int swipeRefreshIndicatorColor = typedArray.getColor(com.softw4re.views.R.styleable.InfiniteListView_swipeRefreshIndicatorColor, context.getResources().getColor(R.color.colorPrimary));
//                this.swipeRefreshLayout.setColorSchemeColors(new int[]{swipeRefreshIndicatorColor});
//                boolean scrollbarVisible = typedArray.getBoolean(com.softw4re.views.R.styleable.InfiniteListView_scrollbarVisible, true);
//                this.listView.setVerticalScrollBarEnabled(scrollbarVisible);
//                boolean dividerVisible = typedArray.getBoolean(com.softw4re.views.R.styleable.InfiniteListView_dividerVisible, true);
//                if(!dividerVisible) {
//                    this.listView.setDividerHeight(0);
//                }
//            } finally {
//                typedArray.recycle();
//            }
//        }
//
//    }
//
//    public void setAdapter(FeedAdapter<Meme> infiniteListAdapter) {
//        this.infiniteListAdapter = infiniteListAdapter;
//        this.listView.setAdapter(infiniteListAdapter);
//        this.listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//            }
//
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                if(MyInfiniteListView.this.hasMore) {
//                    int lastVisibleItem = visibleItemCount + firstVisibleItem;
//                    if(lastVisibleItem >= totalItemCount && !MyInfiniteListView.this.loading) {
//                        MyInfiniteListView.this.infiniteListAdapter.onNewLoadRequired();
//                    }
//
//                }
//            }
//        });
//        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                MyInfiniteListView.this.infiniteListAdapter.onItemClick(position);
//            }
//        });
//        this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                MyInfiniteListView.this.infiniteListAdapter.onItemLongClick(position);
//                return true;
//            }
//        });
//    }
//
//    public void addNewItem(Meme newItem) {
//        this.infiniteListAdapter.addNewItem(this.listView, newItem, null);
//    }
//
//    public void addAll(List<Meme> newItems) {
//        this.infiniteListAdapter.addAll(this.listView, newItems);
//    }
//
//    public void clearList() {
//        this.hasMore = false;
//        this.infiniteListAdapter.clearList(this.listView, null);
//    }
//
//    public void startLoading() {
//        if(this.listView.getFooterViewsCount() > 0) {
//            this.listView.removeFooterView(this.loadingView);
//        }
//
//        this.loading = true;
//        if(!this.swipeRefreshLayout.isRefreshing() && this.listView.getFooterViewsCount() == 0) {
//            if(Build.VERSION.SDK_INT >= 19) {
//                this.listView.addFooterView(this.loadingView, (Object)null, false);
//            } else {
//                this.listView.setAdapter((ListAdapter)null);
//                this.listView.addFooterView(this.loadingView, (Object)null, false);
//                this.listView.setAdapter(this.infiniteListAdapter);
//                this.listView.setSelection(this.infiniteListAdapter.getCount() - 1);
//            }
//        }
//
//    }
//
//    public void stopLoading() {
//        if(this.listView.getFooterViewsCount() > 0) {
//            this.listView.removeFooterView(this.loadingView);
//        }
//
//        this.swipeRefreshLayout.setRefreshing(false);
//        this.loading = false;
//    }
//
//    public void hasMore(boolean hasMore) {
//        this.hasMore = hasMore;
//    }
//}
