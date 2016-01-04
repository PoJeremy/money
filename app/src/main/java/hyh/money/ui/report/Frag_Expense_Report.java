package hyh.money.ui.report;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hyh.money.R;
import hyh.money.dao.CategoryDao;
import hyh.money.dao.ExpenseDao;
import hyh.money.dao.IncomeDao;
import hyh.money.model.Account;
import hyh.money.model.Category;
import hyh.money.model.Expense;
import hyh.money.model.Income;
import hyh.money.ui.record.Act_Record;
import hyh.money.view.ListViewForScrollView;

/**
 * Created by HuangYH on 2015/5/23.
 */
public class Frag_Expense_Report extends Fragment {

    private ListViewForScrollView lv_report_expense;
    private ExpenseAdapter expenseAdapter;
    private ListViewForScrollView lv_report_income;
    private ObservableScrollView sl_report;
    private Expense expense;
    private ExpenseDao expenseDao;
    private List<Expense> expenseList = new ArrayList<Expense>();
    private Income income;
    private IncomeDao incomeDao;
    private List<Income> incomeList = new ArrayList<Income>();
    private Category category;
    private CategoryDao categoryDao;
    private Account account;

    private PieChart chart;
    private PieData data;
    private ArrayList<String> xVals;
    private ArrayList<Entry> yVals;
    private PieDataSet dataSet;
    private Random random;


    public Frag_Expense_Report() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_expense_report, container, false);

        initView(view);

        initData();


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.attachToScrollView(sl_report);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Act_Record.class);
                i.putExtra("flag", 4);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });


        chart = (PieChart) view.findViewById(R.id.chart);
        yVals = new ArrayList<>();
        xVals = new ArrayList<String>();
        for (int i = 0; i < expenseAdapter.getCount(); i++) {
            expense = expenseList.get(i);
            category = categoryDao.find(expense.getCategoryId());
            float aa = (float) (expense.getSum() / expenseDao.getSum() * 100);
            yVals.add(new Entry(aa, i));
            xVals.add(category.getTitle());
        }

        dataSet = new PieDataSet(yVals, "");
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        data = new PieData(xVals, dataSet);
        chart.setCenterText("支出类型统计");
        chart.setDescription("");
        chart.setData(data);
        chart.animateY(3000);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColorTransparent(true);
        chart.setDrawCenterText(true);
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        return view;

    }

    private void initView(View view) {

        sl_report = (ObservableScrollView) view.findViewById(R.id.sl_report);
        sl_report.smoothScrollTo(0, 0);

        lv_report_expense = (ListViewForScrollView) view.findViewById(R.id.lv_report_expense);
        expenseAdapter = new ExpenseAdapter();
        lv_report_expense.setAdapter(expenseAdapter);

    }

    private void initData() {

        expenseDao = new ExpenseDao(getActivity());
        expenseList = expenseDao.getSumCategory();
        incomeDao = new IncomeDao(getActivity());
        incomeList = incomeDao.getSumCategory();
        categoryDao = new CategoryDao(getActivity());

    }

    class ExpenseAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        public ExpenseAdapter() {
            this.inflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return expenseList == null ? 0 : expenseList.size();
        }

        @Override
        public Object getItem(int position) {
            return expenseList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = inflater.inflate(R.layout.item_report_category, null);
                vh.category_title = (TextView) convertView.findViewById(R.id.category_title);
                vh.category_amount = (TextView) convertView.findViewById(R.id.category_amount);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            expense = expenseList.get(position);
            category = categoryDao.find(expense.getCategoryId());
            vh.category_title.setText(category.getTitle());
            vh.category_amount.setText(expense.getSum() + "");
            return convertView;
        }

        class ViewHolder {

            public TextView category_title;
            public TextView category_amount;
        }
    }


//    private void setData(int count, float range) {
//
//        float mult = range;
//
//        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//
//        // IMPORTANT: In a PieChart, no values (Entry) should have the same
//        // xIndex (even if from different DataSets), since no values can be
//        // drawn above each other.
//        for (int i = 0; i < count + 1; i++) {
//            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
//        }
//
//        ArrayList<String> xVals = new ArrayList<String>();
//
////        for (int i = 0; i < count + 1; i++)
////            xVals.add(mParties[i % mParties.length]);
////
////        PieDataSet dataSet = new PieDataSet(yVals1, "Election Results");
//        dataSet.setSliceSpace(3f);
//        dataSet.setSelectionShift(5f);
//
//        // add a lot of colors
//
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//
//        for (int c : ColorTemplate.VORDIPLOM_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.JOYFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.COLORFUL_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.LIBERTY_COLORS)
//            colors.add(c);
//
//        for (int c : ColorTemplate.PASTEL_COLORS)
//            colors.add(c);
//
//        colors.add(ColorTemplate.getHoloBlue());
//
//        dataSet.setColors(colors);
//
//        PieData data = new PieData(xVals, dataSet);
//        data.setValueFormatter(new PercentFormatter());
//        data.setValueTextSize(11f);
//        data.setValueTextColor(Color.BLACK);
////        mChart.setData(data);
////
////        // undo all highlights
////        mChart.highlightValues(null);
////
////        mChart.invalidate();
//    }
}
