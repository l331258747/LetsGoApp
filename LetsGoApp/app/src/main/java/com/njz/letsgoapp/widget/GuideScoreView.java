package com.njz.letsgoapp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njz.letsgoapp.R;
import com.njz.letsgoapp.util.StringUtils;

/**
 * Created by LGQ
 * Time: 2018/8/15
 * Function:
 */

public class GuideScoreView extends LinearLayout {

    TextView guideScoreService, guideScoreLine, guideScoreScore, guideScoreComment;


    public GuideScoreView(Context context) {
        this(context, null);
    }

    public GuideScoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GuideScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(context).inflate(R.layout.view_guide_score, this, true);

        guideScoreService = findViewById(R.id.guide_score_service);
        guideScoreLine = findViewById(R.id.guide_score_line);
        guideScoreScore = findViewById(R.id.guide_score_score);
        guideScoreComment = findViewById(R.id.guide_score_comment);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.guide_score);
        if (attributes != null) {
            float textSize = attributes.getInteger(R.styleable.guide_score_guide_score_text_size, 0);

            if (textSize != 0) {
                guideScoreService.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
                guideScoreLine.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
                guideScoreScore.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
                guideScoreComment.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
            }
            attributes.recycle();
        }
    }

    public void setGuideScoreService(int service) {
        //service_cout
//        guideScoreService.setText(service + "次服务");
        StringUtils.setHtml(guideScoreService,String.format(getResources().getString(R.string.service_cout),service));
    }

    public void setGuideScoreScore(float score) {
        //service_score
//        guideScoreScore.setText(score + "分");
        StringUtils.setHtml(guideScoreService,String.format(getResources().getString(R.string.service_score),score));
    }

    public void setGuideScoreComment(int comment) {
        guideScoreComment.setText("（" + comment + "条点评）");
    }

    public void setGuideScore(int service,float score,int comment){
        setGuideScoreService(service);
        setGuideScoreScore(score);
        setGuideScoreComment(comment);
    }
}
