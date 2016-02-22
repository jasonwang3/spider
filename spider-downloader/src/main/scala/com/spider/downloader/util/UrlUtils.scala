package com.spider.downloader.util

import java.nio.charset.Charset
import java.util.regex.{Pattern, Matcher}

/**
  * Created by jason on 16-2-22.
  */

object UrlUtils {
  val patternForCharset: Pattern = Pattern.compile("charset\\s*=\\s*['\"]*([^\\s;'\"]*)")

  def getCharset(contentType: String): String = {
    val matcher: Matcher = UrlUtils.patternForCharset.matcher(contentType)
    if (matcher.find) {
      val charset: String = matcher.group(1)
      if (Charset.isSupported(charset)) {
        charset
      }
    }
    null
  }

}

