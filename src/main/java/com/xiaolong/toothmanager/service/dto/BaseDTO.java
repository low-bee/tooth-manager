package com.xiaolong.toothmanager.service.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * @Description: 每张表都需要的行
 * @Author xiaolong
 * @Date 2022/3/28 23:04
 */
@Getter
@Setter
public class BaseDTO  implements Serializable {

    private String createdBy;

    private String updatedBy;

    private Timestamp createdTime;

    private Timestamp updatedTime;

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        Field[] fields = this.getClass().getDeclaredFields();
        try {
            for (Field f : fields) {
                f.setAccessible(true);
                builder.append(f.getName(), f.get(this)).append("\n");
            }
        } catch (Exception e) {
            builder.append("toString builder encounter an error");
        }
        return builder.toString();
    }
}
