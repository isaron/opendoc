package cn.ppm123.contact.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import cn.ppm123.contact.HomeActivity;

public class DialogUtil {

    /**
     * 定义一个显示消息的对话框
     *
     * @param ctx
     * @param msg
     * @param goHome
     */
	public static void showDialog(final Context ctx, String msg , boolean goHome) {
        // 创建一个AlertDialog.Builder对象
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setMessage(msg).setCancelable(false);

        if(goHome) {
			builder.setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent i = new Intent(ctx , HomeActivity.class);
					i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					ctx.startActivity(i);
				}
			});
		} else {
			builder.setPositiveButton("确定", null);
		}

		builder.create().show();
	}

    /**
     * 定义一个显示指定组件的对话框
     *
     * @param ctx
     * @param view
     */
	public static void showDialog(Context ctx , View view) {
		new AlertDialog.Builder(ctx).setView(view).setCancelable(false).setPositiveButton("确定", null).create().show();
	}
}
