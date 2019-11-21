package com.itnnsoft.custom_design_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.itnnsoft.custom_design_sqlite.adapter.LienHe_Adapter;
import com.itnnsoft.custom_design_sqlite.data.DBManager;
import com.itnnsoft.custom_design_sqlite.model.LienHe;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edt_sdt,edt_ten;
    RadioButton rdo_nam,rdo_nu;
    ListView lsv_lienhe;
    Button btn_them,btn_luu;
    DBManager dbManager;
    LienHe_Adapter lienHe_adapter;
    List<LienHe> listLienhe;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new DBManager(this);

        edt_sdt=(EditText) findViewById(R.id.edt_number);
        edt_ten=(EditText) findViewById(R.id.edt_name);
        rdo_nam=(RadioButton)findViewById(R.id.rdo_nam);
        rdo_nu=(RadioButton) findViewById(R.id.rdo_nu);

        btn_them = (Button) findViewById(R.id.btn_them);
        btn_luu = (Button) findViewById(R.id.btn_luu);

        lsv_lienhe = (ListView) findViewById(R.id.lv_lienhe);

        listLienhe = dbManager.getAllLienhe();
        setAdapter();

        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LienHe lienHe = createLienhe();
                if(lienHe!=null){
                    dbManager.addLienhe(lienHe);
                }
                updateListLienhe();
                setAdapter();
                edt_sdt.setText("");
                edt_ten.setText("");
            }
        });

        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LienHe lienHe = new LienHe();
                lienHe.setNumber(edt_sdt.getText().toString());
                lienHe.setTen(edt_ten.getText().toString());
                if(rdo_nam.isChecked()){
                    lienHe.setGioi_tinh("Nam");
                }else {
                    lienHe.setGioi_tinh("Nu");
                }
                dbManager.updateLienhe(lienHe);
                updateListLienhe();
                setAdapter();
                edt_sdt.setText("");
                edt_ten.setText("");
            }
        });
        lsv_lienhe.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog_lienhe);
                Button btn_sua = (Button)dialog.findViewById(R.id.btn_luu);
                Button btn_xoa = (Button)dialog.findViewById(R.id.btn_xoa);
                Button btn_call = (Button)dialog.findViewById(R.id.btn_goi);
                final Integer vi_tri = i;

                btn_sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        edt_sdt.setText(listLienhe.get(vi_tri).getNumber());
                        edt_ten.setText(listLienhe.get(vi_tri).getTen());
                        if(listLienhe.get(vi_tri).getGioi_tinh().compareTo("Nu")==0){
                            rdo_nu.setChecked(true);
                            rdo_nam.setChecked(false);
                        }else {
                            rdo_nam.setChecked(true);
                            rdo_nu.setChecked(false);
                        }
                        dialog.cancel();
                    }

                });

                btn_xoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dbManager.deleteLienhe(listLienhe.get(vi_tri).getNumber());
                        Toast.makeText(MainActivity.this,"Đã xoá liên hệ thành công",Toast.LENGTH_LONG).show();
                        updateListLienhe();
                        setAdapter();

                        dialog.cancel();
                    }
                });
                btn_call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+listLienhe.get(vi_tri).getNumber()));
                        startActivity(intent);
                        dialog.cancel();
                    }
                });
                dialog.show();
                return false;
            }
        });


    }


    private void setAdapter() {
        if (lienHe_adapter == null) {
            lienHe_adapter = new LienHe_Adapter(this, R.layout.lienhe_item, listLienhe);
            lsv_lienhe.setAdapter(lienHe_adapter);
        }else{
            lienHe_adapter.notifyDataSetChanged();
            lsv_lienhe.setSelection(lienHe_adapter.getCount()-1);
        }
    }

    public void updateListLienhe(){
        listLienhe.clear();
        listLienhe.addAll(dbManager.getAllLienhe());
        if(lienHe_adapter!= null){
            lienHe_adapter.notifyDataSetChanged();
        }
    }

    private LienHe createLienhe() {
        String name = edt_ten.getText().toString();
        String number = edt_sdt.getText().toString();
        String gioi_tinh="Nam";
        if(rdo_nu.isChecked()){
            gioi_tinh="Nu";
        }

        LienHe lienHe = new LienHe(number,name,gioi_tinh);
        return lienHe;
    }
}
