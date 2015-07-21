package hyh.money.ui.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import java.util.List;

import hyh.money.R;
import hyh.money.model.Account;
import hyh.money.dao.AccountDao;
import hyh.money.model.Income;
import hyh.money.ui.record.Act_Record;

/**
 * Created by HuangYH on 2015/4/4.
 */
public class Frag_Account extends Fragment {

    private static final String TAG = Frag_Account.class.getSimpleName();

    private List<Account> accountList;
    private TextView account_name;
    private TextView account_amount;
    private Account account;
    private TextView account_total;
    private RecyclerView rv_account;
    private RecyclerView.LayoutManager mLayoutManager;
    private AccountDao accountDao;
    private MyAdapter myAdapter;

    public Frag_Account() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_account, container, false);

        accountDao = new AccountDao(getActivity());
        accountList = accountDao.getAll();
        rv_account = (RecyclerView) view.findViewById(R.id.rv_account);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rv_account.setLayoutManager(mLayoutManager);
        rv_account.setItemAnimator(new DefaultItemAnimator());
        rv_account.setHasFixedSize(true);
        myAdapter = new MyAdapter(getActivity(), accountList);
        rv_account.setAdapter(myAdapter);
        if (myAdapter != null) {
            myAdapter.notifyDataSetChanged();
        }
        myAdapter.setOnItemClickListener(new MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int accountId = accountList.get(position).getId();
                Intent intent = new Intent(getActivity(), Act_editAccount.class);
                intent.putExtra("id", accountId);
                intent.putExtra("flag",0);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.attachToRecyclerView(rv_account, new ScrollDirectionListener() {
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
                i.putExtra("flag",0);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        initView(view);
        setHasOptionsMenu(true);
        return view;
    }

    private void initView(View view) {
        double amount = accountDao.getSum();
        account_total = (TextView) view.findViewById(R.id.account_total);
        account_total.setText(amount + "");
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_account, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_accounts:
                Intent i = new Intent(getActivity(), Act_addAccount.class);
                i.putExtra("flag",0);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private List<Account> accountList;
        private Context context;
        private MyItemClickListener myItemClickListener;

        public MyAdapter(Context context, List<Account> accountList) {
            this.context = context;
            this.accountList = accountList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.cardview_account, parent, false);
            return new ViewHolder(view, myItemClickListener);
        }

        public void setOnItemClickListener(MyItemClickListener listener) {
            this.myItemClickListener = listener;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {

            account = accountList.get(position);
            viewHolder.account_name.setText(account.getName());
            viewHolder.account_amount.setText(account.getAmount() + "");
        }

        @Override
        public int getItemCount() {
            return accountList == null ? 0 : accountList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public TextView account_name;
            public TextView account_amount;
            private MyItemClickListener mListener;

            public ViewHolder(View itemView, MyItemClickListener mListener) {
                super(itemView);
                account_name = (TextView) itemView.findViewById(R.id.account_name);
                account_amount = (TextView) itemView.findViewById(R.id.account_amount);
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
