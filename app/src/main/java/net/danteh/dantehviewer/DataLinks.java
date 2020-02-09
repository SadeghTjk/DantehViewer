
package net.danteh.dantehviewer;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataLinks {

    @SerializedName("records")
    @Expose
    private List<Links> records = null;

    public List<Links> getRecords() {
        return records;
    }

}