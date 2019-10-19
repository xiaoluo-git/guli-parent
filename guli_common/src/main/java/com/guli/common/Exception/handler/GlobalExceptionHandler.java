package com.guli.common.Exception.handler;


import com.guli.common.Exception.TeacherException;
import com.guli.common.Exception.util.ExceptionUtil;
import com.guli.common.Result;
import com.guli.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e){
        e.printStackTrace();
        return Result.error().message("出错了,请联系管理员");
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public  Result nullPointerException(NullPointerException e){
        e.printStackTrace();
        return Result.error().code(ResultCode.NullPointer_ERROR).message("空指针异常");
    }



//    @ExceptionHandler(BadSqlGrammarException.class)
//    @ResponseBody
//    public Result sqlError(BadSqlGrammarException e){
//        e.printStackTrace();
//        return Result.error().code(ResultCode.SQL_ERROR).message("SQL语法错误");
//    }


    @ExceptionHandler(TeacherException.class)
    @ResponseBody
    public Result error(TeacherException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().code(e.getCode()).message(e.getMessage());
    }

}
