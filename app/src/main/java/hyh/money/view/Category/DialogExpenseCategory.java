package hyh.money.view.Category;

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
import hyh.money.model.Category;
import hyh.money.dao.CategoryDao;

/**
 * Created by HuangYH on 2015/5/10.
 */
public class DialogExpenseCategory extends Dialog {

    private ListView lv_category_record;
    private List<Category> categoryList;
    private Category category;
    private String str_title;
    private int categoryId;
    private Context context;
    private OnItemClickListener mListener;

    public DialogExpenseCategory(Context context) {
        super(context);
    }

    public DialogExpenseCategory(Context context, int theme) {
        super(context, theme);
    }

    public DialogExpenseCategory(Context context, OnItemClickListener mListener, int theme) {
        super(context, theme);
        this.context = context;
        this.mListener = mListener;
    }

    protected DialogExpenseCategory(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_category);
        initView();
        ininData();
    }

    private void initView() {
        lv_category_record =(ListView)findViewById(R.id.lv_category_record);
    }

    private void ininData() {
        CategoryDao dao = new CategoryDao(getContext());
        categoryList = dao.getExpenseList();
        final CategoryAdapter categoryAdapter = new CategoryAdapter();
        lv_category_record.setAdapter(categoryAdapter);
        if(categoryAdapter != null){
            categoryAdapter.notifyDataSetChanged();
        }
        lv_category_record.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                categoryId =categoryList.get(position).getId();
                str_title = categoryList.get(position).getTitle().toString();
                mListener.getText(categoryId,str_title);
                dismiss();
            }
        });
    }

    private class CategoryAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return categoryList.size();
        }

        @Override
        public Object getItem(int position) {
            return categoryList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if(convertView == null){
                viewHolder = new ViewHolder();
                category = categoryList.get(position);
                convertView = View.inflate(getContext(), R.layout.item_recoed_category, null);
                viewHolder.category_title = (TextView)convertView.findViewById(R.id.category_title);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            viewHolder.category_title.setText(category.getTitle());
            return convertView;
        }

        class ViewHolder{
            public TextView category_title;
        }
    }

    public interface OnItemClickListener{
        void getText(int categoryId,String str_title);
    }
}
