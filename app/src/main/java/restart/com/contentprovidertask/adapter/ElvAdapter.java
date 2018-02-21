package restart.com.contentprovidertask.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import restart.com.contentprovidertask.MainActivity;
import restart.com.contentprovidertask.R;
import restart.com.contentprovidertask.entity.Dish;

/**
 * Created by Administrator on 2018/2/21.
 */

public class ElvAdapter extends BaseExpandableListAdapter {
    private List<String> menuList;
    private List<List<Dish>> data;
    private Context context;

    public ElvAdapter(Context context,List<String> menuList, List<List<Dish>> data) {
        this.menuList = menuList;
        this.data = data;
        this.context = context;
    }

    public void setData(List<String> menuList, List<List<Dish>> data){
        this.menuList = menuList;
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return menuList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).size();
    }

    @Override
    public String getGroup(int groupPosition) {
        return menuList.get(groupPosition);
    }

    @Override
    public Dish getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {

        return Long.parseLong(data.get(groupPosition).get(childPosition).getDish_id());
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        MenuViewHolder menuViewHolder ;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_menu, null);
            menuViewHolder = new MenuViewHolder();
            menuViewHolder.menuTxt = convertView.findViewById(R.id.menu_txt);
            convertView.setTag(menuViewHolder);
        }else {
            menuViewHolder = (MenuViewHolder) convertView.getTag();
        }
        menuViewHolder.menuTxt.setText("--"+menuList.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final DishViewHolder dishViewHolder ;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_name, null);
            dishViewHolder = new DishViewHolder();
            dishViewHolder.dishTxt = convertView.findViewById(R.id.name_txt);
            convertView.setTag(dishViewHolder);
        }else {
            dishViewHolder = (DishViewHolder) convertView.getTag();
        }
        dishViewHolder.dishTxt.setText(data.get(groupPosition).get(childPosition).getDish_name());
        final String _id = data.get(groupPosition).get(childPosition).getDish_id();
        dishViewHolder.dishTxt.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String name =  dishViewHolder.dishTxt.getText().toString();
                builder.setMessage("您确定要删除["+name+"]吗?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int count = context.getContentResolver().delete(MainActivity.uri,"dish_id=?",new String[]{_id});
                        if (count > 0) {
                            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                            data.get(groupPosition).remove(childPosition);
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("取消",null);
                builder.setCancelable(false);
                builder.show();
                return true;
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class MenuViewHolder{
        TextView menuTxt;
    }
    class DishViewHolder{
        TextView dishTxt;
    }
}
