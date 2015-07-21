package hyh.money.view.PasswordLock;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import hyh.money.R;

public class GestureLockShowAdapter extends BaseAdapter{
	
	private Context context;
	private ArrayList<Integer> slepoint ;
	public GestureLockShowAdapter(Context context,ArrayList<Integer> slepoint) {
		this.context = context;
		this.slepoint = slepoint;
	}

	@Override
	public int getCount() {
		return 9;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(context, R.layout.mycricleitem, null);
		
		ImageView imageView = (ImageView) convertView.findViewById(R.id.circle_point);
		for (int i = 0; i < slepoint.size(); i++) {
			if (position == slepoint.get(i)) {
				imageView.setBackgroundResource(R.drawable.mycirclesel);
			}
		}
		
		return convertView;
	}

}
