package info.devexchanges.navvp.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import info.devexchanges.navvp.Adapter.CustomCategoryAdapter;
import info.devexchanges.navvp.Model.Category;
import info.devexchanges.navvp.R;

public class FragmentCategory extends Fragment {
    private ListView lvCategory;

    private List<Category> listCategory;

    private CustomCategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        lvCategory = (ListView) view.findViewById(R.id.lvCategory);

        listCategory = new ArrayList<Category>();


//        Category(String id, String name, int createAt, String urlImage, List<String> listMenuOfCategory)
        listCategory.add(new Category("1","Ca phe",1,"https://tea-1.lozi.vn/v1/images/resized/ca-phe-sua-2854-1390148936?w=320&type=s",null));
        listCategory.add(new Category("1","Ca phe da",1,"https://tea-1.lozi.vn/v1/images/resized/ca-phe-sua-2854-1390148936?w=320&type=s",null));
        listCategory.add(new Category("1","Thuc an",1,"https://tea-1.lozi.vn/v1/images/resized/ca-phe-sua-2854-1390148936?w=320&type=s",null));
        listCategory.add(new Category("1","Sinh to",1,"https://tea-1.lozi.vn/v1/images/resized/ca-phe-sua-2854-1390148936?w=320&type=s",null));

        categoryAdapter = new CustomCategoryAdapter(getActivity(), listCategory);

        lvCategory.setAdapter(categoryAdapter);
        return view;
    }
}
