package sc.hqu.graduationdesign.homeworkmanager.model;

import lombok.Data;

/**
 * @author tzx
 * @date 2021-03-17 16:26
 */
public class CommonSyncSendingResult {

    public final boolean isOk;

    public CommonSyncSendingResult(boolean isOk){
        this.isOk = isOk;
    }

    public boolean isOk(){
        return isOk;
    }

}
