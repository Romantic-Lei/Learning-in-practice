package com.share.common.core.exception.user;

import com.share.common.core.exception.base.BaseException;

/**
 * 用户信息异常类
 *
 * @author share
 */
public class UserException extends BaseException
{
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args)
    {
        super("user", code, args, null);
    }
}
