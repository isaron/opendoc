package cn.ppm123.contact;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.*;
import cn.ppm123.contact.util.DialogUtil;
import cn.ppm123.contact.util.HttpUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-19
 * Time: 下午4:54
 * To change this template use File | Settings | File Templates.
 */
public class HomeActivity extends Activity {

    private String unity;
    private Stack<String> grandParent = new Stack();

    private ProgressDialog loadDialog;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.home);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

        View sTab1 = (View) LayoutInflater.from(this).inflate(R.layout.tabmini, null);
        TextView text1 = (TextView) sTab1.findViewById(R.id.tab_label);
        text1.setText("组织架构");

        View sTab2 = (View) LayoutInflater.from(this).inflate(R.layout.tabmini, null);
        TextView text2 = (TextView) sTab2.findViewById(R.id.tab_label);
        text2.setText("我的信息");

        View sTab3 = (View) LayoutInflater.from(this).inflate(R.layout.tabmini, null);
        TextView text3 = (TextView) sTab3.findViewById(R.id.tab_label);
        text3.setText("搜索");

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setBackgroundColor(Color.WHITE);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("sTab1").setIndicator(sTab1).setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("sTab2").setIndicator(sTab2).setContent(R.id.tab2);
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("sTab3").setIndicator(sTab3).setContent(R.id.tab3);
        tabHost.addTab(tab3);

        Intent intent = getIntent();
        unity = intent.getStringExtra("unity");
        searchStructure(unity, null, false);
    }

    /**
     * catch return event
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(grandParent.size() > 1) {
                loadDialog = ProgressDialog.show(HomeActivity.this, "提示", "正在加载数据...", true, false);

                loaderUpdateHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        grandParent.pop();
                        searchStructure(unity, grandParent.lastElement(), true);

                        loaderUpdateHandler.sendEmptyMessage(0);
                    }
                }, 1);
            }
            else {
                // 创建退出对话框
                AlertDialog isExit = new AlertDialog.Builder(this).create();
                isExit.setTitle("系统提示");
                isExit.setMessage("确定要退出吗");
                isExit.setButton("确定", ExitListener);
                isExit.setButton2("取消", ExitListener);
                isExit.show();
            }

        }

        return false;
    }

    /**
     * search structure data
     *
     * @param unity
     * @param parentId
     */
    private void searchStructure(final String unity, final String parentId, boolean isReturn) {
        // push grand parent
        if(!isReturn) {
            grandParent.push(parentId == null ? "" : parentId);
        }

        Map<String, String> params = new HashMap();
        params.put("unity", unity);
        params.put("parentId", parentId);

        String url = HttpUtil.BASE_URL + "/app/getStructure.do";

        try {
            String result = HttpUtil.postRequest(url, params);
            final JSONArray datas = new JSONArray(result);
            JSONObject data = null;

            int departCount = 0;
            List<Map<String, String>> departs = new ArrayList();
            List<String> users = new ArrayList();

            for(int i = 0; i < datas.length(); i++) {
                data = datas.getJSONObject(i);

                if("Y".equals(data.getString("isDepart"))) {
                    departCount++;
                    Map<String, String> rowMap = new HashMap();
                    rowMap.put("name", data.getString("departName"));
                    departs.add(rowMap);
                } else {
                    users.add(data.getString("username"));
                }
            }

            SimpleAdapter departAdapter = new SimpleAdapter(this, departs, R.layout.checked_item,
                    new String[] {"name"}, new int[] {R.id.departName});

            ListView departListView = (ListView) findViewById(R.id.departListView);
            departListView.setAdapter(departAdapter);

            departListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView adapterView, View view, final int position, long id) {

                    loadDialog = ProgressDialog.show(HomeActivity.this, "提示", "正在加载数据...", true, false);

                    loaderUpdateHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject departData = datas.getJSONObject(position);
                                searchStructure(unity, departData.getString("id"), false);

                                loaderUpdateHandler.sendEmptyMessage(0);

                            } catch(Exception e) {
                                DialogUtil.showDialog(HomeActivity.this, "系统出现错误：" + e.getMessage(), false);
                            }
                        }
                    }, 1);
                }
            });

            ArrayAdapter<String> userAdapter = new ArrayAdapter<String>(this, R.layout.list_item, users);
            ListView userListView = (ListView) findViewById(R.id.userListView);
            userListView.setAdapter(userAdapter);

            final int dc = departCount;

            userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView adapterView, View view, final int position, long id) {

                    loadDialog = ProgressDialog.show(HomeActivity.this, "提示", "正在加载用户...", true, false);

                    loaderUpdateHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject userData = datas.getJSONObject(dc + position);

                                Intent intent = new Intent(HomeActivity.this , UserActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("userId", userData.getString("id"));
                                startActivity(intent);

                                loaderUpdateHandler.sendEmptyMessage(0);

                            } catch(Exception e) {
                                DialogUtil.showDialog(HomeActivity.this, "系统出现错误：" + e.getMessage(), false);
                            }
                        }
                    }, 1);
                }
            });

        } catch(Exception e) {
            loaderUpdateHandler.sendEmptyMessage(0);
            DialogUtil.showDialog(this, "请求服务器失败，请检查网络连接。", false);
        }
    }

    /**
     * 监听对话框里面的button点击事件
     */
    DialogInterface.OnClickListener ExitListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:  // "确认"按钮退出程序
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:  // "取消"按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };

    private Handler loaderUpdateHandler = new Handler() {

        @Override
        public void handleMessage(Message message) {

            if(loadDialog != null) {
                loadDialog.dismiss();
            }
        }
    };
}