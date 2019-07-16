package com.example.leoss.zhuye.ParseXls;

import android.content.Context;
import android.content.Intent;

import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.leoss.R;
import com.example.leoss.duanxingxufa.FileDownloadActivity;
import com.example.leoss.duanxingxufa.File_xianshi.SQLter2.Caozuo_sql;
import com.example.leoss.duanxingxufa.File_xianshi.SQLter2.User_sql;
import com.example.leoss.duanxingxufa.File_xianshi.SQliteMsg.Caozuo2;
import com.example.leoss.duanxingxufa.File_xianshi.SQliteMsg.Msg;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ToFileXlsActivity extends AppCompatActivity {

    String path;
    EditText id_file_ed_xls_show1s;
    int dc;

    String[] str1;
    String smsModel2;
    Button button_daunxing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_file_xls);

     button_daunxing=findViewById(R.id.btt_a1);
     button_daunxing.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             startActivity(new Intent(ToFileXlsActivity.this,FileDownloadActivity.class));
         }
     });

        initView();
    }

    private void initView() {

        id_file_ed_xls_show1s = findViewById(R.id.id_file_ed_xls_show);
        Intent intent=getIntent();
        path=intent.getStringExtra("path");
        dc=intent.getIntExtra("DX",0);
        smsModel2=intent.getStringExtra("speng");
        Toast.makeText(this, path+"哈哈", Toast.LENGTH_SHORT).show();

    }



    public void onReadClick(View view) throws FileNotFoundException
    {




        Toast.makeText(this, "请输入正确的xls文件", Toast.LENGTH_SHORT).show();


            try {
                InputStream input = new FileInputStream(path); // 将文件读取成流
                if (path.endsWith(".xls")) {

                    HSSFWorkbook workbook = new HSSFWorkbook(input);
                    HSSFSheet sheet = workbook.getSheetAt(0);
                    sheetAtchatXLS(workbook, sheet);

                } else if (path.endsWith(".xlsx")) {
                    XSSFWorkbook workbook1 = new XSSFWorkbook(input);
                    XSSFSheet sheet1 = workbook1.getSheetAt(0);
                    sheetAtchatXLSX(workbook1, sheet1);
                }

            } catch (Exception e) {

                printlnToUser(e.toString());
            }

    }






    private void sheetAtchatXLSX(XSSFWorkbook workbook1, XSSFSheet sheet1)
    {

        try
        {
            int rowsCount = sheet1.getPhysicalNumberOfRows();
            FormulaEvaluator formulaEvaluator = workbook1.getCreationHelper().createFormulaEvaluator();

            for (int r = 0; r<rowsCount; r++)
            {
                Row row = sheet1.getRow(r);
                int cellsCount = row.getPhysicalNumberOfCells();

                for (int c = 0; c<cellsCount; c++)
              //  for (int c = 0; c<2; c++)
                {
                    String value = getCellAsString(row, c, formulaEvaluator);
                    String cellInfo = "r:"+r+"; c:"+c+"; v:"+value;
                    printlnToUser(cellInfo);
                }
            }


        }catch (Exception e){
            printlnToUser(e.toString());
        }

    }

    private void sheetAtchatXLS( HSSFWorkbook workbook,HSSFSheet sheet)
    {


        if (dc==1)
        {
  Toast.makeText(ToFileXlsActivity.this,"aaaaaa",Toast.LENGTH_SHORT).show();

            try {

                int rowsCount = sheet.getPhysicalNumberOfRows();
                FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

                printlnToUser(String.valueOf(rowsCount));

                for (int r = 1; r<rowsCount; r++)
                {
                    Row row = sheet.getRow(r);
                    int cellsCount = row.getPhysicalNumberOfCells();
                    str1 =new String[cellsCount];

                    for (int c = 0; c<cellsCount; c++)
                    {
                        String value = getCellAsString(row, c, formulaEvaluator);
                        str1[c] = value;
                    }
                    if(cellsCount>5)
                    {
                        User_sql info = new User_sql(str1[1], str1[2], str1[3], str1[4], str1[5], str1[6]);
                        Caozuo_sql caozuor = new Caozuo_sql(this);
                        caozuor.add(info);
                        printlnToUser("添加成功");

                        String content = smsModel2.replace("{name}",str1[1]).replace("{num1}",str1[3]).replace("{num2}",str1[4]).replace("{num3}",str1[5]).replace("{num4}",str1[6]);
                        Msg msg = new Msg(str1[1],str1[2],content);
                        Caozuo2 caozuo1 = new Caozuo2(this);
                        caozuo1.tianjia(msg);

                        Toast.makeText(this, "添加成功数据！", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(this, "NO！", Toast.LENGTH_SHORT).show();
                    }


                }

                Toast.makeText(this, "添加成功数据！", Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                printlnToUser(e.toString());

            }
            /**
             * elxe
             */
        }else {
            Toast.makeText(ToFileXlsActivity.this,"bbbbbbb",Toast.LENGTH_SHORT).show();
            try {
                int rowsCount = sheet.getPhysicalNumberOfRows();
                FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();

                printlnToUser(String.valueOf(rowsCount));
                for (int r = 1; r<rowsCount; r++)
                {
                    Row row = sheet.getRow(r);
                    int cellsCount = row.getPhysicalNumberOfCells();
                    String[] str =new String[cellsCount];

                    for (int c = 0; c<cellsCount; c++)
                    {
                        String value = getCellAsString(row, c, formulaEvaluator);
                        str[c] = value;
                    }
                    if(cellsCount>=6)
                    {
                        Teacher_info teacher_info = new Teacher_info(str[1], str[2], str[3], str[4], str[5], str[6], str[7]);
                        Caozuo_teacher caozuo_teacher = new Caozuo_teacher(this);
                        caozuo_teacher.add(teacher_info);
                        printlnToUser("添加成功");
                    }

                    path = "kong";
                }
                Toast.makeText(this, "添加成功数据！", Toast.LENGTH_SHORT).show();

            }catch (Exception e){
                printlnToUser(e.toString());

            }
       }

    }


    protected String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
        String value = "";
        Cell cell = row.getCell(c);
        CellValue cellValue = formulaEvaluator.evaluate(cell);

        try {
            if(cellValue == null)
            {
                value = "Blank";
            }else
                {

            switch (cellValue.getCellType()) {
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        value = "" + cellValue.getBooleanValue();
                        break;

                    case HSSFCell.CELL_TYPE_NUMERIC:
                        // 返回数值类型的值
                        Object inputValue = null;// 单元格值
                        Long longVal = Math.round(cellValue.getNumberValue());
                        Double doubleVal = cellValue.getNumberValue();
                        if (Double.parseDouble(longVal + ".0") == doubleVal) {   //判断是否含有小数位.0
                            inputValue = longVal;
                        } else {
                            inputValue = doubleVal;
                        }
                        DecimalFormat df = null;    //格式化为四位小数，按自己需求选择；
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            df = new DecimalFormat("#.####");
                        }
                        value = String.valueOf(df.format(inputValue));
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        value = "" + cellValue.getStringValue();
                        break;
                    case HSSFCell.CELL_TYPE_BLANK:
                        value = "哈哈";;
                        break;
                    case HSSFCell.CELL_TYPE_ERROR:
                        value = "呵呵";;
                        break;
                default:
                    value = "未知类型";
            }
        }



        } catch (NullPointerException e) {
            /* proper error handling should be here */
            printlnToUser("请检查xls文档格式，如有疑问，返回首页详情页面！！");
//            printlnToUser(e.toString());
        }
        return value;
    }

    /**
     * print line to the output TextView
     * @param str
     */
    private void printlnToUser(String str) {
        final String string = str;
        if (id_file_ed_xls_show1s.length()>8000)
        {
            CharSequence fullOutput = id_file_ed_xls_show1s.getText();
            fullOutput = fullOutput.subSequence(5000,fullOutput.length());
            id_file_ed_xls_show1s.setText(fullOutput);
            id_file_ed_xls_show1s.setSelection(fullOutput.length());
        }
        id_file_ed_xls_show1s.append(string+"\n");
    }

    public void share(String fileName, Context context) {
        Uri fileUri = Uri.parse("content://"+getPackageName()+"/"+fileName);
        printlnToUser("sending "+fileUri.toString()+" ...");
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, fileUri);
        shareIntent.setType("application/octet-stream");
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
    }

}
