package com.hanthink.gps.util.logger;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.Vector;

/**
 * 例外のログ出力情報用のクラス
 *
 */
public class LogThrowableInfo implements Serializable {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 异常信息
     */
    private transient Throwable throwable;

    /**
     * ストリング配列型式の异常信息
     */
    private String[] rep = null;

    /**
     * ベクトル出力用クラス
    　*/
    private VectorWriter vw = new VectorWriter();

    /**
     * コンストラクタ
     */
    public LogThrowableInfo() {
        try {
            throw new Exception();
        } catch (Exception e) {
            this.throwable = e;
        }
    }

    /**
     * 取得异常信息
     * @return 异常信息
     */
    public Throwable getThrowable() {
        return throwable;
    }

    /**
     * ストリング配列型式の例外情報を取得する。
     * @return ストリング配列型式の例外情報
     */
    public String[] getThrowableStrRep() {
        if (rep != null) {
            return (String[]) rep.clone();
        } else {
            throwable.printStackTrace(vw);
            rep = vw.toStringArray();
            vw.clear();
            return rep;
        }
    }
}

/**
 * ベクトル出力用クラスです。
 *
 * @see java.io.PrintWriter
 * @since 1.0
 */
class VectorWriter extends PrintWriter {

    /**
     * 出力用ベクトル
     */
    private Vector<String> v;

    /**
     * コンストラクタ
     */
    VectorWriter() {
        super(new NullWriter());
        v = new Vector<String>();
    }

    /**
     * オブジェクトをベクトルに追加する。
     * @param o オブジェクト
     */
    public void println(Object o) {
        v.addElement(o.toString());
    }

    /**
     * charをベクトルに追加する。
     * @param s charの配列
     */
    public void println(char[] s) {
        v.addElement(new String(s));
    }

    /**
     * ストリングをベクトルに追加する。
     * @param s ストリング
     */
    public void println(String s) {
        v.addElement(s);
    }

    /**
     * ベクトルをストリングにチェンジする。
     * @return ストリング配列
     */
    public String[] toStringArray() {
        int len = v.size();
        String[] sa = new String[len];

        for (int i = 0; i < len; i++) {
            sa[i] = (String) v.elementAt(i);
        }

        return sa;
    }

    /**
     * ベクトルをクリアする。
     */
    public void clear() {
        v.setSize(0);
    }
}

/**
 * Nullの出力用クラスです。
 *
 * @see java.io.Writer
 * @since 1.0
 */
class NullWriter extends Writer {

    /**
     * クローズする。
     */
    public void close() {
    }

    /**
     * フラッシュする。
     */
    public void flush() {
    }

    /**
     * 出力する。
     * @param  cbuf char配列
     * @param  off  オフセット
     * @param  len  長さ
     */
    public void write(char[] cbuf, int off, int len) {
    }
}

