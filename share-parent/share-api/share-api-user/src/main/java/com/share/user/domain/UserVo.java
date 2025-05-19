package com.share.user.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户类")
public class UserVo {

	@Schema(description = "昵称")
	private String nickname;

	@Schema(description = "头像")
	private String avatar;

	@Schema(description = "微信open id")
	private String wxOpenId;

	@Schema(description = "押金状态（0：未验证 1：免押金 2：已交押金）")
	private String depositStatus;
}