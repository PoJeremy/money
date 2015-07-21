package hyh.money.ui.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import java.util.List;

import hyh.money.R;
import hyh.money.model.Account;
import hyh.money.model.Category;
import hyh.money.dao.CategoryDao;
import hyh.money.ui.record.Act_Record;

/**
 * Created by HuangYH on 2015/4/24.
 */
public class Frag_Expense_Cat extends Fragment {

    private static final String TAG = Frag_Expense_Cat.class.getSimpleName();

    private List<Category> categoryList;
    private Category category;
    private TextView category_title;
    private RecyclerView rv_category_expense;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyAdapter myAdapter;
    private CategoryDao categoryDao;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fra_expense_category, container, false);

        categoryDao = new CategoryDao(getActivity());
        categoryList = categoryDao.getExpenseList();
        rv_category_expense = (RecyclerView) view.findViewById(R.id.rv_category_expense);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rv_category_expense.setLayoutManager(mLayoutManager);
        rv_category_expense.setItemAnimator(new DefaultItemAnimator());
        rv_category_expense.setHasFixedSize(true);
        myAdapter = new MyAdapter(getActivity(), categoryList);
        rv_category_expense.setAdapter(myAdapter);
        if (myAdapter != null) {
            myAdapter.notifyDataSetChanged();
        }
        myAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int categoryId = categoryList.get(position).getId();
                int type = 0;
                Intent intent = new Intent(getActivity(), Act_editCategory.class);
                intent.putExtra("id", categoryId);
                intent.putExtra("type", type);
                intent.putExtra("flag",3);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.attachToRecyclerView(rv_category_expense, new ScrollDirectionListener() {
            @Override
            public void onScrollDown() {
                Log.d("ListViewFragment", "onScrollDown()");
            }

            @Override
            public void onScrollUp() {
                Log.d("ListViewFragment", "onScrollUp()");
            }
        }, new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView view, int scrollState) {
                Log.d("ListViewFragment", "onScrollStateChanged()");
            }

            @Override
            public void onScrolled(RecyclerView view, int visibleItemCount, int totalItemCount) {
                Log.d("ListViewFragment", "onScroll()");
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Act_Record.class);
                i.putExtra("flag",3);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        return view;

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<Category> categoryList;
        private Context context;
        private MyItemClickListener myItemClickListener;

        public MyAdapter(Context context, List<Category> categoryList) {
            this.context = context;
            this.categoryList = categoryList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.cardview_category_expense, parent, false);
            return new ViewHolder(view, myItemClickListener);
        }

        public void setOnItemClickListener(MyItemClickListener listener) {
            this.myItemClickListener = listener;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {

            category = categoryList.get(position);
            viewHolder.category_title.setText(category.getTitle());
        }

        @Override
        public int getItemCount() {
            return categoryList == null ? 0 : categoryList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView category_title;
            private MyItemClickListener mListener;


            public ViewHolder(View itemView, MyItemClickListener mListener) {
                super(itemView);
                category_title = (TextView) itemView.findViewById(R.id.category_title);
                this.mListener = mListener;
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(view, getPosition());
                }
            }
        }
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
