//package ru.timuruktus.memeexchange.FeedPart;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.widget.AbsListView;
//import android.widget.ListView;
//
//import com.softw4re.views.InfiniteListView;
//
//
//public class CustomListView extends ListView implements ICustomListView{
//
//
//    public CustomListView(Context context){
//        super(context);
//    }
//
//    public CustomListView(Context context, AttributeSet attrs){
//        super(context, attrs);
//    }
//
//    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr){
//        super(context, attrs, defStyleAttr);
//    }
//
//
//    this.setOnScrollListener(new OnScrollListener() {
//        public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//        }
//
//        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//            if(InfiniteListView.this.hasMore) {
//                int lastVisibleItem = visibleItemCount + firstVisibleItem;
//                if(lastVisibleItem >= totalItemCount && !InfiniteListView.this.loading) {
//                    InfiniteListView.this.infiniteListAdapter.onNewLoadRequired();
//                }
//
//            }
//    }
//
//    @Override
//    public void stopLoading(){
//
//    }
//}
