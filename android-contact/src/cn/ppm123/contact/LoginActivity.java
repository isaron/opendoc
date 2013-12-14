package cn.ppm123.contact;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TableLayout;
import cn.ppm123.contact.util.DialogUtil;
import cn.ppm123.contact.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends Activity {

    private ProgressDialog loadDialog;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

        TableLayout bg = (TableLayout) findViewById(R.id.container);
        bg.setBackgroundColor(Color.WHITE);
    }

    public void loginHandler(View view) {

        loadDialog = ProgressDialog.show(this, "提示", "正在登录中...", true, false);

        loaderUpdateHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                EditText unity = (EditText) findViewById(R.id.unity);
                EditText username = (EditText) findViewById(R.id.username);
                EditText password = (EditText) findViewById(R.id.password);

                Map<String, String> params = new HashMap();
                params.put("unity", unity.getText().toString());
                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());

                String url = HttpUtil.BASE_URL + "/app/checkLogin.do";

                try {
                    String result = HttpUtil.postRequest(url, params);

                    if("Y".equals(result)) {
                        Intent intent = new Intent(LoginActivity.this , HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("unity", unity.getText().toString());
                        startActivity(intent);
                        finish();

                    } else {
                        DialogUtil.showDialog(LoginActivity.this, "用户名密码输入有误，请重新输入。", false);
                    }

                } catch(Exception e) {
                    DialogUtil.showDialog(LoginActivity.this, "请求服务器失败，请检查网络连接。", false);
                } finally {
                    loaderUpdateHandler.sendEmptyMessage(0);
                }
            }
        }, 1);
    }

    private Handler loaderUpdateHandler = new Handler() {

        @Override
        public void handleMessage(Message message) {

            if(loadDialog != null) {
                loadDialog.dismiss();
            }
        }
    };
}
