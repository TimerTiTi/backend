package com.titi.titi_common_lib.util;

import static com.titi.titi_common_lib.constant.Constants.*;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HttpRequestHeaderParser {

	/**
	 * The <b>User-Agent</b> request header is a characteristic string that lets servers and network peers identify the application,
	 * operating system, vendor, and/or version of the requesting user agent.
	 * <br/><br/>
	 * <b>Syntax</b> <br/>
	 * User-Agent: product / product-version comment <br/>
	 * Common format for web browsers: <br/>
	 * User-Agent: Mozilla/5.0 (system-information) platform (platform-details) extensions
	 * <br/><br/>
	 * @return system-information
	 * @see <a href="https://www.rfc-editor.org/rfc/rfc9110#field.user-agent">RFC-9110#field.user-agent</a>
	 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/User-Agent">MDN Web Docs User-Agent</a>
	 */
	public static String parseSystemInformationFromUserAgent(@NonNull String userAgent) {
		int firstSpaceIndex = userAgent.indexOf(' ');
		if (firstSpaceIndex != EOS) {
			return parseParenthesis(userAgent, firstSpaceIndex);
		}
		return failParseUserAgent(userAgent);
	}

	private static String parseParenthesis(String userAgent, int firstSpaceIndex) {
		final String substringAfterSpace = userAgent.substring(firstSpaceIndex + 1);
		final int openingParenthesisIndex = substringAfterSpace.indexOf('(');
		final int closingParenthesisIndex = substringAfterSpace.indexOf(')');
		if (openingParenthesisIndex != EOS && closingParenthesisIndex != EOS) {
			return substringAfterSpace.substring(openingParenthesisIndex + 1, closingParenthesisIndex);
		}
		return failParseUserAgent(userAgent);
	}

	private static String failParseUserAgent(String userAgent) {
		log.warn("Cannot parse system-information from User-Agent header: {}", userAgent);
		return null;
	}

}
