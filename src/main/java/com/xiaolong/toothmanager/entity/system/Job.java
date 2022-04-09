package com.xiaolong.toothmanager.entity.system;

import com.xiaolong.toothmanager.service.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description: 每一个角色的工作
 * @Author xiaolong
 * @Date 2022/4/9 18:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Job extends BaseDTO {

//    @NotNull(groups = Update.class)
    @ApiModelProperty(value = "ID", hidden = true)
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "岗位名称")
    private String name;

    @NotNull
    @ApiModelProperty(value = "岗位排序")
    private Long jobSort;

    @NotNull
    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;
}
