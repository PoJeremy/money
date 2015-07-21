package hyh.money.ui.income;


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
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.ScrollDirectionListener;

import java.util.List;

import hyh.money.R;
import hyh.money.dao.AccountDao;
import hyh.money.dao.CategoryDao;
import hyh.money.dao.IncomeDao;
import hyh.money.model.Account;
import hyh.money.model.Category;
import hyh.money.model.Expense;
import hyh.money.model.Income;
import hyh.money.ui.expense.Act_editExpense;
import hyh.money.ui.record.Act_Record;

/**
 * Created by HuangYH on 2015/4/4.
 */
public class Frag_Income extends Fragment {

    private static final String TAG = Frag_Income.class.getSimpleName();

    private RecyclerView rv_income;
    private TextView income_total;
    private MyAdapter myAdapter;
    private Income income;
    private List<Income> incomeList;
    private RecyclerView.LayoutManager mLayoutManager;
    private IncomeDao incomeDao;
    private Account account;
    private AccountDao accountDao;
    private Category category;
    private CategoryDao categoryDao;
    private int accountId;
    private int categoryId;

    public Frag_Income() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_income, container, false);

        incomeDao = new IncomeDao(getActivity());
        incomeList = incomeDao.getAll();

        accountDao = new AccountDao(getActivity());
        categoryDao = new CategoryDao(getActivity());

        rv_income = (RecyclerView) view.findViewById(R.id.rv_income);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rv_income.setLayoutManager(mLayoutManager);
        rv_income.setItemAnimator(new DefaultItemAnimator());
        rv_income.setHasFixedSize(true);
        myAdapter = new MyAdapter(getActivity(), incomeList);
        rv_income.setAdapter(myAdapter);
        if (myAdapter!=null){
            myAdapter.notifyDataSetChanged();
        }

        myAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int incomeId = incomeList.get(position).getId();
                Intent intent = new Intent(getActivity(), Act_editIncome.class);
                intent.putExtra("id", incomeId);
                intent.putExtra("accountId",accountId);
                intent.putExtra("categoryId",categoryId);
                intent.putExtra("flag",2);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        com.melnykov.fab.FloatingActionButton fab = (com.melnykov.fab.FloatingActionButton) view.findViewById(R.id.fab);

        fab.attachToRecyclerView(rv_income, new ScrollDirectionListener() {
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
                i.putExtra("flag",2);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        initView(view);

        return view;
    }

    private void initView(View view) {
        double amount = incomeDao.getSum();
        income_total = (TextView)view.findViewById(R.id.income_total);
        income_total.setText(amount+"");
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<Income> incomeList;
        private Context context;
        private MyItemClickListener myItemClickListener;

        public MyAdapter(Context context , List<Income> incomeList){
            this.context = context;
            this.incomeList = incomeList;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.cardview_income, viewGroup, false);
            return new ViewHolder(view,myItemClickListener);
        }

        public void setOnItemClickListener(MyItemClickListener listener) {
            this.myItemClickListener = listener;
        }

        @Override
        public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int position) {

            income = incomeList.get(position);
            accountId = income.getAccountId();
            account = accountDao.find(accountId);
            categoryId = income.getCategoryId();
            category = categoryDao.find(categoryId);
            viewHolder.tv_cardview_datetime.setText(income.getDatetime());
            viewHolder.tv_cardview_account.setText(account.getName());
            viewHolder.tv_cardview_category.setText(category.getTitle());
            viewHolder.tv_cardview_amount.setText(income.getAmount()+"");
            viewHolder.tv_cardview_remark.setText(income.getRemark());

        }

        @Override
        public int getItemCount() {
            return incomeList == null ? 0 : incomeList.size();
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
