package com.njz.letsgoapp.mvp.other;

import android.content.Context;

import com.njz.letsgoapp.bean.EmptyModel;
import com.njz.letsgoapp.util.http.MethodApi;
import com.njz.letsgoapp.util.http.OnSuccessAndFaultSub;
import com.njz.letsgoapp.util.http.ResponseCallback;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/11/8
 * Function:
 */

public class ReportPresenter implements ReportContract.Presenter {

    Context context;
    ReportContract.View iView;

    public ReportPresenter(Context context, ReportContract.View iView) {
        this.context = context;
        this.iView = iView;
    }

    @Override
    public void upUserReport(int reportId, String reportContent, String reportReason, int coverReportUserType,
                             int reportContentId,int reportClass, List<String> files) {
        ResponseCallback listener = new ResponseCallback<EmptyModel>() {
            @Override
            public void onSuccess(EmptyModel data) {
                iView.upUserReportSuccess(data);
            }

            @Override
            public void onFault(String errorMsg) {
                iView.upUserReportFailed(errorMsg);
            }
        };
        MethodApi.reportUserReport(reportId, reportContent, reportReason,coverReportUserType,
                reportContentId,reportClass, files, new OnSuccessAndFaultSub(listener, context, false));
    }
}
