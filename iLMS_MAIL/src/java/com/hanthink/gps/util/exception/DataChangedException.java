package com.hanthink.gps.util.exception;

/**
 * 排他的なエラーが発生した場合、表示用の例外クラス
 *
 */
public class DataChangedException extends RuntimeException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/** プロパティ名 */
	private String name = null;
	
    /**
     * コンストラクタ
     * @param msg メッセージストリング
     */
	public DataChangedException(String msg) {
		super(msg);
	}
	
	/**
	 * コンストラクタ
	 * @param msg メッセージストリング
	 * @param name プロパティ名
	 */
	public DataChangedException(String msg, String name) {
		super(msg);
		this.name = name;
	}

	/**
	 * プロパティ名を取得する。
	 *
	 * @return プロパティ名
	 */
	public String getName() {
		return name;
	}

	/**
	 * プロパティ名を設定する。
	 *
	 * @param argName プロパティ名
	 */
	public void setName(String argName) {
		name = argName;
	}

}
