package com.spider.model.processor

/**
  * Created by jason on 16-5-18.
  */
@SerialVersionUID(1L)
class AnalyzeResponse(_spiderId: String, _step: Int, _targets: List[String], _content: String) extends Serializable {

  val spiderId: String = _spiderId

  val step: Int = _step

  val targets: List[String] = _targets

  val content = _content


  override def toString = s"AnalyzeResponse($spiderId, $step, $targets, $content)"
}
