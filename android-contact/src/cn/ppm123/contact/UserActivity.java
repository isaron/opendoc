package cn.ppm123.contact;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.ppm123.contact.util.DialogUtil;
import cn.ppm123.contact.util.HttpUtil;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-11-23
 * Time: 上午10:34
 * To change this template use File | Settings | File Templates.
 */
public class UserActivity extends Activity {

    private String cellphone;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.user);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.titlebar);

        LinearLayout userContainer = (LinearLayout) findViewById(R.id.userContainer);
        userContainer.setBackgroundColor(Color.WHITE);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");

        Map<String, String> params = new HashMap();
        params.put("userId", userId);

        String url = HttpUtil.BASE_URL + "/app/getUserInfo.do";

        try {
            String result = HttpUtil.postRequest(url, params);
            JSONObject user = new JSONObject(result);

            TextView username = (TextView) findViewById(R.id.username);
            username.setText(user.getString("username"));

            TextView depart = (TextView) findViewById(R.id.depart);
            depart.setText(user.getString("departName"));

            TextView position = (TextView) findViewById(R.id.position);
            position.setText(user.getString("positionName"));

            TextView cellphone = (TextView) findViewById(R.id.cellphone);
            cellphone.setText(user.getString("cellphone"));
            this.cellphone = user.getString("cellphone");

            TextView telphone = (TextView) findViewById(R.id.telphone);
            telphone.setText(user.getString("telphone"));

            TextView qq = (TextView) findViewById(R.id.qq);
            qq.setText(user.getString("qq"));

            TextView weixin = (TextView) findViewById(R.id.weixin);
            weixin.setText(user.getString("weixin"));

            TextView email = (TextView) findViewById(R.id.email);
            email.setText(user.getString("email"));

            TextView address = (TextView) findViewById(R.id.address);
            address.setText(user.getString("address"));

        } catch(Exception e) {
            DialogUtil.showDialog(this, "请求服务器失败，请检查网络连接。", false);
        }
    }

    public void callHandler(View view) {

        if(this.cellphone == null || "".equals(this.cellphone.trim())) {
            DialogUtil.showDialog(this, "手机号码为空。", false);
        }

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + this.cellphone));
        startActivity(intent);
    }

    public void messageHandler(View view) {

        if(this.cellphone == null || "".equals(this.cellphone.trim())) {
            DialogUtil.showDialog(this, "手机号码为空。", false);
        }

        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + this.cellphone));
        startActivity(intent);
    }
}
