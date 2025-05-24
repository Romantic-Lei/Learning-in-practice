package com.share.order.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "用户类")
public class UserInfoVo {

	@Schema(description = "昵称")
	private String nickname;

	@Schema(description = "微信open id")
	private String wxOpenId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Schema(description = "最后一次登录时间")
	private Date lastLoginTime;
}
