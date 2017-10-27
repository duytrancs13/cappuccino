package info.devexchanges.navvp.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import info.devexchanges.navvp.Model.Category;
import info.devexchanges.navvp.R;

/**
 * Created by sung on 29/08/2017.
 */

public class CustomCategoryAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Category> categoryList;

    public CustomCategoryAdapter(Context context, List<Category> categoryList) {

        this.context = context;

        this.categoryList = categoryList;

        this.layoutInflater = LayoutInflater.from(context);
    }


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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.layout_listview_categories,null);

            viewHolder = new ViewHolder();

            viewHolder.imageCategories = (ImageView) convertView.findViewById(R.id.imageCategories);

            viewHolder.tvNameCategories = (TextView) convertView.findViewById(R.id.tvNameCategories);

            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Category category = this.categoryList.get(position);
        Typeface mFont = Typeface.createFromAsset(context.getAssets(),"Roboto-Bold.ttf");

        Picasso.with(context).load(category.getUrlImage()).into(viewHolder.imageCategories);

        viewHolder.tvNameCategories.setText(category.getName());
        viewHolder.tvNameCategories.setTypeface(mFont);


        return convertView;
    }



    static class ViewHolder extends FragmentActivity {
        ImageView imageCategories;
        TextView tvNameCategories;
    }





}
