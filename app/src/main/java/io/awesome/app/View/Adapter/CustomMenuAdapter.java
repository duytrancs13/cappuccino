package io.awesome.app.View.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import io.awesome.app.Model.Menu;
import io.awesome.app.Model.Ordered;
import io.awesome.app.Presenter.Menu.MenuPresenterImpl;
import io.awesome.app.R;

/**
 * Created by sung on 30/08/2017.
 */

public class CustomMenuAdapter extends BaseAdapter {

    private Context context;

    private LayoutInflater layoutInflater;

    private List<Menu> menuList;

    private MenuPresenterImpl menuPresenterImpl;

    private String receiptId;

    private String token;


    public CustomMenuAdapter(Context context, List<Menu> menuList,MenuPresenterImpl menuPresenterImpl,String receiptId, String token) {
        this.context = context;
        this.menuList = menuList;
        this.menuPresenterImpl = menuPresenterImpl;
        this.receiptId = receiptId;
        this.token = token;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {


        final ViewHolder viewHolder;

        if(view == null){

            viewHolder = new ViewHolder();

            view = layoutInflater.inflate(R.layout.layout_gridview_menu,null);

            viewHolder.imageMenu = (ImageView) view.findViewById(R.id.imageMenu);

            viewHolder.tvNameMenu = (TextView) view.findViewById(R.id.tvNameMenu);

            viewHolder.tvDescriptionMenu = (TextView) view.findViewById(R.id.tvDescriptionMenu);

            viewHolder.noteAdd = (ImageView) view.findViewById(R.id.noteAdd);

            viewHolder.tvPriceMenu = (TextView) view.findViewById(R.id.tvPriceMenu);

            viewHolder.btnSubMenu = (Button) view.findViewById(R.id.btnSubMenu);

            viewHolder.btnQuatityMenu = (Button) view.findViewById(R.id.btnQuatityMenu);

            viewHolder.btnPlusMenu = (Button) view.findViewById(R.id.btnPlusMenu);



            view.setTag(viewHolder);

        }else{

            viewHolder =(ViewHolder) view.getTag();

        }

        final Menu menu = (Menu) menuList.get(position);
        Typeface mFont = Typeface.createFromAsset(context.getAssets(),"Roboto-Bold.ttf");

        Picasso.with(context).load(menu.getUrlImage()).into(viewHolder.imageMenu);

        viewHolder.tvNameMenu.setText(menu.getName());
        viewHolder.tvNameMenu.setTypeface(mFont);

        viewHolder.tvDescriptionMenu.setText(menu.getDescription());

        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(menu.getPrice());

        viewHolder.tvPriceMenu.setText(""+price+" đ");


        viewHolder.btnQuatityMenu.setText(menu.getQuantity()+"");


        viewHolder.noteAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuPresenterImpl.loadPopupNoteAdd();
            }
        });


        viewHolder.btnPlusMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewHolder.btnQuatityMenu.getText().toString().equals("")){
                    menuPresenterImpl.addReceipt(receiptId, token, "menuItemId", menu.get_id());
                    viewHolder.btnQuatityMenu.setText(1+"");
                }else {
                    int qualityCurrent = Integer.parseInt(viewHolder.btnQuatityMenu.getText().toString());
                    menuPresenterImpl.addReceipt(receiptId, token, "menuItemId", menu.get_id());
                    viewHolder.btnQuatityMenu.setText((qualityCurrent + 1) + "");
                }
            }
        });


        viewHolder.btnSubMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qualityCurrent = Integer.parseInt(viewHolder.btnQuatityMenu.getText().toString());
                viewHolder.btnQuatityMenu.setText((qualityCurrent-1)+"");
            }
        });




        return view;
    }

    private class ViewHolder{
        ImageView imageMenu;
        TextView tvNameMenu;
        TextView tvDescriptionMenu;
        ImageView noteAdd;
        TextView tvPriceMenu;
        Button btnSubMenu;
        Button btnQuatityMenu;
        Button btnPlusMenu;

    }
}
