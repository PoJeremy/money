package hyh.money.view.Account;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import hyh.money.R;
import hyh.money.model.Account;
import hyh.money.dao.AccountDao;

/**
 * Created by HuangYH on 2015/5/3.
 */
public class DialogAccount extends Dialog {

    private ListView lv_account_record;
    private List<Account> accountList;
    private Account account;
    private String str_name;
    private int accountId;
    private double str_amount;
    private Context context;
    private OnItemClickListener mListener;


    public DialogAccount(Context context) {
        super(context);
    }

    protected DialogAccount(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public DialogAccount(Context context, int theme) {
        super(context, theme);
    }

    public DialogAccount(Context context, OnItemClickListener mListener, int theme) {
        super(context, theme);
        this.context = context;
        this.mListener = mListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_account);
        initView();
        initData();
    }

    private void initView() {
        lv_account_record = (ListView) findViewById(R.id.lv_account_record);

    }

    private void initData() {
        AccountDao dao = new AccountDao(getContext());
        accountList = dao.getAll();
        final AccountsAdapter accountsAdapter = new AccountsAdapter();
        lv_account_record.setAdapter(accountsAdapter);
        if (accountsAdapter != null) {
            accountsAdapter.notifyDataSetChanged();
        }
        lv_account_record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                accountId = accountList.get(position).getId();
                str_name = accountList.get(position).getName().toString();
                str_amount = accountList.get(position).getAmount();
                mListener.getText(accountId,str_name, str_amount);
                dismiss();
            }
        });
    }

    private class AccountsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return accountList.size();
        }

        @Override
        public Object getItem(int position) {

            return accountList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                account = accountList.get(position);
                convertView = View.inflate(getContext(), R.layout.item_record_account, null);
                viewHolder.account_name = (TextView) convertView.findViewById(R.id.account_name);
                viewHolder.account_amount = (TextView) convertView.findViewById(R.id.account_amount);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.account_name.setText(account.getName());
            viewHolder.account_amount.setText(account.getAmount() + "");
            return convertView;
        }

        class ViewHolder {
            public TextView account_name;
            public TextView account_amount;
        }

    }

    public interface OnItemClickListener {
        void getText(int accountId,String str_name, double str_amount);
    }
}
