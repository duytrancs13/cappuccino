package info.devexchanges.navvp.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import info.devexchanges.navvp.Adapter.CustomFavoriteOrMenuOfCategoryAdapter;
import info.devexchanges.navvp.Model.Menu;
import info.devexchanges.navvp.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FragmentFavoriteOrMenuOfCategory extends Fragment {

    private GridView gvMenu;

    private List<Menu> listMenu;

    private CustomFavoriteOrMenuOfCategoryAdapter customFavoriteOrMenuOfCategoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_or_menu_of_category, container, false);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        gvMenu = (GridView) view.findViewById(R.id.gvMenu);

        listMenu = new ArrayList<Menu>();

//        Menu(String id, String name, int price, String urlImage, String description, int favorite)
        listMenu.add(new Menu("1", "ca phe sua", 29000, "https://tea-1.lozi.vn/v1/images/resized/ca-phe-sua-2854-1390148936?w=320&type=s", "ngon bo re", 1));
        listMenu.add(new Menu("2", "ca phe sua", 29000, "https://tea-1.lozi.vn/v1/images/resized/ca-phe-sua-2854-1390148936?w=320&type=s", "ngon bo re", 2));

        customFavoriteOrMenuOfCategoryAdapter = new CustomFavoriteOrMenuOfCategoryAdapter(getActivity(), listMenu);

        gvMenu.setAdapter(customFavoriteOrMenuOfCategoryAdapter);
        return view;
    }
    protected void attachBaseContext(Context newBase) {
        super.onAttach(CalligraphyContextWrapper.wrap(newBase));
    }
}
