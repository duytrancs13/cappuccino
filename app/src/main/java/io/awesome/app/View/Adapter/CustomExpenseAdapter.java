package io.awesome.app.View.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import io.awesome.app.Model.Expense;
import io.awesome.app.R;

/**
 * Created by sung on 10/06/2018.
 */

public class CustomExpenseAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Expense> expenseList;

    public CustomExpenseAdapter(Context context, List<Expense> expenseList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.expenseList = expenseList;
    }

    @Override
    public int getCount() {
        return expenseList.size();
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
        final ViewHolder viewHolder;

        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.layout_listview_expense,null);

            viewHolder = new ViewHolder();
            viewHolder.btnStatusExpense = (Button) convertView.findViewById(R.id.btnStatusExpense);
            viewHolder.tvNameExpense = (TextView) convertView.findViewById(R.id.tvNameExpense);
            viewHolder.tvQuantityExpense = (TextView) convertView.findViewById(R.id.tvQuantityExpense);
            viewHolder.tvUnitExpense = (TextView) convertView.findViewById(R.id.tvUnitExpense);
            viewHolder.tvPriceExpense = (TextView) convertView.findViewById(R.id.tvPriceExpense);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Expense expense = this.expenseList.get(position);

        Typeface mFont = Typeface.createFromAsset(context.getAssets(),"Roboto-Regular.ttf");

        if(expense.getStatus().equals("Pending")){
            viewHolder.btnStatusExpense.setBackgroundResource(R.drawable.ic_expense_pending);
        }else{
            viewHolder.btnStatusExpense.setBackgroundResource(R.drawable.ic_expense_approved);
        }

        viewHolder.tvNameExpense.setText(expense.getName());
        viewHolder.tvNameExpense.setTypeface(mFont);

        viewHolder.tvQuantityExpense.setText(String.valueOf(expense.getQuantity()));
        viewHolder.tvQuantityExpense.setTypeface(mFont);

        viewHolder.tvUnitExpense.setText(String.valueOf(expense.getUnit()));
        viewHolder.tvUnitExpense.setTypeface(mFont);

        viewHolder.tvPriceExpense.setText(NumberFormat.getNumberInstance(Locale.GERMAN).format(expense.getPrice())+" đ");
        viewHolder.tvPriceExpense.setTypeface(mFont);
        return convertView;
    }

    public void realTimeExpense(List<Expense> lstE) {
        this.expenseList = lstE;
        notifyDataSetChanged();
    }

    static class ViewHolder extends FragmentActivity {
        Button btnStatusExpense;
        TextView tvNameExpense;
        TextView tvQuantityExpense;
        TextView tvUnitExpense;
        TextView tvPriceExpense;
    }
}
