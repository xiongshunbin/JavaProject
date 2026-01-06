package com.mushan.weblog.common.exception;

import com.mushan.weblog.common.enums.ResponseCodeEnum;
import com.mushan.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: mushan
 * @url: www.mushan.com
 * @description: 全局异常处理
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 捕获自定义业务异常
     * @return
     */
    @ExceptionHandler({ BizException.class })
    @ResponseBody
    public Response<Object> handleBizException(HttpServletRequest request, BizException e) {
        log.warn("{} request fail, errorCode: {}, errorMessage: {}", request.getRequestURI(), e.getErrorCode(), e.getErrorMessage());
        return Response.fail(e);
    }

    /**
     * 其他类型异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public Response<Object> handleOtherException(HttpServletRequest request, Exception e) {
        log.error("{} request error, ", request.getRequestURI(), e);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }
}
