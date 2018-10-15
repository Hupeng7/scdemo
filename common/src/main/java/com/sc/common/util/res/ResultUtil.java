package com.sc.common.util.res;

/**
 * @author hp
 * @version 1.0
 * @description: 统一的结果返回工具
 * @date: 11:07 2018/10/15 0015
 */
public class ResultUtil {
    /**
     * 返回成功
     *
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ResultVO success(T result) {
        ResultVO resultVO = new ResultVO();
        ResultEnum successEnum = ResultEnum.SUCCESS;
        resultVO.setCode(successEnum.getCode());
        resultVO.setMsg(successEnum.getMsg());
        resultVO.setResult(result);
        return resultVO;
    }

    /**
     * 无返回值的结果集
     *
     * @return
     */
    public static ResultVO success() {
        return success(null);
    }

    /**
     * 返回错误
     *
     * @param result
     * @param resultEnum
     * @param <T>
     * @return
     */
    public static <T> ResultVO error(T result, ResultEnum resultEnum) {
        ResultVO resultVO = new ResultVO();
        resultVO.setResult(result);
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMsg());
        return resultVO;
    }

    /**
     * 返回错误  无返回值
     *
     * @param resultEnum
     * @return
     */
    public static ResultVO error(ResultEnum resultEnum) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setMsg(resultEnum.getMsg());
        return resultVO;
    }

    /**
     * 返回错误 无返回值
     *
     * @param code
     * @param msg
     * @return
     */
    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }


}
