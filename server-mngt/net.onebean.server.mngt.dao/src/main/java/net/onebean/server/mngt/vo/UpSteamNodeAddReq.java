package net.onebean.server.mngt.vo;

import org.hibernate.validator.constraints.NotEmpty;

/**
* @author 0neBean
* @description upsteam node info model
* @date 2019-03-01 15:25:32
*/
public class UpSteamNodeAddReq {

        /**
        * 节点名称
        */
        @NotEmpty(message = "nodeName can not be empty")
        private String nodeName;
        public String getNodeName(){
            return this.nodeName;
        }
        public void setNodeName(String nodeName){
            this.nodeName = nodeName;
        }


        /**
        * 物理地址
        */
        @NotEmpty(message = "physicalPath can not be empty")
        private String physicalPath;
        public String getPhysicalPath(){
            return this.physicalPath;
        }
        public void setPhysicalPath(String physicalPath){
            this.physicalPath = physicalPath;
        }


        private Integer operatorId;
        public Integer getOperatorId(){
            return this.operatorId;
        }
        public void setOperatorId(Integer operatorId){
            this.operatorId = operatorId;
        }



        private String operatorName;
        public String getOperatorName(){
            return this.operatorName;
        }
        public void setOperatorName(String operatorName){
            this.operatorName = operatorName;
        }


}