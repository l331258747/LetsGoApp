package com.njz.letsgoapp.mvp.other;

import com.njz.letsgoapp.bean.EmptyModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/11/8
 * Function:
 */

public interface ReportContract {

    interface Presenter {
        void upUserReport(int reportId, String reportContent, String reportReason, int coverReportUserType,
                          int reportContentId,int reportClass, List<String> files);
    }

    interface View {
        void upUserReportSuccess(EmptyModel models);
        void upUserReportFailed(String msg);
    }

}
