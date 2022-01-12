package com.xiaolong.toothmanager.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaolong
 * @since 2022-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="SysRoleMenu对象", description="")
public class SysRoleMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long menuId;


}
