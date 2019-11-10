package com.hanthink.gps.util.exception;

import com.hanthink.gps.util.StringUtil;

/**
 * システムなエラーが発生した場合、表示用の例外クラス
 *
 */
public class SystemException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;


    /** エラーコード */
    private String errCode = "";

    /** エラーメッセージ */
    private String errMsg = "";

    /**
     * コンストラクタ
     * @param msg メッセージストリング
     */
	public SystemException(String msg) {
		super(msg);
	}
	
    /**
     * コンストラクタ
     * @param newErrCode メッセージID
     * @param params パラメータ
     * @param thr 発生した異常
     */
    public SystemException(String newErrCode, Object[] params, Throwable thr) {
        super(StringUtil.formatMsg(newErrCode, params), thr);
        this.errCode = newErrCode;
        this.errMsg = StringUtil.formatMsg(newErrCode, params);
    }

    /**
     * コンストラクタ
     * @param newErrCode メッセージID
     * @param thr 発生した異常
     */
    public SystemException(String newErrCode, Throwable thr) {
        super(StringUtil.formatMsg(newErrCode), thr);
        this.errCode = newErrCode;
        this.errMsg = StringUtil.formatMsg(newErrCode);
    }

    /**
     * エラーコードを取得する。
     * @return エラーコード
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * エラーメッセージを取得する。
     * @return エラーメッセージ
     */
    public String getErrMsg() {
        return errMsg;
    }

    /**
     * エラーコードを設定する。
     * @param string エラーコード
     */
    public void setErrCode(String string) {
        errCode = string;
    }

    /**
     * エラーメッセージを設定する。
     * @param string　エラーメッセージ
     */
    public void setErrMsg(String string) {
        errMsg = string;
    }
}
