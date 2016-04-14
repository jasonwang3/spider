package com.spider.processor.selector.impl

import java.util.regex.{Matcher, Pattern}

import com.spider.processor.selector.Selector

/**
  * Created by jason on 16-4-14.
  */
class ReplaceSelector(_regexStr: String, _replacement: String) extends Selector{
  private val regexStr: String = _regexStr

  private val replacement = _replacement

  private var regex: Pattern = Pattern.compile(regexStr)

  override def select(text: String): String = {
    val matcher: Matcher = regex.matcher(text)
    return matcher.replaceAll(replacement)
  }

  override def selectList(text: String): List[String] = throw new UnsupportedOperationException
}
