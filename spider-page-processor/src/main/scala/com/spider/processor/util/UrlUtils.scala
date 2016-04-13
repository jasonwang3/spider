package com.spider.processor.util

import java.net.{MalformedURLException, URL}
import java.nio.charset.Charset
import java.util.regex.{Pattern, Matcher}

import org.apache.commons.lang3.StringUtils

/**
  * Created by jason on 16-2-22.
  */

object UrlUtils {
  val patternForCharset: Pattern = Pattern.compile("charset\\s*=\\s*['\"]*([^\\s;'\"]*)")
  /**
    * allow blank space in quote
    */
  private var patternForHrefWithQuote: Pattern = Pattern.compile("(<a[^<>]*href=)[\"']([^\"'<>]*)[\"']", Pattern.CASE_INSENSITIVE)
  /**
    * disallow blank space without quote
    */
  private var patternForHrefWithoutQuote: Pattern = Pattern.compile("(<a[^<>]*href=)([^\"'<>\\s]+)", Pattern.CASE_INSENSITIVE)


  def canonicalizeUrl(url: String, refer: String): String = {
    var base: URL = null
    var _url: String = url
    try {
      try {
        base = new URL(refer)
      }
      catch {
        case e: MalformedURLException => {
          val abs: URL = new URL(refer)
          return abs.toExternalForm
        }
      }
      if (url.startsWith("?")) {
        _url = base.getPath + url
      }
      val abs: URL = new URL(base, _url)
      return encodeIllegalCharacterInUrl(abs.toExternalForm)
    }
    catch {
      case e: MalformedURLException => {
        return ""
      }
    }
  }

  def  encodeIllegalCharacterInUrl (url: String):String =  {
    return url.replace(" ", "%20")
  }

  def getCharset(contentType: String): String = {
    val matcher: Matcher = UrlUtils.patternForCharset.matcher(contentType)
    if (matcher.find) {
      val charset: String = matcher.group(1)
      if (Charset.isSupported(charset)) {
        return charset
      }
    }
    null
  }

  def fixAllRelativeHrefs(html: String, url: String): String = {
    var _html = html
    _html = replaceByPattern(_html, url, patternForHrefWithQuote)
    _html = replaceByPattern(_html, url, patternForHrefWithoutQuote)
    return _html
  }

  def replaceByPattern(html: String, url: String, pattern: Pattern): String = {
    val stringBuilder: StringBuilder = new StringBuilder
    val matcher: Matcher = pattern.matcher(html)
    var lastEnd: Int = 0
    var modified: Boolean = false
    while (matcher.find) {
      modified = true
      stringBuilder.append(StringUtils.substring(html, lastEnd, matcher.start))
      stringBuilder.append(matcher.group(1))
      stringBuilder.append("\"").append(canonicalizeUrl(matcher.group(2), url)).append("\"")
      lastEnd = matcher.end
    }
    if (!modified) {
      return html
    }
    stringBuilder.append(StringUtils.substring(html, lastEnd))
    return stringBuilder.toString
  }
}

