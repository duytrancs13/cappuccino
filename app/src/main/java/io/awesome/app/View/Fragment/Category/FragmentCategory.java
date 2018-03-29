package io.awesome.app.View.Fragment.Category;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import io.awesome.app.Presenter.Category.CategoryPresenterImpl;
import io.awesome.app.View.Adapter.CustomCategoryAdapter;
import io.awesome.app.Model.Category;
import io.awesome.app.R;
import io.awesome.app.View.Fragment.Menu.FragmentMenu;

import static android.content.Context.MODE_PRIVATE;

public class FragmentCategory extends Fragment implements FragmentCategoryView{
    private ListView lvCategory;


    private CustomCategoryAdapter categoryAdapter;

    public static final String MyPREFERENCES = "capuccino" ;

    private CategoryPresenterImpl categoryPresenter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        lvCategory = (ListView) view.findViewById(R.id.lvCategory);

        SharedPreferences prefs = this.getActivity().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        String token = prefs.getString("token", null);

        categoryPresenter = new CategoryPresenterImpl(view.getContext(),this);

        categoryPresenter.loadMenuCategory(token);

        return view;
    }

    public void toast(String message){
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showMenuCategory(final List<Category> listCategory) {
        categoryAdapter = new CustomCategoryAdapter(getActivity(), listCategory);

        lvCategory.setAdapter(categoryAdapter);
        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                FragmentMenu fragmentMenu = new FragmentMenu(1,listCategory.get(position).getId());
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_root,fragmentMenu).commit();
            }
        });


    }
}
