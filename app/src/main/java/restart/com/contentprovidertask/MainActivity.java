package restart.com.contentprovidertask;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import restart.com.contentprovidertask.adapter.ElvAdapter;
import restart.com.contentprovidertask.entity.Dish;

public class MainActivity extends AppCompatActivity {
    public static final Uri uri = Uri.parse("content://com.imooc.menuprovider");
    private ExpandableListView elv;
    private static ContentResolver contentResolver;
    private ElvAdapter adapter;
    /*增加菜单*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("新增").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    protected void onResume() {
        updata();
        super.onResume();
    }

    /*增加菜单*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                startActivity(new Intent(this,AddActivity.class));
                break;

        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentResolver = getContentResolver();
        elv = findViewById(R.id.Elv);

    }
    /*更新&初始化数据操作*/
    public void updata() {
        if (adapter!=null){
            adapter.setData(getMenu(),getData());
            adapter.notifyDataSetChanged();
        }else {
            adapter = new ElvAdapter(MainActivity.this, getMenu(), getData());
            elv.setAdapter(adapter);
        }
    }

    /**
     * 获得所有的子条目
     *
     * @return
     */
    private List<List<Dish>> getData() {
        List<List<Dish>> childData = new ArrayList<>();
        List<String> menu = getMenu();
        for (String menuType : menu) {
            List<Dish> dishes = new ArrayList<>();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            while (cursor.moveToNext()) {
                Dish dish = new Dish();
                String _id = cursor.getString(cursor.getColumnIndex("dish_id"));
                String dish_name = cursor.getString(cursor.getColumnIndex("dish_name"));
                String dish_type = cursor.getString(cursor.getColumnIndex("dish_type"));
                if (dish_type.equals(menuType)) {//分类
                    dish.setDish_id(_id);
                    dish.setDish_name(dish_name);
                    dish.setDish_type(dish_type);
                    dishes.add(dish);
                }
            }
            childData.add(dishes);
        }


        return childData;
    }

    /**
     * 获得type列表
     *
     * @return
     */
    public static List<String> getMenu() {
        List<String> menuList = new ArrayList<>();
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        String temp = "";
        while (cursor.moveToNext()) {
            String menu = cursor.getString(cursor.getColumnIndex("dish_type"));
            if (!temp.equals(menu)) {
                temp = menu;
                menuList.add(temp);
            }
        }
        return menuList;
    }

}
