package com.minimarket.scanminimarket.Login;

public class User {

    /**
     * status : 200
     * message : Login Success
     * data : {"nama_user":"admin","username":"admin","level":"Admin"}
     */

    private int status;
    private String message;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * nama_user : admin
         * username : admin
         * level : Admin
         */

        private String nama_user;
        private String username;
        private String level;

        public String getNama_user() {
            return nama_user;
        }

        public void setNama_user(String nama_user) {
            this.nama_user = nama_user;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }
}
