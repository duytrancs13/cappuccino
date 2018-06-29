package io.awesome.app.View.Fragment.Menu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.awesome.app.General.SetFont;
import io.awesome.app.Model.Ordered;
import io.awesome.app.Presenter.Menu.MenuPresenterImpl;
import io.awesome.app.View.Adapter.CustomMenuAdapter;
import io.awesome.app.Model.Menu;
import io.awesome.app.R;



import static android.content.Context.MODE_PRIVATE;
import static io.awesome.app.View.Table.TableActivity.checkConfirmChangedOrdered;

@SuppressLint("ValidFragment")
public class FragmentMenu extends Fragment implements FragmentMenuView {

    private int statusMenu;
    private String idCategory;

    public FragmentMenu(int statusMenu, String idCategory) {
        this.statusMenu = statusMenu;
        this.idCategory = idCategory;
    }

    private GridView gvMenu;

    private CustomMenuAdapter customMenuAdapter;

    private MenuPresenterImpl menuPresenterImpl;

    private ProgressDialog dialog ;

    public static final String MyPREFERENCES = "capuccino" ;

    private String token;

    private Button btnPlusMenu;

    private AlertDialog.Builder alert;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);


        //Set font
        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();

        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = prefs.getString("token", null);


        //Mapping
        dialog = new ProgressDialog(this.getContext(),R.style.AppTheme_Dark_Dialog);
        gvMenu = (GridView) view.findViewById(R.id.gvMenu);
        menuPresenterImpl = new MenuPresenterImpl(this,this.getContext());

        btnPlusMenu = (Button) view.findViewById(R.id.btnPlusMenu);

        alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Thêm ghi chú");


        if(statusMenu == 0){
            menuPresenterImpl.loadMenuOfFavorite(token);
        }else if(statusMenu == 1){
            menuPresenterImpl.loadMenuOfCategory(this.idCategory,token);
        }






        return view;
    }

    public void toast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }



    @Override
    public void showMenu(List<Menu> listMenu) {

        customMenuAdapter = new CustomMenuAdapter(getActivity(), listMenu, menuPresenterImpl, token);

        gvMenu.setAdapter(customMenuAdapter);

        dialog.dismiss();
    }



    @Override
    public void setTextPopupNodeAdd(final String textNote, final Ordered ordered) {
        /*alert.setMessage(textNote);*/
        final EditText input = new EditText(getContext());

        alert.setView(input);
        input.setText(textNote);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            //menuPresenterImpl.addNoteForReceipt(input.getText().toString(),menuId, token);
                ordered.setNote(input.getText().toString());
                checkConfirmChangedOrdered = false;
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });
    }

    @Override
    public void showPopupNodeAdd() {
        alert.show();
    }

    @Override
    public void showProgress() {
        new Progress().execute();
    }

    private class Progress extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Đang kết nối...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
