package mongo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MovieFansBehaviorMqDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private int _id;
    private int userId;
    private int typeId;
    private int subjectId;
    private int status;
    private String content;
    private Date addTime;
}
