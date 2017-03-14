package com.example.liuteng.livedemo.bean.serverbean;

import java.util.List;

/**
 * Created by liuteng on 2017/3/14.
 */

public class QAResponse {

    /**
     * ok : 1
     * page : {"count":0,"index":1,"size":10}
     * result : [{"answer":[{"aid":16752,"name":"微信/微博\r","qid":2857,"ratio":0,"type":"Radio"},{"aid":16753,"name":"电子邮件\r","qid":2857,"ratio":0,"type":"Radio"},{"aid":16754,"name":"仪器信息网首页\r","qid":2857,"ratio":0,"type":"Radio"},{"aid":16755,"name":"论坛\r","qid":2857,"ratio":0,"type":"Radio"},{"aid":16756,"name":"网络讲堂栏目 \r","qid":2857,"ratio":0,"type":"Radio"},{"aid":16757,"name":"新闻资讯\r","qid":2857,"ratio":0,"type":"Radio"},{"aid":16758,"name":"其他","qid":2857,"ratio":0,"type":"Radio"}],"content":"您从何种渠道获得此会议信息？","oid":2234,"order":1,"qid":2857},{"answer":[{"aid":16759,"name":"环境/环保\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16760,"name":"石油化工\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16761,"name":"日用化工\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16762,"name":"食品行业\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16763,"name":"能源与燃料\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16764,"name":"电子/半导体\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16765,"name":"物证鉴定\t \r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16766,"name":"材料检测与研究\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16767,"name":"地质/钢铁/有色金属\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16768,"name":"基因组学/代谢组学/蛋白质组学\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16769,"name":"医药（生物技术/生物制药、制药/通用、中药）\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16770,"name":"纺织/印染\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16771,"name":"大学/科研/政府检测机构\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16772,"name":"合同研究和合同生产机构（CRO）\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16773,"name":"企事业实验室\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16774,"name":"第三方检测机构\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16775,"name":"仪器生产/经销企业\r","qid":2858,"ratio":0,"type":"CheckBox"},{"aid":16776,"name":"医院/医疗中心/诊所","qid":2858,"ratio":0,"type":"CheckBox"}],"content":"您所属的行业及单位性质：(可多选)","oid":2234,"order":1,"qid":2858},{"answer":[{"aid":16777,"name":"质谱（LC-MS、GC-MS、ICP-MS、TOF-MS等）\r","qid":2859,"ratio":0,"type":"CheckBox"},{"aid":16778,"name":"光谱（AA、IR、UV、Raman等）\r","qid":2859,"ratio":0,"type":"CheckBox"},{"aid":16779,"name":"色谱（GC、 LC、IC等）\r","qid":2859,"ratio":0,"type":"CheckBox"},{"aid":16780,"name":"波谱（NMR等）\r","qid":2859,"ratio":0,"type":"CheckBox"},{"aid":16781,"name":"物性测试（试验机、热分析、粒度/颗粒、表界面物性等）\r","qid":2859,"ratio":0,"type":"CheckBox"},{"aid":16782,"name":"生命科学（分子生物学、细胞生物学、生物工程设备等）\r","qid":2859,"ratio":0,"type":"CheckBox"},{"aid":16783,"name":"显微镜（光学显微镜、电子显微镜等）\r","qid":2859,"ratio":0,"type":"CheckBox"},{"aid":16784,"name":"X射线仪器（XRF等）\r","qid":2859,"ratio":0,"type":"CheckBox"},{"aid":16785,"name":"其他","qid":2859,"ratio":0,"type":"CheckBox"}],"content":"您日常使用何种分析仪器？(可多选)","oid":2234,"order":1,"qid":2859},{"answer":[{"aid":16786,"name":"无\r","qid":2860,"ratio":0,"type":"Radio"},{"aid":16787,"name":"预算已到位（购买时间在一年内）\r","qid":2860,"ratio":0,"type":"Radio"},{"aid":16788,"name":"预算在申请中（购买时间在一年内）\r","qid":2860,"ratio":0,"type":"Radio"},{"aid":16789,"name":"预算在申请中（一年后才购买）","qid":2860,"ratio":0,"type":"Radio"}],"content":"是否有预算采购新的仪器及设备等？","oid":2234,"order":1,"qid":2860},{"answer":[{"aid":16790,"name":"","qid":2861,"ratio":0,"type":"Text"}],"content":"您计划在未来采购何种仪器或服务？（填写有惊喜）","oid":2234,"order":1,"qid":2861}]
     */

    private int ok;
    private PageBean page;
    private List<ResultBean> result;

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class PageBean {
        /**
         * count : 0
         * index : 1
         * size : 10
         */

        private int count;
        private int index;
        private int size;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public static class ResultBean {
        /**
         * answer : [{"aid":16752,"name":"微信/微博\r","qid":2857,"ratio":0,"type":"Radio"},{"aid":16753,"name":"电子邮件\r","qid":2857,"ratio":0,"type":"Radio"},{"aid":16754,"name":"仪器信息网首页\r","qid":2857,"ratio":0,"type":"Radio"},{"aid":16755,"name":"论坛\r","qid":2857,"ratio":0,"type":"Radio"},{"aid":16756,"name":"网络讲堂栏目 \r","qid":2857,"ratio":0,"type":"Radio"},{"aid":16757,"name":"新闻资讯\r","qid":2857,"ratio":0,"type":"Radio"},{"aid":16758,"name":"其他","qid":2857,"ratio":0,"type":"Radio"}]
         * content : 您从何种渠道获得此会议信息？
         * oid : 2234
         * order : 1
         * qid : 2857
         */

        private String content;
        private int oid;
        private int order;
        private int qid;
        private List<AnswerBean> answer;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getQid() {
            return qid;
        }

        public void setQid(int qid) {
            this.qid = qid;
        }

        public List<AnswerBean> getAnswer() {
            return answer;
        }

        public void setAnswer(List<AnswerBean> answer) {
            this.answer = answer;
        }

        public static class AnswerBean {
            /**
             * aid : 16752
             * name : 微信/微博
             * qid : 2857
             * ratio : 0
             * type : Radio
             */

            private int aid;
            private String name;
            private int qid;
            private int ratio;
            private String type;

            public int getAid() {
                return aid;
            }

            public void setAid(int aid) {
                this.aid = aid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getQid() {
                return qid;
            }

            public void setQid(int qid) {
                this.qid = qid;
            }

            public int getRatio() {
                return ratio;
            }

            public void setRatio(int ratio) {
                this.ratio = ratio;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
