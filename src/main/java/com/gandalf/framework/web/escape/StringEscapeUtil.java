package com.gandalf.framework.web.escape;

import java.io.IOException;
import java.util.BitSet;

import com.gandalf.framework.util.StringUtil;

/**
 * 类StringEscapeUtil.java的实现描述：String escape
 * 
 * @author gandalf 2014-2-21 下午3:03:27
 */
public class StringEscapeUtil {

    // ==========================================================================
    // Java和JavaScript。
    // ==========================================================================

    /**
     * 按Java的规则对字符串进行转义。
     * <p>
     * 将双引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn't say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @return 转义后的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String escapeJava(String str) {
        return escapeJavaStyleString(str, false, false);
    }

    /**
     * 按Java的规则对字符串进行转义。
     * <p>
     * 将双引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn't say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @param strict 是否以严格的方式编码字符串
     * @return 转义后的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String escapeJava(String str, boolean strict) {
        return escapeJavaStyleString(str, false, strict);
    }

    /**
     * 按Java的规则对字符串进行转义。
     * <p>
     * 将双引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn't say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void escapeJava(String str, Appendable out) throws IOException {
        escapeJavaStyleString(str, false, out, false);
    }

    /**
     * 按Java的规则对字符串进行转义。
     * <p>
     * 将双引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn't say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @param out 输出流
     * @param strict 是否以严格的方式编码字符串
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void escapeJava(String str, Appendable out, boolean strict) throws IOException {
        escapeJavaStyleString(str, false, out, strict);
    }

    /**
     * 按JavaScript的规则对字符串进行转义。
     * <p>
     * 将双引号、单引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn\'t say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @return 转义后的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String escapeJavaScript(String str) {
        return escapeJavaStyleString(str, true, false);
    }

    /**
     * 按JavaScript的规则对字符串进行转义。
     * <p>
     * 将双引号、单引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn\'t say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @param strict 是否以严格的方式编码字符串
     * @return 转义后的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String escapeJavaScript(String str, boolean strict) {
        return escapeJavaStyleString(str, true, strict);
    }

    /**
     * 按JavaScript的规则对字符串进行转义。
     * <p>
     * 将双引号、单引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn\'t say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void escapeJavaScript(String str, Appendable out) throws IOException {
        escapeJavaStyleString(str, true, out, false);
    }

    /**
     * 按JavaScript的规则对字符串进行转义。
     * <p>
     * 将双引号、单引号和控制字符转换成<code>'\\'</code>开头的形式，例如tab制表符将被转换成<code>\t</code>。
     * </p>
     * <p>
     * Java和JavaScript字符串的唯一差别是，JavaScript必须对单引号进行转义，而Java不需要。
     * </p>
     * <p>
     * 例如：字符串：<code>He didn't say, "Stop!"</code>被转换成 <code>He didn\'t say, \"Stop!\"</code>
     * </p>
     * 
     * @param str 要转义的字符串
     * @param out 输出流
     * @param strict 是否以严格的方式编码字符串
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void escapeJavaScript(String str, Appendable out, boolean strict) throws IOException {
        escapeJavaStyleString(str, true, out, strict);
    }

    /**
     * 按Java或JavaScript的规则对字符串进行转义。
     * 
     * @param str 要转义的字符串
     * @param javascript 是否对单引号和slash进行转义
     * @param strict 是否以严格的方式编码字符串
     * @return 转义后的字符串
     */
    private static String escapeJavaStyleString(String str, boolean javascript, boolean strict) {
        if (str == null) {
            return null;
        }

        try {
            StringBuilder out = new StringBuilder(str.length() * 2);

            if (escapeJavaStyleString(str, javascript, out, strict)) {
                return out.toString();
            }

            return str;
        } catch (IOException e) {
            return str; // StringBuilder不可能发生这个异常
        }
    }

    /**
     * 按Java或JavaScript的规则对字符串进行转义。
     * 
     * @param str 要转义的字符串
     * @param javascript 是否对单引号和slash进行转义
     * @param out 输出流
     * @param strict 是否以严格的方式编码字符串
     * @return 如果字符串没有变化，则返回<code>false</code>
     */
    private static boolean escapeJavaStyleString(String str, boolean javascript, Appendable out, boolean strict)
                                                                                                                throws IOException {
        boolean needToChange = false;

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        int length = str.length();

        for (int i = 0; i < length; i++) {
            char ch = str.charAt(i);

            if (ch < 32) {
                switch (ch) {
                    case '\b':
                        out.append('\\');
                        out.append('b');
                        break;

                    case '\n':
                        out.append('\\');
                        out.append('n');
                        break;

                    case '\t':
                        out.append('\\');
                        out.append('t');
                        break;

                    case '\f':
                        out.append('\\');
                        out.append('f');
                        break;

                    case '\r':
                        out.append('\\');
                        out.append('r');
                        break;

                    default:

                        if (ch > 0xf) {
                            out.append("\\u00" + Integer.toHexString(ch).toUpperCase());
                        } else {
                            out.append("\\u000" + Integer.toHexString(ch).toUpperCase());
                        }

                        break;
                }

                // 设置改变标志
                needToChange = true;
            } else if (strict && ch > 0xff) {
                if (ch > 0xfff) {
                    out.append("\\u").append(Integer.toHexString(ch).toUpperCase());
                } else {
                    out.append("\\u0").append(Integer.toHexString(ch).toUpperCase());
                }

                // 设置改变标志
                needToChange = true;
            } else {
                switch (ch) {
                    case '\'':
                    case '/': // 注意：对于javascript，对/进行escape是重要的安全措施。

                        if (javascript) {
                            out.append('\\');

                            // 设置改变标志
                            needToChange = true;
                        }

                        out.append(ch);

                        break;

                    case '"':
                        out.append('\\');
                        out.append('"');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case '\\':
                        out.append('\\');
                        out.append('\\');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    default:
                        out.append(ch);
                        break;
                }
            }
        }

        return needToChange;
    }

    /**
     * 按Java的规则对字符串进行反向转义。
     * <p>
     * <code>'\\'</code>开头的形式转换成相应的字符，例如<code>\t</code>将被转换成tab制表符
     * </p>
     * <p>
     * 如果转义符不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 不包含转义字符的字符串
     * @return 恢复成未转义的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String unescapeJava(String str) {
        return unescapeJavaStyleString(str);
    }

    /**
     * 按Java的规则对字符串进行反向转义。
     * <p>
     * <code>'\\'</code>开头的形式转换成相应的字符，例如<code>\t</code>将被转换成tab制表符
     * </p>
     * <p>
     * 如果转义符不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 包含转义字符的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void unescapeJava(String str, Appendable out) throws IOException {
        unescapeJavaStyleString(str, out);
    }

    /**
     * 按JavaScript的规则对字符串进行反向转义。
     * <p>
     * <code>'\\'</code>开头的形式转换成相应的字符，例如<code>\t</code>将被转换成tab制表符
     * </p>
     * <p>
     * 如果转义符不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 包含转义字符的字符串
     * @return 恢复成未转义的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String unescapeJavaScript(String str) {
        return unescapeJavaStyleString(str);
    }

    /**
     * 按Java的规则对字符串进行反向转义。
     * <p>
     * <code>'\\'</code>开头的形式转换成相应的字符，例如<code>\t</code>将被转换成tab制表符
     * </p>
     * <p>
     * 如果转义符不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 包含转义字符的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void unescapeJavaScript(String str, Appendable out) throws IOException {
        unescapeJavaStyleString(str, out);
    }

    /**
     * 按Java的规则对字符串进行反向转义。
     * <p>
     * <code>'\\'</code>开头的形式转换成相应的字符，例如<code>\t</code>将被转换成tab制表符
     * </p>
     * <p>
     * 如果转义符不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 包含转义字符的字符串
     * @return 不包含转义字符的字符串
     */
    private static String unescapeJavaStyleString(String str) {
        if (str == null) {
            return null;
        }

        try {
            StringBuilder out = new StringBuilder(str.length());

            if (unescapeJavaStyleString(str, out)) {
                return out.toString();
            }

            return str;
        } catch (IOException e) {
            return str; // StringBuilder不可能发生这个异常
        }
    }

    /**
     * 按Java的规则对字符串进行反向转义。
     * <p>
     * <code>'\\'</code>开头的形式转换成相应的字符，例如<code>\t</code>将被转换成tab制表符
     * </p>
     * <p>
     * 如果转义符不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 包含转义字符的字符串
     * @param out 输出流
     * @return 如果字符串没有变化，则返回<code>false</code>
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    private static boolean unescapeJavaStyleString(String str, Appendable out) throws IOException {
        boolean needToChange = false;

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        int length = str.length();
        StringBuilder unicode = new StringBuilder(4);
        boolean hadSlash = false;
        boolean inUnicode = false;

        for (int i = 0; i < length; i++) {
            char ch = str.charAt(i);

            if (inUnicode) {
                unicode.append(ch);

                if (unicode.length() == 4) {
                    String unicodeStr = unicode.toString();

                    try {
                        int value = Integer.parseInt(unicodeStr, 16);

                        out.append((char) value);
                        unicode.setLength(0);
                        inUnicode = false;
                        hadSlash = false;

                        // 设置改变标志
                        needToChange = true;
                    } catch (NumberFormatException e) {
                        out.append("\\u" + unicodeStr);
                    }
                }

                continue;
            }

            if (hadSlash) {
                hadSlash = false;

                switch (ch) {
                    case '\\':
                        out.append('\\');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case '\'':
                        out.append('\'');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case '\"':
                        out.append('"');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case 'r':
                        out.append('\r');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case 'f':
                        out.append('\f');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case 't':
                        out.append('\t');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case 'n':
                        out.append('\n');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case 'b':
                        out.append('\b');

                        // 设置改变标志
                        needToChange = true;
                        break;

                    case 'u': {
                        inUnicode = true;
                        break;
                    }

                    default:
                        out.append(ch);
                        break;
                }

                continue;
            } else if (ch == '\\') {
                hadSlash = true;
                continue;
            }

            out.append(ch);
        }

        if (hadSlash) {
            out.append('\\');
        }

        return needToChange;
    }

    // ==========================================================================
    // HTML和XML。
    // ==========================================================================

    /**
     * 根据HTML的规则，将字符串中的部分字符转换成实体编码。
     * <p>
     * 例如：<code>"bread" & "butter"</code>将被转换成 <tt>&amp;quot;bread&amp;quot; &amp;amp;
     * &amp;quot;butter&amp;quot;</tt>.
     * </p>
     * <p>
     * 支持所有HTML 4.0 entities。
     * </p>
     * 
     * @param str 要转义的字符串
     * @return 用实体编码转义的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     * @see <a href="http://hotwired.lycos.com/webmonkey/reference/special_characters/">ISO Entities</a>
     * @see <a href="http://www.w3.org/TR/REC-html32#latin1">HTML 3.2 Character Entities for ISO Latin-1</a>
     * @see <a href="http://www.w3.org/TR/REC-html40/sgml/entities.html">HTML 4.0 Character entity references</a>
     * @see <a href="http://www.w3.org/TR/html401/charset.html#h-5.3">HTML 4.01 Character References</a>
     * @see <a href="http://www.w3.org/TR/html401/charset.html#code-position">HTML 4.01 Code positions</a>
     */
    public static String escapeHtml(String str) {
        return escapeEntities(Entities.HTML40_MODIFIED, str);
    }

    /**
     * 根据HTML的规则，将字符串中的部分字符转换成实体编码。
     * <p>
     * 例如：<code>"bread" & "butter"</code>将被转换成 <tt>&amp;quot;bread&amp;quot; &amp;amp;
     * &amp;quot;butter&amp;quot;</tt>.
     * </p>
     * <p>
     * 支持所有HTML 4.0 entities。
     * </p>
     * 
     * @param str 要转义的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     * @see <a href="http://hotwired.lycos.com/webmonkey/reference/special_characters/">ISO Entities</a>
     * @see <a href="http://www.w3.org/TR/REC-html32#latin1">HTML 3.2 Character Entities for ISO Latin-1</a>
     * @see <a href="http://www.w3.org/TR/REC-html40/sgml/entities.html">HTML 4.0 Character entity references</a>
     * @see <a href="http://www.w3.org/TR/html401/charset.html#h-5.3">HTML 4.01 Character References</a>
     * @see <a href="http://www.w3.org/TR/html401/charset.html#code-position">HTML 4.01 Code positions</a>
     */
    public static void escapeHtml(String str, Appendable out) throws IOException {
        escapeEntities(Entities.HTML40_MODIFIED, str, out);
    }

    /**
     * 根据XML的规则，将字符串中的部分字符转换成实体编码。
     * <p>
     * 例如：<code>"bread" & "butter"</code>将被转换成 <tt>&amp;quot;bread&amp;quot; &amp;amp;
     * &amp;quot;butter&amp;quot;</tt>.
     * </p>
     * <p>
     * 只转换4种基本的XML实体：<code>gt</code>、<code>lt</code>、<code>quot</code>和 <code>amp</code>。 不支持DTD或外部实体。
     * </p>
     * 
     * @param str 要转义的字符串
     * @return 用实体编码转义的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     */
    public static String escapeXml(String str) {
        return escapeEntities(Entities.XML, str);
    }

    /**
     * 根据XML的规则，将字符串中的部分字符转换成实体编码。
     * <p>
     * 例如：<code>"bread" & "butter"</code>将被转换成 <tt>&amp;quot;bread&amp;quot; &amp;amp;
     * &amp;quot;butter&amp;quot;</tt>.
     * </p>
     * <p>
     * 只转换4种基本的XML实体：<code>gt</code>、<code>lt</code>、<code>quot</code>和 <code>amp</code>。 不支持DTD或外部实体。
     * </p>
     * 
     * @param str 要转义的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void escapeXml(String str, Appendable out) throws IOException {
        escapeEntities(Entities.XML, str, out);
    }

    /**
     * 根据指定的规则，将字符串中的部分字符转换成实体编码。
     * 
     * @param entities 实体集合
     * @param str 要转义的字符串
     * @return 用实体编码转义的字符串，如果原字串为<code>null</code>，则返回<code>null</code>
     */
    public static String escapeEntities(Entities entities, String str) {
        if (str == null) {
            return null;
        }

        try {
            StringBuilder out = new StringBuilder(str.length());

            if (escapeEntitiesInternal(entities, str, out)) {
                return out.toString();
            }

            return str;
        } catch (IOException e) {
            return str; // StringBuilder不可能发生这个异常
        }
    }

    /**
     * 根据指定的规则，将字符串中的部分字符转换成实体编码。
     * 
     * @param entities 实体集合
     * @param str 要转义的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void escapeEntities(Entities entities, String str, Appendable out) throws IOException {
        escapeEntitiesInternal(entities, str, out);
    }

    /**
     * 按HTML的规则对字符串进行反向转义，支持HTML 4.0中的所有实体，以及unicode实体如<code>&amp;#12345;</code> 。
     * <p>
     * 例如："&amp;lt;Fran&amp;ccedil;ais&amp;gt;"将被转换成"&lt;Fran&ccedil;ais&gt;"
     * </p>
     * <p>
     * 如果实体不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 不包含转义字符的字符串
     * @return 恢复成未转义的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String unescapeHtml(String str) {
        return unescapeEntities(Entities.HTML40, str);
    }

    /**
     * 按HTML的规则对字符串进行反向转义，支持HTML 4.0中的所有实体，以及unicode实体如<code>&amp;#12345;</code> 。
     * <p>
     * 例如："&amp;lt;Fran&amp;ccedil;ais&amp;gt;"将被转换成"&lt;Fran&ccedil;ais&gt;"
     * </p>
     * <p>
     * 如果实体不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 包含转义字符的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void unescapeHtml(String str, Appendable out) throws IOException {
        unescapeEntities(Entities.HTML40, str, out);
    }

    /**
     * 按XML的规则对字符串进行反向转义，支持unicode实体如<code>&amp;#12345;</code>。
     * <p>
     * 例如："&amp;lt;Fran&amp;ccedil;ais&amp;gt;"将被转换成"&lt;Fran&ccedil;ais&gt;"
     * </p>
     * <p>
     * 如果实体不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 不包含转义字符的字符串
     * @return 恢复成未转义的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String unescapeXml(String str) {
        return unescapeEntities(Entities.XML, str);
    }

    /**
     * 按XML的规则对字符串进行反向转义，支持unicode实体如<code>&amp;#12345;</code>。
     * <p>
     * 例如："&amp;lt;Fran&amp;ccedil;ais&amp;gt;"将被转换成"&lt;Fran&ccedil;ais&gt;"
     * </p>
     * <p>
     * 如果实体不能被识别，它将被保留不变。
     * </p>
     * 
     * @param str 不包含转义字符的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void unescapeXml(String str, Appendable out) throws IOException {
        unescapeEntities(Entities.XML, str, out);
    }

    /**
     * 按指定的规则对字符串进行反向转义。
     * 
     * @param entities 实体集合
     * @param str 不包含转义字符的字符串
     * @return 恢复成未转义的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     */
    public static String unescapeEntities(Entities entities, String str) {
        if (str == null) {
            return null;
        }

        try {
            StringBuilder out = new StringBuilder(str.length());

            if (unescapeEntitiesInternal(entities, str, out)) {
                return out.toString();
            }

            return str;
        } catch (IOException e) {
            return str; // StringBuilder不可能发生这个异常
        }
    }

    /**
     * 按指定的规则对字符串进行反向转义。
     * <p>
     * 如果实体不能被识别，它将被保留不变。
     * </p>
     * 
     * @param entities 实体集合
     * @param str 不包含转义字符的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    public static void unescapeEntities(Entities entities, String str, Appendable out) throws IOException {
        unescapeEntitiesInternal(entities, str, out);
    }

    /**
     * 将字符串中的部分字符转换成实体编码。
     * 
     * @param entities 实体集合
     * @param str 要转义的字符串
     * @param out 字符输出流，不能为<code>null</code>
     * @return 如果字符串没有变化，则返回<code>false</code>
     * @throws IllegalArgumentException 如果<code>entities</code>或输出流为 <code>null</code>
     * @throws IOException 如果输出失败
     */
    private static boolean escapeEntitiesInternal(Entities entities, String str, Appendable out) throws IOException {
        boolean needToChange = false;

        if (entities == null) {
            throw new IllegalArgumentException("The Entities must not be null");
        }

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);
            String entityName = entities.getEntityName(ch);

            if (entityName == null) {
                out.append(ch);
            } else {
                out.append('&');
                out.append(entityName);
                out.append(';');

                // 设置改变标志
                needToChange = true;
            }
        }

        return needToChange;
    }

    /**
     * 将字符串中的已定义实体和unicode实体如<code>&amp;#12345;</code>转换成相应的unicode字符。
     * <p>
     * 未定义的实体将保留不变。
     * </p>
     * 
     * @param entities 实体集合，如果为<code>null</code>，则只转换<code>&amp;#number</code> 实体。
     * @param str 包含转义字符的字符串
     * @param out 字符输出流，不能为<code>null</code>
     * @return 如果字符串没有变化，则返回<code>false</code>
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     */
    private static boolean unescapeEntitiesInternal(Entities entities, String str, Appendable out) throws IOException {
        boolean needToChange = false;

        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        if (str == null) {
            return needToChange;
        }

        for (int i = 0; i < str.length(); ++i) {
            char ch = str.charAt(i);

            if (ch == '&') {
                // 查找&xxxx;
                int semi = str.indexOf(';', i + 1);

                if (semi == -1 || i + 1 >= semi - 1) {
                    out.append(ch);
                    continue;
                }

                // 如果是&#xxxxx;
                if (str.charAt(i + 1) == '#') {
                    int firstCharIndex = i + 2;
                    int radix = 10;

                    if (firstCharIndex >= semi - 1) {
                        out.append(ch);
                        out.append('#');
                        i++;
                        continue;
                    }

                    char firstChar = str.charAt(firstCharIndex);

                    if (firstChar == 'x' || firstChar == 'X') {
                        firstCharIndex++;
                        radix = 16;

                        if (firstCharIndex >= semi - 1) {
                            out.append(ch);
                            out.append('#');
                            i++;
                            continue;
                        }
                    }

                    try {
                        int entityValue = Integer.parseInt(str.substring(firstCharIndex, semi), radix);

                        out.append((char) entityValue);

                        // 设置改变标志
                        needToChange = true;
                    } catch (NumberFormatException e) {
                        out.append(ch);
                        out.append('#');
                        i++;
                        continue;
                    }
                } else {
                    String entityName = str.substring(i + 1, semi);
                    int entityValue = -1;

                    if (entities != null) {
                        entityValue = entities.getEntityValue(entityName);
                    }

                    if (entityValue == -1) {
                        out.append('&');
                        out.append(entityName);
                        out.append(';');
                    } else {
                        out.append((char) entityValue);

                        // 设置改变标志
                        needToChange = true;
                    }
                }

                i = semi;
            } else {
                out.append(ch);
            }
        }

        return needToChange;
    }

    // ==========================================================================
    // SQL语句。
    // ==========================================================================

    /**
     * 按SQL语句的规则对字符串进行转义。
     * <p>
     * 例如：
     * <p/>
     * 
     * <pre>
     * statement.executeQuery(&quot;SELECT * FROM MOVIES WHERE TITLE='&quot; + StringEscapeUtil.escapeSql(&quot;McHale's Navy&quot;) + &quot;'&quot;);
     * </pre>
     * <p/>
     * </p>
     * <p>
     * 目前，此方法只将单引号转换成两个单引号：<code>"McHale's Navy"</code>转换成<code>"McHale''s
     * Navy"</code>。不处理字符串中包含的<code>%</code>和<code>_</code>字符。
     * </p>
     * 
     * @param str 要转义的字符串
     * @return 转义后的字符串，如果原字符串为<code>null</code>，则返回<code>null</code>
     * @see <a href="http://www.jguru.com/faq/view.jsp?EID=8881">faq</a>
     */
    public static String escapeSql(String str) {
        return StringUtil.replace(str, "'", "''");
    }

    /**
     * 按SQL语句的规则对字符串进行转义。
     * <p>
     * 例如：
     * <p/>
     * 
     * <pre>
     * statement.executeQuery(&quot;SELECT * FROM MOVIES WHERE TITLE='&quot; + StringEscapeUtil.escapeSql(&quot;McHale's Navy&quot;) + &quot;'&quot;);
     * </pre>
     * <p/>
     * </p>
     * <p>
     * 目前，此方法只将单引号转换成两个单引号：<code>"McHale's Navy"</code>转换成<code>"McHale''s
     * Navy"</code>。不处理字符串中包含的<code>%</code>和<code>_</code>字符。
     * </p>
     * 
     * @param str 要转义的字符串
     * @param out 输出流
     * @throws IllegalArgumentException 如果输出流为<code>null</code>
     * @throws IOException 如果输出失败
     * @see <a href="http://www.jguru.com/faq/view.jsp?EID=8881">faq</a>
     */
    public static void escapeSql(String str, Appendable out) throws IOException {
        if (out == null) {
            throw new IllegalArgumentException("The Appendable must not be null");
        }

        String result = StringUtil.replace(str, "'", "''");

        if (result != null) {
            out.append(result);
        }
    }

    // ==========================================================================
    // URL/URI encoding/decoding。
    // 根据RFC2396：http://www.ietf.org/rfc/rfc2396.txt
    // ==========================================================================

    /** "Alpha" characters from RFC 2396. */
    private static final BitSet ALPHA      = new BitSet(256);

    static {
        for (int i = 'a'; i <= 'z'; i++) {
            ALPHA.set(i);
        }

        for (int i = 'A'; i <= 'Z'; i++) {
            ALPHA.set(i);
        }
    }

    /** "Alphanum" characters from RFC 2396. */
    private static final BitSet ALPHANUM   = new BitSet(256);

    static {
        ALPHANUM.or(ALPHA);

        for (int i = '0'; i <= '9'; i++) {
            ALPHANUM.set(i);
        }
    }

    /** "Mark" characters from RFC 2396. */
    private static final BitSet MARK       = new BitSet(256);

    static {
        MARK.set('-');
        MARK.set('_');
        MARK.set('.');
        MARK.set('!');
        MARK.set('~');
        MARK.set('*');
        MARK.set('\'');
        MARK.set('(');
        MARK.set(')');
    }

    /** "Reserved" characters from RFC 2396. */
    private static final BitSet RESERVED   = new BitSet(256);

    static {
        RESERVED.set(';');
        RESERVED.set('/');
        RESERVED.set('?');
        RESERVED.set(':');
        RESERVED.set('@');
        RESERVED.set('&');
        RESERVED.set('=');
        RESERVED.set('+');
        RESERVED.set('$');
        RESERVED.set(',');
    }

    /** "Unreserved" characters from RFC 2396. */
    private static final BitSet UNRESERVED = new BitSet(256);

    static {
        UNRESERVED.or(ALPHANUM);
        UNRESERVED.or(MARK);
    }
}
