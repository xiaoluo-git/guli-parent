package com.guli.common;

public interface ResultCode {
    int OK = 20000; //成功
    int ERROR = 20001;//失败
    int TEACHERID_NOT_EXISTS=20002;//用户id不存在
    int NullPointer_ERROR = 20005;//空指针异常
    int SQL_ERROR = 20006;//sql错误
    int EXISTS_NODES = 20007;//存在子节点不可删除
    int COURSE_EXISTS = 20008;//课程已存在
    int COURSE_INSERT_ERR = 20009;//课程插入失败
    int COURSEDESC_INSERT_ERR = 20010;//课程描述插入失败
    int ID_NOT_EXISTS = 20011;//ID不存在

    int CHAPTER_NOT_EXISTS = 20012;//还没有章节数据
    int CHAPTER_EXISTS = 20013;//章节已存在！
    int EXISTS_VIDEO = 20014;//存在video不可删除
}
