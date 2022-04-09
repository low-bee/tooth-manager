package com.xiaolong.toothmanager.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 角色和角色名称
 * @Author xiaolong
 * @Date 2022/4/9 18:32
 */
@Data
@NoArgsConstructor
public class JobSmallDto implements Serializable {

    private Long id;

    private String name;
}
