package com.njz.letsgoapp.mvp.other;

import com.njz.letsgoapp.bean.other.SearchCityModel;

import java.util.List;

/**
 * Created by LGQ
 * Time: 2018/10/12
 * Function:
 */

public interface CitySearchContract {

    //regionFuzzyBySpell
    interface Presenter {
        void regionFuzzyBySpell(String spell);
    }

    interface View {
        void regionFuzzyBySpellSuccess(List<SearchCityModel> models);
        void regionFuzzyBySpellFailed(String msg);
    }
}
