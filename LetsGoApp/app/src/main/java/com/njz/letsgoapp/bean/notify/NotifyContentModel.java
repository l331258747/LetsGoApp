package com.njz.letsgoapp.bean.notify;

import com.njz.letsgoapp.util.GsonUtil;

/**
 * Created by LGQ
 * Time: 2018/10/12
 * Function:
 */

public class NotifyContentModel {


    /**
     * alert :  今晚十点将进行停机维护
     * other : {}
     */

    private String alert;
    private String other;

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public NotifyContentOtherModel getOther() {
        return GsonUtil.convertString2Object(other,NotifyContentOtherModel.class);
    }

    public void setOther(String other) {
        this.other = other;
    }


    public class NotifyContentOtherModel {

        /**
         * img : [?1]
         * name : [?2]
         * sex : [?3]
         * type : G
         */


        /*
        name = 游客姓名
        img = 游客头像
        type = 类型  (回复 H、评论 P、关注 G、点赞 D)
        sex 性别 0男 非0女
         */
        private String img;
        private String name;
        private String sex;
        private String type;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getType() {
            switch (type){
                case "H":
                    return "回复了您!";
                case "P":
                    return "评论了您!";
                case "G":
                    return "关注了您!";
                case "D":
                    return "赞了您!";
                default:
                    return "";
            }
        }

        public void setType(String type) {
            this.type = type;
        }
    }


}
