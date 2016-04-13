package com.spider.processor.selector.impl


import com.spider.processor.selector.Selector

/**
  * Created by jason on 16-3-2.
  */
class SmartContentSelector extends Selector {
  override def select(_html: String): String = {
    var html = ""
    html = _html.replaceAll("(?is)<!DOCTYPE.*?>", "")
    html = _html.replaceAll("(?is)<!--.*?-->", "")
    html = _html.replaceAll("(?is)<script.*?>.*?</script>", "")
    html = _html.replaceAll("(?is)<style.*?>.*?</style>", "")
    html = _html.replaceAll("&.{2,5};|&#.{2,5};", " ")
    html = _html.replaceAll("(?is)<.*?>", "")
    var lines: List[String] = null
    val blocksWidth: Int = 3
    val threshold: Int = 86
    var start: Int = 0
    var end: Int = 0
    val text: StringBuilder = new StringBuilder
    val indexDistribution: List[Integer] = List()

    lines = html.split("\n").toList
    lines.take(lines.length - blocksWidth).foreach(_ => {
      var wordsNum = 0

    })
    //TODO:complete select method

    ""
  }

  override def selectList(test: String): List[String] = {
    throw new UnsupportedOperationException
  }
}
