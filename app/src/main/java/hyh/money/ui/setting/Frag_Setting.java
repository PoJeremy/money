package hyh.money.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import hyh.money.R;

/**
 * Created by HuangYH on 2015/4/5.
 */
public class Frag_Setting extends Fragment {

    private LinearLayout ll_share;
    private LinearLayout ll_setpw;
    private LinearLayout ll_mydata;


    public Frag_Setting(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frag_setting,container,false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        ll_share = (LinearLayout)view.findViewById(R.id.ll_share);
        ll_share.setOnClickListener(new OnshareClick());
        ll_setpw = (LinearLayout)view.findViewById(R.id.ll_setpw);
        ll_setpw.setOnClickListener(new OnsetPw());
        ll_mydata = (LinearLayout)view.findViewById(R.id.ll_mydata);
        ll_mydata.setOnClickListener(new OnmyData());

    }

    // 分享应用事件
    class OnshareClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
            intent.putExtra(Intent.EXTRA_TEXT, "你总以为你不会理财，那是因为你还没用'钱' ，下载地址:https://fir.im/mymoney");
            Uri u = Uri.fromFile(getActivity().getFileStreamPath("share.png"));
            intent.putExtra(Intent.EXTRA_STREAM,u);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(intent, "分享到"));
        }
    }

    //设置密码事件
    class OnsetPw implements View.OnClickListener{

        @Override
        public void onClick(View view) {

        }
    }

    //我的数据事件
    class OnmyData implements View.OnClickListener{

        @Override
        public void onClick(View view) {

        }
    }




}
