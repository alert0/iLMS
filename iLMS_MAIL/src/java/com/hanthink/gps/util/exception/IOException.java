package com.hanthink.gps.util.exception;

/**
 * 入力/出力なエラーが発生した場合、表示用の例外クラス *
 */
public class IOException extends SystemException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 8591304586328975119L;

    /**
     * コンストラクタ
     * @param msg メッセージストリング
     */
	public IOException(String msg) {
		super(msg);
	}
}
