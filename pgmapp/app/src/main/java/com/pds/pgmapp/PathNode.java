package com.pds.pgmapp;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class PathNode {

        @SerializedName("path")
        @Expose
        private List<String> path = null;
        @SerializedName("pathId")
        @Expose
        private String pathId;

        public List<String> getPath() {
            return path;
        }

        public void setPath(List<String> path) {
            this.path = path;
        }

        public String getPathId() {
            return pathId;
        }

        public void setPathId(String pathId) {
            this.pathId = pathId;
        }

    }
