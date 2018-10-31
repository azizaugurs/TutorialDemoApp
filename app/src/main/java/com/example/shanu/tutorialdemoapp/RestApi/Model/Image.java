package com.example.shanu.tutorialdemoapp.RestApi.Model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class Image {

        @SerializedName("data")
        @Expose
        private Boolean data;
        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("success")
        @Expose
        private Boolean success;

        public Boolean getData() {
            return data;
        }

        public void setData(Boolean data) {
            this.data = data;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }


}
