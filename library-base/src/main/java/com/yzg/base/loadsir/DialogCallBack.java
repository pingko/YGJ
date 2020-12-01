package com.yzg.base.loadsir;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tamsiree.rxkit.RxNetTool;
import com.yzg.base.utils.ToastUtil;

public abstract class DialogCallBack extends StringCallback {

    ProgressDialog dialog;

    public DialogCallBack(Context activity) {
        super();
        initDialog(activity);
    }

    private void initDialog(Context activity) {
        if (dialog == null) {
            dialog = new ProgressDialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("加载中...");
        }
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        if (dialog.getContext() instanceof Activity) {
            if (!((Activity) dialog.getContext()).isFinishing()) {
                dialog.show();
            }
        }


//        if (dialog != null && !dialog.isShowing()) {
//            dialog.show();
//        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
//        if (dialog != null && dialog.isShowing()) {
//            dialog.dismiss();
//            dialog = null;
//        }
        if (dialog != null && dialog.getContext() instanceof Activity) {
            if (!((Activity) dialog.getContext()).isFinishing()) {
                dialog.dismiss();
                dialog = null;
            }
        }
    }

    @Override
    public void onError(Response<String> response) {
        super.onError(response);
        if (!RxNetTool.isNetworkAvailable(dialog.getContext())) {
            ToastUtil.show(dialog.getContext(), "网络异常");
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//                dialog = null;
//            }
            if (dialog != null && dialog.getContext() instanceof Activity) {
                if (!((Activity) dialog.getContext()).isFinishing()) {
                    dialog.dismiss();
                    dialog = null;
                }
            }
        }
    }
}
