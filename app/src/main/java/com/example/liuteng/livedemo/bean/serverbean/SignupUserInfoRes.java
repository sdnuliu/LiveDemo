package com.example.liuteng.livedemo.bean.serverbean;

/**
 * Created by liuteng on 2017/3/15.
 */

public class SignupUserInfoRes {

    /**
     * ok : 1
     * page : {"count":0,"index":1,"size":10}
     * result : {"address":"后宫您好您好","company":"高科技产品","email":"346634548@qq.com","ispass":null,"mid":"刘腾","mobile":"18888888888","postcode":null,"tel":"18888888888","username":"m3194246"}
     */

    private int ok;
    private PageBeanRes page;
    private ResultBean result;

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    public PageBeanRes getPage() {
        return page;
    }

    public void setPage(PageBeanRes page) {
        this.page = page;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * address : 后宫您好您好
         * company : 高科技产品
         * email : 346634548@qq.com
         * ispass : null
         * mid : 刘腾
         * mobile : 18888888888
         * postcode : null
         * tel : 18888888888
         * username : m3194246
         */

        private String address;
        private String company;
        private String email;
        private int ispass;
        private String mid;
        private String mobile;
        private String postcode;
        private String tel;
        private String username;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getIspass() {
            return ispass;
        }

        public void setIspass(int ispass) {
            this.ispass = ispass;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
