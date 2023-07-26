package com.gandalf.framework.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.CharArrayBuffer;
import org.brotli.dec.BrotliInputStream;

import com.gandalf.framework.constant.ContentEncodingEnum;

public final class EntityUtils {

	private EntityUtils() {
	}

	/**
	 * Ensures that the entity content is fully consumed and the content stream, if
	 * exists, is closed. The process is done, <i>quietly</i> , without throwing any
	 * IOException.
	 *
	 * @param entity
	 *
	 *
	 * @since 4.2
	 */
	public static void consumeQuietly(final HttpEntity entity) {
		try {
			consume(entity);
		} catch (IOException ioex) {
		}
	}

	/**
	 * Ensures that the entity content is fully consumed and the content stream, if
	 * exists, is closed.
	 *
	 * @param entity
	 * @throws IOException if an error occurs reading the input stream
	 *
	 * @since 4.1
	 */
	public static void consume(final HttpEntity entity) throws IOException {
		if (entity == null) {
			return;
		}
		if (entity.isStreaming()) {
			InputStream instream = entity.getContent();
			if (instream != null) {
				instream.close();
			}
		}
	}

	/**
	 * Read the contents of an entity and return it as a byte array.
	 *
	 * @param entity
	 * @return byte array containing the entity content. May be null if
	 *         {@link HttpEntity#getContent()} is null.
	 * @throws IOException              if an error occurs reading the input stream
	 * @throws IllegalArgumentException if entity is null or if content length >
	 *                                  Integer.MAX_VALUE
	 */
	public static byte[] toByteArray(final HttpEntity entity) throws IOException {
		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		InputStream instream = entity.getContent();
		if (instream == null) {
			return null;
		}
		try {
			if (entity.getContentLength() > Integer.MAX_VALUE) {
				throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
			}
			int i = (int) entity.getContentLength();
			if (i < 0) {
				i = 4096;
			}
			ByteArrayBuffer buffer = new ByteArrayBuffer(i);
			byte[] tmp = new byte[4096];
			int l;
			while ((l = instream.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
			return buffer.toByteArray();
		} finally {
			instream.close();
		}
	}

	public static String toString(final HttpEntity entity, final Charset defaultCharset, String contentEncoding)
			throws IOException, ParseException {
		if (entity == null) {
			throw new IllegalArgumentException("HTTP entity may not be null");
		}
		InputStream instream = entity.getContent();
		if (instream == null) {
			return null;
		}
		ContentEncodingEnum encodingEnum = ContentEncodingEnum.getEnum(contentEncoding);
		if (ContentEncodingEnum.gzip.equals(encodingEnum)) {
			instream = new GZIPInputStream(instream);
		} else if (ContentEncodingEnum.br.equals(encodingEnum)) {
			instream = new BrotliInputStream(instream);
		} else {
			instream = entity.getContent();
		}
		try {
			if (entity.getContentLength() > Integer.MAX_VALUE) {
				throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
			}
			int i = (int) entity.getContentLength();
			if (i < 0) {
				i = 4096;
			}
			Charset charset = null;
			try {
				ContentType contentType = ContentType.get(entity);
				if (contentType != null) {
					charset = contentType.getCharset();
				}
			} catch (final UnsupportedCharsetException ex) {
				throw new UnsupportedEncodingException(ex.getMessage());
			}
			if (charset == null || defaultCharset != null) {
				charset = defaultCharset;
			}
			if (charset == null) {
				charset = HTTP.DEF_CONTENT_CHARSET;
			}
			Reader reader = new InputStreamReader(instream, charset);
			CharArrayBuffer buffer = new CharArrayBuffer(i);
			char[] tmp = new char[1024];
			int l;
			while ((l = reader.read(tmp)) != -1) {
				buffer.append(tmp, 0, l);
			}
			return buffer.toString();
		} finally {
			instream.close();
		}
	}

}
