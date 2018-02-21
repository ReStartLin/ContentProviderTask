package restart.com.contentprovidertask;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText nameEdt;
    private Button addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        spinner = findViewById(R.id.spinner);
        nameEdt = findViewById(R.id.dish_name_edt);
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item,MainActivity.getMenu()));

        addBtn = findViewById(R.id.add_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                String type = (String) spinner.getSelectedItem();
                String name = nameEdt.getText().toString();
                values.put("dish_name",name);
                values.put("dish_type",type);
                Log.e("", "onClick: "+type);
                Log.e("", "onClick: "+name);

                if (!name.equals("")) {
                   Uri uri = getContentResolver().insert(MainActivity.uri, values);
                   long id = ContentUris.parseId(uri);
                    if (id > -1) {
                        Toast.makeText(AddActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        nameEdt.setText("");
                        
                    }else {
                        Toast.makeText(AddActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                    }
                    
                }
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish(); // 返回
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
