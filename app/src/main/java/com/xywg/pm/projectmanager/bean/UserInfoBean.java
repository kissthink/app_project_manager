package com.xywg.pm.projectmanager.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhangyinlei on 2018/7/9
 */
public class UserInfoBean implements Serializable {

    /**
     * orgins : [{"flag":1,"children":[{"flag":1,"children":[{"flag":1,"children":[{"flag":1,"children":[{"flag":0,"id":8,"label":"8888"}],"id":41,"label":"软件研发部"},{"flag":1,"children":[{"flag":0,"id":9,"label":"2"}],"id":42,"label":"软件测试部"}],"id":40,"label":"技术部"},{"flag":1,"children":null,"id":43,"label":"总经办"},{"flag":1,"children":null,"id":44,"label":"财务部"},{"flag":1,"children":null,"id":45,"label":"工程实施部"}],"id":7,"label":"江苏星云网格"},{"flag":1,"children":[{"flag":1,"children":[{"flag":1,"children":[{"flag":1,"children":null,"id":8,"label":"峰公司"},{"flag":1,"children":null,"id":9,"label":"测试2"}],"id":3,"label":"南通分公司项目部一"}],"id":2,"label":"南通分公司"}],"id":1,"label":"集团公司"},{"flag":1,"children":[{"flag":1,"children":null,"id":11,"label":"测试部门"},{"flag":1,"children":null,"id":12,"label":"测试A部门"}],"id":10,"label":"A公司"},{"flag":1,"children":[{"flag":1,"children":[{"flag":1,"children":[{"flag":1,"children":null,"id":38,"label":"南通录入一"}],"id":34,"label":"南通项目部一"},{"flag":1,"children":[{"flag":1,"children":null,"id":39,"label":"南通录入二"}],"id":35,"label":"南通项目部二"}],"id":31,"label":"南通分公司"},{"flag":1,"children":[{"flag":1,"children":null,"id":36,"label":"北京项目部一"}],"id":32,"label":"北京分公司"},{"flag":1,"children":[{"flag":1,"children":null,"id":37,"label":"上海项目部一"}],"id":33,"label":"上海分公司"}],"id":30,"label":"江苏南通三建集团有限公司"}],"id":-1,"label":"运营商"}]
     * sessionId : d607e6b7-db5b-49d8-8e38-c5fbc9c8d81a
     * user : {"orgName":"运营商","suNickName":"运营商超管","id":-1}
     * operation : [{"buttons":"","children":[{"buttons":"update,add,delete,","soName":"质量管理","soUrl":"http://192.168.1.64:8091/#/qulity/index"},{"soName":"安全管理","soUrl":"http://192.168.1.64:8091/#/security/index"},{"soName":"RFID巡检","soUrl":"http://192.168.1.64:8091/#/inspection/index"},{"soName":"55","soUrl":null}],"soName":"江苏星云网格有限公司\"","soUrl":"/h5jump"},{"buttons":"","children":[{"soName":"测试质量","soUrl":"http://192.168.1.64:8091/#/qulity/index"}],"soName":"测试公司2","soUrl":null}]
     */

    private String sessionId;
    private UserBean user;
    private List<OrginsBean> orgins;
    private List<OperationBean> operation;
    private List<UrlsBean> urls;

    public List<UrlsBean> getUrls() {
        return urls;
    }

    public void setUrls(List<UrlsBean> urls) {
        this.urls = urls;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<OrginsBean> getOrgins() {
        return orgins;
    }

    public void setOrgins(List<OrginsBean> orgins) {
        this.orgins = orgins;
    }

    public List<OperationBean> getOperation() {
        return operation;
    }

    public void setOperation(List<OperationBean> operation) {
        this.operation = operation;
    }

    public static class UrlsBean implements Serializable {
        private int flag;
        private String url;

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class UserBean implements Serializable {
        /**
         * orgName : 运营商
         * suNickName : 运营商超管
         * id : -1
         */

        private String orgName;
        private int orgId;
        private String suNickName;
        private int id;

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public String getSuNickName() {
            return suNickName;
        }

        public void setSuNickName(String suNickName) {
            this.suNickName = suNickName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class OrginsBean implements Serializable {
        /**
         * flag : 1
         * children : [{"flag":1,"children":[{"flag":1,"children":[{"flag":1,"children":[{"flag":0,"id":8,"label":"8888"}],"id":41,"label":"软件研发部"},{"flag":1,"children":[{"flag":0,"id":9,"label":"2"}],"id":42,"label":"软件测试部"}],"id":40,"label":"技术部"},{"flag":1,"children":null,"id":43,"label":"总经办"},{"flag":1,"children":null,"id":44,"label":"财务部"},{"flag":1,"children":null,"id":45,"label":"工程实施部"}],"id":7,"label":"江苏星云网格"},{"flag":1,"children":[{"flag":1,"children":[{"flag":1,"children":[{"flag":1,"children":null,"id":8,"label":"峰公司"},{"flag":1,"children":null,"id":9,"label":"测试2"}],"id":3,"label":"南通分公司项目部一"}],"id":2,"label":"南通分公司"}],"id":1,"label":"集团公司"},{"flag":1,"children":[{"flag":1,"children":null,"id":11,"label":"测试部门"},{"flag":1,"children":null,"id":12,"label":"测试A部门"}],"id":10,"label":"A公司"},{"flag":1,"children":[{"flag":1,"children":[{"flag":1,"children":[{"flag":1,"children":null,"id":38,"label":"南通录入一"}],"id":34,"label":"南通项目部一"},{"flag":1,"children":[{"flag":1,"children":null,"id":39,"label":"南通录入二"}],"id":35,"label":"南通项目部二"}],"id":31,"label":"南通分公司"},{"flag":1,"children":[{"flag":1,"children":null,"id":36,"label":"北京项目部一"}],"id":32,"label":"北京分公司"},{"flag":1,"children":[{"flag":1,"children":null,"id":37,"label":"上海项目部一"}],"id":33,"label":"上海分公司"}],"id":30,"label":"江苏南通三建集团有限公司"}]
         * id : -1
         * label : 运营商
         */

        private int flag;
        private int pid;
        private int id;
        private String label;
        private List<ChildrenBean> children;

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }
    }

    public static class OperationBean implements Serializable {

        private int id;
        private String soName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSoName() {
            return soName;
        }

        public void setSoName(String soName) {
            this.soName = soName;
        }
    }
}
