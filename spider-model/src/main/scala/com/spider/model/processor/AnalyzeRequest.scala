package com.spider.model.processor

import com.spider.model.Rule

/**
  * Created by jason on 16-5-17.
  */
@SerialVersionUID(1L)
class AnalyzeRequest(_spiderId: String, _step: Int, _htmlRaw: String, _rule: Rule) extends Serializable {

  val spiderId: String = _spiderId

  val step: Int = _step

  val htmlRaw: String = _htmlRaw

  val rule: Rule = _rule
}
