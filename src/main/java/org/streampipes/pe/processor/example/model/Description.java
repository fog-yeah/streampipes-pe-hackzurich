
package org.streampipes.pe.processor.example.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import org.streampipes.pe.processor.example.model.Caption;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Description {

    @Expose
    private List<Caption> captions;
    @Expose
    private List<String> tags;

    public List<Caption> getCaptions() {
        return captions;
    }

    public void setCaptions(List<Caption> captions) {
        this.captions = captions;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}
