package com.spider.processor.selector.impl

import java.util.regex.{Matcher, Pattern}

import com.spider.model.selector.RegexResult
import com.spider.processor.selector.Selector
import org.apache.commons.lang3.StringUtils

import scala.collection.mutable.ListBuffer


/**
  * Created by jason on 16-2-26.
  */
object RegexSelector {
  def apply(_regexStr: String, _group: Int): RegexSelector = {
    assert(!_regexStr.isEmpty)
    val regexSelector: RegexSelector = new RegexSelector
    var regexStr: String = _regexStr
    if (StringUtils.countMatches(regexStr, "(") - StringUtils.countMatches(regexStr, "\\(") == StringUtils.countMatches(regexStr, "(?:") - StringUtils.countMatches(regexStr, "\\(?:")) {
      regexStr = "(" + regexStr + ")"
    }
    regexSelector.regexStr = regexStr
    regexSelector.regex = Pattern.compile(regexStr, Pattern.DOTALL | Pattern.CASE_INSENSITIVE)
    regexSelector.group = _group
    regexSelector
  }

  def apply(_regex: String): RegexSelector = {
    apply(_regex, 1)
  }
}


class RegexSelector private() extends Selector {

  var regexStr: String = null

  var regex: Pattern = null

  var group: Int = 1

  override def select(text: String): String = {
    selectGroup(text).get(group)
  }

  override def selectList(test: String): List[String] = {
    var strings: ListBuffer[String] = ListBuffer()
    val results: List[RegexResult] = selectGroupList(test)
    results.foreach(result => strings += result.get(group))
    strings.toList
  }


  def selectGroup(text: String): RegexResult = {
    val matcher: Matcher = regex.matcher(text)
    if (matcher.find) {
      val groups: Array[String] = new Array[String](matcher.groupCount + 1)
      for (i <- groups.indices) {
        groups(i) = matcher.group(i)
      }
      return new RegexResult(groups)
    }
    RegexResult.EMPTY_RESULT
  }


  def selectGroupList(text: String): List[RegexResult] = {
    val matcher: Matcher = regex.matcher(text)
    var resultList: ListBuffer[RegexResult] = ListBuffer()
    while (matcher.find) {
      val groups: Array[String] = new Array[String](matcher.groupCount + 1)
      for (i <- groups.indices) {
        groups(i) = matcher.group(i)
      }
      resultList += new RegexResult(groups)
    }
    resultList.toList
  }
}

