package hyh.money.ui.expense;

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
import android.widget.TextView;

import com.melnykov.fab.ScrollDirectionListener;

import java.util.List;

import hyh.money.R;
import hyh.money.dao.AccountDao;
import hyh.money.dao.CategoryDao;
import hyh.money.dao.ExpenseDao;
import hyh.money.model.Account;
import hyh.money.model.Category;
import hyh.money.model.Expense;
import hyh.money.ui.record.Act_Record;

/**
 * Created by HuangYH on 2015/4/4.
 */
public class Frag_Expense extends Fragment {

    private static final String TAG = Frag_Expense.class.getSimpleName();

    private RecyclerView rv_expenses;
    private TextView expense_total;
    private MyAdapter myAdapter;
    private Expense expense;
    private List<Expense> expenseList;
    private RecyclerView.LayoutManager mLayoutManager;
    private ExpenseDao expenseDao;
    private Account account;
    private AccountDao accountDao;
    private Category category;
    private CategoryDao categoryDao;
    private int accountId;
    private int categoryId;

    public Frag_Expense() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_expense, container, false);

        expenseDao = new ExpenseDao(getActivity());
        expenseList = expenseDao.getAll();

        accountDao = new AccountDao(getActivity());
        categoryDao = new CategoryDao(getActivity());

        rv_expenses = (RecyclerView) view.findViewById(R.id.rv_expenses);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rv_expenses.setLayoutManager(mLayoutManager);
        rv_expenses.setItemAnimator(new DefaultItemAnimator());
        rv_expenses.setHasFixedSize(true);
        myAdapter = new MyAdapter(getActivity(),expenseList
        );
        rv_expenses.setAdapter(myAdapter);
        if (myAdapter!=null){
            myAdapter.notifyDataSetChanged();
        }

        myAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int expenseId = expenseList.get(position).getId();
                Intent intent = new Intent(getActivity(), Act_editExpense.class);
                intent.putExtra("id", expenseId);
                intent.putExtra("accountId",accountId);
                intent.putExtra("categoryId",categoryId);
                intent.putExtra("flag",1);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        com.melnykov.fab.FloatingActionButton fab = (com.melnykov.fab.FloatingActionButton) view.findViewById(R.id.fab);

        fab.attachToRecyclerView(rv_expenses, new ScrollDirectionListener() {
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
                i.putExtra("flag",1);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        initView(view);

        return view;
    }

    private void initView(View view) {
        double amount = expenseDao.getSum();
        expense_total = (TextView)view.findViewById(R.id.expense_total);
        expense_total.setText(amount+"");

    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<Expense> expenseList;
        private Context context;
        private MyItemClickListener myItemClickListener;

        public MyAdapter(Context context , List<Expense> expenseList){
            this.context = context;
            this.expenseList = expenseList;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.cardview_expense, viewGroup, false);
            return new ViewHolder(view,myItemClickListener);
        }

        public void setOnItemClickListener(MyItemClickListener listener) {
            this.myItemClickListener = listener;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int position) {

            expense = expenseList.get(position);
            accountId = expense.getAccountId();
            account = accountDao.find(accountId);
            categoryId = expense.getCategoryId();
            category = categoryDao.find(categoryId);
            viewHolder.tv_cardview_datetime.setText(expense.getDatetime());
            viewHolder.tv_cardview_account.setText(account.getName());
            viewHolder.tv_cardview_category.setText(category.getTitle());
            viewHolder.tv_cardview_amount.setText(expense.getAmount()+"");
            viewHolder.tv_cardview_remark.setText(expense.getRemark());

        }

        @Override
        public int getItemCount() {
            return expenseList == null ? 0 : expenseList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView tv_cardview_datetime;
            public TextView tv_cardview_account;
            public TextView tv_cardview_category;
            public TextView tv_cardview_amount;
            public TextView tv_cardview_remark;
            private MyItemClickListener mListener;

            public ViewHolder(View itemView,MyItemClickListener mListener) {
                super(itemView);
                tv_cardview_datetime = (TextView) itemView.findViewById(R.id.tv_cardview_datetime);
                tv_cardview_account = (TextView) itemView.findViewById(R.id.tv_cardview_account);
                tv_cardview_category = (TextView) itemView.findViewById(R.id.tv_cardview_category);
                tv_cardview_amount = (TextView) itemView.findViewById(R.id.tv_cardview_amount);
                tv_cardview_remark = (TextView) itemView.findViewById(R.id.tv_cardview_remark);
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
}
