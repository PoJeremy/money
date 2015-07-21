package hyh.money.view.Calc;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

import hyh.money.R;

/**
 * Created by HuangYH on 2015/5/2.
 */
public class DialogCalculator extends Dialog implements View.OnClickListener {

    private Context context;
    private Button btn_zero;
    private Button btn_one;
    private Button btn_two;
    private Button btn_three;
    private Button btn_four;
    private Button btn_five;
    private Button btn_six;
    private Button btn_seven;
    private Button btn_eight;
    private Button btn_night;
    private Button btn_point;
    private Button btn_ok;
    private Button btn_cancel;
    private Button btn_del;
    private TextView tv_amount;
    private String str;
    private OnSureClickListener mListener;

    private DecimalFormat df = new DecimalFormat("0.0");

    public DialogCalculator(Context context) {
        super(context);
    }

    public DialogCalculator(Context context, int theme) {
        super(context, theme);
    }

    public DialogCalculator(Context context, String str, int theme,OnSureClickListener listener) {
        super(context, theme);
        this.context = context;
        this.mListener = listener;
        this.str = str;
    }

    protected DialogCalculator(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_calculator);
        initView();
        initEvent();
    }

    private void initView() {
        btn_zero = (Button) findViewById(R.id.btn_zero);
        btn_one = (Button) findViewById(R.id.btn_one);
        btn_two = (Button) findViewById(R.id.btn_two);
        btn_three = (Button) findViewById(R.id.btn_three);
        btn_four = (Button) findViewById(R.id.btn_four);
        btn_five = (Button) findViewById(R.id.btn_five);
        btn_six = (Button) findViewById(R.id.btn_six);
        btn_seven = (Button) findViewById(R.id.btn_seven);
        btn_eight = (Button) findViewById(R.id.btn_eight);
        btn_night = (Button) findViewById(R.id.btn_night);
        btn_point = (Button) findViewById(R.id.btn_point);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        tv_amount.setText(str);
    }

    public void initEvent() {
        btn_zero.setOnClickListener(this);
        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);
        btn_five.setOnClickListener(this);
        btn_six.setOnClickListener(this);
        btn_seven.setOnClickListener(this);
        btn_eight.setOnClickListener(this);
        btn_night.setOnClickListener(this);
        btn_point.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String tvResult = tv_amount.getText().toString();
        switch (view.getId()) {
            case R.id.btn_zero:
                if (tvResult.equals("0.0")) {
                    tv_amount.setText("");
                    tvResult = "0";
                    tv_amount.setText(tvResult);
                } else {
                    tvResult += "0";
                    tv_amount.setText(tvResult);
                }
                break;
            case R.id.btn_one:
                if (tvResult.equals("0.0")) {
                    tv_amount.setText("");
                    tvResult = "1";
                    tv_amount.setText(tvResult);
                } else {
                    tvResult += "1";
                    tv_amount.setText(tvResult);
                }
                break;
            case R.id.btn_two:
                if (tvResult.equals("0.0")) {
                    tv_amount.setText("");
                    tvResult = "2";
                    tv_amount.setText(tvResult);
                } else {
                    tvResult += "2";
                    tv_amount.setText(tvResult);
                }
                break;
            case R.id.btn_three:
                if (tvResult.equals("0.0")) {
                    tv_amount.setText("");
                    tvResult = "3";
                    tv_amount.setText(tvResult);
                } else {
                    tvResult += "3";
                    tv_amount.setText(tvResult);
                }
                break;
            case R.id.btn_four:
                if (tvResult.equals("0.0")) {
                    tv_amount.setText("");
                    tvResult = "4";
                    tv_amount.setText(tvResult);
                } else {
                    tvResult += "4";
                    tv_amount.setText(tvResult);
                }
                break;
            case R.id.btn_five:
                if (tvResult.equals("0.0")) {
                    tv_amount.setText("");
                    tvResult = "5";
                    tv_amount.setText(tvResult);
                } else {
                    tvResult += "5";
                    tv_amount.setText(tvResult);
                }
                break;
            case R.id.btn_six:
                if (tvResult.equals("0.0")) {
                    tv_amount.setText("");
                    tvResult = "6";
                    tv_amount.setText(tvResult);
                } else {
                    tvResult += "6";
                    tv_amount.setText(tvResult);
                }
                break;
            case R.id.btn_seven:
                if (tvResult.equals("0.0")) {
                    tv_amount.setText("");
                    tvResult = "7";
                    tv_amount.setText(tvResult);
                } else {
                    tvResult += "7";
                    tv_amount.setText(tvResult);
                }
                break;
            case R.id.btn_eight:
                if (tvResult.equals("0.0")) {
                    tv_amount.setText("");
                    tvResult = "8";
                    tv_amount.setText(tvResult);
                } else {
                    tvResult += "8";
                    tv_amount.setText(tvResult);
                }
                break;
            case R.id.btn_night:
                if (tvResult.equals("0.0")) {
                    tv_amount.setText("");
                    tvResult = "9";
                    tv_amount.setText(tvResult);
                } else {
                    tvResult += "9";
                    tv_amount.setText(tvResult);
                }
                break;
            case R.id.btn_point:
                if (tvResult.indexOf(".") != -1) {
                } else {
                    if (tvResult.equals("0"))
                        tv_amount.setText(("0" + ".").toString());
                    else if (tvResult.equals("")) {
                    } else
                        tv_amount.setText(tvResult + ".");
                }
                break;
            case R.id.btn_del:
                try {
                    tv_amount.setText(tvResult.substring(0, tvResult.length() - 1));
                } catch (Exception e) {
                    tv_amount.setText("");
                }
                break;
            case R.id.btn_cancel:
                this.dismiss();
                break;
            case R.id.btn_ok:
                mListener.getText(df.format(Double.valueOf(tvResult))+"");
                this.dismiss();
                break;

        }
    }

    public interface  OnSureClickListener{
        void getText(String str);
    }

}


