package com.example.len.mytime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txt_time = null;  //显示剩余时间
    private Button btn_start = null;   //各个按钮
    private Button btn_reset = null;
    private Button btn_exit = null;

    private long hour = 0;//时间变量
    private long minute = 0;
    private long second = 0;
    private long time = 0;

    private MyCount count = null;//定时类对象  在下面代码中有定义class——MyCount。


    View v1,v2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    //    requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        v1 = getLayoutInflater().inflate(R.layout.activity_main,null);

        setContentView(v1);

        findViews();
        setListener();
        hour = Long.parseLong("0");
        minute = Long.parseLong("30");
        second = Long.parseLong("0");
        time = (hour * 3600 + minute * 60 + second) * 1000;  //因为以ms为单位，所以乘以1000.
        count = new MyCount(time, 1000);

    //    getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title);
    }

    private void findViews(){
        txt_time = (TextView) findViewById(R.id.text);
        btn_start = (Button) findViewById(R.id.start);
        btn_reset = (Button) findViewById(R.id.reset);
        btn_exit = (Button) findViewById(R.id.exit);

    }

    //绑定Button监听器
    private void setListener() {
        btn_start.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())    //获取按键的ID值！！！！！！
        {
            //点击开始计时按钮
            case R.id.start:

                count.start();  //开始计时  ！！！！！！！！！！
                btn_start.setEnabled(false);
                btn_reset.setVisibility(v.VISIBLE);
                btn_start.setVisibility(v.INVISIBLE);
                btn_reset.setEnabled(true);


                break;
            //点击重置按钮
            case R.id.reset:
                count.cancel();//取消及时，重置界面状态
                txt_time.setText("0:30:00");

                btn_start.setEnabled(true);
                btn_start.setVisibility(v.VISIBLE);
                btn_reset.setEnabled(false);
                btn_reset.setVisibility(v.INVISIBLE);

                break;


            default:
                break;
        }
    }


    class MyCount extends AdvancedCountdownTime{
        public MyCount(long millisInFuture, long countDownInterval) {  //这两个参数在AdvancedCountdownTimer.java中均有(在“构造函数”中).
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            txt_time.setText("0：0：0");
        }

        //更新剩余时间
        @Override
        public void onTick(long millisUntilFinished, int percent) {
            long myhour = (millisUntilFinished / 1000) / 3600;
            long myminute = ((millisUntilFinished / 1000) - myhour * 3600) / 60;
            long mysecond = millisUntilFinished / 1000 - myhour * 3600
                    - myminute * 60;
            txt_time.setText(myhour + ":" + myminute + ":" + mysecond);
        }
    }

//    在mainActivity中显示菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

//    在更多菜单的item中添加点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.task:
                //此处添加任务点击事件
                //跳转到任务界面
                Intent intent1=new Intent(this,TaskLayout.class);
                startActivity(intent1);
                break;
            case R.id.statics:
                //此处添加统计点击事件
                //跳转到统计界面
                Intent intent2=new Intent(this,StaticsLayout.class);
                startActivity(intent2);
                break;
            case R.id.setting:
                //此处添加设置事件
                //跳转到设置界面
                Intent intent3=new Intent(this,SettingLayout.class);
                startActivity(intent3);
                break;
            case R.id.about:
                //此处添加关于事件
                //跳转到关于界面
                Intent intent4=new Intent(this,AboutLayout.class);
                startActivity(intent4);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
