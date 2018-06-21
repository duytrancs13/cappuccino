package io.awesome.app.View.Fragment.ChooseMenu;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.awesome.app.General.SetFont;
import io.awesome.app.Model.Ordered;
import io.awesome.app.Presenter.ChooseMenu.ChooseMenuPresenterImp;
import io.awesome.app.View.Adapter.ViewPagerFragmentChooseMenuAdapter;
import io.awesome.app.R;
import io.awesome.app.View.Table.TableActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.content.Context.MODE_PRIVATE;
import static io.awesome.app.View.Table.TableActivity.checkConfirmChangedOrdered;
import static io.awesome.app.View.Table.TableActivity.listOrdered;

public class FragmentChooseMenu extends Fragment implements FragmentChooseMenuView {
    private TabLayout tab_layout_fragment;
    private ViewPager view_pager_fragment;
    private String[] pageTitle = {"Chọn nhiều", "Theo loại"};
    private ViewPagerFragmentChooseMenuAdapter pagerAdapter;
    private Button btnConfirmOrdered;
    private ChooseMenuPresenterImp chooseMenuPresenterImp;
    public static final String MyPREFERENCES = "capuccino" ;
    private SharedPreferences prefs;
    private String token;
    private ProgressDialog dialog ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_choose_menu, container, false);

        SetFont setFont = new SetFont("Roboto-Regular.ttf");
        setFont.getFont();

        dialog = new ProgressDialog(view.getContext(),R.style.AppTheme_Dark_Dialog);

        tab_layout_fragment = (TabLayout) view.findViewById(R.id.tab_layout_fragment);
        view_pager_fragment = (ViewPager)view.findViewById(R.id.view_pager_fragment);


        for (int i = 0; i < 2; i++) {
            tab_layout_fragment.addTab(tab_layout_fragment.newTab().setText(pageTitle[i]));
        }
        tab_layout_fragment.setTabGravity(TabLayout.GRAVITY_FILL);

        pagerAdapter = new ViewPagerFragmentChooseMenuAdapter(getFragmentManager());
        view_pager_fragment.setAdapter(pagerAdapter);

        view_pager_fragment.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab_layout_fragment));



        tab_layout_fragment.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                view_pager_fragment.setCurrentItem(tab.getPosition());
                pagerAdapter.notifyDataSetChanged();


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        prefs = this.getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        token = prefs.getString("token", null);

        chooseMenuPresenterImp = new ChooseMenuPresenterImp(view.getContext(),this, token);

        btnConfirmOrdered = (Button) view.findViewById(R.id.btnConfirmOrdered);
        btnConfirmOrdered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkConfirmChangedOrdered){
                    showProgress();
                    try {
                        JSONArray jsonArrayItems = new JSONArray();
                        for (Ordered ordered : listOrdered) {

                            JSONObject jsonObject = new JSONObject()
                                    .put("itemId", ordered.getItemId())
                                    .put("quantity", ordered.getQuantity())
                                    .put("note", ordered.getNote());

                            jsonArrayItems.put(jsonObject);
                        }
                        JSONObject object = new JSONObject().put("items",jsonArrayItems);
                        Log.v("AAA", object.toString());
                        chooseMenuPresenterImp.confirmOrdered(object);
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    toast("Vui lòng chọn món");
                }
            }
        });




        return view;
    }

    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
        super.onAttach(CalligraphyContextWrapper.wrap(newBase));
    }

    public void toast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }



    @Override
    public void alertMessage(String titleError, String textError, int responseCode) {
        if(responseCode == 500) {
            Alerter.create(getActivity())
                    .setTitle(titleError)
                    .setText(textError)
                    .setBackgroundColorRes(R.color.red) // or setBackgroundColorInt(Color.CYAN)
                    .show();
        }else{
            Alerter.create(getActivity())
                    .setTitle(titleError)
                    .setText(textError)
                    .setBackgroundColorRes(R.color.colorPrimary) // or setBackgroundColorInt(Color.CYAN)
                    .show();
            checkConfirmChangedOrdered = true;
        }
        dialog.dismiss();
    }

    @Override
    public void showProgress() {
        new Progress().execute();
    }
    private class Progress extends AsyncTask<Void, Void, Void> {

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

