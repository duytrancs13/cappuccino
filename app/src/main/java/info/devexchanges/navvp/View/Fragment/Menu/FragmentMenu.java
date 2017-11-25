package info.devexchanges.navvp.View.Fragment.Menu;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import info.devexchanges.navvp.General.SetFont;
import info.devexchanges.navvp.Presenter.Menu.MenuPresenterImpl;
import info.devexchanges.navvp.View.Adapter.CustomMenuAdapter;
import info.devexchanges.navvp.Model.Menu;
import info.devexchanges.navvp.R;
import info.devexchanges.navvp.View.Login.LoginActivity;

import static android.content.Context.MODE_PRIVATE;

public class FragmentMenu extends Fragment implements FragmentMenuView {

    private GridView gvMenu;

    private List<Menu> listMenu;

    private CustomMenuAdapter customMenuAdapter;

    private MenuPresenterImpl menuPresenterImpl;

    private ProgressDialog dialog ;

    public static final String MyPREFERENCES = "capuccino" ;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);


        //Set font
        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();

        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String token = prefs.getString("token", null);

        //Mapping
        dialog = new ProgressDialog(this.getContext(),R.style.AppTheme_Dark_Dialog);
        gvMenu = (GridView) view.findViewById(R.id.gvMenu);
        menuPresenterImpl = new MenuPresenterImpl(this,this.getContext());

        menuPresenterImpl.loadMenu(token);

        return view;
    }

    public void toast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        new Progress().execute();
    }

    @Override
    public void showMenu(List<Menu> listMenu) {
        customMenuAdapter = new CustomMenuAdapter(getActivity(), listMenu);
        gvMenu.setAdapter(customMenuAdapter);
        dialog.dismiss();
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
