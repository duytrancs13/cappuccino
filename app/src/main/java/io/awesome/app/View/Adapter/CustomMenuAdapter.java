package io.awesome.app.View.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import io.awesome.app.Model.Menu;
import io.awesome.app.Model.Ordered;
import io.awesome.app.Presenter.Menu.MenuPresenterImpl;
import io.awesome.app.R;

import static io.awesome.app.View.Main.MainActivity.receiptId;
import static io.awesome.app.View.Table.TableActivity.checkConfirmChangedOrdered;
import static io.awesome.app.View.Table.TableActivity.listMoreOrdered;
import static io.awesome.app.View.Table.TableActivity.listOldOrdered;
import static io.awesome.app.View.Table.TableActivity.listOrdered;


/**
 * Created by sung on 30/08/2017.
 */

public class CustomMenuAdapter extends BaseAdapter {

    private Context context;

    private LayoutInflater layoutInflater;

    private List<Menu> menuList;

    private MenuPresenterImpl menuPresenterImpl;

    private String token;


    public CustomMenuAdapter(Context context, List<Menu> menuList, MenuPresenterImpl menuPresenterImpl, String token) {
        this.context = context;
        this.menuList = menuList;
        this.menuPresenterImpl = menuPresenterImpl;
        this.layoutInflater = LayoutInflater.from(context);
        this.token = token;
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

        if (view == null) {

            viewHolder = new ViewHolder();

            view = layoutInflater.inflate(R.layout.layout_gridview_menu, null);

            viewHolder.imageMenu = (ImageView) view.findViewById(R.id.imageMenu);

            viewHolder.tvNameMenu = (TextView) view.findViewById(R.id.tvNameMenu);

            viewHolder.noteAdd = (ImageView) view.findViewById(R.id.noteAdd);

            viewHolder.tvPriceMenu = (TextView) view.findViewById(R.id.tvPriceMenu);

            viewHolder.btnQuatityMenu = (Button) view.findViewById(R.id.btnQuatityMenu);

            viewHolder.btnPlusMenu = (Button) view.findViewById(R.id.btnPlusMenu);

            view.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) view.getTag();
        }

        final Menu menu = (Menu) menuList.get(position);
        Typeface mFont = Typeface.createFromAsset(context.getAssets(), "Roboto-Bold.ttf");

        Picasso.with(context).load(menu.getUrlImage()).into(viewHolder.imageMenu);

        viewHolder.tvNameMenu.setText(menu.getName());
        viewHolder.tvNameMenu.setTypeface(mFont);


        String price = NumberFormat.getNumberInstance(Locale.GERMAN).format(menu.getPrice());

        viewHolder.tvPriceMenu.setText("" + price + " đ");


        for (final Ordered itemOrdered: listMoreOrdered) {
            if (itemOrdered.getItemId().equals(menu.getId())) {
                viewHolder.noteAdd.setVisibility(View.VISIBLE);
                viewHolder.btnQuatityMenu.setText(itemOrdered.getQuantity() + "");
                if(itemOrdered.getQuantity() == 0){
                    viewHolder.btnQuatityMenu.setVisibility(View.INVISIBLE);
                    viewHolder.noteAdd.setVisibility(View.INVISIBLE);
                }else{
                    viewHolder.btnQuatityMenu.setVisibility(View.VISIBLE);
                    /*viewHolder.btnSubMenu.setVisibility(View.VISIBLE);*/
                    viewHolder.noteAdd.setVisibility(View.VISIBLE);
                }

                viewHolder.noteAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String textNote = "";
                        for (Ordered newOrdered : listOrdered) {
                            if (newOrdered.getItemId().equals(menu.getId())) {
                                textNote = newOrdered.getNote();
                                //menuPresenterImpl.setTextPopupNoteAdd(textNote, menu.getId());
                                menuPresenterImpl.setTextPopupNoteAdd(textNote, newOrdered);
                                menuPresenterImpl.showPopupNoteAdd();
                                break;
                            }
                        }
                    }
                });
                viewHolder.btnPlusMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int qualityCurrent = Integer.parseInt(viewHolder.btnQuatityMenu.getText().toString());
                        viewHolder.btnQuatityMenu.setText((qualityCurrent + 1) + "");
                        viewHolder.btnQuatityMenu.setVisibility(View.VISIBLE);
                        viewHolder.noteAdd.setVisibility(View.VISIBLE);
                        itemOrdered.setQuantity(itemOrdered.getQuantity()+1);
                        checkConfirmChangedOrdered = false;
                    }
                });

                break;
            } else {
                viewHolder.noteAdd.setVisibility(View.INVISIBLE);
                /*viewHolder.btnSubMenu.setVisibility(View.INVISIBLE);*/
                viewHolder.btnQuatityMenu.setVisibility(View.INVISIBLE);
                viewHolder.btnQuatityMenu.setText("0");

                viewHolder.btnPlusMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //menuPresenterImpl.qualityForReceipt(menu.getId(), token, "1");

                        int qualityCurrent = Integer.parseInt(viewHolder.btnQuatityMenu.getText().toString());

                        if(qualityCurrent == 0){
                            Ordered newOrdered = new Ordered(menu.getId(),menu.getName(),menu.getPrice(),menu.getUrlImage(),qualityCurrent+1,"");
                            listMoreOrdered.add(newOrdered);
                        }else{
                            listMoreOrdered.get(listMoreOrdered.size()-1).setQuantity(qualityCurrent+1);
                        }
                        checkConfirmChangedOrdered = false;
                        viewHolder.btnQuatityMenu.setText((qualityCurrent + 1) + "");
                        viewHolder.noteAdd.setVisibility(View.VISIBLE);
                        viewHolder.btnQuatityMenu.setVisibility(View.VISIBLE);


                    }
                });




                viewHolder.noteAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String textNote = "";
                        for (Ordered newOrdered : listOrdered) {
                            if (newOrdered.getItemId().equals(menu.getId())) {
                                textNote = newOrdered.getNote();
//                                menuPresenterImpl.setTextPopupNoteAdd(textNote, menu.getId());
                                menuPresenterImpl.setTextPopupNoteAdd(textNote, newOrdered);
                                menuPresenterImpl.showPopupNoteAdd();
                                break;
                            }
                        }
                    }
                });
            }
        }
        if (listMoreOrdered.size() == 0) {
            viewHolder.noteAdd.setVisibility(View.INVISIBLE);
            viewHolder.btnQuatityMenu.setVisibility(View.INVISIBLE);
            viewHolder.btnQuatityMenu.setText("0");

            viewHolder.btnPlusMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int qualityCurrent = Integer.parseInt(viewHolder.btnQuatityMenu.getText().toString());
                    if(qualityCurrent == 0){
                        Ordered newOrdered = new Ordered(menu.getId(),menu.getName(),menu.getPrice(),menu.getUrlImage(),qualityCurrent+1,"");
                        listMoreOrdered.add(newOrdered);
                    }else{
                        listMoreOrdered.get(listMoreOrdered.size()-1).setQuantity(qualityCurrent+1);
                    }
                    checkConfirmChangedOrdered = false;
                    viewHolder.btnQuatityMenu.setText((qualityCurrent + 1) + "");
                    viewHolder.noteAdd.setVisibility(View.VISIBLE);
                    viewHolder.btnQuatityMenu.setVisibility(View.VISIBLE);
                }
            });


            viewHolder.noteAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String textNote = "";
                    for (Ordered newOrdered : listOrdered) {
                        if (newOrdered.getItemId().equals(menu.getId())) {
                            textNote = newOrdered.getNote();
                            menuPresenterImpl.setTextPopupNoteAdd(textNote, newOrdered);
                            menuPresenterImpl.showPopupNoteAdd();
                            break;
                        }
                    }
                }
            });
        }


        return view;
    }


    public void toast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder {
        ImageView imageMenu;
        TextView tvNameMenu;
        ImageView noteAdd;
        TextView tvPriceMenu;
        Button btnQuatityMenu;
        Button btnPlusMenu;

    }
}
