package com.hanthink.gps.util.exception;

/**
 * データベースアクセスなエラーが発生した場合、表示用の例外クラス
 * @author huyou
 *
 */
public class DBSqlException extends RuntimeException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     * @param msg メッセージストリング
     */
	public DBSqlException(String msg) {
		super(msg);
	}
}
